# wgr-armory #

#### Library for parsing the World of Warcraft Armory ####

*wgr-armory* is a full-featured API for accessing Blizzard's World of Warcraft Armory. It parses the resulting XML and
converts it into plain old java objects. All regional armories are supported, including Americas, Europe, Korea, China,
and Taiwan. Methods exist to fetch the following information:

* Guild roster data
* Detailed character data, including:
** Base stats
** Talent specs
** Professions
** Reputations
** Statistics
** Items
** Arena Teams
* Customizable character feed
* Filterable Arena Ladders
* Detailed Arena Team data, including:
** Arena Team characters
** Arena Team match history
** Arena Team opponent history
* Individual arena match stats

The first version of this library is written in Java. The goal is to eventually port it to several other languages,
including PHP, Ruby, and Python. 


### Requirements ###

* JDK 1.5
* JDOM 1.1 (included)
* SLF4J 1.6 (included)


### Building wgr-armory ###

Pre-compiled binaries can be downloaded from:

http://github.com/todc/wgr-armory/downloads

You can also download the source and compile it yourself:

    $ git://github.com/todc/wgr-armory.git
    $ cd wgr-armory/java
    $ ant

