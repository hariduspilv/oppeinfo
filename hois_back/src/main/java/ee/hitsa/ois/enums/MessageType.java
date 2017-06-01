package ee.hitsa.ois.enums;

import ee.hitsa.ois.message.ConfirmationNeededMessage;
import ee.hitsa.ois.message.StudentAbsenceCreated;
import ee.hitsa.ois.message.StudentApplicationRejectedMessage;
import ee.hitsa.ois.message.StudentDirectiveCreated;
import ee.hitsa.ois.message.StudentRepresentativeApplicationAccepted;
import ee.hitsa.ois.message.StudentRepresentativeApplicationCreated;
import ee.hitsa.ois.message.StudentRepresentativeApplicationRejectedMessage;

public enum MessageType {

    TEATE_LIIK_UUS_KK(StudentDirectiveCreated.class),
    TEATE_LIIK_MUUD_TUNNIPL(null),
    TEATE_LIIK_AP_LOPP(null),
    TEATE_LIIK_AV_KINNIT(ConfirmationNeededMessage.class),
    TEATE_LIIK_AV_OPPURI_ANDMED(StudentRepresentativeApplicationCreated.class),
    TEATE_LIIK_OP_ESINDAJA(StudentRepresentativeApplicationAccepted.class),
    TEATE_LIIK_OP_TL(StudentRepresentativeApplicationRejectedMessage.class),
    TEATE_LIIK_OP_AVALDUS_TL(StudentApplicationRejectedMessage.class),
    TEATE_LIIK_OP_PT(StudentAbsenceCreated.class),
    TEATE_LIIK_OA_TULEMUS(null);

    private final Class<?> dataBean;

    MessageType(Class<?> dataBean) {
        this.dataBean = dataBean;
    }

    public Class<?> getDataBean() {
        return dataBean;
    }

    public boolean validBean(Object dataBeanObject) {
        if (dataBean == null) {
            return true;
        }
        return dataBeanObject != null && dataBean.isAssignableFrom(dataBeanObject.getClass());
    }
}
