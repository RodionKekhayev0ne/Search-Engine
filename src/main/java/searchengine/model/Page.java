package searchengine.model;


import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "page")
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
@Setter
public class Page {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Integer id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "site_id", referencedColumnName = "id")
    private SiteModel siteId;

    @Column(nullable = false, unique = true)
    private String path;

    @Column(nullable = false)
    private int code;



    @Column(nullable = false)
    private String title;

    @Column(length = 65535,nullable = false)
    private String content;

}
