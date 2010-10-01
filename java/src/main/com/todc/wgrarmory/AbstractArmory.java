package com.todc.wgrarmory;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author <a href="mailto:tim@timodonnell.com">Tim O'Donnell</a>
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
    protected boolean fetchCharacterArenaTeams = true;
    protected boolean fetchCharacterBaseStats = true;

    protected int m_totalRequestCount = 0;
    protected int m_currentRequestCount = 0;

    private Proxy m_proxy;
    private String m_proxyUsername;
    private String m_proxyPassword;


    // ----------------------------------------------------------- Constructors


    public AbstractArmory() {
        try {
            config = new Properties();
            InputStream in = this.getClass().getClassLoader().getResourceAsStream("armory.properties");
            if (in == null) {
                LOG.error("Can't find armory.properties!");
            }
            config.load(in);
        } catch (IOException ex) {
            LOG.warn("armory.properties was not found in the classpath", ex);
        }
    }


    // --------------------------------------------------------- Public Methods


    public void setProxy(String ip, int port, String username, String password) {
        m_proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ip, port));
        m_proxyUsername = username;
        m_proxyPassword = password;
    }

    public Proxy getProxy() {
        return m_proxy;
    }

    public void clearProxy() {
        m_proxy = null;
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

    public boolean isFetchCharacterArenaTeams() {
        return fetchCharacterArenaTeams;
    }

    public void setFetchCharacterArenaTeams(boolean fetchCharacterArenaTeams) {
        this.fetchCharacterArenaTeams = fetchCharacterArenaTeams;
    }

    public boolean isFetchCharacterBaseStats() {
        return fetchCharacterBaseStats;
    }

    public void setFetchCharacterBaseStats(boolean fetchCharacterBaseStats) {
        this.fetchCharacterBaseStats = fetchCharacterBaseStats;
    }

    public int getCurrentRequestCount() {
        return m_currentRequestCount;
    }

    public void resetCurrentRequestCount() {
        m_currentRequestCount = 0;
    }

    public int getTotalRequestCount() {
        return m_totalRequestCount;
    }

    public void resetTotalRequestCount() {
        m_totalRequestCount = 0;
    }


    // ------------------------------------------------------ Protected Methods


    protected String httpGet(String sURL) throws Exception {
        LOG.debug("Requesting URL (" + m_currentRequestCount + "): " + sURL);

        URL url = new URL(sURL);
        HttpURLConnection conn;
        if (this.getProxy() == null) {
            conn = (HttpURLConnection)url.openConnection();
        } else {
            // Alternatively set the proxy username/password using:
            Authenticator.setDefault(new Authenticator() {
              protected PasswordAuthentication getPasswordAuthentication() {
                return new
                   PasswordAuthentication(m_proxyUsername,m_proxyPassword.toCharArray());
            }});
            
            conn = (HttpURLConnection)url.openConnection(this.getProxy());
        }

        conn.setRequestProperty("Content-Type", "application/xml;charset=UTF-8");
        conn.setInstanceFollowRedirects(false);
        conn.setConnectTimeout(10000);
        //conn.setReadTimeout(10 * 1000);

        m_totalRequestCount++;
        m_currentRequestCount++;

        int attempts = 0;
        boolean success = false;

        while (!success && attempts < 3) {
            try {
                conn.connect();
                success = true;
            } catch (java.net.ConnectException ex) {
                LOG.error("Connection timed out requesting " + sURL + " (proxy " + this.getProxy() + ")", ex);
            }
            
            attempts++;
        }

        int responseCode = conn.getResponseCode();

        //
        // OK response. Doesn't mean total success, but at least something
        // we can parse.
        //
        if (responseCode == HttpURLConnection.HTTP_OK)
        {
            StringBuffer responseBody = new StringBuffer();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

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
                    throw new UnknownArmoryException("Internal Server Error (" + responseCode + ") from " + sURL);

                //
                // 302 redirect means the armory is down for maintenace 
                //
                case HttpURLConnection.HTTP_MOVED_TEMP:
                    throw new DownForMaintenanceException("Armory is down for maintenance");

                //
                // Anything else that can go wrong
                //
                default:
                    throw new UnknownArmoryException("Unknown armory error (" + responseCode + ") from " + sURL);
            }

        }
    }


    protected String getArmoryHost(String regionCode) {
        return config.getProperty(CONFIG_URL_KEY + "." + regionCode.toLowerCase());
    }
}
