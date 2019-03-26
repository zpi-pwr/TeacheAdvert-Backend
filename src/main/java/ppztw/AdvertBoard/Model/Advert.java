package ppztw.AdvertBoard.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Tag> tags;

    @Column(nullable = false)
    private String description;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    private Image image;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    private Subcategory subcategory;


    @Column(nullable = false)
    private LocalDate date;

    @Column
    private Status status;

    @ManyToOne
    @JsonBackReference
    private User user;

    @JsonGetter
    public String getBase64() {
        return "data:image/png:base64," + image.getBase64();
    }


    public Advert(String title, List<Tag> tags, String description, Image image,
                  Subcategory subcategory, User user) {
        this.title = title;
        this.tags = new ArrayList<>();
        this.description = description;
        this.image = image;
        this.tags = tags;
        this.subcategory = subcategory;
        this.date = LocalDate.now();
        this.user = user;

        status = Status.OK;
    }

    public enum Status {OK, EDITED, ARCHIVED, BANNED}
}
