package example;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;


public class EnemyAI {
	float x,y;
	private float size = 20;
	private Image img;
	public int health = 100;
	Rectangle rectE;
	
	public EnemyAI(float x, float y, Image _img){
		this.x = x;
		this.y = y;
		img = _img;
	}
	
	public void tick(){
	}
	
	public void render(GameContainer cg, StateBasedGame sg, Graphics g) throws SlickException {
		if(health >= 0){
			rectE = new Rectangle(x,y, size, size);
			g.setColor(Color.red);
			g.fillRect(x * size, y * size - 15, health / 5, 2);
			img.draw(x * size,y * size);
		}
	}
	
	public float getX(){
		return x;
	}
	
	public float getY(){
		return y;
	}
}
