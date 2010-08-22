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
 * 
 *
 * @author <a href="mailto:odonnellt@gmail.com">Tim O'Donnell</a>
 */
public class ArenaOpposingTeam {


    // ----------------------------------------------------- Instance Variables


    private boolean m_deleted;
    private int m_games;
    private int m_wins;
    private int m_losses;
    private double m_winPct;
    private int m_mla;
    private int m_ratingDelta;
    private String m_realm;
    private String m_teamName;


    // ------------------------------------------------------ Getters / Setters


    public boolean isDeleted() {
        return m_deleted;
    }

    public void setDeleted(boolean deleted) {
        m_deleted = deleted;
    }

    public int getGames() {
        return m_games;
    }

    public void setGames(int games) {
        m_games = games;
    }

    public int getWins() {
        return m_wins;
    }

    public void setWins(int wins) {
        m_wins = wins;
    }

    public int getLosses() {
        return m_losses;
    }

    public void setLosses(int losses) {
        m_losses = losses;
    }

    public double getWinPct() {
        return m_winPct;
    }

    public void setWinPct(double winPct) {
        m_winPct = winPct;
    }

    public int getMla() {
        return m_mla;
    }

    public void setMla(int mla) {
        m_mla = mla;
    }

    public int getRatingDelta() {
        return m_ratingDelta;
    }

    public void setRatingDelta(int ratingDelta) {
        m_ratingDelta = ratingDelta;
    }

    public String getRealm() {
        return m_realm;
    }

    public void setRealm(String realm) {
        m_realm = realm;
    }

    public String getTeamName() {
        return m_teamName;
    }

    public void setTeamName(String teamName) {
        m_teamName = teamName;
    }
}
