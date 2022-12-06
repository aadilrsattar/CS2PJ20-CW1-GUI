package Drone;

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
    private final Image droneIMG, toyHeliIMG, objectIMG;

    /**
     * onstructor sets up relevant Graphics context and size of canvas
     * @param g
     * @param cs
     */
    public MyCanvas(GraphicsContext g, int xcs, int ycs) {
    	gc = g;
    	xCanvasSize = xcs;
    	yCanvasSize = ycs;
    	droneIMG = new Image("https://images.vexels.com/media/users/3/215129/isolated/lists/f98ff00414055616a9675d5d4aed6a9d-quadcopter-drone-top-view-stroke-icon.png");
        toyHeliIMG = new Image("https://cdn.iconscout.com/icon/premium/png-256-thumb/helicopter-4209758-3487458.png");
        objectIMG = new Image("https://www.iconsdb.com/icons/download/red/square-48.png");
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
    		Image img = droneIMG;
    		gc.setFill(new ImagePattern(img)); 
    	}
    	else if (type=='p') {
    		Image img = toyHeliIMG;
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

	/**
	 * Show Text .. by writing string s at position x,y
	 * @param x
	 * @param y
	 * @param s
	 */
	public void showText (double x, double y, String s) {
		gc.setTextAlign(TextAlignment.CENTER);							// set horizontal alignment
		gc.setTextBaseline(VPos.CENTER);								// vertical
		gc.setFill(Color.WHITE);										// colour in white
		gc.fillText(s, x, y);						// print score as text
	}

	/**
	 * Show Int .. by writing int i at position x,y
	 * @param x
	 * @param y
	 * @param i
	 */
	public void showInt (double x, double y, int i) {
		showText (x, y, Integer.toString(i));
	}	
}
