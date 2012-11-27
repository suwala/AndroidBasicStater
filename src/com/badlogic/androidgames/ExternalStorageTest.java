package com.badlogic.androidgames;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.widget.TextView;

public class ExternalStorageTest extends Activity {
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		
		super.onCreate(savedInstanceState);
		TextView textView = new TextView(this);
		setContentView(textView);
		
		//ストレージが存在してるかチェック
		String state = Environment.getExternalStorageState();
		if(!state.equals(Environment.MEDIA_MOUNTED)){
			//ないよ！
			textView.setText("No external storage mounted");
		}else{
			File externalDir = Environment.getExternalStorageDirectory();
			//ファイルの設定
			File textFile = new File(externalDir.getAbsolutePath()+File.separator + "filename.txt");
		
		try{
			//書き込みメソッド
			writeTextFile(textFile,"This is a test, Roger");
			String text = readTextFile(textFile);
			textView.setText(text);
			
			//ファイルデリート＆成否のチェック
			if(!textFile.delete()){
				textView.setText("Couldn't remove temporary file");
			}
		}catch(IOException e){
			textView.setText("something went wrong!"+e.getMessage());
		}
			
		}
	}
	
	//書き込みメソッド　スローしてるのでエラーが帰ってくる場合あり
	private void writeTextFile(File file,String text)throws IOException{
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		writer.write(text);
		writer.close();
		
	}
	
	private String readTextFile(File file)throws IOException{
		
		BufferedReader reader = new BufferedReader(new FileReader(file));
		StringBuilder text = new StringBuilder();
		String line;
		while((line = reader.readLine()) != null){
			text.append(line);
			text.append("\n");
		}
		
		reader.close();
		return text.toString();
	}

}
