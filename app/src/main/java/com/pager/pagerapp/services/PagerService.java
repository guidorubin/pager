package com.pager.pagerapp.services;

import com.pager.pagerapp.model.Member;
import com.pager.pagerapp.model.Role;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PagerService {

    @GET("roles")
    Call<ArrayList<Role>> listRoles();

    @GET("team")
    Call<List<Member>> listMembers();

}
