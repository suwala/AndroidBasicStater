package com.badlogic.androidgames.glbasics;



import java.util.Timer;
import java.util.TimerTask;

import javax.microedition.khronos.opengles.GL10;

import android.os.Handler;
import android.widget.Toast;

import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Screen;
import com.badlogic.androidgames.framework.gl.FPSCounter;
import com.badlogic.androidgames.framework.gl.Texture;
import com.badlogic.androidgames.framework.gl.Vertices;
import com.badlogic.androidgames.framework.impl.GLGame;
import com.badlogic.androidgames.framework.impl.GLGraphics;

public class ReimuTest extends GLGame{

	String str = null;
	Timer timer;
	Toast toast2;
	
	@Override
	public void onResume() {
		// TODO 自動生成されたメソッド・スタブ
		super.onResume();
		 toast2 = Toast.makeText(ReimuTest.this, str, Toast.LENGTH_SHORT);
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
		return new ReimuScreen(this);
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

		static final int NUM_REIMU = 100;
		GLGraphics glGraphics;
		Texture reimuTexture;
		Vertices reimuModel;
		Reimu[] reimus;
		FPSCounter fps;
		Toast toast;
		
		public ReimuScreen(Game game) {
			super(game);
			glGraphics = ((GLGame)game).getGLGraphics();

			reimuTexture = new Texture((GLGame)game,"dir/texture/reimu888.png");
			reimuModel = new Vertices(glGraphics, 4, 12, false, true);

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
		public void present(float deltaTime){
			GL10 gl = glGraphics.getGL();
			gl.glViewport(0, 0, glGraphics.getWidth(), glGraphics.getHeight());
			gl.glClearColor(1, 0, 0, 1);
			gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
			gl.glMatrixMode(GL10.GL_PROJECTION);
			gl.glLoadIdentity();
			gl.glOrthof(0, 320, 0, 480, 1, -1);
			
			gl.glEnable(GL10.GL_TEXTURE_2D);
			reimuTexture.bind();
			
			gl.glMatrixMode(GL10.GL_MODELVIEW);
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
		public void resume() {
			// TODO 自動生成されたメソッド・スタブ
			
		}


		@Override
		public void dispose() {
			// TODO 自動生成されたメソッド・スタブ
			
		}

	}

}
