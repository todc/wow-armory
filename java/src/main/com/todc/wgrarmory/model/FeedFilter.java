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
import java.util.Arrays;
import java.util.List;


/**
 * Models the criteria with which to filter a character's activity feed.
 *
 * @author <a href="mailto:tim@timodonnell.com">Tim O'Donnell</a>
 */
public class FeedFilter {


    // -------------------------------------------------------------- Constants


    public static final int ACH_GENERAL = 92;
    public static final int ACH_EXPLORATION = 97;
    public static final int ACH_DUNGEONS = 168;
    public static final int ACH_REPUTATION = 201;
    public static final int ACH_FEATS = 81;
    public static final int ACH_QUESTS = 96;
    public static final int ACH_PVP = 95;
    public static final int ACH_PROFESSIONS = 169;
    public static final int ACH_WORLD_EVENTS = 155;

    public static final String UNCOMMON  = "UNCOMMON";
    public static final String RARE      = "RARE";
    public static final String EPIC      = "EPIC";
    public static final String LEGENDARY = "LEGENDARY";
    public static final String HEIRLOOM  = "HEIRLOOM";

    public static final String ACHIEVEMENT = "ACHIEVEMENT";
    public static final String BOSSKILL    = "BOSSKILL";
    public static final String LOOT        = "LOOT";
    public static final String RESPEC      = "RESPEC";
    public static final String CRITERIA    = "CRITERIA";


    // ----------------------------------------------------- Instance Variables


    private List<String> m_filters = new ArrayList<String>();
    private List<Integer> m_achCategories = new ArrayList<Integer>();
    private String m_itemQuality;
    private int m_itemLevel = 0;


    // ------------------------------------------------------ Getters / Setters


    public String[] getFilters() {
        return m_filters.toArray(new String[m_filters.size()]);
    }

    public void setFilters(String[] filters) {
        m_filters.clear();
        m_filters.addAll(Arrays.asList(filters));
    }

    public Integer[] getAchCategories() {
        return m_achCategories.toArray(new Integer[m_achCategories.size()]);
    }

    public void setAchCategories(Integer[] achCategories) {
        m_achCategories.clear();
        m_achCategories.addAll(Arrays.asList(achCategories));
    }

    public String getItemQuality() {
        return m_itemQuality;
    }

    public void setItemQuality(String itemQuality) {
        m_itemQuality = itemQuality;
    }

    public int getItemLevel() {
        return m_itemLevel;
    }

    public void setItemLevel(int itemLevel) {
        m_itemLevel = itemLevel;
    }

}
