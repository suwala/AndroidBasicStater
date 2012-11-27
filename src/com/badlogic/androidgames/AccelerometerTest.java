package com.badlogic.androidgames;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class AccelerometerTest extends Activity implements SensorEventListener{

	TextView textView;
	StringBuilder builder = new StringBuilder();
	
	@Override
	public void onCreate(Bundle savedInstancesState){
		super.onCreate(savedInstancesState);
		textView = new TextView(this);
		setContentView(textView);
		
		SensorManager manager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
		//センサーを搭載しているかのチェック
		if (manager.getSensorList(Sensor.TYPE_ACCELEROMETER).size() == 0)
			textView.setText("No acceleromter installed");
		else{
			Sensor acceleremoeter = manager.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0);
			//ゲームタイプセンサーのリスナーの取得
			if(!manager.registerListener(this, acceleremoeter,SensorManager.SENSOR_DELAY_GAME))
				textView.setText("Couldn't register sensor listener");
			
		}
		
	}
	
	//センサーの精度が変化したとき？
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	//センサーが情報を取得
	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO 自動生成されたメソッド・スタブ
		builder.setLength(0);
		builder.append("x: ");
		builder.append(event.values[0]);
		builder.append(", y: ");
		builder.append(event.values[1]);
		builder.append(", z: ");
		builder.append(event.values[2]);
		textView.setText(builder.toString());
		
	}
	
	
	
	

}
