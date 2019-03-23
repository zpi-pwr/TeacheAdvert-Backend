package ppztw.AdvertBoard.Model;

import java.util.List;

import javax.persistence.*;

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
    private Long id;

    @Getter(value = AccessLevel.PUBLIC)
    @Setter(value = AccessLevel.PUBLIC)
    @Column(nullable = false)
    private String category_name;

    @Getter(value = AccessLevel.PUBLIC)
    @Setter(value = AccessLevel.PUBLIC)
    private String description;

    @Getter(value = AccessLevel.PUBLIC)
    @Setter(value = AccessLevel.PUBLIC)
    @OneToMany(mappedBy="parentCategory")
    private List<Subcategory> subCategories;

    public void addSubcategory(Subcategory subCategory) {
        subCategories.add(subCategory);
    }

    public void removeSubcategory(Subcategory subCategory) {
        subCategories.remove(subCategory);
    }
}