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
public class Guild {


    // ----------------------------------------------------- Instance Variables


    private String m_name;
    private String m_realmName;
    private String m_regionCode;
    private String m_battlegroup;
    private int m_faction;

    private List<PlayerCharacter> m_roster = new ArrayList<PlayerCharacter>();


    // ------------------------------------------------------ Getters / Setters


    public String getName() {
        return m_name;
    }

    public void setName(String name) {
        m_name = name;
    }

    public String getRealmName() {
        return m_realmName;
    }

    public void setRealmName(String realmName) {
        m_realmName = realmName;
    }

    public String getRegionCode() {
        return m_regionCode;
    }

    public void setRegionCode(String regionCode) {
        m_regionCode = regionCode;
    }

    public String getBattlegroup() {
        return m_battlegroup;
    }

    public void setBattlegroup(String battlegroup) {
        m_battlegroup = battlegroup;
    }

    public int getFaction() {
        return m_faction;
    }

    public void setFaction(int faction) {
        m_faction = faction;
    }

    public List<PlayerCharacter> getRoster() {
        return m_roster;
    }

    public void setRoster(List<PlayerCharacter> roster) {
        m_roster = roster;
    }


    // --------------------------------------------------------- Public Methods


    public void addCharacter(PlayerCharacter pc) {
        m_roster.add(pc);
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("[");
        sb.append("name = " + m_name + "; ");
        sb.append("realm = " + m_realmName + "; ");
        sb.append("region = " + m_regionCode + "; ");
        sb.append("battlegroup = " + m_battlegroup + "; ");
        sb.append("faction = " + m_faction + "; ");
        sb.append("characters = [");

        for (PlayerCharacter pc : m_roster) {
            sb.append(pc + ", ");
        }
        sb.append("]");

        return sb.toString();
    }
}