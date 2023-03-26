package me.zlaio.itemstacked.exceptions;

public class ItemNotFoundException extends RuntimeException {
    
    public ItemNotFoundException(String itemName, String fileName) {
        super("Couldn't find item '" + itemName + "' from file '" + fileName + "'");
    }

}
