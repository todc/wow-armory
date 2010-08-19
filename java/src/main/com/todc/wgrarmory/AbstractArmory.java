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
import java.text.SimpleDateFormat;
import java.util.Properties;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author <a href="mailto:odonnellt@gmail.com">Tim O'Donnell</a>
 */
public abstract class AbstractArmory implements Armory {


    private Logger LOG = LoggerFactory.getLogger(AbstractArmory.class);

    protected Properties config = null;


    // ----------------------------------------------------- Instance Variables


    private Proxy m_proxy;

    private SimpleDateFormat m_sdfShort = new SimpleDateFormat("yyyy-MM-ddZ");
    private SimpleDateFormat m_sdfLong = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");


    // ----------------------------------------------------------- Constructors


    public AbstractArmory() {
        m_sdfShort.setTimeZone(TimeZone.getTimeZone("GMT"));
        m_sdfLong.setTimeZone(TimeZone.getTimeZone("GMT"));

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
}
