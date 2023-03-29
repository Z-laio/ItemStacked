# ItemStacked

Creating custom items can be a pain in development, lets make it a little easier.

# Commands
**Permission for all these commands:** ``itemstacked.create``

``/is reload``
- Reloads the plugin configuration

``/is lore [set|insert|remove]``
- Alters the lines of lore on a held item

``/is data [pdc|custom_model_data] [set|remove]``
- Modifies the data of a held item

``/is setdisplayname``
- Sets the display name of a held item

``/is give <player> <itemName> <amount>``
- Will give a player an item from the save file

``/is saveitem <name>``
- Saves a held item out to the save file under the specified ``<name>``

``/is delete``
- Deletes an item from the save file

# For Developers
The whole purpose of this plugin is really to target developers
who find themselves working with a lot of custom items. So heres an API!
