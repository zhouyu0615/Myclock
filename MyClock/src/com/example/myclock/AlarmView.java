package com.example.myclock;

import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TimePicker;

@SuppressLint("NewApi")
public class AlarmView extends LinearLayout {

	public AlarmView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
		// TODO Auto-generated constructor stub
	}

	public AlarmView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
		// TODO Auto-generated constructor stub
	}

	public AlarmView(Context context) {
		super(context);
		init();
		// TODO Auto-generated constructor stub
	}

	private Button btnAddAlarm;
	private ListView lvAlarmList;

	private ArrayAdapter<AlarmData> adapter;
	private static final String KEY_ALARM_LIST = "AlarmList";
	private AlarmManager alarmManager;

	@Override
	protected void onFinishInflate() {
		// TODO Auto-generated method stub
		super.onFinishInflate();
		lvAlarmList = (ListView) findViewById(R.id.lvAlarmList);
		btnAddAlarm = (Button) findViewById(R.id.btnAddAlarm);
		btnAddAlarm.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				addAlarm();

			}
		});
		
		adapter = new ArrayAdapter<AlarmView.AlarmData>(getContext(),
				android.R.layout.simple_list_item_1);
		readAlarmList();
		lvAlarmList.setAdapter(adapter);
		lvAlarmList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					final int position, long id) {
				// TODO Auto-generated method stub
				new AlertDialog.Builder(getContext()).setTitle("操作选项").setItems(new CharSequence[] {"删除"}, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						switch (which) {
						case 0:
							
							delteAlarm(position);
							break;
						default:
							break;
						}
						
					}
				}).setNegativeButton("取消", null).show();
				
				return true;
			}
		});
	
}


	private void init() {
		alarmManager = (AlarmManager) getContext().getSystemService(
				Context.ALARM_SERVICE);
	}

	private void addAlarm() {
		Calendar c = Calendar.getInstance();

		new TimePickerDialog(getContext(),
				new TimePickerDialog.OnTimeSetListener() {

					@Override
					public void onTimeSet(TimePicker view, int hourOfDay,
							int minute) {
						// TODO Auto-generated method stub
						Calendar calendar = Calendar.getInstance();
						calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
						calendar.set(Calendar.MINUTE, minute);
						calendar.set(Calendar.SECOND, 0);
						calendar.set(Calendar.MILLISECOND, 0);

						Calendar currentCalendar = Calendar.getInstance();

						if (calendar.getTimeInMillis() <= currentCalendar
								.getTimeInMillis()) {
							calendar.setTimeInMillis(calendar.getTimeInMillis()
									+ 24 * 60 * 60 * 1000);
						}

						AlarmData ad = new AlarmData(calendar.getTimeInMillis());
						
						alarmManager.setRepeating(alarmManager.RTC_WAKEUP, 
								calendar.getTimeInMillis(), 
								3*60*1000, 
								PendingIntent.getBroadcast(getContext(), 
								ad.getID(),
								new Intent(getContext(),AlarmReceiver.class), 0));

						// System.out.println(ad);
						adapter.add(ad);
						try {

							saveAlarmList();
							
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				}, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true)
				.show();

	}
	
	
	private void delteAlarm(int position)
	{
		AlarmData adAlarmData=adapter.getItem(position);
		adapter.remove(adAlarmData);
		
		alarmManager.cancel(PendingIntent.getBroadcast(
				getContext(), 
				adAlarmData.getID(), 
				new Intent(getContext(), AlarmReceiver.class), 0));
		saveAlarmList();		
	}
	

	private class AlarmData {
		private long time;

		private String timelable = "";
		private Calendar date;

		public AlarmData(long time) {
			this.time = time;
			date = Calendar.getInstance();
			date.setTimeInMillis(time);

			timelable = String.format("%d月%d日  %d:%d",
					date.get(Calendar.MONTH) + 1,
					date.get(Calendar.DAY_OF_MONTH),
					date.get(Calendar.HOUR_OF_DAY), date.get(Calendar.MINUTE));

		}

		public String getTimelable() {
			return timelable;
		}

		public void setTimelable(String timelable) {
			this.timelable = timelable;
		}

		@Override
		public String toString() {
			return getTimelable();
		}

		public long getTime() {
			return time;
		}	
		
		public int getID()
		{
			return (int)time/1000/60;
		}

	}

	private void saveAlarmList() {
		Editor editor = (Editor) getContext().getSharedPreferences(
				AlarmView.class.getName(), Context.MODE_PRIVATE).edit();

		StringBuffer sbBuffer = new StringBuffer();
		for (int i = 0; i < adapter.getCount(); i++) {
			sbBuffer.append(adapter.getItem(i).getTime()).append(",");
		}

		if (sbBuffer.length() > 1) {
			String content = sbBuffer.toString().substring(0,
					sbBuffer.length() - 1);
			editor.putString(KEY_ALARM_LIST, content);
			System.out.println(content);

		} else {

			editor.putString(KEY_ALARM_LIST, null);
		}
		editor.commit();

	}

	private void readAlarmList() {
		SharedPreferences spPreferences = getContext().getSharedPreferences(
				AlarmView.class.getName(), Context.MODE_PRIVATE);
		String content = spPreferences.getString(KEY_ALARM_LIST, null);

		if (content != null) {
			String[] timeStrings = content.split(",");
			for (String string : timeStrings) {
				adapter.add(new AlarmData(Long.parseLong(string)));

			}

		}

	}

}
