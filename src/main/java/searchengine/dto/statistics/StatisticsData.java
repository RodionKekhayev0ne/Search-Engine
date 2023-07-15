package searchengine.dto.statistics;

import lombok.Data;
import searchengine.lemma.DetailedStatisticsItem;

import java.util.List;

@Data
public class StatisticsData {
    private TotalStatistics total;
    private List<DetailedStatisticsItem> detailed;
}
