package com.Shashank.BlockRunner.handelers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.Shashank.BlockRunner.Main.Game;
import com.Shashank.BlockRunner.handelers.Loader;

public class Player extends Sprite {
	
	private int numcoins;
	private int totalCoins;
	private Loader load;
	
	public Player(Body body) {
		
		super(body);
		//Texture.setEnforcePotImages(false);
		
		Texture tex;
		
		tex = Game.load.getTexture("runner");
		TextureRegion[] sprites = new TextureRegion[4];
		for(int i = 0; i < sprites.length; i++) {
			
			sprites[i] = new TextureRegion(tex, i * 32, 0, 32, 32);
		}
		
		animation.setFrames(sprites, 1 / 12f);
		
		width = sprites[0].getRegionWidth();
		height = sprites[0].getRegionHeight();
	
	}
	public void collectCoins() { numcoins++; }
	public void totcoins(int totcoins){
		this.totalCoins = totcoins;
		};
	public int returncoins(){
		return this.totalCoins;
	}
	public int collectedcoins(){
		return numcoins;
		
	}

	
}
