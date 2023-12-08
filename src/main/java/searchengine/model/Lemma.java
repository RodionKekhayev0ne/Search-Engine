package searchengine.model;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "lemma")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Lemma {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "site_id", referencedColumnName = "id")
    private SiteModel siteId;

    @Column(nullable = false)
    private String lemma;

    @Column(nullable = false)
    private int frequency;

}
