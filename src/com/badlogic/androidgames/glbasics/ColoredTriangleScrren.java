package com.badlogic.androidgames.glbasics;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Screen;
import com.badlogic.androidgames.framework.impl.GLGame;
import com.badlogic.androidgames.framework.impl.GLGraphics;


public class ColoredTriangleScrren extends GLGame{
	
	@Override
	public Screen getStartScreen(){
		return new ColoredTriangle(this);
	}
	
	class ColoredTriangle extends Screen{
		//使用するBufferのサイズ
		final int VERTEX_SIZE = (2+4)*4;
		GLGraphics glGraphics;
		FloatBuffer vertices;
		
		public ColoredTriangle(Game game){
			super(game);
			glGraphics = ((GLGame)game).getGLGraphics();
			
			//バイト要領の指定(24バイト　VMではなくネイティブヒープメモリへ
			ByteBuffer byteBuffer = ByteBuffer.allocateDirect(3*VERTEX_SIZE);
			byteBuffer.order(ByteOrder.nativeOrder());
			
			//FloatBufferへ変換　頂点の設定(z軸は自動的に0になる
			vertices = byteBuffer.asFloatBuffer();
			//色の指定の追加(RBGA
			vertices.put(new float[]{0.0f,0.0f,1,0,0,1,
									319.0f,0.0f,0,1,0,1,
									160.0f,479.0f,0,0,1,1});
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
			
			//追記　色の設定
			gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
			gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
			
			vertices.position(0);
			gl.glVertexPointer(2, GL10.GL_FLOAT, VERTEX_SIZE, vertices);
			
			//ポイントをずらす 座標は指定したのでx,y分ポインタ?をずらす
			vertices.position(2);
			
			//4バイト情報があるよー
			gl.glColorPointer(4, GL10.GL_FLOAT, VERTEX_SIZE, vertices);
			
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
