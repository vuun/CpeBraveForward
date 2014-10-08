package gamePackage;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class Swordman {
	public float x;
	public float y;
	public float DMG;
	public float fadetimes;
	public float animationTimes;
	public int animation = 0;
	public float walkLeftTimes = 0;

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
		shape = new Rectangle(x, y, MainGame.BASIC_SIZE, MainGame.BASIC_SIZE);
		image = new Image("res/MainChaGameHub9.png");
		this.mass = 3;
		speed = (float) ((1 / mass) / 12);
		this.hp = 200;
		this.atk = 0;
		this.DMG = 0;
		front = true;
	}

	public void render() {
		image.draw(x, y, 50, 86);
	}

	public void Flip() throws SlickException {
		// TODO Auto-generated method stub
		if (animation == 0) {
			image = new Image("res/MainChaGameHub5.png");
		}
		if (animation == 1) {
			image = new Image("res/MainChaGameHub6.png");
		}
		if (animation == 2) {
			image = new Image("res/MainChaGameHub7.png");
		}
		if (animation == 3) {
			image = new Image("res/MainChaGameHub8.png");
		}
		front = false;
	}

	public void Faceup() throws SlickException {
		// TODO Auto-generated method stub
		if (animation == 0) {
			image = new Image("res/MainChaGameHub13.png");
		}
		if (animation == 1) {
			image = new Image("res/MainChaGameHub14.png");
		}
		if (animation == 2) {
			image = new Image("res/MainChaGameHub15.png");
		}
		if (animation == 3) {
			image = new Image("res/MainChaGameHub16.png");
		}
		front = false;
	}

	public void Facedown() throws SlickException {
		// TODO Auto-generated method stub
		if (animation == 0) {
			image = new Image("res/MainChaGameHub1.png");
		}
		if (animation == 1) {
			image = new Image("res/MainChaGameHub2.png");
		}
		if (animation == 2) {
			image = new Image("res/MainChaGameHub3.png");
		}
		if (animation == 3) {
			image = new Image("res/MainChaGameHub4.png");
		}
		front = false;
	}

	public void FlipBack() throws SlickException {
		// TODO Auto-generated method stub
		if (animation == 0) {
			image = new Image("res/MainChaGameHub9.png");
		}
		if (animation == 1) {
			image = new Image("res/MainChaGameHub10.png");
		}
		if (animation == 2) {
			image = new Image("res/MainChaGameHub11.png");
		}
		if (animation == 3) {
			image = new Image("res/MainChaGameHub12.png");
		}
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

	void swordmanController(MainGame mainGame, Input input, int delta,
			Monster monster, GameContainer container) throws SlickException {
		// shape
		shape.setLocation(getX(), getY());
		// control
		if (x > -32 && input.isKeyDown(Input.KEY_LEFT)) {
			Flip();

			if (mainGame.swordAtBehind == false) {
				x -= delta * speed;
			}

		}
		if (y <= MainGame.SCREEN_HEIGHT / 2 + (64 * 3) + 1
				&& input.isKeyDown(Input.KEY_DOWN)) {
			Facedown();
			mainGame.swordAtBehind = false;
			y += delta * speed;
		}
		if (input.isKeyDown(Input.KEY_UP)) {
			Faceup();
			mainGame.swordAtBehind = false;
			if (y >= MainGame.SCREEN_HEIGHT / 2 - (32)) {
				y -= delta * speed;
			}
		}

		else if (shape.intersects(monster.shape) != true) {

			if (x + 64 < MainGame.SCREEN_WIDTH
					&& input.isKeyDown(Input.KEY_RIGHT)) {
				FlipBack();
				mainGame.swordAtBehind = false;
				x += delta * speed;
			}

		}
		// hp control
		if (hp <= 0) {
			hp = 0;
		}
		// x-axis check
		if (x <= -32) {
			x = -32;
			mainGame.gameIsOver = true;
			container.setPaused(true);
		}

		if (x + 64 >= MainGame.SCREEN_WIDTH) {
			x = MainGame.SCREEN_WIDTH - 64;
		}
		if (shape.intersects(monster.monsterBehind)) {
			mainGame.swordAtBehind = true;
		}
		walkLeftTimes +=  0.01 * mainGame.deltax / 20;
		if(walkLeftTimes >= 0.4){
			walkLeftTimes = 0;
			mainGame.swordAtBehind = false;
		}


	}

	void animationTimesControl(MainGame mainGame) {
		animationTimes += 0.01 * mainGame.deltax / 20;
		if (animationTimes >= 1) {
			animationTimes = 0;
			animation += 1;
			if (animation >= 4) {
				animation = 0;
			}
		}
	}
}
