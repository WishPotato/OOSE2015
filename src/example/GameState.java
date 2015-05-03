package example;

import java.util.LinkedList;
import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.geom.*;


public class GameState extends BasicGameState {
	MapGenerator MapGen = new MapGenerator();
	PlayerStats PStat = new PlayerStats();
	
	private LinkedList<Bullet> bullets;
	private LinkedList<EnemyAI> enemy;
	
	int size = MapGen.squareSize;
	float Py = MapGen.sy / 2 * size;
	float Px = MapGen.sx / 2 * size;
	float pSpeed = PStat.playerSpeed;

	
	public int count = 0;
	
	private Image MC = null;
	private Image Eimg = null;
	private int[] enemyArr = new int[10];

	public GameState(int state){}
	
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {	
		MapGen.MapGeneration();
		MC = PStat.getPlayerImg();
		Eimg = new Image("data/enemy.png");
	
		if(MapGen.map[(int)Px / MapGen.squareSize][(int)Py / MapGen.squareSize] == 0 || MapGen.map[(int)Px / MapGen.squareSize][(int)Py / MapGen.squareSize] == 2){
			MapGen.MapGeneration();
		}
		while (count < 20){ // makes sure everything gets calculated (debug)
			count++;
		}
		if(count >= 20){
			for(int i = 0; i < 10; i++){
				Random rn = new Random();
				int ranNum = rn.nextInt(MapGen.whiteSpace - 1);
				enemyArr[i] = ranNum;
			}
		}
		
		bullets = new LinkedList<Bullet>();
		enemy = new LinkedList<EnemyAI>();
		
		for(int k = 0; k < 10; k++){
			int mapInt = 0;
			for(int x = 1; x < MapGen.sx - 1; x++){
				for(int y = 1; y < MapGen.sy - 1; y++){
					if(MapGen.map[x][y] == 1){
						mapInt++;
						if(mapInt == enemyArr[k]){
							enemy.add(new EnemyAI(x,y, Eimg));
							MapGen.map[x][y] = 3;
						}
					}
				}
			}
		}
	}
	
	public void update(GameContainer container, StateBasedGame sbg, int delta) throws SlickException {
		
		Input input = container.getInput();
		if(container.getInput().isKeyPressed(Input.KEY_1)){
			sbg.enterState(1, new FadeOutTransition(), new FadeInTransition());
		}
		
		if(input.isKeyDown(Input.KEY_A)){
			Px -= delta * 0.1f * pSpeed;
			if(WallDetected((int)Px, (int)Py)){
				Px += delta * 0.1f * pSpeed;
			}
		}
		else if(input.isKeyDown(Input.KEY_D)){
			Px += delta * 0.1 * pSpeed;
			if(WallDetected((int)Px, (int)Py)){
				Px -= delta * 0.1f * pSpeed;
			}
		}
		else if(input.isKeyDown(Input.KEY_W)){
			Py -= delta * 0.1 * pSpeed;
			if(WallDetected((int)Px, (int)Py)){
				Py += delta * 0.1 * pSpeed;
			}
		}
		else if(input.isKeyDown(Input.KEY_S)){
			Py += delta * 0.1 * pSpeed;
			if(WallDetected((int)Px, (int)Py)){
				Py -= delta * 0.1 * pSpeed;
			}
		}
		// this code handle the rotation of the player towards the mouse
		float xpos = input.getMouseX();
		float ypos = input.getMouseY();
		float xDistance = xpos - Px;
		float yDistance = ypos - Py;
		double rotAngle = Math.toDegrees(Math.atan2(yDistance, xDistance));
		MC.setRotation((float)rotAngle-90);
		
		for(int i = 0; i < enemy.size(); i++){
			enemy.get(i).tick();
		}
		if(input.isKeyPressed(Input.KEY_SPACE) ) {
			bullets.add( new Bullet(Px,Py, new Vector2f(xpos - Px, ypos - Py)));
		}
		// spawn and remove bullets
		for(int i = 0; i < bullets.size(); i++){
			bullets.get(i).update(delta);
			if(WallDetected((int)bullets.get(i).getX(), (int)bullets.get(i).getY())){
				System.out.println("walled");
				bullets.get(i).setActive(false);
				bullets.remove(i);
			}
			/*else if(EnemyDetected((int)bullets.get(i).getX(), (int)bullets.get(i).getY())){
				System.out.println("ENEMY DIED");
				bullets.get(i).setActive(false);
				bullets.remove(i);
				enemy.remove(i);
			}*/
			for(int k = 0; k < enemy.size(); k++){
				if(bullets.get(i).cirB.intersects(enemy.get(k).rectE)){
					System.out.println("GOTCHA BITCHES");
					bullets.get(i).setActive(false);
					bullets.remove(i);
					enemy.remove(k);
				}
			}
		}
	}
	
	public void render(GameContainer cg, StateBasedGame sg, Graphics g) throws SlickException {
		// Creates the map, with white and black tiles
		// TO DO: CHANGE  COLORS TO IMAGES!
		for(int x = 0; x < Window.WIDTH / size; x++) {
			for(int y = 0; y < Window.HEIGHT / size; y++) {
				if(MapGen.map[x][y] == 1){
					g.setColor(Color.white); // Walkable Ground
				}
				else if(MapGen.map[x][y] == 0){
					g.setColor(Color.darkGray); // Non walkable Ground
				}
				else if(MapGen.map[x][y] == 2){
					g.setColor(Color.gray); // Stones
				}
				else if(MapGen.map[x][y] == 3){
					g.setColor(Color.white); // Walkable Ground
				}
				g.fillRect(x * size, y * size, size, size);
			}
		}
		// Health bar
		g.setColor(Color.green);
		g.fillRect(Px - 10, Py - 15, PStat.healthPoint / 5, 2);
		// Player
		MC.draw(Px - 10 , Py - 10);
		
		// enemy spawn

		for(EnemyAI ea : enemy){
			ea.render(g);
			g.draw(ea.rectE);
		}
		
		
		for( Bullet b : bullets )
		{
			b.render(cg, g);
			g.draw(b.cirB);
		}
	}
	
	// Few issues with the detection, but is working
	// MAKE IT MORE USABLE!
	private boolean WallDetected(int x, int y){
		boolean detect;
		if(MapGen.map[(x - 10) / size][(y - 10) / size] == 0){
			detect = true;
		}
		else if(MapGen.map[(x + 10) / size][(y + 10) / size] == 0){
			detect = true;
		}
		else if(MapGen.map[(x - 10) / size][(y - 10) / size] == 2){
			detect = true;
		}
		else if(MapGen.map[(x + 10) / size][(y + 10) / size] == 2){
			detect = true;
		}
		else {
			detect = false;
		}
		return detect;
	}
	
	private boolean EnemyDetected(int x, int y){
		boolean detect;
		if(MapGen.map[x / size][y/size] == 3){
			detect = true;
		}
		else {
			detect = false;
		}
		return detect;
	}

	public int getID() {
		return 1;
	}

}
