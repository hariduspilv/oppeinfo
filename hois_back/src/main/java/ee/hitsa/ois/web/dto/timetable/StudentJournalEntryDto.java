package ee.hitsa.ois.web.dto.timetable;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import ee.hitsa.ois.util.StreamUtil;

public class StudentJournalEntryDto {
    private final Long id;
    private final String entryType;
    private final LocalDate entryDate;
    private final String content;
    private final String gradeValue;
    private final LocalDateTime gradeInserted;
    private final String addInfo;
    private final List<StudentJournalEntryPreviousResultDto> previousResults;
    private final String homework;
    private final LocalDate homeworkDueDate;
    
    public StudentJournalEntryDto(Long id, String entryType, LocalDate entryDate, String content, String gradeValue,
            LocalDateTime gradeInserted, String addInfo, String previousResults, String homework,
            LocalDate homeworkDueDate) {
        this.id = id;
        this.entryType = entryType;
        this.entryDate = entryDate;
        this.content = content;
        this.gradeValue = gradeValue;
        this.gradeInserted = gradeInserted;
        this.addInfo = addInfo;
        this.homework = homework;
        this.homeworkDueDate = homeworkDueDate;
        if (previousResults != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            this.previousResults = StreamUtil
                    .toMappedList(r -> new StudentJournalEntryPreviousResultDto(resultAsString(r.split("/"), 0),
                            LocalDateTime.parse(r.split("/")[1].substring(0, 19), formatter)), Arrays.stream(previousResults.split(",")));
            this.previousResults.sort(Comparator.comparing(StudentJournalEntryPreviousResultDto::getGradeInserted).reversed());
        } else {
            this.previousResults = null;
        }
    }

    public Long getId() {
        return id;
    }

    public String getEntryType() {
        return entryType;
    }

    public LocalDate getEntryDate() {
        return entryDate;
    }

    public String getContent() {
        return content;
    }

    public String getGradeValue() {
        return gradeValue;
    }

    public LocalDateTime getGradeInserted() {
        return gradeInserted;
    }
    
    public String getAddInfo() {
        return addInfo;
    }

    public List<StudentJournalEntryPreviousResultDto> getPreviousResults() {
        return previousResults;
    }

    public String getHomework() {
        return homework;
    }

    public LocalDate getHomeworkDueDate() {
        return homeworkDueDate;
    }
    
}
