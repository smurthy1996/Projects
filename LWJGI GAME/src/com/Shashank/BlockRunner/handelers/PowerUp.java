package com.Shashank.BlockRunner.handelers;

import com.Shashank.BlockRunner.Main.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;

public class PowerUp extends Sprite{
	
	public PowerUp(Body body) {
		super(body);
		
		Texture tex = Game.load.getTexture("PowerUp");
		TextureRegion[] sprites = TextureRegion.split(tex, 15, 16)[0];
		
		setAnimation(sprites, 1 / 12f);

	}


}
