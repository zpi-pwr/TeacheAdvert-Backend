package ppztw.AdvertBoard.View.User;

import lombok.Getter;
import lombok.Setter;
import ppztw.AdvertBoard.Model.Advert;
import ppztw.AdvertBoard.Model.AuthProvider;
import ppztw.AdvertBoard.Model.Profile;
import ppztw.AdvertBoard.Model.Role;
import ppztw.AdvertBoard.Model.User;
import ppztw.AdvertBoard.View.Advert.AdvertSummaryView;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UserView {
    private String name;
    private String email;
    private Boolean emailVerified;
    private List<AdvertSummaryView> adverts;
    private AuthProvider provider;
    private MyProfileView profileView;
    private Role role;

    public UserView(User user, Double rating, Integer ratingCount) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.emailVerified = user.getEmailVerified();
        this.adverts = new ArrayList<>();
        for (Advert advert : user.getAdverts())
            adverts.add(new AdvertSummaryView(advert));
        this.provider = user.getProvider();
        Profile profile = user.getProfile();
        this.profileView = profile == null ? null : new MyProfileView(user, rating, ratingCount);
        this.role = user.getRole();
    }
}
