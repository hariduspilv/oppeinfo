package ee.hitsa.ois.web.dto.student;

import java.time.LocalDate;

public class StudentDormitoryHistoryDto {

    private LocalDate date;
    private String dormitory;

    public StudentDormitoryHistoryDto(LocalDate date, String dormitory) {
        this.date = date;
        this.dormitory = dormitory;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDormitory() {
        return dormitory;
    }

    public void setDormitory(String dormitory) {
        this.dormitory = dormitory;
    }

}
