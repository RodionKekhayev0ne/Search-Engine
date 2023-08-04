package searchengine.model;


import jakarta.persistence.*;

@Entity
@Table(name = "lemma")
public class Lemma {

    public int getId() {
        return id;
    }

    public SiteModel getSiteId() {
        return siteId;
    }

    public String getLemma() {
        return lemma;
    }

    public int getFrequency() {
        return frequency;
    }

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


    public void setSiteId(SiteModel siteId) {
        this.siteId = siteId;
    }

    public void setLemma(String lemma) {
        this.lemma = lemma;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }
}
