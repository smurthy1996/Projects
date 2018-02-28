package com.Shashank.BlockRunner.Play;

import static com.Shashank.BlockRunner.handelers.B2DVars.PPM;

import com.Shashank.BlockRunner.States.GameState;
import com.Shashank.BlockRunner.handelers.B2DVars;
import com.Shashank.BlockRunner.handelers.GameStateManager;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;

public class BlockRenderer{
	
	private Box2DDebugRenderer b2dr = new Box2DDebugRenderer();
	private BodyDef bdef = new BodyDef();
	private PolygonShape shape = new PolygonShape();
	private FixtureDef fdef = new FixtureDef();

	public void rederer(float x, float y,World world,float height, float width,String USERDATA,PolygonShape shape, Body fixture, short CATOGARY_BIT, short[] MASKBITS)
	{
		bdef.position.set(x/PPM,y/PPM);
		bdef.type = BodyType.StaticBody;
		fixture = world.createBody(bdef);
		
		shape.setAsBox(height/PPM, width/PPM);
		fdef.shape = shape;
		fdef.filter.categoryBits = CATOGARY_BIT;
			fdef.filter.groupIndex = (short) (MASKBITS[0] | MASKBITS[1]);	
		fixture.createFixture(fdef).setUserData(USERDATA);
	}
	public void rederer(float x, float y,World world,float height, float width,String USERDATA,PolygonShape shape, Body fixture, short CATOGARY_BIT, short MASKBIT)
	{
		bdef.position.set(x/PPM,y/PPM);
		bdef.type = BodyType.StaticBody;
		fixture = world.createBody(bdef);
		
		shape.setAsBox(height/PPM, width/PPM);
		fdef.shape = shape;
		fdef.filter.categoryBits = CATOGARY_BIT;                 
		fdef.filter.maskBits = MASKBIT;	
		fixture.createFixture(fdef).setUserData(USERDATA);
	}
	

}
