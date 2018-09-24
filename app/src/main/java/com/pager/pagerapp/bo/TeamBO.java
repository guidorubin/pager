package com.pager.pagerapp.bo;

import com.pager.pagerapp.model.Member;
import com.pager.pagerapp.requests.TeamRequest;
import com.pager.pagerapp.requests.TeamRequestCallback;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeamBO implements TeamRequestCallback, RolesBO.Callback {

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

        RolesBO rolesBO = new RolesBO();
        rolesBO.listRoles(this);

    }

    public List<Member> updateTeamWithRoles(Map<String, String> roles) {
        if (team!=null) {
            for (Member member : team) {
                member.setRoleName(roles.get(member.getRoleId()));
            }
        }
        return team;
    }

    @Override
    public void getRoles(Map<String, String> roles) {
        if (team!=null) {
            for (Member member : team) {
                System.out.println("role " + roles.get("" + member.getRoleId()));
                member.setRoleName(roles.get("" + member.getRoleId()));
            }
        }
        if(callback!=null) {
            callback.listTeam(team);
        }
    }

    public interface Callback {
        void listTeam(List<Member> team);
    }

}
