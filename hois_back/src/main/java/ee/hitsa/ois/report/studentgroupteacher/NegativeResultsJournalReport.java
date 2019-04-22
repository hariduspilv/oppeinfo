package ee.hitsa.ois.report.studentgroupteacher;

import java.util.List;

public class NegativeResultsJournalReport {

    private String name;
    private List<ResultReport> results;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ResultReport> getResults() {
        return results;
    }

    public void setResults(List<ResultReport> results) {
        this.results = results;
    }

}
