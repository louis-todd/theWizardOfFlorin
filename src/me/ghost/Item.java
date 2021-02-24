package me.ghost;

import java.util.ArrayList;
import java.util.List;

import org.jsfml.graphics.FloatRect;

import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;

public class Item extends Sprite {

    private Boolean availableToCollect = false;
    private Boolean isFound = false;
    private String itemName = "placeholder";
    private boolean hasBeenCounted = false;
    private static List<Item> toDrawOnDashboard = new ArrayList<Item>();

    public Item(String itemName, float xPosition, float yPosition, Texture itemTexture) {
        this.setTexture(itemTexture);
        this.setPosition(xPosition, yPosition);
        this.itemName = itemName;
    }

    public String getName() {
        return itemName;
    }

    public Boolean availableToCollect() {
        return availableToCollect;
    }

    public Boolean isFound() {
        return isFound;
    }

    public void setAsAvailableToCollect(Boolean isAvailableToCollect) {
        if(isAvailableToCollect==true){
            toDrawOnDashboard.add(new Item(this.getName(), 0, 0, (Texture) this.getTexture()));
        }
        this.availableToCollect = isAvailableToCollect;
    }

    public void setAsFound(Boolean isFound) {
        if(isFound==true){
            toDrawOnDashboard.remove(this.getItemByName(this.getName()));
        }
        this.isFound = isFound;
    }

    public Item getItemByName(String itemName){
        for (Item singleItem : toDrawOnDashboard){
            if(singleItem.getName().equals(itemName)){
                return singleItem;
            }
        }
        return null;
    }

    public FloatRect dialogueArea(float scaleFactor) {
        float centrex = this.getGlobalBounds().left + (this.getGlobalBounds().width / 2);
        float centrey = this.getGlobalBounds().top + (this.getGlobalBounds().height / 2);
        float newHeight = this.getGlobalBounds().height * scaleFactor;
        float newWidth = this.getGlobalBounds().width * scaleFactor;
        float newLeft = centrex - (newWidth / 2);
        float newTop = centrey - (newHeight / 2);
        return new FloatRect(newLeft, newTop, newWidth, newHeight);
    }
    
    public Boolean hasBeenCounted(){
        return hasBeenCounted;
    }

    public void setAsCounted(){
        hasBeenCounted=true;
    }

    public static List<Item> getItemsToDrawOnDashboard(){
        return toDrawOnDashboard;
    }

}
