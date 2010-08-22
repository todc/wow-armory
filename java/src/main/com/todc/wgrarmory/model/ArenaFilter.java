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
public class ArenaFilter {


    public static final int LADDER_2v2 = 2;
    public static final int LADDER_3v3 = 3;
    public static final int LADDER_5v5 = 5;

    public static final String SORT_RANK = "rank";
    public static final String SORT_TEAM_NAME = "name";
    public static final String SORT_REALM = "realm";
    public static final String SORT_FACTION = "faction";
    public static final String SORT_WINS = "sgw";
    public static final String SORT_LOSSES = "sgl";
    public static final String SORT_RATING = "rating";

    public static final String DIR_ASC = "a";
    public static final String DIR_DESC = "d";


    // ----------------------------------------------------- Instance Variables


    private int m_ladder;
    private String m_realm;
    private String m_sortBy;
    private String m_sortDir = DIR_ASC;
    private int m_page;
    private int m_maxPages;


    // ----------------------------------------------------------- Constructors


    public ArenaFilter() {}

    public ArenaFilter(int ladder) {
        m_ladder = ladder;
    }


    // ------------------------------------------------------ Getters / Setters


    public int getLadder() {
        return m_ladder;
    }

    public void setLadder(int ladder) {
        m_ladder = ladder;
    }

    public String getRealm() {
        return m_realm;
    }

    public void setRealm(String realm) {
        m_realm = realm;
    }

    public String getSortBy() {
        return m_sortBy;
    }

    public void setSortBy(String sortBy) {
        m_sortBy = sortBy;
    }

    public String getSortDir() {
        return m_sortDir;
    }

    public void setSortDir(String sortDir) {
        m_sortDir = sortDir;
    }

    public int getPage() {
        return m_page;
    }

    public void setPage(int page) {
        m_page = page;
    }

    public int getMaxPages() {
        return m_maxPages;
    }

    public void setMaxPages(int maxPages) {
        m_maxPages = maxPages;
    }


    // --------------------------------------------------------- Public Methods

}
