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
	public float animationTimes;
	public int animation = 0;
	
	public float width;
	public float height;
	private Image image;
	protected Shape shape;
	protected Shape monsterBehind;
	
	public float mass;
	public float hp;
	public float atk;
	public float speed;
	
	public Monster(float x, float y, float width, float height, float mass) throws SlickException {
	    this.x = x;
	    this.y = y;
	    this.width = width;
	    this.height = height;
	    shape = new Rectangle(x , y, 10, MainGame.BASIC_SIZE);
	    monsterBehind = new Rectangle(x+11 ,y , 40, MainGame.BASIC_SIZE);
	    image = new Image("res/Dog1.png");
	    this.mass = (float) mass;
	    speed = (float) (2.5);
	    this.hp = 60;
	    this.atk = 1;
	    this.DMG = 0;
	    this.fadetimes = 0;
	  }
	
	public void render() {
			image.draw(x,y,width,height);
	}
	public void dogRun() throws SlickException {
		// TODO Auto-generated method stub	
		if(animation ==0){image = new Image("res/Dog1.png");}
		if(animation ==1){image = new Image("res/Dog2.png");}
		if(animation ==2){image = new Image("res/Dog3.png");}
		if(animation ==3){image = new Image("res/Dog4.png");}
		if(animation ==4){image = new Image("res/Dog5.png");}
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
		shape.setLocation(-20, -20);
		monsterBehind.setLocation(-20, -20);
	}
	



	
}
