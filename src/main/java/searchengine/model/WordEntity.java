package searchengine.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "words")
@NoArgsConstructor
@Getter
@Setter
public class WordEntity {

    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Id
    Long id;

    /**
     * nullable = поле не может быть null
     * unique = поле содержит уникальный значения
     * <p>
     * данные параметры задаются, если hibernate
     * создает таблицу, а не через миграции
     */
    @Column(nullable = false, unique = true)
    String word;

    String morphologyInfo;

    int count;
}