package com.todc.wgrarmory.model;


/**
 * Models the win-loss stastics against a particular arena team. Returned from
 * {@link com.todc.wgrarmory.Armory#fetchArenaTeamOpponentHistory}.
 *
 * @author <a href="mailto:tim@timodonnell.com">Tim O'Donnell</a>
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


    // --------------------------------------------------------- Public Methods


    public String toString() {
        StringBuffer sb = new StringBuffer("[");
        sb.append("deleted = " + m_deleted + "; ");
        sb.append("games = " + m_games + "; ");
        sb.append("losses = " + m_losses + "; ");
        sb.append("mla = " + m_mla + "; ");
        sb.append("ratingDelta = " + m_ratingDelta + "; ");
        sb.append("realm = " + m_realm + "; ");
        sb.append("teamName = " + m_teamName + "; ");
        sb.append("winPct = " + m_winPct + "; ");
        sb.append("wins = " + m_wins + "; ");

        return sb.toString();
    }
}
