package com.badlogic.androidgames;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class LifeCycleTest extends Activity {
	
	StringBuilder builder = new StringBuilder();
	TextView textView;
	
	private void log(String text){
		Log.d("LifeCycleTest",text);
		builder.append(text);
		builder.append('\n');
		textView.setText(builder.toString());
	}
	
	//一度しか呼ばれないので主にセットアップ
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		textView = new TextView(this);
		textView.setText(builder.toString());
		setContentView(textView);
		log("created");		
	}
	
	//再始動のたびに呼ばれる
	@Override
	public void onResume(){
		super.onResume();
		log("resumed");
	}
	
	//停止時に必ず呼ばれる onStop()などは呼ばれない場合あり
	@Override
	public void onPause(){
		super.onPause();
		log("paused");
		//isFinishing()でActivityが終了されるかどうかが分かる
		if(isFinishing()){
			log("finishing");
		}
	}

}
