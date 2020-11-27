package Moon;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Missile extends Item {

	private static Image img;
	private double width;
	private double height;

	private double x;
	private double y;

	private int direction;

	private MissileListener mListener;
	// ------------------------------------ Static ------------------------------------
	static {
		try {
			img = ImageIO.read(new File("resProj/Attack/carrotToRight.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// ------------------------------------ Constructor ------------------------------------
	public Missile() {
		this(0, 0, 0);
	}

	public Missile(double x, double y,int direction) {
		// Depending On Rabbit's MoveIndex, Change Carrot Missile Image
		try {
			switch (direction) {
			case 0:
				img = ImageIO.read(new File("res/Attack/carrotToRight.png"));
				this.direction = 0;
				break;

			case 1:
				img = ImageIO.read(new File("res/Attack/carrotToLeft.png"));
				this.direction = 1;
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.x = x;
		this.y = y;

		width = 466;
		height = 288;
	}

	// ------------------------------------ Functions ------------------------------------
	public void update() {
		// Missile Is Out Of Canvas
		if (mListener != null
				&& sfCanvas.instance.getWidth() < x)
			mListener.onOut(this);

		// If Rabbit's Direction is To Left
		if (this.direction == 1)
			x -= 4;
		else
			x += 4;	
	}

	public void paint(Graphics g) {
		Canvas observer = sfCanvas.instance;

		int dx1 = (int) (x);
		int dy1 = (int) (y);
		int dx2 = (int) (dx1 + width / 5);
		int dy2 = (int) (dy1 + height / 5);

		int sx1 = 0;
		int sy1 = 0;
		int sx2 = sx1 + (int) width;
		int sy2 = sy1 + (int) height;

		g.drawImage(img,
				dx1, dy1, dx2, dy2,
				sx1, sy1, sx2, sy2, observer);

	}

	// ------------------------------------ Getters/Setters ------------------------------------
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
	
	public void setMListener(MissileListener mListener) {
		this.mListener = mListener;
	}
	
	@Override
	public Image getImage() {
		return img;
	}
}
