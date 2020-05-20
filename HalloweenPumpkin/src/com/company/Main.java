package com.company;

import acm.graphics.GObject;
import acm.graphics.GPoint;
import acm.program.GraphicsProgram;

import java.awt.event.MouseEvent;


/**
 * Write a GraphicsProgram that draws a picture of the Halloween pumpkin
 * The head is an orange circle, and the eyes and mouth are filled polygons. The stem
 * is presumably a GRect. Use named constants in your program to define the sizes of
 * the various features.
 */

public class Main extends GraphicsProgram {

    public static void main(String[] args) {

        (new com.company.Main()).start();
    }

    public void run() {
        GHalloweenPumpkin halloweenPumpkin = new GHalloweenPumpkin(HEAD_HEIGHT);
        add(halloweenPumpkin, (getWidth()-HEAD_HEIGHT)/2,(getHeight()-HEAD_HEIGHT)/2);

        addMouseListeners();
    }

    public void mousePressed(MouseEvent e) {
        last = new GPoint(e.getX(), e.getY()); //last point on Canvas where mouse was clicked
        gobj = getElementAt(last); //Returns the object present at the point where mouse was clicked. null if no object
    }

    public void mouseDragged(MouseEvent e) {
        if (gobj != null) {
            gobj.move(e.getX() - last.getX(), e.getY() - last.getY());
            last = new GPoint(e.getPoint()); //last point on canvas where the mouse(and object) was dragged to
        }
    }


    private GObject gobj; /* The object being dragged */
    private GPoint last; /* The last mouse position */
    public final double HEAD_HEIGHT = 300;

}