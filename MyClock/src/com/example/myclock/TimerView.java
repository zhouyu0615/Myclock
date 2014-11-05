package com.example.myclock;

import java.util.Timer;
import java.util.TimerTask;

import android.R.integer;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

@SuppressLint("NewApi")
public class TimerView extends LinearLayout {

	public TimerView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public TimerView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public TimerView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	private EditText etHour, etMin, etSec;
	private Button btnStart, btnPause, btnResume, btnReset;

	@Override
	protected void onFinishInflate() {
		// TODO Auto-generated method stub
		super.onFinishInflate();

		btnStart = (Button) findViewById(R.id.btnStart);
		btnStart.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				StartTimer();

				btnPause.setVisibility(View.VISIBLE);
				btnStart.setVisibility(View.GONE);
				btnReset.setVisibility(View.VISIBLE);
			}
		});

		btnPause = (Button) findViewById(R.id.btnPause);
		btnPause.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				StopTimer();
				btnPause.setVisibility(GONE);
				btnReset.setVisibility(GONE);
				btnStart.setVisibility(VISIBLE);

			}
		});
		
		btnReset=(Button) findViewById(R.id.btnReset);
		btnReset.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				StopTimer();
				etHour.setText("00");
				etMin.setText("00");
				etHour.setText("00");
				
               btnStart.setVisibility(VISIBLE);                       				
				
				
			}
		});
			
			
		
		
		
		btnResume=(Button) findViewById(R.id.btnResume);
		

		etHour = (EditText) findViewById(R.id.etHour);
		etMin = (EditText) findViewById(R.id.etMin);
		etSec = (EditText) findViewById(R.id.etSec);

		etHour.setText("00");
		etHour.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				if (!TextUtils.isEmpty(s)) {
					int value = Integer.parseInt(s.toString());

					if (value > 59) {

						etHour.setText("59");
					} else if (value < 0) {

						etHour.setText("00");
					}
					checkToEnableBtnStart();

				}

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});
		etMin.setText("00");
		etMin.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				if (!TextUtils.isEmpty(s)) {
					int value = Integer.parseInt(s.toString());
					
					if (value > 59) {
						
						etMin.setText("59");
					} else if (value < 0) {
						
						etMin.setText("00");
					}
					checkToEnableBtnStart();
					
				}
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
		etSec.setText("00");
		etSec.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				if (!TextUtils.isEmpty(s)) {
					int value = Integer.parseInt(s.toString());
					
					if (value > 59) {
						
						etSec.setText("59");
					} else if (value < 0) {
						
						etSec.setText("00");
					}
					checkToEnableBtnStart();
					
				}
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});

	}

	private TimerTask  timerTask=null;
	private Timer timer= new Timer();
	
	private final int MSG_WHAT_TIME_TICK=1;
	private final int MSG_WHAT_TIME_IS_UP=2;
	
	private int allTimeCount=0;
	
	private void StartTimer() {
		if(timerTask==null)
		{
			allTimeCount=Integer.parseInt(etHour.getText().toString())*60*60+
					     Integer.parseInt(etMin.getText().toString())*60+
					     Integer.parseInt(etSec.getText().toString());
			
			 timerTask=new TimerTask() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					allTimeCount--;
					
					handler.sendEmptyMessage(MSG_WHAT_TIME_TICK);
					if (allTimeCount<=0) {				
						handler.sendEmptyMessage(MSG_WHAT_TIME_IS_UP);
						StopTimer();
					}
					
				}
			};
			
			timer.schedule(timerTask, 1000,1000);
			
		}
		

	}

	private void StopTimer() {
		if(timerTask!=null)
		{
			timerTask.cancel();
			timerTask=null;
			
			
		}

	}
	
	
	private Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg)
		{
			switch (msg.what) {
			case MSG_WHAT_TIME_TICK:
				int hour =allTimeCount/60/60;
				int min =(allTimeCount/60)%60;
				int sec=allTimeCount%60;
				
				etHour.setText(hour+"");
				etMin.setText(min+"");
				etSec.setText(sec+"");
				
				
				break;
			case MSG_WHAT_TIME_IS_UP:
				
				new AlertDialog.Builder(getContext()).setTitle("time is up").setMessage("time is up").setNegativeButton("取消", null).show();
				
				btnStart.setVisibility(VISIBLE);
				
				
				break;
			default:
				break;
			}
			
		}
		
		
	};
	
	private void checkToEnableBtnStart()
	{
		btnStart.setEnabled((!TextUtils.isEmpty(etHour.getText())&&Integer.parseInt(etHour.getText().toString())>0)
				||(!TextUtils.isEmpty(etMin.getText())&&Integer.parseInt(etHour.getText().toString())>0)
				||(!TextUtils.isEmpty(etSec.getText())&&Integer.parseInt(etSec.getText().toString())>0));
		
	}

}
