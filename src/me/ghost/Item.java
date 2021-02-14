package me.ghost;

import java.util.ArrayList;
import org.jsfml.graphics.FloatRect;

import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;

public class Item extends Sprite {

    private Boolean availableToCollect = false;
    private Boolean isFound = false;
    private Boolean reportedFound = false;
    private String itemName = "placeholder";

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

    public Boolean hasBeenReportedAsFound(){
        return reportedFound;
    }

    public void setAshasBeenReportedAsFound(Boolean hasBeenFound){
        reportedFound = hasBeenFound;
    }

    public void setAsAvailableToCollect(Boolean isAvailableToCollect) {
        this.availableToCollect = isAvailableToCollect;
    }

    public void setAsFound(Boolean isFound) {
        this.isFound = isFound;
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

}
