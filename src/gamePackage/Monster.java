package gamePackage;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class Monster {
	public float x;
	public float y;
	public float width;
	public float height;
	private Image image;
	public float mass;
	protected Shape shape;
	public Monster(float x, float y, float width, float height) throws SlickException {
	    this.x = x;
	    this.y = y;
	    this.width = width;
	    this.height = height;
	    shape = new Rectangle(x , y, MainGame.BASIC_SIZE, MainGame.BASIC_SIZE);
	    image = new Image("res/Monster.png");
	    this.mass = 1;
	  }
	public void render() {
			image.draw(x,y,width,height);
	}
	public void setPosition() {
		// TODO Auto-generated method stub
		x = MainGame.SCREEN_WIDTH;
		
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
