package com.jbs.general.model.response.matchesBySeriesId;

import java.util.ArrayList;
import java.util.List;

public class MatchesBySeries {
    private String matchTitle;
    private List<MatchesData> MatchesList = new ArrayList<>();

    public MatchesBySeries() {
    }

    public MatchesBySeries(String matchTitle, List<MatchesData> matchesList) {
        this.matchTitle = matchTitle;
        MatchesList = matchesList;
    }

    public String getMatchTitle() {
        return matchTitle;
    }

    public List<MatchesData> getMatchesList() {
        return MatchesList;
    }
}
