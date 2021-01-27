package ee.hitsa.ois.report;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.domain.subject.Subject;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriod;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriodTeacher;
import ee.hitsa.ois.domain.subject.subjectprogram.SubjectProgram;
import ee.hitsa.ois.domain.subject.subjectprogram.SubjectProgramStudyContent;
import ee.hitsa.ois.domain.subject.subjectprogram.SubjectProgramTeacher;
import ee.hitsa.ois.domain.teacher.Teacher;
import ee.hitsa.ois.enums.HigherAssessment;
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.enums.StudyContentType;
import ee.hitsa.ois.enums.SubjectAssessment;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.ClassifierUtil.ClassifierCache;
import ee.hitsa.ois.util.EnumUtil;
import ee.hitsa.ois.util.PersonUtil;

public class SubjectProgramReport {

    public static final String TEMPLATE_NAME = "subjectprogram.xhtml";
    
    private final Language lang;
    
    private final Subject subject;
    private final List<Teacher> teachers;
    private final SubjectProgram program;
    private final Set<String> assessmentMethodKeys;
    private final Map<String, String> assessmentMethods;
    private final List<SubjectProgramStudyContent> studyContents;
    private final StudyPeriod period;
    
    public SubjectProgramReport(SubjectProgram program, ClassifierCache classifierCache) {
        this(program, classifierCache, Language.ET);
    }
    
    public SubjectProgramReport(SubjectProgram program, ClassifierCache classifierCache, Language lang) {
        this.program = program;
        this.lang = lang;
        period = program.getSubjectStudyPeriod().getStudyPeriod();
        subject = program.getSubjectStudyPeriod().getSubject();
        teachers = program.getTeachers().stream()
                .map(SubjectProgramTeacher::getSubjectStudyPeriodTeacher)
                .map(SubjectStudyPeriodTeacher::getTeacher)
                .collect(Collectors.toList());
        assessmentMethodKeys = new LinkedHashSet<>();
        assessmentMethods = new HashMap<>();
        studyContents = new LinkedList<>();
        
        Classifier assessment = subject.getAssessment();
        Boolean isLetterGrade = subject.getSchool().getIsLetterGrade();
        
        Map<HigherAssessment, String> mappedGrades = classifierCache.getAll(MainClassCode.KORGHINDAMINE).stream()
                .filter(cl -> EnumUtil.valueOf(HigherAssessment.class, cl) != null)
                .collect(Collectors.toMap(cl -> EnumUtil.valueOf(HigherAssessment.class, cl), cl -> {
                    return ReportUtil.gradeValue(cl, isLetterGrade, lang);
                }, (o, n) -> o));
        
        if (ClassifierUtil.equals(SubjectAssessment.HINDAMISVIIS_A, assessment)) {
            addAssessmentMethos(mappedGrades.get(HigherAssessment.KORGHINDAMINE_A), program.getPassDescription());
            addAssessmentMethos(mappedGrades.get(HigherAssessment.KORGHINDAMINE_M), program.getNpassDescription());
        } else if (ClassifierUtil.equals(SubjectAssessment.HINDAMISVIIS_E, assessment)) {
            addAssessmentMethos(mappedGrades.get(HigherAssessment.KORGHINDAMINE_0), program.getGrade0Description());
            addAssessmentMethos(mappedGrades.get(HigherAssessment.KORGHINDAMINE_1), program.getGrade1Description());
            addAssessmentMethos(mappedGrades.get(HigherAssessment.KORGHINDAMINE_2), program.getGrade2Description());
            addAssessmentMethos(mappedGrades.get(HigherAssessment.KORGHINDAMINE_3), program.getGrade3Description());
            addAssessmentMethos(mappedGrades.get(HigherAssessment.KORGHINDAMINE_4), program.getGrade4Description());
            addAssessmentMethos(mappedGrades.get(HigherAssessment.KORGHINDAMINE_5), program.getGrade5Description());
        } else if (ClassifierUtil.equals(SubjectAssessment.HINDAMISVIIS_H, assessment)) {
            addAssessmentMethos(mappedGrades.get(HigherAssessment.KORGHINDAMINE_3), program.getGrade3Description());
            addAssessmentMethos(mappedGrades.get(HigherAssessment.KORGHINDAMINE_4), program.getGrade4Description());
            addAssessmentMethos(mappedGrades.get(HigherAssessment.KORGHINDAMINE_5), program.getGrade5Description());
        }
        program.getStudyContents().forEach(sc -> {
            studyContents.add(sc);
        });
        studyContents.sort(new Comparator<SubjectProgramStudyContent>() {

            @Override
            public int compare(SubjectProgramStudyContent o1, SubjectProgramStudyContent o2) {
                if (ClassifierUtil.equals(StudyContentType.OPPETOOSISU_N, program.getStudyContentType())) {
                    if (o1.getOrderNr() != null && o2.getOrderNr() != null) {
                        return o1.getOrderNr().compareTo(o2.getOrderNr());
                    }
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

    public Language getLang() {
        return lang;
    }

    public SubjectProgram getProgram() {
        return program;
    }

    public Subject getSubject() {
        return subject;
    }

    public List<Teacher> getTeachers() {
        return teachers;
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
