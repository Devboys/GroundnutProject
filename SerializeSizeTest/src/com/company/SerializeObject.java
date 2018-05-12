package com.company;

import java.io.Serializable;


public class SerializeObject implements Serializable {

    private int[][] pC = new int[4][2];

    public SerializeObject(){
        for (int i = 0; i <pC.length ; i++) {
            pC[i][0] = 1;
            pC[i][1] = 2;
        }
    }
}
