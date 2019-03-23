package ppztw.AdvertBoard.Model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "adverts")
@Getter
@Setter
@NoArgsConstructor
public class Advert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Tag> tags;

    @Column(nullable = false)
    private String description;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ImgUrl> imgUrls;

    public Advert(String title, List<String> tags, String description, List<String> imgUrls) {
        this.title = title;
        this.tags = new ArrayList<>();
        this.imgUrls = new ArrayList<>();
        this.description = description;

        for (String tag : tags)
            this.tags.add(new Tag(tag));

        for (String imgUrl : imgUrls)
            this.imgUrls.add(new ImgUrl(imgUrl));
    }
}
