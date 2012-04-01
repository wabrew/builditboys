package com.builditboys.android.nokneadbread;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

public class TabWidget extends TabActivity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		TabHost tabHost = getTabHost(); // The activity TabHost
		TabHost.TabSpec spec; // Resusable TabSpec for each tab
		Intent intent; // Reusable Intent for each tab

		intent = new Intent().setClass(this, PlannerActivity.class);
		spec = tabHost.newTabSpec("planner").setIndicator("Plan")
				.setContent(intent);
		tabHost.addTab(spec);

		intent = new Intent().setClass(this, TimerActivity.class);
		spec = tabHost.newTabSpec("timer").setIndicator("Timer")
				.setContent(intent);
		tabHost.addTab(spec);

		tabHost.setCurrentTab(0);
	}
}
