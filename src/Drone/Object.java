/**
 * 
 */
package Drone;

/**
 * @author shsmchlr
 * Ball which gets in way of game ball
 */
public class Object extends Drone {

	/**
	 * 
	 */
	public Object() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ix
	 * @param iy
	 * @param ir
	 */
	public Object(double ix, double iy, double ir) {
		super(ix, iy, ir);
		type = 'o';
	}

	/* (non-Javadoc)
	 * @see uk.ac.reading.profrichardmitchell83.Ball#checkBall(uk.ac.reading.profrichardmitchell83.BallArena)
	 */
	@Override
	protected void checkBall(DroneArena b) {
		// nowt to do

	}

	/* (non-Javadoc)
	 * @see uk.ac.reading.profrichardmitchell83.Ball#adjustBall()
	 */
	@Override
	protected void adjustBall() {
		// nowt to do

	}
	protected String getStrType() {
		return "Object";
	}	

}