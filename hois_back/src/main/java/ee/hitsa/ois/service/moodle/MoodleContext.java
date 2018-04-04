package ee.hitsa.ois.service.moodle;

import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hois.moodle.LogContext;

public class MoodleContext {
    
    private final HoisUserDetails user;
    private String ipAddress;
    private Long journalId;
    private Long subjectStudyPeriodId;
    private final LogContext log = new LogContext();
    
    public MoodleContext(HoisUserDetails user) {
        this.user = user;
    }
    
    public HoisUserDetails getUser() {
        return user;
    }
    
    public String getIpAddress() {
        return ipAddress;
    }
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
    
    public Long getJournalId() {
        return journalId;
    }
    public void setJournalId(Long journalId) {
        this.journalId = journalId;
    }
    
    public Long getSubjectStudyPeriodId() {
        return subjectStudyPeriodId;
    }
    public void setSubjectStudyPeriodId(Long subjectStudyPeriodId) {
        this.subjectStudyPeriodId = subjectStudyPeriodId;
    }

    public LogContext getLog() {
        return log;
    }
    
}
