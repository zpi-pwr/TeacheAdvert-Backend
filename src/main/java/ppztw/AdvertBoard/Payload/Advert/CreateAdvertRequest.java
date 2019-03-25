package ppztw.AdvertBoard.Payload.Advert;


import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
public class CreateAdvertRequest {

    @NotBlank
    private String title;

    @Nullable
    private List<String> tags;

    @NotBlank
    private String description;

    @NotBlank
    private String subcategory;
}
