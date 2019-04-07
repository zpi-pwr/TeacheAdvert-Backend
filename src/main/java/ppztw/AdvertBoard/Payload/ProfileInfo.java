package ppztw.AdvertBoard.Payload;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

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
}
