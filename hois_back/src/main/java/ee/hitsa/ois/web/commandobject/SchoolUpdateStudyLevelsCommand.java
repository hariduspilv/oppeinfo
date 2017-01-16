package ee.hitsa.ois.web.commandobject;

import java.util.List;

public class SchoolUpdateStudyLevelsCommand extends VersionedCommand {

    private List<String> studyLevels;

    public List<String> getStudyLevels() {
        return studyLevels;
    }

    public void setStudyLevels(List<String> studyLevels) {
        this.studyLevels = studyLevels;
    }
}
