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

import com.todc.wgrarmory.model.Achievement;
import com.todc.wgrarmory.model.AchievementCategory;
import com.todc.wgrarmory.model.PlayerCharacter;


/**
 * @author <a href="mailto:tim@timodonnell.com">Tim O'Donnell</a>
 */
public interface Armory {

    public Proxy getProxy();
    public void setProxy(String ip, int port);

    public boolean isFetchAchievementTitle();
    public void setFetchAchievementTitle(boolean val);
    public boolean isFetchAchievementDescription();
    public void setFetchAchievementDescription(boolean val);
    public boolean isFetchAchievementCriteria();
    public void setFetchAchievementCriteria(boolean val);
    public boolean isFetchSubAchievements();
    public void setFetchSubAchievements(boolean val);


    public PlayerCharacter fetchCharacter(String charName, String realmName, String regionCode)
            throws Exception;

    public List<AchievementCategory> fetchCharacterAchievements(String charName, String realmName, String regionCode, int category)
            throws Exception;

    public List<AchievementCategory> fetchCharacterAchievements(String charName, String realmName, String regionCode, int category, int[] subCategories)
            throws Exception;

}
