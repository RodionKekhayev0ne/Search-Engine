package searchengine.model;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import jakarta.persistence.*;

import searchengine.Application;


import java.util.*;

@Entity
@Table(name = "site")
public class SiteModel {

    // id,status, status time, status error, url, name

    public Integer getId() {
        return id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


//    @OneToMany(mappedBy = "site",cascade = CascadeType.ALL,orphanRemoval = true)
//    private Set<Page> pageSet = new HashSet<>();


    @Column(nullable = false)
    private Enum<Application.Status> status;

    @Column(nullable = false,name = "status_time")
    private Date statusTime;

    @Column(nullable = false, name = "status_error")
    private String statusError;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private String name;


    @Column(nullable = false, name = "page_count")
    private Integer pageCount;


    @Column(nullable = false, name = "lemma_count")
    private Integer lemmaCount;
    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public Integer getLemmaCount() {
        return lemmaCount;
    }

    public void setLemmaCount(Integer lemmaCount) {
        this.lemmaCount = lemmaCount;
    }


    public Enum<Application.Status> getStatus() {
        return status;
    }

    public void setStatus(Enum<Application.Status> status) {
        this.status = status;
    }

    public Date getStatusTime() {
        return statusTime;
    }

    public void setStatusTime(Date statusTime) {
        this.statusTime = statusTime;
    }

    public String getStatusError() {
        return statusError;
    }

    public void setStatusError(String statusError) {
        this.statusError = statusError;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Site{" +
                "id=" + id +
                ", status=" + status +
                ", statusTime=" + statusTime +
                ", statusError='" + statusError + '\'' +
                ", url='" + url + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

}
