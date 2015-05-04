package example;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.Image;

public class MainMenu extends BasicGameState{
	
	Image startGame = null;
	Image tryAgain = null;
	Image exit = null;
	Image high = null;
	Image low = null;
	public boolean win;
	
	public MainMenu(int state){
		
	}
	public MainMenu(){
		
	}
	
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		high = new Image("data/TopGround.png");
		low = new Image("data/LowGround.png");
		startGame = new Image("data/Play.png");
		exit = new Image("data/exit.png");
		tryAgain = new Image("data/tryagain.png");
	}
	
	public void update(GameContainer container, StateBasedGame sbg, int delta) throws SlickException {
		int posX = Mouse.getX();
		int posY = Mouse.getY();
		
		if((posX > Window.WIDTH / 2 - 150  && posX < Window.WIDTH / 2 + 150) && (posY > Window.HEIGHT /2 + 140 && posY < Window.HEIGHT /2 + 200)){
			System.out.println("asd");
			if(Mouse.isButtonDown(0)){
				sbg.enterState(1);
			}
		}
		if((posX > Window.WIDTH / 2 - 150  && posX < Window.WIDTH / 2 + 150) && (posY > Window.HEIGHT /2 - 260 && posY < Window.HEIGHT /2 - 200)){
			System.out.println("dsa");
			if(Mouse.isButtonDown(0)){
				container.exit();
			}
		}
	}
	
	public void render(GameContainer cg, StateBasedGame sg, Graphics g) throws SlickException {	
		for(int x = 0; x < Window.WIDTH / 20; x++){
			for(int y = 0; y < Window.HEIGHT / 20; y++){
				if(x == 0 || x == Window.WIDTH / 20 - 1 || y == 0 || y == Window.HEIGHT / 20 - 1){
					high.draw(x * 20,y * 20);
				}
				else {
					low.draw(x * 20, y * 20);
				}
			}
		}
		startGame.draw(Window.WIDTH / 2 - 150, Window.HEIGHT /2 - 200);
		exit.draw(Window.WIDTH / 2 - 150, Window.HEIGHT /2 + 200);
	}
	
	public int getID() {
		// TODO Auto-generated method stub
		return 0;
	}

}
