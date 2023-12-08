package searchengine.model;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "`index`")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Index {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "page_id", referencedColumnName = "id")
    private Page pageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lemma_id", referencedColumnName = "id")
    private Lemma lemmaId;

    @Column(nullable = false,name = "`rank`")
    private Double rank;
}
