package Moon;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class Tiger extends Enemy {

	private Image img;

	private double width;
	private double height;

	private double x;
	private double y;
	private double dx;
	private double dy;
	private double vx;
	private double vy;

	private int imgMoveIndex_Y = 0;
	private int imgMoveIndex_X = 0;
	private int moveTempo = 2;
	private int speed = 1;

	private int moveIdxX;
	private int moveIdxY;
	private Canvas canvas;
	private boolean entered = false;

	
	private Random rand;
	private int timeoutForAttack = 60;
	private int timeoutForStop = 60;
	private int timeOutForMoving=60;
	
	private int jumpdirection=0; 
	private int jumpIdx=0;
	
	private int pLength;
	private double crushPoint;
	
	private double cout;
	private int direction = 0;

	// ------------------------------------ Constructor
	// ------------------------------------
	public Tiger() {
		this(0, 0);
	}

	public Tiger(double x, double y) {
		this(0, 0, null);

	}

	public Tiger(double x, double y, Canvas instance) {
		super(x, y, instance);

		width = 112;
		height = 129;
		rand = new Random();

		pLength = 0;
		crushPoint = 0;
		try {
			img = ImageIO.read(new File("res/tiger.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	// ------------------------------------ Functions
	// ------------------------------------


	public void paint(Graphics g) {
		Canvas observer = sfCanvas.instance;
		int x = (int) getX();
		int y = (int) getY();

		int w = (int) getWidth();
		int h = (int) getHeight();

		int yoffset = getImgMoveIndex_Y() * h;
		int xoffset = getImgMoveIndex_X() * w;
	
		
		int dx1 = x - w / 2;
		int dy1 = y;
		int dx2 = x + w / 2;
		int dy2 = y + h;

		int sx1 = 0 + xoffset;
		int sy1 = h * 3 + yoffset;
		int sx2 = w + xoffset;
		int sy2 = h * 4 + yoffset;

		g.drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, observer);

		setImgMoveIndex_Y(moveIdxY);
	}

	@Override
	public Image getImage() {
		// TODO Auto-generated method stub
		return null;
	}
}
