package gamePackage;



import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;


public class MainGame extends BasicGame{
	protected static int SCREEN_WIDTH = 680;
	protected static int SCREEN_HEIGHT = 480;
	protected static int BASIC_SIZE = 64;
	protected static int MON_NUM = 12;
	protected double times = 0;
	protected double totaltimes = 0;
	protected double levelRandomDelay = 0;
	protected double lv = 0;
	float deltaX = 0;
	Swordman swordman;
	BackGround BG;
	BackGround darkBG;
	BackGround goddess;
	Dialog intro;
	Monster[] monsters;
	Boolean collide = false;
	Boolean reinit = false;
	Boolean swordAtBehind = false;
	Boolean gameIsOver = false;
	
	public MainGame(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		BG.render();
		goddess.render();
		swordman.render();
		for (Monster monster : monsters) {
			if(monster.isDestroy == false){
		      monster.render();
		    }
			
//			if(swordman.shape.intersects(monster.monsterBehind) == true){
//				g.drawString("XXXXXXXX", monster.x, monster.y-50);
//			}
		}
		Others(g);
		//damageGraphicController(g);
		drawTimes(g);
		darkBG.render();
		if(intro.animation <= 2 || gameIsOver == true){
			intro.render();
		}
		//g.drawString("" + swordAtBehind, SCREEN_WIDTH/2+100, 10);
	
	}

	private void Others(Graphics g) {
		//test each hp
		if(gameIsOver == true){
			g.drawString("Game Over", SCREEN_WIDTH/2, 70);
		}
//		for (Monster monster : monsters){
//			if(monster.hp > 0){
//				g.drawString(""+(int)monster.hp, monster.x, monster.y-50);
//			}
//		}
		String fastMsg = "";
		if(lv == 1){
			fastMsg = "Stamina reduce rate is Faster!!";
		}
		if(lv == 2){
			fastMsg = "Stamina reduce rate is MORE Faster!!";
		}
		if(lv == 3){
			fastMsg = "Stamina reduce rate is Nearly FASTEST!!";
		}
		if(lv == 4){
			fastMsg = "Stamina reduce rate is GODLIKE!!";
		}
		g.drawString(""+fastMsg, SCREEN_WIDTH/2, 30);

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
			swordman.fadetimes -= 0.07;
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
		g.drawString("Stamina : " + (int)times, SCREEN_WIDTH/2, 10);
		g.drawString("Total Times: " + (int)totaltimes, SCREEN_WIDTH/2, 50);
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		change_BG_Color(container);
		container.pause();
		BG = new BackGround(0, 0, "ground+cloud");
		darkBG = new BackGround(0, 0, "dark");
		swordman = new Swordman( SCREEN_WIDTH/2, SCREEN_HEIGHT / 2);
		goddess= new BackGround( swordman.getX() + 64, 175, "goddess");
		intro = new Dialog(0,352);
		monsterInit();
		timesInit();
		
		//others
		swordman.fadetimes = 1;
		for(Monster monster : monsters){
			monster.fadetimes = 1;
		}
		//
	}

	private void monsterInit() throws SlickException {
		//monster = new Monster( SCREEN_WIDTH, SCREEN_HEIGHT/2, BASIC_SIZE , BASIC_SIZE);
		monsters = new Monster[MON_NUM];
	    for (int i = 0; i < MON_NUM; i++) {
	    	monsters[i] = new Monster( SCREEN_WIDTH, SCREEN_HEIGHT / 2 + (64 * (i % 4)), BASIC_SIZE + 10 , BASIC_SIZE, (float) 1.7);
	    	if(i>=4){
	    		monsters[i] = new Monster( SCREEN_WIDTH + 64 * 3, SCREEN_HEIGHT/2 + (64 * (i % 4)), BASIC_SIZE + 10, BASIC_SIZE, (float) 1.7);
	    	}
	    	if(i>=8){
	    		monsters[i] = new Monster( SCREEN_WIDTH + 64 * 6, SCREEN_HEIGHT/2 + (64 * (i % 4)), BASIC_SIZE + 10, BASIC_SIZE, (float) 1.7);
	    	}

	    }
	    //
	}

	private void timesInit() {
		times = 30.5;
	}

	private void change_BG_Color(GameContainer container) {
		Color background = new Color(10, 200, 256); //L.Blue
	    container.getGraphics().setBackground(background);
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		 deltaX = delta;
		 Input input = container.getInput();
		 if(input.isKeyDown(Input.KEY_A))
			 totaltimes += 10;
		 //monster involved
		 for (Monster monster : monsters){
			 swordman.playerController(this, input, delta, monster, container);
			 timesAndAnimationController(container, monster, input);
			 BGController();
			 monsterController(monster);
			 battleController(monster);
			 REPLAYABLE(container, input);
			 goddessController(container, input);
			 monster.dogRun();
		 }
		 //
		 //others controller
		 levelController(container);
		 goddess.goddessFlash();
		 intro.dialogChange();
		 gameMenu(container, input);
		 //

	}

	private void levelController(GameContainer container) {
		for (Monster monster : monsters){
			levelRandomDelay += 0.01 *deltaX /60;
			if(levelRandomDelay > 0.5) {
				monster.speed = (float) ( (2.5) * (float)(1+Math.random()));
				levelRandomDelay = 0;
			}
		}
		if( (int)(totaltimes) > 45 ){
			lv = 1;
			times -= 0.01 *deltaX /60 /4;
		}
		if( (int)(totaltimes) > 95 ){
			lv = 2;
			times -= 0.01 *deltaX /60/2;
		}
		if( (int)(totaltimes) > 150 ){
			lv = 3;
			times -= 0.01 *deltaX /60;
		}
		if( (int)(totaltimes) > 250 ){
			lv = 4;
			times -= 0.01 *deltaX /60;
		}
	}

	private void goddessController(GameContainer container, Input input) {
		if(swordman.shape.intersects(goddess.shape) && input.isKeyDown(Input.KEY_Z)){
			 //container.setPaused(true);
			 goddess.x = (float) Math.random()*600;
			 times = 30;
		 }
		goddess.shape.setLocation(goddess.getX(),goddess.getY());
	}

	private void gameMenu(GameContainer container, Input input) {

		if(input.isKeyPressed(Input.KEY_ENTER)){
			reinit = true;
		}
	}

	private void REPLAYABLE(GameContainer container, Input input) throws SlickException {
		if( reinit == true ){
			container.reinit();
			totaltimes = 0;
			gameIsOver = false;
			reinit = false;
		}
	}


	

	private void battleController(Monster monster) throws SlickException {
			if( swordman.shape.intersects(monster.shape) != true){
				monster.x -= monster.speed * deltaX / 17;	 
			 }
			 else{
			 //atk animation (bounce)
			 swordman.x -= monster.mass * 50 * (1 / swordman.mass) * deltaX / 17;
			 monster.x += (1 / monster.mass) * 20 * deltaX / 17; 
			 monster.x += swordman.mass * 5 * deltaX / 17;
			 //
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

		
			 }
	}

	private void monsterController(Monster monster) throws SlickException {
		
		//shape
		if(monster.isDestroy == false)
		{
			monster.shape.setLocation(monster.getX(), monster.getY());
			monster.monsterBehind.setLocation(monster.getX()+6, monster.getY());
		}
		//
		 //died
		 if( monster.hp <= 0 ){
			monster.destroy();			
		 }
		 
		if( monster.x <= 0){
			//monster.x = 0;
			monster.setPosition();
		}
		
	}

	private void BGController() {
		//BG.x -= 0.2*deltax /17;
		if( BG.x < -200 ){
			BG.setPosition();
		}
	}

	private void timesAndAnimationController(GameContainer container, Monster monster, Input input) {
		//timescontrol
		times -= 0.01 *deltaX / 60;
		totaltimes += 0.01 *deltaX / 60;
		if(times <= 0){
			times = 0;
			container.setPaused(true);
			gameIsOver = true;
			
		}
		//
		//animation
		swordman.animationTimes += 0.01 *deltaX / 20;
		if(swordman.animationTimes >= 1){
			swordman.animationTimes = 0;
			swordman.animation += 1;
			if(swordman.animation >= 4){
				swordman.animation = 0;
			}
		}
		goddess.animationTimes += 0.01 *deltaX / 20;
		if(goddess.animationTimes >= 1){
			goddess.animationTimes = 0;
			goddess.animation += 1;
			if(goddess.animation >= 8){
				goddess.animation = 0;
			}
		}
		monster.animationTimes += 0.01 *deltaX / 1.5;
		if(monster.animationTimes >= 1){
			monster.animationTimes = 0;
			monster.animation += 1;
			if(monster.animation >= 5){
				monster.animation = 0;
			}
		}
		
		if(input.isKeyPressed(Input.KEY_Z)){
			intro.animation += 1;
			if(intro.animation == 3){
				container.resume();
				intro.animation += 1;
			}
		}
		//

	}
	
	public static void main(String[] args) {
		try {
				  MainGame game = new MainGame("BraveForward");
				  AppGameContainer appgc = new AppGameContainer(game);
				  appgc.setDisplayMode(SCREEN_WIDTH,SCREEN_HEIGHT, false);
				  appgc.setTargetFrameRate(60);
				  appgc.setVSync(true);
				  //appgc.setMinimumLogicUpdateInterval(1);
				  //appgc.setMaximumLogicUpdateInterval(1);
				  //appgc.setShowFPS(false);
				  //appgc.setClearEachFrame(true);
				  appgc.start();
				} catch (SlickException e) {
				  e.printStackTrace();
				}
		  }
}
