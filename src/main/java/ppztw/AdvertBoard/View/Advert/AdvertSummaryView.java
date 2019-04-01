package ppztw.AdvertBoard.View.Advert;

import lombok.Getter;
import lombok.Setter;
import ppztw.AdvertBoard.Model.Advert;

import java.time.LocalDate;

@Getter
@Setter
public class AdvertSummaryView {
    Long id;
    String title;
    String pic;
    LocalDate date;

    public AdvertSummaryView(Advert advert) {
        this.id = advert.getId();
        this.title = advert.getTitle();
        this.pic = advert.getBase64();
        this.date = advert.getDate();
    }
}
