package com.Shashank.BlockRunner.Play;

import static com.Shashank.BlockRunner.handelers.B2DVars.PPM;
import static com.Shashank.BlockRunner.handelers.MyContactListener.death;
import static com.Shashank.BlockRunner.handelers.MyContactListener.powerup;
import static com.Shashank.BlockRunner.handelers.MyContactListener.wins;
import java.util.Random;
import com.Shashank.BlockRunner.Main.Game;
import com.Shashank.BlockRunner.Play.Menu;
import com.Shashank.BlockRunner.States.GameState;
import com.Shashank.BlockRunner.handelers.B2DVars;
import com.Shashank.BlockRunner.handelers.Coins;
import com.Shashank.BlockRunner.handelers.GameStateManager;
import com.Shashank.BlockRunner.handelers.HUD;
import com.Shashank.BlockRunner.handelers.Mine;
import com.Shashank.BlockRunner.handelers.MyContactListener;
import com.Shashank.BlockRunner.handelers.MyInput;
import com.Shashank.BlockRunner.handelers.Player;
import com.Shashank.BlockRunner.handelers.PowerUp;
import com.Shashank.BlockRunner.handelers.Sprite;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;


public class Play extends GameState {

	private World world;
	private Body playerBody;
	private Body barrier;
	private OrthographicCamera b2dcam;
	private MyContactListener cl;
	Random ran = new Random();
	private float tileSize;
	private TiledMap map;
	private OrthogonalTiledMapRenderer tMap;
	private BitmapFont font = new BitmapFont();
	private float i = 0;
	private boolean createblock;
	BlockRenderer blk = new BlockRenderer();
	private Box2DDebugRenderer b2dr = new Box2DDebugRenderer();
	PolygonShape shape = new PolygonShape();
	FixtureDef fdef = new FixtureDef();
	private SpriteBatch batch = new SpriteBatch();
	private Sprite spr;
	private Player player;
	private TiledMapTileLayer layer2;
	private Body etc;
	private Array<Coins> coins;
	private boolean debug;
	private Array<Mine> mines;
	private Array<PowerUp> PowerUp;
	public static String PlayerImage = "runner";
	public static int lives = 5;
	private HUD hud;
	private float width;
	private float tilesize2;
	public static int level;


	// Static body - unaffected by forces and cannot move

	// Kenematic body - unaffected by forces

	// Dynamic body- Gets Affected by forces


	
		
public Play(GameStateManager gsm) {
		
		super(gsm);
		// set up box2d stuff
		world = new World(new Vector2(0, -9.81f), true);
		cl = new MyContactListener();
		world.setContactListener(cl);
		b2dr = new Box2DDebugRenderer();
		
		// create player
		createPlayer();
		
		// create tiles
		createTiles();
		
		//Create coins
		coins();
		
		
		player.totcoins(coins.size);
		mina();
		
		PoweUpSprite();
		
		hud = new HUD(player);
		
		// set up box2d cam
		b2dcam = new OrthographicCamera();
		b2dcam.setToOrtho(false, Game.V_WIDTH / PPM, Game.V_HEIGHT / PPM);
		
	}
	
	public void handleInput() {
		
		// player jump
		if(MyInput.isPresses(MyInput.UPArrow)) {
			if(cl.isPlayerOnGround()) {
				playerBody.applyForceToCenter(0, 250, true);
			}
		}

	}
	
	public void update(float dt) {
		
		handleInput();
		
		//System.out.println(playerBody.getPosition());
		
		world.step(dt, 6, 2);
		
		if(wins == true) {
			Game.load.getSound("select").play();
			wins = false;
			gsm.setState(GameStateManager.LEVELSELECT);
		}
		
		if(lives == -1){
			lives = 5;
			Game.load.getSound("die").play();
			gsm.setState(GameStateManager.LEVELSELECT);
		}
		
		if(death)
		{
			this.lives--;
			Game.load.getSound("hit").play();
			death = false;
			gsm.setState(GameStateManager.PLAY);
		}
		System.out.println(lives);
		
		if(powerup == true)
		{
			this.lives++;
			System.out.println(lives);
			powerup = false;
		}
		
		if (playerBody.getLinearVelocity().x < 0.001)// || death == true)
		{
			this.lives--;
			Game.load.getSound("hit").play();
			Game.load.getSound("hit").play();
			gsm.setState(GameStateManager.PLAY);

		}
		if(player.getBody().getPosition().y < 0) {
			this.lives--;
			Game.load.getSound("hit").play();
			gsm.setState(GameStateManager.PLAY);
		}
		//System.out.println(death);
		Array<Body> bodies = cl.getBods();
		for(int i = 0;i < bodies.size;i++)
		{
			Body b = bodies.get(i);
			coins.removeValue((Coins)b.getUserData(), true);
			world.destroyBody(b);
			player.collectCoins();
			Game.load.getSound("crystal").play();
		}
		bodies.clear();
		
		Array<Body> bodies2 = cl.getBods2();
		for(int i = 0;i < bodies2.size;i++)  
			
		{
			Body b2 = bodies2.get(i);
			PowerUp.removeValue((PowerUp) b2.getUserData(), true);
			world.destroyBody(b2);
			Game.load.getSound("pup").play();
		}
		bodies2.clear();
		System.out.println(cl.ispg());
		
		
		for(int i = 0; i < coins.size;i++)
		{
			coins.get(i).update(dt);;
			//System.out.println(coins.get(i).getPosition());
		}
		for(int i = 0; i < mines.size;i++)
		{
			mines.get(i).update(dt);;
			//System.out.println(coins.get(i).getPosition());
		}
		for(int i = 0; i < PowerUp.size;i++)
		{
			PowerUp.get(i).update(dt);
			//System.out.println(coins.get(i).getPosition());
		}
		player.update(dt);
		
		
		
	}
	
	public void render() {
		// clear screen
		Gdx.gl11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		
		//System.out.println(Gdx.graphics.getFramesPerSecond());
		
		// draw tile map
		tMap.setView(cam);
		tMap.render();
		
		cam.position.set( playerBody.getPosition().x * PPM + Game.V_WIDTH / 4, (Game.V_HEIGHT / 2) + 48,0);
		cam.update();
		// draw player
		sb.setProjectionMatrix(cam.combined);
		player.render(sb);
		
		
		for(int i = 0; i < coins.size;i++)
		{
			coins.get(i).render(sb);
			//System.out.println(coins.get(i).getPosition());
		}
		for(int i = 0; i < mines.size;i++)
		{
			mines.get(i).render(sb);
			//System.out.println(coins.get(i).getPosition());
		}
		for(int i = 0; i < PowerUp.size;i++)
		{
			PowerUp.get(i).render(sb);
			//System.out.println(coins.get(i).getPosition());
		}
		
		sb.setProjectionMatrix(hudcam.combined);
		hud.render(sb);
		
		// draw box2d world
		b2dr.render(world, cam.combined);

		
	}
	
	public void dispose() {}
	
	private void createPlayer() {
		
	
		
		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();
		PolygonShape shape = new PolygonShape();
		
		// create player
		bdef.position.set(60 / PPM, 150 / PPM);
		bdef.type = BodyType.DynamicBody;
		playerBody = world.createBody(bdef);
		playerBody.setLinearVelocity(2f,0);
		
		
		shape.setAsBox(13 / PPM, 13 / PPM);
		fdef.shape = shape;
		fdef.filter.categoryBits = B2DVars.BIT_PLAYER;
		fdef.filter.maskBits = B2DVars.BIT_GRAY | B2DVars.BIT_FINISH | B2DVars.BIT_COINS | B2DVars.BIT_SPIKE | B2DVars.BIT_STAR;
		playerBody.createFixture(fdef).setUserData("player");
		
		// create foot sensor
		shape.setAsBox(13 / PPM, 2 / PPM, new Vector2(0, -13 / PPM), 0);
		fdef.shape = shape;
		fdef.filter.categoryBits = B2DVars.BIT_PLAYER;
		fdef.filter.maskBits = B2DVars.BIT_GRAY | B2DVars.BIT_FINISH;
		fdef.isSensor = true;
		playerBody.createFixture(fdef).setUserData("foot");
		
		
		//System.out.println(playerBody);
		// create player
		player = new Player(playerBody);
		//playerBody.setUserData(player);
		
//		for(int i = 0; i < 20;i++)
//		{
//			bdef.position.set(this.game.V_WIDTH / PPM, this.game.V_HEIGHT / PPM);
//			bdef.type = BodyType.DynamicBody;
//			etc = world.createBody(bdef);
//			
//			
//			shape.setAsBox(13 / PPM, 13 / PPM);
//			fdef.shape = shape;
//			fdef.filter.categoryBits = B2DVars.BIT_PLAYER;
//			fdef.filter.maskBits = B2DVars.BIT_GRAY | B2DVars.BIT_FINISH | B2DVars.BIT_COINS;
//			etc.createFixture(fdef).setUserData("player");
//		}
		shape.dispose();
		
		
	}
	
	private void createTiles() {
		
		// load tile map
		map = new TmxMapLoader().load("Levels/map"+level+".tmx");
		tMap = new OrthogonalTiledMapRenderer(map);
		
		tileSize = (int) map.getProperties().get("tilewidth");
		TiledMapTileLayer layer;
		
		layer = (TiledMapTileLayer) map.getLayers().get("gray");
		createLayer(layer, B2DVars.BIT_GRAY,"Ground");
		
		layer2 = (TiledMapTileLayer) map.getLayers().get("Finish");
		createLayer(layer2,B2DVars.BIT_FINISH,"Finish");
	}
	
	private void createLayer(TiledMapTileLayer layer, short bits, String data) {
		
		this.width = layer.getTileWidth();
		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();
		
		// go through all the cells in the layer
		for(int row = 0; row < layer.getHeight(); row++) {
			for(int col = 0; col < layer.getWidth(); col++) {
				
				// get cell
				Cell cell = layer.getCell(col, row);
				
				// check if cell exists
				if(cell == null) continue;
				if(cell.getTile() == null) continue;
				
				// create a body + fixture from cell
				bdef.type = BodyType.StaticBody;
				bdef.position.set(
					(col + 0.5f) * tileSize / PPM,
					(row + 0.5f) * tileSize / PPM
				);
				
				ChainShape cs = new ChainShape();
				Vector2[] v = new Vector2[5];
				v[0] = new Vector2(
					-tileSize / 2 / PPM, -tileSize / 2 / PPM);
				v[1] = new Vector2(
					-tileSize / 2 / PPM, tileSize / 2 / PPM);
				v[2] = new Vector2(
					tileSize / 2 / PPM, tileSize / 2 / PPM);
				v[3] = new Vector2(
					tileSize / 2 / PPM, -tileSize / 2 / PPM);
				v[4] = new Vector2(
					-tileSize / 2 / PPM, -tileSize / 2 / PPM);
				cs.createChain(v);
				fdef.friction = 0;
				fdef.shape = cs;
				fdef.filter.categoryBits = bits;
				fdef.filter.maskBits = B2DVars.BIT_PLAYER;
				fdef.isSensor = false;
				world.createBody(bdef).createFixture(fdef).setUserData(data);
				cs.dispose();
				
			}
		}
	}
	
	public void coins()
	{
		
		coins = new Array<Coins>();
		
		MapLayer layer =  map.getLayers().get("Coins");
		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();
		
		for(MapObject mo : layer.getObjects()) {
				
				bdef.type = BodyType.StaticBody;
                Ellipse e = ((EllipseMapObject) mo).getEllipse();
                float x = e.x/PPM;
                float y = e.y/PPM;
            	bdef.position.set(x, y);
            	CircleShape cshape = new CircleShape();
    			cshape.setRadius(8/PPM);
    			
    			fdef.shape = cshape;
    			fdef.isSensor = true;
    			fdef.filter.categoryBits = B2DVars.BIT_COINS;
    			fdef.filter.maskBits = B2DVars.BIT_PLAYER;
    			
    			Body bod = world.createBody(bdef);
    			bod.createFixture(fdef).setUserData("coin");
    			
    			Coins coin = new Coins(bod);
    			
    			coins.add(coin);
    			
    			bod.setUserData(coin);
    			cshape.dispose();
		}
	}
	public void mina()
	{
		mines = new Array<Mine>();
		
		MapLayer layer2 = map.getLayers().get("Mine");
		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();
		
		for(MapObject mo : layer2.getObjects())
		{
			bdef.type = BodyType.StaticBody;
			Ellipse e = ((EllipseMapObject) mo).getEllipse();
			float x = e.x/PPM;
			float y = e.y/PPM;
			bdef.position.set(x, y);
			CircleShape cshape = new CircleShape();
			cshape.setRadius(8/PPM);
			
			fdef.shape = cshape;
			fdef.isSensor = true;
			fdef.filter.categoryBits = B2DVars.BIT_SPIKE;
			fdef.filter.maskBits = B2DVars.BIT_PLAYER;
			
			Body bod = world.createBody(bdef);
			bod.createFixture(fdef).setUserData("spike");
			
			Mine mine = new Mine(bod);
			
			mines.add(mine);
			
			bod.setUserData(mine);
			
			cshape.dispose();
		}		
	}
	
	public void PoweUpSprite()
	{
		PowerUp = new Array<PowerUp>();
		
		MapLayer layer2 = map.getLayers().get("PowerUp");
		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();
		
		for(MapObject mo : layer2.getObjects())
		{
			bdef.type = BodyType.StaticBody;
			Ellipse e = ((EllipseMapObject) mo).getEllipse() ;
			float x = e.x/PPM;
			float y = e.y/PPM;
			bdef.position.set(x, y);
			CircleShape cshape = new CircleShape();
			cshape.setRadius(8/PPM);
			
			fdef.shape = cshape;
			fdef.isSensor = true;
			fdef.filter.categoryBits = B2DVars.BIT_STAR;
			fdef.filter.maskBits = B2DVars.BIT_PLAYER;
			
			Body bod = world.createBody(bdef);
			bod.createFixture(fdef).setUserData("star");
			
			PowerUp Pup = new PowerUp(bod);
			
			PowerUp.add(Pup);
			
			bod.setUserData(Pup);
			cshape.dispose();
		}
	}
}