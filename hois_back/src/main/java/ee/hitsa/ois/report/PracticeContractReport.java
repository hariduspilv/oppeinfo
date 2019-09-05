package ee.hitsa.ois.report;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import ee.hitsa.ois.domain.Contract;
import ee.hitsa.ois.domain.enterprise.Enterprise;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.TranslateUtil;
import ee.hitsa.ois.web.dto.ContractDto;
import ee.hitsa.ois.web.dto.ContractModuleSubjectDto;

public class PracticeContractReport {
    
    public static final String TEMPLATE_NAME = "practice.contract.fo";
    
    private final String studentFullname;
    
    private final ContractDto contract;
    private final String practicePlace;
    private final Enterprise enterprise;
    private final Student student;
    private final School school;
    private final List<ModuleSubject> moduleSubjects;

    public PracticeContractReport(Contract contract) {
        this(contract, Language.ET);
    }
    
    public PracticeContractReport(Contract contract, Language lang) {
        this.contract = ContractDto.of(contract);
        enterprise = contract.getEnterprise();
        student = contract.getStudent();
        school = student.getSchool();
        studentFullname = PersonUtil.fullnameAndIdcode(contract.getStudent().getPerson());
        moduleSubjects = this.contract.getModuleSubjects().stream().map(ms -> {
            return new ModuleSubject(ms, lang == null ? Language.ET : lang);
        }).collect(Collectors.toList());
        // RTF ignores \n so it should be replaced with \r. In this case we just add additionally \r
        this.contract.setPracticePlan(this.contract.getPracticePlan().replace("\n", "\n\r"));
        
        if (Boolean.TRUE.equals(this.contract.getIsPracticeSchool())) {
            practicePlace = TranslateUtil.translate("report.contract.practicePlaceSchool", lang == null ? Language.ET : lang);
        } else if (Boolean.TRUE.equals(this.contract.getIsPracticeTelework())) {
            practicePlace = TranslateUtil.translate("report.contract.practicePlaceTelework", lang == null ? Language.ET : lang);
        } else if (Boolean.TRUE.equals(this.contract.getIsPracticeEnterprise())) {
            practicePlace = this.contract.getPracticePlace();
        } else { // other
            practicePlace = this.contract.getPracticePlace();
        }
    }

    public String getStudentFullname() {
        return studentFullname;
    }

    public ContractDto getContract() {
        return contract;
    }

    public String getPracticePlace() {
        return practicePlace;
    }

    public Enterprise getEnterprise() {
        return enterprise;
    }

    public School getSchool() {
        return school;
    }

    public Student getStudent() {
        return student;
    }
    
    public List<ModuleSubject> getModuleSubjects() {
        return moduleSubjects;
    }

    public class ModuleSubject {
        
        private final String subject;
        private final String module;
        private final String theme;
        private final BigDecimal credits;
        private final Short hours;
        private final String astroHours;
        
        public ModuleSubject(ContractModuleSubjectDto moduleSubject, Language lang) {
            subject = TranslateUtil.name(moduleSubject.getSubject(), lang);
            module = TranslateUtil.name(moduleSubject.getModule(), lang);
            theme = TranslateUtil.name(moduleSubject.getTheme(), lang);
            credits = moduleSubject.getCredits();
            hours = moduleSubject.getHours();
            astroHours = getAstronomicalHoursAsString(hours);
        }
        
        private String getAstronomicalHoursAsString(Short academicHours) {
            if (academicHours == null) {
                return "0:00";
            }
            double total = academicHours.doubleValue() * 3 / 4;
            double aHours = Math.floor(total);
            double minutes = Math.ceil((total - aHours) * 60);
            return String.format("%.0ft %2.0fmin", Double.valueOf(aHours), Double.valueOf(minutes));
        }

        public String getSubject() {
            return subject;
        }

        public String getModule() {
            return module;
        }

        public String getTheme() {
            return theme;
        }

        public BigDecimal getCredits() {
            return credits;
        }

        public Short getHours() {
            return hours;
        }

        public String getAstroHours() {
            return astroHours;
        }
    }
    
}
