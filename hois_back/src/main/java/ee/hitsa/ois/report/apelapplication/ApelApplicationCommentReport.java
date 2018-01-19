package ee.hitsa.ois.report.apelapplication;

import java.time.LocalDateTime;

import ee.hitsa.ois.domain.apelapplication.ApelApplicationComment;
import ee.hitsa.ois.util.PersonUtil;

public class ApelApplicationCommentReport {

    private final String insertedBy;
    private final LocalDateTime inserted;
    private final String addInfo;
    
    public ApelApplicationCommentReport(ApelApplicationComment comment) {
        insertedBy = PersonUtil.stripIdcodeFromFullnameAndIdcode(comment.getInsertedBy());
        inserted = comment.getInserted();
        addInfo = comment.getAddInfo();
    }
    
    public String getInsertedBy() {
        return insertedBy;
    }
    
    public LocalDateTime getInserted() {
        return inserted;
    }
    
    public String getAddInfo() {
        return addInfo;
    }
    
}
