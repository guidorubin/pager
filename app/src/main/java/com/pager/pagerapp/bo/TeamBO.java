package com.pager.pagerapp.bo;

import com.pager.pagerapp.model.Member;
import com.pager.pagerapp.requests.TeamRequest;
import com.pager.pagerapp.requests.TeamRequestCallback;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeamBO implements TeamRequestCallback {

    private Callback callback;
    private List<Member> team;

    public TeamBO(Callback callback) {
        this.callback = callback;
    }

    public void listTeam() {
        TeamRequest teamRequest = new TeamRequest (this);
        teamRequest.execute();
    }

    @Override
    public void onTeamRequest(List<Member> team) {
        this.team = team;
        callback.listTeam(team);
    }

    public interface Callback {
        void listTeam(List<Member> team);
    }

}
