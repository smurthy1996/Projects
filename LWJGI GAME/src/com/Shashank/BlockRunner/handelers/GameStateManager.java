package com.Shashank.BlockRunner.handelers;

import java.util.Stack;

import com.Shashank.BlockRunner.Main.Game;
import com.Shashank.BlockRunner.Play.Menu;
import com.Shashank.BlockRunner.Play.Play;
import com.Shashank.BlockRunner.States.GameState;

public class GameStateManager {
	
	
	private Game game;
	
	private Stack<GameState> gameStates;
	
	public static final int PLAY = 6969696;
	public static final int MENU = 83774392;
	public static final int LEVELSELECT = 32423423;
	
	public GameStateManager(Game game){
		this.game = game;
		gameStates = new Stack<GameState>();
		pushState(MENU);
			
	}
	
	public Game game() {return game;};
	
	public void update(float dt){
		gameStates.peek().update(dt);
	}
	public void render(){
		gameStates.peek().render();
	}
	private GameState getState(int state){
		if(state == MENU) return new Menu(this);
		if(state == PLAY) return new Play(this);
		if(state == LEVELSELECT) return new LevelSelect(this);
		return null;
	}
	
	public void setState(int state){
		gameStates.push(getState(state));
		pushState(state);
	}
	public void pushState(int state)
	{
		gameStates.push(getState(state));
	}
	public void popState(){
		GameState g = gameStates.pop();
		g.dispose();
	}
	
	

}
