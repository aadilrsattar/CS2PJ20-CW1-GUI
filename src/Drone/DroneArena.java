package Drone;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

@SuppressWarnings("serial")
public class DroneArena implements Serializable{
    private int arenaWidth;
    private int arenaHeight;
    Random randomCoords; 
    ArrayList<Drone> numDrone; 
    public int numDroneArena; 

    public DroneArena(int x, int y) {
        arenaWidth = x;
        arenaHeight = y;
        randomCoords = new Random();
        numDrone = new ArrayList<Drone>();
        Drone d = new Drone(1, 1, Direction.North);
        d.resetID(); //resets id
    }

    public void addDrone() {
        int posX;
        int posY;
        if (numDrone.size() < (arenaWidth * arenaHeight)) {
            do {
                posX = randomCoords.nextInt(arenaWidth);
                posY = randomCoords.nextInt(arenaHeight);
            } while (getDroneAt(posX, posY) != null);

            Drone anyPlace = new Drone(posX, posY, Direction.getRandomDirection());
            numDrone.add(anyPlace);
        }
    }

    public int numofDrones() {
        if (numDroneArena < (arenaWidth * arenaHeight)) {
            numDroneArena++;
        } 
        else {
            System.err.println("\nYou've reached the maximum number of Drones. ");
        }
        return numDroneArena;
    }


    public boolean canMoveHere(int x, int y) {
        if (getDroneAt(x, y) != null || x >= arenaWidth || y >= arenaHeight || x < 0 || y < 0) {
        	return false;
        } 
        else {return true;}
    }

    public void showDrones(ConsoleCanvas c) {
        for (Drone d : numDrone) {
            d.displayDrone(c);
        }
    }

    public void moveAllDrones(DroneArena a) {
        for (Drone d : numDrone)
            d.tryToMove(a);
    }

    public Drone getDroneAt(int x, int y) {
        Drone res = null;
        for (Drone a : numDrone) {
            if (a.isHere(x, y) == true) { 
                res = a;
            }
        }return res; 
    }

    public int getArenaWidth() {
        return arenaWidth;
    }

    public int getArenaHeight() {
        return arenaHeight;
    }
    
    
    public String toString() {
        String res = "";
        if (numDrone.isEmpty() == false) {
            res = "";
            res += "The size of the arena is: " + arenaWidth + " by " + arenaHeight;
            for (int i = 0; i < numDrone.size(); i++) {
                res += "\n" + numDrone.get(i).toString();
            }
        }return res;
    }
}