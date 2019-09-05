package ee.hitsa.ois.web.dto.poll;

public class GraphOptions {
    
    private TitleOptions title;

    public GraphOptions(TitleOptions titleOptions) {
        this.title = titleOptions;
    }
    
    public GraphOptions() {
        // default
    }

    public TitleOptions getTitle() {
        return title;
    }

    public void setTitle(TitleOptions title) {
        this.title = title;
    }
    

}
