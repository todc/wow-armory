package com.todc.wgrarmory.model;


/**
 * @author <a href="mailto:tim@timodonnell.com">Tim O'Donnell</a>
 */
public class Faction {


    // -------------------------------------------------------------- Constants


    public static final int ALLIANCE_VANGUARD = 1037;
    public static final int ARGENT_CRUSADE = 1106;
    public static final int ASHEN_VERDICT = 1156;
    public static final int FRENZYHEART_TRIBE = 1103;
    public static final int HORDE_EXPEDITION = 1052;
    public static final int KALUAK = 1073;
    public static final int KIRIN_TOR = 1090;
    public static final int KNIGHTS_OF_THE_EBON_BLADE = 1098;
    public static final int ORACLES = 1105;
    public static final int SILVER_COVENANT = 1094;
    public static final int SONS_OF_HODIR = 1119;
    public static final int SUNREAVERS = 1124;
    public static final int WYRMREST_ACCORD = 1091;


    // ----------------------------------------------------- Instance Variables


    private int m_id;
    private String m_name;
    private int m_reputation;


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

    public int getReputation() {
        return m_reputation;
    }

    public void setReputation(int reputation) {
        m_reputation = reputation;
    }


    // --------------------------------------------------------- Public Methods


    public String toString() {
        StringBuffer sb = new StringBuffer("[");
        sb.append("id = " + m_id + "; ");
        sb.append("name = " + m_name + "; ");
        sb.append("reputation = " + m_reputation);
        sb.append("]");

        return sb.toString();
    }

}
