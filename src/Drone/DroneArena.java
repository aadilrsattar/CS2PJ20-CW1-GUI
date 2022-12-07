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
	private static final long serialVersionUID = 1L;

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
		allBalls.add(new EnemyDrone(xS/2, yS/2, 25, 20, 10));	// add game Drone
		allBalls.add(new PreyDrone(xS/2, 30, 20, 20, 15));			// add target Drone
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
		for (int i=0;i<allBalls.size();i++) {
			allBalls.get(i).checkBall(this);
			if (allBalls.get(i).toRemove) {
				allBalls.remove(i);
			}
		}
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
	public double CheckBallAngle(double x, double y, double rad, double ang, int notID, Drone edo) {
		boolean bool=true;
		double ans = ang;
		while(bool){
			if (x < rad || x > xSize - rad) {ans = 180 - ans; return ans;}
			// if Drone hit (tried to go through) left or right walls, set mirror angle, being 180-angle
			if (y < rad || y > ySize - rad) { ans = -ans; return ans;}
			// if try to go off top or bottom, set mirror angle
			
			for (Drone b : allBalls) 
				if (b.getID() != notID && b.hitting(x, y, rad)) {
					ans = 180*Math.atan2(y-b.getY(), x-b.getX())/Math.PI;
					if (b.getClass()==EnemyDrone.class && edo.getClass()==PreyDrone.class) {
						edo.toRemove=true;
					}
					bool=false;
				}
					// check all balls except one with given id
					// if hitting, return angle between the other Drone and this one.
					// return the angle
				else {
					bool=false;
				}
					
				
		}return ans; // return the angle
	}
	
	private Boolean checkplace(double x, double y, double r) {
		
		if((x+r>=xSize || y+r>= ySize || x<r || y<r ))return true;
		
		for(Drone d : allBalls) {
			
			if (d.hitting(x, y, r)) return true;
			 
		}
		return false;
	}
	
	public void addEnemyDrone() {
		randomGenerator = new Random(); 
		double x,y,ang,r=20;
		int counter=0;
		do {
		x = randomGenerator.nextInt(xSize);
		y = randomGenerator.nextInt(ySize);
		ang = randomGenerator.nextInt(360);
		counter++;
		}while (checkplace(x,y,r)&&counter<100);
		
		allBalls.add(new EnemyDrone(x, y, r, ang, 10));

		}
	
	public void addPreyDrone() {
		randomGenerator = new Random(); 
		double x,y,ang,r=20;
		int counter=0;
		do {
		x = randomGenerator.nextInt(xSize);
		y = randomGenerator.nextInt(ySize);
		ang = randomGenerator.nextInt(360);
		counter++;
		}while (checkplace(x,y,r)&&counter<100);
		
		allBalls.add(new PreyDrone(x, y, r, ang, 13));

		}

	public void addObject() {
		randomGenerator = new Random(); 
		double x,y,r=20;
		int counter=0;
		do {
		x = randomGenerator.nextInt(xSize);
		y = randomGenerator.nextInt(ySize);
		counter++;
		}while (checkplace(x,y,r)&&counter<100);
		
		allBalls.add(new Object(x, y, r));

		}
}