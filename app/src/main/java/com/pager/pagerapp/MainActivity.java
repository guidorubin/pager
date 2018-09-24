package com.pager.pagerapp;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import com.pager.pagerapp.adapters.ListTeamAdapter;
import com.pager.pagerapp.model.Member;
import com.pager.pagerapp.requests.TeamRequest;
import com.pager.pagerapp.requests.TeamRequestCallback;

import java.util.List;

public class MainActivity extends AppCompatActivity implements TeamRequestCallback, ListTeamAdapter.OnItemClickListener {

    //Hacer diccionario para los puestos...

    private RecyclerView recyclerView;
    private ListTeamAdapter adapter;
    private SearchView searchView;
    private MenuItem searchItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        recyclerView = (RecyclerView) findViewById(R.id.teamRecycleView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        TeamRequest teamRequest = new TeamRequest(this);

    }

    @Override
    public void onTeamRequest(List<Member> team) {
        if (team!=null) {

            loadTeamList(team);
            System.out.println(team.size());
        }
    }

    private void loadTeamList(List<Member> team) {


        adapter = new ListTeamAdapter(this, team, this);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onItemClick(Member member) {

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
}
