package ee.hitsa.ois.message;

import ee.hitsa.ois.domain.protocol.ProtocolStudent;

public class StudentResultMessage extends StudentMessage {

    // TODO
    // oppeaine_kood   <oppeaine_kood> Õppeaine kood, milles õppur on saanud õppetulemuse
    // oppeaine_nimetus    <oppeaine_nimetus>  Õppeaine või teema/mooduli nimetus, milles õppur on saanud õppetulemuse või mille tunniplaanis on toimunud muudatus

    public StudentResultMessage() {
    }

    public StudentResultMessage(ProtocolStudent protocolStudent) {
        super(protocolStudent.getStudent());
    }
}
