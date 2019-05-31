package ee.hitsa.ois.report;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import ee.hitsa.ois.domain.application.Application;
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.enums.SupportServiceType;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.TranslateUtil;
import ee.hitsa.ois.web.dto.AutocompleteResult;

public class ApplicationTugiReport {
    public static final String TEMPLATE_NAME = "support.service.xhtml";

    private final String student;
    private final String curriculumVersion;
    private final LocalDate submittedDt;
    private final String submitter;
    private final String status;
    
    private final String description;
    private final String addInfo;
    
    private final String committee;
    private final LocalDateTime committeeDecisionDate;
    private final Boolean committeeDecision;
    private final String committeeDecisionAddInfo;
    
    private final List<SupportServiceTugi> supportServices;
    private final List<SupportServiceModuleTugi> supportModules;
    private final List<ApplicationFileTugi> files;
    
    private final String implementationPlan;
    
    private final String confirmer;
    private final LocalDateTime confirmedDt;
    private final Boolean isConfirmed;
    private final String reason;
    
    public ApplicationTugiReport(Application application, Language lang) {
        student = application.getStudent().getPerson().getFullname();
        curriculumVersion = application.getStudent().getCurriculumVersion().getCode();
        submittedDt = application.getSubmitted().toLocalDate();
        submitter = PersonUtil.stripIdcodeFromFullnameAndIdcode(application.getInsertedBy());
        status = TranslateUtil.name(application.getStatus(), lang);
        
        description = application.getOtherText();
        addInfo = application.getAddInfo();
        
        committee = application.getCommittee() != null ? application.getCommittee().getNameEt() : null;
        committeeDecisionDate = application.getCommitteeDecisionAdded();
        committeeDecision = application.getIsDecided();
        committeeDecisionAddInfo = application.getDecision();
        
        implementationPlan = application.getImplementationPlan();
        
        confirmer = PersonUtil.stripIdcodeFromFullnameAndIdcode(application.getChangedBy());
        confirmedDt = application.getRepresentativeConfirmed();
        isConfirmed = application.getIsRepresentativeConfirmed();
        reason = application.getRepresentativeDecisionAddInfo();
        
        supportServices = StreamUtil.toMappedList(service -> {
            SupportServiceTugi dto = new SupportServiceTugi();
            dto.setCode(service.getSupportService().getCode());
            dto.setName(TranslateUtil.name(service.getSupportService(), lang));
            return dto;
        }, application.getSupportServices());
        
        supportModules = application.getSupportServices().stream()
                .filter(s -> ClassifierUtil.equals(SupportServiceType.TUGITEENUS_1, s.getSupportService()))
                .flatMap(s -> s.getModules().stream())
                .map(m -> {
                    AutocompleteResult name = AutocompleteResult.of(m.getModule(), false);
                    SupportServiceModuleTugi dto = new SupportServiceModuleTugi();
                    dto.setName(TranslateUtil.name(name, lang));
                    dto.setDescription(m.getAddInfo());
                    return dto;
                }).collect(Collectors.toList());
        
        files = StreamUtil.toMappedList(file -> {
            ApplicationFileTugi dto = new ApplicationFileTugi();
            dto.setName(file.getOisFile().getFname());
            dto.setAdder(PersonUtil.stripIdcodeFromFullnameAndIdcode(file.getInsertedBy()));
            dto.setAddedDt(file.getInserted());
            return dto;
        }, application.getFiles());
    }

    public String getStudent() {
        return student;
    }

    public String getCurriculumVersion() {
        return curriculumVersion;
    }

    public LocalDate getSubmittedDt() {
        return submittedDt;
    }

    public String getSubmitter() {
        return submitter;
    }

    public String getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public String getAddInfo() {
        return addInfo;
    }

    public String getCommittee() {
        return committee;
    }

    public LocalDateTime getCommitteeDecisionDate() {
        return committeeDecisionDate;
    }

    public Boolean getCommitteeDecision() {
        return committeeDecision;
    }

    public String getCommitteeDecisionAddInfo() {
        return committeeDecisionAddInfo;
    }

    public List<SupportServiceTugi> getSupportServices() {
        return supportServices;
    }

    public List<SupportServiceModuleTugi> getSupportModules() {
        return supportModules;
    }

    public List<ApplicationFileTugi> getFiles() {
        return files;
    }

    public String getImplementationPlan() {
        return implementationPlan;
    }

    public String getConfirmer() {
        return confirmer;
    }

    public LocalDateTime getConfirmedDt() {
        return confirmedDt;
    }

    public Boolean getIsConfirmed() {
        return isConfirmed;
    }

    public String getReason() {
        return reason;
    }
    
    static class SupportServiceTugi {
        
        private String code;
        private String name;
        
        public String getCode() {
            return code;
        }
        public void setCode(String code) {
            this.code = code;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
    }

    static class SupportServiceModuleTugi {
        
        private String name;
        private String description;
        
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public String getDescription() {
            return description;
        }
        public void setDescription(String description) {
            this.description = description;
        }
    }
    
    static class ApplicationFileTugi {
        
        private String adder;
        private LocalDateTime addedDt;
        private String name;
        
        public String getAdder() {
            return adder;
        }
        public void setAdder(String adder) {
            this.adder = adder;
        }
        public LocalDateTime getAddedDt() {
            return addedDt;
        }
        public void setAddedDt(LocalDateTime addedDt) {
            this.addedDt = addedDt;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
    }
}
