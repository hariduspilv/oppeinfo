package ee.hitsa.ois.web.dto;

import java.time.LocalDate;

import ee.hitsa.ois.service.ehis.EhisTeacherExportService.ExportTeacherRequest;

public class EhisTeacherRequestDto {
    
    private String user; 
    private LocalDate from;
    private LocalDate thru;

    public static EhisTeacherRequestDto of(ExportTeacherRequest overlappedRequest) {
        EhisTeacherRequestDto dto = new EhisTeacherRequestDto();
        dto.setUser(overlappedRequest.getUser());
        dto.setFrom(overlappedRequest.getFrom());
        dto.setThru(overlappedRequest.getThru());
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

}
