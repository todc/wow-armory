/*
 * $Id$
 *
 * Copyright (c)2010, Tim O'Donnell. All Rights Reserved.
 *
 * This code may not be used or reproduced in part or in whole without
 * express written permission of Tim O'Donnell.
 */
package com.todc.wgrarmory.model;


import java.util.HashMap;
import java.util.Map;


/**
 * @author <a href="mailto:odonnellt@gmail.com">Tim O'Donnell</a>
 */
public class ItemFilter {


    // -------------------------------------------------------------- Constants


    public static final String SOURCE_DUNGEON = "dungeon";
    public static final String SOURCE_REPUTATION = "reputation";

    public static final String RARITY_POOR = "pr";
    public static final String RARITY_UNCOMMON = "un";
    public static final String RARITY_RARE = "re";
    public static final String RARITY_EPIC = "ec";
    public static final String RARITY_LEGENDARY = "lg";
    public static final String RARITY_HEIRLOOM = "hm";


    // ----------------------------------------------------- Instance Variables


    private String m_rarity;
    private int m_minReqLevel;
    private int m_maxReqLevel;
    private String m_source;
    private String m_itemType;
    private String m_dungeonDifficulty;
    private String m_dungeon;

    private Map<String,String> m_otherCriteria = new HashMap<String,String>();


    // ------------------------------------------------------ Getters / Setters


    public String getRarity() {
        return m_rarity;
    }

    public void setRarity(String rarity) {
        m_rarity = rarity;
    }

    public int getMinReqLevel() {
        return m_minReqLevel;
    }

    public void setMinReqLevel(int minReqLevel) {
        m_minReqLevel = minReqLevel;
    }

    public int getMaxReqLevel() {
        return m_maxReqLevel;
    }

    public void setMaxReqLevel(int maxReqLevel) {
        m_maxReqLevel = maxReqLevel;
    }

    public String getSource() {
        return m_source;
    }

    public void setSource(String source) {
        m_source = source;
    }

    public String getItemType() {
        return m_itemType;
    }

    public void setItemType(String itemType) {
        m_itemType = itemType;
    }

    public String getDungeonDifficulty() {
        return m_dungeonDifficulty;
    }

    public void setDungeonDifficulty(String dungeonDifficulty) {
        m_dungeonDifficulty = dungeonDifficulty;
    }

    public String getDungeon() {
        return m_dungeon;
    }

    public void setDungeon(String dungeon) {
        m_dungeon = dungeon;
    }
    

    public Map<String, String> getOtherCriteria() {
        return m_otherCriteria;
    }

    public void setOtherCriteria(Map<String, String> otherCriteria) {
        m_otherCriteria = otherCriteria;
    }


    // --------------------------------------------------------- Public Methods


    public void addOtherCriteria(String field, String value) {
        m_otherCriteria.put(field, value);
    }
}
