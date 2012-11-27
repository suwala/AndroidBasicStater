package com.badlogic.androidgames;

import android.os.Bundle;
import android.app.ListActivity;
import android.view.Menu;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class AndoroidBasicsStarter extends ListActivity {
	
	//文字列を追加するとリストに表示され　クリックでそのActivityへ飛ぶ
	String tests[] = {"LifeCycleTest","SingleTouchTest","MultiTOuchTest","KeyTset","AcceleremoterTest"
			,"LifeCycleTest","SingleTouchTest","MultiTouchTest","KeyTest","AccelerometerTest","AssetsTest","ExternalStorageTest"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,tests));
        
    }
    
    @Override
    protected void onListItemClick(ListView list,View view,int position,long id){
    	
    	super.onListItemClick(list, view, position, id);
    	
    	String testName = tests[position];
    	try{
    		Class clazz = Class.forName("com.badlogic.androidgames."+testName);
    		Intent intent = new Intent(this,clazz);
    		startActivity(intent);
    	}catch (ClassNotFoundException e){
    		e.printStackTrace();
    	}    	
    }


}
