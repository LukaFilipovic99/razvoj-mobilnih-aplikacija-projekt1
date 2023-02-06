package filipovic.youtube_api_demo_app.dto;

import java.util.List;

public class Response {

    private List<Result> results;

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }
}
