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
	//Monster monster;
	Monster[] monsters;
	Boolean collide = false;
	Boolean reinit = false;
	public MainGame(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		// TODO Auto-generated method stub
		testUnitHP(g);
		damageGraphicController(g);

		swordman.render();
		BG.render();
//		if(monster.isDestroy() == false)
//		{monster.render();}
	
		for (Monster monster : monsters) {
			if(monster.isDestroy() == false){
		      monster.render();
		    }
		}

		drawTimes(g);
		//g.drawString("" + collide, SCREEN_WIDTH/2+100, 10);
	
	}

	private void testUnitHP(Graphics g) {
		//test each hp
		g.drawString("Player Hp : "+swordman.hp, SCREEN_WIDTH/2+100, 30);
		for (Monster monster : monsters){
			if(monster.hp > 0){
				g.drawString("Monster Hp : "+monster.hp, monster.x, monster.y-50);
			}
		}
		if( swordman.hp < 0 ){
			 g.drawString("YOU DIED", SCREEN_WIDTH/2+100, 10);
		}
		//
	}

	private void damageGraphicController(Graphics g) {
		//damage
		for (Monster monster : monsters) {
			if(swordman.shape.intersects(monster.shape) == true  || swordman.fadetimes > 0){
				g.drawString(""+(int)swordman.DMG, swordman.x + 64, swordman.y - 10);
				if(swordman.fadetimes <= 0){
					swordman.fadetimes = (float) 1.5;
				}
			}
			swordman.fadetimes -= 0.1;
			if(swordman.fadetimes <= 0){
				swordman.fadetimes = 0;
			}
			if(swordman.shape.intersects(monster.shape) == true || monster.fadetimes > 0){
				g.drawString(""+(int)monster.DMG, monster.x + 64, monster.y - 10);
				
				if(monster.fadetimes <= 0){
					monster.fadetimes = (float) 1.5;
				}
			}
				
				monster.fadetimes -= 0.1;
				if(monster.fadetimes <= 0){
					monster.fadetimes = 0;
				}
	
			}
			
		}

	private void drawTimes(Graphics g) {
		g.drawString("" + (float)times, SCREEN_WIDTH/2, 10);
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		// TODO Auto-generated method stub
		change_BG_Color(container);
		swordman = new Swordman( SCREEN_WIDTH/2, SCREEN_HEIGHT/2);
		//monster = new Monster( SCREEN_WIDTH, SCREEN_HEIGHT/2, BASIC_SIZE , BASIC_SIZE);
		monsters = new Monster[3];
	    for (int i = 0; i < 3; i++) {
	      monsters[i] = new Monster( SCREEN_WIDTH, SCREEN_HEIGHT/2 + (64*i), BASIC_SIZE , BASIC_SIZE);
	    }
	    //
		BG = new BackGround(SCREEN_WIDTH/2 + 20, SCREEN_HEIGHT/2 + -170);
		timesInit();
		
		//others
		swordman.fadetimes = 1;
		for(Monster monster : monsters){
			monster.fadetimes = 1;
		}
		//
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
		 for (Monster monster : monsters){
			 playerController(input, delta, monster);
			 timesController();
			 BGController();
			 monsterController(monster);
			 battleController(monster);
			 //REPLAYABLE(container, input);
		 }

	}

	private void REPLAYABLE(GameContainer container, Input input) throws SlickException {
		if( reinit == true ){
			container.reinit();
			reinit = false;
		}
	}


	

	private void battleController(Monster monster) throws SlickException {
			if( swordman.shape.intersects(monster.shape) != true){
				monster.x -= 2;	 
			 }
			 else{
				 
			 //atk animation
			 swordman.x -= monster.mass * 50 * (1/swordman.mass);
			 monster.x += (1/monster.mass) * 20; 
			 monster.x += swordman.mass * 5;
			 //battle
			 swordman.hp -= monster.atk;
			 swordman.DMG = monster.atk;
			 if(swordman.front==true){
				 monster.hp -= swordman.atk;
				 monster.DMG = swordman.atk;
		 	 }
			 else{
				 monster.DMG = 0;
			 }
			 //died
			 if( monster.hp <= 0 ){
				monster.shape.setLocation(-20, -20);
				monster.destroy();
				
				reinit=true;
			 }
			 
		
			 }
			if( monster.x < -100 ){
				monster.setPosition();
			}
	}

	private void monsterController(Monster monster) {
		
		//shape
		if(monster.isDestroy() == false)
		{monster.shape.setLocation(monster.getX(), monster.getY());}
		//
	}

	private void BGController() {
		BG.x -= 1;
		if( BG.x < -100 ){
			BG.setPosition();
		}
	}

	private void timesController() {
		times -= 0.01;
		if(times <= 0){
			times = 0;
		}
	}
	
	private void playerController(Input input, int delta, Monster monster) throws SlickException {
		// TODO Auto-generated method stub
		//shape
		swordman.shape.setLocation(swordman.getX(),swordman.getY());
		//control
		if (swordman.x > -1 && input.isKeyDown(Input.KEY_LEFT)) {
		    	swordman.Flip();
		    	swordman.x -= swordman.speed;
		}
		if (swordman.y <= SCREEN_HEIGHT/2 +(64*3)+1 && input.isKeyDown(Input.KEY_DOWN)) {		    
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
		//hp control
		if(swordman.hp <= 0){
			swordman.hp = 0;
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
