package ppztw.AdvertBoard.Model;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class ProfileRatingKey implements Serializable {
    private Long ratingId;
    private Long ratedId;
}