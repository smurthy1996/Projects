package com.Shashank.BlockRunner.handelers;

//import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.Shashank.*;
import com.Shashank.BlockRunner.Main.Game;

public class Coins extends Sprite {

	public Coins(Body body)
	{
		super(body);
		
		Texture tex = Game.load.getTexture("coins");
		TextureRegion[] sprites = TextureRegion.split(tex, 16, 16)[0];
		
		setAnimation(sprites, 1 / 12f);
	}

}
