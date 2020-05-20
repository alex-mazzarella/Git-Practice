/*
 * File: Breakout.java
 * -------------------
 * This file will eventually implement the game of Breakout.
 */
package com.company;

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;


public class Main extends GraphicsProgram {
    /**
     * Width and height of application window in pixels
     */
    public static final int APPLICATION_WIDTH = 400;
    public static final int APPLICATION_HEIGHT = 600;
    /**
     * Dimensions of game board (usually the same)
     */
    private static final int WIDTH = APPLICATION_WIDTH;
    private static final int HEIGHT = APPLICATION_HEIGHT;
    /**
     * Dimensions of the paddle
     */
    private static final int PADDLE_WIDTH = 60;
    private static final int PADDLE_HEIGHT = 10;
    /**
     * Offset of the paddle up from the bottom
     */
    private static final int PADDLE_Y_OFFSET = 30;
    /**
     * Number of bricks per row
     */
    private static final int NBRICKS_PER_ROW = 10;
    /**
     * Number of rows of bricks
     */
    private static final int NBRICK_ROWS = 10;
    /**
     * Separation between bricks
     */
    private static final int BRICK_SEP = 4;
    /**
     * Width of a brick
     */
    private static final int BRICK_WIDTH =
            (WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;
    /**
     * Height of a brick
     */
    private static final int BRICK_HEIGHT = 8;
    /**
     * Radius of the ball in pixels
     */
    private static final int BALL_RADIUS = 10;
    /**
     * Offset of the top brick row from the top
     */
    private static final int BRICK_Y_OFFSET = 70;
    /**
     * Number of turns
     */
    private static final int NTURNS = 3;
    /**
     * initial Y velocity is set at 3
     */
    private static final double INITIAL_VY = 3;
    /**
     * velocity of the ball
     */
    private double vx, vy = INITIAL_VY;

    /**
     * declaring paddle in main to make it visible to all methods. this way,
     * I can access the info from thee method mouseMoved
     */
    private GRect brick;
    private GRect paddle;
    private GOval ball;
    private GObject collider; // used to check if there is a collision in method checkCollidingObject
    private double numberOfBricksLeft = NBRICKS_PER_ROW * NBRICK_ROWS;
    int turnsLeft = NTURNS;
    private final RandomGenerator rgen = RandomGenerator.getInstance();


    public static void main(String[] args) {

        (new com.company.Main()).start();
    }

    public void run() {

        setUpGame();
        while ((turnsLeft > 0) && (numberOfBricksLeft > 0)) {
            playGame();
        }
        GLabel gameOver = new GLabel("GAME OVER!");
        gameOver.setFont(new Font("Serif", Font.BOLD, 35));
        gameOver.setLocation(((double) WIDTH / 2) - ((gameOver.getWidth()) / 2),
                ((double) HEIGHT / 2) - (gameOver.getAscent() / 2));
        add(gameOver);


    }


    /**
     * setUpGame generates the bricks and paddle before the start of the game
     */
    private void setUpGame() {
        setUpBricks();
        setUpPaddle();

    }


    /**
     * method SetUpBricks populates the screen with the rows of bricks specified in the
     * Static instance declaration.
     * Colors of the bricks are assigned for rows 1-10 through an array containing the colors types for
     * each row.
     * no parameters are passed and does not return any value.
     */

    private void setUpBricks() {

        Color[] colors = {Color.RED, Color.RED, Color.ORANGE, Color.ORANGE,
                Color.YELLOW, Color.YELLOW, Color.GREEN, Color.GREEN, Color.CYAN, Color.CYAN};
        double y = BRICK_Y_OFFSET;
        /**two nested loops - counter i runs for the rows, j for the columns
         * */
        for (int i = 0; i < NBRICK_ROWS; i++) {
            /**here the value of x for the first brick is calculated by subtracting the value of
             half of the Brick widths in one row and half of the (separators - 1).
             even if the formula seems correct, visually the rows of bricks don't seem to be centered
             They would have appeared more centered if x was simply set at 1.
             **/

            double x = ((double) getWidth() / 2) - (double) (NBRICKS_PER_ROW * BRICK_WIDTH) / 2
                    - (double) ((NBRICKS_PER_ROW - 1) * BRICK_SEP) / 2;
            for (int j = 0; j < NBRICKS_PER_ROW; j++) {

                brick = new GRect(x, y, BRICK_WIDTH, BRICK_HEIGHT);
                brick.setFillColor(colors[i]);
                brick.setColor(colors[i]);
                brick.setFilled(true);
                add(brick);
                x += (BRICK_WIDTH + BRICK_SEP);

            }
            y += (BRICK_HEIGHT + BRICK_SEP);
        }

    }

    public void setUpPaddle() {
        paddle = new GRect((((double) getWidth() - PADDLE_WIDTH) / 2),
                (getHeight() - PADDLE_Y_OFFSET - PADDLE_HEIGHT), PADDLE_WIDTH, PADDLE_HEIGHT);
        paddle.setFillColor(Color.BLACK);
        paddle.setFilled(true);
        add(paddle);
        addMouseListeners();
    }

    /**
     * the mouse tracks the point at mid width of the paddle.
     * to control that the paddle does not move out of the application window,
     * I will limit the movement to getWidth() - half of the paddle width
     * and start from 0+ half paddle width
     */

    public void mouseMoved(MouseEvent e) {
        if (e.getX() < (getWidth() - (PADDLE_WIDTH / 2)) && (e.getX() > (PADDLE_WIDTH / 2))) {
            paddle.setLocation((e.getX() - ((double) PADDLE_WIDTH / 2)), (getHeight() - PADDLE_Y_OFFSET - PADDLE_HEIGHT));
        }


    }


    /**
     * following method creates a ball and puts it to the center of the game board
     */

    private void generateBall() {
        ball = new GOval(((double) getWidth() / 2) - BALL_RADIUS,
                ((double) getHeight() / 2) - BALL_RADIUS, BALL_RADIUS * 2, BALL_RADIUS * 2);
        ball.setColor(Color.BLACK);
        ball.setFilled(true);
        add(ball);


    }

    private void moveBall() {
        ball.move(vx, vy);
    }



    /**
     * in playGame, ball is generated
     * in the middle of the game board and starts with a random speed between 1-3
     */
    private void playGame() {
        generateBall();
        vx = rgen.nextDouble(1.0, 3.0); //initialize the speed of vx as random
        if (rgen.nextBoolean(0.5)) vx = -vx; // randomly set vx to positive or negative
        //temporarily, to build the algorithm, I will set the ball to move
        // and bounce off the walls constantly
        while (numberOfBricksLeft > 0) {
            // condition with (while ball does not hit the floor)
            moveBall();
            if (!checkForWallCollision()) break;//if the ball has past the floor, interrupts the while cycle
            collider = getCollidingObject();
            if (collider != null) {
                checkTypeOfObjectCollided(collider);
            }
            pause(25);
        }

    }




    /**
     * Method checkForWallCollision checks if the ball has hit one of the walls of game board.
     * Removes one turnsLeft if the ball has past the floor
     * RETURNS FALSE if the ball has past the floor.
     * RETURNS TRUE in any other case
     */

    private boolean checkForWallCollision() {

        /**first collision check:if TRUE, we just got past the bottom "floor" and the player loses one turn left*/
        if (ball.getY() > (getHeight() - BALL_RADIUS * 2)) {
            turnsLeft--;
            pause(2000);
            remove(ball);
            return false;
        }

        /**second collision check:if TRUE, we just got past the top "ceiling"*/
        else if (ball.getY() < 0) {
            vy = -vy; //invert velocity direction
            /** assume bounce will move ball an amount below the
             ceiling equal to the amount it would have gone
             beyond the ceiling.*/
            double diff = ball.getY();
            ball.move(0, -2 * diff);
        }

        /**third collision check:if TRUE, we just got past the "right wall"*/

        else if (ball.getX() > (getWidth() - BALL_RADIUS * 2)) {
            vx = -vx; //invert velocity direction
            /** assume bounce will move ball an amount
             * inside the wall equal to the amount it would have gone past the wall*/
            double diff = ball.getX() - (getWidth() - BALL_RADIUS * 2);
            ball.move(0, -2 * diff);
        }

        /**Fourth collision check: if true, we just got past the left wall*/
        else if (ball.getX() < 0) {
            vx = -vx; //invert velocity direction
            /** assume bounce will move ball an amount
             * inside the wall equal to the amount it would have gone past the wall*/
            double diff = ball.getX();
            ball.move(0, -2 * diff);
        }
        return true;
    }


    /**
     * Method checkCollidingObject checks if the ball has hit a brick or the paddle. Returns to
     * the caller a type GObject which contains info of the paddle/brick hit
     * if No Object is hit, returns null
     */
    private GObject getCollidingObject() {
        if (getElementAt(ball.getX(), ball.getY()) != null) {
            return getElementAt(ball.getX(), ball.getY());
        }
        if (getElementAt(ball.getX(), ball.getY() + (2 * BALL_RADIUS)) != null) {
            return getElementAt(ball.getX(), ball.getY() + (2 * BALL_RADIUS));
        }
        if (getElementAt(ball.getX() + (2 * BALL_RADIUS), vy) != null) {
            return getElementAt(ball.getX() + (2 * BALL_RADIUS), ball.getY());
        }
        if (getElementAt(ball.getX() + (2 * BALL_RADIUS), ball.getY() + (2 * BALL_RADIUS)) != null) {
            return getElementAt(ball.getX() + (2 * BALL_RADIUS), ball.getY() + (2 * BALL_RADIUS));
        }
        return null;
    }

    /**
     * Method checkTypeOfObjectCollided checks what kind of object has been collided.
     * If it is the paddle, it bounces, if a brick, it removes it and bounces
     */

    private void checkTypeOfObjectCollided(GObject objectCollided) {
        if (objectCollided == paddle) {

            vy = -vy; //invert velocity direction
            /** assume bounce will move ball an amount above the
             paddle equal to the amount it would have dropped
             through the paddle.*/
            double diff = ball.getY() - (getHeight() - PADDLE_Y_OFFSET - PADDLE_HEIGHT - BALL_RADIUS * 2);
            ball.move(0, -2 * diff);

        } else {

            remove(objectCollided);
            vy = -vy; //invert velocity direction
            numberOfBricksLeft--;
        }

    }


}