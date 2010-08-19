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

import org.apache.commons.digester.Digester;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.todc.wgrarmory.model.PlayerCharacter;
import com.todc.wgrarmory.model.Profession;
import com.todc.wgrarmory.model.TalentSpec;


/**
 * Implementation of Armory using Apache Commons Digester to parse the
 * resulting XML.
 *
 * @author <a href="mailto:tim@timodonnell.com">Tim O'Donnell</a>
 */
public class DigesterArmory extends AbstractArmory {


    protected Logger LOG = LoggerFactory.getLogger(DigesterArmory.class);


    // -------------------------------------------------------------- Constants


    private Digester m_characterDigester = new Digester();

    private static final String UTF8 = "UTF-8";


    // ----------------------------------------------------- Instance Variables


    // ----------------------------------------------------------- Constructors


    public DigesterArmory() {
        super();

        addCharacterRules(m_characterDigester);
    }


    // ------------------------------------------------------ Getters / Setters


    // --------------------------------------------------------- Public Methods


    public PlayerCharacter fetchCharacter(String charName, String realmName, String regionCode) {
        try {
            String armoryUrl = config.getProperty("armory.url."+regionCode.toLowerCase());
            String rn = URLEncoder.encode(realmName, UTF8);
            String cn = URLEncoder.encode(charName, UTF8);

            String url = "http://" + armoryUrl + "/character-sheet.xml?r=" + rn + "&cn=" + cn + "&rhtml=n";

            String xml = this.httpGet(url);

            PlayerCharacter character = new PlayerCharacter();

            m_characterDigester.clear();
            m_characterDigester.push(character);
            m_characterDigester.parse(new StringReader(xml));

            return character;
        } catch (Exception ex) {
            LOG.error(ex.getMessage());
            ex.printStackTrace(System.err);
        }

        return null;
    }


    // -------------------------------------------------------- Private Methods


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
    }


    public static void main(String... args) throws Exception {
        long start = System.currentTimeMillis();
        
        Armory armory = new DigesterArmory();

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
