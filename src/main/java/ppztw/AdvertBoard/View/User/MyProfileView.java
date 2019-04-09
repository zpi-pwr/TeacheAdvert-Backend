package ppztw.AdvertBoard.View.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ppztw.AdvertBoard.Model.Profile;

@Getter
@Setter
@NoArgsConstructor
public class MyProfileView extends ProfileSummaryView {
    String firstName;

    String lastName;

    String telephoneNumber;

    String contactMail;

    public MyProfileView(Profile profile) {
        super(profile);
        this.firstName = profile.getFirstName();
        this.lastName = profile.getLastName();
        this.telephoneNumber = profile.getTelephoneNumber();
        this.contactMail = profile.getContactMail();
    }
}
