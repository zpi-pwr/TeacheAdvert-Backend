package ppztw.AdvertBoard.Model.Advert;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ppztw.AdvertBoard.Model.User;

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

    @Lob
    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    private Image image;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    private Category subcategory;


    @Column(nullable = false)
    private LocalDate date;

    @Column
    private Status status;

    @ManyToOne
    @JsonBackReference
    private User user;

    @OneToMany(cascade = CascadeType.ALL)
    private List<AdvertInfo> additionalInfo;

    @JsonGetter
    public String getBase64() {
        if (image != null)
            return "data:image/png:base64," + image.getBase64();
        else return "";
    }


    public Advert(String title, List<Tag> tags, String description, Image image,
                  Category subcategory, User user, List<AdvertInfo> additionalInfo) {
        this.title = title;
        this.tags = new ArrayList<>();
        this.description = description;
        this.image = image;
        this.tags = tags;
        this.subcategory = subcategory;
        this.date = LocalDate.now();
        this.user = user;
        this.additionalInfo = additionalInfo;

        status = Status.OK;
    }

    public enum Status {OK, EDITED, ARCHIVED, BANNED}
}
