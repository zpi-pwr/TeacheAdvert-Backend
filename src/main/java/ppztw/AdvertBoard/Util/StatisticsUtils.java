package ppztw.AdvertBoard.Util;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

public class StatisticsUtils {

    private Map<Long, Double> normalizeIntoDistribution(Map<Long, Double> map) {

        Map<Long, Double> expMap = new HashMap<>();
        double expSum = 0;

        for (Map.Entry<Long, Double> entry : map.entrySet()) {
            double exp = Math.exp(entry.getValue());
            expMap.put(entry.getKey(), exp);
            expSum += exp;
        }
        Map<Long, Double> distribution = new HashMap<>();
        for (Map.Entry<Long, Double> entry : expMap.entrySet())
            distribution.put(entry.getKey(), entry.getValue() / expSum);

        return distribution
                .entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(
                        toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                                LinkedHashMap::new));
    }
}
