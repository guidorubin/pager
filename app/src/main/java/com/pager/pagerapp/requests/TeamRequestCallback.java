package com.pager.pagerapp.requests;

import com.pager.pagerapp.model.Member;

import java.util.List;

public interface TeamRequestCallback {

    void onTeamRequest(List<Member> team);
}
