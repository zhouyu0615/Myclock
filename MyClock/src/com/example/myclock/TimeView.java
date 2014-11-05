package com.example.myclock;

import java.util.Calendar;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author zhouyu
 *
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB) public class TimeView extends LinearLayout {

	public TimeView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public TimeView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public TimeView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
    private TextView tvTime;
	@Override
	protected void onFinishInflate() {
		// TODO Auto-generated method stub
		super.onFinishInflate();
		
		tvTime=(TextView) findViewById(R.id.tvTime);
		
		tvTime.setText("hello");
		timeHandler.sendEmptyMessage(0);	
	}
	

	@Override
	protected void onVisibilityChanged(View changedView, int visibility) {
		// TODO Auto-generated method stub
		super.onVisibilityChanged(changedView, visibility);
		
		if(visibility==getVisibility())
		{
			timeHandler.sendEmptyMessage(0);			
		}
		else
		{
			
			timeHandler.removeMessages(0);
		}
		
	}
	
	private void refreshTime()
	{
		Calendar c=Calendar.getInstance();
		
		tvTime.setText(String.format("%d:%d:%d", c.get(Calendar.HOUR_OF_DAY),
				c.get(Calendar.MINUTE),c.get(Calendar.SECOND)));
		System.out.println("refresh time");
		
	}
	
	private Handler timeHandler=new Handler(){
		
	public void handleMessage(android.os.Message msg) {
			
			refreshTime();
			if (getVisibility()==View.VISIBLE) {
				timeHandler.sendEmptyMessageDelayed(0, 1000);
			}		
		};
		
		
	};
	
	

}
