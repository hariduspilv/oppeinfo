package ee.hitsa.ois.web.dto.poll;

import java.util.List;

public class AllPollResultsDto {
    
    private List<PollThemeResultDto> content;

    public List<PollThemeResultDto> getContent() {
        return content;
    }

    public void setContent(List<PollThemeResultDto> content) {
        this.content = content;
    }
}
