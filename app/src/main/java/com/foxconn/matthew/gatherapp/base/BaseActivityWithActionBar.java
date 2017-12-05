package com.foxconn.matthew.gatherapp.base;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.foxconn.matthew.gatherapp.R;

/**
 * Created by Matthew on 2017/11/21.
 */

public abstract class BaseActivityWithActionBar extends AppCompatActivity {

    public final String TAG = getClass().getSimpleName();

    FrameLayout mContainer;
    DrawerLayout mDrawerLayout;
    FloatingActionButton fab;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //使状态栏沉浸
        /*if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }*/
        setContentView(R.layout.activity_base);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.home_bt);
        }
        mDrawerLayout=findViewById(R.id.drawer_layout);
        navigationView.setCheckedItem(R.id.ZhiHu);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
        fab=findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(MainActivity.this, "Morning~", Toast.LENGTH_SHORT).show();
                Snackbar.make(view, "Data delete~", Snackbar.LENGTH_LONG).setAction("Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(BaseActivityWithActionBar.this, "Data restored!", Toast.LENGTH_SHORT).show();
                    }
                }).show();
            }
        });
        mContainer=findViewById(R.id.container);
        mContainer.addView(View.inflate(this, getContentViewResId(), null));
        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.elephant:
                break;
            case R.id.owl:
                Toast.makeText(this, "You click the owl~", Toast.LENGTH_SHORT).show();
                break;
            case R.id.wolf:
                Toast.makeText(this, "You click the wolf ~", Toast.LENGTH_SHORT).show();
                break;
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
                break;
        }
        return true;
    }

    protected abstract int getContentViewResId();

    protected abstract void init ();

}
