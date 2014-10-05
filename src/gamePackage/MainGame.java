package gamePackage;



import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;


public class MainGame extends BasicGame{
	protected static int SCREEN_WIDTH = 680;
	protected static int SCREEN_HEIGHT = 480;
	protected static int BASIC_SIZE = 64;
	protected static int MON_NUM= 12;
	protected double times = 0;
	protected double totaltimes = 0;
	float deltax = 0;
	Swordman swordman;
	BackGround BG;
	BackGround darkBG;
	BackGround goddess;
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
			
			if(swordman.shape.intersects(monster.monsterBehind) == true){
				g.drawString("XXXXXXXX", monster.x, monster.y-50);
			}
		}
		Others(g);
		damageGraphicController(g);
		drawTimes(g);
		darkBG.render();
		//g.drawString("" + swordAtBehind, SCREEN_WIDTH/2+100, 10);
	
	}

	private void Others(Graphics g) {
		//test each hp
		if(gameIsOver==true){
			g.drawString("Game Over", SCREEN_WIDTH/2, 70);
		}
		for (Monster monster : monsters){
			if(monster.hp > 0){
				g.drawString(""+monster.hp, monster.x, monster.y-50);
			}
		}
		g.drawString("Dont Reach the Dark", SCREEN_WIDTH/2, 30);

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
		g.drawString("Timer : " + (float)times, SCREEN_WIDTH/2, 10);
		g.drawString("Total : " + (float)totaltimes, SCREEN_WIDTH/2, 50);
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		change_BG_Color(container);
		BG = new BackGround(0, 0, "ground+cloud");
		darkBG = new BackGround(0, 0, "dark");
		goddess= new BackGround((float) Math.random()*680, 75, "goddess");
		swordman = new Swordman( SCREEN_WIDTH/2, SCREEN_HEIGHT/2);
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
	    	monsters[i] = new Monster( SCREEN_WIDTH, SCREEN_HEIGHT/2 + (64*(i%4)), BASIC_SIZE , BASIC_SIZE, (float) 1.7);
	    	if(i>=4){
	    		monsters[i] = new Monster( SCREEN_WIDTH + 64*3, SCREEN_HEIGHT/2 + (64*(i%4)), BASIC_SIZE , BASIC_SIZE, (float) 1.7);
	    	}
	    	if(i>=8){
	    		monsters[i] = new Monster( SCREEN_WIDTH + 64*6, SCREEN_HEIGHT/2 + (64*(i%4)), BASIC_SIZE , BASIC_SIZE, (float) 1.7);
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
		 deltax=delta;
		 Input input = container.getInput();
		 //monster involved
		 for (Monster monster : monsters){
			 playerController(input, delta, monster, container);
			 timesAndAnimationController(container);
			 BGController();
			 monsterController(monster);
			 battleController(monster);
			 REPLAYABLE(container, input);
			 goddessController(container, input);
		 }
		 //
		 goddess.goddessFlash();
		 gameMenu(container, input);

	}

	private void goddessController(GameContainer container, Input input) {
		if(swordman.shape.intersects(goddess.shape) && input.isKeyDown(Input.KEY_Z)){
			 container.setPaused(true);
			 goddess.x = (float) Math.random()*680;
			 times = 30;
		 }
		goddess.shape.setLocation(goddess.getX()+64,goddess.getY());
	}

	private void gameMenu(GameContainer container, Input input) {
		if(input.isKeyDown(Input.KEY_SPACE) && gameIsOver == false){
			 if(container.isPaused()){
				 container.setPaused(false);
			 }
		}
		if(input.isKeyDown(Input.KEY_ENTER)){
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
				monster.x -= monster.speed *deltax/17;	 
			 }
			 else{
			 //atk animation
			 swordman.x -= monster.mass * 50 * (1/swordman.mass) *deltax/17;
			 monster.x += (1/monster.mass) * 20 *deltax/17; 
			 monster.x += swordman.mass * 5 *deltax /17;
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
		
		monster.mass = (float) (3 + totaltimes / 10000);
	}

	private void BGController() {
		//BG.x -= 0.2*deltax /17;
		if( BG.x < -200 ){
			BG.setPosition();
		}
	}

	private void timesAndAnimationController(GameContainer container) {
		times -= 0.01 *deltax /60;
		totaltimes += 0.01 *deltax /60;
		swordman.animationTimes += 0.01 *deltax /20;
		if(swordman.animationTimes >= 1){
			swordman.animationTimes = 0;
			swordman.animation += 1;
			if(swordman.animation >= 4){
				swordman.animation = 0;
			}
		}
		goddess.animationTimes += 0.01 *deltax /20;
		if(goddess.animationTimes >= 1){
			goddess.animationTimes = 0;
			goddess.animation += 1;
			if(goddess.animation >= 4){
				goddess.animation = 0;
			}
		}
		if(times <= 0){
			times = 0;
			container.setPaused(true);
			gameIsOver = true;
			
		}
	}
	
	private void playerController(Input input, int delta, Monster monster,GameContainer container) throws SlickException {
		//shape
		swordman.shape.setLocation(swordman.getX(),swordman.getY());
		//control
		if (swordman.x > -1 && input.isKeyDown(Input.KEY_LEFT)  && swordAtBehind == false) {
		    	swordman.Flip();
		    	swordman.x -= delta*swordman.speed;

		}
		if (swordman.y <= SCREEN_HEIGHT/2 +(64*3)+1 && input.isKeyDown(Input.KEY_DOWN)) {
			swordAtBehind = false;
			swordman.Facedown();
	    	swordman.y += delta*swordman.speed;
		}
		if (swordman.y >= SCREEN_HEIGHT/2 -(32) && input.isKeyDown(Input.KEY_UP)) {
			swordAtBehind = false;
			swordman.Faceup();
	    	swordman.y -= delta*swordman.speed;
		}
		
		else if( swordman.shape.intersects(monster.shape) != true){
			
			 if (swordman.x+64 < SCREEN_WIDTH && input.isKeyDown(Input.KEY_RIGHT)) {
				 	swordman.FlipBack();
				 	swordAtBehind = false;
				 	swordman.x += delta*swordman.speed;
			    }
				 
		}
		//hp control
		if(swordman.hp <= 0){
			swordman.hp = 0;
		}
		//x-axis check
		if(swordman.x <= 0){
			swordman.x = 0;
			gameIsOver=true;
			container.setPaused(true);
		}

		if(swordman.x+64 >= SCREEN_WIDTH){
			swordman.x = SCREEN_WIDTH-64;
		}
		if(swordman.shape.intersects(monster.monsterBehind)){
			swordAtBehind = true;
		}
			 
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
