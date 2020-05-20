package com.company;

import acm.graphics.GObject;
import acm.graphics.GPoint;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;


/**
 * This program allows users to create rectangles on the canvas
 * by pressing and dragging with the mouse.
 * If the user presses on an existing rectangle, it can be moved by dragging the mouse
 * then if a key of the keyboard is pressed, the last selected rectangle changes color randomly
 */

public class Main extends GraphicsProgram {

    public static void main(String[] args) {

        (new com.company.Main()).start();

    }

    /**
     * Runs the program
     */

    public void run() {
        addMouseListeners();
        addKeyListeners();
    }

    /**
     * Called on mouse press to:
     * -record the starting coordinates of new object (if mouse pressed on a pixel with NO existing object)
     * -record the starting coordinates of existing object (if mouse pressed on a pixel with existing object)
     */
    public void mousePressed(MouseEvent e) {
        last = new GPoint(e.getX(), e.getY()); //last point on Canvas where mouse was clicked
        gobj = getElementAt(last); //Returns the object present at the point where mouse was clicked. null if no object
        if (gobj == null) {  //if gobj is null, no existing object where mouse was pressed. new object created
            startX = e.getX();
            startY = e.getY();
            currentRect = new GRect(startX, startY, 0, 0);
            currentRect.setFilled(true);
            add(currentRect);
        } else {  //if gobj is NOT null, existing object found where mouse was pressed
            currentRect = (GRect) gobj;
        }
    }

    /**
     * Called on mouse drag to:
     * -reshape the current rectangle (if mouse was pressed on a pixel with NO existing object)
     * -move the object that was pressed over (if mouse was pressed on a pixel with  existing object)
     */
    public void mouseDragged(MouseEvent e) {

        if (gobj == null) { //if gobj == null, means that mouse was pressed on an empty pixel and
            // a new object is being created. Therefore, when the mouse is dragged,
            // the new object gets reshaped
            double x = Math.min(e.getX(), startX);
            double y = Math.min(e.getY(), startY);
            double width = Math.abs(e.getX() - startX);
            double height = Math.abs(e.getY() - startY);
            currentRect.setBounds(x, y, width, height);
        } else {  //if gobj == null, means that mouse was pressed on an existing object. Therefore
            // the existing object will be dragged
            gobj.move(e.getX() - last.getX(), e.getY() - last.getY());
            last = new GPoint(e.getPoint()); //last point on canvas where the mouse(and object) was dragged to

        }
    }

    /**if any key gets typed, after having pressed the mouse
     * on an existing object, that object changes to a random color*/

    public void keyTyped(KeyEvent e) {
        if (gobj != null) {
            gobj.setColor(rgen.nextColor());
        }
    }


    /* Private statements */


    private GRect currentRect; /* The current rectangle */
    private double startX; /* The initial mouse X position */
    private double startY; /* The initial mouse Y position */
    private GObject gobj; /* used to check if there is an existing object where the mouse is clicked */
    private GPoint last; /* The last mouse position */
    private final RandomGenerator rgen = RandomGenerator.getInstance();
}