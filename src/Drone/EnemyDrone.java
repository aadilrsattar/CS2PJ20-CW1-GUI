/**
 * 
 */
package Drone;

/**
 * @StudentNo. 30004098
 *	This extending class of Drone is a drone that can attack a PreyDrone, but generally just bounces around
 */
public class EnemyDrone extends Drone {

	private static final long serialVersionUID = 1L;	
	private static int EnemyDroneID=0;					// ID for right side panel
	private double bAngle, bSpeed;						// angle and speed of travel
	/**
	 * 
	 */
	public EnemyDrone() {
		// TODO Auto-generated constructor stub
	}

	/** Create EnemyDrone, size ir, arena sizes ix,iy, moving at angle ia and speed is
	 * @param ix
	 * @param iy
	 * @param ir
	 * @param ia
	 * @param is
	 */
	public EnemyDrone(double ix, double iy, double ir, double ia, double is) {
		super(ix, iy, ir);
		bAngle = ia;
		bSpeed= is;
		ID=EnemyDroneID++;
		type='e';	
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
		
		if (x<0) x=x+rad;										//these are used to correct positions of drones that go off canvas 
		if (x>DroneInterface.getxSize()-rad+3) x=x-(rad+5);		//done by pushing them back in to boundaries of arena
		
		if (y<0) y=y+rad;								
		if (y>DroneInterface.getySize()-rad+3) y=y-rad+5;
		
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
		EnemyDroneID=0;
	}
	
}