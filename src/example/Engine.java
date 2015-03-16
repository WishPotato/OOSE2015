package example;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Engine extends StateBasedGame{

	public Engine() {
		super("Gauntlet");
		// TODO Auto-generated constructor stub
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
	
	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		gc.setTargetFrameRate(60);
		gc.setAlwaysRender(true); // Renders even if the game is minimized
		gc.setMaximumLogicUpdateInterval(60);
		gc.setVSync(true);
		gc.setShowFPS(false); // Removes the FPS counter
	}

}
