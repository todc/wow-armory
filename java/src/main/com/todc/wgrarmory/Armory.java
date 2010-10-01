package com.todc.wgrarmory;


import java.net.Proxy;
import java.util.List;
import java.util.Map;

import com.todc.wgrarmory.model.*;


/**
 * @author <a href="mailto:tim@timodonnell.com">Tim O'Donnell</a>
 */
public interface Armory {


    /**
     * Specify a proxy IP and port through which to tunnel all armory requests.
     *
     * @param ip IP address
     * @param port Port number
     * @param username Username used to connect to proxy
     * @param password Password used to connect to proxy
     */
    public void setProxy(String ip, int port, String username, String password);
    public Proxy getProxy();
    public void clearProxy();

    public int getCurrentRequestCount();
    public int getTotalRequestCount();
    public void resetCurrentRequestCount();
    public void resetTotalRequestCount();

    /**
     * Specify the lowest level character to return during fetchGuild. For
     * example, specifying '80' will cause fetchGuild to only return characters
     * level 80 or higher in the guild roster.
     *
     * @param val Minimum character level
     */
    public void setFetchMinLevel(int val);
    public int getFetchMinLevel();

    /**
     * Specify whether to populate the achievement title when fetching
     * character achievements. Defaults to true.
     *
     * @param val False to skip achievement titles, True to retrieve them
     */
    public void setFetchAchievementTitle(boolean val);
    public boolean isFetchAchievementTitle();

    /**
     * Specify whether to populate the achievement description when fetching
     * character achievements. Defaults to true.
     *
     * @param val False to skip achievement description, True to retrieve them
     */
    public void setFetchAchievementDescription(boolean val);
    public boolean isFetchAchievementDescription();

    /**
     * Specify whether to populate the achievement description when fetching
     * character achievements. Defaults to true.
     *
     * @param val False to skip achievement description, True to retrieve them
     */
    public void setFetchAchievementCriteria(boolean val);
    public boolean isFetchAchievementCriteria();

    /**
     * Specify whether to parse and populate sub-achievements when fetching
     * character achievements. Defaults to true.
     *
     * @param val False to skip sub-achievements, True to retrieve them
     */
    public void setFetchSubAchievements(boolean val);
    public boolean isFetchSubAchievements();

    /**
     * Specify whether to parse and populate talents and glyphs during
     * fetchCharacter. Defaults to true.
     *
     * @param val False to skip character talents, True to retrieve them
     */
    public void setFetchCharacterTalents(boolean val);
    public boolean isFetchCharacterTalents();

    /**
     * Specify whether to parse and populate professions during fetchCharacter.
     * Defaults to true.
     *
     * @param val False to skip character professions, True to retrieve them
     */
    public void setFetchCharacterProfessions(boolean val);
    public boolean isFetchCharacterProfessions();

    /**
     * Specify whether to parse and populate character items during
     * fetchCharacter. Defaults to true.
     *
     * @param val False to skip character items, True to retrieve them
     */
    public void setFetchCharacterItems(boolean val);
    public boolean isFetchCharacterItems();

    /**
     * Specify whether to parse and populate a character's arena teams during
     * <code>fetchCharacter</code>. Defaults to true.
     *
     * @param val False to skip character arena teams, True to retrieve them
     */
    public void setFetchCharacterArenaTeams(boolean val);
    public boolean isFetchCharacterArenaTeams();

    /**
     * Specify whether to parse and populate a character's base stats during
     * <code>fetchCharacter</code>. Defaults to true.
     *
     * @param val False to skip character base stats, True to retrieve them
     */
    public void setFetchCharacterBaseStats(boolean val);
    public boolean isFetchCharacterBaseStats();


    /**
     * Determine if the specified region's armory is currently available.
     * Returns true if it's up, false if it's down for maintenance.
     *
     * @param regionCode
     * @return
     * @throws Exception
     */
    public boolean isArmoryUp(String regionCode) throws Exception;


    /**
     * Retrieve guild info, including roster, from the Armory. Performs HTTP
     * request to guild-info.xml.
     *
     * @param guildName Name of the guild
     * @param realmName Name of the realm
     * @param regionCode Region code, e.g. US, EU, etc
     *
     * @return Matching guild object
     *
     * @throws Exception
     */
    public Guild fetchGuild(String guildName, String realmName, String regionCode)
            throws Exception;


    /**
     * Fetch top-level character data including talent specs, professions, and
     * currently equipped items. Performs HTTP request to character-sheet.xml.
     *
     * @param charName Name of character
     * @param realmName Name of realm
     * @param regionCode Region code (e.g. US, EU, etc)
     *
     * @return Matching PlayerCharacter object
     *
     * @throws Exception
     */
    public PlayerCharacter fetchCharacter(String charName, String realmName, String regionCode)
            throws Exception;


    /**
     * Fetch character achievements for the given top-level category (e.g.
     * Dungeons and Raids = 168).
     *
     * @param charName Name of character
     * @param realmName Name of realm
     * @param regionCode Region code (e.g. US, EU, etc)
     * @param category ID of achievement category to parse. Values can be found
     *                 in AchievementCategory.
     *
     * @return List of matching achievement sub-categories and achievement objects
     *
     * @throws Exception
     */
    public List<AchievementCategory> fetchCharacterAchievements(String charName, String realmName, String regionCode, int category)
            throws Exception;


    /**
     * Fetch character achievements for the given top-level category (e.g.
     * Dungeons and Raids = 168). Optionally limit fetch to only specific
     * sub-categories.
     *
     * @param charName Name of character
     * @param realmName Name of realm
     * @param regionCode Region code (e.g. US, EU, etc)
     * @param category ID of achievement category to parse. Values can be found
     *                 in AchievementCategory.
     * @param subCategories Array of specific AchievementCategory IDs to fetch.
     *        Pass in null to obtain all sub-categories.
     *
     * @return List of matching achievement sub-categories and achievement objects
     *
     * @throws Exception
     */
    public List<AchievementCategory> fetchCharacterAchievements(String charName, String realmName, String regionCode, int category, int[] subCategories)
            throws Exception;


    /**
     * Fetch character faction reputations. Performs HTTP request to
     * character-reputation.xml.
     *
     * @param charName Name of character
     * @param realmName Name of realm
     * @param regionCode Region code (e.g. US, EU, etc)
     *
     * @return List of all faction reputations
     *
     * @throws Exception
     */
    public List<Faction> fetchCharacterReputation(String charName, String realmName, String regionCode)
            throws Exception;


    /**
     * Fetch character talents specs and glyphs. Performs HTTP request to
     * character-talents.xml.
     *
     * @param charName Name of character
     * @param realmName Name of realm
     * @param regionCode Region code (e.g. US, EU, etc)
     *
     * @return List of all talent specs and associated glyphs
     *
     * @throws Exception
     */
    public List<TalentSpec> fetchCharacterTalents(String charName, String realmName, String regionCode)
            throws Exception;


    /**
     * Fetch all character statistics for the specified category. Performs HTTP
     * request for character-statistics.xml.
     *
     * @param charName Character name
     * @param realmName Realm name
     * @param regionCode Region code (e.g. US, EU, etc)
     * @param category ID of category to fetch
     *
     * @return Map where each entry consists of the category name (key) and a
     *         list of corresponding statistic objects (value).
     *
     * @throws Exception
     */
    public Map<String,List<Statistic>> fetchCharacterStatistics(String charName, String realmName, String regionCode, int category)
            throws Exception;


    /**
     * Fetch character statistics for the specified category and sub-categories.
     * Performs HTTP request for character-statistics.xml.
     *
     * @param charName Character name
     * @param realmName Realm name
     * @param regionCode Region code (e.g. US, EU, etc)
     * @param category ID of category to fetch
     * @param subCategories Array of sub-category names to fetch. All others
     *        will be ignored.
     *
     * @return Map where each entry consists of the category name (key) and a
     *         list of corresponding statistic objects (value).
     *
     * @throws Exception
     */
    public Map<String,List<Statistic>> fetchCharacterStatistics(String charName, String realmName, String regionCode, int category, String[] subCategories)
            throws Exception;


    /**
     * Fetch entries from the character feed which match the given filter
     * criteria. Perform Armory request to character-feed.atom.
     *
     * @param charName Character name
     * @param realmName Realm name
     * @param regionCode Region code (e.g. US, EU, etc)
     * @param filter Filter criteria
     *
     * @return List of matching FeedEntry objects
     *
     * @throws Exception
     */
    public List<FeedEntry> fetchCharacterFeed(String charName, String realmName, String regionCode, FeedFilter filter)
            throws Exception;


    /**
     * Fetch arena teams according to the filter criteria supplied. Sends a
     * request to the armory URL arena-ladder.xml.
     *
     * @param regionCode Region code (e.g. US, EU, etc)
     * @param battlegroup Name of battlegroup (e.g. Bloodlust, Retaliation, etc)
     * @param filter Filter criteria. At a minimum, a ladder type (e.g. 3v3)
     *        must be specified in the filter.
     *
     * @return List of matching ArenaTeam objects
     *
     * @throws Exception
     */
    public List<ArenaTeam> fetchArenaLadder(String regionCode, String battlegroup, ArenaFilter filter)
            throws Exception;


    /**
     * Fetch arena team members for the given team. Sends a request to the
     * armory URL team-info.xml.
     *
     * @param ladder Ladder type (e.g. 2v2, 3v3, 5v5). See constants in
     *        {@link ArenaFilter}
     * @param regionCode Region code (e.g. US, EU, etc)
     * @param realmName Name of realm
     * @param teamName Name of arena team
     *
     * @return List of matching ArenaTeamMember objects
     *
     * @throws Exception
     */
    public List<ArenaTeamMember> fetchArenaTeamMembers(int ladder, String regionCode, String realmName, String teamName)
            throws Exception;


    /**
     * Fetch arena matches for the given team. Sends a request to the armory
     * URL arena-team-game-chart.xml.
     *
     * @param ladder Ladder type (e.g. 2v2, 3v3, 5v5). See constants in
     *        {@link ArenaFilter}
     * @param regionCode Region code (e.g. US, EU, etc)
     * @param realmName Name of realm
     * @param teamName Name of arena team
     *
     * @return List of matching ArenaMatch objects
     *
     * @throws Exception
     */
    public List<ArenaMatch> fetchArenaTeamMatchHistory(int ladder, String regionCode, String realmName, String teamName)
            throws Exception;


    /**
     * Fetch opponent history for the given team. Sends a request to the armory
     * URL arena-team-report-opposing-teams.xml.
     *
     * @param ladder Ladder type (e.g. 2v2, 3v3, 5v5). See constants in
     *        {@link ArenaFilter}
     * @param regionCode Region code (e.g. US, EU, etc)
     * @param realmName Name of realm
     * @param teamName Name of arena team
     *
     * @return List of matching ArenaOpposingTeam objects
     *
     * @throws Exception
     */
    public List<ArenaOpposingTeam> fetchArenaTeamOpponentHistory(int ladder, String regionCode, String realmName,
                                                                 String teamName)
            throws Exception;


    /**
     * Returns the details of the given arena match. Performs a request to
     * the armory URL arena-game.xml.
     *
     * @param regionCode Region code (e.g. US, EU, etc)
     * @param battlegroup Battlegroup name
     * @param matchId ID of arena match
     *
     * @return Matching ArenaMatch object
     *
     * @throws Exception
     */
    public ArenaMatch fetchArenaMatchDetails(String regionCode, String battlegroup, long matchId)
            throws Exception;


    public List<Item> fetchItems(ItemFilter filter) throws Exception;

}
