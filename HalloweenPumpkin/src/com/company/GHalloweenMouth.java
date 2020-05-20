package com.company;

import acm.graphics.GCompound;
import acm.graphics.GPolygon;

/**
 * this class returns a GCompound with a creepy mouth for a Halloween pumpkin.
 * receives as a parameter the height of the pumpkin of type double
 */

public class GHalloweenMouth extends GCompound {

    // I will create the mouth as a compound of diamonds

    public GHalloweenMouth(double headHeight) {

        double toothY = 0;
        int numberOfUpperTeeth = (NUMBER_OF_TEETH / 2) + 1;
        double toothWidth = headHeight * MOUTH_WIDTH / numberOfUpperTeeth; //the number 4 is specific for this mouth, with 7 teeth
        double toothX = toothWidth / 2; //necessary offseting the x of the first tooth by half tooth width to center the mouth
        for (int i = 0; i < NUMBER_OF_TEETH; i++) {
            GPolygon tooth = createTooth(headHeight, toothWidth);
            tooth.setFilled(true);
            add(tooth, toothX, toothY);
            toothX += toothWidth / 2;
            if (toothY == 0) {
                toothY += toothWidth / 2;
            } else toothY -= toothWidth / 2;
        }
    }


    private GPolygon createTooth(double headHeight, double toothWidth) {

        GPolygon poly = new GPolygon();
        poly.addVertex(-toothWidth / 2, 0);
        poly.addVertex(0, toothWidth / 2);
        poly.addVertex(toothWidth / 2, 0);
        poly.addVertex(0, -toothWidth / 2);
        return (poly);
    }


    public final double MOUTH_WIDTH = 0.4;

    public final int NUMBER_OF_TEETH = 9; //MUST BE an ODD number
}
