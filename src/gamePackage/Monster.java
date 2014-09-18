package gamePackage;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Monster {
	public float x;
	public float y;
	public float width;
	public float height;
	private Image image;
	public float mass;
	public Monster(float x, float y, float width, float height) throws SlickException {
	    this.x = x;
	    this.y = y;
	    this.width = width;
	    this.height = height;
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

}
