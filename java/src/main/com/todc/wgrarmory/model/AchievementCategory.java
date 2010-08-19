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
 * @author <a href="mailto:odonnellt@gmail.com">Tim O'Donnell</a>
 */
public class AchievementCategory {


    // ----------------------------------------------------- Instance Variables


    private int m_id;
    private String m_raidZoneName;
    private int m_raidSize;
    private List<Achievement> m_achievements = new ArrayList<Achievement>();


    // ------------------------------------------------------ Getters / Setters


    public int getId() {
        return m_id;
    }

    public void setId(int id) {
        m_id = id;
    }

    public String getRaidZoneName() {
        return m_raidZoneName;
    }

    public void setRaidZoneName(String raidZoneName) {
        m_raidZoneName = raidZoneName;
    }

    public int getRaidSize() {
        return m_raidSize;
    }

    public void setRaidSize(int raidSize) {
        m_raidSize = raidSize;
    }

    public List<Achievement> getAchievements() {
        return m_achievements;
    }

    public void setAchievements(List<Achievement> achievements) {
        m_achievements = achievements;
    }


    // --------------------------------------------------------- Public Methods


    public String toString() {
        return null;
    }
}
