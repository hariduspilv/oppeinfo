package ee.hitsa.ois.report;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.TranslateUtil;
import ee.hitsa.ois.web.dto.StudentSupportServiceDto;

public class SupportServicesReport {
    public static final String TEMPLATE_NAME = "support.services.xhtml";

    private final String idcode;
    private final String firstName;
    private final String lastName;
    private final String curriculum;
    private final String curriculumCode;
    private final String curriculumVersion;
    private final LocalDate from;
    private final LocalDate thru;
    
    private final List<StudentServiceServiceReport> services;
    
    public SupportServicesReport(Student student, LocalDate from, LocalDate thru, List<StudentSupportServiceDto> services, EntityManager em, Language lang) {
        idcode = student.getPerson().getIdcode();
        firstName = student.getPerson().getFirstname();
        lastName = student.getPerson().getLastname();
        if (student.getCurriculumVersion() != null) {
            curriculum = student.getCurriculumVersion().getCurriculum().getNameEt();
            curriculumCode = student.getCurriculumVersion().getCurriculum().getCode();
            curriculumVersion = student.getCurriculumVersion().getCode();
        } else {
            curriculum = null;
            curriculumCode = null;
            curriculumVersion = null;
        }
        this.from = from;
        this.thru = thru;
        
        this.services = StreamUtil.nullSafeList(services).stream().map(s -> new StudentServiceServiceReport(s, em, lang)).collect(Collectors.toList());
    }

    public String getIdcode() {
        return idcode;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCurriculum() {
        return curriculum;
    }

    public String getCurriculumCode() {
        return curriculumCode;
    }
    
    public String getCurriculumVersion() {
        return curriculumVersion;
    }

    public LocalDate getFrom() {
        return from;
    }

    public LocalDate getThru() {
        return thru;
    }

    public List<StudentServiceServiceReport> getServices() {
        return services;
    }

    public static class StudentServiceServiceReport {
        
        private final LocalDate entryDate;
        private final String title;
        private final String content;
        private final List<String> contentArr;
        private final String validity;
        private final Boolean isPublic;
        private final Boolean visible;
        private final Boolean hasFile;
        private final String submitter;
        
        public StudentServiceServiceReport(StudentSupportServiceDto service, EntityManager em, Language lang) {
            entryDate = service.getEntryDate();
            if (service.getNameEt() != null) {
                title = service.getNameEt();
            } else {
                title = TranslateUtil.translate("report.support.services.directive", lang);
            }
            if (Boolean.TRUE.equals(service.getIsArtificial())) {
                contentArr = Arrays.stream(service.getContent().split(";")).map(code -> TranslateUtil.name(em.getReference(Classifier.class, code), lang)).collect(Collectors.toList());
                content = null;
            } else {
                content = service.getContent();
                contentArr = Collections.emptyList();
            }
            Classifier validityCl = em.getReference(Classifier.class, service.getValidity());
            validity = TranslateUtil.name(validityCl, lang);
            isPublic = service.getIsPublic();
            visible = service.getVisible();
            hasFile = Boolean.valueOf(service.getFile() != null);
            submitter = service.getEntrySubmitter();
        }

        public LocalDate getEntryDate() {
            return entryDate;
        }

        public String getTitle() {
            return title;
        }

        public String getContent() {
            return content;
        }

        public List<String> getContentArr() {
            return contentArr;
        }

        public String getValidity() {
            return validity;
        }

        public Boolean getIsPublic() {
            return isPublic;
        }

        public Boolean getVisible() {
            return visible;
        }

        public Boolean getHasFile() {
            return hasFile;
        }

        public String getSubmitter() {
            return submitter;
        }
    }
}
