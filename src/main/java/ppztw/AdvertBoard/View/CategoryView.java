package ppztw.AdvertBoard.View;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CategoryView {
    long id;
    long name;
    List<CategoryView> subcategories;
}
