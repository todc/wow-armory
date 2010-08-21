/*
 * $Id$
 *
 * Copyright (c)2010, Tim O'Donnell. All Rights Reserved.
 *
 * This code may not be used or reproduced in part or in whole without
 * express written permission of Tim O'Donnell.
 */
package com.todc.wgrarmory;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author <a href="mailto:odonnellt@gmail.com">Tim O'Donnell</a>
 */
public abstract class AbstractArmory implements Armory {


    private Logger LOG = LoggerFactory.getLogger(AbstractArmory.class);

    private static final String CONFIG_URL_KEY = "armory.url";


    // ----------------------------------------------------- Instance Variables


    protected Properties config = null;

    protected int fetchMinLevel = 1;

    protected boolean fetchAchievementTitle = true;
    protected boolean fetchAchievementDescription = true;
    protected boolean fetchAchievementCriteria = true;
    protected boolean fetchSubAchievements = true;

    protected boolean fetchCharacterTalents = true;
    protected boolean fetchCharacterProfessions = true;
    protected boolean fetchCharacterItems = true;

    private Proxy m_proxy;


    // ----------------------------------------------------------- Constructors


    public AbstractArmory() {
        try {
            config = new Properties();
            URL url = ClassLoader.getSystemResource("armory.properties");
            config.load(url.openStream());
        } catch (IOException ex) {
            LOG.warn("armory.properties was not found in the classpath", ex);
        }
    }


    // --------------------------------------------------------- Public Methods


    public void setProxy(String ip, int port) {
        m_proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ip, port));
    }

    public Proxy getProxy() {
        return m_proxy;
    }

    public int getFetchMinLevel() {
        return fetchMinLevel;
    }

    public void setFetchMinLevel(int fetchMinLevel) {
        this.fetchMinLevel = fetchMinLevel;
    }

    public boolean isFetchAchievementTitle() {
        return fetchAchievementTitle;
    }

    public void setFetchAchievementTitle(boolean fetchAchievementTitle) {
        this.fetchAchievementTitle = fetchAchievementTitle;
    }

    public boolean isFetchAchievementDescription() {
        return fetchAchievementDescription;
    }

    public void setFetchAchievementDescription(boolean fetchAchievementDescriptions) {
        this.fetchAchievementDescription = fetchAchievementDescriptions;
    }

    public boolean isFetchAchievementCriteria() {
        return fetchAchievementCriteria;
    }

    public void setFetchAchievementCriteria(boolean fetchAchievementCriteria) {
        this.fetchAchievementCriteria = fetchAchievementCriteria;
    }

    public boolean isFetchSubAchievements() {
        return fetchSubAchievements;
    }

    public void setFetchSubAchievements(boolean fetchSubAchievements) {
        this.fetchSubAchievements = fetchSubAchievements;
    }

    public boolean isFetchCharacterTalents() {
        return fetchCharacterTalents;
    }

    public void setFetchCharacterTalents(boolean fetchCharacterTalents) {
        this.fetchCharacterTalents = fetchCharacterTalents;
    }

    public boolean isFetchCharacterProfessions() {
        return fetchCharacterProfessions;
    }

    public void setFetchCharacterProfessions(boolean fetchCharacterProfessions) {
        this.fetchCharacterProfessions = fetchCharacterProfessions;
    }

    public boolean isFetchCharacterItems() {
        return fetchCharacterItems;
    }

    public void setFetchCharacterItems(boolean fetchCharacterItems) {
        this.fetchCharacterItems = fetchCharacterItems;
    }

    
    // ------------------------------------------------------ Protected Methods


    protected String httpGet(String sURL) throws Exception {
        URL url = new URL(sURL);
        HttpURLConnection conn;
        if (this.getProxy() == null) {
            conn = (HttpURLConnection)url.openConnection();
        } else {
            conn = (HttpURLConnection)url.openConnection(this.getProxy());
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

            return responseBody.toString();
        }
        else
        {
            conn.disconnect();

            switch (responseCode)
            {
                //
                // HTTP 503
                // Blizzard throttles client requests to about 1.5/second. You can
                // burst upwards of 60 requests in 45 seconds, but after that Blizz
                // will shut you out with a 503 error.
                //
                case HttpURLConnection.HTTP_UNAVAILABLE:
                    throw new TooManyRequestsException("Too many armory requests made recently. HTTP Code " + responseCode);

                //
                // HTTP 500
                // This will be returned when searching for a guild that doesn't
                // exist. Probably for other errors too.
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


    protected String getArmoryHost(String regionCode) {
        return config.getProperty(CONFIG_URL_KEY + "." + regionCode.toLowerCase());
    }
}
