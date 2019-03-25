package ppztw.AdvertBoard.Model;


import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.tomcat.util.codec.binary.Base64;

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

    @JsonAnyGetter
    private String getBase64() {
        return Base64.encodeBase64String(pic);
    }
}
