package me.ghost.battle;

import java.util.ArrayList;

import org.jsfml.graphics.*;
import org.jsfml.graphics.Drawable;
import org.jsfml.system.Vector2f;

public class BattleWindow implements Drawable{

    private final ArrayList<Drawable> toDraw;

    public BattleWindow(){

        toDraw = new ArrayList<>();
        rectangle(640,480,0,0,0,0,0);// background
        rectangle(500,180,70,10,255,255,255);//ghost area
        rectangle(350,100,150,210,255,255,255); // player area

        rectangle(30,20,10, 340,255,30,0);//health label
        // for health circles
        for (int i=50; i<=100 ; i+=25)
        {
            healthCircle(i, 340);
        }
        rectangle(620,100,10, 370,98,52,18);//dialogue box
    }

    private void rectangle(int width, int height, int y, int x, int r, int g, int b){
        Vector2f dimensions = new Vector2f(width,height);
        RectangleShape textBackground = new RectangleShape(dimensions) {{
            this.setPosition(y, x);
            this.setSize(dimensions);
            this.setFillColor(new Color(r,g,b));
            this.setOutlineThickness(1);
        }};
        toDraw.add(textBackground);
    }

    private void healthCircle(int y, int x){

        CircleShape circle = new CircleShape(10) {{

            this.setPosition(y, x);
            this.setFillColor(new Color(255,0,255));
            this.setOutlineThickness(1);
        }};
        toDraw.add(circle);
    }

    @Override
    public void draw(RenderTarget renderTarget, RenderStates renderStates) {
        for(Drawable item : toDraw) {
            renderTarget.draw(item);
        }
    }
}