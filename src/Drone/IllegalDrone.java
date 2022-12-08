/**
 * 
 */
package Drone;

/**
 * @StudentNo. 30004098
 * This extending class of Drone is a drone that can die when touched by PoliceHelicopter
 */
public class IllegalDrone extends Drone{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static int IllegalDroneID=0;					// ID for right side pane
	private double bAngle, bSpeed;							// angle and speed of travel

	/** Create Prey Drone, size ir, arena sizes ix,iy, moving at angle ia and speed is
	 * @param ix
	 * @param iy
	 * @param ir
	 * @param ia
	 * @param is
	 */
	public IllegalDrone(double ix, double iy, double ir, double ia, double is) {
		super(ix, iy, ir);
		bAngle = ia;			// angle
		bSpeed = is;			// speed
		ID=IllegalDroneID++;	// ID for right pane
		type = 'i';				// type of drone
	}

	/** 
	 * checkDrone in arena - change angle of travel if hitting wall or another Drone
	 * @param b DroneArena
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
		double radAngle = bAngle*Math.PI/180;		// put angle in radians
		
		if (x<=0) x=x+rad;
		if (x>DroneInterface.getxSize()) x=x-rad;	//these are used to correct positions of drones that go off canvas 
													//done by pushing them back in to boundaries of arena
		if (y<=0) y=y+rad;
		if (y>DroneInterface.getySize()) y=y-rad;
		
		else {
				double multiplier = DroneInterface.getSpeedMult();
				
				x += bSpeed * Math.cos(radAngle) * multiplier;		// new X position
				y += bSpeed * Math.sin(radAngle) * multiplier;		// new Y position
		}
	}
	/**
	 * return string defining drone type, here being Illegal Drone
	 */
	protected String getStrType() {
		return "Illegal Drone";
	}	
	
	/**
	 * resetting ID when making new arena
	 */
	public static void resetID() {
		IllegalDroneID=0;
	}
}