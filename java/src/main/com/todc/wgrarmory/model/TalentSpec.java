/*
 * $Id$
 *
 * Copyright (c)2010, Tim O'Donnell. All Rights Reserved.
 *
 * This code may not be used or reproduced in part or in whole without
 * express written permission of Tim O'Donnell.
 */
package com.todc.wgrarmory.model;


import java.util.ArrayList;
import java.util.List;


/**
 * @author <a href="mailto:tim@timodonnell.com">Tim O'Donnell</a>
 */
public class TalentSpec {


    // ----------------------------------------------------- Instance Variables


    private int m_number;
    private String m_name;
    private int m_treeOne;
    private int m_treeTwo;
    private int m_treeThree;
    private String m_build;
    private boolean m_isActive;

    private List<Glyph> m_glyphs = new ArrayList<Glyph>();


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

    public String getBuild() {
        return m_build;
    }

    public void setBuild(String build) {
        m_build = build;
    }

    public List<Glyph> getGlyphs() {
        return m_glyphs;
    }

    public void setGlyphs(List<Glyph> glyphs) {
        m_glyphs = glyphs;
    }


    // --------------------------------------------------------- Public Methods


    /**
     * Returns the talent spec's build order in t1/t2/t3 format.
     *
     * @return build order in short form
     */
    public String getShortBuild() {
        return m_treeOne + "/" + m_treeTwo + "/" + m_treeThree;
    }


    public void addGlyph(Glyph glyph) {
        m_glyphs.add(glyph);
    }


    public String toString() {
        StringBuffer sb = new StringBuffer("[");
        sb.append("name = " + m_name + "; ");
        sb.append("num = " + m_number + "; ");
        sb.append("active = " + m_isActive + "; ");
        sb.append("treeOne = " + m_treeOne + "; ");
        sb.append("treeTwo = " + m_treeTwo + "; ");
        sb.append("treeThree = " + m_treeThree + "; ");
        sb.append("build = " + m_build + "; ");
        sb.append("glyphs = ");

        for (Glyph g : m_glyphs) {
            sb.append(g + ", ");
        }
        sb.append("]");

        return sb.toString();
    }
}
