package ee.hitsa.ois.web.dto;

import ee.hitsa.ois.domain.WsEhisStudentLog;
import ee.hitsa.ois.domain.directive.DirectiveStudent;
import ee.hitsa.ois.domain.student.Student;

import java.math.BigDecimal;
import java.util.List;

public class EhisStudentReport {

    private List<Graduation> graduations;

    private List<CurriculaFulfilment> fulfilments;

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

    public static class Graduation {
        private Long studentId;
        private String name;
        private String idcode;
        private String curriculum;
        private String docNr;
        private Boolean cumLaude;
        private String academicNr;

        private Boolean error;
        private String message;

        public static Graduation of(DirectiveStudent directiveStudent, WsEhisStudentLog wsEhisStudentLog) {
            Graduation graduation = new Graduation();
            graduation.setStudentId(directiveStudent.getStudent().getId());
            graduation.setName(directiveStudent.getStudent().getPerson().getFullname());
            graduation.setIdcode(directiveStudent.getStudent().getPerson().getIdcode());

            graduation.setCurriculum(directiveStudent.getStudent().getCurriculumVersion().getCurriculum().getCode());
            //graduation.setDocNr();
            graduation.setCumLaude(directiveStudent.getIsCumLaude());
            //graduation.setAcademicNr();

            graduation.setError(Boolean.valueOf(Boolean.TRUE.equals(wsEhisStudentLog.getHasOtherErrors())
                    || Boolean.TRUE.equals(wsEhisStudentLog.getHasXteeErrors())));

            graduation.setMessage(wsEhisStudentLog.getLogTxt());

            return graduation;
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

    public static class CurriculaFulfilment {
        private Long studentId;
        private String name;
        private String idcode;
        private String curriculum;
        private BigDecimal percentage;
        private BigDecimal points;

        private Boolean error;
        private String message;

        public static CurriculaFulfilment of(Student student, WsEhisStudentLog wsEhisStudentLog) {
            CurriculaFulfilment fulfilment = new CurriculaFulfilment();
            fulfilment.setStudentId(student.getId());
            fulfilment.setName(student.getPerson().getFullname());
            fulfilment.setIdcode(student.getPerson().getIdcode());

            fulfilment.setCurriculum(student.getCurriculumVersion().getCurriculum().getCode());

            // TODO currently no way to find
            fulfilment.setPercentage(new BigDecimal(100));
            // TODO currently no way to find
            fulfilment.setPoints(new BigDecimal(50));

            fulfilment.setError(Boolean.valueOf(Boolean.TRUE.equals(wsEhisStudentLog.getHasOtherErrors())
                    || Boolean.TRUE.equals(wsEhisStudentLog.getHasXteeErrors())));

            fulfilment.setMessage(wsEhisStudentLog.getLogTxt());
            return fulfilment;
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
