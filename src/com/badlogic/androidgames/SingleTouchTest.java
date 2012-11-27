package com.badlogic.androidgames;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TextView;

public class SingleTouchTest extends Activity implements OnTouchListener{

	//StringBuilder 可変長型文字列 Stringは長さを変更できないがコチラは出来る　というかStringでもコレが呼び出されている　文字列を弄るならコチラを使った方が早い
	StringBuilder builder = new StringBuilder();
	TextView textView;
	
	public void onCreate(Bundle savedInstanceState) {
		// TODO 自動生成されたコンストラクター・スタブ
		super.onCreate(savedInstanceState);
		textView = new TextView(this);
		textView.setText("Touch and Drag(one finger only)!");
		textView.setOnTouchListener(this);
		setContentView(textView);
	}
	
	@Override
	public boolean onTouch(View v,MotionEvent event){
		builder.setLength(0);
		switch(event.getAction()){
		case MotionEvent.ACTION_DOWN:
			builder.append("down, ");
			break;
		case MotionEvent.ACTION_MOVE:
			builder.append("move, ");
			break;
		case MotionEvent.ACTION_CANCEL:
			builder.append("cancel, ");
			break;
		case MotionEvent.ACTION_UP:
			builder.append("up, ");
			break;
		}

		builder.append(event.getX());
		builder.append(", ");
		builder.append(event.getY());
		String text = builder.toString();

		Log.d("TouchTest",text);
		textView.setText(text);
		return true;
	}
	
}
