package example;

import javafx.scene.shape.Shape;

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
	float Py = MapGen.sy / 2 * size;
	float Px = MapGen.sx / 2 * size;
	float pSpeed = 2;
	public String mouse = "no input";
	public String playerpos ="no where";
	public float rotAngle;
	private Image MC = null;

	
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		MapGen.MapGeneration();
		MC = new Image("data/MC.png");
		if(MapGen.map[(int)Px / MapGen.squareSize][(int)Py / MapGen.squareSize] == 0){
			MapGen.MapGeneration();
		}
	}
	
	public void update(GameContainer container, StateBasedGame sbg, int delta) throws SlickException {
		Input input = container.getInput();
		if(container.getInput().isKeyPressed(Input.KEY_1)){
			sbg.enterState(1, new FadeOutTransition(), new FadeInTransition());
		}
		
		if(input.isKeyDown(Input.KEY_A)){
			Px -= delta * 0.1f * pSpeed;
			if(WallDetected()){
				Px += delta * 0.1f * pSpeed;
			}
		}
		else if(input.isKeyDown(Input.KEY_D)){
			Px += delta * 0.1 * pSpeed;
			if(WallDetected()){
				Px -= delta * 0.1f * pSpeed;
			}
		}
		else if(input.isKeyDown(Input.KEY_W)){
			Py -= delta * 0.1 * pSpeed;
			if(WallDetected()){
				Py += delta * 0.1 * pSpeed;
			}
		}
		else if(input.isKeyDown(Input.KEY_S)){
			Py += delta * 0.1 * pSpeed;
			if(WallDetected()){
				Py -= delta * 0.1 * pSpeed;
			}
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
		// TO DO: CHANGE  COLORS TO IMAGES!
		for(int x = 0; x < Window.WIDTH / size; x++) {
			for(int y = 0; y < Window.HEIGHT / size; y++) {
				if(MapGen.map[x][y] == 1){
					g.setColor(Color.white); // Walkable Ground
				}
				else if(MapGen.map[x][y] == 0){
					g.setColor(Color.black); // Nonwalkable Ground
				}
				else if(MapGen.map[x][y] == 2){
					g.setColor(Color.gray); // Stones
				}
				g.fillRect(x * size, y * size, size, size);
			}
		}
		
		g.drawString(mouse, 30, 30);
		g.drawString(playerpos, 30, 40);
		MC.draw(Px - 10 , Py - 10);
		MC.setRotation(rotAngle);
	}
	
	// Few issues with the detection, but is kinda working
	private boolean WallDetected(){
		boolean detect;
		if(MapGen.map[(int)(Px - 10) / size][(int)(Py - 10) / size] == 0){
			detect = true;
		}
		else if(MapGen.map[(int)(Px + 10) / size][(int)(Py + 10) / size] == 0){
			detect = true;
		}
		else if(MapGen.map[(int)(Px - 10) / size][(int)(Py - 10) / size] == 2){
			detect = true;
		}
		else if(MapGen.map[(int)(Px + 10) / size][(int)(Py + 10) / size] == 2){
			detect = true;
		}
		else {
			detect = false;
		}
		return detect;
	}

	public int getID() {
		// TODO Auto-generated method stub
		return 0;
	}

}
