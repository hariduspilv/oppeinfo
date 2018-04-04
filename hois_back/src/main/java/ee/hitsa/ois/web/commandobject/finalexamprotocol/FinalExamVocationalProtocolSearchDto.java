package ee.hitsa.ois.web.commandobject.finalexamprotocol;

import ee.hitsa.ois.web.dto.ModuleProtocolSearchDto;

public class FinalExamVocationalProtocolSearchDto extends ModuleProtocolSearchDto {

    private String teacher;
    
    public String getTeacher() {
        return teacher;
    }
    
    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }
    
}
