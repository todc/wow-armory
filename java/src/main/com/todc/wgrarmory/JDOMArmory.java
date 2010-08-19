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
import java.util.ArrayList;
import java.util.List;

import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.todc.wgrarmory.model.PlayerCharacter;
import com.todc.wgrarmory.model.Profession;
import com.todc.wgrarmory.model.TalentSpec;


/**
 * Implementation of Armory using JDOM to parse the resulting XML.
 *
 * @author <a href="mailto:tim@timodonnell.com">Tim O'Donnell</a>
 */
public class JDOMArmory extends AbstractArmory {


    protected Logger LOG = LoggerFactory.getLogger(JDOMArmory.class);


    // -------------------------------------------------------------- Constants


    private static final String UTF8 = "UTF-8";


    // ----------------------------------------------------- Instance Variables


    // ----------------------------------------------------------- Constructors


    // ------------------------------------------------------ Getters / Setters


    // --------------------------------------------------------- Public Methods


    public PlayerCharacter fetchCharacter(String charName, String realmName, String regionCode) {
        PlayerCharacter character = null;

        try {
            String armoryUrl = config.getProperty("armory.url."+regionCode.toLowerCase());
            String rn = URLEncoder.encode(realmName, UTF8);
            String cn = URLEncoder.encode(charName, UTF8);

            String url = "http://" + armoryUrl + "/character-sheet.xml?r=" + rn + "&cn=" + cn + "&rhtml=n";

            String sXML = this.httpGet(url);
            Element xml = toXml(sXML);

            Namespace ns = xml.getNamespace();

            String errCode = xml.getChild("characterInfo").getAttributeValue("errCode");
            if (errCode != null) {
                if (errCode.equals("noCharacter")) {
                    throw new CharacterNotFoundException(regionCode + "-" + charName + " not found in armory");
                } else {
                    throw new UnknownArmoryException("Error fetching " + regionCode + "-" + charName + ": " + errCode);
                }
            }

            Element elChar = xml.getChild("characterInfo", ns).getChild("character", ns);

            character = new PlayerCharacter();
            character.setName(elChar.getAttributeValue("name"));
            character.setPlayerClass(elChar.getAttribute("classId").getIntValue());
            character.setRace(elChar.getAttribute("raceId").getIntValue());
            character.setGender(elChar.getAttribute("genderId").getIntValue());
            character.setFaction(elChar.getAttribute("factionId").getIntValue());
            character.setLevel(elChar.getAttribute("level").getIntValue());
            character.setAchievementPoints(elChar.getAttribute("points").getIntValue());
            character.setGuildName(elChar.getAttributeValue("guildName"));
            character.setRealm(elChar.getAttributeValue("realm"));
            character.setTitle(elChar.getAttributeValue("suffix"));
            character.setTitleId(elChar.getAttribute("titleId").getIntValue());
            character.setBattlegroup(elChar.getAttributeValue("battleGroup"));

            //
            // Talent Specs
            //
            Element elTalentSpecs = xml.getChild("characterInfo", ns).getChild("characterTab", ns).getChild("talentSpecs", ns);
            List<Element> talentSpecs = elTalentSpecs.getChildren("talentSpec");
            for (Element spec : talentSpecs) {
                int group = spec.getAttribute("group").getIntValue();
                boolean isActive = spec.getAttributeValue("active") != null;

                TalentSpec talentSpec = new TalentSpec();
                talentSpec.setName(spec.getAttributeValue("prim"));
                talentSpec.setTreeOne(spec.getAttribute("treeOne").getIntValue());
                talentSpec.setTreeTwo(spec.getAttribute("treeTwo").getIntValue());
                talentSpec.setTreeThree(spec.getAttribute("treeThree").getIntValue());
                talentSpec.setActive(isActive);
                talentSpec.setNumber(group);
            }

            //
            // Professions
            //
            List<Profession> profs = new ArrayList<Profession>();
            Element elProfessions = xml.getChild("characterInfo", ns).getChild("characterTab", ns).getChild("professions", ns);
            List<Element> skills = elProfessions.getChildren("skill");
            for (Element skill : skills) {
                Profession prof = new Profession();
                prof.setId(skill.getAttribute("id").getIntValue());
                prof.setName(skill.getAttributeValue("name"));
                prof.setMax(skill.getAttribute("max").getIntValue());
                prof.setValue(skill.getAttribute("value").getIntValue());

                profs.add(prof);
            }
            character.setProfessions(profs);

        } catch (Exception ex) {
            LOG.error(ex.getMessage());
        }

        return character;
    }


    // -------------------------------------------------------- Private Methods


    private Element toXml(String s) throws Exception {
        SAXBuilder builder = new SAXBuilder();
        builder.setIgnoringElementContentWhitespace(true);
        org.jdom.Document doc = builder.build(new ByteArrayInputStream(s.getBytes(UTF8)));
        return doc.getRootElement();
    }


    public static void main(String... args) throws Exception {
        long start = System.currentTimeMillis();

        Armory armory = new JDOMArmory();

        String[] names = new String[] {"Gogan", "Kuramori", "Aozaru", "Haibane", "Asano", "Ikuya", "Orlandin", "Zol", "Zorthy", "Torchholder"};

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