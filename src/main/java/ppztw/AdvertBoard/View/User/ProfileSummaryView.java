package ppztw.AdvertBoard.View.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ppztw.AdvertBoard.Model.Profile;

@Getter
@Setter
@NoArgsConstructor
public class ProfileSummaryView {
    Long id;

    String visibleName;

    public ProfileSummaryView(Profile profile) {
        this.id = profile.getId();
        this.visibleName = profile.getVisibleName();
    }
}
