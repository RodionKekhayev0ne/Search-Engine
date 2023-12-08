package searchengine.services;

import lombok.AllArgsConstructor;
import searchengine.dto.statistics.StatisticsResponse;

public interface StatisticsService {
    StatisticsResponse getStatistics();
}
