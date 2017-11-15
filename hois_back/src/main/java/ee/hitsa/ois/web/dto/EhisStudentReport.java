package ee.hitsa.ois.web.dto;

import ee.hitsa.ois.domain.WsEhisStudentLog;
import ee.hitsa.ois.domain.directive.DirectiveStudent;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.util.DateUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class EhisStudentReport {

    private List<Graduation> graduations;
    private List<CurriculaFulfilment> fulfilments;
    private List<ForeignStudy> foreignStudies;

    public List<Graduation> getGraduations() {
        return graduations;
    }

    public void setGraduations(List<Graduation> graduations) {
        this.graduations = graduations;
    }

    public List<CurriculaFulfilment> getFulfilments() {
        return fulfilments;
    }

    public void setFulfilments(List<CurriculaFulfilment> fulfilments) {
        this.fulfilments = fulfilments;
    }

    public List<ForeignStudy> getForeignStudies() {
        return foreignStudies;
    }

    public void setForeignStudies(List<ForeignStudy> foreignStudies) {
        this.foreignStudies = foreignStudies;
    }

    public static class Graduation extends StudentReport {
        private String docNr;
        private Boolean cumLaude;
        private String academicNr;

        public static Graduation of(DirectiveStudent directiveStudent, WsEhisStudentLog log) {
            Graduation graduation = new Graduation();
            graduation.fill(directiveStudent.getStudent(), log);
            //graduation.setDocNr();
            graduation.setCumLaude(directiveStudent.getIsCumLaude());
            //graduation.setAcademicNr();

            return graduation;
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

        public void setCumLaude(Boolean cumLaude) {
            this.cumLaude = cumLaude;
        }

        public String getAcademicNr() {
            return academicNr;
        }

        public void setAcademicNr(String academicNr) {
            this.academicNr = academicNr;
        }
    }

    public static class CurriculaFulfilment extends StudentReport {
        private BigDecimal percentage;
        private BigDecimal points;

        public static CurriculaFulfilment of(Student student, WsEhisStudentLog log) {
            CurriculaFulfilment fulfilment = new CurriculaFulfilment();
            fulfilment.fill(student, log);

            // TODO currently no way to find
            fulfilment.setPercentage(new BigDecimal(100));
            // TODO currently no way to find
            fulfilment.setPoints(new BigDecimal(50));
            return fulfilment;
        }

        public BigDecimal getPercentage() {
            return percentage;
        }

        public void setPercentage(BigDecimal percentage) {
            this.percentage = percentage;
        }

        public BigDecimal getPoints() {
            return points;
        }

        public void setPoints(BigDecimal points) {
            this.points = points;
        }
    }

    public static class ForeignStudy extends StudentReport {
        private LocalDate fromDate;
        private LocalDate toDate;

        public static ForeignStudy of(DirectiveStudent ds, WsEhisStudentLog log) {
            ForeignStudy foreignStudy = new ForeignStudy();
            foreignStudy.fill(ds.getStudent(), log);

            foreignStudy.setFromDate(DateUtils.periodStart(ds));
            foreignStudy.setToDate(DateUtils.periodEnd(ds));

            foreignStudy.setError(Boolean.valueOf(Boolean.TRUE.equals(log.getHasOtherErrors()) || Boolean.TRUE.equals(log.getHasXteeErrors())));
            foreignStudy.setMessage(log.getLogTxt());

            return foreignStudy;
        }

        public LocalDate getFromDate() {
            return fromDate;
        }

        public void setFromDate(LocalDate fromDate) {
            this.fromDate = fromDate;
        }

        public LocalDate getToDate() {
            return toDate;
        }

        public void setToDate(LocalDate toDate) {
            this.toDate = toDate;
        }
    }

    protected static class StudentReport {
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
    }
}
