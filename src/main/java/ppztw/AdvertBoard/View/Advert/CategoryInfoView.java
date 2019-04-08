package ppztw.AdvertBoard.View.Advert;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ppztw.AdvertBoard.Model.Advert.CategoryInfo;
import ppztw.AdvertBoard.Model.Advert.InfoType;

@Getter
@Setter
@NoArgsConstructor
public class CategoryInfoView {
    Long id;
    String name;
    InfoType type;

    public CategoryInfoView(CategoryInfo categoryInfo) {
        this.id = categoryInfo.getId();
        this.name = categoryInfo.getName();
        this.type = categoryInfo.getType();
    }
}
