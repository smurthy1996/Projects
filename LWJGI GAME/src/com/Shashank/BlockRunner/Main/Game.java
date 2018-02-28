package com.Shashank.BlockRunner.Main;


import com.Shashank.BlockRunner.handelers.GameStateManager;
import com.Shashank.BlockRunner.handelers.MyInput;
import com.Shashank.BlockRunner.handelers.MyInputProcessor;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.Shashank.BlockRunner.handelers.Loader;
import com.Shashank.BlockRunner.Play.Play;


public class Game implements ApplicationListener{

	public static final String TITLE = "BLOCK RUNNER";
	public static final int V_WIDTH = 320;
	public static final int V_HEIGHT = 240;
	public static final int SCALE = 2;
	
	public static final float STEP = 1/60f;
	private SpriteBatch sb;
	private OrthographicCamera cam;
	private OrthographicCamera hudcam;

	public SpriteBatch getSpriteBatch() {return sb;};
	public OrthographicCamera getCamera() {return this.cam;};
	public OrthographicCamera getHUDCamera() {return this.hudcam;};
	
	private GameStateManager gsm;
	private float accum;
	public static Loader load;
	
	
	
	public void create() {
		
		Texture.setEnforcePotImages(false);
		
		
		
		load = new Loader();
		load.loadTexture("ImageCache/"+Play.PlayerImage+".png"); //put the .png here
		load.loadTexture("ImageCache/spinning.png","coins");
		load.loadTexture("ImageCache/Mine.png","mine");
		load.loadTexture("ImageCache/PowerUp.png","PowerUp");
		load.loadTexture("ImageCache/hud.png","hud");
		load.loadTexture("ImageCache/Menu.png","menu");
		load.loadTexture("ImageCache/space.png", "space");
		load.loadTexture("ImageCache/heart.png","heart");
		
		load.loadSound("Sfx/crystal.wav", "crystal");
		load.loadSound("Sfx/hit.wav","hit");
		load.loadSound("Sfx/jump.wav", "jump");
		load.loadSound("Sfx/levelselect.wav", "select");
		load.loadSound("Sfx/powerup.wav", "pup");
		load.loadSound("Sfx/die.wav", "die");
		load.loadMusic("Music/Andain_-_Promises__Myon___Shane_54_Summer_Of_Love_Mix_.mp3", "music1");
		load.loadMusic("Music/Sou-Kanai-Never-Give-In-Aknael-amp-Abebe-Bekeela-Remix(muzofon.com).mp3", "music2");
		

		
//		load.getMusic("music1").setLooping(true);
//		load.getMusic("music1").setVolume(0.5f);
//		load.getMusic("music1").play();		
		Gdx.input.setInputProcessor(new MyInputProcessor());
		sb = new SpriteBatch();
		cam = new OrthographicCamera();
		cam.setToOrtho(false,V_WIDTH,V_HEIGHT);
		hudcam = new OrthographicCamera();
		hudcam.setToOrtho(false,V_WIDTH,V_HEIGHT);
		gsm = new GameStateManager(this);
	}
	public void render() {
		accum += Gdx.graphics.getDeltaTime();
		while (accum >= STEP){
			accum -= STEP;
			gsm.update(STEP);
			gsm.render();
			MyInput.update();
		}
	}
	public void dispose(){}
	
	
	public void resize(int w,int h){}
	public void pause(){}
	public void resume(){}
	

	
	
	
	
}
