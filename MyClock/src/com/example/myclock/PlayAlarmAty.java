package com.example.myclock;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore.Audio.Media;
import android.view.View;
import android.widget.Button;

public class PlayAlarmAty extends Activity {

	private Button cancelButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play_alarm);
		
		cancelButton=(Button) findViewById(R.id.Cancelbtn);
		cancelButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub				
				mPlayer.stop();
				
				Intent intent=new Intent();
				intent.setClass(PlayAlarmAty.this, MyClock.class);
				startActivity(intent);
							
			}
		});		
		mPlayer = MediaPlayer.create(this, R.raw.ring);
		
		mPlayer.start();
		
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mPlayer.stop();
		
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
		mPlayer.stop();
		mPlayer.release();
	
	}
	private MediaPlayer mPlayer;
}
