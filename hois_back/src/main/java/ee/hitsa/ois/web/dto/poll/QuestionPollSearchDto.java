package ee.hitsa.ois.web.dto.poll;

public class QuestionPollSearchDto extends PollSearchDto {
    
    private Boolean isThemePageable;

    public Boolean getIsThemePageable() {
        return isThemePageable;
    }

    public void setIsThemePageable(Boolean isThemePageable) {
        this.isThemePageable = isThemePageable;
    }
    
}
