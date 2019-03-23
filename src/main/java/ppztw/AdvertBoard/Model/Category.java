package ppztw.AdvertBoard.Model;

import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "categories", uniqueConstraints = {
        @UniqueConstraint(columnNames = "category_name")
})
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @Getter(value = AccessLevel.PUBLIC)
    @Setter(value = AccessLevel.PUBLIC)
    @Column(name = "category_name", nullable = false)
    private String categoryName;

    @Getter(value = AccessLevel.PUBLIC)
    @Setter(value = AccessLevel.PUBLIC)
    private String description;

    @Getter(value = AccessLevel.PUBLIC)
    @Setter(value = AccessLevel.PUBLIC)
    @OneToMany(mappedBy="parentCategory")
    @JsonManagedReference
    private List<Subcategory> subCategories;

    public void addSubcategory(Subcategory subCategory) {
        subCategories.add(subCategory);
    }

    public void removeSubcategory(Subcategory subCategory) {
        subCategories.remove(subCategory);
    }
}