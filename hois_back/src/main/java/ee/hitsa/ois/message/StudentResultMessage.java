package ee.hitsa.ois.message;

import ee.hitsa.ois.domain.protocol.Protocol;
import ee.hitsa.ois.domain.protocol.ProtocolHdata;
import ee.hitsa.ois.domain.protocol.ProtocolStudent;
import ee.hitsa.ois.domain.protocol.ProtocolVdata;
import ee.hitsa.ois.domain.subject.Subject;

public class StudentResultMessage extends StudentMessage {

    private final String subjectCode;
    private final String subjectName;

    public StudentResultMessage() {
        subjectCode = null;
        subjectName = null;
    }

    public StudentResultMessage(ProtocolStudent protocolStudent) {
        super(protocolStudent.getStudent());

        Protocol protocol = protocolStudent.getProtocol();
        if(Boolean.TRUE.equals(protocol.getIsVocational())) {
            ProtocolVdata data = protocol.getProtocolVdata();
            subjectCode = "";
            subjectName = data.getCurriculumVersionOccupationModule().getCurriculumModule().getNameEt();
        } else {
            ProtocolHdata data = protocol.getProtocolHdata();
            Subject subject = Boolean.TRUE.equals(protocol.getIsFinalThesis()) ? data.getFinalSubject() : data.getSubjectStudyPeriod().getSubject();
            subjectCode = subject.getCode();
            subjectName = subject.getNameEt();
        }
    }

    public String getOppeaineKood() {
        return subjectCode;
    }

    public String getOppeaineNimetus() {
        return subjectName;
    }
}
