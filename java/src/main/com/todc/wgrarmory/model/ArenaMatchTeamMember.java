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
 * Models an arena team's character and its playing stats from a particular
 * arena match. Returned as part of a {@link ArenaMatchTeam} object from
 * {@link com.todc.wgrarmory.Armory#fetchArenaMatchDetails}.
 *
 * @author <a href="mailto:tim@timodonnell.com">Tim O'Donnell</a>
 */
public class ArenaMatchTeamMember {


    // ----------------------------------------------------- Instance Variables


    private String m_name;
    private int m_classId;
    private int m_damageDone;
    private int m_damageTaken;
    private boolean m_deleted;
    private boolean m_died;
    private int m_gender;
    private int m_healingDone;
    private int m_healingTaken;
    private int m_killingBlows;
    private int m_race;


    // ------------------------------------------------------ Getters / Setters


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

    public int getDamageDone() {
        return m_damageDone;
    }

    public void setDamageDone(int damageDone) {
        m_damageDone = damageDone;
    }

    public int getDamageTaken() {
        return m_damageTaken;
    }

    public void setDamageTaken(int damageTaken) {
        m_damageTaken = damageTaken;
    }

    public boolean isDeleted() {
        return m_deleted;
    }

    public void setDeleted(boolean deleted) {
        m_deleted = deleted;
    }

    public boolean isDied() {
        return m_died;
    }

    public void setDied(boolean died) {
        m_died = died;
    }

    public int getGender() {
        return m_gender;
    }

    public void setGender(int gender) {
        m_gender = gender;
    }

    public int getHealingDone() {
        return m_healingDone;
    }

    public void setHealingDone(int healingDone) {
        m_healingDone = healingDone;
    }

    public int getHealingTaken() {
        return m_healingTaken;
    }

    public void setHealingTaken(int healingTaken) {
        m_healingTaken = healingTaken;
    }

    public int getKillingBlows() {
        return m_killingBlows;
    }

    public void setKillingBlows(int killingBlows) {
        m_killingBlows = killingBlows;
    }

    public int getRace() {
        return m_race;
    }

    public void setRace(int race) {
        m_race = race;
    }


    // --------------------------------------------------------- Public Methods


    public String toString() {
        StringBuffer sb = new StringBuffer("[");
        sb.append("name = " + m_name + "; ");
        sb.append("classId = " + m_classId + "; ");
        sb.append("damageDone = " + m_damageDone + "; ");
        sb.append("damageTaken = " + m_damageTaken + "; ");
        sb.append("deleted = " + m_deleted + "; ");
        sb.append("died = " + m_died + "; ");
        sb.append("gender = " + m_gender + "; ");
        sb.append("healingDone = " + m_healingDone + "; ");
        sb.append("healingTaken = " + m_healingTaken + "; ");
        sb.append("killingBlows = " + m_killingBlows + "; ");
        sb.append("race = " + m_race);

        return sb.toString();
    }

}
