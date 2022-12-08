/**
 * 
 */
package Drone;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;


/**
 * @StudentNo. 30004098
 * Class for Arena of Drones
 */
public class DroneArena implements Serializable {	
	private static final long serialVersionUID = 1L;

	Random randomGenerator;
 
	
	private int xSize, ySize;						// size of arena
	private ArrayList<Drone> allDrones;				// array list of all Drones in arena
	
	/**
	 * construct arena of size xS by yS
	 * @param xS
	 * @param yS
	 */
	DroneArena(int xS, int yS){
		xSize = xS;									//imports xSize and ySizefrom arena		
		ySize = yS;
		allDrones = new ArrayList<Drone>();			// list of all drones, initially empty
		addEnemyDrone();							// add Enemy Drone
		addPreyDrone();								// add Prey Drone
		addObject();								// add object
	}
	
	/**
	 * return arena size in x direction 
	 * @return
	 */
	public int getXSize() {
		return xSize;
	}
	
	/**
	 * return arena size in y direction
	 * @return
	 */
	public int getYSize() {
		return ySize;
	}
	
	/**
	 * draw all drones in the arena into interface bi
	 * @param bi
	 */
	public void drawArena(MyCanvas mc) {
		for (Drone b : allDrones) b.drawDrone(mc);		// draw all drones
	}
	
	/**
	 * check all drones .. see if need to change angle of moving drones, etc 
	 */
	public void checkdrones() {
		for (int i=0;i<allDrones.size();i++) {
			allDrones.get(i).checkDrone(this);
			if (allDrones.get(i).toRemove) {
				allDrones.remove(i);
			}
		}
	}
	
	/**
	 * Clears all drones
	 */
	public void clearDrones() {
		allDrones.clear();
	}
	
	/**
	 * adjust all drones .. move any movable ones
	 */
	public void adjustdrones() {
		for (Drone b : allDrones) b.adjustDrone();
	}

	/**
	 * return list of strings defining each Drone
	 * @return
	 */
	public ArrayList<String> describeAll() {
		ArrayList<String> ans = new ArrayList<String>();			// set up empty array
		for (Drone b : allDrones) ans.add(b.toString());			// add string defining each Drone
		return ans;													// return string list
	}
	
	/** 
	 * Check angle of Drone ... if hitting wall, rebound; if hitting Drone, change angle
	 * @param x				Drone x position
	 * @param y				y
	 * @param rad			radius
	 * @param ang			current angle
	 * @param notID			identify of Drone not to be checked
	 * @param edo			other Drone
	 * @return				new angle 
	 */
	public double CheckdroneAngle(double x, double y, double rad, double ang, int notID, Drone edo) {
		boolean bool=true;
		double ans = ang;
		while(bool){
			if (x < rad || x > xSize - rad) {ans = 180 - ans; return ans;}
			// if Drone hit left or right walls, set mirror angle, being 180-angle
			if (y < rad || y > ySize - rad) { ans = -ans; return ans;}
			// if try to go off top or bottom, set mirror angle
			
			for (Drone b : allDrones) 
				if (b.getID() != notID && b.hitting(x, y, rad)) {
					ans = 180*Math.atan2(y-b.getY(), x-b.getX())/Math.PI;
					if (b.getClass()==EnemyDrone.class && edo.getClass()==PreyDrone.class) {
						edo.toRemove=true;
					}
					bool=false;
				}
							// check all drones except one with given id
							// if hitting, return angle between the other Drone and this one.
							// return the angle
				else {
					bool=false;
				}
					
				
		}return ans; // return the angle
	}
	
	/**
	 * Function to check the placement of Drone before placing, to make sure the space is free
	 * @param x
	 * @param y
	 * @param r
	 * @return
	 */
	private Boolean checkplace(double x, double y, double r) {
		if((x+r>=xSize || y+r>= ySize || x<r || y<r ))return true; //check if hitting wall
		for(Drone d : allDrones) {
			if (d.hitting(x, y, r)) return true;	//checks if hitting any drone
			 }
		return false;
	}
	
	/**
	 * Function to add an Enemy Drone
	 */
	public void addEnemyDrone() {
		randomGenerator = new Random(); 
		double x,y,ang,r=20;
		int counter=0;
		do {
		x = randomGenerator.nextInt(xSize);					// Makes random x and y value
		y = randomGenerator.nextInt(ySize);		
		ang = randomGenerator.nextInt(360);
		counter++;
		}while (checkplace(x,y,r)&&counter<100);			// Checks if there is anything in that position
		allDrones.add(new EnemyDrone(x, y, r, ang, 5));		// Adds if there isn't anything in those coordinates
	}
	
	/**
	 * Function to add an add a Prey Drone
	 */
	public void addPreyDrone() {
		randomGenerator = new Random(); 
		double x,y,ang,r=12;
		int counter=0;
		do {
		x = randomGenerator.nextInt(xSize);					// Makes random x and y value
		y = randomGenerator.nextInt(ySize);
		ang = randomGenerator.nextInt(360);
		counter++;
		}while (checkplace(x,y,r)&&counter<100);			// Checks if there is anything in that position
		allDrones.add(new PreyDrone(x, y, r, ang, 8));		// Adds if there isn't anything in those coordinates
	}
	
	/**
	 * Function to add an add an Object
	 */
	public void addObject() {
		randomGenerator = new Random(); 
		double x,y,r=20;
		int counter=0;
		do {
		x = randomGenerator.nextInt(xSize);					// Makes random x and y value				
		y = randomGenerator.nextInt(ySize);
		counter++;
		}while (checkplace(x,y,r)&&counter<100);			// Checks if there is anything in that position
		allDrones.add(new Obstacle(x, y, r));				// Adds if there isn't anything in those coordinates
	}
}