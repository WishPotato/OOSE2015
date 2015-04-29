package example;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;


public class EnemyAI {
	public int spawnAmt = 10;
	public int healthPoint = 60;
	public int damage = 10;
	public int[] enemyArr = new int[spawnAmt];
	
	public Image getEnemyImg() throws SlickException{
		Image img = new Image("data/enemy.png");
		return img;
	}
}
