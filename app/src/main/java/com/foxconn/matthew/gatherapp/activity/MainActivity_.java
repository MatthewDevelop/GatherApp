package com.foxconn.matthew.gatherapp.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import com.foxconn.matthew.gatherapp.fragment.WanAndroidFragment;
import com.foxconn.matthew.gatherapp.fragment.ZhihuNewsFragment;
import com.foxconn.matthew.gatherapp.test.fragment.PagerTestFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author:Matthew
 * @date:2018/1/12
 * @email:guocheng0816@163.com
 */

public class MainActivity_ extends AppCompatActivity {

    public final String TAG = getClass().getSimpleName();
    @BindView(R.id.container)
    FrameLayout mContainer;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.nav_view)
    NavigationView mNavigationView;


    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;

    List<Fragment> mFragments;
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
        ButterKnife.bind(this);
        initView();
        init();

    }

    private void init() {
        mFragmentManager = getSupportFragmentManager();
        initFragments();
        loadFragment(0);
    }

    private void initFragments() {
        mFragments=new ArrayList<>();
        mFragments.add(new ZhihuNewsFragment());
        mFragments.add(new WanAndroidFragment());
        mFragments.add(new PagerTestFragment());
    }

    private void initView() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.home_bt);
        }

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();
                switch (id) {
                    case R.id.ZhiHu:
                        loadFragment(0);
                        break;
                    case R.id.WanAndroid:
                        loadFragment(1);
                        break;
                    case R.id.pig:
                        loadFragment(2);
                        break;
                    default:
                        break;
                }
                mDrawerLayout.closeDrawers();
                return true;
            }
        });

        mNavigationView.setCheckedItem(R.id.ZhiHu);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Data delete~", Snackbar.LENGTH_LONG).setAction("Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(MainActivity_.this, "Data restored!", Toast.LENGTH_SHORT).show();
                    }
                }).show();
            }
        });
    }

    /**
     * 加载碎片布局
     * @param index
     */
    public void loadFragment(int index){
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.container, mFragments.get(index));
        mFragmentTransaction.commit();
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

}
