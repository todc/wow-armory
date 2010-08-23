package com.todc.wgrarmory.model;


import java.util.List;


/**
 * Models an arena team for a specific arena match. Returned by
 * {@link com.todc.wgrarmory.Armory#fetchArenaMatchDetails}.
 *
 * @author <a href="mailto:odonnellt@gmail.com">Tim O'Donnell</a>
 */
public class ArenaMatchTeam {


    // ----------------------------------------------------- Instance Variables


    private boolean m_deleted;
    private String m_name;
    private int m_newRating;
    private int m_ratingDelta;
    private String m_realm;
    private String m_result;

    private List<ArenaMatchTeamMember> m_members;


    // ------------------------------------------------------ Getters / Setters
    

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

    public int getRatingDelta() {
        return m_ratingDelta;
    }

    public void setRatingDelta(int ratingDelta) {
        m_ratingDelta = ratingDelta;
    }

    public List<ArenaMatchTeamMember> getMembers() {
        return m_members;
    }

    public void setMembers(List<ArenaMatchTeamMember> members) {
        m_members = members;
    }


    // --------------------------------------------------------- Public Methods


    public String toString() {
        StringBuffer sb = new StringBuffer("[");
        sb.append("deleted = " + m_deleted + "; ");
        sb.append("name = " + m_name + "; ");
        sb.append("newRating = " + m_newRating + "; ");
        sb.append("ratingDelta = " + m_ratingDelta + "; ");
        sb.append("realm = " + m_realm + "; ");
        sb.append("result = " + m_result + "; ");
        sb.append("members = ");

        if (m_members == null) {
            sb.append("null");
        } else {
            sb.append("[");
            for (ArenaMatchTeamMember m : m_members) {
                sb.append(m + ", ");
            }
            sb.append("]");
        }

        sb.append("]");

        return sb.toString();
    }
}
