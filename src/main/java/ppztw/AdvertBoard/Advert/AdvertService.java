package ppztw.AdvertBoard.Advert;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ppztw.AdvertBoard.Exception.ResourceNotFoundException;
import ppztw.AdvertBoard.Model.Advert.Advert;
import ppztw.AdvertBoard.Model.Advert.Category;
import ppztw.AdvertBoard.Repository.Advert.AdvertRepository;
import ppztw.AdvertBoard.Repository.Advert.CategoryRepository;
import ppztw.AdvertBoard.View.Advert.AdvertSummaryView;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdvertService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    AdvertRepository advertRepository;

    public Page<AdvertSummaryView> getPageByCategoryId(Long categoryId, Pageable pageable,
                                                       String titleContains) {
        List<Long> categoryIds = getAllCategoryIds(categoryId);
        List<AdvertSummaryView> summaryViewList = new ArrayList<>();
        Page<Advert> adverts;
        if (titleContains != null && !titleContains.isEmpty())
            adverts = advertRepository.findAllByCategoryIdInAndTitleLike(categoryIds, titleContains, pageable);
        else
            adverts = advertRepository.findAllByCategoryIdIn(categoryIds, pageable);
        for (Advert advert : adverts)
            summaryViewList.add(new AdvertSummaryView(advert));

        return new PageImpl<>(summaryViewList, pageable, adverts.getTotalElements());
    }

    private List<Long> getAllCategoryIds(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
        List<Long> categoryIds = new ArrayList<>();
        categoryIds.add(category.getId());
        for (Category subcategory : category.getAllSubcategories())
            categoryIds.add(subcategory.getId());
        return categoryIds;
    }
}
