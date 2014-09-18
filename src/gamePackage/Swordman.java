package gamePackage;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Swordman {
	public float x;
	public float y;
	private Image image;
	public float mass;
	public Swordman(float x, float y) throws SlickException {
	    this.x = x;
	    this.y = y;
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
}
