package ee.hitsa.ois.web.dto;

import java.util.HashSet;
import java.util.Set;

import ee.hitsa.ois.domain.DeclarationSubject;
import ee.hitsa.ois.domain.subject.Subject;
import ee.hitsa.ois.domain.subject.SubjectConnect;
import ee.hitsa.ois.enums.SubjectConnection;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.commandobject.VersionedCommand;

public class DeclarationSubjectDto extends VersionedCommand {

    private Long id;
    private Long subjectStudyPeriod;
    private Long declaration;
    private Set<String> teachers;
    private SubjectSearchDto subject;
    private AutocompleteResult module;
    private Boolean isOptional;
    private Set<AutocompleteResult> mandatoryPrerequisiteSubjects;
    private Set<AutocompleteResult> recommendedPrerequisiteSubjects;
    private Boolean isDeclaredRepeatedly;

    public static DeclarationSubjectDto of(DeclarationSubject declarationSubject) {
        DeclarationSubjectDto dto = new DeclarationSubjectDto();
        dto.setId(EntityUtil.getId(declarationSubject));
        dto.setSubjectStudyPeriod(EntityUtil.getId(declarationSubject.getSubjectStudyPeriod()));
        dto.setDeclaration(EntityUtil.getId(declarationSubject.getDeclaration()));
        dto.setVersion(declarationSubject.getVersion());
        dto.setIsOptional(declarationSubject.getIsOptional());
        
        if(declarationSubject.getModule() != null) {
            dto.setModule(AutocompleteResult.of(declarationSubject.getModule()));
        }
        dto.setTeachers(StreamUtil.toMappedSet(t -> t.getTeacher().getPerson().getFullname(), 
                declarationSubject.getSubjectStudyPeriod().getTeachers()));
        SubjectSearchDto subjectDto = new SubjectSearchDto();
        Subject subject = declarationSubject.getSubjectStudyPeriod().getSubject();
        subjectDto.setId(EntityUtil.getId(subject));
        subjectDto.setCode(subject.getCode());
        subjectDto.setNameEt(subject.getNameEt());
        subjectDto.setNameEn(subject.getNameEn());
        subjectDto.setCredits(subject.getCredits());
        subjectDto.setAssessment(subject.getAssessment().getValue());
        dto.setSubject(subjectDto);
        
        Set<AutocompleteResult> mandatoryPrerequisiteSubjects = new HashSet<>();
        Set<AutocompleteResult> recommendedPrerequisiteSubjects = new HashSet<>();
        for (SubjectConnect connection: subject.getSubjectConnections()) {
            AutocompleteResult s = AutocompleteResult.of(connection.getConnectSubject());
            String connectionCode = EntityUtil.getCode(connection.getConnection());
            if (SubjectConnection.AINESEOS_EK.name().equals(connectionCode)) {
                mandatoryPrerequisiteSubjects.add(s);
            } else if (SubjectConnection.AINESEOS_EV.name().equals(connectionCode)) {
                recommendedPrerequisiteSubjects.add(s);
            }

        }
        dto.setMandatoryPrerequisiteSubjects(mandatoryPrerequisiteSubjects);
        dto.setRecommendedPrerequisiteSubjects(recommendedPrerequisiteSubjects);
        
        return dto;
    }

    public Boolean getIsDeclaredRepeatedly() {
        return isDeclaredRepeatedly;
    }

    public void setIsDeclaredRepeatedly(Boolean isDeclaredRepeatedly) {
        this.isDeclaredRepeatedly = isDeclaredRepeatedly;
    }

    public Set<AutocompleteResult> getMandatoryPrerequisiteSubjects() {
        return mandatoryPrerequisiteSubjects;
    }

    public void setMandatoryPrerequisiteSubjects(Set<AutocompleteResult> mandatoryPrerequisiteSubjects) {
        this.mandatoryPrerequisiteSubjects = mandatoryPrerequisiteSubjects;
    }

    public Set<AutocompleteResult> getRecommendedPrerequisiteSubjects() {
        return recommendedPrerequisiteSubjects;
    }

    public void setRecommendedPrerequisiteSubjects(Set<AutocompleteResult> recommendedPrerequisiteSubjects) {
        this.recommendedPrerequisiteSubjects = recommendedPrerequisiteSubjects;
    }

    public Long getSubjectStudyPeriod() {
        return subjectStudyPeriod;
    }

    public void setSubjectStudyPeriod(Long subjectStudyPeriod) {
        this.subjectStudyPeriod = subjectStudyPeriod;
    }

    public SubjectSearchDto getSubject() {
        return subject;
    }

    public void setSubject(SubjectSearchDto subject) {
        this.subject = subject;
    }

    public Boolean getIsOptional() {
        return isOptional;
    }

    public void setIsOptional(Boolean isOptional) {
        this.isOptional = isOptional;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDeclaration() {
        return declaration;
    }

    public void setDeclaration(Long declaration) {
        this.declaration = declaration;
    }

    public Set<String> getTeachers() {
        return teachers;
    }

    public void setTeachers(Set<String> teachers) {
        this.teachers = teachers;
    }

    public AutocompleteResult getModule() {
        return module;
    }

    public void setModule(AutocompleteResult module) {
        this.module = module;
    }
}
