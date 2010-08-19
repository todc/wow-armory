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

import com.todc.wgrarmory.model.PlayerCharacter;


/**
 * @author <a href="mailto:tim@timodonnell.com">Tim O'Donnell</a>
 */
public interface Armory {

    public void setProxy(String ip, int port);

    public Proxy getProxy();

    public PlayerCharacter fetchCharacter(String charName, String realmName, String regionCode);

}