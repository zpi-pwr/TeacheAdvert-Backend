package ppztw.AdvertBoard.Model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "images")
@NoArgsConstructor
@Getter
@Setter
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @Column(nullable = false)
    private String filename;

    @JsonIgnore
    @Lob
    @Column(nullable = false)
    private byte[] pic;

    public Image(String filename, byte[] pic) {
        this.filename = filename;
        this.pic = pic;
    }
}
