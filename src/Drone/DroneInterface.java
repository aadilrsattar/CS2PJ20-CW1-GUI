/**
 * 
 */
package Drone;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * @author shsmchlr
 * Example with balls, paddles and targets in arena
 */

public class DroneInterface extends Application {
	private MyCanvas mc;
	private AnimationTimer timer;								// timer used for animation
	private VBox rtPane;										// vertical box for putting info
	private DroneArena arena;
	private JFileChooser chooser;
	private static int xSize=500;
	private static int ySize=600;
	public static double SPEEDmult=1;
	
	public DroneInterface() {
    	chooser=new JFileChooser("/");
    	FileFilter filter = new FileFilter() {
    		@Override
    		public boolean accept(File f) {
    		if (f.getAbsolutePath().endsWith(".lom")) return true;
    		if (f.isDirectory()) return true;
    		
    		return false;
    		}
    		public String getDescription() {
    		return "lom";
    		}
    	};
    	chooser.setFileFilter(filter);
	}
	
	/**
	 * function to show in a box ABout the programme
	 */
	private void showAbout() {
	    Alert alert = new Alert(AlertType.INFORMATION);				// define what box is
	    alert.setTitle("About");									// say is About
	    alert.setHeaderText(null);
	    alert.setContentText("RJM's JavaFX Demonstrator");			// give text
	    alert.showAndWait();										// show box and wait for user to close
	}

	 /**
	  * set up the mouse event - when mouse pressed, put ball there
	  * @param canvas
	  */
	void setMouseEvents (Canvas canvas) {
	       canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, 		// for MOUSE PRESSED event
	    	       new EventHandler<MouseEvent>() {
	    	           @Override
	    	           public void handle(MouseEvent e) {
	  		            	drawWorld();							// redraw world
	  		            	drawStatus();
	    	           }
	    	       });
	}
	/**
	 * set up the menu of commands for the GUI
	 * @return the menu bar
	 */
	MenuBar setMenu() {
		
		MenuBar menuBar = new MenuBar();						// create main menu
	
		Menu mFile = new Menu("File");							// add File main menu
		
		MenuItem mSave = new MenuItem("Save");					// whose sub menu has Exit
		
		mSave.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent s) {
		    	saveArena();
		    }
		    });
		
		MenuItem mLoad = new MenuItem("Load");					// whose sub menu has Exit
		mLoad.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent s) {					// action on exit is
		    	loadArena();

		    }
		});
		
		MenuItem mExit = new MenuItem("Exit");					// whose sub menu has Exit
		mExit.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent t) {					// action on exit is
	        	timer.stop();									// stop timer
		        System.exit(0);									// exit program
		    }
		});
		mFile.getItems().addAll(mSave, mLoad, mExit);							// add exit to File menu
		
		Menu mHelp = new Menu("Help");							// create Help menu
		MenuItem mAbout = new MenuItem("About");				// add About sub men item
		mAbout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
            	showAbout();									// and its action to print about
            }	
		});
		mHelp.getItems().addAll(mAbout);						// add About to Help main item
		
		menuBar.getMenus().addAll(mFile, mHelp);				// set main menu with File, Help
		return menuBar;											// return the menu
	}

	/**
	 * set up the horizontal box for the bottom with relevant buttons
	 * @return
	 */
	private HBox setButtons() {
	    Button btnStart = new Button("Start");					// create button for starting
	    btnStart.setOnAction(new EventHandler<ActionEvent>() {	// now define event when it is pressed
	        @Override
	        public void handle(ActionEvent event) {
	        	timer.start();									// its action is to start the timer
	       }
	    });

	    Button btnStop = new Button("Pause");					// now button for stop
	    btnStop.setOnAction(new EventHandler<ActionEvent>() {
	        @Override
	        public void handle(ActionEvent event) {
	           	timer.stop();									// and its action to stop the timer
	       }
	    });

	    Button btnEAdd = new Button("New Enemy Drone");				// now button for stop
	    btnEAdd.setOnAction(new EventHandler<ActionEvent>() {
	        @Override
	        public void handle(ActionEvent event) {
	           	arena.addEnemyDrone();								// and its action to stop the timer
	           	drawWorld();
	       }
	    });
	    Button btnPAdd = new Button("New Prey Drone");				// now button for stop
	    btnPAdd.setOnAction(new EventHandler<ActionEvent>() {
	        @Override
	        public void handle(ActionEvent event) {
	           	arena.addPreyDrone();								// and its action to stop the timer
	           	drawWorld();
	       }
	    });
	    
	    Button btnOAdd = new Button("New Object");				// now button for stop
	    btnOAdd.setOnAction(new EventHandler<ActionEvent>() {
	        @Override
	        public void handle(ActionEvent event) {
	           	arena.addObject();								// and its action to stop the timer
	           	drawWorld();
	       }
	    });
	    
	    Button btnSPEEDplus = new Button("+");				// now button for stop
	    btnSPEEDplus.setOnAction(new EventHandler<ActionEvent>() {
	        @Override
	        public void handle(ActionEvent event) {
	        	if (SPEEDmult < 5)SPEEDmult+=0.5;								// and its action to stop the timer
	           	drawWorld();
	       }
	    });
	    
	    Button btnSPEEDminus = new Button("-");				// now button for stop
	    btnSPEEDminus.setOnAction(new EventHandler<ActionEvent>() {
	        @Override
	        public void handle(ActionEvent event) {
	        	if (SPEEDmult > 0.5) SPEEDmult-=0.5;								// and its action to stop the timer
	           	drawWorld();
	       }
	    });
	    														// now add these buttons + labels to a HBox
	    return new HBox(new Label("Run: "), btnStart, btnStop, new Label(" Add: "), btnEAdd, btnPAdd, btnOAdd, new Label("Speed adjust: "), btnSPEEDplus, btnSPEEDminus);
	}


	/** 
	 * draw the world with ball in it
	 */
	public void drawWorld () {
	 	mc.clearCanvas();						// set beige colour
	 	mc.drawIt(MyCanvas.backgroundGIF, arena.getXSize()/2, arena.getYSize()/2, arena.getXSize(), arena.getYSize() );
	 	arena.drawArena(mc);
	}
	
	/**
	 * show where ball is, in pane on right
	 */
	public void drawStatus() {
		rtPane.getChildren().clear();					// clear rtpane
		ArrayList<String> allBs = arena.describeAll();
		for (String s : allBs) {
			Label l = new Label(s); 		// turn description into a label
			rtPane.getChildren().add(l);	// add label	
		}	
	}
	
	private void saveArena() {
		Thread t1 = new Thread(new Runnable() {
				public void run() {
					int returnVal = chooser.showSaveDialog(null);
					if (returnVal == JFileChooser.APPROVE_OPTION) {
						File selFile = chooser.getSelectedFile();
						try {
							FileOutputStream FileInput = new FileOutputStream(selFile);
							ObjectOutputStream res = new ObjectOutputStream(FileInput);
							res.writeObject(arena);
							res.close();
							} catch (Exception e) {
								System.out.println("Cannot save file");}	
						}

				}
		});
		t1.start();
	}
	private void loadArena() {
		Thread t2 = new Thread(new Runnable() {
				public void run() {
					int returnVal = chooser.showOpenDialog(null);
	            	if (returnVal == JFileChooser.APPROVE_OPTION) {
	            		File selFile = chooser.getSelectedFile();
	            		try {
	            			
	            			FileInputStream FileInput = new FileInputStream(selFile);
	            			ObjectInputStream res = new ObjectInputStream(FileInput);
	            			arena=(DroneArena)res.readObject();
	            			res.close();
	            		} catch (Exception e) {
	            			System.out.print("Cannot load this File");
	            		}
	            	}
				}
		});
		t2.start();
	}
	

	/* (non-Javadoc)
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("RJMs attempt at Moving my Balls");
	    BorderPane bp = new BorderPane();
	    bp.setPadding(new Insets(10, 20, 10, 20));

	    bp.setTop(setMenu());											// put menu at the top

	    Group root = new Group();										// create group with canvas
	    Canvas canvas = new Canvas( getxSize(), ySize );
	    root.getChildren().add( canvas );
	    bp.setLeft(root);												// load canvas to left area
	
	    mc = new MyCanvas(canvas.getGraphicsContext2D(), getxSize(), ySize);

	    setMouseEvents(canvas);											// set up mouse events

	    arena = new DroneArena(getxSize(), ySize);								// set up arena
	    drawWorld();
	    
	    timer = new AnimationTimer() {									// set up timer
	        public void handle(long currentNanoTime) {					// and its action when on
	        		arena.checkBalls();									// check the angle of all balls
		            arena.adjustBalls();								// move all balls
		            drawWorld();										// redraw the world
		            drawStatus();										// indicate where balls are
	        }
	    };

	    rtPane = new VBox();											// set vBox on right to list items
		rtPane.setAlignment(Pos.TOP_LEFT);								// set alignment
		rtPane.setPadding(new Insets(5, 75, 75, 5));					// padding
 		bp.setRight(rtPane);											// add rtPane to borderpane right
		  
	    bp.setBottom(setButtons());										// set bottom pane with buttons

	    Scene scene = new Scene(bp, getxSize()*1.5, ySize*1.2);							// set overall scene
        bp.prefHeightProperty().bind(scene.heightProperty());
        bp.prefWidthProperty().bind(scene.widthProperty());

        primaryStage.setScene(scene);
        primaryStage.show();
	  

	}
	



	/**
	 * @param args
	 */
	public static void main(String[] args) {
	    Application.launch(args);			// launch the GUI

	}

	public static int getxSize() {
		return xSize;
	}
	
	public static int getySize() {
		return ySize;
	}


}