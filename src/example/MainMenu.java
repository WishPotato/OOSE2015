package example;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.Image;

public class MainMenu extends BasicGameState{
	
	Image startGame;
	Image exit;
	int imgPosX;
	int imgPosY;
	
	public MainMenu(int state){
		
	}
	
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		
	}
	
	public void update(GameContainer container, StateBasedGame sbg, int delta) throws SlickException {
		int posX = Mouse.getX();
		int posY = Mouse.getY();
		
		if((posX > imgPosX  && posX < 311) && (posY > 209 && posY < 260)){
			if(Mouse.isButtonDown(0)){
				sbg.enterState(1);
			}
		}
	}
	
	public void render(GameContainer cg, StateBasedGame sg, Graphics g) throws SlickException {
		g.drawString("Whalecum", Window.WIDTH/2, Window.HEIGHT/2);
	}
	
	public int getID() {
		// TODO Auto-generated method stub
		return 0;
	}

}
