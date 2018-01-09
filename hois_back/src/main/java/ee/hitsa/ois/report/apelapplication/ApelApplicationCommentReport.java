package ee.hitsa.ois.report.apelapplication;

import java.time.LocalDateTime;

import ee.hitsa.ois.domain.apelapplication.ApelApplicationComment;

public class ApelApplicationCommentReport {

    private final String insertedBy;
    private final LocalDateTime inserted;
    private final String addInfo;
    
    public ApelApplicationCommentReport(ApelApplicationComment comment) {
        insertedBy = comment.getInsertedBy().substring(0, comment.getInsertedBy().indexOf("("));
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
