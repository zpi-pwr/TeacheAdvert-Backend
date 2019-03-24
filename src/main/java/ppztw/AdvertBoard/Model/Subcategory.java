package ppztw.AdvertBoard.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "subcategories", uniqueConstraints = {
        @UniqueConstraint(columnNames = "subcategory_name")
})
public class Subcategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subcategory_id")
    private Long id;

    @Column(name = "subcategory_name", nullable = false)
    private String subcategoryName;

    private String description;

    @ManyToOne
    @JoinColumn(name = "category_name")
    @JsonBackReference
    private Category parentCategory;

    @OneToMany
    @JsonBackReference
    private List<Advert> adverts;

    public void changeCategory(Category newCategory) {
        if(this.parentCategory != null) {
            this.parentCategory.getSubCategories().remove(this);
        }

        newCategory.getSubCategories().add(this);
        this.parentCategory = newCategory;
    }

    public void addAdvert(Advert advert) {
        if (adverts == null)
            adverts = new ArrayList<>();
        adverts.add(advert);
    }

}
