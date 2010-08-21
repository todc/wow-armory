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
 * @author <a href="mailto:odonnellt@gmail.com">Tim O'Donnell</a>
 */
public class ArenaMatchTeamMember {
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
}
