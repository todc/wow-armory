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

import com.todc.wgrarmory.model.*;
import org.apache.commons.digester.Digester;

import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Implementation of Armory using Apache Commons Digester to parse the
 * resulting XML.
 *
 * @author <a href="mailto:tim@timodonnell.com">Tim O'Donnell</a>
 */
public class DigesterArmory extends AbstractArmory {


    protected Logger LOG = LoggerFactory.getLogger(DigesterArmory.class);


    // -------------------------------------------------------------- Constants


    private static final String UTF8 = "UTF-8";


    // ----------------------------------------------------- Instance Variables


    private Digester m_characterDigester = new Digester();

    private SimpleDateFormat m_sdfShort = new SimpleDateFormat("yyyy-MM-ddZ");
    private SimpleDateFormat m_sdfLong = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");


    // ----------------------------------------------------------- Constructors


    public DigesterArmory() {
        super();

        m_sdfShort.setTimeZone(TimeZone.getTimeZone("GMT"));
        m_sdfLong.setTimeZone(TimeZone.getTimeZone("GMT"));

        addCharacterRules(m_characterDigester);
    }


    // ------------------------------------------------------ Getters / Setters


    // --------------------------------------------------------- Public Methods


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
        String armoryHost = getArmoryHost(regionCode);
        String rn = URLEncoder.encode(realmName, UTF8);
        String cn = URLEncoder.encode(charName, UTF8);

        String url = "http://" + armoryHost + "/character-achievements.xml?r=" + rn + "&cn=" + cn + "&rhtml=n";

        String responseBody = this.httpGet(url);

        if (!isCharacterFound(responseBody)) {
            throw new CharacterNotFoundException(regionCode + "-" + charName + " not found in armory");
        }

        Element root = toXml(responseBody);

        List<AchievementCategory> allCategories = new ArrayList<AchievementCategory>();

        AchievementCategory topCategory = new AchievementCategory();

        //
        // Parse the top-level achievements, e.g. Glory of the Raider
        //
        Element elTopLevelCategory = root.getChild("category");
        parseAchievements(elTopLevelCategory.getChildren("achievement"), topCategory.getAchievements());

        //
        // Sadly, there's no identifier to deliniate which instance is which in the xml.
        // We're very dependent on the order of the <category> elements.
        //
        List<Element> categories = elTopLevelCategory.getChildren("category");

        // ToC 10
        List<Element> xmlTOC10Achievs = categories.get(8).getChildren("achievement"); // fourth to last category
        parseAchievements(xmlTOC10Achievs, topCategory.getAchievements());

        // ToC 25
        //List<Element> xmlTOC25Achievs = categories.get(9).getChildren("achievement"); // third to last category

        // ICC 10
        List<Element> xmlICC10Achievs = categories.get(10).getChildren("achievement"); // second to last category
        parseAchievements(xmlICC10Achievs, topCategory.getAchievements());

        // ICC 25
        //List<Element> xmlICC25Achievs = categories.get(11).getChildren("achievement"); // last category

        return allCategories;
    }


    // -------------------------------------------------------- Private Methods


    private void parseAchievements(List<Element> xmlAchievs, List<Achievement> charAchievements) throws Exception {
        for (Element element : xmlAchievs) {
            Achievement achievement = new Achievement();

            int id = Integer.parseInt(element.getAttributeValue("id"));
            String title = element.getAttributeValue("title");

            achievement.setId(id);
            achievement.setTitle(title);

            if (element.getAttribute("dateCompleted") != null) {
                String rawCompleted = element.getAttributeValue("dateCompleted");

                //2009-12-13T02:47:00-06:00
                rawCompleted = rawCompleted.replaceAll("([+-])(\\d\\d):(\\d\\d)$", "$1$2$3");
                Date completed = m_sdfLong.parse(rawCompleted);
                achievement.setCompleted(completed);

                charAchievements.add(achievement);
            } else {
                List<Element> criteria = element.getChildren("criteria", element.getNamespace());

                for (Element critElement : criteria) {
                    if (critElement.getAttribute("date") != null) {
                        Integer critId = Integer.parseInt(critElement.getAttributeValue("id"));
                        String rawCompleted = critElement.getAttributeValue("date");
                        String name = critElement.getAttributeValue("name");

                        Achievement achievCrit = new Achievement();
                        achievCrit.setId(critId);

                        //2009-12-13-06:00
                        rawCompleted = rawCompleted.replaceAll("([+-])(\\d\\d):(\\d\\d)$", "$1$2$3");
                        Date completed = m_sdfShort.parse(rawCompleted);
                        achievCrit.setCompleted(completed);

                        charAchievements.add(achievCrit);
                    }
                }
            }

            List<Element> subAchievs = element.getChildren("achievement", element.getNamespace());
            if (subAchievs != null) {
                parseAchievements(subAchievs, charAchievements);
            }
        }
    }


    private void addCharacterRules(Digester d) {
        d.addSetProperties(
            "page/characterInfo/character",
            new String[] {"classId", "factionId", "genderId", "raceId", "suffix", "points", "battleGroup"},
            new String[] {"playerClass", "faction", "gender", "race", "title", "achievementPoints", "battlegroup"}
        );

        d.addObjectCreate("page/characterInfo/characterTab/talentSpecs/talentSpec", TalentSpec.class);
        d.addSetNext("page/characterInfo/characterTab/talentSpecs/talentSpec", "addTalentSpec");
        d.addSetProperties(
            "page/characterInfo/characterTab/talentSpecs/talentSpec",
            new String[] {"prim", "group"},
            new String[] {"name", "number"}
        );

        d.addObjectCreate("page/characterInfo/characterTab/professions/skill", Profession.class);
        d.addSetNext("page/characterInfo/characterTab/professions/skill", "addProfession");
        d.addSetProperties("page/characterInfo/characterTab/professions/skill");

        d.addObjectCreate("page/characterInfo/characterTab/secondaryProfessions/skill", Profession.class);
        d.addSetNext("page/characterInfo/characterTab/secondaryProfessions/skill", "addSecondaryProfession");
        d.addSetProperties("page/characterInfo/characterTab/secondaryProfessions/skill");

        d.addObjectCreate("page/characterInfo/characterTab/items/item", Item.class);
        d.addSetNext("page/characterInfo/characterTab/items/item", "addItem");
        d.addSetProperties(
            "page/characterInfo/characterTab/items/item",
            "permanentEnchantItemId",
            "enchantId"
        );
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
        
        Armory armory = new DigesterArmory();

        String[] names = new String[] {"Gogan123", "Kuramori", "Aozaru", "Haibane", "Asano", "Ikuya", "Orlandin", "Zol", "Zorthy", "Torchholder"};

        int count = 3;
        for (int i=0; i<count; i++) {
            for (String name : names) {
                long s1 = System.currentTimeMillis();

                PlayerCharacter c = armory.fetchCharacter(name, "Dawnbringer", "US");

                long e1 = System.currentTimeMillis();

                System.out.println((e1-s1) + "ms - " + c);
            }

            Thread.sleep(3);
        }

        long end = System.currentTimeMillis();
        System.out.println("Done. Averaged " + ((end-start)/(names.length * count)) + "ms/req");
    }

}
