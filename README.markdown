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
* JDOM 1.1 (included)
* SLF4J 1.6 (included)


Building wgr-armory
-------------------

Pre-compiled binaries can be downloaded from:

<http://github.com/todc/wgr-armory/downloads>

You can also download the source and compile it yourself:

    $ git://github.com/todc/wgr-armory.git
    $ cd wgr-armory/java
    $ ant


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