package com.pager.pagerapp.bo;

import com.pager.pagerapp.model.Role;
import com.pager.pagerapp.requests.RolesRequest;
import com.pager.pagerapp.requests.RolesRequestCallback;

import java.util.HashMap;
import java.util.Map;

public class RolesBO implements RolesRequestCallback {

    private Callback callback;

    public RolesBO () {
    }
    public void listRoles(Callback callback) {
        this.callback = callback;

        RolesRequest rolesRequest = new RolesRequest(this);
        rolesRequest.execute();

    }
    @Override
    public void onRolesRequest(Map<String, String> roles) {
        callback.getRoles(roles);

    }

    public interface Callback {
        void getRoles(Map<String, String> roles);
    }

}
