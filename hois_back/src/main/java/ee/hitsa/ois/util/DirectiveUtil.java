package ee.hitsa.ois.util;

import ee.hitsa.ois.domain.directive.Directive;
import ee.hitsa.ois.domain.directive.DirectiveStudent;

public abstract class DirectiveUtil {

    public static DirectiveStudent getDirectiveStudent(Directive directive, Long studentId) {
        return directive.getStudents().stream()
                .filter(ds -> !Boolean.TRUE.equals(ds.getCanceled()) 
                        && studentId.equals(EntityUtil.getNullableId(ds.getStudent())))
                .findAny().get();
    }
}
