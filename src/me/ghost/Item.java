package me.ghost;

import org.jsfml.graphics.FloatRect;

import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;

/**
 *this class represents the item class.
 * this has an item constructor and also has all the methods relating to the item constructor 
 */
public class Item extends Sprite {

    private Boolean availableToCollect = false;
    private Boolean isFound = false;
    private String itemName = "placeholder";

    /**
     *this method is the constructor for the item
     * @param itemName to set the name of the item
     * @param xPosition to set the item's X position of the map
     * @param yPosition to set the item's Y position on the map
     * @param itemTexture to set the graphic for the item
     */

    public Item(String itemName, float xPosition, float yPosition, Texture itemTexture) {
        this.setTexture(itemTexture);
        this.setPosition(xPosition, yPosition);
        this.itemName = itemName;
    }

    /**
     * get method to return the name of the item
     * @return returns ths name of the item in string form
     */
    public String getName() {
        return itemName;
    }

    /**
     * method to get back the item if its available to collect
     * @return returns true if item is picked up already
     * returns false if the item is dropped
     */
    public Boolean availableToCollect() {
        return availableToCollect;
    }

    /**
     * boolean to return true if the item is found or false if item not found
     * @return returns True if item is found and false
     */

    public Boolean isFound() {
        return isFound;
    }

    /**
     * theis method sets the item to available to collect when the item is dropped
     * @param isAvailableToCollect Name of the item which has to be set available to collect
     */

    public void setAsAvailableToCollect(Boolean isAvailableToCollect) {
        this.availableToCollect = isAvailableToCollect;
    }

    /**
     * when the item is collect and given to the ghost it's isFound is set to TRUE
     * @param isFound if the quest item is handed to the ghost it is set to found.
     */

    public void setAsFound(Boolean isFound) {
        this.isFound = isFound;
    }

    /**
     *
     * @param scaleFactor
     * @return
     */

    public FloatRect dialogueArea(float scaleFactor) {
        float centrex = this.getGlobalBounds().left + (this.getGlobalBounds().width / 2);
        float centrey = this.getGlobalBounds().top + (this.getGlobalBounds().height / 2);
        float newHeight = this.getGlobalBounds().height * scaleFactor;
        float newWidth = this.getGlobalBounds().width * scaleFactor;
        float newLeft = centrex - (newWidth / 2);
        float newTop = centrey - (newHeight / 2);
        return new FloatRect(newLeft, newTop, newWidth, newHeight);
    }

}
