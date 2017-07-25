package ee.hitsa.ois.report;

import java.util.List;
import java.util.stream.Collectors;

import ee.hitsa.ois.domain.statecurriculum.StateCurriculumOccupation;
import ee.hitsa.ois.enums.MainClassCode;

public class StateCurriculumOccupationReport {
    
    private final String name;
    private final List<String> partOccupations;
    private final List<String> spetsOccupations;

    public StateCurriculumOccupationReport(StateCurriculumOccupation stateCurriculumOccupation) {
        name = stateCurriculumOccupation.getOccupation().getNameEt();
        partOccupations = stateCurriculumOccupation.getOccupation().getChildConnects().stream()
                .filter(child -> MainClassCode.OSAKUTSE.name().equals(child.getClassifier().getMainClassCode()))
                .map(child -> child.getClassifier().getNameEt()).collect(Collectors.toList());
        spetsOccupations = stateCurriculumOccupation.getOccupation().getChildConnects().stream()
                .filter(child -> MainClassCode.SPETSKUTSE.name().equals(child.getClassifier().getMainClassCode()))
                .map(child -> child.getClassifier().getNameEt()).collect(Collectors.toList());
    }

    public String getName() {
        return name;
    }
    public List<String> getPartOccupations() {
        return partOccupations;
    }
    public List<String> getSpetsOccupations() {
        return spetsOccupations;
    }
}
