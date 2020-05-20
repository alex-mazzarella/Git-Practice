package com.company;

import acm.graphics.GLabel;
import acm.graphics.GLine;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;

public class Main extends GraphicsProgram {

    GLabel message;

    public static void main(String[] args) {

        (new com.company.Main()).start();
    }


    public void run() {
        
/**        drawing the following two lines to check where the actual boundaries are (debugging only)*/
        GLine topBorder = new GLine (0,0,getWidth(),0);
        GLine sideBorder = new GLine(getWidth(),0,getWidth(),getHeight());
        add(topBorder);
        add(sideBorder);

        setUpMessage();
        while (true) {
            animateMessage();
        }
    }


    private void animateMessage() {
        moveMessage();
        checkForWallCollision();
        pause(DELAY);
    }


    private void moveMessage() {
        message.move(vx, vy);
    }


    /***setting up the message displayed*/

    private void setUpMessage() {
        message = new GLabel("I LOVE JAVA");
        message.setFont("MS Gothic-40");
        message.setColor(rgen.nextColor());
        add(message, (getWidth()-message.getWidth())/2, (getHeight()-message.getAscent())/2);
        vx = rgen.nextDouble(1.0, 3.0); //initialize the speed of vx as random
        if (rgen.nextBoolean(0.5)) vx = -vx; // randomly set vx to positive or negative

    }

    /**method checks for wall collision. when a wall is hit,
     *  the message bounces and changes color randomly*/

    private void checkForWallCollision() {

        /**first collision check:if TRUE, we just hit the "floor" */
        if (message.getY() > (getHeight())) {
            message.setColor(rgen.nextColor());
            vy = -vy; //invert velocity direction
            /** assume bounce will move message an amount below the
             ceiling equal to the amount it would have gone
             beyond the ceiling.*/
            double diff = message.getY()-getHeight();

            message.move(0, -2 * diff);

        }

        /**second collision check:if TRUE, we just got past the top "ceiling"*/
        else if (message.getY() < message.getAscent()) {
            message.setColor(rgen.nextColor());
            vy = -vy; //invert velocity direction
            /** assume bounce will move message an amount below the
             ceiling equal to the amount it would have gone
             beyond the ceiling.*/
            double diff = message.getY() - message.getAscent();
            message.move(0, -2 * diff);
        }

        /**third collision check:if TRUE, we just got past the "right wall"*/

        else if (message.getX() > (getWidth() - message.getWidth())) {
            message.setColor(rgen.nextColor());
             vx = -vx; //invert velocity direction
            /** assume bounce will move message an amount
             * inside the wall equal to the amount it would have gone past the wall*/
            double diff = message.getX() - (getWidth() - message.getWidth());
            message.move(0, -2 * diff);
        }

        /**Fourth collision check: if true, we just got past the left wall*/
        else if (message.getX() < 0) {
            message.setColor(rgen.nextColor());
            vx = -vx; //invert velocity direction
            /** assume bounce will move ball an amount
             * inside the wall equal to the amount it would have gone past the wall*/
            double diff = message.getX();
            message.move(0, -2 * diff);
        }
    }




    /********************************
     * declaration of instance variables
     ************************************
     */

    /** initial Y velocity is set at 3
            */
    private static final double INITIAL_VY = 3;
    /**
     * velocity of the ball
     */
    private double vx, vy = INITIAL_VY;
    private final double DELAY = 25;
    private final RandomGenerator rgen = new RandomGenerator();

}