package ppztw.AdvertBoard.Model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "adverts")
@Getter
@Setter
public class Advert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @OneToMany
    @Column
    private List<String> tags;

    @Column(nullable = false)
    private String description;

    @OneToMany
    @Column
    private List<String> imgUrls;

    public Advert(String title, List<String> tags, String description, List<String> imgUrls) {
        this.title = title;
        this.tags = tags;
        this.description = description;
        this.imgUrls = imgUrls;
    }
}
