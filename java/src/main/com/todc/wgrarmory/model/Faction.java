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
public class Faction {

    // ----------------------------------------------------- Instance Variables


    private int m_id;
    private String m_name;
    private int m_reputation;


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

    public int getReputation() {
        return m_reputation;
    }

    public void setReputation(int reputation) {
        m_reputation = reputation;
    }


    // --------------------------------------------------------- Public Methods


    public String toString() {
        StringBuffer sb = new StringBuffer("[");
        sb.append("id = " + m_id + "; ");
        sb.append("name = " + m_name + "; ");
        sb.append("reputation = " + m_reputation);
        sb.append("]");

        return sb.toString();
    }

}
