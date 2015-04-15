package example;

//import javafx.stage.Screen;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Engine extends StateBasedGame{

	
	public Engine() {
		super("Gauntlet");
	
	}
	public void initStatesList(GameContainer container) throws SlickException {

		container.setTargetFrameRate(60);
		container.setAlwaysRender(true); // Renders even if the game is minimized
		container.setMaximumLogicUpdateInterval(60);
		container.setVSync(true);
		container.setShowFPS(true); // Removes the FPS counter
		this.addState(new GameState());
		this.addState(new GameOverState());
		
	}

	
	public static void main(String[] args) {
		
		try {
			AppGameContainer game = new AppGameContainer(new Engine());
			game.setDisplayMode(Window.WIDTH, Window.HEIGHT, false);
			game.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}

	}
	
	
	
}

