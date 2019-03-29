package ppztw.AdvertBoard.View.Advert;

import lombok.Getter;
import lombok.Setter;
import ppztw.AdvertBoard.Model.Advert;
import ppztw.AdvertBoard.Model.Tag;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class AdvertDetailsView extends AdvertSummaryView {

    Long userId;
    String userName;
    String description;
    Long categoryId;
    String categoryName;
    List<String> tags;


    public AdvertDetailsView(Advert advert) {
        super(advert);
        this.userId = advert.getUser().getId();
        this.userName = advert.getUser().getName();
        this.description = advert.getDescription();
        this.categoryId = advert.getSubcategory().getId();
        this.categoryName = advert.getSubcategory().getCategoryName();
        this.tags = new ArrayList<>();
        for (Tag tag : advert.getTags())
            this.tags.add(tag.getName());
    }
}
