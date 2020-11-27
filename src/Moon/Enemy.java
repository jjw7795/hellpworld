package Moon;

import java.awt.Canvas;
import java.awt.Image;
import java.util.Random;

public abstract class  Enemy extends Animal implements JumpListener{

	private Image img;

	private Canvas canvas;
	private boolean entered = false;
	private int moveIdxX;
	private int moveIdxY;

	private Random rand;
	private int timeoutForAttack = 60;
	private int timeoutForStop = 60;
	private int timeOutForMoving = 60;

	private int jumpdirection = 0;
	private int jumpIdx = 0;

	private int pLength;
	private double crushPoint;

	private double cout;
	private int direction = 0;

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

	public Enemy() {
		this(0, 0);
	}

	public Enemy(double x, double y) {
		this(0, 0, null);
	}

	public Enemy(double x, double y, Canvas instance) {

		canvas = instance;
		cout = 0;
		img = getImage();

		width = 112;
		height = 129;
		rand = new Random();

		pLength = 0;
		crushPoint = 0;

	}



	public boolean isRabbitEntered(double rabbitX) {
		double w = width;
		;
		double enemyX1 = x - w / 2;
		double enemyX2 = x + w / 2;

		if (rabbitX < enemyX1 + w / 2)
			direction = 0; // Enemy : Right
		else
			direction = 1;

		if (enemyX1 < rabbitX && rabbitX < enemyX2)
			return true;

		return false;
	}

	public boolean tigerIsCollision(double x) {
		return x > x - 20 && x < x + 20;
	}

	@Override
	public void update() {
		setCrushPoint(x + pLength);
		if (canvas == sfCanvas.instance) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					if (entered != true) {
						if (cout < x && x - 150 < cout) {
							if (cout + 50 < x) {
								moveIdxY = 0;
								moveIdxX = 0;

								leftMove();

							}

						} else if (cout > x && x + 150 > cout) {
							if (cout - 50 > x) {
								moveIdxY = 3;
								moveIdxX = 0;

								rightMove();

							}
						}
						timeOutForMoving--;
						if (timeOutForMoving == 0) {
							if (cout < x && x - 400 < cout) {
								moveIdxY = 0;
								moveIdxX = 0;
//																setImgMoveIndex_Y(0);

								jumpdirection = -1;
								new JumpThread((JumpListener) Enemy.this).start();

							} else if (cout > x && x + 400 > cout) {
								moveIdxY = 3;
								moveIdxX = 0;
//																setImgMoveIndex_Y(3);

								jumpdirection = 1;
								new JumpThread((JumpListener) Enemy.this).start();

							} else {
								jumpdirection = 0;
							}
							try {
								Thread.sleep(1000);

							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							timeOutForMoving = 60;
						}
					} else if (entered) { // 범위 내에 토끼 있을 때, 일정 시간 지난 후 공격
						timeoutForAttack--;
						if (timeoutForAttack == 0) {
							attack();
							timeoutForAttack = rand.nextInt(45) + 1;
						}
					}
				}
			}).start();
		}
	}

	public void rightMove() {
		int xmove = (int) x;
		xmove += 1;
		imgMoveIndex_Y =3;

		int indexmovex = 0;
		indexmovex++;
		indexmovex  = indexmovex % 4;
		x+=xmove;

	}

	public void leftMove() {
		int xmove = (int) x;
		xmove -= 1;
		imgMoveIndex_Y =0;

		int indexmovex = 0;
		indexmovex++;
		indexmovex = indexmovex % 3;
		x +=xmove;
	}

	public void attack() {
		// 랜덤으로 공격
		int randomAttack = rand.nextInt(10);
		randomAttack %= 2;

		int direction = getDirection();

		switch (randomAttack) {
		case 0: // 공격 : 펀치
			switch (direction) {
			case 0: // 왼쪽으로 보는 방향
				pLength = -56;
//				moveIdxY = 1;
//				moveIdxX = 0;
				imgMoveIndex_X =0;
				imgMoveIndex_Y =1;
				break;
			case 1: // 오른쪽으로 보는 방향
				pLength = 56;
//				moveIdxY = 3;
//				moveIdxX = 0;
				imgMoveIndex_X =0;
				imgMoveIndex_Y =3;
				break;
			}
			break;
		case 1: // 공격 : 발차기
			switch (direction) {
			case 0:
				pLength = 0;
//				moveIdxY = 2;
//				moveIdxX = 0;
				imgMoveIndex_X =0;
				imgMoveIndex_Y =2;
				break;
			case 1:
				pLength = 0;
//				moveIdxY = 4;
//				moveIdxX = 0;
				imgMoveIndex_X =0;
				imgMoveIndex_Y =4;
				break;
			}
			break;
		}

	}

	@Override
	public void jumpTimeArrived(int jumpIdx, int jumpy) {
		x = getJumpdirection() * 10;
		y =jumpy;
		setJumpIdx(jumpIdx);
	}

	@Override
	public void jumpTimeEnded() {
		setJumpIdx(0);
	}

	public int getDirection() {
		return direction;
	}

	public int getJumpdirection() {
		return jumpdirection;
	}

	public void setJumpdirection(int jumpdirection) {
		this.jumpdirection = jumpdirection;
	}

	public int getJumpIdx() {
		return jumpIdx;
	}

	public void setJumpIdx(int jumpIdx) {
		this.jumpIdx = jumpIdx;
	}

	public void addX(int add) {
		x =((int) x+ add);

	}

	public void addY(int add) {
		y =((int) y + add);
	}

	public double getCout() {
		return cout;
	}

	public void setCout(double cout) {
		this.cout = cout;
	}

	public boolean isEntered() {
		return entered;
	}

	public void setEntered(boolean entered) {
		this.entered = entered;
	}

	public double getCrushPoint() {
		return crushPoint;
	}

	public void setCrushPoint(double crushPoint) {
		this.crushPoint = crushPoint;
	}
}
