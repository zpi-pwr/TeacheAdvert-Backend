package ppztw.AdvertBoard.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "categories", uniqueConstraints = {
        @UniqueConstraint(columnNames = "category_name")
})
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @Column(name = "category_name", nullable = false)
    private String categoryName;

    private String description;

    @OneToMany(mappedBy="parentCategory")
    @JsonManagedReference
    private List<Subcategory> subCategories;

    public void addSubcategory(Subcategory subCategory) {
        subCategories.add(subCategory);
    }

    public void removeSubcategory(Subcategory subCategory) {
        subCategories.remove(subCategory);
    }

    @JsonIgnore
    public List<Advert> getAdverts() {
        List<Advert> adverts = new ArrayList<>();
        for (Subcategory subcategory : subCategories)
            adverts.addAll(subcategory.getAdverts());
        return adverts;
    }
}