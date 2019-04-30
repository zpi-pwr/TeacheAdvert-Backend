package ppztw.AdvertBoard.Advert.Category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ppztw.AdvertBoard.Exception.ResourceNotFoundException;
import ppztw.AdvertBoard.Model.Advert.Advert;
import ppztw.AdvertBoard.Model.Advert.Category;
import ppztw.AdvertBoard.Repository.Advert.CategoryRepository;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;


    public List<Advert> getCategoryAdverts(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

        List<Advert> adverts = category.getAdverts().stream()
                .filter(advert -> advert.getStatus() != Advert.Status.ARCHIVED
                        && advert.getStatus() != Advert.Status.BANNED)
                .collect(Collectors.toList());

        return adverts;
    }


}
