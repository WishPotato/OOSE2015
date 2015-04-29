package example;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class PlayerStats {
	MapGenerator MapGen = new MapGenerator();
	
	public int healthPoint = 100;
	public float playerSpeed = 2;
	public int playerDamage = 20;
	
	public Image getPlayerImg() throws SlickException{
		Image img = new Image("data/MC.png");
		return img;
	}
}
