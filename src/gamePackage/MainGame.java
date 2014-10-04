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
	protected static int MON_NUM= 8;
	protected double times = 0;
	float deltax = 0;
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
		for (Monster monster : monsters) {
			if(monster.isDestroy == false){
		      monster.render();
		    }
			
			if(swordman.shape.intersects(monster.monsterBehind) == true){
				g.drawString("XXXXXXXX", monster.x, monster.y-50);
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
				g.drawString(""+monster.hp, monster.x, monster.y-50);
			}
		}
		g.drawString(""+deltax*60/17, SCREEN_WIDTH/2+100, 10);

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
		monsterInit();
		BG = new BackGround(SCREEN_WIDTH/2 + 20, SCREEN_HEIGHT/2 + -170);
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
	    	monsters[i] = new Monster( SCREEN_WIDTH, SCREEN_HEIGHT/2 + (64*(i%4)), BASIC_SIZE , BASIC_SIZE, 3);
	    	if(i>=4){
	    		monsters[i] = new Monster( SCREEN_WIDTH + 64*3, SCREEN_HEIGHT/2 + (64*(i%4)), BASIC_SIZE , BASIC_SIZE, 3);
	    	}
	    	if(i>=8){
	    		monsters[i] = new Monster( SCREEN_WIDTH + 64*6, SCREEN_HEIGHT/2 + (64*(i%4)), BASIC_SIZE , BASIC_SIZE, 3);
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
		// TODO Auto-generated method stub
		 deltax=delta;
		 Input input = container.getInput();
		 //monster involved
		 for (Monster monster : monsters){
			 playerController(input, delta, monster, container);
			 timesController();
			 BGController();
			 monsterController(monster);
			 battleController(monster);
			 REPLAYABLE(container, input);
		 }
		 //
		 if(input.isKeyDown(Input.KEY_SPACE)){
			 if(container.isPaused()){
				 container.setPaused(false);
			 }
			 else{
			 container.setPaused(true);
			 }
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
			monster.x = 0;
			//monster.setPosition();
		}
	}

	private void BGController() {
		BG.x -= 0.5 *deltax /17;
		if( BG.x < -100 ){
			BG.setPosition();
		}
	}

	private void timesController() {
		times -= 0.01 *deltax /17;
		if(times <= 0){
			times = 0;
		}
	}
	
	private void playerController(Input input, int delta, Monster monster,GameContainer container) throws SlickException {
		// TODO Auto-generated method stub
		//shape
		swordman.shape.setLocation(swordman.getX(),swordman.getY());
		//control
    	if(swordman.shape.intersects(monster.monsterBehind) == false){
			if (swordman.x > -1 && input.isKeyDown(Input.KEY_LEFT) ) {
			    	swordman.Flip();
			    	swordman.x -= delta*swordman.speed;
	
			}
			if (swordman.y <= SCREEN_HEIGHT/2 +(64*3)+1 && input.isKeyDown(Input.KEY_DOWN)) {		    
		    	swordman.y += delta*swordman.speed;
			}
			if (swordman.y >= SCREEN_HEIGHT/2 && input.isKeyDown(Input.KEY_UP)) {  	
		    	swordman.y -= delta*swordman.speed;
			}
			
			else if( swordman.shape.intersects(monster.shape) != true){
				
				 if (swordman.x+64 < SCREEN_WIDTH && input.isKeyDown(Input.KEY_RIGHT)) {
					 	swordman.FlipBack();
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
				reinit=true;
				container.setPaused(true);
			}
	
			if(swordman.x+64 >= SCREEN_WIDTH){
				swordman.x = SCREEN_WIDTH-64;
			}
			 
    	}
	}

	public static void main(String[] args) {
		try {
				  MainGame game = new MainGame("BraveForward");
				  AppGameContainer appgc = new AppGameContainer(game);
				  appgc.setDisplayMode(SCREEN_WIDTH,SCREEN_HEIGHT, false);
				  appgc.setTargetFrameRate(60);
				  appgc.setVSync(true);
				  appgc.start();
				  appgc.setMinimumLogicUpdateInterval(1000);
				  appgc.setMaximumLogicUpdateInterval(1000);
				  appgc.setShowFPS(false);
				  appgc.setClearEachFrame(true);
				} catch (SlickException e) {
				  e.printStackTrace();
				}
		  }
}
