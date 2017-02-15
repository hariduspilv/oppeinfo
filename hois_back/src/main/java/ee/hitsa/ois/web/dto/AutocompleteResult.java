package ee.hitsa.ois.web.dto;

import ee.hitsa.ois.domain.School;
import ee.hitsa.ois.domain.StateCurriculum;
import ee.hitsa.ois.domain.StudentGroup;
import ee.hitsa.ois.domain.curriculum.Curriculum;
import ee.hitsa.ois.domain.curriculum.CurriculumSpeciality;
import ee.hitsa.ois.domain.curriculum.CurriculumVersion;

public class AutocompleteResult<ID> {

    private final ID id;
    private final String nameEt;
    private final String nameEn;

    public AutocompleteResult(ID id, String nameEt, String nameEn) {
        this.id = id;
        this.nameEt = nameEt;
        this.nameEn = nameEn;
    }

    public ID getId() {
        return id;
    }

    public String getNameEt() {
        return nameEt;
    }

    public String getNameEn() {
        return nameEn;
    }

    public static AutocompleteResult<Long> of(Curriculum curriculum) {
        return new AutocompleteResult<>(curriculum.getId(), curriculum.getNameEt(), curriculum.getNameEn());
    }

    public static AutocompleteResult<Long> of(CurriculumVersion curriculumVersion) {
        Curriculum curriculum = curriculumVersion.getCurriculum();
        return new AutocompleteResult<>(curriculumVersion.getId(), curriculum.getNameEt(), curriculum.getNameEn());
    }

    public static AutocompleteResult<Long> of(CurriculumSpeciality curriculumSpeciality) {
        return new AutocompleteResult<>(curriculumSpeciality.getId(), curriculumSpeciality.getNameEt(), curriculumSpeciality.getNameEn());
    }

    public static AutocompleteResult<Long> of(School school) {
        return new AutocompleteResult<>(school.getId(), school.getNameEt(), school.getNameEn());
    }

    public static AutocompleteResult<Long> of(StudentGroup studentGroup) {
        return new AutocompleteResult<>(studentGroup.getId(), studentGroup.getCode(), studentGroup.getCode());
    }

    public static AutocompleteResult<Long> of(SubjectAutocompleteResult subject) {
        return new AutocompleteResult<>(subject.getId(), String.format("%1$s - %2$s", subject.getCode(), subject.getNameEt()), String.format("%1$s - %2$s", subject.getCode(), subject.getNameEn()));
    }

    public static AutocompleteResult<Long> of(StateCurriculum stateCurriculum) {
        return new AutocompleteResult<>(stateCurriculum.getId(), stateCurriculum.getNameEt(), stateCurriculum.getNameEn());
    }
}
