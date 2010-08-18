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
public class PlayerCharacter {


    // -------------------------------------------------------------- Constants


    public static final int ALLIANCE = 0;
    public static final int HORDE = 1;

    public static final int MALE = 0;
    public static final int FEMALE = 1;

    public static final int BLOOD_ELF = 10;
    public static final int ORC = 2;
    public static final int TAUREN = 6;
    public static final int TROLL = 8;
    public static final int UNDEAD = 5;
    public static final int DRAENEI = 0;
    public static final int DWARF = 0;
    public static final int GNOME = 0;
    public static final int HUMAN = 0;
    public static final int NIGHT_ELF = 0;

    public static final int DEATH_KNIGHT = 6;
    public static final int DRUID = 11;
    public static final int HUNTER = 3;
    public static final int MAGE = 8;
    public static final int PALADIN = 2;
    public static final int PRIEST = 5;
    public static final int ROGUE = 4;
    public static final int SHAMAN = 7;
    public static final int WARLOCK = 9;
    public static final int WARRIOR = 1;


    // ----------------------------------------------------- Instance Variables


    private String[] m_factionNames = new String[] {"Alliance", "Horde"};
    private String[] m_genderNames = new String[] {"Male", "Female"};
    private String[] m_raceNames = new String[] {"", "1", "Orc", "3", "4", "Undead", "Tauren", "7", "Troll", "9", "Blood Elf"};
    private String[] m_classNames = new String[] {"", "Warrior", "Paladin", "Hunter", "Rogue", "Priest", "Death Knight", "Shaman", "Mage", "Warlock", "", "Druid"};

    private String m_name;
    private int m_playerClass;
    private int m_gender;
    private int m_faction;
    private int m_race;


    // ------------------------------------------------------ Getters / Setters


    public String getName() {
        return m_name;
    }

    public void setName(String name) {
        m_name = name;
    }

    public int getRace() {
        return m_race;
    }

    public void setRace(int race) {
        m_race = race;
    }

    public int getGender() {
        return m_gender;
    }

    public void setGender(int gender) {
        m_gender = gender;
    }

    public int getPlayerClass() {
        return m_playerClass;
    }

    public void setPlayerClass(int playerClass) {
        m_playerClass = playerClass;
    }

    public int getFaction() {
        return m_faction;
    }

    public void setFaction(int faction) {
        m_faction = faction;
    }


    // --------------------------------------------------------- Public Methods


    public String getGenderName(int gender) {
        return m_genderNames[gender];
    }


    public String getFactionName(int faction) {
        return m_factionNames[faction];
    }


    public String getRaceName(int race) {
        return m_raceNames[race];
    }


    public String getClassName(int classId) {
        return m_classNames[classId];
    }


    public String toString() {
        return "[" +
               "name = " + m_name + ", " +
               "faction = " + m_faction + ", " +
               "gender = " + m_gender + ", " +
               "race = " + m_race + ", " +
               "class = " + m_playerClass +
               "]";

    }
}
