package ppztw.AdvertBoard.View;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ppztw.AdvertBoard.Model.CategoryInfo;
import ppztw.AdvertBoard.Model.InfoType;

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
