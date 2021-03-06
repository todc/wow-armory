package com.todc.wgrarmory.model;


import java.util.ArrayList;
import java.util.List;


/**
 * @author <a href="mailto:tim@timodonnell.com">Tim O'Donnell</a>
 */
public class PlayerCharacter {


    // -------------------------------------------------------------- Constants


    public static final int ALLIANCE = 0;
    public static final int HORDE = 1;

    public static final int MALE = 0;
    public static final int FEMALE = 1;

    public static final int BLOOD_ELF = 10;
    public static final int ORC = 2;
    public static final int TAUREN = 6;
    public static final int TROLL = 8;
    public static final int UNDEAD = 5;
    public static final int DRAENEI = 11;
    public static final int DWARF = 3;
    public static final int GNOME = 7;
    public static final int HUMAN = 1;
    public static final int NIGHT_ELF = 4;

    public static final int DEATH_KNIGHT = 6;
    public static final int DRUID = 11;
    public static final int HUNTER = 3;
    public static final int MAGE = 8;
    public static final int PALADIN = 2;
    public static final int PRIEST = 5;
    public static final int ROGUE = 4;
    public static final int SHAMAN = 7;
    public static final int WARLOCK = 9;
    public static final int WARRIOR = 1;

    private static String[] m_factionNames = new String[] {"Alliance", "Horde"};
    private static String[] m_genderNames = new String[] {"Male", "Female"};
    private static String[] m_raceNames = new String[] {"", "Human", "Orc", "Dwarf", "Night Elf", "Undead", "Tauren", "Gnome", "Troll", "", "Blood Elf", "Draenei"};
    private static String[] m_classNames = new String[] {"", "Warrior", "Paladin", "Hunter", "Rogue", "Priest", "Death Knight", "Shaman", "Mage", "Warlock", "", "Druid"};


    // --------------------------------------------------------- Static Methods


    public static String getGenderName(int gender) {
        return m_genderNames[gender];
    }


    public static String getFactionName(int faction) {
        return m_factionNames[faction];
    }


    public static String getRaceName(int race) {
        return m_raceNames[race];
    }


    public static String getClassName(int classId) {
        return m_classNames[classId];
    }


    // ----------------------------------------------------- Instance Variables


    private String m_name;
    private Integer m_playerClass;
    private Integer m_gender;
    private Integer m_faction;
    private Integer m_race;
    private Integer m_level;
    private Integer m_rank;

    private String m_battlegroup;
    private String m_guildName;
    private String m_realm;
    private String m_region;
    private String m_title;
    private Integer m_titleId;

    private Integer m_achievementPoints;

    private BaseStats m_baseStats;

    private List<TalentSpec> m_talentSpecs = new ArrayList<TalentSpec>();
    private List<Profession> m_professions = new ArrayList<Profession>();
    private List<Profession> m_secondaryProfessions = new ArrayList<Profession>();
    private List<Item> m_items = new ArrayList<Item>();
    private List<ArenaTeam> m_arenaTeams = new ArrayList<ArenaTeam>();


    // ------------------------------------------------------ Getters / Setters


    public String getName() {
        return m_name;
    }

    public void setName(String name) {
        m_name = name;
    }

    public Integer getRace() {
        return m_race;
    }

    public void setRace(Integer race) {
        m_race = race;
    }

    public Integer getGender() {
        return m_gender;
    }

    public void setGender(Integer gender) {
        m_gender = gender;
    }

    public Integer getPlayerClass() {
        return m_playerClass;
    }

    public void setPlayerClass(Integer playerClass) {
        m_playerClass = playerClass;
    }

    public Integer getFaction() {
        return m_faction;
    }

    public void setFaction(Integer faction) {
        m_faction = faction;
    }

    public Integer getLevel() {
        return m_level;
    }

    public void setLevel(Integer level) {
        m_level = level;
    }

    public Integer getRank() {
        return m_rank;
    }

    public void setRank(Integer rank) {
        m_rank = rank;
    }

    public String getBattlegroup() {
        return m_battlegroup;
    }

    public void setBattlegroup(String battlegroup) {
        m_battlegroup = battlegroup;
    }

    public String getGuildName() {
        return m_guildName;
    }

    public void setGuildName(String guild) {
        m_guildName = guild;
    }

    public String getRealm() {
        return m_realm;
    }

    public void setRealm(String realm) {
        m_realm = realm;
    }

    public String getRegion() {
        return m_region;
    }

    public void setRegion(String region) {
        m_region = region;
    }

    public String getTitle() {
        return m_title;
    }

    public void setTitle(String title) {
        m_title = title;
    }

    public Integer getTitleId() {
        return m_titleId;
    }

    public void setTitleId(Integer titleId) {
        m_titleId = titleId;
    }

    public Integer getAchievementPoints() {
        return m_achievementPoints;
    }

    public void setAchievementPoints(Integer achievementPoints) {
        m_achievementPoints = achievementPoints;
    }

    public List<Profession> getProfessions() {
        return m_professions;
    }

    public void setProfessions(List<Profession> professions) {
        m_professions = professions;
    }

    public List<Profession> getSecondaryProfessions() {
        return m_secondaryProfessions;
    }

    public void setSecondaryProfessions(List<Profession> secondaryProfessions) {
        m_secondaryProfessions = secondaryProfessions;
    }

    public List<TalentSpec> getTalentSpecs() {
        return m_talentSpecs;
    }

    public void setTalentSpecs(List<TalentSpec> talentSpecs) {
        m_talentSpecs = talentSpecs;
    }

    public List<Item> getItems() {
        return m_items;
    }

    public void setItems(List<Item> items) {
        m_items = items;
    }

    public List<ArenaTeam> getArenaTeams() {
        return m_arenaTeams;
    }

    public void setArenaTeams(List<ArenaTeam> arenaTeams) {
        m_arenaTeams = arenaTeams;
    }

    public BaseStats getBaseStats() {
        return m_baseStats;
    }

    public void setBaseStats(BaseStats baseStats) {
        m_baseStats = baseStats;
    }


    // --------------------------------------------------------- Public Methods


    public void addTalentSpec(TalentSpec spec) {
        m_talentSpecs.add(spec);
    }

    public TalentSpec getPrimaryTalentSpec() {
        for (TalentSpec t : m_talentSpecs) {
            if (t.getNumber() == 1) {
                return t;
            }
        }

        return null;
    }

    public TalentSpec getSecondaryTalentSpec() {
        for (TalentSpec t : m_talentSpecs) {
            if (t.getNumber() == 2) {
                return t;
            }
        }

        return null;
    }

    public TalentSpec getActiveTalentSpec() {
        for (TalentSpec t : m_talentSpecs) {
            if (t.isActive()) {
                return t;
            }
        }

        return null;
    }

    public void addProfession(Profession prof) {
        m_professions.add(prof);
    }

    public void addSecondaryProfession(Profession prof) {
        m_secondaryProfessions.add(prof);
    }

    public void addItem(Item item) {
        m_items.add(item);
    }

    public void addArenaTeam(ArenaTeam team) {
        m_arenaTeams.add(team);
    }


    public String toString() {
        StringBuffer sb = new StringBuffer(
           "[" +
           "name = " + m_name + "; " +
           "faction = " + m_faction + "; " +
           "gender = " + m_gender + "; " +
           "race = " + m_race + "; " +
           "class = " + m_playerClass + "; " +
           "level = " + m_level + "; " +
           "titleId = " + m_titleId + "; " +
           "title = " + m_title + "; " +
           "achievementPoints = " + m_achievementPoints + "; " +
           "battlegroup = " + m_battlegroup + "; " +
           "guildName = " + m_guildName + "; " +
           "realm = " + m_realm + "; "
        );

        sb.append("talentSpecs = [");
        for (TalentSpec spec : m_talentSpecs) {
            sb.append(spec + ", ");
        }
        sb.append("]; ");

        sb.append("professions = [");
        for (Profession prof : m_professions) {
            sb.append(prof + ", ");
        }
        sb.append("]; ");

        sb.append("professions = [");
        for (Profession prof : m_professions) {
            sb.append(prof + ", ");
        }
        sb.append("]; ");

        sb.append("secondaryProfessions = [");
        for (Profession prof : m_secondaryProfessions) {
            sb.append(prof + ", ");
        }
        sb.append("]");

        sb.append("items = [");
        for (Item item : m_items) {
            sb.append(item + ", ");
        }
        sb.append("]");

        sb.append("]");

        return sb.toString();
    }
}
