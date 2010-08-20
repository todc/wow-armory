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
     * Retrieve guild info, including roster, from the Armory.
     *
     * @param guildName Name of the guild
     * @param realmName Name of the realm
     * @param regionCode Region code, e.g. US, EU, etc
     * @return Matching guild object
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


    public List<AchievementCategory> fetchCharacterAchievements(String charName, String realmName, String regionCode, int category)
            throws Exception
    {
        return fetchCharacterAchievements(charName, realmName, regionCode, category, null);
    }


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


    // -------------------------------------------------------- Private Methods


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

        /*
        String[] names = new String[] {"Gogan", "Kuramori", "Aozaru", "Haibane", "Asano", "Ikuya", "Orlandin", "Zol", "Zorthy", "Torchholder"};

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

        String[] names = new String[] { "Gentlemen of Leisure" };

        int count = 1;
        for (int i=0; i<count; i++) {
            for (String name : names) {
                long s1 = System.currentTimeMillis();

                Guild g = armory.fetchGuild(name, "Dawnbringer", "US");

                long e1 = System.currentTimeMillis();

                System.out.println((e1-s1) + "ms - " + g);
            }

            Thread.sleep(3);
        }

        long end = System.currentTimeMillis();
        System.out.println("Done. Averaged " + ((end-start)/(names.length * count)) + "ms/req");
    }

}
