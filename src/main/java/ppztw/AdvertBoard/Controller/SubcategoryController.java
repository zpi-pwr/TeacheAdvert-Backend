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
import ppztw.AdvertBoard.Payload.CreateSubcategoryRequest;
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
@RequestMapping("/subcategory")
public class SubcategoryController {

    private static final Logger logger = LoggerFactory.getLogger(SubcategoryController.class);

    @Autowired
    private SubcategoryRepository subCategoryRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private AdvertRepository advertRepository;


    @PostMapping("/add")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> addSubcategory(@CurrentUser UserPrincipal userPrincipal, @Valid @RequestBody CreateSubcategoryRequest createSubcategoryRequest) {
        if(subCategoryRepository.existsBySubcategoryName(createSubcategoryRequest.getSubcategoryName())) {
            throw new BadRequestException("Subcategory with this name already exists!");
        }

        Category category = categoryRepository.findByCategoryName(createSubcategoryRequest.getCategory())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "name", createSubcategoryRequest.getCategory()));

        Subcategory subcategory = new Subcategory();
        subcategory.setSubcategoryName(createSubcategoryRequest.getSubcategoryName());
        subcategory.setDescription(createSubcategoryRequest.getDescription());
        subcategory.setParentCategory(category);

        logger.info(String.format("Admin %s has created subcategory with name %s and parent category %s"
                , userPrincipal.getName(), createSubcategoryRequest.getSubcategoryName(), createSubcategoryRequest.getCategory()));

        subCategoryRepository.save(subcategory);

        return ResponseEntity.ok(new ApiResponse(true, "Subcategory created successfully!"));
    }

    @PostMapping("/remove")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> removeSubcategory(@CurrentUser UserPrincipal userPrincipal, String subcategoryName) {
        Subcategory subcategory = subCategoryRepository.findBySubcategoryName(subcategoryName)
                .orElseThrow(() -> new ResourceNotFoundException("Subcategory", "name", subcategoryName));

        Category parentCategory = subcategory.getParentCategory();

        if(parentCategory != null) {
            parentCategory.getSubCategories().remove(subcategory);
            categoryRepository.save(parentCategory);
        }

        subCategoryRepository.delete(subcategory);

        logger.info(String.format("Admin %s has removed subcategory with name %s and parent category: %s"
                , userPrincipal.getName(), subcategoryName, parentCategory.getCategoryName()));

        return ResponseEntity.ok(new ApiResponse(true, "Category removed successfully!"));
    }

    @PostMapping("/edit")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> changeCategory(@CurrentUser UserPrincipal userPrincipal, String subcategoryName, String newCategoryName) {
        Subcategory subcategory = subCategoryRepository.findBySubcategoryName(subcategoryName)
                .orElseThrow(() -> new ResourceNotFoundException("Subcategory", "name", subcategoryName));

        Category category = categoryRepository.findByCategoryName(newCategoryName)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "name", subcategoryName));

        subcategory.changeCategory(category);

        subCategoryRepository.save(subcategory);
        categoryRepository.save(category);

        logger.info(String.format("Admin %s has changed subcategory with name %s. New parent: %s"
                , userPrincipal.getName(), subcategoryName, newCategoryName));

        return ResponseEntity.ok(new ApiResponse(true, "Subcategory edited successfully!"));
    }

    @GetMapping("/all")
    @PreAuthorize("permitAll()")
    public List<Subcategory> getAllSubcategories() {
        return subCategoryRepository.findAll();
    }

    @GetMapping("/get")
    @PreAuthorize("permitAll()")
    public Page<Advert> getSubcategoryAdverts(@RequestParam("subcategoryName") String subcategoryName,
                                              Pageable pageable) {
        Subcategory subcategory = subCategoryRepository.findBySubcategoryName(subcategoryName)
                .orElseThrow(() -> new ResourceNotFoundException("Subcategory", "name", subcategoryName));
        PageUtils<Advert> pageUtils = new PageUtils<>();
        List<Long> advertIds = subcategory.getAdvertsIds();
        List<Advert> adverts = new ArrayList<>();
        Iterable<Advert> iterable = advertRepository.findAllById(advertIds);
        iterable.forEach(adverts::add);
        return pageUtils.getPage(adverts, pageable);
    }
}