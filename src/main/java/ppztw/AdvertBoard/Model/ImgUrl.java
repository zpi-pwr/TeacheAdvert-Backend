package ppztw.AdvertBoard.Model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "imgUrls")
@Getter
@Setter
public class ImgUrl {
    @Id
    private String name;

    ImgUrl(String name) {
        this.name = name;
    }

}
