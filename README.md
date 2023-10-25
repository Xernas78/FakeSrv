# Fake Amethyst
An Amethyst version of a Fake Minecraft server that only appears in server list ping (SLP)

You just have to download the last release and execute the jar file like any other jar file

## server.properties

Then you can modify server.properties which has 4 properties:
- players_max
- players_online
- version_name
- version_protocol

version_protocol is used to determine the version of the server, you can find all the protocols at https://wiki.vg/Protocol_version_numbers

## MOTD

You can also modify the motd.json file which is the MOTD of the server !
It has a chat component format in JSON just like the chat message format at https://wiki.vg/Chat#Current_system_.28JSON_Chat.29

## Kick Reason

You can modify the kick message when someone tries to join the server if you want with the kick.json file.
The JSON format is the same as the motd.json file, simple right ?

## Favicon

You can also add the favicon of the server by adding a 64x64 icon.png file to your server directory.

## Special

You can add a program variable by adding "debug" at the end of your java command to execute the jar (java -jar AmethystFake.jar debug) to display the packets sent and received by the server

### Thanks to https://wiki.vg for all the resources that helped me make this project
