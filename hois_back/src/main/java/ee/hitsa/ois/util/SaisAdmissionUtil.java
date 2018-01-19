package ee.hitsa.ois.util;

import ee.hitsa.ois.domain.sais.SaisAdmission;

public abstract class SaisAdmissionUtil {

    public static boolean isHigher(SaisAdmission saisAdmission) {
        return CurriculumUtil.isHigher(saisAdmission.getCurriculumVersion().getCurriculum());
    }
}
