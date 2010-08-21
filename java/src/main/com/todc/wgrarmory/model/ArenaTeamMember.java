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
 * @author Tim O'Donnell
 */
public class ArenaTeamMember {


    // ----------------------------------------------------- Instance Variables


    private String m_battlegroup;
    private String regionCode;
    private String m_realm;
    private String m_guildName;
    private long m_guildId;
    private String m_name;
    private int m_classId;
    private int m_genderId;
    private int m_raceId;
    private int m_contribution;
    private int m_gamesPlayed;
    private int m_gamesWon;
    private int m_seasonGamesPlayed;
    private int m_seasonGamesWon;
    private int m_teamRank;


    // ------------------------------------------------------ Getters / Setters

    
    public String getBattlegroup() {
        return m_battlegroup;
    }

    public void setBattlegroup(String battlegroup) {
        m_battlegroup = battlegroup;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getRealm() {
        return m_realm;
    }

    public void setRealm(String realm) {
        m_realm = realm;
    }

    public String getGuildName() {
        return m_guildName;
    }

    public void setGuildName(String guildName) {
        m_guildName = guildName;
    }

    public long getGuildId() {
        return m_guildId;
    }

    public void setGuildId(long guildId) {
        m_guildId = guildId;
    }

    public String getName() {
        return m_name;
    }

    public void setName(String name) {
        m_name = name;
    }

    public int getClassId() {
        return m_classId;
    }

    public void setClassId(int classId) {
        m_classId = classId;
    }

    public int getGenderId() {
        return m_genderId;
    }

    public void setGenderId(int genderId) {
        m_genderId = genderId;
    }

    public int getRaceId() {
        return m_raceId;
    }

    public void setRaceId(int raceId) {
        m_raceId = raceId;
    }

    public int getContribution() {
        return m_contribution;
    }

    public void setContribution(int contribution) {
        m_contribution = contribution;
    }

    public int getGamesPlayed() {
        return m_gamesPlayed;
    }

    public void setGamesPlayed(int gamesPlayed) {
        m_gamesPlayed = gamesPlayed;
    }

    public int getGamesWon() {
        return m_gamesWon;
    }

    public void setGamesWon(int gamesWon) {
        m_gamesWon = gamesWon;
    }

    public int getSeasonGamesPlayed() {
        return m_seasonGamesPlayed;
    }

    public void setSeasonGamesPlayed(int seasonGamesPlayed) {
        m_seasonGamesPlayed = seasonGamesPlayed;
    }

    public int getSeasonGamesWon() {
        return m_seasonGamesWon;
    }

    public void setSeasonGamesWon(int seasonGamesWon) {
        m_seasonGamesWon = seasonGamesWon;
    }

    public int getTeamRank() {
        return m_teamRank;
    }

    public void setTeamRank(int teamRank) {
        m_teamRank = teamRank;
    }


    // --------------------------------------------------------- Public Methods


    public int getSeasonGamesLost() {
        return m_seasonGamesPlayed - m_seasonGamesWon;
    }

}
