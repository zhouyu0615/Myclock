package com.example.myclock;


import android.R.anim;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

@SuppressLint({ "NewApi", "HandlerLeak" }) public class StopWatch extends LinearLayout {

	public StopWatch(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public StopWatch(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public StopWatch(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	
	private TextView tvStopWatch;
	private Button btnStartWatch,btnResetWatch;
	
	private int TimeCount10Ms= 0;
	
    @Override
    protected void onFinishInflate() {
    	// TODO Auto-generated method stub
    	super.onFinishInflate();
    	
    	tvStopWatch=(TextView) findViewById(R.id.tvStopTime);
    	
    	tvStopWatch.setText(String.format("%d:%d:%d", 0,0,0));
    	
    	btnStartWatch=(Button) findViewById(R.id.btnStartWatch);
    	btnStartWatch.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				System.out.println("你按下了："+btnStartWatch.getText());
				if(btnStartWatch.getText().equals("Start"))
				{
					timeHandler.sendEmptyMessage(0);
					btnStartWatch.setText("Stop");										
				}
				else 
				{
					timeHandler.removeMessages(0);
					btnStartWatch.setText("Start");
				}

			}	
			
		});
    	
    	
    	btnResetWatch=(Button) findViewById(R.id.btnResetWatch);   	
    	btnResetWatch.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				TimeCount10Ms=0;
				ShowTime(); 
				
				timeHandler.removeMessages(0);
			}
		});
    	
    	
    }   
    Handler timeHandler=new Handler()
    {
    	public void handleMessage(android.os.Message msg)
    	{
    		TimeCount10Ms++;
    		timeHandler.sendEmptyMessageDelayed(0, 10);
    		
    		ShowTime(); 
    		
    	}
    	
    	
    };
    

    private void ShowTime()
    {
		tvStopWatch.setText(String.format("%d:%d:%d", TimeCount10Ms/100/60,
				TimeCount10Ms/100%60,
				TimeCount10Ms%100));
    	
    }; 
	

}
