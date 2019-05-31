package ee.hitsa.ois.report;

import static ee.hitsa.ois.util.TranslateUtil.name;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.protocol.ProtocolStudent;
import ee.hitsa.ois.domain.protocol.ProtocolStudentOccupation;
import ee.hitsa.ois.domain.student.StudentOccupationCertificate;
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;

public class FinalProtocolStudentReport {

    private final String name;
    private final String grade;
    private final String gradeName;
    private final List<String> occupations;
    private final List<String> partOccupations;
    private final String curriculumGrade;

    public FinalProtocolStudentReport(ProtocolStudent student, Boolean isVocational, Boolean letterGrades,
            Language lang) {
        name = PersonUtil.fullname(student.getStudent().getPerson());

        if (student.getGrade() != null) {
            grade = Boolean.FALSE.equals(isVocational) && Boolean.TRUE.equals(letterGrades)
                    ? student.getGrade().getValue2()
                    : student.getGrade().getValue();
        } else {
            grade = null;
        }

        gradeName = student.getGrade() != null ? name(student.getGrade(), lang) : null;

        List<ProtocolStudentOccupation> nonPartOccupations = StreamUtil.toFilteredList(
                pso -> pso.getOccupation() != null && pso.getPartOccupation() == null,
                student.getProtocolStudentOccupations());
        occupations = new ArrayList<>();
        for (ProtocolStudentOccupation pso : nonPartOccupations) {
            String occupation = ClassifierUtil.getNullableNameEt(pso.getOccupation());
            StudentOccupationCertificate certificate = pso.getStudentOccupationCertificate();
            if (certificate != null) {
                Classifier speciality = certificate.getSpeciality();
                if (speciality != null) {
                    occupation += " (" + ClassifierUtil.getNullableNameEt(speciality) + ")";
                }
            }
            occupations.add(occupation);
        }

        if (Boolean.TRUE.equals(isVocational)) {
            partOccupations = StreamUtil.nullSafeList(student.getProtocolStudentOccupations()).stream()
                    .filter(pso -> pso.getPartOccupation() != null)
                    .map(pso -> ClassifierUtil.getNullableNameEt(pso.getPartOccupation())).collect(Collectors.toList());
            curriculumGrade = null;
        } else {
            partOccupations = null;
            curriculumGrade = student.getCurriculumGrade() != null ? name(student.getCurriculumGrade(), lang) : null;
        }
    }

    public String getName() {
        return name;
    }

    public String getGrade() {
        return grade;
    }

    public String getGradeName() {
        return gradeName;
    }

    public List<String> getOccupations() {
        return occupations;
    }

    public List<String> getPartOccupations() {
        return partOccupations;
    }

    public String getCurriculumGrade() {
        return curriculumGrade;
    }

}
