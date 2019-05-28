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

    Long profileId;
    String profileName;
    String description;
    List<String> tags;
    Advert.Status status;
    Long conversationId;


    public AdvertDetailsView(Advert advert) {
        super(advert);
        this.profileId = advert.getUser().getProfile() != null ?
                advert.getUser().getProfile().getId() : null;
        this.profileName = advert.getUser().getProfile() != null ?
                advert.getUser().getProfile().getVisibleName() : null;
        this.description = advert.getDescription();
        this.status = advert.getStatus();
        this.tags = new ArrayList<>();
        this.conversationId = advert.getConversationId();
        for (Tag tag : advert.getTags())
            this.tags.add(tag.getName());

    }
}
