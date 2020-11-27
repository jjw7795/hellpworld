package Moon;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Rabbit extends Animal implements JumpListener {
	private Image img;
	private Canvas canvas;
	private static final int LEFT = 37;
	private static final int UP = 38;
	private static final int RIGHT = 39;
	private static final int DOWN = 40;
	private static final int PUNCH= 90;
	private static final int KICK =88;
	private static final int SPACE = 32;
	private static final int CTRL = 17;

	private int E = 0;
	private int W = 0;
	private int N = 0;
	private int S = 0;

	private int jumpdirection=0; 
	private int jumpIdx=0;
	private int rabbitDirection=0;

	private boolean arrived = false;

	private int pLength;
	private double crushPoint;

	// For Carrot Missile
	private int ctrlCount = 0;

	
	private RabbitKeyListener keyListener;
	// ------------------------------------ Constructor ------------------------------------
	public Rabbit() {
		this(0, 0, null);
	}

	public Rabbit(double x, double y,Canvas instance) {
		super(x, y);

		try {
			img = ImageIO.read(new File("res/rabbit.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		canvas = instance;
		setWidth(80);
		setHeight(92);

		//충돌변수
		pLength = 0;
		crushPoint =0;
	}

	// ------------------------------------ Functions ------------------------------------
	@Override
	public Image getImage() {
		return img;
	}
	@Override
	public void update() {
		double x = getX();
		double y = getY();
		double w = getWidth();
		double h = getHeight();
	

		setCrushPoint(getX()+pLength);
	
		moveOnFight(x, y, w, h, sfCanvas.instance.getWidth(), sfCanvas.instance.getHeight());

	}
	public double getCrushPoint() {
		return crushPoint;
	}

	public void setCrushPoint(double crushPoint) {
		this.crushPoint = crushPoint;
	}
	
	// Story Fight Canvas / Fight Canvas : Check Pressed Key & Update Rabbit's Move
	private void moveOnFight(double x, double y, double w, double h, double canvasWidth, double canvasHeight) {
		if (x < 0 + w / 2) {
			x = 0 + w / 2;
			setJumpDirection(-1);
		}
		else if (canvasWidth - w / 2 < x) {
			x = canvasWidth - w / 2;
			setJumpDirection(1);
		} 
		else {
			if (E == 1)
				x += 2;
			if (W == 1)
				x -= 2;
			if (N == 1)
				y -= 2;
			if (S == 1)
				y += 2;
		}
		setX(x);
		setY(y);
	}
	public void move(int key) {

		switch (key) {
		case LEFT:
			rabbitDirection = -1;
			W = 1;
			break;

		case RIGHT:
			rabbitDirection = 1;
			E=1;
			break;

		case UP:

			//			점프 쓰레드를 실행한다.
			if(canvas == sfCanvas.instance) {
				new JumpThread((JumpListener) Rabbit.this).start();
			}
			else
				N=1;

			break;

		case DOWN:

			S = 1;
			break;

		case PUNCH:
			if(rabbitDirection==1) {
				pLength=56;
				setImgMoveIndex_Y(7);
			}
			else if(rabbitDirection == -1) {
				pLength=-56;
				setImgMoveIndex_Y(4);
			}
			break;
		case KICK:
			if(rabbitDirection==1) {
				pLength=56;
				setImgMoveIndex_Y(8);
			}
			else if(rabbitDirection == -1) {
				pLength=-56;
				setImgMoveIndex_Y(5);
			}
			break;
		case SPACE:
			if (keyListener != null)
				keyListener.onSpacePressed();
			break;

		case CTRL:
			if (keyListener != null)
				keyListener.onCtrlPressed();
			break;
		}
	}
	public void initKey(int key) {
		switch (key) {
		case LEFT:
			W = 0;
			break;

		case RIGHT:
			E = 0;
			break;

		case UP:
			N = 0;
			break;

		case DOWN:
			S = 0;
			break;

		case PUNCH:
			if(rabbitDirection==1) {
				pLength=0;
				setImgMoveIndex_Y(6);
			}
			else if(rabbitDirection == -1) {
				pLength=0;
				setImgMoveIndex_Y(3);
			}
			break;

		case KICK:
			if(rabbitDirection==1) {
				pLength=0;
				setImgMoveIndex_Y(6);
			}
			else if(rabbitDirection == -1) {
				pLength=0;
				setImgMoveIndex_Y(3);
			}
			break;
		}

	}
	public void paint(Graphics g) {
		Canvas observer = sfCanvas.instance;

		int x = (int) getX();
		int y = (int) getY();

		int w = (int) getWidth();
		int h = (int) getHeight();

		int moveIdxX = getImgMoveIndex_X();
		int moveIdxY = getImgMoveIndex_Y();


		if(S == 1)
			moveIdxY = 1;
		else if(N == 1)
			moveIdxY = 2;
		else if(W == 1)
			moveIdxY = 3;
		else if(E == 1)
			moveIdxY = 6;


		int yoffset = moveIdxY * h;
		int xoffset = moveIdxX * w;

		if (!((E == 0) && (W == 0) && (N == 0) && (S == 0))) {
			moveIdxX++;
			moveIdxX %= 4;
		} else
			moveIdxX = 0;

		int dx1 = x-w/2;
		int dy1 = y;
		int dx2 = x + w/2;
		int dy2 = y + h;

		int sx1 = 0 + xoffset;
		int sy1 = 0 + yoffset;
		int sx2 = w + xoffset;
		int sy2 = h + yoffset;

		g.drawImage(img,
				dx1, dy1, dx2, dy2,
				sx1, sy1, sx2, sy2, observer);

		setImgMoveIndex_X(moveIdxX);
	}

	// StoryFightCanvas / FightCanvas : Rabbit Fire Carrot Missile
	public Missile fire() {
		double x = getX();
		double y = getY();
		int direction;

		Missile missile;

		if (getImgMoveIndex_Y() == 3) {
			direction = 1;
			missile = new Missile(x - 80, y + 20, direction);
		} else {
			direction = 0;
			missile = new Missile(x + 10, y + 20, direction);
		}

		return missile;
	}
	public void addX(int add) {
		setX((int)getX()+add);

	}
	public void addY(int add) {
		setY((int)getY()+add);
	}

	public boolean isCollision(double x) {
		return x>getX()-10&&x<getX()+10;
	}

	//Rabbit Jump function
	@Override
	public void jumpTimeArrived(int jumpIdx, int jumpy) {
		addX(getJumpDirection()*5);
		addY(jumpy);
		//화면 밖으로 못나가게 하는거 같음
		if (getX()<0+getWidth()/2) 
			setX(0+getWidth()/2);
		if (getX()> (500- getWidth()/2)) 
			setX(500- getWidth()/2);

		setJumpIdx(jumpIdx);		
	}

	@Override
	public void jumpTimeEnded() {
		setJumpIdx(0);

	}
	// ------------------------------------ Getters/Setters ------------------------------------


	public boolean isArrived() {
		return arrived;
	}

	public void setArrived(boolean arrived) {
		this.arrived = arrived;
	}

	public int getJumpDirection() {
		return this.jumpdirection;
	}

	public void setJumpDirection (int jumpDirection) {
		this.jumpdirection=jumpDirection;
	}
	public int getJumpIdx() {
		return this.jumpIdx;
	}

	public void setJumpIdx(int jumpIdx) {
		this.jumpIdx=jumpIdx;
	}
	public boolean isJumping() {
		if (this.jumpIdx==0)
			return false;
		return true;
	}
	public void setRabbitKeyListener(RabbitKeyListener keyListener) {
		this.keyListener = keyListener;
	}

	public int getCtrlCount() {
		return ctrlCount;
	}

	public void setCtrlCount(int ctrlCount) {
		this.ctrlCount = ctrlCount;
	}

}
