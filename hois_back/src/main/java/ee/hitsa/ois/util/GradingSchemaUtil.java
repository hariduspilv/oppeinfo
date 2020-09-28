package ee.hitsa.ois.util;

import ee.hitsa.ois.domain.gradingschema.GradingSchema;
import ee.hitsa.ois.enums.Permission;
import ee.hitsa.ois.enums.PermissionObject;
import ee.hitsa.ois.service.security.HoisUserDetails;

public abstract class GradingSchemaUtil {

    public static boolean canView(HoisUserDetails user, GradingSchema gradingSchema) {
        return UserUtil.hasPermission(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_HINDAMISSYSTEEM)
                && UserUtil.isSchoolAdmin(user, gradingSchema.getSchool());
    }

    public static boolean canViewTypeSchemas(HoisUserDetails user) {
        return UserUtil.hasPermission(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_HINDAMISSYSTEEM)
                && user.isSchoolAdmin();
    }

    public static boolean canCreate(HoisUserDetails user) {
        return UserUtil.hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_HINDAMISSYSTEEM)
                && user.isSchoolAdmin();
    }

    public static boolean canEdit(HoisUserDetails user, GradingSchema gradingSchema) {
        return UserUtil.hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_HINDAMISSYSTEEM)
                && UserUtil.isSchoolAdmin(user, gradingSchema.getSchool());
    }

}
