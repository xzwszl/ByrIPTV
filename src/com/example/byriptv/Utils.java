package com.example.byriptv;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;
import android.widget.Toast;

public class Utils {
	
	public static boolean isWifiOpen(Context context) {
		
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		
		State wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
		
		if (wifi == State.CONNECTED || wifi == State.CONNECTING)
			return true;
		
		return false;
	}
	
	public static void displayMessage(Context context, String msg) {
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}
}
