package com.badlogic.androidgames.glbasics;



import java.util.Timer;
import java.util.TimerTask;

import javax.microedition.khronos.opengles.GL10;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Screen;
import com.badlogic.androidgames.framework.gl.BindableVertices;
import com.badlogic.androidgames.framework.gl.FPSCounter;
import com.badlogic.androidgames.framework.gl.Texture;
import com.badlogic.androidgames.framework.impl.GLGame;
import com.badlogic.androidgames.framework.impl.GLGraphics;

public class OptimizationReimuTest extends GLGame{

	String str = null;
	Timer timer;
	Toast toast2;
	int i;
	
	@Override
	public void onResume() {
		// TODO 自動生成されたメソッド・スタブ
		super.onResume();
		 toast2 = Toast.makeText(OptimizationReimuTest.this, str, Toast.LENGTH_SHORT);
		onToast("test");
	}
	
	@Override
	public void onPause(){
		super.onPause();
		if(timer != null)
			timer.cancel();
	}

	@Override
	public Screen getStartScreen(){
		Intent intent = getIntent();
		
		return new ReimuScreen(this,intent.getIntExtra("et", 100));
	}
	
	public void setStr(String str){
		this.str = str;
	}
	
	public void onToast(final String str1){
		this.str = str1;
		final Handler handler = new Handler();		
		timer = new Timer();
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				
				handler.post(new Runnable() {
					@Override
					public void run() {
						toast2.setText(str);	
						toast2.show();
					}
				});
				
			}
		}, 0,100);		
	}
	
	class ReimuScreen extends Screen{

		int NUM_REIMU;
		GLGraphics glGraphics;
		Texture reimuTexture;
		BindableVertices reimuModel;
		Reimu[] reimus;
		FPSCounter fps;
		Toast toast;
		
		public ReimuScreen(Game game,int objCount) {
			super(game);
			
			if(objCount > 100)
				NUM_REIMU = objCount;
			else
				NUM_REIMU = 100;
			
			Log.d("ReimuScreen","Create Object "+ NUM_REIMU+"!");
			glGraphics = ((GLGame)game).getGLGraphics();

			reimuTexture = new Texture((GLGame)game,"dir/texture/reimu888.png");
			reimuModel = new BindableVertices(glGraphics, 4, 12, false, true);

			reimuModel.setVertices(new float[]{-16,-16,0,1,
					16,-16,1,1,
					16, 16,1,0,
					-16,16,0,0},0,16);
			reimuModel.setIndices(new short[] {0,1,2,2,3,0}, 0, 6);

			reimus = new Reimu[NUM_REIMU];
			for(int i=0;i<NUM_REIMU;i++)
				reimus[i] = new Reimu();		
			
			fps = new FPSCounter();
		}		

		@Override
		public void update(float deltaTime){
			
			game.getInput().getTouchEvents();
			game.getInput().getKeyEvents();
			for(int i=0;i < NUM_REIMU;i++)
				reimus[i].update(deltaTime);
		}
		
		@Override
		public void resume() {
			GL10 gl = glGraphics.getGL();
			gl.glViewport(0, 0, glGraphics.getWidth(), glGraphics.getHeight());
			gl.glClearColor(1, 0, 0, 1);
			gl.glMatrixMode(GL10.GL_PROJECTION);
			gl.glLoadIdentity();
			gl.glOrthof(0, 320, 0, 480, 1, -1);
			
			reimuTexture.reload();
			gl.glEnable(GL10.GL_TEXTURE_2D);
			reimuTexture.bind();			
		}
		
		@Override
		public void present(float deltaTime){
			GL10 gl = glGraphics.getGL();
			gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
			
			gl.glMatrixMode(GL10.GL_MODELVIEW);
			reimuModel.bind();
			
			for(int i=0;i<NUM_REIMU;i++){
				gl.glLoadIdentity();
				gl.glTranslatef(reimus[i].x, reimus[i].y, 0);//移動
				
				//gl.glRotatef(reimus[i].angleX, 1, 0, 0);//回転
				//gl.glRotatef(reimus[i].angleY, 1, 0, 0);
				//gl.glRotatef(reimus[i].angleZ, 0, 0, 1);
				//gl.glScalef(reimus[i].scaleX, reimus[i].scaleY, 0);//移動→回転→スケール　順番のこの順で行うこと！
				//gl.glRotatef(45, 0, 0, 1);
				gl.glRotatef(reimus[i].angleY, 0, 1, 0);		
				gl.glScalef(reimus[i].scaleX, reimus[i].scaleY, 0);
				reimuModel.draw(GL10.GL_TRIANGLES, 0, 6);//レンダリング
			}
			reimuModel.unbind();
			
			//fps.logFrame();
			String str = fps.getFrametoString();
			if(str != null)
				setStr(str);
			
		}


		@Override
		public void pause() {
			// TODO 自動生成されたメソッド・スタブ
			
		}


		


		@Override
		public void dispose() {
			// TODO 自動生成されたメソッド・スタブ
			
		}

	}

}
