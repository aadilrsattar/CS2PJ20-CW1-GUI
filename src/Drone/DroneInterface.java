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
import javax.swing.JOptionPane;
import javax.swing.JTextField;
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
 * @StudentNo. 30004098
 * Initialises a JavaFX GUI with all features, including drones, savin etc.
 */

public class DroneInterface extends Application {
	private MyCanvas mc;
	private AnimationTimer timer;								// timer used for animation
	private VBox rtPane;										// vertical box for putting info
	private DroneArena arena;									// what arena is referred to in this class
	private JFileChooser chooser;								// for file load and save
	private static int xSize=500;								// The xSize initially
	private static int ySize=500;								// The ySize initially
	private static double speedMult=1;							// Speed Multiplier, for increasing speed
	
	public DroneInterface() {
    	chooser=new JFileChooser("/");							// This is a filter for loading files
    	FileFilter filter = new FileFilter() {					
    		@Override
    		public boolean accept(File f) {
    		if (f.getAbsolutePath().endsWith(".lom")) return true;	// It makes sure the file has the right extension
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
	    alert.setContentText("This Simulation contains Police Drones chasing Illegal Drone, but pretty dumbly.");		// give text
	    alert.showAndWait();										// show box and wait for user to close
	}
	
	private void showManual() {
		Alert alert = new Alert(AlertType.INFORMATION);				// define what box is
	    alert.setTitle("Manual");									// say is a Manual
	    alert.setHeaderText(null);
	    alert.setContentText("Buttons add new flying objects, and speed increases/decreases with the + and - buttons respectively.");		// give text
	    alert.showAndWait();										// show box and wait for user to close
	}

	 /**
	  * set up the mouse event - when mouse pressed, put Drone there
	  * @param canvas
	  */
	void setMouseEvents (Canvas canvas) {
	       canvas.addEventHandler(MouseEvent.MOUSE_MOVED, 		// for MOUSE MOVED event
	    	       new EventHandler<MouseEvent>() {
	    	           @Override
	    	           public void handle(MouseEvent e) {
	  		            	drawWorld();							// redraw world
	  		            	drawStatus();							// redraw status
	    	           }
	    	       });
	}
	/**
	 * set up the menu of commands for the GUI
	 * @return the menu bar
	 */
	MenuBar setMenu() {
		
		MenuBar menuBar = new MenuBar();						// create top menu
	
		Menu mFile = new Menu("File");							// add File main menu
		
		MenuItem loadnewArena = new MenuItem("New Arena");			// whose sub menu has new arena
		loadnewArena.setOnAction(new EventHandler<ActionEvent>() {	
			public void handle (ActionEvent s) {
				loadnewArena();										// which loads a new arena
			}
		});
		
		MenuItem mSave = new MenuItem("Save");					// whose sub menu has Save
		mSave.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent s) {
		    	saveArena();									// which saves arena
		    }
		    });
		
		
		
		MenuItem mLoad = new MenuItem("Load");					// whose sub menu has Load
		mLoad.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent s) {					
		    	loadArena();									// which load arena
		    	drawWorld ();									// redraws world
		    }
		});
		
		MenuItem mExit = new MenuItem("Exit");					// whose sub menu has Exit
		mExit.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent t) {					// action on exit is
	        	timer.stop();									// stop timer
		        System.exit(0);									// exit program
		    }
		});
		mFile.getItems().addAll(loadnewArena,mSave, mLoad, mExit);	// add new arena, save, load and exit to File menu
		
		Menu mHelp = new Menu("Help");							// create Help menu
		MenuItem mAbout = new MenuItem("About");				// add About sub menu item
		mAbout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
            	showAbout();									// and its action to print about
            }	
		});
		
		MenuItem mManual = new MenuItem("Manual");					// add Manual sub menu 
		mManual.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
            	showManual();									// and its action to print Manual
            }	
		});
		
		mHelp.getItems().addAll(mAbout, mManual);						// add About to Help main item
		
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

	    Button btnStop = new Button("Pause");					// now button for pause
	    btnStop.setOnAction(new EventHandler<ActionEvent>() {
	        @Override
	        public void handle(ActionEvent event) {
	           	timer.stop();									// and its action to stop the timer
	       }
	    });

	    Button btnEAdd = new Button("New Police Helicopter");		// now button for adding new helicopter
	    btnEAdd.setOnAction(new EventHandler<ActionEvent>() {
	        @Override
	        public void handle(ActionEvent event) {
	           	arena.addEnemyDrone();								// and its action to add Enemy drone 
	           	drawWorld();										// then draw world with the update
	       }
	    });
	    Button btnPAdd = new Button("New Illegal Drone");				// now button for adding new drone
	    btnPAdd.setOnAction(new EventHandler<ActionEvent>() {
	        @Override
	        public void handle(ActionEvent event) {
	           	arena.addPreyDrone();								// and its action to add PreyDrone
	           	drawWorld();										// then draw world with the update
	       }
	    });
	    
	    Button btnOAdd = new Button("New Obstacle");				// now button for stop
	    btnOAdd.setOnAction(new EventHandler<ActionEvent>() {
	        @Override
	        public void handle(ActionEvent event) {
	           	arena.addObject();								// and its action to addObject
	           	drawWorld();									// then draw world with the update
	       }
	    });
	    
	    Button btnSPEEDplus = new Button("+");							// now button to increase speed multiplier
	    btnSPEEDplus.setOnAction(new EventHandler<ActionEvent>() {
	        @Override
	        public void handle(ActionEvent event) {
	        	if (speedMult < 5)speedMult+=0.5;								// and its action to increase speedMult
	           	drawWorld();													// then draw world with the updates
	       }
	    });
	    
	    Button btnSPEEDminus = new Button("-");							// now button to decrease speed multiplier
	    btnSPEEDminus.setOnAction(new EventHandler<ActionEvent>() {
	        @Override
	        public void handle(ActionEvent event) {
	        	if (speedMult > 0.5) speedMult-=0.5;								// and its action to decrease speedMult
	           	drawWorld();														// then draw world with the updates
	       }
	    });
	    														// now add these buttons + labels to a HBox
	    return new HBox(new Label("Run: "), btnStart, btnStop, new Label(" Add: "), btnEAdd, btnPAdd, btnOAdd, new Label("Speed adjust: "), btnSPEEDplus, btnSPEEDminus);
	}


	/** 
	 * draw the world with Drone in it
	 */
	public void drawWorld () {
	 	mc.clearCanvas();						// set a Background
	 	mc.drawIt(mc.getbackground(), arena.getXSize()/2, arena.getYSize()/2, arena.getXSize(), arena.getYSize() );
	 	arena.drawArena(mc);
	}
	
	/**
	 * show where items are, displayed on a pane on right
	 */
	public void drawStatus() {
		rtPane.getChildren().clear();					// clear rtpane
		ArrayList<String> allBs = arena.describeAll();
		for (String s : allBs) {
			Label l = new Label(s); 		// turn description into a label
			rtPane.getChildren().add(l);	// add label to pane
		}	
	}
	/**
	 * function to load a new arena 
	 */
	private void loadnewArena() {	
		Thread t1 = new Thread(new Runnable() {			// loading it on to a new thread, as my main arena was crashing when it was loaded on the same thread as the rest of the program
			public void run() {
				JTextField nWidth = new JTextField();	// xCoord taken from user input
				JTextField nHeight = new JTextField();	// yCoord taken from user input
				Object[] fields = {"Enter a new Width", nWidth, "Enter a new Height", nHeight};	//Display the message to user, asking for width and height
				JOptionPane.showConfirmDialog(null, fields, "New Arena", JOptionPane.WARNING_MESSAGE); // Label of the dialog asking for input
				try {
					int xNew = Integer.parseInt(nWidth.getText());				// Gets input from the user input and puts it in to xNew and yNew
					int yNew = Integer.parseInt(nHeight.getText());				
					if (yNew>=100 && yNew <=500 || xNew>=100 && xNew <=500) {	// Check if it is a real number and the parameters are workable
						xSize=xNew;												// If the parameters work
						ySize=yNew;												// then add xNew and yNew as new Size
					    arena.clearDrones();									// function called to delete all drones from Array
						EnemyDrone.resetID();									// reset all ID of drones used in right pane
						PreyDrone.resetID();									
						Obstacle.resetID();										
					    arena = new DroneArena(xSize, ySize);					// set up arena
					    drawWorld();											// shows updated arena
					}
					else {JOptionPane.showMessageDialog(null, "Error, too big/small"); // if input is too big or small, tells the user
					}
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "Invalid Input, please input a number"); // if input is not a valid number, tells the user
				}
			}
		});
		t1.start();	// start on that thread
	}
	
	/**
	 * Function to save the current arena
	 */
	private void saveArena() {												// loading it on to a new thread, as my main arena was crashing when it was loaded on the same thread as the rest of the program
		Thread t1 = new Thread(new Runnable() {
				public void run() {
					int returnVal = chooser.showSaveDialog(null);			//shows the save dialog available in JFileChooser
					if (returnVal == JFileChooser.APPROVE_OPTION) {
						File selFile = chooser.getSelectedFile();			// return the name of the file selected to save to
						try {
							FileOutputStream FileInput = new FileOutputStream(selFile);
							ObjectOutputStream res = new ObjectOutputStream(FileInput);		// opens the file to write an object to
							res.writeObject(arena);											// writes arena to the file
							res.close();
							} catch (Exception e) {
								System.out.println("Cannot save file");}					// if save not done, show in Console
						}
				}
		});
		t1.start();	//start thread
	}
	
	
	/**
	 * Function to load a previous arena
	 */
	private void loadArena() {
		Thread t1 = new Thread(new Runnable() {							// loading it on to a new thread, as my main arena was crashing when it was loaded on the same thread as the rest of the program
				public void run() {
					int returnVal = chooser.showOpenDialog(null);		//shows the open dialog available in JFileChooser
	            	if (returnVal == JFileChooser.APPROVE_OPTION) {		
	            		File selFile = chooser.getSelectedFile();		// return the name of the file selected to load from
	            		try {
	            			
	            			FileInputStream FileInput = new FileInputStream(selFile);
	            			ObjectInputStream res = new ObjectInputStream(FileInput);	// opens the file to load an object from
	            			arena=(DroneArena)res.readObject();							// writes to arena from the file
	            			res.close();
	            		} catch (Exception e) {
	            			System.out.print("Cannot load this file");					// if save not done, show in Console
	            		}
	            	}
				}
		});
		t1.start();	// start thread
	}
	

	/* (non-Javadoc)
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("30004098 --  Aadil's Illegal Drone Catcher");
		
	    BorderPane bp = new BorderPane();
	    
	    bp.setPadding(new Insets(10, 20, 10, 20));

	    bp.setTop(setMenu());											// put menu at the top

	    Group root = new Group();										// create group with canvas
	    Canvas canvas = new Canvas( getxSize(), ySize );
	    root.getChildren().add( canvas );
	    bp.setLeft(root);												// load canvas to left area
	
	    mc = new MyCanvas(canvas.getGraphicsContext2D(), xSize, ySize);

	    setMouseEvents(canvas);											// set up mouse events

	    arena = new DroneArena(xSize, ySize);								// set up arena
	    drawWorld();
	    
	    timer = new AnimationTimer() {									// set up timer
	        public void handle(long currentNanoTime) {					// and its action when on
	        		arena.checkdrones();									// check the angle of all drones
		            arena.adjustdrones();								// move all drones
		            drawWorld();										// redraw the world
		            drawStatus();										// indicate where drones are
	        }
	    };

	    rtPane = new VBox();											// set vBox on right to list items
		rtPane.setAlignment(Pos.TOP_LEFT);								// set alignment
		rtPane.setPadding(new Insets(5, 75, 75, 5));					// padding
 		bp.setRight(rtPane);											// add rtPane to borderpane right
		  
	    bp.setBottom(setButtons());										// set bottom pane with buttons

	    Scene scene = new Scene(bp, xSize*1.5, ySize*1.2);				// set overall scene
        bp.prefHeightProperty().bind(scene.heightProperty());					
        bp.prefWidthProperty().bind(scene.widthProperty());

        primaryStage.setScene(scene);
        primaryStage.show();
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
	    Application.launch(args);										// launch the GUI

	}
	
	/**
	 * 
	 * @return xSize
	 */
	public static int getxSize() {								//	getter & setters 
		return xSize;											// for arena x length
	}
	
	/**
	 * 
	 * @return ySize
	 */
	public static int getySize() {
		return ySize;											// for arena y length 
	}
	
	/**
	 * 
	 * @return speedMult
	 */
	public static double getSpeedMult() {
		return speedMult;										// for speed multiplier
	}

}