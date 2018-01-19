package ee.hitsa.ois.report;

import java.util.List;
import java.util.stream.Collectors;

import ee.hitsa.ois.domain.statecurriculum.StateCurriculumOccupation;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.util.ClassifierUtil;

public class StateCurriculumOccupationReport {

    private final String name;
    private final List<String> partOccupations;
    private final List<String> spetsOccupations;

    public StateCurriculumOccupationReport(StateCurriculumOccupation stateCurriculumOccupation) {
        name = stateCurriculumOccupation.getOccupation().getNameEt();
        partOccupations = stateCurriculumOccupation.getOccupation().getChildConnects().stream()
                .filter(child -> ClassifierUtil.mainClassCodeEquals(MainClassCode.OSAKUTSE, child.getClassifier()))
                .map(child -> child.getClassifier().getNameEt()).collect(Collectors.toList());
        spetsOccupations = stateCurriculumOccupation.getOccupation().getChildConnects().stream()
                .filter(child -> ClassifierUtil.mainClassCodeEquals(MainClassCode.SPETSKUTSE, child.getClassifier()))
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
