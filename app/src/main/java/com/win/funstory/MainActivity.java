package com.win.funstory;

import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.win.funstory.adapters.MyAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private SlidingPaneLayout slidingPaneLayout;
    private NavigationView menu;
    private ActionBarDrawerToggle toggle;
    private ViewPager pager;
    private TabLayout tab;



   // private ActionBarToggle mActionBarToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        slidingPaneLayout= (SlidingPaneLayout) findViewById(R.id.slidingMenu);
        menu=(NavigationView)findViewById(R.id.menu);

        menu.setNavigationItemSelectedListener(this);
        tab= (TabLayout) findViewById(R.id.tab);
        pager= (ViewPager) findViewById(R.id.viewPager);

        List<String>  list=new ArrayList<>();
        list.add("专享");
        list.add("视频");
        list.add("纯文");
        list.add("纯图");
        list.add("精华");

        pager.setAdapter(new MyAdapter(getSupportFragmentManager(), list));
        tab.setupWithViewPager(pager);


        getSupportActionBar().setHomeButtonEnabled(true);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(toggle.onOptionsItemSelected(item)){
            return  true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item1:
                Toast.makeText(this,"点击了糗事", Toast.LENGTH_SHORT).show();
                break;
            case R.id.item2:
                Toast.makeText(this,"点击了糗有圈", Toast.LENGTH_SHORT).show();
                break;
            case R.id.item3:
                Toast.makeText(this,"点击了发现", Toast.LENGTH_SHORT).show();
                break;
            case R.id.item4:
                Toast.makeText(this,"点击了小纸条", Toast.LENGTH_SHORT).show();
                break;
            case R.id.mymine:
                Toast.makeText(this,"我的", Toast.LENGTH_SHORT).show();
                break;
            case R.id.exit:
                finish();
                break;
        }
        slidingPaneLayout.closePane();
        return true;
    }
}
