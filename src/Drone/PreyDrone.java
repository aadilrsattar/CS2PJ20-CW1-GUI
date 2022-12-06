/**
 * 
 */
package Drone;

import java.io.Serializable;

/**
 * @author shsmchlr
 * The Target Ball which you are aiming at
 */
public class PreyDrone extends Drone{
	
	private int score;
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
		score = 0;
		type = 'p';
		bAngle = ia;
		bSpeed = is;
	}

	/** 
	 * checkBall in arena 
	 * @param b DroneArena
	 */
	@Override
	protected void checkBall(DroneArena b) {
		if (b.checkHit(this)) score++;			// if been hit, then increase score
		bAngle = b.CheckBallAngle(x, y, rad, bAngle, ballID);
	}
	/**
	 * draw Ball and display score
	 */
	public void drawBall(MyCanvas mc) {
		super.drawBall(mc);
		mc.showInt(x, y, score);
	}

	/**
	 * adjustBall
	 * for moving the ball - not needed here
	 */
	@Override
	protected void adjustBall() {
		double radAngle = bAngle*Math.PI/180;		// put angle in radians
		x += bSpeed * Math.cos(radAngle);		// new X position
		y += bSpeed * Math.sin(radAngle);		// new Y position
		}	
	/**
	 * return string defining ball ... here as target
	 */
	protected String getStrType() {
		return "Prey Drone";
	}	
}