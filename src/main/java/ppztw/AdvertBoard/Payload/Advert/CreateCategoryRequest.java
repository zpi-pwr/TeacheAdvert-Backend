package ppztw.AdvertBoard.Payload.Advert;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;
import ppztw.AdvertBoard.Model.Advert.InfoType;

import javax.validation.constraints.NotBlank;
import java.util.Map;

public class CreateCategoryRequest {
    @Getter(value = AccessLevel.PUBLIC)
    @Setter(value = AccessLevel.PUBLIC)
    @NotBlank
    private String categoryName;

    @Getter(value = AccessLevel.PUBLIC)
    @Setter(value = AccessLevel.PUBLIC)
    private String description;

    @Getter
    @Nullable
    private Long parentId;

    @Getter
    @Nullable
    private Map<String, InfoType> infos;

}
