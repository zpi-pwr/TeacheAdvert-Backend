package ppztw.AdvertBoard.View.Advert;

import lombok.Getter;
import lombok.Setter;
import ppztw.AdvertBoard.Model.Advert;
import ppztw.AdvertBoard.Model.AdvertInfo;
import ppztw.AdvertBoard.Model.Tag;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class AdvertDetailsView extends AdvertSummaryView {

    Long userId;
    String userName;
    String description;
    Long categoryId;
    String categoryName;
    List<String> tags;
    Map<String, Object> additionalInfo;


    public AdvertDetailsView(Advert advert) {
        super(advert);
        this.userId = advert.getUser().getId();
        this.userName = advert.getUser().getName();
        this.description = advert.getDescription();
        this.categoryId = advert.getSubcategory().getId();
        this.categoryName = advert.getSubcategory().getCategoryName();
        this.tags = new ArrayList<>();
        this.additionalInfo = new HashMap<>();
        for (Tag tag : advert.getTags())
            this.tags.add(tag.getName());
        for (AdvertInfo info : advert.getAdditionalInfo()) {
            Object value = new Object();
            switch (info.getCategoryInfo().getType()) {
                case word:
                    value = info.getValue();
                    break;
                case money:
                    value = BigDecimal.valueOf(Double.valueOf(info.getValue()
                            .replace(',', '.')))
                            .setScale(2, RoundingMode.UP)
                            .doubleValue();
                    break;
                case intNum:
                    value = Long.valueOf(info.getValue());
                    break;
                case floatNum:
                    value = Double.valueOf(info.getValue().replace(',', '.'));
                    break;
            }
            additionalInfo.put(info.getCategoryInfo().getName(), value);
        }
    }
}
