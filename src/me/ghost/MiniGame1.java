//package me.ghost;
//
//import org.jsfml.graphics.*;
//import org.jsfml.system.Vector2f;
//import org.jsfml.window.Keyboard;
//import org.jsfml.window.event.Event;
//import org.jsfml.window.event.KeyEvent;
//import java.util.ArrayList;
//import java.util.Random;
//import org.jsfml.graphics.Drawable;
//
//
//
//
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