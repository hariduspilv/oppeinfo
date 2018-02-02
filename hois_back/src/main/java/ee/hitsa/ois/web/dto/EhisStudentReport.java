package ee.hitsa.ois.web.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import ee.hitsa.ois.domain.WsEhisStudentLog;
import ee.hitsa.ois.domain.apelapplication.ApelApplicationRecord;
import ee.hitsa.ois.domain.directive.DirectiveStudent;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.util.DateUtils;
import ee.hitsa.ois.util.StreamUtil;

public class EhisStudentReport {

    private Long studentId;
    private String name;
    private String idcode;
    private String curriculum;

    private Boolean error;
    private String message;

    protected void fill(Student student, WsEhisStudentLog log) {
        setStudentId(student.getId());
        setName(student.getPerson().getFullname());
        setIdcode(student.getPerson().getIdcode());
        setCurriculum(student.getCurriculumVersion().getCurriculum().getCode());

        setError(Boolean.valueOf(Boolean.TRUE.equals(log.getHasOtherErrors()) || Boolean.TRUE.equals(log.getHasXteeErrors())));
        setMessage(log.getLogTxt());
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdcode() {
        return idcode;
    }

    public void setIdcode(String idcode) {
        this.idcode = idcode;
    }

    public String getCurriculum() {
        return curriculum;
    }

    public void setCurriculum(String curriculum) {
        this.curriculum = curriculum;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class ApelApplication extends EhisStudentReport {
        private final List<StudyRecord> records;

        public List<StudyRecord> getRecords() {
            return records;
        }

        public ApelApplication(ee.hitsa.ois.domain.apelapplication.ApelApplication application, WsEhisStudentLog log) {
            fill(application.getStudent(), log);
            records = StreamUtil.toMappedList(StudyRecord::new, application.getRecords());
        }

        public static class StudyRecord {
            private final BigDecimal credits;
            private final Boolean isFormalLearning;

            public StudyRecord(ApelApplicationRecord record) {
                isFormalLearning = record.getIsFormalLearning();
                // TODO
                credits = BigDecimal.ZERO;
            }

            public BigDecimal getCredits() {
                return credits;
            }

            public Boolean getIsFormalLearning() {
                return isFormalLearning;
            }
        }
    }

    public static class Graduation extends EhisStudentReport {
        private String docNr;
        private final Boolean cumLaude;
        private String academicNr;

        public Graduation(DirectiveStudent directiveStudent, WsEhisStudentLog log) {
            fill(directiveStudent.getStudent(), log);
            //graduation.setDocNr();
            cumLaude = directiveStudent.getIsCumLaude();
            //graduation.setAcademicNr();
        }

        public String getDocNr() {
            return docNr;
        }

        public void setDocNr(String docNr) {
            this.docNr = docNr;
        }

        public Boolean getCumLaude() {
            return cumLaude;
        }

        public String getAcademicNr() {
            return academicNr;
        }

        public void setAcademicNr(String academicNr) {
            this.academicNr = academicNr;
        }
    }

    public static class CurriculaFulfilment extends EhisStudentReport {
        private final BigDecimal percentage;
        private final BigDecimal points;

        public CurriculaFulfilment(Student student, WsEhisStudentLog log) {
            fill(student, log);

            // TODO currently no way to find
            percentage = new BigDecimal(100);
            // TODO currently no way to find
            points = new BigDecimal(50);
        }

        public BigDecimal getPercentage() {
            return percentage;
        }

        public BigDecimal getPoints() {
            return points;
        }
    }

    public static class ForeignStudy extends EhisStudentReport {
        private final LocalDate fromDate;
        private final LocalDate toDate;

        public ForeignStudy(DirectiveStudent ds, WsEhisStudentLog log) {
            fill(ds.getStudent(), log);

            fromDate = DateUtils.periodStart(ds);
            toDate = DateUtils.periodEnd(ds);
        }

        public LocalDate getFromDate() {
            return fromDate;
        }

        public LocalDate getToDate() {
            return toDate;
        }
    }
}
