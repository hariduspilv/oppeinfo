package ee.hitsa.ois.web.dto;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import ee.hitsa.ois.domain.DeclarationSubject;
import ee.hitsa.ois.domain.subject.Subject;
import ee.hitsa.ois.domain.subject.SubjectConnect;
import ee.hitsa.ois.domain.subject.subjectprogram.SubjectProgram;
import ee.hitsa.ois.enums.SubjectConnection;
import ee.hitsa.ois.enums.SubjectProgramStatus;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.SubjectProgramUtil;
import ee.hitsa.ois.web.commandobject.VersionedCommand;

public class DeclarationSubjectDto extends VersionedCommand {

    private Long id;
    private Long subjectStudyPeriod;
    private Long declaration;
    private List<String> teachers;
    private List<SubjectProgramResult> programs;
    private SubjectSearchDto subject;
    private AutocompleteResult module;
    private Boolean isOptional;
    private Set<PrerequisiteSubjectDto> mandatoryPrerequisiteSubjects;
    private Set<PrerequisiteSubjectDto> recommendedPrerequisiteSubjects;
    private Boolean isDeclaredRepeatedly;
    private Boolean isAssessed;

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
        dto.setTeachers(PersonUtil.sorted(declarationSubject.getSubjectStudyPeriod().getTeachers().stream().map(t -> t.getTeacher().getPerson())));
        // In case of having not available or not created subject program it should return ID '-1' which means that there is no program available.
        dto.setPrograms(declarationSubject.getSubjectStudyPeriod().getTeachers().stream().map(t -> {
            Optional<SubjectProgram> optProgram = t.getSubjectPrograms().stream().findFirst(); // Only 1 study program for subjectStudyPeriodTeacher
            SubjectProgramResult programDto = new SubjectProgramResult();
            programDto.setTeacherName(PersonUtil.fullname(t.getTeacher().getPerson()));
            if (optProgram.isPresent() && ClassifierUtil.equals(SubjectProgramStatus.AINEPROGRAMM_STAATUS_K, optProgram.get().getStatus())) {
                programDto.setId(optProgram.get().getId());
                programDto.setPublicStudent(Boolean.valueOf(SubjectProgramUtil.isPublicForStudent(optProgram.get())));
            } else {
                programDto.setId(Long.valueOf(-1));
            }
            return programDto;
        }).collect(Collectors.toList()));
        SubjectSearchDto subjectDto = new SubjectSearchDto();
        Subject subject = declarationSubject.getSubjectStudyPeriod().getSubject();
        subjectDto.setId(EntityUtil.getId(subject));
        subjectDto.setCode(subject.getCode());
        subjectDto.setNameEt(subject.getNameEt());
        subjectDto.setNameEn(subject.getNameEn());
        subjectDto.setCredits(subject.getCredits());
        subjectDto.setAssessment(subject.getAssessment().getValue());
        dto.setSubject(subjectDto);
        
        Set<PrerequisiteSubjectDto> mandatoryPrerequisiteSubjects = new HashSet<>();
        Set<PrerequisiteSubjectDto> recommendedPrerequisiteSubjects = new HashSet<>();
        
        for (SubjectConnect connection: subject.getSubjectConnections()) {
            Subject cs = connection.getConnectSubject();
            PrerequisiteSubjectDto s = new PrerequisiteSubjectDto(cs.getId(), cs.getCode(), cs.getNameEt(), cs.getNameEn(),
                    cs.getCredits(), cs.getAssessment().getValue(), null);
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
    
    
    public Boolean getIsAssessed() {
        return isAssessed;
    }

    public void setIsAssessed(Boolean isAssessed) {
        this.isAssessed = isAssessed;
    }

    public Boolean getIsDeclaredRepeatedly() {
        return isDeclaredRepeatedly;
    }

    public void setIsDeclaredRepeatedly(Boolean isDeclaredRepeatedly) {
        this.isDeclaredRepeatedly = isDeclaredRepeatedly;
    }

    public Set<PrerequisiteSubjectDto> getMandatoryPrerequisiteSubjects() {
        return mandatoryPrerequisiteSubjects;
    }

    public void setMandatoryPrerequisiteSubjects(Set<PrerequisiteSubjectDto> mandatoryPrerequisiteSubjects) {
        this.mandatoryPrerequisiteSubjects = mandatoryPrerequisiteSubjects;
    }

    public Set<PrerequisiteSubjectDto> getRecommendedPrerequisiteSubjects() {
        return recommendedPrerequisiteSubjects;
    }

    public void setRecommendedPrerequisiteSubjects(Set<PrerequisiteSubjectDto> recommendedPrerequisiteSubjects) {
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

    public List<String> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<String> teachers) {
        this.teachers = teachers;
    }

    public List<SubjectProgramResult> getPrograms() {
        return programs;
    }


    public void setPrograms(List<SubjectProgramResult> programs) {
        this.programs = programs;
    }


    public AutocompleteResult getModule() {
        return module;
    }

    public void setModule(AutocompleteResult module) {
        this.module = module;
    }
}
