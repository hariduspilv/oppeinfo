package ee.hitsa.ois.web.commandobject.finalexamprotocol;

import ee.hitsa.ois.web.commandobject.ModuleProtocolSearchCommand;

public class FinalExamVocationalProtocolSearchCommand extends ModuleProtocolSearchCommand {

    private Long teacher;
    
    public Long getTeacher() {
        return teacher;
    }
    
    public void setTeacher(Long teacher) {
        this.teacher = teacher;
    }
    
}
