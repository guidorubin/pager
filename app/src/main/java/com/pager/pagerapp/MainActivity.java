package com.pager.pagerapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import com.pager.pagerapp.adapters.ListTeamAdapter;
import com.pager.pagerapp.bo.RolesBO;
import com.pager.pagerapp.bo.SocketBO;
import com.pager.pagerapp.bo.TeamBO;
import com.pager.pagerapp.model.Member;

import org.parceler.Parcels;

import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements ListTeamAdapter.OnItemClickListener, TeamBO.Callback, SocketBO.Callback, RolesBO.Callback {

    private ProgressDialog progressBar;

    private RecyclerView recyclerView;
    private ListTeamAdapter adapter;
    private SearchView searchView;
    private MenuItem searchItem;
    private TeamBO teamBO;
    private SocketBO socketBO;
    private Map<String, String> roles;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showProgressBar();
        init();
    }

    private void init() {
        recyclerView = (RecyclerView) findViewById(R.id.teamRecycleView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new ListTeamAdapter(this, this);
        recyclerView.setAdapter(adapter);

        teamBO = new TeamBO(this);
        teamBO.listTeam();

        RolesBO rolesBO = new RolesBO();
        rolesBO.listRoles(this);


        socketBO = new SocketBO(this);
    }

    private void loadTeamList(List<Member> team) {
        adapter.setDataList(team);

    }

    @Override
    public void onItemClick(Member member) {

        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        if(roles!=null) {
            member.setRoleName(roles.get("" + member.getRoleId()));
        }

        intent.putExtra("member", Parcels.wrap(member));
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_items, menu);
        searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint("Search by Name...");
        searchView.setIconified(true);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                fetchItem(query);
                searchView.clearFocus();
                searchView.onActionViewCollapsed();
                searchView.setQuery("", true);
                MainActivity.this.setTitle(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                clearResults();
                return false;
            }
        });
        return true;
    }

    private void fetchItem(String query) {
        adapter.getFilter().filter(query);
    }

    private void clearResults() {
        fetchItem("");
        MainActivity.this.setTitle(R.string.app_name);

    }

    @Override
    public void onBackPressed() {
        if(!getTitle().equals(getString(R.string.app_name))) {
            clearResults();
        } else {
            super.onBackPressed();
        }
    }



    @Override
    protected void onResume() {
        super.onResume();
        socketBO.open();
    }

    @Override
    protected void onPause() {
        super.onPause();
        socketBO.close();
    }

    @Override
    public void newMemberUpdate(final Member newMember) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.addMember(newMember);
            }
        });

    }

    @Override
    public void statusUpdate(final String status, final String member) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.updateStatus(status, member);
            }
        });

    }

    @Override
    public void listTeam(List<Member> team) {
        hideProgressBar();
        if (team!=null) {
            loadTeamList(team);
        }

    }

    @Override
    public void getRoles(Map<String, String> roles) {
        this.roles = roles;
        adapter.updateRoles(roles);
    }

    public void showProgressBar() {
        progressBar = ProgressDialog.show(this, "", getString(R.string.loading));
    }

    public void hideProgressBar() {
        if (progressBar!=null && progressBar.isShowing()) {
            progressBar.hide();
        }
    }
}
