/*
 * $Id$
 *
 * Copyright (c)2010, Tim O'Donnell. All Rights Reserved.
 *
 * This code may not be used or reproduced in part or in whole without
 * express written permission of Tim O'Donnell.
 */


import java.util.List;
import java.util.Map;

import com.todc.wgrarmory.Armory;
import com.todc.wgrarmory.DefaultArmoryImpl;
import com.todc.wgrarmory.model.*;


/**
 * @author <a href="tim@timodonnell.com">Tim O'Donnell</a>
 */
public class FetchCharacterExample {


    public static final String CHAR_NAME   = "Gogan";
    public static final String REALM_NAME  = "Dawnbringer";
    public static final String REGION_CODE = "US";


    public static void main(String... args) throws Exception {

        Armory armory = new DefaultArmoryImpl();

        // --------------------------------------------------------------------
        //
        // Fetch Character Sheet info
        //
        // --------------------------------------------------------------------
        PlayerCharacter player = armory.fetchCharacter(CHAR_NAME, REALM_NAME, REGION_CODE);

        System.out.println("Character Info");
        System.out.println("--------------");
        System.out.println("Name:        " + player.getName() + player.getTitle());
        System.out.println("Guild:       " + player.getGuildName());
        System.out.println("Level:       " + player.getLevel());
        System.out.println("Faction:     " + PlayerCharacter.getFactionName( player.getFaction() ));
        System.out.println("Gender:      " + PlayerCharacter.getGenderName( player.getGender() ));
        System.out.println("Race:        " + PlayerCharacter.getRaceName( player.getRace() ));
        System.out.println("Class:       " + PlayerCharacter.getClassName( player.getPlayerClass() ));
        System.out.println("Realm:       " + player.getRealm());
        System.out.println("Region:      " + player.getRegion());
        System.out.println("BattleGroup: " + player.getBattlegroup());
        System.out.println("Achiev. Pts: " + player.getAchievementPoints());


        System.out.println("");
        System.out.println("Base Stats");
        System.out.println("----------");
        System.out.println(player.getBaseStats());

        System.out.println("");
        System.out.println("Talent Specs");
        System.out.println("------------");

        TalentSpec primarySpec = player.getPrimaryTalentSpec();
        TalentSpec secondarySpec = player.getSecondaryTalentSpec();

        System.out.println("Primary:   " + primarySpec.getName() + " (" + primarySpec.getShortBuild() + ")");
        System.out.println("Secondary: " + secondarySpec.getName() + " (" + secondarySpec.getShortBuild() + ")");


        System.out.println("");
        System.out.println("Active Glyphs");
        System.out.println("-------------");

        for (Glyph glyph : primarySpec.getGlyphs()) {
            System.out.println( Glyph.getTypeName(glyph.getType()) + " - " + glyph.getName() );
        }


        System.out.println("");
        System.out.println("Primary Professions");
        System.out.println("-------------------");

        List<Profession> professions = player.getProfessions();
        for (Profession prof : professions) {
            System.out.println(prof.getValue() + " " + prof.getName());
        }


        System.out.println("");
        System.out.println("Secondary Professions");
        System.out.println("---------------------");

        List<Profession> secProfessions = player.getSecondaryProfessions();
        for (Profession prof : secProfessions) {
            System.out.println(prof.getValue() + " " + prof.getName());
        }


        System.out.println("");
        System.out.println("Arena Teams");
        System.out.println("-----------");

        List<ArenaTeam> arenaTeams = player.getArenaTeams();
        for (ArenaTeam team : arenaTeams) {
            System.out.println(team.getName() + " / Rating: " + team.getRating() + " / " + team.getSeasonGamesWon() + "W - " + team.getSeasonGamesLost() + "L");

            List<ArenaTeamMember> members = team.getTeamMembers();
            for (ArenaTeamMember member : members) {
                System.out.println("   " + member.getName() + " - " + PlayerCharacter.getClassName( member.getClassId() ));
            }
        }


        System.out.println("");
        System.out.println("Items");
        System.out.println("-----");

        List<Item> items = player.getItems();
        for (Item item : items) {
            // slot numbers in the armory are 0-based, but in-game they're 1-based
            System.out.println("Slot " + (item.getSlot()+1) + " - " + item.getName());
        }


        // --------------------------------------------------------------------
        //
        // Fetch Character Achievements
        //
        // --------------------------------------------------------------------
        armory.setFetchAchievementDescription(false);  // don't fetch achievement descriptions,
        armory.setFetchAchievementCriteria(false);     // or criteria
        armory.setFetchSubAchievements(false);         // or sub-achievements

        List<AchievementCategory> achievCats = armory.fetchCharacterAchievements(
            CHAR_NAME, REALM_NAME, REGION_CODE,
            AchievementCategory.ID_DUNGEONS_AND_RAIDS,
            new int[] {AchievementCategory.ID_FALL_OF_THE_LICH_KING_10, AchievementCategory.ID_CALL_OF_THE_CRUSADE_10}
        );

        System.out.println("");
        System.out.println("Achievements");
        System.out.println("------------");

        for (AchievementCategory cat : achievCats) {
            System.out.println(cat.getName() + ":");

            List<Achievement> achievements = cat.getAchievements();
            for (Achievement ach : achievements) {
                if (ach.getCompleted() != null) {
                    System.out.print("   " + ach.getCompleted());
                }

                System.out.println(" - " + ach.getTitle());
            }
        }


        // --------------------------------------------------------------------
        //
        // Fetch Character Reputations
        //
        // --------------------------------------------------------------------
        List<Faction> factions = armory.fetchCharacterReputation(CHAR_NAME, REALM_NAME, REGION_CODE);

        System.out.println("");
        System.out.println("Reputations");
        System.out.println("-----------");
        for (Faction f : factions) {
            System.out.println(f.getReputation() + " - " + f.getName());
        }


        // --------------------------------------------------------------------
        //
        // Fetch Character Statistics
        //
        // specifically for:
        //   - Dungeons & Raids
        //   -- Falls of the Lich King
        //   -- Call of the Crusade
        //
        // --------------------------------------------------------------------
        int statCategory = Statistic.CAT_DUNGEONS; // Dungeons & Raids
        String[] statSubCategories = new String[] {"Fall of the Lich King", "Call of the Crusade"};
        Map<String,List<Statistic>> categories = armory.fetchCharacterStatistics(CHAR_NAME, REALM_NAME, REGION_CODE, statCategory, statSubCategories);

        System.out.println("");
        System.out.println("Statistics");
        System.out.println("----------");

        for (Map.Entry<String,List<Statistic>> entry : categories.entrySet()) {
            System.out.println(entry.getKey() + ":");
            for (Statistic stat : entry.getValue()) {
                System.out.print("   " + stat.getName() + " - " + stat.getQuantity());
                if (stat.getHighest() != null) {
                    System.out.print(" (Highest: " + stat.getHighest() + ")");
                }
                System.out.println("");
            }

            System.out.println("");
        }

    }

}
