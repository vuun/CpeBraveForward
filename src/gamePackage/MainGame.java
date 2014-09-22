package gamePackage;
import java.awt.Container;

import org.lwjgl.opengl.XRandR.Screen;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;


public class MainGame extends BasicGame{
	protected static int SCREEN_WIDTH = 680;
	protected static int SCREEN_HEIGHT = 480;
	protected static int BASIC_SIZE = 64;
	protected double times = 0;
	Swordman swordman;
	BackGround BG;
	Monster monster;
	Boolean collide = false;
	Boolean reinit = false;
	public MainGame(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		// TODO Auto-generated method stub
		//test
		g.drawString("Player Hp : "+swordman.hp, SCREEN_WIDTH/2+100, 30);
		g.drawString("Monster Hp : "+monster.hp, SCREEN_WIDTH/2+100, 50);
		 if( swordman.hp < 0 ){
			 g.drawString("YOU DIED", SCREEN_WIDTH/2+100, 10);
		 }
		 
		swordman.render();
		BG.render();
		if(monster.isDestroy() == false)
		{monster.render();}
		drawTimes(g);
		//g.drawString("" + collide, SCREEN_WIDTH/2+100, 10);
	
	}

	private void drawTimes(Graphics g) {
		g.drawString("" + (float)times, SCREEN_WIDTH/2, 10);
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		// TODO Auto-generated method stub
		change_BG_Color(container);
		swordman = new Swordman( SCREEN_WIDTH/2, SCREEN_HEIGHT/2);
		monster = new Monster( SCREEN_WIDTH, SCREEN_HEIGHT/2, BASIC_SIZE , BASIC_SIZE);
		BG = new BackGround(SCREEN_WIDTH/2 + 20, SCREEN_HEIGHT/2 + -170);
		timesInit();
	}

	private void timesInit() {
		times = 30;
	}

	private void change_BG_Color(GameContainer container) {
		Color background = new Color(10, 200, 256); //L.Blue
	    container.getGraphics().setBackground(background);
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		// TODO Auto-generated method stub
		 Input input = container.getInput();
		 playerController(input, delta);
		 timesController();
		 BGController();
		 monsterController();
		 battleController();
		 REPLAYABLE(container, input);

	}

	private void REPLAYABLE(GameContainer container, Input input) throws SlickException {
		if( reinit == true ){
			container.reinit();
			reinit = false;
		}
	}


	

	private void battleController() throws SlickException {
		if( swordman.shape.intersects(monster.shape) != true){
			monster.x -= 2;	 
		 }
		 else{
			 if( swordman.mass > monster.mass){
				 
				 //atk animation
				 swordman.x -= monster.mass * 50 * (1/swordman.mass);
				 monster.x += (1/monster.mass) * 20; 
				 monster.x += swordman.mass * 5;
				 //battle
				 swordman.hp -= monster.atk;
				 if(swordman.front==true){
					 monster.hp -= swordman.atk;
			 	 }
				 //died
				 if( monster.hp <= 0 ){
					monster.shape.setLocation(-20, -20);
					monster.destroy();
					
					reinit=true;
				 }
				 

			 }
		 }
		if( monster.x < -100 ){
			monster.setPosition();
		}
	}

	private void monsterController() {

		//shape
		if(monster.isDestroy() == false)
		{monster.shape.setLocation(monster.getX(), monster.getY());}
		//
	}

	private void BGController() {
		BG.x -= 0.2;
		if( BG.x < -100 ){
			BG.setPosition();
		}
	}

	private void timesController() {
		times -= 0.001;
	}
	
	private void playerController(Input input, int delta) throws SlickException {
		// TODO Auto-generated method stub
		//shape
		swordman.shape.setLocation(swordman.getX(),swordman.getY());
		//control
		if (swordman.x > -1 && input.isKeyDown(Input.KEY_LEFT)) {
		    	swordman.Flip();
		    	swordman.x -= swordman.speed;
		}
		if (swordman.y <= SCREEN_HEIGHT-100 && input.isKeyDown(Input.KEY_DOWN)) {		    
	    	swordman.y += swordman.speed;
		}
		if (swordman.y >= SCREEN_HEIGHT/2 && input.isKeyDown(Input.KEY_UP)) {  	
	    	swordman.y -= swordman.speed;
		}
		
		else if( swordman.shape.intersects(monster.shape) != true){
			
			 if (swordman.x+64 < SCREEN_WIDTH && input.isKeyDown(Input.KEY_RIGHT)) {
				 	swordman.FlipBack();
				 	swordman.x += swordman.speed;
			    }
				 
		}
		//x-axis check
		if(swordman.x <= 0){
			swordman.x = 0;
		}

		if(swordman.x+64 >= SCREEN_WIDTH){
			swordman.x = SCREEN_WIDTH-64;
		}
		 

	}

	public static void main(String[] args) {
		try {
				  MainGame game = new MainGame("BraveForward");
				  AppGameContainer appgc = new AppGameContainer(game);
				  appgc.setDisplayMode(SCREEN_WIDTH,SCREEN_HEIGHT, false);
				  appgc.setTargetFrameRate(100);
				  appgc.start();
				} catch (SlickException e) {
				  e.printStackTrace();
				}
		  }
}
