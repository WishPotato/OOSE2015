package example;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;


public class GameState extends BasicGameState {
	MapGenerator MapGen = new MapGenerator();
	int size = MapGen.squareSize;
	float Py = Window.HEIGHT/2;
	float Px = Window.WIDTH/2;
	float PSpeed = 4;
	public String mouse = "no input";
	public String playerpos ="no where";
	public float rotAngle;
	private Image MC = null;
	
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		MapGen.MapGeneration();
		MC = new Image("data/MC.png");
	}
	
	public void update(GameContainer container, StateBasedGame sbg, int arg2)
			throws SlickException {
		if(container.getInput().isKeyPressed(Input.KEY_1)){
			sbg.enterState(1, new FadeOutTransition(), new FadeInTransition());
		}
		if(container.getInput().isKeyDown(Input.KEY_A)){
			Px -= PSpeed;
		}
		if(container.getInput().isKeyDown(Input.KEY_D)){
			Px += PSpeed;
		}
		if(container.getInput().isKeyDown(Input.KEY_W)){
			Py -= PSpeed;
		}
		if(container.getInput().isKeyDown(Input.KEY_S)){
			Py += PSpeed;
		}
		
		int xpos = Mouse.getX();
		int ypos = Mouse.getY();
		mouse = "Mouse position x: "+xpos+ " y:" +ypos;
		playerpos = "player position x: "+Px+" y: "+Py;
		rotAngle = (float) Math.cos(Math.toDegrees((Px-xpos)/(Py-ypos)));
		//System.out.print(rotAngle);
		
	}
	
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g) throws SlickException {
		// Creates the map, with white and black tiles
		for(int x = 0; x < Window.WIDTH / size; x++) {
			for(int y = 0; y < Window.HEIGHT / size; y++) {
				if(MapGen.map[x][y] == 1){
					g.setColor(Color.white);
				}
				else if(MapGen.map[x][y] == 0){
					g.setColor(Color.black);
				}
				g.fillRect(x * size, y * size, size, size);
			}
		}
		
		g.drawString(mouse, 30, 30);
		g.drawString(playerpos, 30, 40);
		MC.draw(Px-10, Py-10);
		MC.setRotation(rotAngle);
	}
	
	public int getID() {
		// TODO Auto-generated method stub
		return 0;
	}

}
