package ee.hitsa.ois.web.dto.juhan;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import ee.hitsa.ois.web.dto.BusyTimeDto;

import java.util.List;

@JsonInclude(Include.NON_NULL)
public class JuhanRoomDto {

    private Long id;
    private String code;
    private String name;
    private Boolean isBusy;
    private List<BusyTimeDto> busyTimes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIsBusy() {
        return isBusy;
    }

    public void setIsBusy(Boolean isBusy) {
        this.isBusy = isBusy;
    }

    public List<BusyTimeDto> getBusyTimes() {
        return busyTimes;
    }

    public void setBusyTimes(List<BusyTimeDto> busyTimes) {
        this.busyTimes = busyTimes;
    }
}
