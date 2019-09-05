package ee.hitsa.ois.enums;

import ee.hitsa.ois.message.AcademicLeaveEnding;
import ee.hitsa.ois.message.ApelApplicationCreated;
import ee.hitsa.ois.message.ConfirmationNeededMessage;
import ee.hitsa.ois.message.PracticeJournalUniqueUrlMessage;
import ee.hitsa.ois.message.StudentAbsenceCreated;
import ee.hitsa.ois.message.StudentApplicationChosenCommitteeMessage;
import ee.hitsa.ois.message.StudentApplicationConfirmed;
import ee.hitsa.ois.message.StudentApplicationCreated;
import ee.hitsa.ois.message.StudentApplicationRejectedMessage;
import ee.hitsa.ois.message.StudentDirectiveCreated;
import ee.hitsa.ois.message.StudentRemarkCreated;
import ee.hitsa.ois.message.StudentRepresentativeApplicationAccepted;
import ee.hitsa.ois.message.StudentRepresentativeApplicationCreated;
import ee.hitsa.ois.message.StudentRepresentativeApplicationRejectedMessage;
import ee.hitsa.ois.message.StudentRepresentativeEnding;
import ee.hitsa.ois.message.StudentResultMessage;
import ee.hitsa.ois.message.StudentScholarshipEnding;
import ee.hitsa.ois.message.SupportServiceEnding;
import ee.hitsa.ois.message.TimetableChanged;
import ee.hitsa.ois.message.TimetableEventCreated;
import ee.hitsa.ois.message.ContractSupervisorMessage;
import ee.hitsa.ois.message.PollReminderMessage;

public enum MessageType {

    TEATE_LIIK_UUS_KK(StudentDirectiveCreated.class),
    TEATE_LIIK_OP_KASKKIRI(StudentDirectiveCreated.class),
    TEATE_LIIK_OP_AVALDUS(StudentApplicationCreated.class),
    TEATE_LIIK_OP_AVALDUS_KINNIT(StudentApplicationConfirmed.class),
    TEATE_LIIK_TOET_KATK(StudentScholarshipEnding.class),
    TEATE_LIIK_MUUD_TUNNIPL(TimetableChanged.class),
    TEATE_LIIK_AP_LOPP(AcademicLeaveEnding.class),
    TEATE_LIIK_AV_KINNIT(ConfirmationNeededMessage.class),
    TEATE_LIIK_AV_OPPURI_ANDMED(StudentRepresentativeApplicationCreated.class),
    TEATE_LIIK_OP_ESINDAJA(StudentRepresentativeApplicationAccepted.class),
    TEATE_LIIK_OP_ESINDAJA_LOPP(StudentRepresentativeEnding.class),
    TEATE_LIIK_OP_TL(StudentRepresentativeApplicationRejectedMessage.class),
    TEATE_LIIK_OP_AVALDUS_TL(StudentApplicationRejectedMessage.class),
    TEATE_LIIK_OP_AVALDUS_YL(StudentApplicationChosenCommitteeMessage.class),
    TEATE_LIIK_OP_PT(StudentAbsenceCreated.class),
    TEATE_LIIK_OA_TULEMUS(StudentResultMessage.class),
    TEATE_LIIK_OP_MARKUS(StudentRemarkCreated.class),
    TEATE_LIIK_PRAKTIKA_URL(PracticeJournalUniqueUrlMessage.class),
    TEATE_LIIK_TUGI_LOPP(SupportServiceEnding.class),
    TEATE_LIIK_TUNN_SYNDMUS(TimetableEventCreated.class),
    TEATE_LIIK_VOTA(ApelApplicationCreated.class),
    TEATE_LIIK_KYSI_EV_JUHENDAJA(ContractSupervisorMessage.class),
    TEATE_LIIK_KYSI_MEELDETULETUS(PollReminderMessage.class);

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
