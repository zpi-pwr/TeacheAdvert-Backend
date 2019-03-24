package ppztw.AdvertBoard.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
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

    @OneToMany(cascade = CascadeType.ALL)
    private List<Tag> tags;

    @Column(nullable = false)
    private String description;

    @OneToMany(cascade = CascadeType.ALL)
    private List<ImgUrl> imgUrls;

    @JsonManagedReference
    @ManyToOne(cascade = CascadeType.ALL)
    private Subcategory subcategory;


    @Column(nullable = false)
    private LocalDate date;

    @Column
    private Status status;

    @ManyToOne
    @JsonBackReference
    private User user;


    public Advert(String title, List<String> tags, String description, List<String> imgUrls,
                  Subcategory subcategory, User user) {
        this.title = title;
        this.tags = new ArrayList<>();
        this.imgUrls = new ArrayList<>();
        this.description = description;

        for (String tag : tags)
            this.tags.add(new Tag(tag));

        for (String imgUrl : imgUrls)
            this.imgUrls.add(new ImgUrl(imgUrl));

        this.subcategory = subcategory;
        this.date = LocalDate.now();
        this.user = user;

        status = Status.OK;
    }

    public enum Status {OK, EDITED, ARCHIVED, BANNED}
}