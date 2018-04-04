package ee.hois.moodle.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GradeItemsResponse {

    @JsonProperty("gradeitems")
    private List<GradeItem> gradeItems;

    public List<GradeItem> getGradeItems() {
        return gradeItems;
    }
    public void setGradeItems(List<GradeItem> gradeItems) {
        this.gradeItems = gradeItems;
    }

}
