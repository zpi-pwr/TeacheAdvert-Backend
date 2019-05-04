package ppztw.AdvertBoard.Model;

import lombok.Getter;
import lombok.Setter;
import ppztw.AdvertBoard.Model.Advert.Advert;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @OneToOne
    User reportingUser;

    @OneToOne
    Advert reportedAdvert;

    String comment;

    public Report(User reportingUser, Advert reportedAdvert, String comment) {
        this.reportingUser = reportingUser;
        this.reportedAdvert = reportedAdvert;
        this.comment = comment;
    }


}
