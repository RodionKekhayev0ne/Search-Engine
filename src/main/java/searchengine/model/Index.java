package searchengine.model;
import jakarta.persistence.*;

@Entity
@Table(name = "`index`")
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



    public Page getPageId() {
        return pageId;
    }

    public Lemma getLemmaId() {
        return lemmaId;
    }

    public Double getRank() {
        return rank;
    }

    public void setPageId(Page pageId) {
        this.pageId = pageId;
    }

    public void setLemmaId(Lemma lemmaId) {
        this.lemmaId = lemmaId;
    }

    public void setRank(Double rank) {
        this.rank = rank;
    }

}
