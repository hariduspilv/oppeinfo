package ee.hitsa.ois.web.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import ee.hitsa.ois.domain.WsEhisStudentLog;
import ee.hitsa.ois.domain.directive.DirectiveStudent;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.util.DateUtils;
import ee.hitsa.ois.util.EntityUtil;

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
        private final LocalDate confirmed;
        private final List<StudyRecord> records;

        public ApelApplication(ee.hitsa.ois.domain.apelapplication.ApelApplication application, WsEhisStudentLog log,
                List<StudyRecord> records) {
            fill(application.getStudent(), log);
            confirmed = application.getConfirmed().toLocalDate();
            this.records = records;
        }

        public LocalDate getConfirmed() {
            return confirmed;
        }

        public List<StudyRecord> getRecords() {
            return records;
        }
        
        public static class StudyRecord {
            private final BigDecimal credits;
            private final Boolean isFormalLearning;
            private LocalDate gradeDate;
            private String schoolNameEt;
            private String countryCode;
            
            public StudyRecord(BigDecimal credits, Boolean isFormalLearning) {
                this.credits = credits;
                this.isFormalLearning = isFormalLearning;
            }

            public LocalDate getGradeDate() {
                return gradeDate;
            }

            public void setGradeDate(LocalDate gradeDate) {
                this.gradeDate = gradeDate;
            }

            public String getSchoolNameEt() {
                return schoolNameEt;
            }

            public void setSchoolNameEt(String schoolNameEt) {
                this.schoolNameEt = schoolNameEt;
            }

            public String getCountryCode() {
                return countryCode;
            }

            public void setCountryCode(String countryCode) {
                this.countryCode = countryCode;
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
        private final Boolean cumLaude;
        private final String docNr;
        private final String academicNr;
        private final List<String> extraNr;
        private final String academicNrEn;
        private final List<String> extraNrEn;

        public Graduation(DirectiveStudent directiveStudent, WsEhisStudentLog log,
                String docNr, String academicNr, List<String> extraNr, String academicNrEn, List<String> extraNrEn) {
            fill(directiveStudent.getStudent(), log);
            
            cumLaude = directiveStudent.getIsCumLaude();
            this.docNr = docNr;
            this.academicNr = academicNr;
            this.extraNr = extraNr;
            this.academicNrEn = academicNrEn;
            this.extraNrEn = extraNrEn;
        }

        public Boolean getCumLaude() {
            return cumLaude;
        }

        public String getDocNr() {
            return docNr;
        }

        public String getAcademicNr() {
            return academicNr;
        }
        
        public List<String> getExtraNr() {
            return extraNr;
        }

        public String getAcademicNrEn() {
            return academicNrEn;
        }

        public List<String> getExtraNrEn() {
            return extraNrEn;
        }
    }

    public static class CurriculaFulfilment extends EhisStudentReport {
        private final BigDecimal percentage;
        private final BigDecimal points;
        private final Boolean lastPeriod;
        
        public CurriculaFulfilment(Student student, WsEhisStudentLog log, BigDecimal percentage,
                BigDecimal points, Boolean lastPeriod) {
            fill(student, log);

            this.percentage = percentage;
            this.points = points;
            this.lastPeriod = lastPeriod;
        }

        public BigDecimal getPercentage() {
            return percentage;
        }

        public BigDecimal getPoints() {
            return points;
        }

        public Boolean getLastPeriod() {
            return lastPeriod;
        }
        
    }

    public static class ForeignStudy extends EhisStudentReport {
        private final LocalDate fromDate;
        private final LocalDate toDate;
        private final String abroadPurpose;
        private final BigDecimal points;
        private final Integer nominalStudyExtension;
        private final LocalDate nominalStudyEnd;
        private final String schoolName;
        private final String country;
        private final String abroadProgramme;

        public ForeignStudy(DirectiveStudent ds, WsEhisStudentLog log, BigDecimal points, Integer nominalStudyExtension) {
            fill(ds.getStudent(), log);

            fromDate = DateUtils.periodStart(ds);
            toDate = DateUtils.periodEnd(ds);
            abroadPurpose = EntityUtil.getCode(ds.getAbroadPurpose());
            this.points = points;
            this.nominalStudyExtension = nominalStudyExtension;
            nominalStudyEnd = ds.getNominalStudyEnd();
            schoolName = Boolean.TRUE.equals(ds.getIsAbroad()) ? ds.getAbroadSchool() : ds.getEhisSchool().getNameEt();
            country = EntityUtil.getCode(ds.getCountry());
            abroadProgramme = EntityUtil.getCode(ds.getAbroadProgramme());
        }

        public LocalDate getFromDate() {
            return fromDate;
        }

        public LocalDate getToDate() {
            return toDate;
        }

        public String getAbroadPurpose() {
            return abroadPurpose;
        }

        public BigDecimal getPoints() {
            return points;
        }
        
        public Integer getNominalStudyExtension() {
            return nominalStudyExtension;
        }

        public LocalDate getNominalStudyEnd() {
            return nominalStudyEnd;
        }

        public String getSchoolName() {
            return schoolName;
        }

        public String getCountry() {
            return country;
        }

        public String getAbroadProgramme() {
            return abroadProgramme;
        }
    }

    public static class Dormitory extends EhisStudentReport {
        private final String dormitory;
        private final LocalDate changeDate;

        public Dormitory(Student student, WsEhisStudentLog log, String dormitory, LocalDate changeDate) {
            fill(student, log);

            this.dormitory = dormitory;
            this.changeDate = changeDate;
        }

        public String getDormitory() {
            return dormitory;
        }

        public LocalDate getChangeDate() {
            return changeDate;
        }
    }
    
    public static class Course extends EhisStudentReport {
        private final Integer newCourse;
        private final LocalDate changed;
        
        public Course(Student student, WsEhisStudentLog log, Integer newCourse, LocalDate changed) {
            fill(student, log);
            
            this.newCourse = newCourse;
            this.changed = changed;
        }

        public Integer getNewCourse() {
            return newCourse;
        }

        public LocalDate getChanged() {
            return changed;
        }
    }
    
    public static class SpecialNeeds extends EhisStudentReport {
        
        public SpecialNeeds(Student student, WsEhisStudentLog log) {
            fill(student, log);
        }
    }
}
