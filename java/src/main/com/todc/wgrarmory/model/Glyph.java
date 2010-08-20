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
 * @author <a href="tim@timodonnell.com">Tim O'Donnell</a>
 */
public class Glyph {


    public static final int MAJOR = 1;
    public static final int MINOR = 2;


    // ----------------------------------------------------- Instance Variables


    private int m_id;
    private String m_name;
    private String m_effect;
    private int m_type;


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

    public String getEffect() {
        return m_effect;
    }

    public void setEffect(String effect) {
        m_effect = effect;
    }

    public int getType() {
        return m_type;
    }

    public void setType(int type) {
        m_type = type;
    }


    // --------------------------------------------------------- Public Methods


    public String toString() {
        StringBuffer sb = new StringBuffer("[");
        sb.append("id = " + m_id + "; ");
        sb.append("name = " + m_name + "; ");
        sb.append("effect = " + m_effect + "; ");
        sb.append("type = " + m_type);
        sb.append("]");

        return sb.toString();
    }

}
