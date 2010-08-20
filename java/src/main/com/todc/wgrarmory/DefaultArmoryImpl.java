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

import org.apache.commons.digester.Digester;

import org.jdom.Element;
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


    private Digester m_characterDigester = new Digester();

    private SimpleDateFormat m_sdfShort = new SimpleDateFormat("yyyy-MM-ddZ");
    private SimpleDateFormat m_sdfLong = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");


    // ----------------------------------------------------------- Constructors


    public DefaultArmoryImpl() {
        super();

        m_sdfShort.setTimeZone(TimeZone.getTimeZone("GMT"));
        m_sdfLong.setTimeZone(TimeZone.getTimeZone("GMT"));

        addCharacterRules(m_characterDigester);
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

        PlayerCharacter character = new PlayerCharacter();

        // clear any internal member variable values before using the
        // Digester on a new document
        m_characterDigester.clear();

        m_characterDigester.push(character);
        m_characterDigester.parse(new StringReader(xml));

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


    // -------------------------------------------------------- Private Methods


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


    private void addCharacterRules(Digester d) {
        d.addSetProperties(
            "page/characterInfo/character",
            new String[] {"classId", "factionId", "genderId", "raceId", "suffix", "points", "battleGroup"},
            new String[] {"playerClass", "faction", "gender", "race", "title", "achievementPoints", "battlegroup"}
        );

        if (fetchCharacterTalents) {
            d.addObjectCreate("page/characterInfo/characterTab/talentSpecs/talentSpec", TalentSpec.class);
            d.addSetNext("page/characterInfo/characterTab/talentSpecs/talentSpec", "addTalentSpec");
            d.addSetProperties(
                "page/characterInfo/characterTab/talentSpecs/talentSpec",
                new String[] {"prim", "group"},
                new String[] {"name", "number"}
            );
        }

        if (fetchCharacterProfessions) {
            d.addObjectCreate("page/characterInfo/characterTab/professions/skill", Profession.class);
            d.addSetNext("page/characterInfo/characterTab/professions/skill", "addProfession");
            d.addSetProperties("page/characterInfo/characterTab/professions/skill");

            d.addObjectCreate("page/characterInfo/characterTab/secondaryProfessions/skill", Profession.class);
            d.addSetNext("page/characterInfo/characterTab/secondaryProfessions/skill", "addSecondaryProfession");
            d.addSetProperties("page/characterInfo/characterTab/secondaryProfessions/skill");
        }

        if (fetchCharacterItems) {
            d.addObjectCreate("page/characterInfo/characterTab/items/item", Item.class);
            d.addSetNext("page/characterInfo/characterTab/items/item", "addItem");
            d.addSetProperties(
                "page/characterInfo/characterTab/items/item",
                "permanentEnchantItemId",
                "enchantId"
            );
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


    // ------------------------------------------------------------ Main Method


    public static void main(String... args) throws Exception {
        long start = System.currentTimeMillis();
        
        Armory armory = new DefaultArmoryImpl();
        armory.setFetchAchievementTitle(true);
        armory.setFetchAchievementDescription(false);
        armory.setFetchAchievementCriteria(false);
        armory.setFetchSubAchievements(false);

        //String[] names = new String[] {"Gogan", "Kuramori", "Aozaru", "Haibane", "Asano", "Ikuya", "Orlandin", "Zol", "Zorthy", "Torchholder"};
        String[] names = new String[] {"Gogan"};
        String[] guilds = new String[] { "Gentlemen of Leisure" };

        /*
        // Test character and achievements
        int count = 3;
        for (int i=0; i<count; i++) {
            for (String name : names) {
                long s1 = System.currentTimeMillis();

                PlayerCharacter c = armory.fetchCharacter(name, "Dawnbringer", "US");
                List<AchievementCategory> achievCats = armory.fetchCharacterAchievements(name, "Dawnbringer", "US",
                    AchievementCategory.ID_DUNGEONS_AND_RAIDS,
                    new int[] {AchievementCategory.ID_FALL_OF_THE_LICH_KING_10}
                );

                long e1 = System.currentTimeMillis();

                System.out.println((e1-s1) + "ms - " + c);

                for (AchievementCategory cat : achievCats) {
                    System.out.println(cat);
                }
            }

            Thread.sleep(3);
        }
        */

        /*
        // Test guild
        int count = 1;
        for (int i=0; i<count; i++) {
            for (String name : guilds) {
                long s1 = System.currentTimeMillis();

                Guild g = armory.fetchGuild(name, "Dawnbringer", "US");

                long e1 = System.currentTimeMillis();

                System.out.println((e1-s1) + "ms - " + g);
            }

            Thread.sleep(3);
        }
        */

        /*
        // Test reputations
        int count = 1;
        for (int i=0; i<count; i++) {
            for (String name : names) {
                long s1 = System.currentTimeMillis();

                List<Faction> factions = armory.fetchCharacterReputation(name, "Dawnbringer", "US");

                long e1 = System.currentTimeMillis();

                for (Faction f : factions) {
                    System.out.println(name + " - " + f);
                }
            }

            Thread.sleep(3);
        }
        */

        // Test talents
        int count = 1;
        for (int i=0; i<count; i++) {
            for (String name : names) {
                long s1 = System.currentTimeMillis();

                List<TalentSpec> talents = armory.fetchCharacterTalents(name, "Dawnbringer", "US");

                long e1 = System.currentTimeMillis();

                for (TalentSpec ts : talents) {
                    System.out.println(name + " - " + ts);
                }
            }

            Thread.sleep(3);
        }

        long end = System.currentTimeMillis();
        System.out.println("Done. Averaged " + ((end-start)/(names.length * count)) + "ms/req");
    }

}
