package com.badlogic.androidgames;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ActivitySelect extends ListActivity{
	
	String tests[] = {"AndoroidBasicsStarter","GLBasicsStarterActivity"};
	String activity[] = {"com.badlogic.androidgames.","com.badlogic.androidgames.glbasics."};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,tests));
        
    }
    
    @Override
    protected void onListItemClick(ListView list,View view,int position,long id){
    	
    	super.onListItemClick(list, view, position, id);
    	
    	String testName = activity[position]+tests[position];
    	try{
    		Class clazz = Class.forName(testName);
    		Intent intent = new Intent(this,clazz);
    		startActivity(intent);
    	}catch (ClassNotFoundException e){
    		e.printStackTrace();
    	}    	
    }

}
