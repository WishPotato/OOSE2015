package example;

import java.util.Iterator;
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


public class GameState extends BasicGameState {
	MapGenerator MapGen = new MapGenerator();
	PlayerStats PStat = new PlayerStats();
	EnemyAI EAI = new EnemyAI();
	
	private LinkedList<Bullet> bullets;
	
	int size = MapGen.squareSize;
	float Py = MapGen.sy / 2 * size;
	float Px = MapGen.sx / 2 * size;
	float pSpeed = PStat.playerSpeed;

	
	public int count = 0;
	
	private Image MC = null;
	private Image EImg = null;

	
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {		
		MapGen.MapGeneration();
		MC = PStat.getPlayerImg();
		EImg = EAI.getEnemyImg();
		if(MapGen.map[(int)Px / MapGen.squareSize][(int)Py / MapGen.squareSize] == 0){
			MapGen.MapGeneration();
		}
		while (count < 20){ // makes sure everything gets calculated (debug)
			count++;
		}
		if(count >= 20){
			for(int i = 0; i < EAI.spawnAmt; i++){
				Random rn = new Random();
				int ranNum = rn.nextInt(MapGen.whiteSpace - 1);
				EAI.enemyArr[i] = ranNum;
			}
		}
		
		bullets = new LinkedList<Bullet>();
		
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
		// this code handle the rotation of the player towards the mouse
		float xpos = input.getMouseX();
		float ypos = input.getMouseY();
		float xDistance = xpos - Px;
		float yDistance = ypos - Py;
		double rotAngle = Math.toDegrees(Math.atan2(yDistance, xDistance));
		MC.setRotation((float)rotAngle-90);
		
		Iterator<Bullet> i = bullets.iterator();
		while( i.hasNext() )
		{
			Bullet b = i.next();
			if( b.isActive() )
			{
				b.update(delta);
			}
			else
			{
				i.remove();
			}
		}
		//System.out.println(bullets.size());
		if( container.getInput().isKeyPressed(Input.KEY_SPACE) )
		{
			bullets.add( new Bullet( new Vector2f(500,0) , new Vector2f(300,0) ) );
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
					g.setColor(Color.darkGray); // Nonwalkable Ground
				}
				else if(MapGen.map[x][y] == 2){
					g.setColor(Color.gray); // Stones
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
		for(int i = 0; i < EAI.spawnAmt; i++){
			int mapInt = 0;
			for(int x = 1; x < MapGen.sx - 1; x++){
				for(int y = 1; y < MapGen.sy - 1; y++){
					if(MapGen.map[x][y] == 1){
						mapInt++;
						if(mapInt == EAI.enemyArr[i]){
							g.setColor(Color.red);
							g.fillRect(x * size - 10, y * size - 15, EAI.healthPoint / 3 , 2);
							EImg.draw(x * size - 10, y * size - 10);
							break;
						}
					}
				}
			}
		}
		
		for( Bullet b : bullets )
		{
			b.render(cg, sg, g);
		}
	}
	
	// Few issues with the detection, but is working
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
