# MutualDemise
MutualDemise is a plugin intended for use in server events or fun amongst friends. The plugin was made customizable so it can be applied differently. For example, you could have a challenge where one player tries to die and the others try to stop them, the consequence of them dying being everyone else dies. Or, you could have a special challenge added to a survival playthrough where if one person dies, someone random dies (or everyone dies!). Have fun with it!

## Installation
1. Download the latest jar from the releases tab
2. Add the jar to your plugins folder
3. Restart & don't die!

## config.yml
The config.yml file was designed to be as simple as possible. If `everyone_dies` is true, if one player dies, everyone dies. If false, the amount of players set below will die. 
```yaml
everyone_dies: false # Set true if everyone should die if someone dies
random_kill_count: 1 # Set how many players should die if everyone_dies is false
enabled_worlds: # Worlds where if a player die, others die
  - "world"
  - "world_nether"
  - "world_the_end"
immune_players: # UUIDs of players who are immune to being killed
```

## Commands
| Command                       | Permission          | Description             |
|-------------------------------|---------------------|-------------------------|
| /mutualdemise reload          | mutualdemise.reload | Reloads config.yml      |
| /mutualdemise add <player>    | mutualdemise.add    | Grant player immunity   |
| /mutualdemise remove <player> | mutualdemise.remove | Revokes player immunity |

Alias is `/md`

## Update & Issues
I will update this plugin on request with any feature, just create an issue and it will be done! I'll also fix any bugs or issues that come up with the plugin. Just let me know!