package ppztw.AdvertBoard.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ppztw.AdvertBoard.Exception.BadRequestException;
import ppztw.AdvertBoard.Exception.ResourceNotFoundException;
import ppztw.AdvertBoard.Model.Advert;
import ppztw.AdvertBoard.Model.Category;
import ppztw.AdvertBoard.Model.Subcategory;
import ppztw.AdvertBoard.Payload.ApiResponse;
import ppztw.AdvertBoard.Payload.CreateCategoryRequest;
import ppztw.AdvertBoard.Repository.AdvertRepository;
import ppztw.AdvertBoard.Repository.CategoryRepository;
import ppztw.AdvertBoard.Repository.SubcategoryRepository;
import ppztw.AdvertBoard.Security.CurrentUser;
import ppztw.AdvertBoard.Security.UserPrincipal;
import ppztw.AdvertBoard.Util.PageUtils;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("category")
public class CategoryController {

    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private SubcategoryRepository subCategoryRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private AdvertRepository advertRepository;

    @PostMapping("/add")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> addCategory(@CurrentUser UserPrincipal userPrincipal, @Valid @RequestBody CreateCategoryRequest createCategoryRequest) {
        if(categoryRepository.existsByCategoryName(createCategoryRequest.getCategoryName())) {
            throw new BadRequestException("Category with this name already exists!");
        }


        Category category = new Category();
        category.setCategoryName(createCategoryRequest.getCategoryName());
        category.setDescription(createCategoryRequest.getDescription());

        logger.info(String.format("Admin %s has created category with name %s", userPrincipal.getName(), createCategoryRequest.getCategoryName()));

        categoryRepository.save(category);

        return ResponseEntity.ok(new ApiResponse(true, "Category created successfully!"));
    }

    @PostMapping("/remove")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> removeCategory(@CurrentUser UserPrincipal userPrincipal, @RequestParam String categoryName) {
        Category category = categoryRepository.findByCategoryName(categoryName)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "name", categoryName));

        List<Subcategory> subcategories = category.getSubCategories();

        for(int i = 0; i < subcategories.size(); i++) {
            Subcategory subcategory = subcategories.get(i);
            subcategory.setParentCategory(null);
            subCategoryRepository.save(subcategory);
        }

        categoryRepository.delete(category);

        logger.info(String.format("Admin %s has removed category with name %s"
                , userPrincipal.getName(), categoryName));

        return ResponseEntity.ok(new ApiResponse(true, "Category removed successfully!"));
    }

    @GetMapping("/all")
    @PreAuthorize("permitAll()")
    public List<Category> getCategory() {
        return categoryRepository.findAll();
    }

    @GetMapping("/get")
    @PreAuthorize("permitAll()")
    public Page<Advert> getCategoryAdverts(@RequestParam String categoryName, Pageable pageable) {
        Category category = categoryRepository.findByCategoryName(categoryName)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "name", categoryName));
        PageUtils<Advert> pageUtils = new PageUtils<>();
        List<Long> advertIds = category.getAdvertsIds();
        List<Advert> adverts = new ArrayList<>();
        Iterable<Advert> iterable = advertRepository.findAllById(advertIds);
        iterable.forEach(adverts::add);
        return pageUtils.getPage(adverts, pageable);
    }
}