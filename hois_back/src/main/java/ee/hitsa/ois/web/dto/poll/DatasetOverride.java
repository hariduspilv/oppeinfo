package ee.hitsa.ois.web.dto.poll;

import java.math.BigDecimal;
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
    
    public DatasetOverride(List<QuestionResponsePairDto> text, Long sum, BigDecimal averageWeight) {
        this.pointBorderColor = text;
        this.sum = sum;
        this.average = averageWeight;
    }
    
    private List<QuestionResponsePairDto> pointBorderColor;
    private Long sum;
    private BigDecimal average;

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

    public BigDecimal getAverage() {
        return average;
    }

    public void setAverage(BigDecimal average) {
        this.average = average;
    }
}
