package com.todc.wgrarmory.model;


import java.util.ArrayList;
import java.util.List;


/**
 * @author <a href="mailto:odonnellt@gmail.com">Tim O'Donnell</a>
 */
public class AchievementCategory {


    // -------------------------------------------------------------- Constants


    public static final int ID_DUNGEONS_AND_RAIDS = 168;
    public static final String DUNGEONS_AND_RAIDS = "Dungeons and Raids";
    public static final int ID_CLASSIC = 0;
    public static final String CLASSIC = "Classic";
    public static final int ID_BURNING_CRUSADE = 1;
    public static final String BURNING_CRUSADE = "Burning Crusade";
    public static final int ID_LICH_KING_DUNGEON = 2;
    public static final String LICH_KING_DUNGEON = "Lich King Dungeon";
    public static final int ID_LICH_KING_HEROIC = 3;
    public static final String LICH_KING_HEROIC = "Lich King Heroic";

    public static final int ID_LICH_KING_10 = 4;
    public static final String LICH_KING_10 = "Lich King (10-man)";
    public static final int ID_LICH_KING_25 = 5;
    public static final String LICH_KING_25 = "Lich King (25-man)";

    public static final int ID_ULDUAR_10 = 6;
    public static final String ULDUAR_10 = "Ulduar (10-man)";
    public static final int ID_ULDUAR_25 = 7;
    public static final String ULDUAR_25 = "Ulduar (25-man)";

    public static final int ID_CALL_OF_THE_CRUSADE_10 = 8;
    public static final String CALL_OF_THE_CRUSADE_10 = "Call of the Crusade (10-man)";
    public static final int ID_CALL_OF_THE_CRUSADE_25 = 9;
    public static final String CALL_OF_THE_CRUSADE_25 = "Call of the Crusade (25-man)";

    public static final int ID_FALL_OF_THE_LICH_KING_10 = 10;
    public static final String FALL_OF_THE_LICH_KING_10 = "Fall of the Lich King (10-man)";
    public static final int ID_FALL_OF_THE_LICH_KING_25 = 11;
    public static final String FALL_OF_THE_LICH_KING_25 = "Fall of the Lich King (25-man)";


    public static final String[] NAMES = new String[] {
        CLASSIC, BURNING_CRUSADE, LICH_KING_DUNGEON, LICH_KING_HEROIC, LICH_KING_10, LICH_KING_25,
        ULDUAR_10, ULDUAR_25, CALL_OF_THE_CRUSADE_10, CALL_OF_THE_CRUSADE_25,
        FALL_OF_THE_LICH_KING_10, FALL_OF_THE_LICH_KING_25
    };


    // ----------------------------------------------------- Instance Variables


    private int m_id;
    private String m_name;
    private List<Achievement> m_achievements = new ArrayList<Achievement>();


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

    public List<Achievement> getAchievements() {
        return m_achievements;
    }

    public void setAchievements(List<Achievement> achievements) {
        m_achievements = achievements;
    }


    // --------------------------------------------------------- Public Methods


    public String toString() {
        StringBuffer sb = new StringBuffer("[");
        sb.append("id = " + m_id + "; ");
        sb.append("name = " + m_name + "; ");
        sb.append("achievements = [");

        for (Achievement a : m_achievements) {
            sb.append(a + ", ");
        }
        sb.append("]");

        return sb.toString();
    }
}
