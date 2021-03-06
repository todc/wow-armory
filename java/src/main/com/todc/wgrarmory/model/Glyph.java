package com.todc.wgrarmory.model;


/**
 * @author <a href="mailto:tim@timodonnell.com">Tim O'Donnell</a>
 */
public class Glyph {


    // -------------------------------------------------------------- Constants


    public static final int MAJOR = 1;
    public static final int MINOR = 2;

    private static final String[] names = new String[] { "", "Major", "Minor" };


    // --------------------------------------------------------- Static Methods


    public static String getTypeName(int type) {
        return names[type];
    }


    // ----------------------------------------------------- Instance Variables


    private int m_id;
    private String m_name;
    private String m_effect;
    private int m_type;


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

    public String getEffect() {
        return m_effect;
    }

    public void setEffect(String effect) {
        m_effect = effect;
    }

    public int getType() {
        return m_type;
    }

    public void setType(int type) {
        m_type = type;
    }


    // --------------------------------------------------------- Public Methods


    public String toString() {
        StringBuffer sb = new StringBuffer("[");
        sb.append("id = " + m_id + "; ");
        sb.append("name = " + m_name + "; ");
        sb.append("effect = " + m_effect + "; ");
        sb.append("type = " + m_type);
        sb.append("]");

        return sb.toString();
    }

}
