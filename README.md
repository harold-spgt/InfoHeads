# Infoheads-mvn
Repository / SourceCode for the plugin Infoheads


# What is it
This is a plugin in which when you right click a block (which you configure) it displays a chosen message & command!
The plugin does this using the Conversation API and listeners
Check the spigot page for more info

# What Version then?
This plugin was ORIGINALLY coded using the 1.13 API but then was switched to 1.8.8. The version is compatible with versions 1.8-1.14. (1.8 has missing features - inventory cache).

# Tutorial
How to install:
1) Install the plugin with Vault (For Permissions!)
2) Start your server
3) Plugin Installed!

How to use:
1) Type /infoheads
2) You will be put through a conversation and asked a variety of questions
3) Answer the questions.. lol
4) Done!
5) Simply right click the block to use the plugin

# Developers
Now, on to you.

READ THE LICENSE!

You do not have to include reference to my name in a project you may make however, it's muchly appreciated!
After all, this is free code so some credit would be nice ey?

How this works - the plugin uses maven to compile and uses reflections to determine the version. The main area is Plugin-IH. You can see the usage of reflection in the InfoHeads class (main class). Version 1.8 = V1_8 Versions 1.9-1.12.2 = legacy and Version 1.13-1.14 = V1_13 <-- that's odd I know.

Anyway, Have fun reading throug my code!
