/*
 * $Id$
 *
 * Copyright (c)2010, Tim O'Donnell. All Rights Reserved.
 *
 * This code may not be used or reproduced in part or in whole without
 * express written permission of Tim O'Donnell.
 */
package com.todc.wgrarmory;


import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;

import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.todc.wgrarmory.model.*;


/**
 * Default implementation of Armory interface.
 *
 * @author <a href="mailto:tim@timodonnell.com">Tim O'Donnell</a>
 */
public class DefaultArmoryImpl extends AbstractArmory {


    protected Logger LOG = LoggerFactory.getLogger(DefaultArmoryImpl.class);


    // -------------------------------------------------------------- Constants


    private static final String UTF8 = "UTF-8";


    // ----------------------------------------------------- Instance Variables


    private SimpleDateFormat m_sdfShort = new SimpleDateFormat("yyyy-MM-ddZ");
    private SimpleDateFormat m_sdfLong = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");


    // ----------------------------------------------------------- Constructors


    public DefaultArmoryImpl() {
        super();

        m_sdfShort.setTimeZone(TimeZone.getTimeZone("GMT"));
        m_sdfLong.setTimeZone(TimeZone.getTimeZone("GMT"));
    }


    // ------------------------------------------------------ Getters / Setters


    // --------------------------------------------------------- Public Methods


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
    @SuppressWarnings("unchecked")
    public Guild fetchGuild(String guildName, String realmName, String regionCode) throws Exception {
        String armoryHost = getArmoryHost(regionCode);
        String rn = URLEncoder.encode(realmName, UTF8);
        String gn = URLEncoder.encode(guildName, UTF8);

        String url = "http://" + armoryHost + "/guild-info.xml?r=" + rn + "&gn=" + gn + "&rhtml=n";

        String responseBody = this.httpGet(url);

        Element root = toXml(responseBody);

        Element elGuildHeader = root.getChild("guildInfo").getChild("guildHeader");

        Guild guild = new Guild();
        guild.setName(elGuildHeader.getAttributeValue("name"));
        guild.setRealmName(elGuildHeader.getAttributeValue("realm"));
        guild.setRegionCode(regionCode.toUpperCase());
        guild.setBattlegroup(elGuildHeader.getAttributeValue("battleGroup"));
        guild.setFaction(elGuildHeader.getAttribute("faction").getIntValue());

        List<Element> elements = root.getChild("guildInfo")
                                     .getChild("guild")
                                     .getChild("members")
                                     .getChildren("character");

        for (Element element : elements) {
            int level = element.getAttribute("level").getIntValue();

            if (level >= fetchMinLevel) {
                PlayerCharacter character = new PlayerCharacter();
                character.setRealm(guild.getRealmName());
                character.setRegion(guild.getRegionCode());
                character.setBattlegroup(guild.getBattlegroup());
                character.setGuildName(guild.getName());

                character.setName(element.getAttributeValue("name"));
                character.setLevel(element.getAttribute("level").getIntValue());
                character.setGender(element.getAttribute("genderId").getIntValue());
                character.setPlayerClass(element.getAttribute("classId").getIntValue());
                character.setRace(element.getAttribute("raceId").getIntValue());
                character.setFaction(guild.getFaction());
                character.setRank(element.getAttribute("rank").getIntValue());
                character.setAchievementPoints(element.getAttribute("achPoints").getIntValue());

                guild.addCharacter(character);
            }
        }

        return guild;
    }


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
    @SuppressWarnings("unchecked")
    public PlayerCharacter fetchCharacter(String charName, String realmName, String regionCode)
            throws Exception
    {
        String armoryHost = getArmoryHost(regionCode);
        String rn = URLEncoder.encode(realmName, UTF8);
        String cn = URLEncoder.encode(charName, UTF8);

        String url = "http://" + armoryHost + "/character-sheet.xml?r=" + rn + "&cn=" + cn + "&rhtml=n";

        String xml = this.httpGet(url);

        if (!isCharacterFound(xml)) {
            throw new CharacterNotFoundException(regionCode + "-" + charName + " not found in armory (" + xml.length() + ")");
        }

        Element root = toXml(xml);

        Element elChar = root.getChild("characterInfo").getChild("character");

        PlayerCharacter character = new PlayerCharacter();
        character.setName(elChar.getAttributeValue("name"));
        character.setLevel(elChar.getAttribute("level").getIntValue());
        character.setRealm(elChar.getAttributeValue("realm"));
        character.setRegion(regionCode.toUpperCase());
        character.setBattlegroup(elChar.getAttributeValue("battleGroup"));
        character.setPlayerClass(elChar.getAttribute("classId").getIntValue());
        character.setRace(elChar.getAttribute("raceId").getIntValue());
        character.setGender(elChar.getAttribute("genderId").getIntValue());
        character.setFaction(elChar.getAttribute("factionId").getIntValue());
        character.setAchievementPoints(elChar.getAttribute("points").getIntValue());
        character.setTitleId(elChar.getAttribute("titleId").getIntValue());
        character.setTitle(elChar.getAttributeValue("suffix"));
        character.setGuildName(elChar.getAttributeValue("guildName"));

        // Fetch talents?
        if (fetchCharacterTalents) {
            Element elTalentSpecs = root.getChild("characterInfo").getChild("characterTab").getChild("talentSpecs");
            List<Element> xmlTalentSpecs = elTalentSpecs.getChildren("talentSpec");
            for (Element elTalentSpec : xmlTalentSpecs) {
                TalentSpec spec = new TalentSpec();
                spec.setName(elTalentSpec.getAttributeValue("prim"));
                spec.setNumber(elTalentSpec.getAttribute("group").getIntValue());
                spec.setActive(elTalentSpec.getAttributeValue("active") != null);
                spec.setTreeOne(elTalentSpec.getAttribute("treeOne").getIntValue());
                spec.setTreeTwo(elTalentSpec.getAttribute("treeTwo").getIntValue());
                spec.setTreeThree(elTalentSpec.getAttribute("treeThree").getIntValue());

                character.addTalentSpec(spec);
            }

            // get active glyphs
            List<Element> xmlGlyphs = root.getChild("characterInfo")
                                          .getChild("characterTab")
                                          .getChild("glyphs")
                                          .getChildren("glyph");

            TalentSpec primaryTalentSpec = character.getPrimaryTalentSpec();

            for (Element elGlyph : xmlGlyphs) {
                Glyph glyph = new Glyph();
                glyph.setId(elGlyph.getAttribute("id").getIntValue());
                glyph.setName(elGlyph.getAttributeValue("name"));
                glyph.setEffect(elGlyph.getAttributeValue("effect"));

                String type = elGlyph.getAttributeValue("type");
                if (type.equals("major")) glyph.setType(Glyph.MAJOR);
                if (type.equals("minor")) glyph.setType(Glyph.MINOR);

                primaryTalentSpec.addGlyph(glyph);
            }

        }

        // Fetch items?
        if (fetchCharacterItems) {
            Element elItems = root.getChild("characterInfo").getChild("characterTab").getChild("items");
            List<Element> xmlItems = elItems.getChildren("item");
            for (Element elItem : xmlItems) {
                Item item = new Item();
                item.setSlot(elItem.getAttribute("slot").getIntValue());
                item.setId(elItem.getAttribute("id").getIntValue());
                item.setName(elItem.getAttributeValue("name"));
                item.setRarity(elItem.getAttribute("rarity").getIntValue());

                String enchantId = elItem.getAttributeValue("permanentEnchantItemId");
                if (enchantId != null) {
                    item.setEnchantId(Integer.parseInt(enchantId));
                }
                item.setGem0Id(elItem.getAttribute("gem0Id").getIntValue());
                item.setGem1Id(elItem.getAttribute("gem1Id").getIntValue());
                item.setGem2Id(elItem.getAttribute("gem2Id").getIntValue());

                character.addItem(item);
            }
        }

        // Fetch professions?
        if (fetchCharacterProfessions) {
            Element elProfessions = root.getChild("characterInfo").getChild("characterTab").getChild("professions");
            List<Element> xmlSkills = elProfessions.getChildren("skill");
            for (Element elSkill : xmlSkills) {
                Profession prof = new Profession();
                prof.setId(elSkill.getAttribute("id").getIntValue());
                prof.setName(elSkill.getAttributeValue("name"));
                prof.setMax(elSkill.getAttribute("max").getIntValue());
                prof.setValue(elSkill.getAttribute("value").getIntValue());

                character.addProfession(prof);
            }

            Element elSecProfessions = root.getChild("characterInfo").getChild("characterTab").getChild("secondaryProfessions");
            List<Element> xmlSecSkills = elSecProfessions.getChildren("skill");
            for (Element elSkill : xmlSecSkills) {
                Profession prof = new Profession();
                prof.setId(elSkill.getAttribute("id").getIntValue());
                prof.setName(elSkill.getAttributeValue("name"));
                prof.setMax(elSkill.getAttribute("max").getIntValue());
                prof.setValue(elSkill.getAttribute("value").getIntValue());

                character.addSecondaryProfession(prof);
            }
        }

        return character;
    }


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
            throws Exception
    {
        return fetchCharacterAchievements(charName, realmName, regionCode, category, null);
    }


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
    @SuppressWarnings("unchecked")
    public List<AchievementCategory> fetchCharacterAchievements(String charName, String realmName, String regionCode, int category, int[] subCategories)
            throws Exception
    {
        String armoryHost = getArmoryHost(regionCode);
        String rn = URLEncoder.encode(realmName, UTF8);
        String cn = URLEncoder.encode(charName, UTF8);

        String url = "http://" + armoryHost + "/character-achievements.xml?r=" + rn + "&cn=" + cn + "&c=" + category + "&rhtml=n";

        String responseBody = this.httpGet(url);

        if (!isCharacterFound(responseBody)) {
            throw new CharacterNotFoundException(regionCode + "-" + charName + " not found in armory");
        }

        Element root = toXml(responseBody);

        List<AchievementCategory> allCategories = new ArrayList<AchievementCategory>();

        Element elTopLevelCategory = root.getChild("category");

        //
        // Parse the top-level achievements, e.g. Glory of the Raider
        //
        if (shouldFetch(AchievementCategory.ID_DUNGEONS_AND_RAIDS, subCategories)) {
            AchievementCategory topCategory = new AchievementCategory();
            topCategory.setId(AchievementCategory.ID_DUNGEONS_AND_RAIDS);
            topCategory.setName(AchievementCategory.DUNGEONS_AND_RAIDS);
            allCategories.add(topCategory);

            parseAchievements(elTopLevelCategory.getChildren("achievement"), topCategory.getAchievements());
        }

        //
        // Sadly, there's no identifier to deliniate which instance is which in the xml.
        // We're very dependent on the order of the <category> elements.
        //
        List<Element> categories = elTopLevelCategory.getChildren("category");

        for (int id=0; id<categories.size(); id++) {
            if (shouldFetch(id, subCategories)) {
                AchievementCategory ac = new AchievementCategory();
                ac.setId(id);
                ac.setName(AchievementCategory.NAMES[id]);

                List<Element> xmlAchievements = categories.get(id).getChildren("achievement");
                parseAchievements(xmlAchievements, ac.getAchievements());

                allCategories.add(ac);
            }
        }

        return allCategories;
    }


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
    @SuppressWarnings("unchecked")
    public List<Faction> fetchCharacterReputation(String charName, String realmName, String regionCode) throws Exception {
        String armoryHost = getArmoryHost(regionCode);
        String rn = URLEncoder.encode(realmName, UTF8);
        String cn = URLEncoder.encode(charName, UTF8);

        String url = "http://" + armoryHost + "/character-reputation.xml?r=" + rn + "&cn=" + cn + "&rhtml=n";

        String responseBody = this.httpGet(url);

        Element root = toXml(responseBody);

        List<Element> elFactions = root.getChild("characterInfo")
                                       .getChild("reputationTab")
                                       .getChildren("faction");

        List<Faction> factions = new ArrayList<Faction>();

        parseReputations(elFactions, factions);

        return factions;
    }


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
    @SuppressWarnings("unchecked")
    public List<TalentSpec> fetchCharacterTalents(String charName, String realmName, String regionCode) throws Exception {
        String armoryHost = getArmoryHost(regionCode);
        String rn = URLEncoder.encode(realmName, UTF8);
        String cn = URLEncoder.encode(charName, UTF8);

        String url = "http://" + armoryHost + "/character-talents.xml?r=" + rn + "&cn=" + cn + "&rhtml=n";

        String responseBody = this.httpGet(url);

        Element root = toXml(responseBody);

        List<Element> xmlTalentGroups = root.getChild("characterInfo")
                                            .getChild("talents")
                                            .getChildren("talentGroup");

        List<TalentSpec> talents = new ArrayList<TalentSpec>();

        for (Element elTalentGroup : xmlTalentGroups) {
            TalentSpec spec = new TalentSpec();
            spec.setNumber(elTalentGroup.getAttribute("group").getIntValue());
            spec.setActive(elTalentGroup.getAttributeValue("active") != null);
            spec.setName(elTalentGroup.getAttributeValue("prim"));

            List<Element> xmlTalentSpecs = elTalentGroup.getChildren("talentSpec");

            for (Element elTalentSpec : xmlTalentSpecs) {
                spec.setBuild(elTalentSpec.getAttributeValue("value"));
                spec.setTreeOne(elTalentSpec.getAttribute("treeOne").getIntValue());
                spec.setTreeTwo(elTalentSpec.getAttribute("treeTwo").getIntValue());
                spec.setTreeThree(elTalentSpec.getAttribute("treeThree").getIntValue());

                List<Element> xmlGlyphs = elTalentGroup.getChild("glyphs").getChildren("glyph");

                for (Element elGlyph : xmlGlyphs) {
                    Glyph glyph = new Glyph();
                    glyph.setId(elGlyph.getAttribute("id").getIntValue());
                    glyph.setName(elGlyph.getAttributeValue("name"));
                    glyph.setEffect(elGlyph.getAttributeValue("effect"));

                    String sType = elGlyph.getAttributeValue("type");
                    int type = 0;
                    if (sType != null) {
                        if (sType.equalsIgnoreCase("major")) type = Glyph.MAJOR;
                        else if (sType.equalsIgnoreCase("minor")) type = Glyph.MINOR;
                    }
                    glyph.setType(type);

                    spec.addGlyph(glyph);
                }
            }

            talents.add(spec);
        }

        return talents;
    }


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
    public Map<String,List<Statistic>> fetchCharacterStatistics(String charName, String realmName, String regionCode, int category) throws Exception {
        return fetchCharacterStatistics(charName, realmName, regionCode, category, null);
    }


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
    @SuppressWarnings("unchecked")
    public Map<String,List<Statistic>> fetchCharacterStatistics(String charName, String realmName, String regionCode, int category, String[] subCategories) throws Exception {
        String armoryHost = getArmoryHost(regionCode);
        String rn = URLEncoder.encode(realmName, UTF8);
        String cn = URLEncoder.encode(charName, UTF8);

        String url = "http://" + armoryHost + "/character-statistics.xml?r=" + rn + "&cn=" + cn + "&c=" + category + "&rhtml=n";

        String responseBody = this.httpGet(url);

        Element root = toXml(responseBody);

        List<Element> xmlTopLevelStats = root.getChildren("statistic");
        String topLevelName = root.getAttributeValue("name");

        Map<String,List<Statistic>> categories = new LinkedHashMap<String,List<Statistic>>();

        // top-level category stats
        if (shouldFetch(topLevelName, subCategories)) {
            List<Statistic> topLevelStats = new ArrayList<Statistic>();

            for (Element elStat : xmlTopLevelStats) {
                Statistic stat = new Statistic();

                if (elStat.getAttribute("id") != null) {
                    stat.setId(elStat.getAttribute("id").getIntValue());
                }
                stat.setName(elStat.getAttributeValue("name"));

                String qty = elStat.getAttributeValue("quantity");
                if (qty != null && !qty.equals("--")) {
                    stat.setQuantity(Integer.parseInt(qty));
                }

                stat.setHighest(elStat.getAttributeValue("highest"));

                topLevelStats.add(stat);
            }

            categories.put(topLevelName, topLevelStats);
        }

        // all other sub-category stats
        List<Element> xmlCategories = root.getChildren("category");
        for (Element elCategory : xmlCategories) {
            String categoryName = elCategory.getAttributeValue("name");

            if (shouldFetch(categoryName, subCategories)) {
                List<Element> xmlStats = elCategory.getChildren("statistic");

                List<Statistic> stats = new ArrayList<Statistic>();

                for (Element elStat : xmlStats) {
                    Statistic stat = new Statistic();

                    if (elStat.getAttribute("id") != null) {
                        stat.setId(elStat.getAttribute("id").getIntValue());
                    }
                    stat.setName(elStat.getAttributeValue("name"));

                    String qty = elStat.getAttributeValue("quantity");
                    if (qty != null && !qty.equals("--")) {
                        stat.setQuantity(Integer.parseInt(qty));
                    }

                    stat.setHighest(elStat.getAttributeValue("highest"));

                    stats.add(stat);
                }

                categories.put(categoryName, stats);
            }
        }


        return categories;
    }


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
    @SuppressWarnings("unchecked")
    public List<FeedEntry> fetchCharacterFeed(String charName, String realmName, String regionCode, FeedFilter filter)
            throws Exception
    {
        String armoryHost = getArmoryHost(regionCode);
        String rn = URLEncoder.encode(realmName, UTF8);
        String cn = URLEncoder.encode(charName, UTF8);

        String url = "http://" + armoryHost + "/character-feed.atom?r=" + rn + "&cn=" + cn;

        String[] filters = filter.getFilters();
        if (filters != null) {
            String filtersParam = "&filters=";
            for (int i=0; i<filters.length; i++) {
                filtersParam += filters[i];
                if (i+1 < filters.length) {
                    filtersParam += ",";
                }
            }

            url += filtersParam;
        }

        Integer[] categories = filter.getAchCategories();
        if (categories != null) {
            String catParam = "&achCategories=";
            for (int i=0; i<categories.length; i++) {
                catParam += categories[i];
                if (i+1 < categories.length) {
                    catParam += ",";
                }
            }

            url += catParam;
        }

        if (filter.getItemQuality() != null) {
            url += "&itemQuality=" + filter.getItemQuality();
        }

        if (filter.getItemLevel() > 0) {
            url += "&itemLevel=" + filter.getItemLevel();
        }

        String responseBody = this.httpGet(url);

        Element root = toXml(responseBody);
        Namespace ns = root.getNamespace();

        List<FeedEntry> entries = new ArrayList<FeedEntry>();

        List<Element> xmlEntries = root.getChildren("entry", ns);
        for (Element elEntry : xmlEntries) {
            FeedEntry entry = new FeedEntry();
            entry.setTitle(elEntry.getChildText("title", ns));
            entry.setContent(elEntry.getChildText("content", ns));
            entry.setId(elEntry.getChildText("id", ns));
            entry.setLink(elEntry.getChildText("link", ns));

            String rawPublished = elEntry.getChildText("published", ns);
            rawPublished = rawPublished.replaceAll("([+-])(\\d\\d):(\\d\\d)$", "$1$2$3");
            Date published = m_sdfLong.parse(rawPublished);
            entry.setPublished(published);

            String rawUpdated = elEntry.getChildText("updated", ns);
            rawUpdated = rawUpdated.replaceAll("([+-])(\\d\\d):(\\d\\d)$", "$1$2$3");
            Date updated = m_sdfLong.parse(rawUpdated);
            entry.setUpdated(updated);

            entries.add(entry);
        }

        return entries;
    }


    @SuppressWarnings("unchecked")
    public List<ArenaTeam> fetchArenaLadder(String regionCode, String battlegroup, ArenaFilter filter) throws Exception {
        String armoryHost = getArmoryHost(regionCode);
        String bg = URLEncoder.encode(battlegroup, UTF8);

        String url = "http://" + armoryHost + "/arena-ladder.xml?rhtml=n&b=" + bg + "&ts=" + filter.getLadder();

        if (filter.getRealm() != null) {
            url += "&ff=realm&fv=" + URLEncoder.encode(filter.getRealm(), UTF8);
        }

        if (filter.getSortBy() != null) {
            url += "&sf=" + filter.getSortBy() + "&sd=" + filter.getSortDir();
        }

        if (filter.getPage() > 0) {
            url += "&p=" + filter.getPage();
        }

        LOG.info("Requesting URL: " + url);
        String responseBody = this.httpGet(url);
        System.out.println(responseBody);

        Element root = toXml(responseBody);

        Element elPagedResult = root.getChild("arenaLadderPagedResult");
        filter.setMaxPages(elPagedResult.getAttribute("maxPage").getIntValue());

        List<ArenaTeam> teams = new ArrayList<ArenaTeam>();

        List<Element> xmlTeams = root.getChild("arenaLadderPagedResult")
                                     .getChild("arenaTeams")
                                     .getChildren("arenaTeam");
        
        for (Element elTeam : xmlTeams) {
            ArenaTeam team = new ArenaTeam();

            team.setBattlegroup(elTeam.getAttributeValue("battleGroup"));
            team.setCreated(elTeam.getAttribute("created").getLongValue());
            team.setFaction(elTeam.getAttribute("factionId").getIntValue());
            team.setGamesPlayed(elTeam.getAttribute("gamesPlayed").getIntValue());
            team.setGamesWon(elTeam.getAttribute("gamesWon").getIntValue());
            team.setLastSeasonRank(elTeam.getAttribute("lastSeasonRanking").getIntValue());
            team.setName(elTeam.getAttributeValue("name"));
            team.setRank(elTeam.getAttribute("ranking").getIntValue());
            team.setRating(elTeam.getAttribute("rating").getIntValue());
            team.setRealm(elTeam.getAttributeValue("realm"));
            team.setSeasonGamesPlayed(elTeam.getAttribute("seasonGamesPlayed").getIntValue());
            team.setSeasonGamesWon(elTeam.getAttribute("seasonGamesWon").getIntValue());
            team.setSize(elTeam.getAttribute("size").getIntValue());
            team.setTeamSize(elTeam.getAttribute("teamSize").getIntValue());

            teams.add(team);
        }

        return teams;
    }


    // -------------------------------------------------------- Private Methods


    @SuppressWarnings("unchecked")
    private void parseAchievements(List<Element> xmlAchievs, List<Achievement> charAchievements) throws Exception {
        for (Element element : xmlAchievs) {
            Achievement achievement = new Achievement();

            achievement.setId(element.getAttribute("id").getIntValue());

            // should we fetch the title?
            if (fetchAchievementTitle) {
                achievement.setTitle(element.getAttributeValue("title"));
            }

            // should we fetch the description?
            if (fetchAchievementDescription) {
                achievement.setDescription(element.getAttributeValue("desc"));
            }

            if (element.getAttribute("dateCompleted") != null) {
                // e.g. 2009-12-13T02:47:00-06:00
                String rawCompleted = element.getAttributeValue("dateCompleted");
                rawCompleted = rawCompleted.replaceAll("([+-])(\\d\\d):(\\d\\d)$", "$1$2$3");
                Date completed = m_sdfLong.parse(rawCompleted);
                achievement.setCompleted(completed);

                charAchievements.add(achievement);
            } else {
                // should we fetch the criteria?
                if (fetchAchievementCriteria) {
                    List<Element> criteria = element.getChildren("criteria", element.getNamespace());

                    for (Element critElement : criteria) {
                        if (critElement.getAttribute("date") != null) {
                            Achievement achievCrit = new Achievement();

                            achievCrit.setId(critElement.getAttribute("id").getIntValue());

                            if (fetchAchievementTitle) {
                                achievCrit.setTitle(critElement.getAttributeValue("name"));
                            }

                            if (fetchAchievementDescription) {
                                achievCrit.setParentId(achievement.getId());
                            }

                            // e.g. 2009-12-13-06:00
                            String rawCompleted = critElement.getAttributeValue("date");
                            rawCompleted = rawCompleted.replaceAll("([+-])(\\d\\d):(\\d\\d)$", "$1$2$3");
                            Date completed = m_sdfShort.parse(rawCompleted);
                            achievCrit.setCompleted(completed);

                            achievement.getCriteria().add(achievCrit);
                        }
                    }
                }
            }

            // should we fetch the sub-achievements?
            if (fetchSubAchievements) {
                List<Element> subAchievs = element.getChildren("achievement", element.getNamespace());
                if (subAchievs != null) {
                    parseAchievements(subAchievs, achievement.getSubAchievements());
                }
            }
        }
    }


    @SuppressWarnings("unchecked")
    private void parseReputations(List<Element> xmlFactions, List<Faction> factions) throws Exception {
        for (Element elFaction : xmlFactions) {
            boolean isHeader = elFaction.getAttributeValue("header") != null;

            if (!isHeader) {
                Faction faction = new Faction();
                faction.setId(elFaction.getAttribute("id").getIntValue());
                faction.setName(elFaction.getAttributeValue("name"));
                faction.setReputation(elFaction.getAttribute("reputation").getIntValue());

                factions.add(faction);
            }

            // recursively parse sub-factions
            List<Element> xmlSubFactions = elFaction.getChildren("faction");
            parseReputations(xmlSubFactions, factions);
        }
    }


    private boolean shouldFetch(int needle, int[] haystack) {
        if (haystack == null) {
            return true;
        } else {
            for (int item : haystack) {
                if (item == needle) {
                    return true;
                }
            }
        }

        return false;
    }


    private boolean shouldFetch(String needle, String[] haystack) {
        if (haystack == null) {
            return true;
        } else {
            for (String item : haystack) {
                if (item.equals(needle)) {
                    return true;
                }
            }
        }

        return false;
    }


    /**
     * The armory will return a 200 response if the character doesn't
     * exist, but the response body will look like this:
     *
     * <pre>
     * <?xml version="1.0" encoding="UTF-8"?><?xml-stylesheet type="text/xsl" href="/_layout/character/sheet.xsl"?>
     * <page globalSearch="1" lang="en_us" requestUrl="/character-sheet.xml">
     *   <tabInfo subTab="profile" tab="character" tabGroup="character" tabUrl=""/>
     *   <characterInfo errCode="noCharacter"/>
     * </page>
     * </pre>
     *
     * Check for the "errCode" attribute, and throw an appropriate
     * exception.
     *
     * TODO: Faster way to check for noCharacter, rather than String.indexOf()?
     *
     * @param s http response from armory
     * @return true if the character was found, false if not
     */
    private boolean isCharacterFound(String s) {
        return s.indexOf("errCode=\"noCharacter\"") == -1;
    }


    private Element toXml(String s) throws Exception {
        SAXBuilder builder = new SAXBuilder();
        builder.setIgnoringElementContentWhitespace(true);
        org.jdom.Document doc = builder.build(new ByteArrayInputStream(s.getBytes(UTF8)));
        return doc.getRootElement();
    }

}
