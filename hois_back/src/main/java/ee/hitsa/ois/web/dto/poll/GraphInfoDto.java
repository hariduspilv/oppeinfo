package ee.hitsa.ois.web.dto.poll;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import ee.hitsa.ois.web.dto.AutocompleteResult;

public class GraphInfoDto {
    
    private List<AutocompleteResult> labels = new ArrayList<>();
    private List<DatasetOverride> labelOverride = new ArrayList<>();
    private List<List<BigDecimal>> data = new ArrayList<>();
    private GraphOptions options;
    private Long max;
    
    public GraphInfoDto() {
        // need 2 rows of data for conclusions and personal answers
        this.data.add(new ArrayList<>());
    }
    
    public List<AutocompleteResult> getLabels() {
        return labels;
    }
    public void setLabels(List<AutocompleteResult> labels) {
        this.labels = labels;
    }
    public List<List<BigDecimal>> getData() {
        return data;
    }
    public void setData(List<List<BigDecimal>> data) {
        this.data = data;
    }
    public List<DatasetOverride> getLabelOverride() {
        return labelOverride;
    }
    public void setLabelOverride(List<DatasetOverride> labelOverride) {
        this.labelOverride = labelOverride;
    }
    public GraphOptions getOptions() {
        return options;
    }
    public void setOptions(GraphOptions options) {
        this.options = options;
    }
    public Long getMax() {
        return max;
    }
    public void setMax(Long max) {
        this.max = max;
    }
}
