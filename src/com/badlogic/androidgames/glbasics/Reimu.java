package com.badlogic.androidgames.glbasics;

import java.util.Random;

public class Reimu {
	
	static final Random rand = new Random();
	public float x,y;
	public float angleX,angleY,angleZ;
	public float scaleX,scaleY;
	float dirX,dirY,aX,aY,aZ,sX,sY;
	
	public Reimu(){
		x = rand.nextFloat() * 320;
		y = rand.nextFloat() * 480;
		dirX = rand.nextFloat() * 50;
		dirY = rand.nextFloat() * 50;
		
		aX = 1;
		aY = 1;
		aZ = 1;
		
		sX = 0.05f;
		sY = 0.05f;
		
		angleX = rand.nextFloat() * 360;
		angleY = rand.nextFloat() * 360;
		angleZ = rand.nextFloat() * 360;
		
		angleX = 0;
		angleY = 0;
		angleZ = 0;
		
		scaleX= rand.nextFloat() * 2f;
		scaleY= scaleX;
	}
	
	public void update(float deltaTime){
		move(deltaTime);
		//angle(deltaTime);
		//scale(deltaTime);
	}
	
	private void move(float deltaTime){
		x = x+dirX * deltaTime;
		y = y+dirY * deltaTime;
		
		if(x < 0){
			dirX = -dirX;
			x = 0;
			angleY=0;
		}
		
		if(x > 320){
			dirX = -dirX;
			x=320;
			angleY=180;
		}
		
		if(y < 0){
			dirY=-dirY;
			y=0;
		}
		
		if(y>480){
			dirY=-dirY;
			y=480;
		}	
	}
	
	private void angle(float deltaTime){
		
		angleX = angleX + aX;
		angleY = angleY + aY;
		angleZ = angleZ + aZ;
		
		if(angleX < 0)
			angleX = 360;
		if(angleX > 360)
			angleX = 0;
//		if(angleY < 0)
//			angleY = 360;
//		if(angleY > 360)
//			angleY = 0;
		if(angleZ < 0)
			angleZ = 360;
		if(angleZ > 360)
			angleZ = 0;
		
	}
	
	private void scale(float deltaTime){
		
		scaleX = scaleX + sX;
		scaleY = scaleY + sY;
		
		if(scaleX > 2){
			sX = -sX;
			scaleX = 2;
		}
		
		if(scaleX < 0.5){
			sX = -sX;
			scaleX = 0.5f;
		}
		
		if(scaleY > 2){
			sY = -sY;
			scaleY = 2;
		}
		
		if(scaleY < 0.5){
			sY = -sY;
			scaleY = 0.5f;
		}
	}

}
