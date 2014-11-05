package com.example.myclock;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;


public class MyClock extends ActionBarActivity {

	
	
	private TabHost tabhost; 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_clock);
        
        tabhost=(TabHost)findViewById(android.R.id.tabhost);
        
        tabhost.setup();
        
        tabhost.addTab(tabhost.newTabSpec("tabTime").setIndicator("时间").setContent(R.id.tabTime)); 
        tabhost.addTab(tabhost.newTabSpec("tabAlarm").setIndicator("闹钟").setContent(R.id.tabAlarm)); 
        tabhost.addTab(tabhost.newTabSpec("tabTimer").setIndicator("计时器").setContent(R.id.tabTimer)); 
        tabhost.addTab(tabhost.newTabSpec("tabStopTime").setIndicator("秒表").setContent(R.id.tabStopWatch)); 
               
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my_clock, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
