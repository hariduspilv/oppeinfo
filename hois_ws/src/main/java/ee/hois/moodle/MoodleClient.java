package ee.hois.moodle;

import java.util.List;

public class MoodleClient {

    public void courseLinkPossible(Config config, Long courseId, List<String> academicianIds) {
        // TODO implementation and return type
    }

    public void enrollStudents(Config config, Long courseId, List<String> academicianIds, List<String> studentIds) {
        // TODO implementation and return type
    }

    public void genErolledUsers(Config config, Long courseId, List<String> academicianIds) {
        // TODO implementation and return type
    }

    public void getGradeItems(Config config, Long courseId, List<String> academicianIds) {
        // TODO implementation and return type
    }

    public void getGradesByItemId(Config config, List<Long> gradeItemIds, List<String> academicianIds, List<String> studentIds) {
        // TODO implementation and return type
    }

    private static String location(Config config) {
        StringBuilder url = new StringBuilder(config.getAddress());
        String path = config.getPluginPath();
        if(hasText(path)) {
            url.append(path);
        }
        String name = config.getPluginName();
        if(hasText(name)) {
            url.append(name);
        }
        return url.toString();
    }

    private static boolean hasText(String text) {
        return text != null && text.trim().length() > 0;
    }
}
