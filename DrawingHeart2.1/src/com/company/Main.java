package com.company;

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

import static java.lang.Math.sqrt;


/**
 * Change your definition of the heart from the DrawingHeart exercise so that you can
 * generate a filled heart as a single GCompound unit whose origin in the center of
 * the square. The pieces of the GCompound are now two GArcs (or GOvals) and a
 * GPolygon
 * The heart will change color randomly
 */

public class Main extends GraphicsProgram {

    public static void main(String[] args) {

        (new com.company.Main()).start();
    }

    public void run() {
        while(true) {
            Color heartColor = rgen.nextColor();
            GHeart valentineHeart = new GHeart(HEART_HEIGHT, heartColor);
            add(valentineHeart, getWidth() / 2, (getHeight() + HEART_HEIGHT) / 2 - HEART_HEIGHT / 2);
            pause(DELAY);


        }
    }

    private RandomGenerator rgen = new RandomGenerator();
    private final double HEART_HEIGHT = 150;
    private final double DELAY = 150;
}