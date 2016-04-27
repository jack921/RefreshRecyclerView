package com.refreshrecyclerview.jack.refreshrecyclerview;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button linear,gridlayout,staggered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);

        linear=(Button)findViewById(R.id.linearlayout);
        gridlayout=(Button)findViewById(R.id.gridlayout);
        staggered=(Button)findViewById(R.id.staggeredgrid);

        linear.setOnClickListener(this);
        gridlayout.setOnClickListener(this);
        staggered.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.fab:
                Snackbar.make(v,"Snackbar",Snackbar.LENGTH_SHORT).show();
                break;
            case R.id.linearlayout:
                startActivity(new Intent(this,LinearLayoutActivity.class));
                break;
            case R.id.gridlayout:
                startActivity(new Intent(this,GridlayoutActivity.class));
                break;
            case R.id.staggeredgrid:
                startActivity(new Intent(this,StaggeredActivity.class));
                break;
        }
    }

}

