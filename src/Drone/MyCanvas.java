package Drone;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.ArcType;

/**
 * @StudentNo. 30004098
 *  Class to handle a canvas, and images within it
 */
public class MyCanvas {
	private int xCanvasSize, yCanvasSize;							// constants for relevant sizes
    private GraphicsContext gc; 									// name for GraphicsContext within this class
    private static Image droneIMG, heliIMG, objectIMG, birdIMG; 	// Image for the different filling to portray different types of drones
    private static Image backgroundGIF;								// Image for the background
    

    /**
     * 
     * @param g
     * @param xSize
     * @param ySize
     */
    MyCanvas(GraphicsContext g, int xSize, int ySize) {
    	gc = g;	
    	xCanvasSize = xSize;									//getting parameters from arguments
    	yCanvasSize = ySize;
    	loadImages();											// loadImages function, to loadImages
    	


    }
      
    /**
     * 
     * @return xCanvasSize										// getters and setters
     */
    public int getXCanvasSize() {
    	return xCanvasSize;										// for x size of canvas
    }
    /**
     * get size of xcanvas in y    
     * @return yCanvasSize
     */
    public int getYCanvasSize() {
    	return yCanvasSize;										// for y size of canvas
    }
    
    /**
     * 
     * @return backgroundGIF
     */
    public Image getbackground() {	
    	return backgroundGIF;									// for background to be used in interface
    }
    /**
     * clear the canvas
     */
    
    /**
     * loadImages function to load images from src folder
     */
    private static void loadImages() {
    	if (droneIMG != null && heliIMG != null && objectIMG != null && backgroundGIF != null && birdIMG != null ) { //makes sure only loads the images once
    		return;
    	}
    	try {
    		droneIMG = new Image(new FileInputStream("src/drone.png"));				//load image of drone from src
    		heliIMG = new Image(new FileInputStream("src/helicopter.png"));			//load image of helicopter from src
    		objectIMG = new Image(new FileInputStream("src/square-48.png"));		//load image of object from src
    		backgroundGIF = new Image(new FileInputStream("src/background.gif"));	//load gif of Background from src
    		birdIMG = new Image(new FileInputStream("src/birds.png"));				//load png of birds from src
    	} catch (FileNotFoundException e) {
    		System.out.println("Images not loading, check src folder");
    	}
    }
    
    /**
     * clear the canvas
     */
    public void clearCanvas() {
		gc.clearRect(0,  0,  xCanvasSize,  yCanvasSize);		// clear canvas
    }
    
	/**
     * drawIt ... draws Obstacle defined by given image at position and size
     * @param i		image
     * @param x		centre of the positions given
     * @param y	
     * @param sx	xsize of arena
     * @param sy	ysize of arena
     */
	public void drawIt (Image i, double x, double y, double sx, double sy) {
			// to draw centred at x,y, give top left position and x,y size
			// sizes/position in range 0..1, so scale to canvassize 
		gc.drawImage(i, x-sx/2, y-sy/2, sx, sy);
	}

	
	/**
	 * show the Drone at position x,y , radius r, and different fills depending on type
	 * @param x
	 * @param y
	 * @param rad
	 * @param type
	 */
    public void showCircle(double x, double y, double rad, char type) {
    	
    	if (type=='h') {						//if type is h (helicopter)
    		Image img = heliIMG;	
    		gc.setFill(new ImagePattern(img)); // set image fill to helicopter
    	}
    	else if (type=='i') {					// if type is i (illegal drone)
    		Image img = droneIMG;				
    		gc.setFill(new ImagePattern(img)); 	// set circle fill to drone image
    	}
    	else if (type=='o') {					// if type is o(object)
    		Image img = objectIMG;
    		gc.setFill(new ImagePattern(img)); 	// set circle fill to object image
    	}
    	else if (type=='b') {					// if type is b(bird)
    		Image img = birdIMG;
    		gc.setFill(new ImagePattern(img));	// set circle fill to bird image
    	}
        showCircle(x, y, rad);           // show the circle
    }
	/**
	 * show circle in current colour atx,y size rad
	 * @param x
	 * @param y
	 * @param rad
	 */
	public void showCircle(double x, double y, double rad) {
		gc.fillArc(x-rad, y-rad, rad*2, rad*2, 0, 360, ArcType.ROUND);	// fill circle and show it
	}

}
