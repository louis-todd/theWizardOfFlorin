package me.ghost.battle;

import java.util.ArrayList;

import org.jsfml.graphics.*;
import org.jsfml.graphics.Drawable;
import org.jsfml.system.Vector2f;

public class BattleWindow implements Drawable{

    private  ArrayList<Drawable> toDraw;
    private  ArrayList<CircleShape> healthCircles;
    private  ArrayList<RectangleShape> columnRec;
    //private final RectangleShape ghostArea;
    private  RectangleShape playerArea;

    public BattleWindow(){
        int life = 1;

//        while(life == 1)
//        {

        toDraw = new ArrayList<>();
        healthCircles = new ArrayList<>();
        columnRec = new ArrayList<>();

        rectangle(640, 480, 0, 0, 0, 0, 0); //Background
        //ghostArea = rectangle(500, 180, 70, 10, 255, 255, 255); //Ghost Area
        playerArea = rectangle(620, 320, 10, 10, 255, 255, 255); //Player Area
        rectangle(30,20,10, 340,255,30,0); //Health label

// for health circles
        for (int i=50; i<=100 ; i+=25)
        {
            healthCircles.add(healthCircle(i, 340));
        }


        rectangle(620,100,10, 370,98,52,18);//dialogue box

//            bottomColumnRectangle = rectangle(25, 120, 150, 211, 0, 0, 204, 0);//first try
//            topColumnRectangle = rectangle(25, 120, 150, 9, 0, 0, 204, 0);//first try

        columnUpdate();

//        }
    }

    private void columnUpdate(){

        for(int i=580;i>=200;i-=120 ){

            column( i, 201);//bottom1
            column(i, 9);//top1


        }
//                column( i, 201);//bottom2
//                column( i, 9);//top2
//
//                column( i, 201);//bottom3
//                column( i, 9);//top3
//
//                column(i, 201);//bottom4
//                column(i, 9);//top4

        //toDraw.remove(column( 140, 201));
    }

    private RectangleShape rectangle(int width, int height, int x, int y, int r, int g, int b){
        Vector2f dimensions = new Vector2f(width,height);
        RectangleShape Rectangle = new RectangleShape(dimensions);
        Rectangle.setPosition(x, y);
        Rectangle.setSize(dimensions);
        Rectangle.setFillColor(new Color(r,g,b));
        Rectangle.setOutlineThickness(1);
        toDraw.add(Rectangle);
        return Rectangle;
    }

    private RectangleShape column ( int x, int y){
        Vector2f dimensions = new Vector2f(25,130);
        RectangleShape Rectangle = new RectangleShape(dimensions);
        Rectangle.setPosition(x, y);
        Rectangle.setSize(dimensions);
        Rectangle.setFillColor(new Color(0, 0, 204));
        //toDraw.add(Rectangle);
        columnRec.add(Rectangle);
        return Rectangle;
    }

    private CircleShape healthCircle(int x, int y) {
        CircleShape circle = new CircleShape(10);
        circle.setPosition(x, y);
        circle.setFillColor(new Color(255, 0, 255));
        circle.setOutlineThickness(1);
        toDraw.add(circle);
        return circle;
    }

    @Override
    public void draw(RenderTarget renderTarget, RenderStates renderStates) {
        for(Drawable item : toDraw) {
            renderTarget.draw(item);
        }
    }

    public ArrayList<CircleShape> getHealthCircles() {
        return healthCircles;
    }

    public ArrayList<RectangleShape> getColumn() {

        return columnRec;
    }
//
//    public RectangleShape getGhostArea() {
//        return ghostArea;
//    }

    public RectangleShape getPlayerArea() {
        return playerArea;
    }
}










//public class MiniGame1 extends BattleWindow
//{
//    public Random rand;
//    public ArrayList<Drawable> toDraw;
//
//    public MiniGame1()
//    {
//        toDraw = new ArrayList<>();
//
//        addColumn();
//        addColumn();
//        addColumn();
//        addColumn();
//
//    }
//
//
//    private void columnRectangle(int x, int y, int height, int width)
//    {
//        Vector2f dimensions = new Vector2f(width,height);
//        RectangleShape Column = new RectangleShape(dimensions);
//            Column.setPosition(y, x);
//            Column.setSize(dimensions);
//            Column.setFillColor(new Color(255,128,0));
//            Column.setOutlineThickness(1);
//
//        toDraw.add(Column);
//    }
//
//
//    public void addColumn()
//    {
//        int space = 300;
//        int width = 100;
//
//        int height = 50 + rand.nextInt(300);
//
//        int HEIGHT =640;
//        int WIDTH  = 480;
//
////        if ()
////        {
////            toDraw.add(new columnRectangle(WIDTH + width + toDraw.size() * 300, HEIGHT - height - 120, width, height));
////            toDraw.add(new columnRectangle(WIDTH + width + (toDraw.size() - 1) * 300, 0, width, HEIGHT - height - space));
////           columnRectangle(WIDTH + width + toDraw.size() * 300, HEIGHT - height - 100, width, height,);
////           columnRectangle(WIDTH + width + (toDraw.size() - 1) * 300, 0, width, HEIGHT - height - space);
//
//            columnRectangle(WIDTH + width + toDraw.size() * 300, HEIGHT - height - 100, width, height);
//            columnRectangle(WIDTH + width + (toDraw.size() - 1) * 300, 0, width, HEIGHT - height - space);
//
////        }
////        else
////        {
//////            toDraw.add(new columnRectangle(toDraw.get(toDraw.size() - 1).x + 600, HEIGHT - height - 120, width, height));
//////            toDraw.add(new columnRectangle(toDraw.get(toDraw.size() - 1).x, 0, width, HEIGHT - height - space));
////
//////           columnRectangle(toDraw.get(toDraw.size() - 1).x + 600, HEIGHT - height - 120, width, height);
//////            columnRectangle(toDraw.get(toDraw.size() - 1).x, 0, width, HEIGHT - height - space);
////
////            columnRectangle(toDraw.get(toDraw.size() - 1).x + 600, HEIGHT - height - 120, width, height);
////            columnRectangle(toDraw.get(toDraw.size() - 1).x, 0, width, HEIGHT - height - space);
////
////        }
//
//    }
//
//    @Override
//    public void draw(RenderTarget renderTarget, RenderStates renderStates) {
//        for(Drawable item : toDraw) {
//            renderTarget.draw(item);
//        }
//    }
//
//
//}

//    @Override
//    public void actionPerformed(ActionEvent e) {
//
//    }
//
//    @Override
//    public void keyTyped(KeyEvent e) {
//
//    }
//
//    @Override
//    public void keyPressed(KeyEvent e) {
//
//    }
//
//    @Override
//    public void keyReleased(KeyEvent e) {
//
//    }
//
//    @Override
//    public void mouseClicked(MouseEvent e) {
//
//    }
//
//    @Override
//    public void mousePressed(MouseEvent e) {
//
//    }
//
//    @Override
//    public void mouseReleased(MouseEvent e) {
//
//    }
//
//    @Override
//    public void mouseEntered(MouseEvent e) {
//
//    }
//
//    @Override
//    public void mouseExited(MouseEvent e) {
//
//    }