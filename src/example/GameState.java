package example;

import java.util.LinkedList;
import java.util.Random;
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
	float Py;
	float Px;
	float pSpeed = PStat.playerSpeed;

	
	public int count = 0;
	
	private Image MC = null;
	private Image Eimg = null;
	private Image stoneImg = null;
	private Image lowImg = null;
	private Image highImg = null;
	private int[] enemyArr = new int[10];
	
	private Circle playerCirc = null;

	public GameState(int state){
	}
	
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		
		MC = PStat.getPlayerImg();
		Eimg = new Image("data/enemy.png");
		stoneImg = new Image("data/Stones.png");
		lowImg = new Image("data/LowGround.png");
		highImg = new Image("data/TopGround.png");
	
		Py = MapGen.sy / 2 * size;
		Px = MapGen.sx / 2 * size;
		MapGen.MapGeneration();
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
						if(mapInt == enemyArr[k] - 10){
							enemy.add(new EnemyAI(x,y, Eimg));
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
		playerCirc = new Circle(Px - size/2, Py - size/2, size/2);
		
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
				bullets.get(i).setActive(false);
			}
			for(int k = 0; k < enemy.size(); k++){
				if(playerCirc.intersects(enemy.get(k).rectE)){
					sbg.enterState(0);
				}
				if(bullets.get(i).cirB.intersects(enemy.get(k).rectE)){
					bullets.get(i).setActive(false);
					enemy.remove(k);
				}
			}
		}
		if(enemy.size() == 0){
			sbg.enterState(0);
		}
	}
	
	public void render(GameContainer cg, StateBasedGame sg, Graphics g) throws SlickException {
		for(int x = 0; x < Window.WIDTH / size; x++) {
			for(int y = 0; y < Window.HEIGHT / size; y++) {
				if(MapGen.map[x][y] == 1){
					lowImg.draw(x * size, y * size);
				}
				else if(MapGen.map[x][y] == 0){
					highImg.draw(x * size, y * size);
				}
				else if(MapGen.map[x][y] == 2){
					stoneImg.draw(x * size, y * size);
				}
			}
		}
		// Player
		MC.draw(Px - 10 , Py - 10);
		// enemy spawn
		for(EnemyAI ea : enemy){
			ea.render(g);
		}
		for( Bullet b : bullets )
		{
			b.render(cg, g);
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

	public int getID() {
		return 1;
	}

}
