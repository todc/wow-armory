/*
 * $Id$
 *
 * Copyright (c)2010, Tim O'Donnell. All Rights Reserved.
 *
 * This code may not be used or reproduced in part or in whole without
 * express written permission of Tim O'Donnell.
 */
package com.todc.wgrarmory.model;


/**
 * @author <a href="mailto:tim@timodonnell.com">Tim O'Donnell</a>
 */
public class Profession {


    // -------------------------------------------------------------- Constants


    public static final int ENCHANTING = 333;
    public static final int JEWELCRAFTING = 755;

    public static final int COOKING = 185;
    public static final int FIRST_AID = 129;
    public static final int FISHING = 356;
    public static final int RIDING = 762;


    // ----------------------------------------------------- Instance Variables


    private int m_id;
    private String m_name;
    private int m_value;
    private int m_max;


    // ------------------------------------------------------ Getters / Setters


    public int getId() {
        return m_id;
    }

    public void setId(int id) {
        m_id = id;
    }

    public String getName() {
        return m_name;
    }

    public void setName(String name) {
        m_name = name;
    }

    public int getValue() {
        return m_value;
    }

    public void setValue(int value) {
        m_value = value;
    }

    public int getMax() {
        return m_max;
    }

    public void setMax(int max) {
        m_max = max;
    }


    // --------------------------------------------------------- Public Methods


    public String toString() {
        return "[id = " + m_id + "; name = " + m_name + "; value = " + m_value + "; max = " + m_max + "]";
    }
    

}
