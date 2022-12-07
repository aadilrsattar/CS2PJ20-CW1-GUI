/**
 * 
 */
package Drone;

/**
 * @author shsmchlr
 *
 */
public class EnemyDrone extends Drone {
	private static int EnemyDroneID=0;
	double bAngle, bSpeed;			// angle and speed of travel
	/**
	 * 
	 */
	public EnemyDrone() {
		// TODO Auto-generated constructor stub
	}

	/** Create game Drone, size ir ay ix,iy, moving at angle ia and speed is
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
	}

	/**
	 * checkball - change angle of travel if hitting wall or another Drone
	 * @param b   DroneArena
	 */
	@Override
	protected void checkBall(DroneArena b) {
		bAngle = b.CheckBallAngle(x, y, rad, bAngle, ballID, this);
	}

	/**
	 * adjustBall
	 * Here, move Drone depending on speed and angle
	 */
	@Override
	protected void adjustBall() {
		double radAngle = bAngle*Math.PI/180;		// put angle in radians
		
		if (x<=0+rad) x=x+rad;
		if (x>DroneInterface.getxSize()) x=x-rad;
		
		if (y<=0+rad) y=y+rad;
		if (y>DroneInterface.getySize()) y=y-rad;
		
		else {
				x += bSpeed * Math.cos(radAngle) * DroneInterface.SPEEDmult;		// new X position
				y += bSpeed * Math.sin(radAngle) * DroneInterface.SPEEDmult;		// new Y position
		}
	}
	/**
	 * return string defining Drone type
	 */
	protected String getStrType() {
		return "Enemy Drone";
	}
	
}