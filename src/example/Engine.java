package example;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Engine extends BasicGame{

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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	@Override
	public void init(GameContainer gc) throws SlickException {
		gc.setTargetFrameRate(60);
		gc.setAlwaysRender(true); // Renders even if the game is minimized
		gc.setMaximumLogicUpdateInterval(60);
		gc.setVSync(true);
		gc.setShowFPS(false); // Removes the FPS counter
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		// TODO Auto-generated method stub
		
	}

}
