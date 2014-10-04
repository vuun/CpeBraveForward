package gamePackage;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class BackGround {
	public float x;
	public float y;
	
	private Image image;
	public float mass;
	protected Shape shape;
	String name = "";
	
	public BackGround(float x, float y,String name) throws SlickException {
	    this.x = x;
	    this.y = y;
	    if(name == "ground")
	    {image = new Image("res/ground.png");}
	    if(name == "cloud")
	    {image = new Image("res/cloud.png");}
	    if(name == "dark")
	    {image = new Image("res/dark.png");}
	    if(name == "goddess")
	    {
	    	shape = new Rectangle(x , y, MainGame.BASIC_SIZE*2, MainGame.BASIC_SIZE*3);
	    	image = new Image("res/goddess.png");	
	    }
	    this.mass = 1;
	  }
	
	public void render() {
		    image.draw(x,y);
	}
	
	public void setPosition() {
		// TODO Auto-generated method stub
		x = 0;
		
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
