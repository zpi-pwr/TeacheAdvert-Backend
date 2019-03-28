package ppztw.AdvertBoard.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @Column(name = "category_name", nullable = false)
    private String categoryName;

    private String description;

    @ManyToOne(cascade = CascadeType.ALL)
    @Nullable
    @JsonBackReference
    private Category parentCategory;

    @OneToMany(mappedBy="parentCategory")
    @JsonManagedReference
    private List<Category> subcategories;

    @OneToMany
    private List<Advert> adverts;

    public void addAdvert(Advert advert) {
        adverts.add(advert);
    }

    public List<Long> getAdvertsId() {
        List<Long> idList = new ArrayList<>();
        for (Advert advert : adverts)
            idList.add(advert.getId());
        for (Category subcategory : subcategories)
            idList.addAll(subcategory.getAdvertsId());
        return idList;
    }

}