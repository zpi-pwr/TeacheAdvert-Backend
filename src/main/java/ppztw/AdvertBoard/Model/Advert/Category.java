package ppztw.AdvertBoard.Model.Advert;

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

    @OneToMany(cascade = CascadeType.ALL)
    private List<CategoryInfo> infoList;

    public void addAdvert(Advert advert) {
        adverts.add(advert);
    }

    public List<Advert> getAdverts() {
        List<Advert> advertList = new ArrayList<>();
        advertList.addAll(adverts);
        for (Category subcategory : subcategories)
            advertList.addAll(subcategory.getAdverts());
        return advertList;
    }

    public List<CategoryInfo> getInfoList() {
        List<CategoryInfo> infos = new ArrayList<>();
        if (infoList != null)
            infos.addAll(infoList);
        if (parentCategory != null)
            infos.addAll(parentCategory.getInfoList());
        return infos;
    }
}