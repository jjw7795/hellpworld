package Moon;



public class JumpThread extends Thread {
	  boolean jumping=false;
	    
	    int jumpingy[]=new int[]{0, -50, -20,-6,-6,0,6,6,20,50}; 
	    
	    int jumpIdx;
	    
	    JumpListener jumpListener;
	    public void setJumpListener(JumpListener jumpListener) {
			this.jumpListener = jumpListener;
		}
	    public JumpThread(JumpListener jumpListener) {
//	    	super();
	    	this.jumpIdx=1;
	    	this.jumpListener=jumpListener;
	    }
	    
	    public void run() {
	    	
	    	while(this.jumpIdx<this.jumpingy.length) {
	    		
	    		this.jumpListener.jumpTimeArrived(this.jumpIdx, this.jumpingy[this.jumpIdx]);
	    		
		    	try {
		    		Thread.sleep(34);
		    	} catch (InterruptedException e) {
		    		
					e.printStackTrace();
				}
		    	
		    	this.jumpIdx++;
	    	}
	    	this.jumpListener.jumpTimeEnded();
	    		
	    }
}
