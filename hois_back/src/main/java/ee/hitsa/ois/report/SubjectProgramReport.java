package ee.hitsa.ois.report;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.domain.subject.Subject;
import ee.hitsa.ois.domain.subject.subjectprogram.SubjectProgram;
import ee.hitsa.ois.domain.subject.subjectprogram.SubjectProgramStudyContent;
import ee.hitsa.ois.domain.teacher.Teacher;
import ee.hitsa.ois.enums.StudyContentType;
import ee.hitsa.ois.enums.SubjectAssessment;
import ee.hitsa.ois.util.ClassifierUtil;

public class SubjectProgramReport {

    public static final String TEMPLATE_NAME = "subjectprogram.xhtml";
    
    private final Subject subject;
    private final Teacher teacher;
    private final SubjectProgram program;
    private final Set<String> assessmentMethodKeys;
    private final Map<String, String> assessmentMethods;
    private final List<SubjectProgramStudyContent> studyContents;
    private final StudyPeriod period;
    
    public SubjectProgramReport(SubjectProgram program) {
        this.program = program;
        this.period = program.getSubjectStudyPeriodTeacher().getSubjectStudyPeriod().getStudyPeriod();
        this.subject = program.getSubjectStudyPeriodTeacher().getSubjectStudyPeriod().getSubject();
        this.teacher = program.getSubjectStudyPeriodTeacher().getTeacher();
        assessmentMethodKeys = new LinkedHashSet<>();
        assessmentMethods = new HashMap<>();
        studyContents = new LinkedList<>();
        
        Classifier assessment = subject.getAssessment();
        if (ClassifierUtil.equals(SubjectAssessment.HINDAMISVIIS_A, assessment)) {
            addAssessmentMethos("A", program.getPassDescription());
            addAssessmentMethos("MA", program.getNpassDescription());
        } else if (ClassifierUtil.equals(SubjectAssessment.HINDAMISVIIS_E, assessment)) {
            addAssessmentMethos("0", program.getGrade0Description());
            addAssessmentMethos("1", program.getGrade1Description());
            addAssessmentMethos("2", program.getGrade2Description());
            addAssessmentMethos("3", program.getGrade3Description());
            addAssessmentMethos("4", program.getGrade4Description());
            addAssessmentMethos("5", program.getGrade5Description());
        } else if (ClassifierUtil.equals(SubjectAssessment.HINDAMISVIIS_H, assessment)) {
            addAssessmentMethos("3", program.getGrade3Description());
            addAssessmentMethos("4", program.getGrade4Description());
            addAssessmentMethos("5", program.getGrade5Description());
        }
        program.getStudyContents().forEach(sc -> {
            studyContents.add(sc);
        });
        studyContents.sort(new Comparator<SubjectProgramStudyContent>() {

            @Override
            public int compare(SubjectProgramStudyContent o1, SubjectProgramStudyContent o2) {
                if (ClassifierUtil.equals(StudyContentType.OPPETOOSISU_N, program.getStudyContentType())) {
                    return o1.getWeekNr().compareTo(o2.getWeekNr());
                }
                return o1.getStudyDt().compareTo(o2.getStudyDt());
            }
        });
    }
    
    private void addAssessmentMethos(String key, String value) {
        assessmentMethodKeys.add(key);
        assessmentMethods.put(key, value);
    }

    public SubjectProgram getProgram() {
        return program;
    }

    public Subject getSubject() {
        return subject;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public Map<String, String> getAssessmentMethods() {
        return assessmentMethods;
    }

    public Set<String> getAssessmentMethodKeys() {
        return assessmentMethodKeys;
    }

    public List<SubjectProgramStudyContent> getStudyContents() {
        return studyContents;
    }

    public StudyPeriod getPeriod() {
        return period;
    }
}
