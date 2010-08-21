/*
 * $Id$
 *
 * Copyright (c)2010, Tim O'Donnell. All Rights Reserved.
 *
 * This code may not be used or reproduced in part or in whole without
 * express written permission of Tim O'Donnell.
 */
package com.todc.wgrarmory;


import java.net.Proxy;
import java.util.List;
import java.util.Map;

import com.todc.wgrarmory.model.*;


/**
 * @author <a href="mailto:tim@timodonnell.com">Tim O'Donnell</a>
 */
public interface Armory {

    public Proxy getProxy();
    public void setProxy(String ip, int port);

    public int getFetchMinLevel();
    public void setFetchMinLevel(int val);

    public boolean isFetchAchievementTitle();
    public void setFetchAchievementTitle(boolean val);
    public boolean isFetchAchievementDescription();
    public void setFetchAchievementDescription(boolean val);
    public boolean isFetchAchievementCriteria();
    public void setFetchAchievementCriteria(boolean val);
    public boolean isFetchSubAchievements();
    public void setFetchSubAchievements(boolean val);

    public boolean isFetchCharacterTalents();
    public void setFetchCharacterTalents(boolean val);
    public boolean isFetchCharacterProfessions();
    public void setFetchCharacterProfessions(boolean val);
    public boolean isFetchCharacterItems();
    public void setFetchCharacterItems(boolean val);


    public Guild fetchGuild(String guildName, String realmName, String regionCode)
            throws Exception;

    public PlayerCharacter fetchCharacter(String charName, String realmName, String regionCode)
            throws Exception;

    public List<AchievementCategory> fetchCharacterAchievements(String charName, String realmName, String regionCode, int category)
            throws Exception;

    public List<AchievementCategory> fetchCharacterAchievements(String charName, String realmName, String regionCode, int category, int[] subCategories)
            throws Exception;

    public List<Faction> fetchCharacterReputation(String charName, String realmName, String regionCode)
            throws Exception;

    public List<TalentSpec> fetchCharacterTalents(String charName, String realmName, String regionCode)
            throws Exception;

    public Map<String,List<Statistic>> fetchCharacterStatistics(String charName, String realmName, String regionCode, int category)
            throws Exception;

    public Map<String,List<Statistic>> fetchCharacterStatistics(String charName, String realmName, String regionCode, int category, String[] subCategories)
            throws Exception;

}
