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
 * Models the high-level details of an arena team, as returned from
 * {@link com.todc.wgrarmory.Armory#fetchArenaLadder}.
 *
 * @author <a href="mailto:tim@timodonnell.com">Tim O'Donnell</a>
 */
public class ArenaTeam {


    // ----------------------------------------------------- Instance Variables


    private String m_regionCode;
    private int m_rank;
    private String m_name;
    private String m_battlegroup;
    private String m_realm;
    private long m_created;
    private int m_faction;
    private int m_gamesPlayed;
    private int m_gamesWon;
    private int m_lastSeasonRank;
    private int m_rating;
    private int m_seasonGamesPlayed;
    private int m_seasonGamesWon;
    private int m_size;
    private int m_teamSize;


    // ------------------------------------------------------ Getters / Setters


    public String getRegionCode() {
        return m_regionCode;
    }

    public void setRegionCode(String regionCode) {
        m_regionCode = regionCode;
    }

    public int getRank() {
        return m_rank;
    }

    public void setRank(int rank) {
        m_rank = rank;
    }

    public String getName() {
        return m_name;
    }

    public void setName(String name) {
        m_name = name;
    }

    public String getBattlegroup() {
        return m_battlegroup;
    }

    public void setBattlegroup(String battlegroup) {
        m_battlegroup = battlegroup;
    }

    public String getRealm() {
        return m_realm;
    }

    public void setRealm(String realm) {
        m_realm = realm;
    }

    public long getCreated() {
        return m_created;
    }

    public void setCreated(long created) {
        m_created = created;
    }

    public int getFaction() {
        return m_faction;
    }

    public void setFaction(int faction) {
        m_faction = faction;
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

    public int getLastSeasonRank() {
        return m_lastSeasonRank;
    }

    public void setLastSeasonRank(int lastSeasonRank) {
        m_lastSeasonRank = lastSeasonRank;
    }

    public int getRating() {
        return m_rating;
    }

    public void setRating(int rating) {
        m_rating = rating;
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

    public int getSize() {
        return m_size;
    }

    public void setSize(int size) {
        m_size = size;
    }

    public int getTeamSize() {
        return m_teamSize;
    }

    public void setTeamSize(int teamSize) {
        m_teamSize = teamSize;
    }


    // --------------------------------------------------------- Public Methods


    public int getGamesLost() {
        return m_gamesPlayed - m_gamesWon;
    }

    public int getSeasonGamesLost() {
        return m_seasonGamesPlayed - m_seasonGamesWon;
    }


    public String toString() {
        StringBuffer sb = new StringBuffer("[");
        sb.append("battlegroup = " + m_battlegroup + "; ");
        sb.append("created = " + m_created + "; ");
        sb.append("faction = " + m_faction + "; ");
        sb.append("gamesPlayed = " + m_gamesPlayed + "; ");
        sb.append("gamesWon = " + m_gamesWon + "; ");
        sb.append("lastSeasonRank = " + m_lastSeasonRank + "; ");
        sb.append("name = " + m_name + "; ");
        sb.append("rank = " + m_rank + "; ");
        sb.append("rating = " + m_rating + "; ");
        sb.append("realm = " + m_realm + "; ");
        sb.append("regionCode = " + m_regionCode + "; ");
        sb.append("seasonGamesPlayed = " + m_seasonGamesPlayed + "; ");
        sb.append("seasonGamesWon = " + m_seasonGamesWon + "; ");
        sb.append("size = " + m_size + "; ");
        sb.append("teamSize = " + m_teamSize);

        sb.append("]");

        return sb.toString();
    }

}
