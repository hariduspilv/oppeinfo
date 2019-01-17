package ee.hitsa.ois.util;

import java.util.Comparator;

import ee.hitsa.ois.domain.directive.Directive;
import ee.hitsa.ois.domain.directive.DirectiveStudent;
import ee.hitsa.ois.enums.DirectiveType;

public abstract class DirectiveUtil {
    
    private static final Comparator<DirectiveStudent> ID_COMPARATOR = Comparator.comparing(DirectiveStudent::getId);
    private static final Comparator<DirectiveStudent> LASTNAME_COMPARATOR = Comparator.comparing(ds -> ds.getPerson().getLastname());
    private static final Comparator<DirectiveStudent> GRADUATE_COMPARATOR = Comparator.<DirectiveStudent, String>comparing(
            ds -> EntityUtil.getCode(ds.getStudent().getCurriculumVersion().getCurriculum().getOrigStudyLevel()))
            .thenComparing(ds -> ds.getStudent().getCurriculumVersion().getCurriculum().getNameEt().toUpperCase())
            .thenComparing(ds -> ds.getStudent().getStudentGroup(), Comparator.nullsLast(Comparator.comparing(sg -> sg.getCode().toUpperCase())))
            .thenComparing(ds -> ds.getPerson().getLastname().toUpperCase())
            .thenComparing(ds -> ds.getPerson().getFirstname(), Comparator.nullsLast(Comparator.comparing(fn -> fn.toUpperCase())));

    public static DirectiveStudent getDirectiveStudent(Directive directive, Long studentId) {
        return directive.getStudents().stream()
                .filter(ds -> !Boolean.TRUE.equals(ds.getCanceled()) 
                        && studentId.equals(EntityUtil.getNullableId(ds.getStudent())))
                .findAny().get();
    }
    
    public static Comparator<DirectiveStudent> getStudentDtoComparator(DirectiveType directiveType) {
        if (DirectiveType.KASKKIRI_LOPET.equals(directiveType)) {
            return GRADUATE_COMPARATOR;
        }
        return ID_COMPARATOR;
    }
    
    public static Comparator<DirectiveStudent> getStudentEkisComparator(DirectiveType directiveType) {
        if (DirectiveType.KASKKIRI_LOPET.equals(directiveType)) {
            return GRADUATE_COMPARATOR;
        }
        return LASTNAME_COMPARATOR;
    }
}
