package example;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class GameState extends BasicGameState {
	float Px = Window.WIDTH/2;
	float Py = Window.HEIGHT/2;
	float PSpeed = 4;
	
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		
	}
	
	public void update(GameContainer container, StateBasedGame sbg, int arg2)
			throws SlickException {
		if(container.getInput().isKeyPressed(Input.KEY_1)){
			sbg.enterState(1, new FadeOutTransition(), new FadeInTransition());
		}
		if(container.getInput().isKeyDown(Input.KEY_A)){
			Px -= PSpeed;
		}
		if(container.getInput().isKeyDown(Input.KEY_D)){
			Px += PSpeed;
		}
		if(container.getInput().isKeyDown(Input.KEY_W)){
			Py -= PSpeed;
		}
		if(container.getInput().isKeyDown(Input.KEY_S)){
			Py += PSpeed;
		}
	}
	
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g)
			throws SlickException {
		g.drawRect(Px, Py, 20, 20);
	}
	
	public int getID() {
		// TODO Auto-generated method stub
		return 0;
	}

}
