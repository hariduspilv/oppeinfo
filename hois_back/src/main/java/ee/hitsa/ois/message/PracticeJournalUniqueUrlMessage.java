package ee.hitsa.ois.message;

import ee.hitsa.ois.domain.student.Student;

public class PracticeJournalUniqueUrlMessage extends StudentMessage {

    private final String url;

    public PracticeJournalUniqueUrlMessage() {
        url = null;
    }

    public PracticeJournalUniqueUrlMessage(Student student, String url) {
        super(student);

        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
