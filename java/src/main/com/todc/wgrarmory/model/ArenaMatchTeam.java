/*
 * $Id$
 *
 * Copyright (c)2010, Tim O'Donnell. All Rights Reserved.
 *
 * This code may not be used or reproduced in part or in whole without
 * express written permission of Tim O'Donnell.
 */
package com.todc.wgrarmory.model;


import java.util.List;


/**
 * @author <a href="mailto:odonnellt@gmail.com">Tim O'Donnell</a>
 */
public class ArenaMatchTeam {
    private boolean m_deleted;
    private String m_name;
    private int m_newRating;
    private int m_ratingDelta;
    private String m_realm;
    private String m_result;
    private List<ArenaMatchTeamMember> m_members;

    public boolean isDeleted() {
        return m_deleted;
    }

    public void setDeleted(boolean deleted) {
        m_deleted = deleted;
    }

    public String getName() {
        return m_name;
    }

    public void setName(String name) {
        m_name = name;
    }

    public int getNewRating() {
        return m_newRating;
    }

    public void setNewRating(int newRating) {
        m_newRating = newRating;
    }

    public String getRealm() {
        return m_realm;
    }

    public void setRealm(String realm) {
        m_realm = realm;
    }

    public String getResult() {
        return m_result;
    }

    public void setResult(String result) {
        m_result = result;
    }

    public List<ArenaMatchTeamMember> getMembers() {
        return m_members;
    }

    public void setMembers(List<ArenaMatchTeamMember> members) {
        m_members = members;
    }
}
