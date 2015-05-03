package example;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;


public class EnemyAI {
	float x,y;
	private float size = 20;
	private Image img;
	
	public int health = 100;
	public Rectangle rectE = null;
	
	public EnemyAI(float x, float y, Image _img){
		this.x = x;
		this.y = y;
		img = _img;
	}
	
	public void tick(){
		if(health > 0){
			//rectE = new Rectangle(x,y, size, size);
		}
	}
	
	public void render(Graphics g){
		if(health > 0){
			g.setColor(Color.red);
			g.fillRect(x * size, y * size - 15, health / 5, 2);
			img.draw(x * size,y * size);
			rectE = new Rectangle(x * size,y * size, size, size);
		}
	}
	
	public float getX(){
		return x;
	}
	
	public float getY(){
		return y;
	}
}
