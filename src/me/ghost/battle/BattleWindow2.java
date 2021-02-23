package me.ghost.battle;

import java.util.ArrayList;

import org.jsfml.graphics.*;
import org.jsfml.graphics.Drawable;
import org.jsfml.system.Vector2f;

public class BattleWindow2 implements Drawable {
    private RenderWindow Window;
    private ArrayList<Drawable> toDraw;
    private ArrayList<CircleShape> healthCircles;
    private ArrayList<Drawable> getToDraw;
    private ArrayList<RectangleShape> RecColumn;
    //private final RectangleShape ghostArea;
    private RectangleShape playerArea;

    public BattleWindow2(RenderWindow Window) {
        int life = 1;
        toDraw = new ArrayList<>();
        toDraw = new ArrayList<>();
        healthCircles = new ArrayList<>();
        getToDraw = new ArrayList<>();
        RecColumn = new ArrayList<>();
        this.Window = Window;
        //Column topColumn = new Column(140,201);

//      while(life == 1)
//      {


            rectangle(640, 480, 0, 0, 0, 0, 0); //Background
            //ghostArea = rectangle(500, 180, 70, 10, 255, 255, 255); //Ghost Area
            playerArea = rectangle(620, 320, 10, 10, 255, 255, 255); //Player Area
            rectangle(30, 20, 10, 340, 255, 30, 0); //Health label


            // for health circles
            for (int i = 50; i <= 100; i += 25) {
                healthCircles.add(healthCircle(i, 340));
            }

            rectangle(620, 100, 10, 370, 98, 52, 18);//dialogue box

        RecColumn.add(column(140, 201));//bottom1
        RecColumn.add(column(140, 9));//top1

        RecColumn.add(column(260, 201));//bottom2
        RecColumn.add(column(260, 9));//top2

        RecColumn.add(column(380, 201));//bottom3
        RecColumn.add(column(380, 9));//top3

        RecColumn.add(column(500, 201));//bottom4
        RecColumn.add(column(500, 9));//top4








//            column(140, 201);//bottom1
//            column(140, 9);//top1
//                columnUpdate();
//            bottomColumnRectangle = rectangle(25, 120, 150, 211, 0, 0, 204, 0);//first try
//            topColumnRectangle = rectangle(25, 120, 150, 9, 0, 0, 204, 0);//first try


            //updateWindow();

//        }
    }

    public void columnUpdate() {
        while(true) {
            for (int i = 0; i < RecColumn.size(); i++) {
                RecColumn.get(i).move(-10, 0);
                Window.clear();
                System.out.println("hello");
                Window.draw(RecColumn.get(i));

            }
        }

    }

//    private Object getX() {
//    }

    private RectangleShape rectangle(int width, int height, int x, int y, int r, int g, int b) {
        Vector2f dimensions = new Vector2f(width, height);
        RectangleShape Rectangle = new RectangleShape(dimensions);
        Rectangle.setPosition(x, y);
        Rectangle.setSize(dimensions);
        Rectangle.setFillColor(new Color(r, g, b));
        Rectangle.setOutlineThickness(1);
        toDraw.add(Rectangle);
        return Rectangle;
    }

    private RectangleShape column(int x, int y) {
        Vector2f dimensions = new Vector2f(25, 130);
        RectangleShape Rectangle = new RectangleShape(dimensions);
        Rectangle.setPosition(x, y);
        Rectangle.setSize(dimensions);
        Rectangle.setFillColor(new Color(0, 0, 204));
       // toDraw.add(Rectangle);
        toDraw.add(Rectangle);
        return Rectangle;
    }

//    moveColumn

    private CircleShape healthCircle(int x, int y) {
        CircleShape circle = new CircleShape(10);
        circle.setPosition(x, y);
        circle.setFillColor(new Color(255, 0, 255));
        circle.setOutlineThickness(1);
        toDraw.add(circle);
        return circle;
    }

    public void updateWindow()
    {
        try
        {
            //this.repaint();
            Thread.sleep(20);
            synchronized (this)
            {
                toDraw.clear();
            }
        }
        catch (Exception e) {
            System.out.println(" Error");
        }
    }

    @Override
    public void draw(RenderTarget renderTarget, RenderStates renderStates) {
        for (Drawable item : toDraw) {
            renderTarget.draw(item);
        }
    }

    public ArrayList<CircleShape> getHealthCircles() {
        return healthCircles;
    }

//    public ArrayList<Drawable> getColumn() {
//
//        return column;
//    }
//
//    public RectangleShape getGhostArea() {
//        return ghostArea;
//    }

    public RectangleShape getPlayerArea() {
        return playerArea;
    }
}