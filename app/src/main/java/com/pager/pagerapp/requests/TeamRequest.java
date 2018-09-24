package com.pager.pagerapp.requests;

import com.pager.pagerapp.model.Member;
import com.pager.pagerapp.services.PagerService;
import com.pager.pagerapp.services.RetrofitInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeamRequest implements Executable {

    private TeamRequestCallback teamRequestCallback;

     public TeamRequest(final TeamRequestCallback teamRequestCallback) {
        this.teamRequestCallback = teamRequestCallback;
    }

    @Override
    public void execute() {
        PagerService service = RetrofitInstance.getRetrofitInstance().create(PagerService.class);

        Call<List<Member>> call = service.listMembers();
        call.enqueue(new Callback<List<Member>>() {
            @Override
            public void onResponse(Call<List<Member>> call, Response<List<Member>> response) {
                teamRequestCallback.onTeamRequest(response.body());
            }

            @Override
            public void onFailure(Call<List<Member>> call, Throwable t) {
                teamRequestCallback.onTeamRequest(null);
            }
        });

    }
}

