package ppztw.AdvertBoard.Model;


import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "images")
@NoArgsConstructor
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String filename;

    @Lob
    private byte[] pic;

    public Image(String filename, byte[] pic) {
        this.filename = filename;
        this.pic = pic;
    }
}
