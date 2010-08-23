package com.todc.wgrarmory.model;


/**
 * Models a single character statistic. Used by
 * <code>Armory.fetchCharacterStatistics</code>.
 *
 * @author <a href="mailto:tim@timodonnell.com">Tim O'Donnell</a>
 */
public class Statistic {


    // -------------------------------------------------------------- Constants


    public static final int CAT_CHARACTER = 130;
    public static final int CAT_COMBAT = 141;
    public static final int CAT_KILLS = 128;
    public static final int CAT_DEATHS = 122;
    public static final int CAT_QUESTS = 133;
    public static final int CAT_DUNGEONS = 14807;
    public static final int CAT_SKILLS = 132;
    public static final int CAT_TRAVEL = 134;
    public static final int CAT_SOCIAL = 131;
    public static final int CAT_PVP = 21;


    // ----------------------------------------------------- Instance Variables


    private int m_id = 0;
    private String m_name;
    private int m_quantity = 0;
    private String m_highest;


    // ------------------------------------------------------ Getters / Setters


    public int getId() {
        return m_id;
    }

    public void setId(int id) {
        m_id = id;
    }

    public String getName() {
        return m_name;
    }

    public void setName(String name) {
        m_name = name;
    }

    public int getQuantity() {
        return m_quantity;
    }

    public void setQuantity(int quantity) {
        m_quantity = quantity;
    }

    public String getHighest() {
        return m_highest;
    }

    public void setHighest(String highest) {
        m_highest = highest;
    }


    // --------------------------------------------------------- Public Methods


    public String toString() {
        StringBuffer sb = new StringBuffer("[");
        sb.append("id = " + m_id + "; ");
        sb.append("name = " + m_name + "; ");
        sb.append("quantity = " + m_quantity + "; ");
        sb.append("highest = " + m_highest);
        sb.append("]");

        return sb.toString();
    }

}
