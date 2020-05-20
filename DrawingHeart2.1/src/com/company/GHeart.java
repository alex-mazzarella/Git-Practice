package com.company;

import acm.graphics.GArc;
import acm.graphics.GCompound;
import acm.graphics.GPolygon;

import java.awt.*;

import static java.lang.Math.sqrt;

public class GHeart extends GCompound {

    public GHeart (double heartHeight, Color heartColor){
        double squareHalfHeight = ((heartHeight * sqrt(2)) / (sqrt(2) + 2));
        double radius = (squareHalfHeight * sqrt(2)) / 2;
        double dx = radius/sqrt(2);//dx measures the distance between the center of the arc
        // and the imaginary line that connects the two upper vertex of the GLines
        bottomOfHeart = createBottomOfHeart(squareHalfHeight);
        arcLeft = new GArc(radius*2,radius*2,45.0,180.0);
        arcRight = new GArc(radius*2,radius*2,-45.0,180.0);

        bottomOfHeart.setFilled(true);
        arcLeft.setFilled(true);
        arcRight.setFilled(true);
        bottomOfHeart.setColor(heartColor);
        arcLeft.setColor(heartColor);
        arcRight.setColor(heartColor);
        add(bottomOfHeart,0,0);
        add(arcLeft,-dx-radius,-dx-radius);
        add(arcRight,+dx-radius,-dx-radius);

    }


    private GPolygon createBottomOfHeart(double squareHalfHeight) {
        GPolygon poly = new GPolygon();
        poly.addVertex(-squareHalfHeight,0);
        poly.addVertex(0,squareHalfHeight);
        poly.addVertex(squareHalfHeight,0);
        poly.addVertex(0,-squareHalfHeight);
        return poly;
    }


    GPolygon bottomOfHeart;
    GArc arcLeft,arcRight;
}
