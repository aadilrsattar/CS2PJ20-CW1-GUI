package Drone;

import javax.swing.*;
import java.io.*;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;


public class DroneInterface {
    private Scanner s;                    		
    private DroneArena myArena;                	
    private JFileChooser chooser;
    
    @SuppressWarnings({ "unused", "resource" })
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
    	
        s = new Scanner(System.in);                	
        myArena = new DroneArena(0, 0);
        
        int xInput = 0;
        int yInput = 0;

        char ch = ' ';
        do {
        	System.out.print("Enter (A)dd drone, get (I)nformation, (D)isplay arena, create (N)ew arena,\n(M)ove drones one space, move drones (T)en times, (S)ave arena, (L)oad arena or e(X)it > ");
            ch = s.next().charAt(0);
            s.nextLine();
            switch (ch) {
            
                case 'A':
                case 'a':
                    if (myArena.getArenaHeight() == 0 || myArena.getArenaWidth() == 0) {
                        System.out.println("Create an arena before adding drone \n");
                    } 
                    else {
                        myArena.addDrone();
                        System.out.println("\nDrone added. Total number of drones in arena = " + myArena.numofDrones()+"\n");
                    }
                    break;
                    
                case 'I':
                case 'i':
                    if (myArena.getArenaHeight() == 0 || myArena.getArenaWidth() == 0) {
                        System.out.println("No arena, please create one before proceeding.\n");
                    } 
                    else if (myArena.numDrone.isEmpty() == true) {
                        System.out.println("Warning! Please insert drones to move!");
                        System.out.println("\nArena dimensions: " + xInput + " by " + yInput + ".");
                    } 
                    else {
                        System.out.print("\n" + myArena.toString() + "\n");
                    }
                    break;
                    
                case 'd':
                case 'D':
                    if (myArena.getArenaHeight() == 0 || myArena.getArenaWidth() == 0) {
                        System.out.println("No arena, please create one before proceeding.\n");
                    } 
                    else {
                        System.out.println("\n");
                        doDisplay();
                    }
                    break;
                    
                case 'm':
                case 'M':
                    if (xInput == 0 || yInput == 0) {
                        System.out.println("No arena, please create one before proceeding.\n");
                    } 
                    else {
                        if (!myArena.numDrone.isEmpty()) {
                            System.out.println("\n");
                            myArena.moveAllDrones(myArena);
                            doDisplay();
                        } 
                        else {
                            System.out.println("Warning! Please insert drones to move!\n");
                        }
                    }
                    break;
                    
                case 't':
                case 'T':
                    if (myArena.getArenaHeight() == 0 || myArena.getArenaWidth() == 0) {
                        System.out.println("No arena, please create one before proceeding.\n");
                    } 
                    else {
                        if (!myArena.numDrone.isEmpty()) {
                            for (int i = 0; i < 10; i++) {
                                myArena.moveAllDrones(myArena);
                                doDisplay();
                                try {
                                    TimeUnit.MILLISECONDS.sleep(100); 
                                } catch (InterruptedException e) {
                                    System.out.format("IOException: %s%n", e);
                                }
                            }
                        } 
                        else {
                            System.out.println("Warning! Please insert drones to move!\n");
                        }
                    }
                    break;
                    
                case 'n':
                case 'N':
                    System.out.print("\n Input arena dimensions: ");
                    System.out.print(" X = ");
                    try {
                        xInput = s.nextInt();
                        while (xInput < 0) {
                            System.out.println("Please input a postive number. ");
                            System.out.println("X = ");
                            xInput = s.nextInt();
                        }
                    } catch (Exception a) {
                        System.out.println("Invalid Number");
                        System.out.print("\n X = ");
                        s.nextLine();
                        xInput = s.nextInt();
                    }
                    System.out.print(" Y = ");
                    try {
                        yInput = s.nextInt();
                        while (yInput < 0) {
                            System.out.println("Please input a postive number. ");
                            System.out.print("\n\nY = ");
                            yInput = s.nextInt();
                        }
                    } catch (Exception b) {
                        System.out.println("Invalid Number.");
                        System.out.print("Y = ");
                        s.nextLine();
                        yInput = s.nextInt();
                    }
                    myArena = new DroneArena(xInput, yInput);
                    System.out.println("Arena created! Dimensions: " + xInput + " by " + yInput + ".");
                    break;
                    
                case 's':
                case 'S':
                    if (myArena.getArenaHeight() == 0 || myArena.getArenaWidth() == 0) {
                        System.out.println("No arena, please create one before proceeding.\n");
                    } 
                    else {
                    	int returnVal = chooser.showSaveDialog(null);
                    	if (returnVal == JFileChooser.APPROVE_OPTION) {
                    		File selFile = chooser.getSelectedFile();
                			try {
								FileOutputStream FileInput = new FileOutputStream(selFile);
								ObjectOutputStream res = new ObjectOutputStream(FileInput);
								res.writeObject(myArena);
							} catch (IOException e) {
								e.printStackTrace();
							}

                    	}
                    }
                    break;
                    
                case 'l':
                case 'L':
                	int returnVal = chooser.showOpenDialog(null);
                	if (returnVal == JFileChooser.APPROVE_OPTION) {
                		File selFile = chooser.getSelectedFile();
                		try {
                			FileInputStream FileInput = new FileInputStream(selFile);
                			ObjectInputStream res = new ObjectInputStream(FileInput);
                			myArena=(DroneArena)res.readObject();
                		} catch (Exception e) {
                			System.out.print(" ");
                		}
                	}

                    break;
            }
        } while (ch != 'X');                       

        s.close();                                   
    }

    void doDisplay() {
        ConsoleCanvas field = new ConsoleCanvas(myArena.getArenaWidth() + 2, myArena.getArenaHeight() + 2);
        myArena.showDrones(field);
        System.out.println(field.toString());
    }


    @SuppressWarnings("unused")
	public static void main(String[] args) {
        DroneInterface r = new DroneInterface();    
    }
}
