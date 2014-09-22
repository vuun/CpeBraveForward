package gamePackage;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class BackGround {
	public float x;
	public float y;
	
	private Image image;
	public float mass;
	
	public BackGround(float x, float y) throws SlickException {
	    this.x = x;
	    this.y = y;
	    image = new Image("res/BG.png");
	    this.mass = 1;
	  }
	
	public void render() {
		    image.draw(x,y);
	}
	
	public void setPosition() {
		// TODO Auto-generated method stub
		x = MainGame.SCREEN_WIDTH;
		
	}

}
