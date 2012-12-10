package com.badlogic.androidgames.glbasics;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Screen;
import com.badlogic.androidgames.framework.impl.GLGame;
import com.badlogic.androidgames.framework.impl.GLGraphics;


public class FirstTriangleTest extends GLGame{
	
	@Override
	public Screen getStartScreen(){
		return new FirstTriangleScreen(this);
	}
	
	class FirstTriangleScreen extends Screen{
		GLGraphics glGraphics;
		FloatBuffer vertices;
		
		public FirstTriangleScreen (Game game){
			super(game);
			glGraphics = ((GLGame)game).getGLGraphics();
			//バイト要領の指定(24バイト　VMではなくネイティブヒープメモリへ
			ByteBuffer byteBuffer = ByteBuffer.allocateDirect(3*2*4);
			byteBuffer.order(ByteOrder.nativeOrder());
			
			//FloatBufferへ変換　頂点の設定(z軸は自動的に0になる
			vertices = byteBuffer.asFloatBuffer();
			vertices.put(new float[]{0.0f,0.0f,
									319.0f,0.0f,
									160.0f,479.0f});
			//positionを先頭 limitに格納
			vertices.flip();
			}

		@Override
		public void update(float deltaTime) {
			
			game.getInput().getTouchEvents();
			game.getInput().getKeyEvents();
			
		}

		@Override
		public void present(float deltaTime) {
			GL10 gl = glGraphics.getGL();
			//ビューポート（切り出される画面のサイズ）の設定
			gl.glViewport(0, 0, glGraphics.getWidth(), glGraphics.getHeight());
			//画面のクリア
			gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
			//手動で描画？
			gl.glMatrixMode(GL10.GL_PROJECTION);
			//単位行列の設定
			gl.glLoadIdentity();
			//視錐台(空間)の設定　薄っぺらい板みたいな
			gl.glOrthof(0, 320, 0, 480, 1, -1);
			
			//色の設定と塗りつぶし
			gl.glColor4f(1, 0, 0, 1);
			gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
			//描画するデータの指定 頂点座標が２つ　型の指定(float) 配列内の頂点間 FloatBuffer
			gl.glVertexPointer(2, GL10.GL_FLOAT, 9, vertices);
			//三角形の指定
			gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);
		}

		@Override
		public void pause() {
			
		}

		@Override
		public void resume() {
			
		}

		@Override
		public void dispose() {
			
		}
		
		
	}

}
