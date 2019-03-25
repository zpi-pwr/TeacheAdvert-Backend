package ppztw.AdvertBoard.Payload.Advert;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImagePayload {
    private String base64;
    private String name;
    private String size;
    private String type;
}
