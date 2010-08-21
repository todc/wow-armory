/*
 * $Id$
 *
 * Copyright (c)2010, Tim O'Donnell. All Rights Reserved.
 *
 * This code may not be used or reproduced in part or in whole without
 * express written permission of Tim O'Donnell.
 */


import java.util.List;

import com.todc.wgrarmory.Armory;
import com.todc.wgrarmory.DefaultArmoryImpl;
import com.todc.wgrarmory.model.Guild;
import com.todc.wgrarmory.model.PlayerCharacter;


/**
 * @author <a href="tim@timodonnell.com">Tim O'Donnell</a>
 */
public class FetchGuildExample {

    public static void main(String... args) throws Exception {

        Armory armory = new DefaultArmoryImpl();

        // only return level 80 characters
        armory.setFetchMinLevel(80);

        // send the request to the armory
        Guild guild = armory.fetchGuild("Gentlemen of Leisure", "Dawnbringer", "US");

        // display result
        System.out.println("Guild Info");
        System.out.println("----------");
        System.out.println("Name:        " + guild.getName());
        System.out.println("Faction:     " + Guild.getFactionName( guild.getFaction() ));
        System.out.println("Realm:       " + guild.getRealmName());
        System.out.println("Region:      " + guild.getRegionCode());
        System.out.println("BattleGroup: " + guild.getBattlegroup());

        System.out.println("");
        System.out.println("Guild Roster");
        System.out.println("------------");

        // iterate through guild roster and print each character
        List<PlayerCharacter> roster = guild.getRoster();
        for (PlayerCharacter player : roster) {
            System.out.println(
                player.getName() + " - " +
                "Level " + player.getLevel() + " " +
                PlayerCharacter.getGenderName( player.getGender() ) + " " +
                PlayerCharacter.getRaceName( player.getRace() ) + " " +
                PlayerCharacter.getClassName( player.getPlayerClass() )
            );
        }

    }

}
