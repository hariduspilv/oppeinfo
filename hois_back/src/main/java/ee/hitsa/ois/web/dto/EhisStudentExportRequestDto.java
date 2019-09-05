package ee.hitsa.ois.web.dto;

import java.time.LocalDate;

import ee.hitsa.ois.enums.EhisStudentDataType;
import ee.hitsa.ois.service.ehis.EhisStudentService;
import ee.hitsa.ois.util.PersonUtil;

public class EhisStudentExportRequestDto {

    private String user;
    private LocalDate from;
    private LocalDate thru;
    private EhisStudentDataType type;
    
    public static EhisStudentExportRequestDto of(EhisStudentService.ExportStudentsRequest request) {
        EhisStudentExportRequestDto dto = new EhisStudentExportRequestDto();
        dto.setUser(PersonUtil.stripIdcodeFromFullnameAndIdcode(request.getUser()));
        dto.setFrom(request.getFrom());
        dto.setThru(request.getThru());
        dto.setType(request.getType());
        return dto;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public LocalDate getFrom() {
        return from;
    }

    public void setFrom(LocalDate from) {
        this.from = from;
    }

    public LocalDate getThru() {
        return thru;
    }

    public void setThru(LocalDate thru) {
        this.thru = thru;
    }

    public EhisStudentDataType getType() {
        return type;
    }

    public void setType(EhisStudentDataType type) {
        this.type = type;
    }
}
