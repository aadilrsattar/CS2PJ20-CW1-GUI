/**
 * 
 */
package Drone;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;


/**
 * @author shsmchlr
 * Class for Arena of balls
 */
public class DroneArena implements Serializable {	
	Random randomGenerator;
 
	
	int xSize, ySize;						// size of arena
	private ArrayList<Drone> allBalls;			// array list of all balls in arena
	/**
	 * construct an arena
	 */
	DroneArena() {
		this(500, 400);			// default size

	}
	/**
	 * construct arena of size xS by yS
	 * @param xS
	 * @param yS
	 */
	DroneArena(int xS, int yS){
		xSize = xS;
		ySize = yS;
		allBalls = new ArrayList<Drone>();					// list of all balls, initially empty
		allBalls.add(new EnemyDrone(xS/2, yS/2, 30, 45, 10));	// add game Drone
		allBalls.add(new PreyDrone(xS/2, 30, 20, 15, 15));			// add target Drone
	}
	/**
	 * return arena size in x direction
	 * @return
	 */
	public double getXSize() {
		return xSize;
	}
	/**
	 * return arena size in y direction
	 * @return
	 */
	public double getYSize() {
		return ySize;
	}
	/**
	 * draw all balls in the arena into interface bi
	 * @param bi
	 */
	public void drawArena(MyCanvas mc) {
		for (Drone b : allBalls) b.drawBall(mc);		// draw all balls
	}
	/**
	 * check all balls .. see if need to change angle of moving balls, etc 
	 */
	public void checkBalls() {
		for (Drone b : allBalls) b.checkBall(this);	// check all balls
	}
	/**
	 * adjust all balls .. move any moving ones
	 */
	public void adjustBalls() {
		for (Drone b : allBalls) b.adjustBall();
	}

	/**
	 * return list of strings defining each Drone
	 * @return
	 */
	public ArrayList<String> describeAll() {
		ArrayList<String> ans = new ArrayList<String>();		// set up empty arraylist
		for (Drone b : allBalls) ans.add(b.toString());			// add string defining each Drone
		return ans;												// return string list
	}
	/** 
	 * Check angle of Drone ... if hitting wall, rebound; if hitting Drone, change angle
	 * @param x				Drone x position
	 * @param y				y
	 * @param rad			radius
	 * @param ang			current angle
	 * @param notID			identify of Drone not to be checked
	 * @return				new angle 
	 */
	public double CheckBallAngle(double x, double y, double rad, double ang, int notID) {
		boolean bool=true;
		double ans = ang;
		while(bool){
			if (x < rad || x > xSize - rad) ans = 180 - ans;
			// if Drone hit (tried to go through) left or right walls, set mirror angle, being 180-angle
			if (y < rad || y > ySize - rad) ans = -ans;
			// if try to go off top or bottom, set mirror angle
		
			for (Drone b : allBalls) 
				if (b.getID() != notID && b.hitting(x, y, rad)) ans = 180*Math.atan2(y-b.getY(), x-b.getX())/Math.PI;
					// check all balls except one with given id
					// if hitting, return angle between the other Drone and this one.
					// return the angle
				else {
					bool=false;
				}
					
				
		}return ans; // return the angle
	}

	/**
	 * check if the target Drone has been hit by another Drone
	 * @param target	the target Drone
	 * @return 	true if hit
	 */
	public boolean checkHit(Drone target) {
		boolean ans = false;
		for (Drone b : allBalls)
			if (b instanceof EnemyDrone && b.hitting(target)) ans = true;
				// try all balls, if GameBall, check if hitting the target
		return ans;
	}
	
	public void addEnemyDrone() {
		randomGenerator = new Random(); 
		
		boolean bool=true;
		while(bool) {
			double x = randomGenerator.nextInt(xSize);
			double y = randomGenerator.nextInt(ySize);
			double r = 20;
			if (x < r || x > xSize - r);
			else if (y < r || y > ySize - r);
			else {
				for (Drone b : allBalls) {
					if (b.hitting(x, y, 30));
					else {
						allBalls.add(new EnemyDrone(x, y, 20, 45, 10));
						bool=false;
						break;
					}
				}
			}
		}
	}
	public void addPreyDrone() {
		randomGenerator = new Random(); 
		
		boolean bool=true;
		while(bool) {
			double x = randomGenerator.nextInt(xSize);
			double y = randomGenerator.nextInt(ySize);
			double r = 20;
			if (x < r || x > xSize - r);
			else if (y < r || y > ySize - r);
			else {
				for (Drone b : allBalls) {
					if (b.hitting(x, y, 30));
					else {
						allBalls.add(new PreyDrone(x, y, 20, 45, 10));
						bool=false;
						break;
					}
				}
			}
		}
	}
}