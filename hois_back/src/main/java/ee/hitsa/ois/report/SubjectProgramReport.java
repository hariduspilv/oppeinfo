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
                    String regex = "^\\d+(\\-\\d+)?$";
                    if (o1.getWeekNr() == o2.getWeekNr()) {
                        return 0;
                    }
                    if (o1.getWeekNr() == null || o2.getWeekNr() == null) {
                        return o1.getWeekNr() == null ? 1 : -1;
                    }
                    
                    boolean isValid1 = o1.getWeekNr().matches(regex);
                    boolean isValid2 = o2.getWeekNr().matches(regex);
                    
                    if (isValid1 && isValid2) {
                        String[] splitted1 = o1.getWeekNr().split("-");
                        String[] splitted2 = o2.getWeekNr().split("-");
                        Long value1 = null;
                        Long value2 = null;
                        try {
                            value1 = Long.valueOf(splitted1[0]);
                        } catch (@SuppressWarnings("unused") NumberFormatException ex) { }
                        try {
                            value2 = Long.valueOf(splitted2[0]);
                        } catch (@SuppressWarnings("unused") NumberFormatException ex) { }
                        
                        if (value1 != null && value2 != null) {
                            if (value1.equals(value2) && (splitted1.length > 1 || splitted2.length > 1)) {
                                if (splitted1.length > 1 && splitted2.length > 1) {
                                    value1 = null;
                                    value2 = null;
                                    try {
                                        value1 = Long.valueOf(splitted1[1]);
                                    } catch (@SuppressWarnings("unused") NumberFormatException ex) { }
                                    try {
                                        value2 = Long.valueOf(splitted2[1]);
                                    } catch (@SuppressWarnings("unused") NumberFormatException ex) { }
                                    return value1 == value2 ? 0 : value1 == null ? 1 : value2 == null ? -1 : value1.compareTo(value2);
                                }
                                return splitted1.length > 1 ? 1 : -1;
                            }
                            return value1.compareTo(value2);
                        }
                        return value1 == value2 ? 0 : value1 == null ? 1 : -1;
                    } else if (isValid1 || isValid2) {
                        return isValid1 ? -1 : 1;
                    }
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
