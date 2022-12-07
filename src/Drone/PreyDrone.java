/**
 * 
 */
package Drone;

/**
 * @author shsmchlr
 * The Target Ball which you are aiming at
 */
public class PreyDrone extends Drone{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static int PreyDroneID=0;
	double bAngle, bSpeed;
	
	/**
	 * 
	 */
	public PreyDrone() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ix
	 * @param iy
	 * @param ir
	 */
	public PreyDrone(double ix, double iy, double ir, double ia, double is) {
		super(ix, iy, ir);
		type = 'p';
		bAngle = ia;
		bSpeed = is;
		ID=PreyDroneID++;
	}

	/** 
	 * checkBall in arena 
	 * @param b DroneArena
	 */
	@Override
	protected void checkBall(DroneArena b) {
		bAngle = b.CheckBallAngle(x, y, rad, bAngle, ballID, this);
	}
	/**
	 * draw Ball and display score
	 */
	public void drawBall(MyCanvas mc) {
		super.drawBall(mc);
	}

	/**
	 * adjustBall
	 * for moving the ball - not needed here
	 */
	@Override
	protected void adjustBall() {
		double radAngle = bAngle*Math.PI/180;		// put angle in radians
		
		if (x<=0+rad) x=x+rad;
		if (x>DroneInterface.getxSize()) x=x-rad;
		
		if (y<=0+rad) y=y+rad;
		if (y>DroneInterface.getySize()) y=y-rad;
		
		else {
				double multiplier = DroneInterface.getSPEEDmult();
				
				x += bSpeed * Math.cos(radAngle) * multiplier;		// new X position
				y += bSpeed * Math.sin(radAngle) * multiplier;		// new Y position
		}
	}
	/**
	 * return string defining ball ... here as target
	 */
	protected String getStrType() {
		return "Prey Drone";
	}	
	
	public static void resetID() {
		PreyDroneID=0;
	}
}