package gamePackage;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class Swordman {
	public float x;
	public float y;
	private Image image;
	public float mass;
	protected Shape shape = null;
	public Swordman(float x, float y) throws SlickException {
	    this.x = x;
	    this.y = y;
	    shape = new Rectangle(x , y, MainGame.BASIC_SIZE, MainGame.BASIC_SIZE);
	    image = new Image("res/MainCha.png");
	    this.mass = 1;
	  }
	public void render() {
		    image.draw(x,y);
	}
	public void Flip() throws SlickException {
		// TODO Auto-generated method stub	
		image = new Image("res/MainChaFlip.png");
	}
	public void FlipBack() throws SlickException {
		// TODO Auto-generated method stub
		image = new Image("res/MainCha.png");
	}
	public float getY() {
		// TODO Auto-generated method stub
		return y;
	}
	public float getX() {
		// TODO Auto-generated method stub
		return x;
	}
}
