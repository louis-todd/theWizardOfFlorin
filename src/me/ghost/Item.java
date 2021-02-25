package me.ghost;

import java.util.ArrayList;
import java.util.List;

import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;

/**
 *
 * {@inheritDoc}
 * The Item class is used to represent the entities set to collect by the NPCs.
 * The Item class is also used for the dashboard in showing which items are to be collected
 * 
 */
public class Item extends Sprite {

    private boolean availableToCollect = false;
    private boolean isFound = false;
    private String itemName = "placeholder";
    private boolean hasBeenCounted = false;
    private static List<Item> toDrawOnDashboard = new ArrayList<Item>();

    /**
     * The class Item only has a default constructor which sets the items appearance, position, and name.
     * @param itemName sets the name of the item.
     * @param xPosition sets the item's X position of the map.
     * @param yPosition sets the item's Y position on the map.
     * @param itemTexture sets the graphic for the item.
     */

    public Item(String itemName, float xPosition, float yPosition, Texture itemTexture) {
        this.setTexture(itemTexture);
        this.setPosition(xPosition, yPosition);
        this.itemName = itemName;
    }

    /**
     * Gets the name of this Item.
     * @return the name of the item.
     */
    public String getName() {
        return itemName;
    }

    /**
     * Gets availableToCollect which is set when this item has been included as part of the NPC brief.
     * @return whether the item is inclued as part of the current fetch quest.
     */
    public boolean availableToCollect() {
        return availableToCollect;
    }

    /**
     * Gets isFound which is updated when this item is picked up by the user.
     * @return whether this item has been picked up by the player.
     */

    public boolean isFound() {
        return isFound;
    }

    /**
     * Sets availableToCollect once this item has been included as part of the NPC brief.
     * @param isAvailableToCollect sets whether the item should be availableToCollect.
     */
    public void setAsAvailableToCollect(Boolean isAvailableToCollect) {
        if(isAvailableToCollect){
            //Check if the array contains an item with this name
            String nameOfItemToAdd = this.getName();
            Boolean alreadyIn = false;
            for(Item tmp:toDrawOnDashboard){
                if(tmp.getName()==nameOfItemToAdd){
                    alreadyIn = true;
                }
            }
            if(alreadyIn == false){
                toDrawOnDashboard.add(new Item(this.getName(), 0, 0, (Texture) this.getTexture()));
            }
        }
        this.availableToCollect = isAvailableToCollect;
    }

    /**
     * Sets this item to found, intended to be used once an item has been picked up.
     * @param isFound sets whether the item has been picked up by the player.
     */
    public void setAsFound(Boolean isFound) {
        if(isFound){
            toDrawOnDashboard.remove(this.getItemByName(this.getName()));
        }
        this.isFound = isFound;
    }

    /**
     * This method provides an item lookup by name within the set of items displayed on the dashboard.
     * @param itemName sets the name of the item searching for.
     * @return the item which has the associated name, or null if it does not exist.
     */
    private Item getItemByName(String itemName){
        for (Item singleItem : toDrawOnDashboard){
            if(singleItem.getName().equals(itemName)){
                return singleItem;
            }
        }
        return null;
    }

    /**
     *
     * Gets the radius in which a player can interact with an object.
     * @param scaleFactor sets the scale factor of the radius.
     * @return the area in which the player can interact with this item.
     */
    public FloatRect interactionRadius(float scaleFactor) {
        float centrex = this.getGlobalBounds().left + (this.getGlobalBounds().width / 2);
        float centrey = this.getGlobalBounds().top + (this.getGlobalBounds().height / 2);
        float newHeight = this.getGlobalBounds().height * scaleFactor;
        float newWidth = this.getGlobalBounds().width * scaleFactor;
        float newLeft = centrex - (newWidth / 2);
        float newTop = centrey - (newHeight / 2);
        return new FloatRect(newLeft, newTop, newWidth, newHeight);
    }
    
    /** 
     * Gets whether the item has already been included in the total number of items collected from its associated NPC.
     * @return whether the item has already been included in collected total.
     */
    public boolean hasBeenCounted(){
        return hasBeenCounted;
    }

    /** 
     * Sets counted state to true to track whether this item has been collected.
     */
    public void setAsCounted(){
        hasBeenCounted=true;
    }

    /**
     * @return the set of items that are currently to be displayed on the dashboard.
     */
    public static List<Item> getItemsToDrawOnDashboard(){
        return toDrawOnDashboard;
    }

}
