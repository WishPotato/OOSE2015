package example;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Vector2f;

public class Bullet
{
	MapGenerator MapGen = new MapGenerator();

	private Vector2f pos;
	private Vector2f speed;
	private int lived = 0;
	
	public Circle cirB = null;
	
	private boolean active = true;
	
	private static int MAX_LIFETIME = 2000;
	
	public Bullet(float x, float y, Vector2f speed)
	{
		pos = new Vector2f(x,y);
		this.speed = speed;
	}
	public void update (int t)
	{
		if (active)
		{
			cirB = new Circle(pos.getX(), pos.getY(), 5);
			Vector2f realSpeed = speed.copy();
			realSpeed.scale( (t/1000.0f) );
			pos.add( realSpeed );
			
			lived += t;
			if( lived > MAX_LIFETIME) {
				active = false;
			}
		}
	}
	
	
	public void render(GameContainer cg, Graphics g){
		if (active){
			g.setColor(Color.red);
			g.fillOval(pos.getX()-5, pos.getY()-5, 10, 10);
		}
		
	}
	public boolean isActive()
	{
		return active;
	}
	
	public void setActive(boolean bool){
		active = bool;
	}
	
	public int getLived(){
		return lived;
	}
	
	public float getX(){
		return pos.getX();
	}
	public float getY(){
		return pos.getY();
	}
}
