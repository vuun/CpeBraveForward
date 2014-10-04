package gamePackage;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class Monster {
	public boolean isDestroy = false;
	public float DMG;
	public float x;
	public float y;
	public float fadetimes;
	
	public float width;
	public float height;
	private Image image;
	protected Shape shape;
	
	public float mass;
	public float hp;
	public float atk;
	public float speed;
	
	public Monster(float x, float y, float width, float height) throws SlickException {
	    this.x = x;
	    this.y = y;
	    this.width = width;
	    this.height = height;
	    shape = new Rectangle(x , y, MainGame.BASIC_SIZE, MainGame.BASIC_SIZE);
	    image = new Image("res/Monster.png");
	    this.mass = (float) 2.2;
	    speed = (float) ((1/mass) * (0.2) * 17);
	    this.hp = 60;
	    this.atk = 1;
	    this.DMG = 0;
	    this.fadetimes = 0;
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
	
	public void destroy() throws SlickException {
		// TODO Auto-generated method stub
		//image.destroy();
		isDestroy = true;
	}
	



	
}
