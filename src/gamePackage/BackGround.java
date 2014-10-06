package gamePackage;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class BackGround {
	public float x;
	public float y;
	public float animationTimes;
	public int animation = 0;

	private Image image;
	public float mass;
	protected Shape shape;
	String name = "";

	public BackGround(float x, float y, String name) throws SlickException {
		this.x = x;
		this.y = y;
		if (name == "ground+cloud") {
			image = new Image("res/ground+cloud.png");
		}
		if (name == "dark") {
			image = new Image("res/dark.png");
		}
		if (name == "goddess") {
			shape = new Rectangle(x, y, 32, MainGame.BASIC_SIZE);
			image = new Image("res/fairy1.png");
		}
		this.mass = 1;
	}

	public void render() {
		image.draw(x, y);

	}

	public void goddessFlash() throws SlickException {
		// TODO Auto-generated method stub
		if (animation == 0) {
			image = new Image("res/fairy1.png");
		}
		if (animation == 1) {
			image = new Image("res/fairy2.png");
		}
		if (animation == 2) {
			image = new Image("res/fairy3.png");
		}
		if (animation == 3) {
			image = new Image("res/fairy4.png");
		}
		if (animation == 4) {
			image = new Image("res/fairy5.png");
		}
		if (animation == 5) {
			image = new Image("res/fairy6.png");
		}
		if (animation == 6) {
			image = new Image("res/fairy7.png");
		}
		if (animation == 7) {
			image = new Image("res/fairy8.png");
		}
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

	void goddessController(MainGame mainGame, GameContainer container, Input input) {
		if (mainGame.swordman.shape.intersects(shape)
				&& input.isKeyDown(Input.KEY_Z)) {
			// container.setPaused(true);
			x = (float) Math.random() * 600;
			mainGame.times = 30;
		}
		shape.setLocation(getX(), getY());
	}

	void animationTimesControl(MainGame mainGame) {
		animationTimes += 0.01 * mainGame.deltax / 20;
		if (animationTimes >= 1) {
			animationTimes = 0;
			animation += 1;
			if (animation >= 8) {
				animation = 0;
			}
		}
	}

}
