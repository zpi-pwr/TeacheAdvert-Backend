package ppztw.AdvertBoard.Payload;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class ProfileInfo {
    @Nullable
    String visibleName;

    @Nullable
    String firstName;

    @Nullable
    String lastName;

    @Nullable
    String telephoneNumber;

    @Nullable
    String contactMail;

    @Nullable
    Map<Long, Double> categoryEntries;
}
