package me.zlaio.itemstacked.exceptions;

public class InvalidItemConfigurationException extends RuntimeException {

    public InvalidItemConfigurationException(String itemName) {
        super("The 'material' key for item '" + itemName + "' is either missing or invalid.");
    }

    public InvalidItemConfigurationException(InvalidItemConfigurationException e) {
        super(e.getMessage());
    }

}
