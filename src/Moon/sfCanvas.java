package Moon;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

public class sfCanvas extends Canvas {

	public static Canvas instance;
	
	private Music music;
	
	private Background background;
	private Background blackbg;
	
	private Item[] items;
	private int itemSize;
	
	private Animal[] animals;
	private int animalSize;
	
	private Rabbit rabbit;
	private Wolf wolf;
	private Bear bear;
	private Tiger tiger;
	private Enemy enemy;
	
	
	private EnergyBar[] bars;
	private EnergyBar rabbitBar;
	private EnergyBar enemyBar;
	private EnergyBar barCTRL;
	
	private Missile[] missiles;
	private int missileSize;
	
	private Vs vs;
	
	//충돌
	
	private boolean isCollision;
	private boolean tigerIsCollision;
	
	private int timeOutForAttact =10;
	
	//서브 뜨레드
	private boolean running =true;
	
	private Result[] results;
	private int resultSize =0;
	private Button resultBackButton;
	
	private boolean rabbitKnockdown =false;
	private boolean enemyKnockdown =false;
	private boolean gameResult =false;
	private boolean rabbitWin =false;
	
	
	public sfCanvas() {
		instance =this;
		this.setSize(500,500);
		this.setBackground(Color.pink);
		music = new Music();
		
		isCollision =false;
		tigerIsCollision =false;
		
		background = new Background("res/fight.png",instance);	
		blackbg = new Background("res/setbackground.png",instance);
		
		items = new Item[100];
		itemSize =0;
		
		//enegy
		bars = new EnergyBar[4];
		bars[0] = new EnergyBar(10,10,0);
		bars[1] = new EnergyBar(10,10,1);
		bars[2] = new EnergyBar(270,10,0);
		bars[3] = new EnergyBar(270,10,1);
		
		rabbitBar = bars[1];
		enemyBar = bars[3];
		
		
		barCTRL = new EnergyBar(10,50,2);
		
		items[itemSize++] = vs = new Vs(200,0);
		items[itemSize++] = rabbit = new Rabbit(150,280,instance);
		
		
		int datas[];
	
			wolf = new Wolf(350,240,instance);
			items[itemSize++] = enemy = wolf;
	
			bear = new Bear(350,240,instance);
			items[itemSize++] = enemy = bear;
		
			tiger = new Tiger(350,240,instance);
			items[itemSize++] = enemy = tiger;
	
//		
		animalSize =2;
		animals = new Animal[animalSize];
		items[itemSize++] = animals[0] = rabbit;
		items[itemSize++] = animals[1] =enemy;
//		
		missiles = new Missile[10];
		missileSize =0;
//		
//		//result
		results = new Result[2];
		results[resultSize++] = new Result(120,500,1);
		results[resultSize++] = new Result(120,100,0);
//		
		resultBackButton = new Button(200,300,"res/back.png");
		resultBackButton.setWidth(50);;
		resultBackButton.setHeight(50);
	
		start();
	}
	
	public void start() {
		Runnable sub = new Runnable() {

			@Override
			public void run() {
				while(running) {
					background.update();

					for(int i=0; i < itemSize; i++)
						items[i].update();

					// Give Rabbit's Location To Enemy
					double rabbitX = rabbit.getX();
					// If Rabbit Is On Enemy's Range, Enemy Attack Rabbit
					if(enemy.isRabbitEntered(rabbitX))
						enemy.setEntered(true);
					else
						enemy.setEntered(false);
					// Game Result : Win
					if(rabbitKnockdown == false && enemyKnockdown == true) {
						gameResult = true;
						results[1].setZ(0);
					}
					// Game Result : Lose
					else if(rabbitKnockdown == true && enemyKnockdown == false) {
						gameResult = true;
						results[1].setZ(1);
					}
					// The others
					repaint();


					try {
						Thread.sleep(17);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				}

			}
		};

		Thread thread = new Thread(sub);
		thread.start();
	}
	
	@Override
	public void update(Graphics g) {
		paint(g);
	}
	@Override
	public void paint(Graphics g) {
		Image buf = this.createImage(this.getWidth(), this.getHeight());
		Graphics bg = buf.getGraphics();
		
		background.paint(bg);
			for(int i=0; i <  itemSize; i++)
			items[i].paint(bg);
		
		for(int i=0;i<4;i++)
			bars[i].paint(bg);
		
		
		blackbg.paint(bg);
		for (int i = 0; i < resultSize; i++)
			results[i].paint(bg);
		resultBackButton.paint(bg);
		
		g.drawImage(buf,0,0, sfCanvas.instance );
		
		
	
		
	}
	
}
