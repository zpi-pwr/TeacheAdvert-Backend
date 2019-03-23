package ppztw.AdvertBoard.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ppztw.AdvertBoard.Exception.BadRequestException;
import ppztw.AdvertBoard.Exception.ResourceNotFoundException;
import ppztw.AdvertBoard.Model.Category;
import ppztw.AdvertBoard.Model.Subcategory;
import ppztw.AdvertBoard.Payload.ApiResponse;
import ppztw.AdvertBoard.Payload.CreateCategoryRequest;
import ppztw.AdvertBoard.Payload.CreateSubcategoryRequest;
import ppztw.AdvertBoard.Repository.CategoryRepository;
import ppztw.AdvertBoard.Repository.SubcategoryRepository;
import ppztw.AdvertBoard.Security.CurrentUser;
import ppztw.AdvertBoard.Security.UserPrincipal;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private SubcategoryRepository subCategoryRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @PostMapping("/add")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> addCategory(@CurrentUser UserPrincipal userPrincipal, @Valid @RequestBody CreateCategoryRequest createCategoryRequest) {
        if(categoryRepository.existsByCategoryName(createCategoryRequest.getCategoryName())) {
            throw new BadRequestException("Category with this name already exists!");
        }


        Category category = new Category();
        category.setCategoryName(createCategoryRequest.getCategoryName());
        category.setDescription(createCategoryRequest.getDescription());

        logger.info(String.format("Admin %s has created category with the name %s", userPrincipal.getName(), createCategoryRequest.getCategoryName()));

        categoryRepository.save(category);

        return ResponseEntity.ok(new ApiResponse(true, "Category created successfully!"));
    }

    @PostMapping("/add_subcategory")
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

        logger.info(String.format("Admin %s has created subcategory with the name %s and parent category %s"
                , userPrincipal.getName(), createSubcategoryRequest.getSubcategoryName(), createSubcategoryRequest.getCategory()));

        subCategoryRepository.save(subcategory);

        return ResponseEntity.ok(new ApiResponse(true, "Subcategory created successfully!"));
    }

    @GetMapping("/get/all")
    @PreAuthorize("permitAll()")
    public Map<String, List<Category>> getCategory() {
        List<Category> categories = categoryRepository.findAll();
        Map<String, List<Category>> categoryMap = new HashMap<>();
        categoryMap.put("categories", categories);
        return categoryMap;
    }

    @GetMapping("/get_subcategory/all")
    @PreAuthorize("permitAll()")
    public Map<String, List<Subcategory>> getSubcategory() {
        List<Subcategory> subcategories = subCategoryRepository.findAll();
        Map<String, List<Subcategory>> categoryMap = new HashMap<>();
        categoryMap.put("subcategories", subcategories);
        return categoryMap;
    }
}