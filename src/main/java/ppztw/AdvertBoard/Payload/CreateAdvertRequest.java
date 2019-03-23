package ppztw.AdvertBoard.Payload;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
public class CreateAdvertRequest {

    @NotBlank
    private String title;

    private List<String> tags;

    @NotBlank
    private String description;

    private List<String> imgUrls;
}
