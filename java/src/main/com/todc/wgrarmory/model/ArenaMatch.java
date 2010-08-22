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
import java.util.Date;
import java.util.List;


/**
 * Models a single Arena Match. It is returned by
 * {@link com.todc.wgrarmory.Armory#fetchArenaMatchDetails}
 * and {@link com.todc.wgrarmory.Armory#fetchArenaTeamMatchHistory}.
 *
 * @author <a href="mailto:tim@timodonnell.com">Tim O'Donnell</a>
 */
public class ArenaMatch {


    // ----------------------------------------------------- Instance Variables


    private boolean m_deleted;
    private long m_id;
    private String m_otherTeamName;
    private int m_newRating;
    private Date m_date;

    private String m_battlegroup;
    private String m_map;
    private int m_matchLength;

    private int m_ladder;

    private List<ArenaMatchTeam> m_teams;


    // ------------------------------------------------------ Getters / Setters


    public boolean isDeleted() {
        return m_deleted;
    }

    public void setDeleted(boolean deleted) {
        m_deleted = deleted;
    }

    public long getId() {
        return m_id;
    }

    public void setId(long id) {
        m_id = id;
    }

    public String getOtherTeamName() {
        return m_otherTeamName;
    }

    public void setOtherTeamName(String otherTeamName) {
        m_otherTeamName = otherTeamName;
    }

    public int getNewRating() {
        return m_newRating;
    }

    public void setNewRating(int newRating) {
        m_newRating = newRating;
    }

    public Date getDate() {
        return m_date;
    }

    public void setDate(Date date) {
        m_date = date;
    }

    public String getBattlegroup() {
        return m_battlegroup;
    }

    public void setBattlegroup(String battlegroup) {
        m_battlegroup = battlegroup;
    }

    public String getMap() {
        return m_map;
    }

    public void setMap(String map) {
        m_map = map;
    }

    public int getMatchLength() {
        return m_matchLength;
    }

    public void setMatchLength(int matchLength) {
        m_matchLength = matchLength;
    }

    public int getLadder() {
        return m_ladder;
    }

    public void setLadder(int ladder) {
        m_ladder = ladder;
    }

    public List<ArenaMatchTeam> getTeams() {
        return m_teams;
    }

    public void setTeams(List<ArenaMatchTeam> teams) {
        m_teams = teams;
    }


    // --------------------------------------------------------- Public Methods


    public void addTeam(ArenaMatchTeam team) {
        if (m_teams == null) {
            m_teams = new ArrayList<ArenaMatchTeam>();
        }

        m_teams.add(team);
    }


    public String toString() {
        StringBuffer sb = new StringBuffer("[");
        sb.append("battlegroup = " + m_battlegroup + "; ");
        sb.append("date = " + m_date + "; ");
        sb.append("deleted = " + m_deleted + "; ");
        sb.append("id = " + m_id + "; ");
        sb.append("ladder = " + m_ladder + "; ");
        sb.append("map = " + m_map + "; ");
        sb.append("matchLength = " + m_matchLength + "; ");
        sb.append("newRating = " + m_newRating + "; ");
        sb.append("otherTeamName = " + m_otherTeamName + "; ");
        sb.append("teams = ");

        if (m_teams == null) {
            sb.append("null");
        } else {
            sb.append("[");
            for (ArenaMatchTeam t : m_teams) {
                sb.append(t + ", ");
            }
            sb.append("]");
        }

        return sb.toString();
    }
}