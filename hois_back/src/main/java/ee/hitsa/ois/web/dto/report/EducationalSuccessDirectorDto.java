package ee.hitsa.ois.web.dto.report;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsBoolean;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;
import static ee.hitsa.ois.util.JpaQueryUtil.getValue;

public class EducationalSuccessDirectorDto {
    
    private String header; // to be translated in fronend
    private Object data;
    private Boolean bold; // to be made bold in frontend
    
    public EducationalSuccessDirectorDto(Object r) {
        this.header = resultAsString(r, 0);
        this.data = getValue(r, 1);
        this.bold = resultAsBoolean(r, 2);
    }
    public String getHeader() {
        return header;
    }
    public void setHeader(String header) {
        this.header = header;
    }
    public Object getData() {
        return data;
    }
    public void setData(Object data) {
        this.data = data;
    }
    public Boolean getBold() {
        return bold;
    }
    public void setBold(Boolean bold) {
        this.bold = bold;
    }

}
