package ppztw.AdvertBoard.Model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class AdvertInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private CategoryInfo categoryInfo;

    private String value;

    public AdvertInfo(CategoryInfo categoryInfo, String value) {
        this.categoryInfo = categoryInfo;
        this.value = value;
    }

}
