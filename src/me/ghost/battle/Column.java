//package me.ghost.battle;
//
//import java.util.ArrayList;
//
//import org.jsfml.graphics.*;
//import org.jsfml.graphics.Drawable;
//import org.jsfml.system.Vector2f;
//
//public class Column {
//
//    private ArrayList<Drawable> toDraw;
//    private int xCoordinate;
//    private int yCoordinate;
//
//
//    public Column(int xCoordinate, int yCoordinate) {
//        //column(this.xCoordinate,this.yCoordinate);
//
//        Vector2f dimensions = new Vector2f(25, 130);
//        RectangleShape Rectangle = new RectangleShape(dimensions);
//        Rectangle.setPosition(xCoordinate, yCoordinate);
//        Rectangle.setSize(dimensions);
//        Rectangle.setFillColor(new Color(0, 0, 204));
//        toDraw = new ArrayList<>();
//        toDraw.add(Rectangle);
//        //return Rectangle;
//
//    }
//
//    public RectangleShape column(int x, int y) {
//        Vector2f dimensions = new Vector2f(25, 130);
//        RectangleShape Rectangle = new RectangleShape(dimensions);
//        Rectangle.setPosition(x, y);
//        Rectangle.setSize(dimensions);
//        Rectangle.setFillColor(new Color(0, 0, 204));
//        toDraw = new ArrayList<>();
//        toDraw.add(Rectangle);
//        return Rectangle;
//    }
//
//    public double getX()
//    {
//        return xCoordinate;
//    }
//
//    public double getY()
//    {
//        return yCoordinate;
//    }
//
//    public void draw(RenderTarget renderTarget, RenderStates renderStates) {
//        for (Drawable item : toDraw) {
//            renderTarget.draw(item);
//        }
//    }
//}
