/**
 * 
 */
package Drone;

/**
 * @StudentNo. 30004098
 *	This extending class of Drone is a drone that can attack a IllegalDrone, but generally just bounces around
 */
public class PoliceHelicopter extends Drone {

	private static final long serialVersionUID = 1L;	
	private static int PoliceHelicopterID=0;					// ID for right side pane
	private double bAngle, bSpeed;								// angle and speed of travel

	/** Create PoliceHelicopter, size ir, arena sizes ix,iy, moving at angle ia and speed is
	 * @param ix
	 * @param iy
	 * @param ir
	 * @param ia
	 * @param is
	 */
	public PoliceHelicopter(double ix, double iy, double ir, double ia, double is) {
		super(ix, iy, ir);
		bAngle = ia;				//angle of drone
		bSpeed= is;					//speed of drone	
		ID=PoliceHelicopterID++;	//ID for right pane
		type='h';					//type of drone
	}

	/**
	 * checkDrone - change angle of travel if hitting wall or another Drone
	 * @param b   DroneArena
	 */
	@Override
	protected void checkDrone(DroneArena b) {
		bAngle = b.CheckdroneAngle(x, y, rad, bAngle, DroneID, this);
	}

	/**
	 * adjustDrone
	 * Here, move Drone depending on speed, angle and position
	 */
	@Override
	protected void adjustDrone() {
		double radAngle = bAngle*Math.PI/180;					// put angle in radians
		
		if (x<=0) x=x+rad;										//these are used to correct positions of drones that go off canvas 
		if (x>=DroneInterface.getxSize()-rad+3) x=x-(rad+5);		//done by pushing them back in to boundaries of arena
		
		if (y<=0) y=y+rad;								
		if (y>=DroneInterface.getySize()-rad+3) y=y-rad+5;
		
		else {
				double multiplier = DroneInterface.getSpeedMult();
				x += bSpeed * Math.cos(radAngle) * multiplier;		// new X position
				y += bSpeed * Math.sin(radAngle) * multiplier;		// new Y position
		}
	}
	/**
	 * return string defining drone type, here being police helicopter
	 */
	protected String getStrType() {
		return "Police Helicopter";
	}
	
	/**
	 * resetting ID when making new arena
	 */
	public static void resetID() {
		PoliceHelicopterID=0;
	}
	
}