package ppztw.AdvertBoard.Payload.Advert;

import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
public class EditAdvertRequest {

    @NotBlank
    private Long id;

    @Nullable
    private String title;

    @Nullable
    private List<String> tags;

    @Nullable
    private String description;

    @Nullable
    private CommonsMultipartFile image;

}
