package ppztw.AdvertBoard.Model.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ppztw.AdvertBoard.Model.User.User;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @OneToOne
    User user;

    String visibleName;

    String firstName;

    String lastName;

    String telephoneNumber;

    String contactMail;


}
