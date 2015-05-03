package example;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class PlayerStats {
	MapGenerator MapGen = new MapGenerator();
	
	public int healthPoint = 100;
	public float playerSpeed = 2;
	public int playerDamage = 20;
	public float y = MapGen.sy / 2 * MapGen.squareSize;
	public float x = MapGen.sx / 2 * MapGen.squareSize;
	
	public Image getPlayerImg() throws SlickException{
		Image img = new Image("data/MC.png");
		return img;
	}
	
	public void update(GameContainer container, StateBasedGame sbg, int delta) throws SlickException {
		if(healthPoint >= 0){
			sbg.enterState(0);
		}
	}
}
