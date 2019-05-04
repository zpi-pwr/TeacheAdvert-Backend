package ppztw.AdvertBoard.Payload.Advert;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReportAdvertRequest {
    Long advertId;
    String comment;
}
