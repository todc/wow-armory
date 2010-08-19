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
 * @author <a href="mailto:odonnellt@gmail.com">Tim O'Donnell</a>
 */
public class TalentSpec {


    // ----------------------------------------------------- Instance Variables


    private String m_name;
    private int m_treeOne;
    private int m_treeTwo;
    private int m_treeThree;
    private boolean m_isActive;
    private int m_number;


    // ------------------------------------------------------ Getters / Setters


    public String getName() {
        return m_name;
    }

    public void setName(String name) {
        m_name = name;
    }

    public int getTreeOne() {
        return m_treeOne;
    }

    public void setTreeOne(int treeOne) {
        m_treeOne = treeOne;
    }

    public int getTreeTwo() {
        return m_treeTwo;
    }

    public void setTreeTwo(int treeTwo) {
        m_treeTwo = treeTwo;
    }

    public int getTreeThree() {
        return m_treeThree;
    }

    public void setTreeThree(int treeThree) {
        m_treeThree = treeThree;
    }

    public boolean isActive() {
        return m_isActive;
    }

    public void setActive(boolean active) {
        m_isActive = active;
    }

    public int getNumber() {
        return m_number;
    }

    public void setNumber(int number) {
        m_number = number;
    }


    // --------------------------------------------------------- Public Methods


    public String getBuild() {
        return m_treeOne + "/" + m_treeTwo + "/" + m_treeThree;
    }


    public String toString() {
        return "[name = " + m_name + "; treeOne = " + m_treeOne + "; treeTwo = " + m_treeTwo + "; treeThree = " + m_treeThree + "; active = " + m_isActive + "; num = " + m_number + "]";
    }
}
