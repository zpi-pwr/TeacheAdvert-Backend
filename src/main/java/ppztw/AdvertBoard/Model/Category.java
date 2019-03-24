package ppztw.AdvertBoard.Model;

import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

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
}