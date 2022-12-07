package Drone;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;

import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.ArcType;
import javafx.scene.text.TextAlignment;

/**
 * @author shsmchlr
 *  Class to handle a canvas, used by different GUIs
 */
public class MyCanvas {
	int xCanvasSize = 500;				// constants for relevant sizes
	int yCanvasSize = 1000;
    GraphicsContext gc; 
    private static Image droneIMG, toyHeliIMG, objectIMG;

    /**
     * onstructor sets up relevant Graphics context and size of canvas
     * @param g
     * @param cs
     */
    public MyCanvas(GraphicsContext g, int xcs, int ycs) {
    	gc = g;
    	xCanvasSize = xcs;
    	yCanvasSize = ycs;
    	try {
			droneIMG = new Image(new FileInputStream("src/drone.png"));
			toyHeliIMG = new Image(new FileInputStream("src/helicopter.png"));
	        objectIMG = new Image(new FileInputStream("src/square-48.png"));
		} catch (FileNotFoundException e) {
			System.out.println("Image not load, kys");
		}

    }
    /**
     * get size in x of canvas
     * @return xsize
     */
    public int getXCanvasSize() {
    	return xCanvasSize;
    }
    /**
     * get size of xcanvas in y    
     * @return ysize
     */
    public int getYCanvasSize() {
    	return yCanvasSize;
    }

    /**
     * clear the canvas
     */
    public void clearCanvas() {
		gc.clearRect(0,  0,  xCanvasSize,  yCanvasSize);		// clear canvas
    }
    
	/**
     * drawIt ... draws object defined by given image at position and size
     * @param i		image
     * @param x		xposition	in range 0..1
     * @param y
     * @param sz	size
     */
	public void drawIt (Image i, double x, double y, double sz) {
			// to draw centred at x,y, give top left position and x,y size
			// sizes/position in range 0..1, so scale to canvassize 
		gc.drawImage(i, xCanvasSize * (x - sz/2), yCanvasSize*(y - sz/2), xCanvasSize*sz, yCanvasSize*sz);
	}

	
	/**
	 * show the ball at position x,y , radius r 
	 * @param x
	 * @param y
	 * @param rad
	 * @param col
	 */
    public void showCircle(double x, double y, double rad, char type) {
    	
    	if (type=='e') {
    		Image img = toyHeliIMG;
    		gc.setFill(new ImagePattern(img)); 
    	}
    	else if (type=='p') {
    		Image img = droneIMG;
    		gc.setFill(new ImagePattern(img)); 
    	}
    	else if (type=='o') {
    		Image img = objectIMG;
    		gc.setFill(new ImagePattern(img)); 
    	}

        showCircle(x, y, rad);                        // show the circle
    }
	/**
	 * show circle in current colour atx,y size rad
	 * @param x
	 * @param y
	 * @param rad
	 */
	public void showCircle(double x, double y, double rad) {
		gc.fillArc(x-rad, y-rad, rad*2, rad*2, 0, 360, ArcType.ROUND);	// fill circle
	}

}
