package ee.hitsa.ois.web.commandobject;

public class StudentDeclarationCommand extends UsersSearchCommand {

    private Boolean nextPeriod;
    private Long period;

    public Boolean getNextPeriod() {
        return nextPeriod;
    }

    public void setNextPeriod(Boolean nextPeriod) {
        this.nextPeriod = nextPeriod;
    }

    public Long getPeriod() {
        return period;
    }

    public void setPeriod(Long period) {
        this.period = period;
    }
}
