package ppztw.AdvertBoard.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ppztw.AdvertBoard.Exception.ResourceNotFoundException;
import ppztw.AdvertBoard.Model.Advert;
import ppztw.AdvertBoard.Model.Category;
import ppztw.AdvertBoard.Model.CategoryInfo;
import ppztw.AdvertBoard.Model.InfoType;
import ppztw.AdvertBoard.Payload.ApiResponse;
import ppztw.AdvertBoard.Payload.CreateCategoryRequest;
import ppztw.AdvertBoard.Repository.AdvertRepository;
import ppztw.AdvertBoard.Repository.CategoryRepository;
import ppztw.AdvertBoard.Security.CurrentUser;
import ppztw.AdvertBoard.Security.UserPrincipal;
import ppztw.AdvertBoard.Util.PageUtils;
import ppztw.AdvertBoard.View.Advert.AdvertSummaryView;
import ppztw.AdvertBoard.View.CategoryView;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("category")
public class CategoryController {

    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private AdvertRepository advertRepository;

    @PostMapping("/add")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> addCategory(@CurrentUser UserPrincipal userPrincipal, @Valid @RequestBody CreateCategoryRequest createCategoryRequest) {

        Category category = new Category();
        category.setCategoryName(createCategoryRequest.getCategoryName());
        category.setDescription(createCategoryRequest.getDescription());

        Long parentId = createCategoryRequest.getParentId();
        if (parentId != null) {
            Category parent = categoryRepository.findById(parentId)
                    .orElseThrow(() -> new ResourceNotFoundException("Category", "id", parentId));

            category.setParentCategory(parent);
        }

        List<CategoryInfo> categoryInfos = new ArrayList<>();

        if (createCategoryRequest.getInfos() != null) {
            for (Map.Entry<String, InfoType> entry : createCategoryRequest.getInfos().entrySet())
                categoryInfos.add(new CategoryInfo(entry.getKey(), entry.getValue()));

        }
        category.setInfoList(categoryInfos);

        logger.info(String.format("Admin %s has created category with name %s", userPrincipal.getName(), createCategoryRequest.getCategoryName()));

        categoryRepository.save(category);

        return ResponseEntity.ok(new ApiResponse(true, "Category created successfully!"));
    }

    @PostMapping("/remove")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> removeCategory(@CurrentUser UserPrincipal userPrincipal, @RequestParam Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));

        List<Category> subcategories = category.getSubcategories();

        for(int i = 0; i < subcategories.size(); i++) {
            Category subcategory = subcategories.get(i);
            subcategory.setParentCategory(null);
            categoryRepository.save(subcategory);
        }

        categoryRepository.delete(category);

        logger.info(String.format("Admin %s has removed category with id %s"
                , userPrincipal.getName(), id));

        return ResponseEntity.ok(new ApiResponse(true, "Category removed successfully!"));
    }

    @GetMapping("/all")
    @PreAuthorize("permitAll()")
    public CategoryView getCategory() {
        Category root = categoryRepository.findById(0L)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", "root"));

        return new CategoryView(root);
    }

    @GetMapping("/get")
    @PreAuthorize("permitAll()")
    public Page<AdvertSummaryView> getCategoryAdverts(
            @RequestParam Long categoryId, Pageable pageable,
            @RequestParam(required = false) LocalDate maxDate,
            @RequestParam(required = false) LocalDate minDate,
            @RequestParam(required = false) String titleContains) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
        PageUtils<AdvertSummaryView> pageUtils = new PageUtils<>();
        List<Advert> adverts = category.getAdverts();
        List<AdvertSummaryView> advertViews = new ArrayList<>();
        for (Advert advert : adverts)
            advertViews.add(new AdvertSummaryView(advert));

        if (maxDate != null)
            advertViews = advertViews.stream()
                    .filter(advert -> advert.getDate().isBefore(maxDate))
                    .collect(Collectors.toList());
        if (minDate != null)
            advertViews = advertViews.stream()
                    .filter(advert -> advert.getDate().isAfter(minDate))
                    .collect(Collectors.toList());
        if (titleContains != null)
            advertViews = advertViews.stream()
                    .filter(advert -> advert.getTitle().contains(titleContains))
                    .collect(Collectors.toList());

        return pageUtils.getPage(advertViews, pageable);
    }
}