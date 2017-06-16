package ee.hitsa.ois.web.dto.report;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.util.HashMap;
import java.util.Map;

public class StudentStatisticsDto {

    private final Long id;
    private final String nameEt;
    private final String nameEn;
    private final Map<String, Long> result;

    public StudentStatisticsDto(Object record) {
        this.id = resultAsLong(record, 0);
        this.nameEt = resultAsString(record, 1);
        this.nameEn = resultAsString(record, 2);
        this.result = new HashMap<>();
    }

    public Long getId() {
        return id;
    }

    public String getNameEt() {
        return nameEt;
    }

    public String getNameEn() {
        return nameEn;
    }

    public Map<String, Long> getResult() {
        return result;
    }

    public long getTotal() {
        return result.values().stream().mapToLong(r -> r.longValue()).sum();
    }
}
