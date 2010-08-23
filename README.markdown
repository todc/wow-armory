wgr-armory
==========

#### Library for parsing the World of Warcraft Armory ####

**wgr-armory** is a full-featured API for accessing Blizzard's World of Warcraft Armory. It parses the resulting XML and converts it into plain old java objects. All regional armories are supported, including Americas, Europe, Korea, China, and Taiwan. Methods exist to fetch the following information:

* Guild roster data
* Detailed character data, including:
    * Base stats
    * Talent specs
    * Professions
    * Reputations
    * Statistics
    * Items
    * Arena Teams
* Customizable character activity feed
* Sortable and paginated Arena Ladders
* Detailed Arena Team data, including:
    * Arena Team characters
    * Arena Team match history
    * Arena Team opponent history
* Individual arena match stats
* Comprehensive item search. All filters supported.

The first version of this library is written in Java. The goal is to eventually port it to several other languages, including PHP, Ruby, and Python. 


Requirements
------------

* JDK 1.5
* JDOM 1.1 (included) - http://www.jdom.org
* SLF4J 1.6 (included) - http://www.slf4j.org


Building
--------

Tagged releases can be downloaded from:

<http://github.com/todc/wgr-armory/downloads>

You can also download the source and compile it yourself:

    $ git://github.com/todc/wgr-armory.git
    $ cd wgr-armory/java
    $ ant


Usage
-----

Using wgr-armory in your project is a simple matter of copying the required jar files to your classpath. In addition to the wgr-armory.jar file, you'll also need to include the JDOM and SLF4J API libraries, both of which are included in the distribution.

The main class you'll interact with is the `Armory` class. Simply instantiate it and call any of its public methods to interact with the WoW Armory.

For example, to obtain basic character data:

    Armory armory = new DefaultArmoryImpl();
    PlayerCharacter character = armory.fetchCharacter("Gogan", "Dawnbringer", "US");

Each method of the Armory class corresponds to a single HTTP request to the Armory.

Some methods, such as `fetchCharacter` and `fetchCharacterAchievements` can return a lot of data, some of which may not be necessary or desired. All of this data is returned from the Armory, however, you can choose to omit some of this data from the resulting java objects. This is helpful if you don't need some of this data and want to save on memory.

For example, you can limit exactly what fields of the `PlayerCharacter` object are populated:

    Armory armory = new DefaultArmoryImpl();
    armory.setFetchCharacterTalents(false);
    armory.setFetchCharacterProfessions(false);
    armory.setFetchCharacterItems(false);
    armory.setFetchCharacterArenaTeams(false);
    armory.setFetchCharacterBaseStats(false);

The resulting `PlayerCharacter` object will only contain high-level info such as name, race, gender, class, level, guild, etc.

Another nice feature includes being able limit which characters are returned when fetching a guild's roster. For example:

    Armory armory = new DefaultArmoryImpl();
    armory.setFetchMinLevel(80);
    Guild guild = armory.fetchGuild("Gentlemen of Leisure", "Dawnbringer", "US");

This will return only characters of level 80+ in the guild roster.

Any of the Armory methods can be requested through a custom proxy, which can be specified at any time:

    Armory armory = new DefaultArmoryImpl();
    armory.setProxy("192.168.0.1", 8000);

Additional examples of most of the libraries features can be found in the `examples` directory of the source tree at:

<http://github.com/todc/wgr-armory/tree/master/java/src/examples/>


Mailing list
------------

Please join the mailing list if you're interested in using or developing the software: <http://groups.google.com/group/wgr-armory>


Issue tracker
-------------

Found a bug? I'd like to fix it. Please report it, along with what you tried to do, what you expected, and what actually happened -- or better yet, provide a patch: <http://github.com/todc/wgr-armory/issues>


Contributing
------------

Please contribute fixes and features. You can find issues to work on in the [Issue tracker](http://github.com/todc/wgr-armory/issues). Please fork the source code, make your changes and submit a Github pull request. By submitting a patch, you agree that your software can be released under the same license as this software.


License
-------

This software is provided under an MIT open source license, read the `LICENSE.txt` file for details.


Copyright
---------

Copyright (c) 2010 Tim O'Donnell.