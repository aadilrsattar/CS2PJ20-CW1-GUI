/**
 * 
 */
package Drone;

/**
 * @StudentNo. 30004098
 *	This extending class of Drone is a bird that just annoys everyone
 */
public class Birds extends Drone {

	private static final long serialVersionUID = 1L;	
	private static int BirdsID=0;					// ID for right side panel
	private double bAngle, bSpeed;						// angle and speed of travel

	/** Create EnemyDrone, size ir, arena sizes ix,iy, moving at angle ia and speed is
	 * @param ix
	 * @param iy
	 * @param ir
	 * @param ia
	 * @param is
	 */
	public Birds(double ix, double iy, double ir, double ia, double is) {
		super(ix, iy, ir);
		bAngle = ia;
		bSpeed= is;
		ID=BirdsID++;
		type='b';	
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
	 * return string defining drone type, here being Bird
	 */
	protected String getStrType() {
		return "Bird";
	}
	
	/**
	 * resetting ID when making new arena
	 */
	public static void resetID() {
		BirdsID=0;
	}
	
}