package com.badlogic.androidgames.glbasics;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;
import android.util.Log;

import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Screen;
import com.badlogic.androidgames.framework.impl.GLGame;
import com.badlogic.androidgames.framework.impl.GLGraphics;


public class TexturedTriangleScreen extends GLGame{
	
	@Override
	public Screen getStartScreen(){
		return new TexturedTriangle(this);
	}
	
	class TexturedTriangle extends Screen{
		//使用するBufferのサイズ
		final int VERTEX_SIZE = (2+2)*4;
		GLGraphics glGraphics;
		FloatBuffer vertices;
		int textureId;
		
		public  TexturedTriangle (Game game){
			super(game);
			glGraphics = ((GLGame)game).getGLGraphics();
			
			//バイト要領の指定(24バイト　VMではなくネイティブヒープメモリへ
			ByteBuffer byteBuffer = ByteBuffer.allocateDirect(3*VERTEX_SIZE);
			byteBuffer.order(ByteOrder.nativeOrder());
			
			//FloatBufferへ変換　頂点の設定(z軸は自動的に0になる
			vertices = byteBuffer.asFloatBuffer();
			//色の指定の追加(RBGA
			vertices.put(new float[]{0.0f,0.0f,0.0f,1.0f,
									319.0f,0.0f,1.0f,1.0f,
									160.0f,479.0f,0.5f,0.0f});
			//positionを先頭 limitに格納
			vertices.flip();
			
			textureId = loadTexture("dir/texture/reimu.png");
			}
		
		public int loadTexture(String fileName){
			try{
				Bitmap bitmap = BitmapFactory.decodeStream(game.getFileIO().readAsset(fileName));
				GL10 gl = glGraphics.getGL();
				int textureIds[] = new int[1];
				gl.glGenTextures(1, textureIds,0);
				int textureId = textureIds[0];
				gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);
				GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
				gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
				gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_NEAREST);
				
				/*フィルタの種類を示すGL_LINEAR　上より滑らかになる
				gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
				gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);*/
				gl.glBindTexture(GL10.GL_TEXTURE_2D, 0);
				bitmap.recycle();
				return textureId;
			}catch(IOException e){
				Log.d("TexturedTriangleTest","couldn't load asset 'reimu'!");
				throw new RuntimeException("couldn't load asset'"+fileName+"'");
				
			}
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
			
			//テクスチャの有効化
			gl.glEnable(GL10.GL_TEXTURE_2D);
			gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);
			
			gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
			gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
			
			vertices.position(0);
			gl.glVertexPointer(2, GL10.GL_FLOAT, VERTEX_SIZE, vertices);
			
			//ポイントをずらす 座標は指定したのでx,y分ポインタ?をずらす
			vertices.position(2);
			
			//2バイト情報があるよー　今回はテクスチャを指定　色を追加で設定することも可能
			gl.glTexCoordPointer(2, GL10.GL_FLOAT, VERTEX_SIZE, vertices);
			
			//三角形の指定
			gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);
		}

		@Override
		public void pause() {
			
		}

		@Override
		public void resume() {
			textureId = loadTexture("dir/texture/reimu.png");
		}

		@Override
		public void dispose() {
			
		}
		
		
	}

}
