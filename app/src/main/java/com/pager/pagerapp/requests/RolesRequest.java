package com.pager.pagerapp.requests;

import com.pager.pagerapp.model.Role;
import com.pager.pagerapp.services.PagerService;
import com.pager.pagerapp.services.RetrofitInstance;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RolesRequest implements Executable {

    private RolesRequestCallback rolesRequestCallback;

     public RolesRequest(RolesRequestCallback rolesRequestCallback) {
        this.rolesRequestCallback = rolesRequestCallback;
    }

    @Override
    public void execute() {
        PagerService service = RetrofitInstance.getRetrofitInstance().create(PagerService.class);

        Call<Map<String, String>> call = service.listRoles();
        call.enqueue(new Callback<Map<String, String>>() {

            @Override
            public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                rolesRequestCallback.onRolesRequest(response.body());
            }

            @Override
            public void onFailure(Call<Map<String, String>> call, Throwable t) {
                rolesRequestCallback.onRolesRequest(null);
            }
        });

    }
}

