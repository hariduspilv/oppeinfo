package ee.hitsa.ois.web.dto.report;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import ee.hitsa.ois.util.Translatable;

public class StudentStatisticsDto implements Translatable {

    private final Long id;
    private final String nameEt;
    private final String nameEn;
    private final String merCode;
    private final Map<String, Long> result;
    private final Set<String> resultFilter;

    public StudentStatisticsDto(Object record) {
        this.id = resultAsLong(record, 0);
        this.nameEt = resultAsString(record, 1);
        this.nameEn = resultAsString(record, 2);
        this.merCode = resultAsString(record, 3);
        this.result = new HashMap<>();
        this.resultFilter = new HashSet<>();
    }

    public Long getId() {
        return id;
    }

    @Override
    public String getNameEt() {
        return nameEt;
    }

    public Set<String> getResultFilter() {
        return resultFilter;
    }

    public String getMerCode() {
        return merCode;
    }

    @Override
    public String getNameEn() {
        return nameEn;
    }

    public Map<String, Long> getResult() {
        return result;
    }

    public long getTotal() {
        return result.entrySet().stream().filter(r -> !resultFilter.contains(r.getKey())).mapToLong(r -> r.getValue().longValue()).sum();
    }

}
