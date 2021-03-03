package ee.hitsa.ois.report;

import static ee.hitsa.ois.util.TranslateUtil.name;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.protocol.ProtocolStudent;
import ee.hitsa.ois.domain.protocol.ProtocolStudentOccupation;
import ee.hitsa.ois.domain.student.StudentOccupationCertificate;
import ee.hitsa.ois.enums.HigherAssessment;
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.EnumUtil;
import ee.hitsa.ois.util.FinalProtocolUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;

public class FinalProtocolStudentReport {

    private final String name;
    private final String grade;
    private final String gradeName;
    private final String gradingSchemaGrade;
    private final List<String> occupations;
    private final List<String> partOccupations;
    private final String curriculumGrade;
    private final Boolean canSetCurriculumGrade;

    public FinalProtocolStudentReport(ProtocolStudent student, Boolean isVocational, Boolean letterGrades,
            Language lang) {
        name = PersonUtil.fullname(student.getStudent().getPerson());

        if (student.getGradingSchemaRow() != null) {
            grade = null;
            gradeName = name(student.getGrade(), lang);
            gradingSchemaGrade = Language.EN.equals(lang) ? student.getGradingSchemaRow().getGradeEn()
                    : student.getGradingSchemaRow().getGrade();
        } else if (student.getGrade() != null) {
            // can't use ReportUtil.gradeValue because higher grade should not be taken from name
            if (Boolean.FALSE.equals(isVocational)) {
                HigherAssessment assessment = EnumUtil.valueOf(HigherAssessment.class, student.getGrade());
                if (Boolean.TRUE.equals(assessment.getIsDistinctive())) {
                    grade = Boolean.TRUE.equals(letterGrades) ? student.getGrade().getValue2() : student.getGrade().getValue();
                } else {
                    grade = Language.EN.equals(lang) ?  student.getGrade().getExtraval2() :  student.getGrade().getExtraval1();
                }
            } else {
                grade = student.getGrade().getValue();
            }
            gradeName = name(student.getGrade(), lang);
            gradingSchemaGrade = null;
        } else {
            grade = null;
            gradeName = null;
            gradingSchemaGrade = null;
        }

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
            canSetCurriculumGrade = Boolean.FALSE;
        } else {
            partOccupations = null;
            curriculumGrade = student.getCurriculumGrade() != null ? name(student.getCurriculumGrade(), lang) : null;
            canSetCurriculumGrade = FinalProtocolUtil.studentCanSetCurriculumGrade(student);
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

    public String getGradingSchemaGrade() {
        return gradingSchemaGrade;
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

    public Boolean getCanSetCurriculumGrade() {
        return canSetCurriculumGrade;
    }
}
