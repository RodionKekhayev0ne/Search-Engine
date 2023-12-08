package searchengine.model;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import jakarta.persistence.*;

import lombok.*;
import searchengine.Application;


import java.util.*;

@Entity
@Table(name = "site")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class SiteModel {
    public Integer getId() {
        return id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Integer id;


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
