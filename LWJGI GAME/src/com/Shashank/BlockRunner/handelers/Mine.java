package com.Shashank.BlockRunner.handelers;

import com.Shashank.BlockRunner.Main.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;

public class Mine extends Sprite{
	
	public Mine(Body body)
	{
		super(body);
		
		Texture tex = Game.load.getTexture("mine");
		TextureRegion[] sprites = TextureRegion.split(tex, 18, 18)[0];
		
		setAnimation(sprites, 1 / 12f);
	}

}
