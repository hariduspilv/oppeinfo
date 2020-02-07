package ee.hitsa.ois.web.dto;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import ee.hitsa.ois.util.JpaQueryUtil;

public class ForeignStudentDto {
    
    private Long id;
    private String points;
    private BigInteger nominalStudyExtension;
    private Set<Long> apelApplicationIds = new HashSet<>();
    
    public ForeignStudentDto(Object r) {
        this.id = JpaQueryUtil.resultAsLong(r, 0);
        String studyExtension = JpaQueryUtil.resultAsString(r, 1);
        if (studyExtension != null) {
            Long studyExtentionNr = Long.valueOf(studyExtension);
            if (studyExtentionNr != null) {
                this.nominalStudyExtension = BigInteger.valueOf(studyExtentionNr.longValue());
            }
        }
        Long eap = JpaQueryUtil.resultAsLong(r, 2);
        if (eap != null) {
            this.points = String.valueOf(eap);
        }
        String nominalApelApplicationIds = JpaQueryUtil.resultAsString(r, 3);
        String creditApelApplicationIds = JpaQueryUtil.resultAsString(r, 4);
        apelApplicationIds.addAll(getIds(nominalApelApplicationIds));
        apelApplicationIds.addAll(getIds(creditApelApplicationIds));
    }
    
    private static List<Long> getIds(String nominalApelApplicationIds) {
        if (nominalApelApplicationIds == null) return new ArrayList<>();
        List<String> stringIds = Arrays.asList(nominalApelApplicationIds.split(","));
        return stringIds.stream().map(p -> Long.valueOf(p)).collect(Collectors.toList());
    }
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getPoints() {
        return points;
    }
    public void setPoints(String points) {
        this.points = points;
    }
    public BigInteger getNominalStudyExtension() {
        return nominalStudyExtension;
    }
    public void setNominalStudyExtension(BigInteger nominalStudyExtension) {
        this.nominalStudyExtension = nominalStudyExtension;
    }

    public Set<Long> getApelApplicationIds() {
        return apelApplicationIds;
    }

    public void setApelApplicationIds(Set<Long> apelApplicationIds) {
        this.apelApplicationIds = apelApplicationIds;
    }

}
