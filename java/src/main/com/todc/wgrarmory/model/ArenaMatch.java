/*
 * $Id$
 *
 * Copyright (c)2010, Tim O'Donnell. All Rights Reserved.
 *
 * This code may not be used or reproduced in part or in whole without
 * express written permission of Tim O'Donnell.
 */
package com.todc.wgrarmory.model;


import java.util.Date;


/**
 *
  <page globalSearch="1" lang="en_us" requestUrl="/arena-game.xml">

  <game battleGroup="Bloodlust" id="33389057" map="Blade's Edge Arena" matchLength="186" matchStartTime="1278023094000"
        reamOffset="-28800000" teamSize="3">

    <team deleted="false" name="Sodah Snutz Venruki" ratingDelta="48" ratingNew="48" realm="Blackrock" result="win">

      <member characterName="Pookz" classId="8" damageDone="84995" damageTaken="34856" deleted="false" died="false" genderId="1"
              healingDone="3852" healingTaken="41090" killingBlows="1" raceId="10"/>

      <member characterName="Toezer" classId="2" damageDone="11501" damageTaken="19084" deleted="false" died="false" genderId="1"
              healingDone="61991" healingTaken="19251" killingBlows="1" raceId="10"/>

      <member characterName="Fooples" classId="9" damageDone="97391" damageTaken="11247" deleted="false" died="false" genderId="1"
              healingDone="19554" healingTaken="20423" killingBlows="0" raceId="5"/>
    </team>
    <team deleted="false" name="The Infinity Chop" ratingDelta="0" ratingNew="1996" realm="Blackrock" result="loss">

      <member characterName="Torhs" classId="7" damageDone="0" damageTaken="108510" deleted="true" died="true" genderId="0"
              healingDone="84762" healingTaken="74487" killingBlows="0" raceId="6" />

      <member characterName="Kadryel" classId="2" damageDone="59572" damageTaken="12002" deleted="true" died="false" genderId="1"
              healingDone="3479" healingTaken="11969" killingBlows="0" raceId="10" />

      <member characterName="Thorhh" classId="5" damageDone="8417" damageTaken="73360" deleted="true" died="false" genderId="1"
              healingDone="45807" healingTaken="44759" killingBlows="0" raceId="5" />
    </team>
  </game>

  </page>

 *
 * @author <a href="mailto:odonnellt@gmail.com">Tim O'Donnell</a>
 */
public class ArenaMatch {


    // ----------------------------------------------------- Instance Variables


    private boolean m_deleted;
    private long m_id;
    private String m_otherTeamName;
    private int m_newRating;
    private Date m_date;

    private String m_battlgroup;
    private String m_map;
    private int m_matchLength;


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

    public String getBattlgroup() {
        return m_battlgroup;
    }

    public void setBattlgroup(String battlgroup) {
        m_battlgroup = battlgroup;
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
}