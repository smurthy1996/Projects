package com.Shashank.BlockRunner.handelers;


import com.Shashank.BlockRunner.Main.Game;
import com.Shashank.BlockRunner.Play.Play;
import com.Shashank.BlockRunner.States.GameState;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class LevelSelect extends GameState {
	
	private TextureRegion reg;
	
	private GameButton[][] buttons;

	private float dt;
	
	public LevelSelect(GameStateManager gsm) {
		
		super(gsm);
		
		reg = new TextureRegion(Game.load.getTexture("space"), 0, 0, 320, 240);
		
		TextureRegion buttonReg = new TextureRegion(Game.load.getTexture("hud"), 0, 0, 32, 32);
		buttons = new GameButton[5][5];
		
		for(int row = 0; row < buttons.length; row++) {
			for(int col = 0; col < buttons[0].length; col++) {
				buttons[row][col] = new GameButton(buttonReg, 80 + col * 40, 200 - row * 40, cam);
				buttons[row][col].setText(row * buttons[0].length + col + 1 + "");
			}
		}
		
		cam.setToOrtho(false, Game.V_WIDTH, Game.V_HEIGHT);
		
	}
	
	public void handleInput() {
		for(int row = 0; row < buttons.length; row++) {
			for(int col = 0; col < buttons[0].length; col++) {
				buttons[row][col].update(dt);
			}
		}
		for(int i = 0; i < buttons.length; i++) {
			for(int j = 0; j < buttons[0].length; j++) {
				if(buttons[i][j].isClicked()){
					System.out.println(i * buttons[0].length + j + 1);
				Play.level = i * buttons[0].length + j + 1;
				//Game.load.getSound("levelselect").play();
				gsm.setState(GameStateManager.PLAY);
				}
			}
		}
		
	}
	
	public void update(float dt) {
		this.dt = dt;
		handleInput();
		
		
		
	}
	
	public void render() {
		
		sb.setProjectionMatrix(cam.combined);
		
		sb.begin();
		sb.draw(reg, 0, 0);
		sb.end();
		
		for(int row = 0; row < buttons.length; row++) {
			for(int col = 0; col < buttons[0].length; col++) {
				buttons[row][col].render(sb);
			}
		}
		
	}
	
	public void dispose() {
		// everything is in the resource manager com.neet.blockbunny.handlers.Content
	}
	
}
