package com.jenisk.realmcrudoprations;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jenisk.realmcrudoprations.adapter.DirectoryListAdapter;
import com.jenisk.realmcrudoprations.database.RealmHelper;
import com.jenisk.realmcrudoprations.model.DirectoryModel;
import com.jenisk.realmcrudoprations.utils.SeparatorDecoration;

import java.util.ArrayList;

/**
 * Created by Jenis Kasundra on 03/05/2018.
 */

public class MainActivity extends AppCompatActivity {
    FloatingActionButton fabAddUser;
    static ArrayList<DirectoryModel> directoryModelArrayList;
    DirectoryListAdapter directoryListAdapter;
    private RecyclerView rvDirectory;
    private RealmHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        bindView();
        addListner();
        setRecyclerView();
    }

    private void bindView() {
        rvDirectory = (RecyclerView) findViewById(R.id.rvDirectory);
        rvDirectory.setLayoutManager(new LinearLayoutManager(this));
        SeparatorDecoration decoration = new SeparatorDecoration(this, Color.GRAY, 1.5f);
        rvDirectory.addItemDecoration(decoration);
        fabAddUser = (FloatingActionButton) findViewById(R.id.fabAddUser);
    }

    private void init() {
        helper = new RealmHelper(this);
        directoryModelArrayList = new ArrayList<DirectoryModel>();

    }

    private void addListner() {
        fabAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddDirectoryActivity.class));
            }
        });
    }


    /**
     * set recyclerview with try get data from realm
     */
    public void setRecyclerView(){
        try{
            directoryModelArrayList = helper.findAllDirectory();
        }catch (Exception e){
            e.printStackTrace();
        }
      directoryListAdapter = new DirectoryListAdapter(MainActivity.this,directoryModelArrayList);
        rvDirectory.setAdapter(directoryListAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setRecyclerView();
    }

}
