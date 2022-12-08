/**
 * 
 */
package Drone;

/**
 * @StudentNo. 30004098
 * Drone which gets in way of game Drone
 */
public class Obstacle extends Drone {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static int ObjectID=0;						// ID for right side pane

	/**
	 * Create Obstacle, size ir and arena sizes ix,iy
	 * @param ix
	 * @param iy
	 * @param ir
	 */
	public Obstacle(double ix, double iy, double ir) {
		super(ix, iy, ir);
		ID=ObjectID++; 		//ID for right side pane
		type = 'o';			//type of drone
	}

	/**
	 * return string defining drone type, here being Obstacle
	 */
	protected String getStrType() {
		return "Obstacle";
	}	

	/**
	 * resetting ID when making new arena
	 */
	public static void resetID() {
		ObjectID=0;
	}
	
	
	/**
	 * These following two are not needed here, as this 
	 * doesn't move, but it does inherits it from Drone
	 */
	protected void checkDrone(DroneArena b) {}

	protected void adjustDrone() {}
	

}