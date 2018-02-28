package com.Shashank.BlockRunner.handelers;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.utils.Array;

public class MyContactListener implements ContactListener {
	
	private int numFootContacts;
	private Array<Body> bodiesToRemove;
	private Array<Body> bodiesToRemove2;
	private boolean testcontact;
	public static boolean wins = false;
	public static boolean death = false;
	public static boolean powerup = false;
	
	public MyContactListener(){
		
		super();
		
		bodiesToRemove = new Array<Body>();
		bodiesToRemove2 = new Array<Body>();  
	}
	
	// called when two fixtures start to collide
	public void beginContact(Contact c) {
		
		Fixture fa = c.getFixtureA();
		Fixture fb = c.getFixtureB();
		
		if(fa.getUserData() != null && fa.getUserData().equals("foot")) {
			numFootContacts++;
			testcontact = true;
		}
		if(fb.getUserData() != null && fb.getUserData().equals("foot")) {
			numFootContacts++;
			testcontact = true;

		}
		//System.out.println(fb.getUserData());
		if(fa.getUserData() != null && fa.getUserData().equals("coin")) {
			bodiesToRemove.add(fa.getBody());
		}
		if(fb.getUserData() != null && fb.getUserData().equals("coin")) {
			bodiesToRemove.add(fb.getBody());
		}
		if(fa.getUserData() != null && fa.getUserData().equals("Finish")) {
			wins = true;
		}
		if(fb.getUserData() != null && fb.getUserData().equals("Finish")) {
			wins = true;
		}		
		if(fa.getUserData() != null && fa.getUserData().equals("star")) {
			bodiesToRemove2.add(fa.getBody());
			powerup = true;
		}
		else
		{
			powerup = false;
		}
		if(fb.getUserData() != null && fb.getUserData().equals("star")) {
			bodiesToRemove2.add(fb.getBody());
			powerup = true;
		}
		else{
			powerup = false;
		}
		if(fa.getUserData() != null && fa.getUserData().equals("spike")) {
			death = true;
			return;
		}
		else{
			death = false;
		}
		if(fb.getUserData() != null && fb.getUserData().equals("spike")) {
			death = true;
			return;
		}
		else{
			death = false;
		}

		
		//System.out.println(death);
		
	}
	
	// called when two fixtures no longer collide
	public void endContact(Contact c) {
		
		Fixture fa = c.getFixtureA();
		Fixture fb = c.getFixtureB();
		
		if(fa == null || fb == null) return;
		
		if(fa.getUserData() != null && fa.getUserData().equals("foot")) {
			numFootContacts--;
			testcontact = false;
			//System.out.println("Object "+fa.getUserData() + " Colliend with "+fb.getUserData());
			
		}
		if(fb.getUserData() != null && fb.getUserData().equals("foot")) {
			numFootContacts--;
			testcontact = false;
			//System.out.println("Object "+fb.getUserData() + " Colliend with "+fa.getUserData());
		}
		
		
	}
	
	public boolean isPlayerOnGround() { return numFootContacts > 0; }
	public Array<Body> getBods(){return this.bodiesToRemove;};
	public Array<Body> getBods2(){return this.bodiesToRemove2;};
	public boolean ispg() {return this.testcontact;};
	
	
	
	public void preSolve(Contact c, Manifold m) {}
	public void postSolve(Contact c, ContactImpulse ci) {}
	
}
