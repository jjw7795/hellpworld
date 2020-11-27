package Moon;

import java.awt.Canvas;
import java.awt.Frame;

public class GameFrame extends Frame {

	
	Canvas sfCanvas;
  public  static 	GameFrame instance;
	
	public GameFrame() {
		sfCanvas = new sfCanvas();
		instance =this;
		setVisible(true);
		setSize(500,500);
		add(sfCanvas);
	}
}
