package me.ghost.battle.dodge;

import me.ghost.character.Npc;
import org.jsfml.graphics.CircleShape;
import org.jsfml.graphics.Color;
import org.jsfml.system.Vector2f;
import java.util.concurrent.ThreadLocalRandom;


public class Projectile extends CircleShape {

    private final boolean rightRotate;
    private final Color[] colours = {Color.BLACK, Color.BLUE, Color.RED, Color.GREEN};
    private Vector2f velocity = null;
    private Vector2f totalMovement = new Vector2f(0, 0);

    /**
     * Draws a circle of random colour.
     * {@inheritDoc}
     */
    public Projectile(int radius, int i) {
        super(radius, i);
        this.setRandomColour();
        this.rightRotate = ThreadLocalRandom.current().nextBoolean();
    }

    /**
     * Sets the projectory to a random colour.
     */
    private void setRandomColour(){
        Color randomColour = colours[ThreadLocalRandom.current().nextInt(4)];
        this.setFillColor(randomColour);
    }

    /**
     * Applies a velocity to this projectile.
     */
    public void applyVelocity(){
        if (this.velocity == null) {
            int maxx = 5;
            int maxy = 2;
            boolean positivex = ThreadLocalRandom.current().nextBoolean();
            this.velocity = new Vector2f((positivex) ? ThreadLocalRandom.current().nextFloat() * maxx : ThreadLocalRandom.current().nextFloat()*(-maxx), ThreadLocalRandom.current().nextFloat() * maxy + 1);
        }
        this.rotate(rightRotate ? ThreadLocalRandom.current().nextFloat() * 5 : ThreadLocalRandom.current().nextFloat() * (-5));
        this.setPosition(this.getPosition().x + this.velocity.x, this.getPosition().y + this.velocity.y);
        this.totalMovement = new Vector2f(this.totalMovement.x + this.velocity.x, this.totalMovement.x + this.velocity.y);
    }
    
    /** 
     * Throws this projectile.
     * @param player sets the NPC throwing this projectile.
     */
    public void thrown(Npc player) {
        this.setPosition(player.getPosition().x + 8, player.getPosition().y + 8);
        this.applyVelocity();
    }

    /** 
     * @return total movement.
     */
    public Vector2f getTotalMovement() {
        return totalMovement;
    }
    
}
