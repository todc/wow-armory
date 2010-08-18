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
import java.util.Properties;
import java.util.TimeZone;

import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.todc.wgrarmory.model.PlayerCharacter;


/**
 * Convenience methods for interacting with and parsing XML responses from the
 * Blizzard WoW Armory.
 *
 * @author <a href="mailto:tim@timodonnell.com">Tim O'Donnell</a>
 */
public class Armory {


    Logger LOG = LoggerFactory.getLogger(Armory.class);


    // -------------------------------------------------------------- Constants


    private Properties m_config = null;

    private static final String UTF8 = "UTF-8";


    // ----------------------------------------------------- Instance Variables


    private SimpleDateFormat m_sdfShort = new SimpleDateFormat("yyyy-MM-ddZ");
    private SimpleDateFormat m_sdfLong = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

    private Proxy m_proxy = null;


    // ----------------------------------------------------------- Constructors


    public Armory() {
        m_sdfShort.setTimeZone(TimeZone.getTimeZone("GMT"));
        m_sdfLong.setTimeZone(TimeZone.getTimeZone("GMT"));

        try {
            m_config = new Properties();
            URL url = ClassLoader.getSystemResource("armory.properties");
            m_config.load(url.openStream());
        } catch (IOException ex) {
            LOG.warn("armory.properties was not found in the classpath", ex);
        }
    }


    // ------------------------------------------------------ Getters / Setters


    public void setProxy(String ip, int port) {
        m_proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ip, port));
    }


    // --------------------------------------------------------- Public Methods


    public PlayerCharacter fetchCharacter(String charName, String realmName, String regionCode) {
        PlayerCharacter character = null;

        try {
            String armoryUrl = m_config.getProperty("armory.url."+regionCode.toLowerCase());
            String rn = URLEncoder.encode(realmName, UTF8);
            String cn = URLEncoder.encode(charName, UTF8);

            String url = "http://" + armoryUrl + "/character-sheet.xml?r=" + rn + "&cn=" + cn + "&rhtml=n";

            Element xml = httpGet(url);

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
        } catch (Exception ex) {
            LOG.error(ex.getMessage());
        }

        return character;
    }


    // -------------------------------------------------------- Private Methods


    private Element httpGet(String sURL) throws Exception {
        URL url = new URL(sURL);
        HttpURLConnection conn;
        if (m_proxy == null) {
            conn = (HttpURLConnection)url.openConnection();
        } else {
            conn = (HttpURLConnection)url.openConnection(m_proxy);
        }

        conn.connect();

        int responseCode = conn.getResponseCode();

        //
        // OK response. Doesn't mean total success, but at least something
        // we can parse.
        //
        if (responseCode == HttpURLConnection.HTTP_OK)
        {
            StringBuffer responseBody = new StringBuffer();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line;
            while ((line = in.readLine()) != null){
               responseBody.append(line);
            }

            conn.disconnect();

            return toXml(responseBody.toString());
        }
        else
        {
            conn.disconnect();

            switch (responseCode)
            {
                //
                // Blizzard throttles client requests to about 1.5/second. You can
                // burst upwards of 60 requests in 45 seconds, but after that Blizz
                // will shut you out with a 503 error.
                //
                case HttpURLConnection.HTTP_UNAVAILABLE:
                    throw new TooManyRequestsException("Too many armory requests made recently. HTTP Code " + responseCode);

                //
                // Pretty sure Blizz assigns this some other meaning, but I have
                // to double check to figure out the appropriate exception to throw.
                //
                case HttpURLConnection.HTTP_INTERNAL_ERROR:
                    throw new UnknownArmoryException("Internal Server Error. HTTP Code " + responseCode);

                //
                // Anything else that can go wrong
                //
                default:
                    throw new UnknownArmoryException("Unknown armory error. HTTP Code " + responseCode);
            }

        }
    }


    private Element toXml(String s) throws Exception {
        SAXBuilder builder = new SAXBuilder();
        builder.setIgnoringElementContentWhitespace(true);
        org.jdom.Document doc = builder.build(new ByteArrayInputStream(s.getBytes(UTF8)));
        return doc.getRootElement();
    }


    public static void main(String... args) throws Exception {
        Armory armory = new Armory();

        PlayerCharacter character1 = armory.fetchCharacter("Gogan", "Dawnbringer", "US");
        System.out.println(character1);

        PlayerCharacter character2 = armory.fetchCharacter("Kuramori", "Dawnbringer", "US");
        System.out.println(character2);

        PlayerCharacter character3 = armory.fetchCharacter("Aozaru", "Dawnbringer", "US");
        System.out.println(character3);


        /*
        String s = "2009-12-13T02:47:00-06:00";
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        String preprocessed = s.replaceAll("([+-])(\\d\\d):(\\d\\d)$", "$1$2$3");
        System.out.println("s = "+preprocessed);
        sdf.parse(preprocessed);
        */
    }

}
