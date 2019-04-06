package ppztw.AdvertBoard.View;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ppztw.AdvertBoard.Model.Category;
import ppztw.AdvertBoard.Model.CategoryInfo;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CategoryView {
    Long id;
    String name;
    List<CategoryView> subcategories;
    List<CategoryInfoView> infoList;

    public CategoryView(Category category) {
        this.id = category.getId();
        this.name = category.getCategoryName();
        subcategories = new ArrayList<>();
        for (Category subcategory : category.getSubcategories()) {
            subcategories.add(new CategoryView(subcategory));
        }
        infoList = new ArrayList<>();
        for (CategoryInfo info : category.getInfoList())
            infoList.add(new CategoryInfoView(info));
    }
}
