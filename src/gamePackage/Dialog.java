package gamePackage;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Dialog {
	public float x;
	public float y;
	public float animationTimes;
	public int animation = 0;
	
	private Image image;
	

	public Dialog(float x, float y) throws SlickException {
	    this.x = x;
	    this.y = y;	
	    image = new Image("res/BG.png");
	  }
	
	public void render() {
		image.draw(x,y);

	}
	public void dialogChange() throws SlickException {
		// TODO Auto-generated method stub	
		if(animation ==0){image = new Image("res/Maincha_fairy dialog01.png");}
		if(animation ==1){image = new Image("res/Maincha_fairy dialog02.png");}
		if(animation ==2){image = new Image("res/Maincha_fairy dialog03.png");}
		
		if(animation ==4){image = new Image("res/Maincha_fairy dialog04.png");}

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

