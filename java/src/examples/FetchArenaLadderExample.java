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
import com.todc.wgrarmory.model.*;


/**
 * @author <a href="tim@timodonnell.com">Tim O'Donnell</a>
 */
public class FetchArenaLadderExample {

    public static void main(String... args) throws Exception {

        Armory armory = new DefaultArmoryImpl();

        // --------------------------------------------------------------------
        //
        // Fetch Arena Teams
        //
        // --------------------------------------------------------------------

        // Define a filter for the request. At a minimum, you need to specify
        // which ladder you want, e.g. 2v2, 3v3, 5v5.
        // By default, results will be returned sorted by rank.
        //
        // In this example, we'll search for only teams on Blackrock, sorted
        // by team name, and skip to the second page of results
        ArenaFilter filter = new ArenaFilter(ArenaFilter.LADDER_3v3);
        filter.setRealm("Blackrock");
        filter.setPage(2);
        filter.setSortBy(ArenaFilter.SORT_TEAM_NAME);

        // send the request to the armory
        List<ArenaTeam> teams = armory.fetchArenaLadder("US", "Bloodlust", filter);

        // print the results
        System.out.println("Results");
        System.out.println("-------");

        for (ArenaTeam team : teams) {
            System.out.println(
                team.getRank() + ") " + team.getName() +
                " (" + team.getSeasonGamesWon() + "-" + team.getSeasonGamesLost() + ") - " +
                "Rating: " + team.getRating()
            );
        }


        // --------------------------------------------------------------------
        //
        // Fetch Team Members
        //
        // --------------------------------------------------------------------

        // get the first team and display the members
        ArenaTeam team = teams.get(0);

        // request the member list from the armory
        List<ArenaTeamMember> members = armory.fetchArenaTeamMembers(
            team.getTeamSize(),
            team.getRegionCode(),
            team.getRealm(),
            team.getName()
        );

        System.out.println("");
        System.out.println("Members of team: " + team.getName());

        for (ArenaTeamMember member : members) {
            System.out.println("   " +
                member.getName() + " (" + PlayerCharacter.getClassName( member.getClassId() ) + ") - " +
                member.getSeasonGamesWon() + " wins, " + member.getSeasonGamesLost() + " losses"
            );
        }


        // --------------------------------------------------------------------
        //
        // Fetch Team Match History
        //
        // --------------------------------------------------------------------

        List<ArenaMatch> matches = armory.fetchArenaTeamMatchHistory(
            team.getTeamSize(),
            team.getRegionCode(),
            team.getRealm(),
            team.getName()
        );

        System.out.println("");
        System.out.println("First 10 Matches");

        for (int i=0; i<10; i++) {
            ArenaMatch match = matches.get(i);

            System.out.println("   " + match.getDate() + " " + match.getOtherTeamName() + " (" + match.getNewRating() + ")");
        }


        // --------------------------------------------------------------------
        //
        // Fetch Team Opponent History
        //
        // --------------------------------------------------------------------

        List<ArenaOpposingTeam> opponents = armory.fetchArenaTeamOpponentHistory(
            team.getTeamSize(),
            team.getRegionCode(),
            team.getRealm(),
            team.getName()
        );

        System.out.println("");
        System.out.println("Top Opponents");

        for (int i=0; i<10; i++) {
            ArenaOpposingTeam opp = opponents.get(i);

            System.out.println("   " + opp.getTeamName() + " (" + opp.getWins() + "W - " + opp.getLosses() + "L)");
        }

    }

}
