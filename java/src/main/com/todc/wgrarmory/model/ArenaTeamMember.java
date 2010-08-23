package com.todc.wgrarmory.model;


/**
 * Models the statistics of a particular arena team's member. Returned from
 * {@link com.todc.wgrarmory.Armory#fetchArenaTeamMembers}, as well as
 * part of the <code>PlayerCharacter</code> object return from
 * <code>fetchCharacter</code>.
 *
 * @author <a href="mailto:tim@timodonnell.com">Tim O'Donnell</a>
 */
public class ArenaTeamMember {


    // ----------------------------------------------------- Instance Variables


    private String m_battlegroup;
    private String m_regionCode;
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
        return m_regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.m_regionCode = regionCode;
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


    public String toString() {
        StringBuffer sb = new StringBuffer("[");
        sb.append("battlegroup = " + m_battlegroup + "; ");
        sb.append("classId = " + m_classId + "; ");
        sb.append("contribution = " + m_contribution + "; ");
        sb.append("gamesPlayed = " + m_gamesPlayed + "; ");
        sb.append("gamesWon = " + m_gamesWon + "; ");
        sb.append("genderId = " + m_genderId + "; ");
        sb.append("guildId = " + m_guildId + "; ");
        sb.append("guildName = " + m_guildName + "; ");
        sb.append("name = " + m_name + "; ");
        sb.append("raceId = " + m_raceId + "; ");
        sb.append("realm = " + m_realm + "; ");
        sb.append("regionCode = " + m_regionCode + "; ");
        sb.append("seasonGamesPlayed = " + m_seasonGamesPlayed + "; ");
        sb.append("seasonGamesWon = " + m_seasonGamesWon + "; ");
        sb.append("teamRank = " + m_teamRank);
        sb.append("]");

        return sb.toString();
    }

}
