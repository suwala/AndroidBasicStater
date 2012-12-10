package com.badlogic.androidgames;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Paint.Style;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;

public class PixcelTest extends Activity{
	FastRenderView renderView;
	
	public void onCreate(Bundle savedInstancesState){
		
		super.onCreate(savedInstancesState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		renderView = new FastRenderView(this);
		setContentView(renderView);
	}
	
	protected void onResume(){
		super.onResume();
		renderView.resume();
	}
	
	protected void onPause(){
		super.onPause();
		renderView.pause();
	}
	
	class FastRenderView extends SurfaceView implements Runnable{

		Thread renderThread = null;
		SurfaceHolder holder;
		//volatile コンパイラによる最適化防止　及び　常に最新のものが参照される
		volatile boolean running = false;
		
		public FastRenderView(Context context) {
			super(context);
			holder = getHolder();
		}
		
		public void resume(){
			running = true;
			renderThread = new Thread(this);
			renderThread.start();
			
		}
		
		public void run(){
			Paint paint=new Paint();
			paint.setColor(Color.RED);
			paint.setStyle(Style.STROKE);
			while(running){
				if(!holder.getSurface().isValid())//continueは処理がスキップされる（ループは継続）
					continue;
				Canvas canvas = holder.lockCanvas();
				canvas.drawRGB(255, 255, 255);
				for(int x=0;x<this.getWidth();x++)
					for(int y=0;y<this.getHeight();y++)
						canvas.drawRect(new Rect(x,y,x+1,y+1), paint);
				
				holder.unlockCanvasAndPost(canvas);
			}
		}
		
		public void pause(){
			running = false;
			while(true){
				try{
					renderThread.join();
					break;
				}catch (InterruptedException e){
					//リトライ
				}
			}
		}
	}

}
