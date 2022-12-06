package Drone;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Drone implements Serializable {

    private Direction facing; // direction it faces
    private int dx, dy, ID; // private integers for class drone to use
    public static int droneCount = -1; // Static variable to constantly count existing drones

 
    public Drone(int x, int y, Direction f) {
        dx = x;
        dy = y;
        
        ID = droneCount++;
        facing = f;
    }

    public void resetID() {
        droneCount = 1;
    }

    public void displayDrone(ConsoleCanvas c) {
        char dronechar = 'D';
        c.showIt(dx, dy, dronechar);
    }

    public boolean isHere(int sx, int sy) {
        if (sx == dx && sy == dy) {
            return true;}
        else {
            return false;}
    }

    public void tryToMove(DroneArena a) {
        switch (facing) {
            case North:
                if (a.canMoveHere(dx - 1, dy)) {
                    dx = dx - 1;}
                else {
                    facing = facing.nextDirection();}
                break;
            case East:
                if (a.canMoveHere(dx, dy + 1)) {
                    dy = dy + 1;}
                else {
                    facing = facing.nextDirection();}
                break;
            case South:
                if (a.canMoveHere(dx + 1, dy)) {
                    dx = dx + 1;}
                else {
                    facing = facing.nextDirection();}
                break;
            case West:
                if (a.canMoveHere(dx, dy - 1)) {
                    dy = dy - 1;}
                else {
                    facing = facing.nextDirection();}
                break;
        }
    }

    public String toString() {
        return "Drone " + ID + " at " + dx + ", " + dy + " facing " + facing.toString();
    }
}