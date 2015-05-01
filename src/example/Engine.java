package example;

//import javafx.stage.Screen;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Engine extends StateBasedGame{

	public static final int menu = 0;
	public static final int play = 1;
	
	public Engine() {
		super("Gauntlet");
		// add "public GameState(int state)" before removing the other "//"
		//this.addState(new GameState(play));
		this.addState(new MainMenu(menu));
		//this.addState(new GameOverState());
	
	}
	public void initStatesList(GameContainer container) throws SlickException {

		container.setTargetFrameRate(60);
		container.setAlwaysRender(true); // Renders even if the game is minimized
		container.setMaximumLogicUpdateInterval(60);
		container.setVSync(true);
		container.setShowFPS(false); // Removes the FPS counter
		this.getState(menu).init(container, this);
		//this.getState(play).init(container, this);
		this.enterState(menu);
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

