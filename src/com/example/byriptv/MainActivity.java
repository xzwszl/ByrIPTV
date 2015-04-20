package com.example.byriptv;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends ListActivity {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_main);
		init();
	}
	
	private void init() {
		
		
		getListView().setAdapter(new MyAdapter(getApplicationContext(),getResources().getColor(android.R.color.black),20));
		getListView().setDivider(getResources().getDrawable(android.R.color.white));
		getListView().setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				if (Utils.isWifiOpen(getApplicationContext())) {
					Intent intent = new Intent(MainActivity.this,PlayActivity.class);
					intent.putExtra("position", position);
					startActivity(intent);
				} else 
					Utils.displayMessage(getApplicationContext(), "«Î¡¨Ω”WiFi~~");
							
			}
			
		});
	}
	
}
