package ppztw.AdvertBoard.Model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tags")
@Getter
@Setter
public class Tag {
    @Id
    private String name;

    Tag(String name) {
        this.name = name;
    }
}
