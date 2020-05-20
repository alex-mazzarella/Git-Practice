package com.company;

import acm.graphics.GCompound;
import acm.graphics.GOval;
import acm.graphics.GPolygon;
import acm.graphics.GRect;

import java.awt.*;

public class GHalloweenPumpkin extends GCompound {

    //builds a funny halloween pumpkin face. NOTE: the height does not include the top stem

    public GHalloweenPumpkin(double headHeight) {
        //
        GOval head = new GOval(headHeight, headHeight); //creating head
        head.setFilled(true);
        head.setColor(Color.ORANGE);
        GRect stem = new GRect(STEM_WIDTH * headHeight, STEM_HEIGHT * headHeight);
        //creating stem
        stem.setFilled(true);

        GPolygon leftEye = createEye(headHeight); //creating Eyes
        GPolygon rightEye = createEye(headHeight);
        leftEye.setFilled(true);
        rightEye.setFilled((true));

        GPolygon nose = createNose(headHeight); //creating nose
        nose.setFilled(true);

        //GPolygon mouth = createMouth(headHeight); // creating mouth


        add(stem, headHeight / 2 - (STEM_WIDTH * headHeight) / 2,
                -0.9 * (STEM_HEIGHT * headHeight));
        add(head);
        add(leftEye, headHeight * EYES_OFFSET, headHeight * EYES_OFFSET);
        add(rightEye, headHeight * (1 - EYES_OFFSET), headHeight * EYES_OFFSET);
        add(nose, headHeight / 2, headHeight / 2);


        GHalloweenMouth mouth = new GHalloweenMouth(headHeight);
        add(mouth, (headHeight - MOUTH_WIDTH * headHeight) / 2, headHeight * 0.8);


    }


    private GPolygon createEye(double headHeight) {
        GPolygon poly = new GPolygon();
        poly.addVertex(-(EYE_WIDTH * headHeight) / 2, -(EYE_HEIGHT * headHeight) / 2);
        poly.addVertex((EYE_WIDTH * headHeight) / 2, -(EYE_HEIGHT * headHeight) / 2);
        poly.addVertex(0, (EYE_HEIGHT * headHeight) / 2);
        return poly;
    }

    private GPolygon createNose(double headHeight) {
        GPolygon poly = new GPolygon();
        poly.addVertex(-(NOSE_WIDTH * headHeight) / 2, (NOSE_HEIGHT * headHeight) / 2);
        poly.addVertex(0, -(NOSE_HEIGHT * headHeight) / 2);
        poly.addVertex((NOSE_WIDTH * headHeight) / 2, (NOSE_HEIGHT * headHeight) / 2);
        return poly;
    }

/*
    private GPolygon createMouth(double headHeight) {
        GPolygon poly = new GPolygon();
        double width =MOUTH_WIDTH*headHeight;
        double height = MOUTH_HEIGHT*headHeight;
        poly.addVertex(-width/2,-height/4);
        poly.addVertex(-width*0.4,-height/2);
        poly.addVertex(-width*0.3,-height/4);
        poly.addVertex(-width*0.2,-height/2);
        poly.addVertex(-width*0.1,-height/4);
        poly.addVertex(0,-height/2);
        poly.addVertex(width*0.1,-height/4);
        poly.addVertex(width*0.2,-height/2);
        poly.addVertex(width*0.3, -height/4);
        poly.addVertex(width*0.4,-height/2);
        poly.addVertex(width/2,-height/4);//11
        poly.addVertex(width*0.4,height/4);//12

        poly.addVertex(width*0.3,height/2);//13
        poly.addVertex(width*0.2,height/4);//14
        poly.addVertex(width*0, height/2);//15
        poly.addVertex(-width*0.1,height/4);//16
        poly.addVertex(-width*0.2,height/2);
        poly.addVertex(-width*0.3,height/4);
        poly.addVertex(-width*0.4,height/2);
        return poly;

    } */

    private final double STEM_HEIGHT = 0.08;
    private final double STEM_WIDTH = STEM_HEIGHT / 2;
    private final double EYE_HEIGHT = 0.15;
    private final double EYE_WIDTH = 0.15;
    private final double EYES_OFFSET = 0.3;
    private final double NOSE_HEIGHT = 0.15;
    private final double NOSE_WIDTH = 0.15;
    public final double MOUTH_WIDTH = 0.4;


}
