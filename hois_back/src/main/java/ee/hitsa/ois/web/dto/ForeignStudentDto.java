package ee.hitsa.ois.web.dto;

import java.math.BigInteger;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import ee.hitsa.ois.util.JpaQueryUtil;

public class ForeignStudentDto {
    
    private Long id;
    private String points;
    private BigInteger nominalStudyExtension;
    private Map<Long, String> apelApplicationIdsAndNominal = new HashMap<>();
    
    public ForeignStudentDto(Object r) {
        this.id = JpaQueryUtil.resultAsLong(r, 0);
        String studyExtensionEhisSent = JpaQueryUtil.resultAsString(r, 1);
        String studyExtension = JpaQueryUtil.resultAsString(r, 2);
        this.nominalStudyExtension = new BigInteger(studyExtensionEhisSent.equals("0") ? studyExtension : studyExtensionEhisSent);
        Long eap = JpaQueryUtil.resultAsLong(r, 3);
        if (eap != null) {
            this.points = String.valueOf(eap);
        }
        apelApplicationIdsAndNominal.putAll(getApelApplicationEntries(JpaQueryUtil.resultAsStringList(r, 4, ","), JpaQueryUtil.resultAsStringList(r, 5, ",")));
    }
    
    private static Map<Long, String> getApelApplicationEntries(List<String> apelApplicationIds, List<String> apelApplicationNominal) {
        if (apelApplicationIds == null || apelApplicationNominal == null) { 
            return Collections.emptyMap(); 
        }
        
        return IntStream.range(0, apelApplicationIds.size())
                .boxed()
                .collect(Collectors.toMap(i -> Long.valueOf(apelApplicationIds.get(i.intValue())), i -> apelApplicationNominal.get(i.intValue()), (o, n) -> o));
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

    public Map<Long, String> getApelApplicationIdsAndNominal() {
        return apelApplicationIdsAndNominal;
    }

    public void setApelApplicationIdsAndNominal(Map<Long, String> apelApplicationIdsAndNominal) {
        this.apelApplicationIdsAndNominal = apelApplicationIdsAndNominal;
    }

}
