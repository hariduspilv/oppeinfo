package ee.hitsa.ois.web.dto;

import ee.hitsa.ois.domain.PracticeJournal;
import ee.hitsa.ois.domain.PracticeJournalEvaluation;
import ee.hitsa.ois.domain.protocol.ProtocolStudent;
import ee.hitsa.ois.domain.student.StudentCurriculumModuleOutcomesResult;
import ee.hitsa.ois.domain.student.StudentCurriculumModuleOutcomesResultHistory;
import ee.hitsa.ois.domain.timetable.JournalEntryStudent;
import ee.hitsa.ois.domain.timetable.JournalEntryStudentHistory;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.validation.ClassifierRestriction;

public class GradeDto {

    @ClassifierRestriction({MainClassCode.KUTSEHINDAMINE, MainClassCode.KORGHINDAMINE})
    private String code;
    private Long gradingSchemaRowId;

    public GradeDto() { }

    public GradeDto(String code, Long gradingSchemaRowId) {
        this.code = code;
        this.gradingSchemaRowId = gradingSchemaRowId;
    }

    public static GradeDto of(ProtocolStudent protocolStudent) {
        String gradeCode = EntityUtil.getNullableCode(protocolStudent.getGrade());
        Long gradingSchemaRowId = EntityUtil.getNullableId(protocolStudent.getGradingSchemaRow());
        return gradeCode != null ? new GradeDto(gradeCode, gradingSchemaRowId) : null;
    }

    public static GradeDto of(JournalEntryStudent journalEntryStudent) {
        String gradeCode = EntityUtil.getNullableCode(journalEntryStudent.getGrade());
        Long gradingSchemaRowId = EntityUtil.getNullableId(journalEntryStudent.getGradingSchemaRow());
        return gradeCode != null ? new GradeDto(gradeCode, gradingSchemaRowId) : null;
    }

    public static GradeDto of(JournalEntryStudentHistory journalEntryStudentHistory) {
        String gradeCode = EntityUtil.getNullableCode(journalEntryStudentHistory.getGrade());
        Long gradingSchemaRowId = EntityUtil.getNullableId(journalEntryStudentHistory.getGradingSchemaRow());
        return gradeCode != null ? new GradeDto(gradeCode, gradingSchemaRowId) : null;
    }

    public static GradeDto of(StudentCurriculumModuleOutcomesResult outcomesResult) {
        String gradeCode = EntityUtil.getNullableCode(outcomesResult.getGrade());
        Long gradingSchemaRowId = EntityUtil.getNullableId(outcomesResult.getGradingSchemaRow());
        return gradeCode != null ? new GradeDto(gradeCode, gradingSchemaRowId) : null;
    }

    public static GradeDto of(StudentCurriculumModuleOutcomesResultHistory outcomesResultHistory) {
        String gradeCode = EntityUtil.getNullableCode(outcomesResultHistory.getGrade());
        Long gradingSchemaRowId = EntityUtil.getNullableId(outcomesResultHistory.getGradingSchemaRow());
        return gradeCode != null ? new GradeDto(gradeCode, gradingSchemaRowId) : null;
    }

    public static GradeDto of(PracticeJournal practiceJournal) {
        String gradeCode = EntityUtil.getNullableCode(practiceJournal.getGrade());
        Long gradingSchemaRowId = EntityUtil.getNullableId(practiceJournal.getGradingSchemaRow());
        return gradeCode != null ? new GradeDto(gradeCode, gradingSchemaRowId) : null;
    }

    public static GradeDto of(PracticeJournalEvaluation practiceJournalEvaluation) {
        String gradeCode = EntityUtil.getNullableCode(practiceJournalEvaluation.getValueClf());
        Long gradingSchemaRowId = EntityUtil.getNullableId(practiceJournalEvaluation.getGradingSchemaRow());
        return gradeCode != null ? new GradeDto(gradeCode, gradingSchemaRowId) : null;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getGradingSchemaRowId() {
        return gradingSchemaRowId;
    }

    public void setGradingSchemaRowId(Long gradingSchemaRowId) {
        this.gradingSchemaRowId = gradingSchemaRowId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || code == null || !getClass().equals(obj.getClass())) {
            return false;
        }

        GradeDto gradeObj = (GradeDto) obj;
        return code.equals(gradeObj.code) && ((gradingSchemaRowId == null && gradeObj.gradingSchemaRowId == null)
            || (gradingSchemaRowId != null && gradingSchemaRowId.equals(gradeObj.gradingSchemaRowId)));
    }
}
