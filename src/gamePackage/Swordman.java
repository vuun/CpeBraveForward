package gamePackage;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class Swordman {
	public float x;
	public float y;
	public float DMG;
	public float fadetimes;
	
	public float mass;
	public float hp;
	public float atk;
	public float speed;
	
	private Image image;
	protected Shape shape = null;
	public Boolean front = true;
	
	public Swordman(float x, float y) throws SlickException {
	    this.x = x;
	    this.y = y;
	    shape = new Rectangle(x , y, MainGame.BASIC_SIZE, MainGame.BASIC_SIZE);
	    image = new Image("res/MainCha.png");
	    this.mass = 3;
	    speed = (float) ((1/mass)/12);
	    this.hp = 200;
	    this.atk = 2;
	    this.DMG = 0;
	    front = true;
	  }
	
	public void render() {
		    image.draw(x,y);
	}
	
	public void Flip() throws SlickException {
		// TODO Auto-generated method stub	
		image = new Image("res/MainChaFlip.png");
		front = false;
	}
	
	public void FlipBack() throws SlickException {
		// TODO Auto-generated method stub
		image = new Image("res/MainCha.png");
		front = true;
	}
	
	public float getY() {
		// TODO Auto-generated method stub
		return y;
	}
	
	public float getX() {
		// TODO Auto-generated method stub
		return x;
	}
	
	public void destroy() throws SlickException {
		// TODO Auto-generated method stub
		image.destroy();
	}
	
	public boolean isDestroy() {
		// TODO Auto-generated method stub
		return image.isDestroyed();
	}
}
