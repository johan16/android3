package com.example.titulares;

import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;

public class SplashActivity extends Activity{
	//tiempo de duracion
	private long delay = 3000;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.layout_splash);
		
		TimerTask task = new TimerTask(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				// finalizar el proceso actual.
				finish();
				
				//lanzar la ventana principal
												// ( DONDE ESTAMOS , DONDE QUEREMOS IR );
				Intent i = new Intent().setClass(SplashActivity.this, PrincipalActivity.class);
				startActivity(i);
				
			}
			
		};
		
		
		Timer timer = new Timer();
		timer.schedule(task, delay);
		
	}
}
