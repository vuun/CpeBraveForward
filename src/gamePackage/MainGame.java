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
	protected double times = 0;
	Swordman swordman;
	BackGround BG;
	Monster monster;
	public MainGame(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		// TODO Auto-generated method stub
		swordman.render();
		BG.render();
		monster.render();
		drawTimes(g);
	}

	private void drawTimes(Graphics g) {
		g.drawString("" + (float)times, SCREEN_WIDTH/2, 10);
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		// TODO Auto-generated method stub
		change_BG_Color(container);
		swordman = new Swordman( SCREEN_WIDTH/2, SCREEN_HEIGHT/2);
		monster = new Monster( SCREEN_WIDTH/2, SCREEN_HEIGHT/2, 64 , 64);
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
		 updateSmMovement(input, delta);
		 timesController();
		 BGController();
		 monsterController();
		
	}

	private void monsterController() {
		monster.x -= 0.3;
		if( monster.x < -100 ){
			monster.setPosition();
		}
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
	
	private void updateSmMovement(Input input, int delta) throws SlickException {
		// TODO Auto-generated method stub 
		 if (swordman.x > -1 && input.isKeyDown(Input.KEY_LEFT)) {
		    	swordman.Flip();
		    	swordman.x -= swordman.mass;
		    }
		 if (swordman.x+64 < SCREEN_WIDTH && input.isKeyDown(Input.KEY_RIGHT)) {
			 	swordman.FlipBack();
			 	swordman.x += swordman.mass;
		    }
	}

	public static void main(String[] args) {
		try {
				  MainGame game = new MainGame("BraveForward");
				  AppGameContainer appgc = new AppGameContainer(game);
				  appgc.setDisplayMode(SCREEN_WIDTH,SCREEN_HEIGHT, false);
				  appgc.start();
				} catch (SlickException e) {
				  e.printStackTrace();
				}
		  }
}
