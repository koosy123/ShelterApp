package com.cookandroid.shelterapp;

import android.app.TabActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TabHost;

public class MainActivity extends TabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TabHost tabHost = getTabHost();
        TabHost.TabSpec tabSpecMap = tabHost.newTabSpec("MAP").setIndicator("",getResources().getDrawable(R.drawable.place));
        tabSpecMap.setContent(new Intent(this, MapActivity.class));
        tabHost.addTab(tabSpecMap);
        TabHost.TabSpec tabSpecNews = tabHost.newTabSpec("News").setIndicator("",getResources().getDrawable(R.drawable.news));
        tabSpecNews.setContent(new Intent(this, NewsActivity.class));
        tabHost.addTab(tabSpecNews);
        TabHost.TabSpec tabSpecCommu = tabHost.newTabSpec("Commu").setIndicator("",getResources().getDrawable(R.drawable.community));
        tabSpecCommu.setContent(new Intent(this, ComActivity.class));
        tabHost.addTab(tabSpecCommu);
        TabHost.TabSpec tabSpecInfo = tabHost.newTabSpec("Info").setIndicator("",getResources().getDrawable(R.drawable.info));
        tabSpecInfo.setContent(new Intent(this, InfoActivity.class));
        tabHost.addTab(tabSpecInfo);
        tabHost.setCurrentTab(0);
    }
}
