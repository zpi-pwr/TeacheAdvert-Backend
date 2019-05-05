package ppztw.AdvertBoard.Model.User;

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

    @Enumerated(EnumType.STRING)
    CaseStatus caseStatus = CaseStatus.unsolved;

    public Report(User reportingUser, Advert reportedAdvert, String comment) {
        this.reportingUser = reportingUser;
        this.reportedAdvert = reportedAdvert;
        this.comment = comment;
    }


}
