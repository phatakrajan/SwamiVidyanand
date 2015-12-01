package com.swami.vidyanand.data;

/**
 * Created by Rajan_Phatak on 11/8/2015.
 */
public enum Group {

    PARAMPARA ("PARAMPARA",0),
    SADGURU ("SADGURU",1),
    SUVICHAR ("SUVICHAR",2),
    PRABODHAN ("PRABODHAN",3),
    ASHRAM ("ASHRAM",4),
    SADHANA ("SADHANA",5);

    private String stringValue;
    private int intValue;
    private Group(String toString, int value) {
        stringValue = toString;
        intValue = value;
    }

    @Override
    public String toString() {
        return stringValue;
    }
}
