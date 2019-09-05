package ee.hitsa.ois.web.dto.poll;

import java.util.List;

public class DatasetOverride {
    
    /**
     *  This property is used for additional data
     * @param text
     */
    public DatasetOverride(List<QuestionResponsePairDto> text, Long sum) {
        this.pointBorderColor = text;
        this.sum = sum;
    }
    
    private List<QuestionResponsePairDto> pointBorderColor;
    private Long sum;

    public List<QuestionResponsePairDto> getPointBorderColor() {
        return pointBorderColor;
    }

    public void setPointBorderColor(List<QuestionResponsePairDto> pointBorderColor) {
        this.pointBorderColor = pointBorderColor;
    }

    public Long getSum() {
        return sum;
    }

    public void setSum(Long sum) {
        this.sum = sum;
    }
}
