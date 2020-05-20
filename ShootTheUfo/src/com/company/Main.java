/**
 * in this basic game, the user will try to
 * shoot and hit a UFO with a set cannon,
 * before the UFO lands
 */

package com.company;

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class Main extends GraphicsProgram {

    /** Size and speed of UFO */
    private static final int UFO_WIDTH = 40;
    private static final int UFO_HEIGHT = UFO_WIDTH / 2;
    private static final int UFO_SPEED = 5;
    /** Size and speed of bullets */
    private static final int BULLET_SPEED = 10;
    private static final int BULLET_DIAM = 5;
    /** Animation cycle delay */
    private static final int DELAY = 10;

    private static final GLabel welcomeMessageLabel = new GLabel("Welcome! destroy the UFO and save your planet!!!");
    private static final GLabel instruction = new GLabel("Click on the screen to shoot the UFO!");
    private static final GLabel loserMessageLabel = new GLabel("YOU LOSE! UFO HAS LANDED!");
    private static final GLabel winnerMessageLabel = new GLabel("YOU WIN! UFO HAS BEEN SHOT!");


    /** private instance variables */
    private GRect ufo;
    private GOval bullet;
    private boolean ufoToLeft; // when true, UFO is moving to left
    RandomGenerator rGen = new RandomGenerator();

    public static void main(String[] args) {

        (new com.company.Main()).start();
    }

    public void run() {
        welcomeMessage();
        setup();
        while (!gameOver()) {
            moveUFO();
            moveBullet();
            checkForBulletCollisions();
            pause(DELAY);
        }
        if (ufo == null) {//if Game is over because UFO has been shot, winner message
            winnerMessageLabel.setFont("Arial Black-20");
            add(winnerMessageLabel, (getWidth() - winnerMessageLabel.getWidth()) / 2,
                    (getHeight() - winnerMessageLabel.getAscent()) / 2);
            pause(3000);
            remove(winnerMessageLabel);
        } else {
            loserMessageLabel.setFont("Arial Black-20");//else, loser message
            add(loserMessageLabel, (getWidth() - loserMessageLabel.getWidth()) / 2,
                    (getHeight() - loserMessageLabel.getAscent()) / 2);
            pause(3000);
            remove(loserMessageLabel);

        }

    }


    /**message to welcome the user*/
    private void welcomeMessage() {
        welcomeMessageLabel.setFont("Arial Black-20");
        add(welcomeMessageLabel, (getWidth() - welcomeMessageLabel.getWidth()) / 2,
                (getHeight() - welcomeMessageLabel.getAscent()) / 2);
        pause(3500);
        remove(welcomeMessageLabel);
        howToPlayInstruction();
        countdown3toStart();
    }


    private void howToPlayInstruction() {

        instruction.setFont("Arial Black-20");
        add(instruction, (getWidth() - instruction.getWidth()) / 2,
                (getHeight() - instruction.getAscent()) / 2);
        pause(3500);
        remove(instruction);

    }

    /**countdown on screen before starting game*/
    private void countdown3toStart() {

        GLabel counter = new GLabel("3");
        counter.setFont("Arial Black-34");
        add(counter, (getWidth() - counter.getWidth()) / 2,
                (getHeight() - counter.getAscent()) / 2);
        pause(1000);
        counter.setLabel("2");
        add(counter, (getWidth() - counter.getWidth()) / 2,
                (getHeight() - counter.getAscent()) / 2);
        pause(1000);
        counter.setLabel("1");
        add(counter, (getWidth() - counter.getWidth()) / 2,
                (getHeight() - counter.getAscent()) / 2);
        pause(1000);
        counter.setLabel("START!!!");
        add(counter, (getWidth() - counter.getWidth()) / 2,
                (getHeight() - counter.getAscent()) / 2);
        pause(500);
        remove(counter);
    }

    /** setup UFO at top right and add mouse listeners */
    private void setup() {
        ufo = new GRect(UFO_WIDTH, UFO_HEIGHT);
        ufo.setColor(rGen.nextColor());
        ufo.setFilled(true);
        add(ufo, getWidth(), 0);
        ufoToLeft = true;
        addMouseListeners();
    }


    /** determines if game is over -- true if either
     * the UFO is destroyed or if the UFO lands */
    private boolean gameOver() {
        return (ufo == null) ||
                (ufo.getY() >= getHeight() - UFO_HEIGHT);
    }


    /** when mouse is clicked create bullet, unless a bullet
     * already exists.
     */
    public void mouseClicked(MouseEvent e) {
        if (bullet == null) {
            bullet = new GOval(BULLET_DIAM, BULLET_DIAM);
            bullet.setFilled(true);
            bullet.setColor(Color.RED);
            add(bullet, (getWidth() - BULLET_DIAM) / 2,
                    getHeight() - BULLET_DIAM);
        }
    }


    private void moveUFO() {
        if (ufoToLeft) {
            ufo.move(-UFO_SPEED, 0);
            if (ufo.getX() <= 0) {  // if true, ufo has reached the left edge of the canvas
                ufoToLeft = false;
                ufo.move(-ufo.getX(), UFO_HEIGHT);
            }
        } else {
            ufo.move(UFO_SPEED, 0);
            if ((ufo.getX() + UFO_WIDTH) >= getWidth()) {  // if true, ufo has reached the right edge of the canvas
                ufoToLeft = true;
                ufo.move(0, UFO_HEIGHT);
            }
        }


    }


    /** moves bullet */
    private void moveBullet() {
        if (bullet != null) {
            bullet.move(0, -BULLET_SPEED);
        }
    }


    /** checks for bullet interaction with the world
     * (either colliding with the UFO or moving offscreen)
     */
    private void checkForBulletCollisions() {
        bulletCollideWithUFO();
        bulletMovesOutOfScreen();
    }

    /** checks to see if UFO and bullet collide, if so
     * bullet and UFO are removed and both variables are
     * set to null.
     */

    private void bulletCollideWithUFO() {
        if (bullet != null) {
            GObject collidingObject = getElementAt(bullet.getX(), bullet.getY());
            if (collidingObject == ufo) {
                pause(1000);
                remove(ufo);
                ufo = null;
                remove(bullet);
                bullet = null;
            }
        }
    }

    /** determines if bullet has moved of screen,
     * if it has, removes bullet, sets variable to null
     */

    private void bulletMovesOutOfScreen() {
        if (bullet != null) {
            if (bullet.getY() <= -BULLET_DIAM) {
                remove(bullet);
                bullet = null;
            }
        }
    }


}
