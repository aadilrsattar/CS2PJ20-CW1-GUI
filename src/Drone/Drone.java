/**
 * 
 */
package Drone;

import java.io.Serializable;

/**
 * @StudentNo. 30004098
 * Initial class from drones, which all other drones extend to
 */
public abstract class Drone implements Serializable {
	private static final long serialVersionUID = 1L;
	protected double x, y, rad;							// position and size of drone
	protected char type;								// used to set type of drone
	private static int droneCounter = 0;				// used to give each drone a unique identifier
	protected int DroneID;								// unique identifier for item
	protected int ID;									// unique identifier for each specific item
	protected boolean toRemove=false;					// boolean value to identify what drone to remove 
	
	Drone(){};
	/**
	 * construct a drone/Drone of radius ir at ix,iy
	 *
	 * @param ix
	 * @param iy
	 * @param ir
	 */
	Drone (double ix, double iy, double ir) {
		x = ix;
		y = iy;
		rad = ir;
		DroneID = droneCounter++;		// set the identifier and increment class static
		type='z';						// type needs to be initialised here, otherwise it doesn't show properly
		ID=0;							// ID, to add to the describeall in arena, for status bar, using toString()
	}
	
	/**
	 * return x position
	 * @return
	 */
	public double getX() { return x; }
	
	/**
	 * return y position
	 * @return
	 */
	public double getY() { return y; }
	
	/**
	 * return radius of drone
	 * @return
	 */
	public double getRad() { return rad; }

	/**
	 * return the ID of Drone
	 * @return
	 */
	public int getID() {return DroneID; }
	
	/**
	 * draw a circle into the interface 
	 * @param mc
	 */
	public void drawDrone(MyCanvas mc) {
		mc.showCircle(x, y, rad, type);
	}
	/**
	 * 
	 * @return "Drone"
	 */
	protected String getStrType() {
		return "Drone";
	}
	
	/** 
	 * return string describing Drone
	 */
	public String toString() {
		return getStrType()+" "+ ID +" at "+Math.round(x)+", "+Math.round(y);
	}
	
	/**
	 * abstract method for checking a Drone in arena b
	 * @param b
	 */
	protected abstract void checkDrone(DroneArena b);
	
	/**
	 * abstract method for adjusting a Drone 
	 */
	protected abstract void adjustDrone();
	
	
	/**
	 * is Drone at ox,oy size or hitting this Drone
	 * @param ox
	 * @param oy
	 * @param or
	 * @return true if hitting
	 */
	public boolean hitting(double ox, double oy, double or) {
		return (ox-x)*(ox-x) + (oy-y)*(oy-y) < (or+rad)*(or+rad);
	}		// hitting if dist between Drone and ox,oy < ist rad + or
	
	/** is Drone hitting the other Drone
	 * 
	 * @param oDrone - the other Drone
	 * @return true if hitting
	 */
	public boolean hitting (Drone oDrone) {
		return hitting(oDrone.getX(), oDrone.getY(), oDrone.getRad());
	}
}