package ee.hitsa.ois.report.teacherdetailload;

public class PeriodDto {

    private Long index;
    private String name;
    private Long nr;
    private Long colspan;
    private Boolean studyPeriod;
    private Boolean week;
    private Boolean month;
    private Boolean total;

    public Long getIndex() {
        return index;
    }

    public void setIndex(Long index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getNr() {
        return nr;
    }

    public void setNr(Long nr) {
        this.nr = nr;
    }

    public Long getColspan() {
        return colspan;
    }

    public void setColspan(Long colspan) {
        this.colspan = colspan;
    }

    public Boolean getStudyPeriod() {
        return studyPeriod;
    }

    public void setStudyPeriod(Boolean studyPeriod) {
        this.studyPeriod = studyPeriod;
    }

    public Boolean getWeek() {
        return week;
    }

    public void setWeek(Boolean week) {
        this.week = week;
    }

    public Boolean getMonth() {
        return month;
    }

    public void setMonth(Boolean month) {
        this.month = month;
    }

    public Boolean getTotal() {
        return total;
    }

    public void setTotal(Boolean total) {
        this.total = total;
    }

}
