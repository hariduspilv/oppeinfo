package ee.hitsa.ois.service.poll;

import java.util.ArrayList;
import java.util.List;

import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.poll.GraphInfoDto;

public class GraphTeacherDto {
    
    private AutocompleteResult teacher;
    private List<GraphInfoDto> graph = new ArrayList<>();
    
    public AutocompleteResult getTeacher() {
        return teacher;
    }
    public void setTeacher(AutocompleteResult teacher) {
        this.teacher = teacher;
    }
    public List<GraphInfoDto> getGraph() {
        return graph;
    }
    public void setGraph(List<GraphInfoDto> graph) {
        this.graph = graph;
    }
    
}
