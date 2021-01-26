package me.ghost;

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

    private void rectangle(int vectorDi1, int vectorDi2,int Pos1, int Pos2, int colour1, int colour2, int colour3){
        Vector2f dimensions = new Vector2f(vectorDi1,vectorDi2);
        RectangleShape textBackground = new RectangleShape(dimensions) {{
            this.setPosition(Pos1, Pos2);
            this.setSize(dimensions);
            this.setFillColor(new Color(colour1,colour2,colour3));
            this.setOutlineThickness(1);
        }};
        toDraw.add(textBackground);
    }

    private void healthCircle(int Pos1, int Pos2){

        CircleShape circle = new CircleShape(10) {{

            this.setPosition(Pos1, Pos2);
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


//    private void battleSquare(){
//        //Set the rectangle for the text to sit in
//        Vector2f dimensions = new Vector2f(620,100);
//        RectangleShape textBackground = new RectangleShape(dimensions) {{
//            this.setPosition(10, 370);
//            this.setSize(dimensions);
//            //this.setFillColor(new Color(98,52,18));
//            this.setFillColor(getFillColor().BLACK);
//        }};
//        toDraw.add(textBackground);
//    }


//        private void rectangle (int x, int y, int xPosition, int yPosition)
//    {
//        rectangle = new RectangleShape(new Vector2f(x,y));
//        rectangle.setOrigin(x/2,y/2);
//        rectangle.setPosition(xPos,yPos);
//        rectangle.setFillColor()
//    }

//    private void CharterCircle(){
//        //Set the rectangle for the text to sit in
//        Vector2f dimensions = new Vector2f(600,600);
//        RectangleShape textBackground = new RectangleShape(dimensions) {{
//            this.setPosition(20, 300);
//            this.setSize(dimensions);
//            this.setFillColor(new Color(98,52,18));
//        }};
//        toDraw.add(textBackground);
//    }

//    private void CharterHealthRectangle(){
//        //Set the rectangle for the text to sit in
//        Vector2f dimensions = new Vector2f(800,600);
//        RectangleShape textBackground = new RectangleShape(dimensions) {{
//            this.setPosition(20, 300);
//            this.setSize(dimensions);
//            this.setFillColor(new Color(98,52,18));
//        }};
//        toDraw.add(textBackground);
//    }
// public class Battle {
//     Texture texture = new Texture();


//     public void battleStartUpdate() {
//         window.clear(Color.RED);


//         isDialogue();
//         window.display();
//     }

//     public void loadImage(){



//         try {
//             //Load the image.
//             Image image = new Image();
//             image.loadFromFile(Paths.get("undertaleBattle1.png"));

//             //Apply the color mask
//             image.createMaskFromColor(Color.BLUE);

//             //Load the masked image into the texture
//             texture.loadFromImage(image);
//         } catch(IOException|TextureCreationException ex) {
//             System.err.println("Something went wrong:");
//             ex.printStackTrace();
//         }


//     }


// }

//public class Battle {
//    //    Texture texture = new Texture();
//    private RectangleShape Rectangle;
//    //RectangleShape CombatArea = new RectangleShape(new Vector2f(500, 300));
//
//
//    public rectangle (int x, int y, int xPosition, int yPosition)
//    {
//        rectangle = new RectangleShape(new Vector2f(x,y));
//        rectangle.setOrigin(x/2,y/2);
//        rectangle.setPosition(xPos,yPos);
//    }
//
//
//
//
////    public void battleStartUpdate(int ghostNumber, string BattleAreaPath,  ) {
////        window.clear(Color.RED);
////
////        window.draw(combatArea);
////
////        isDialogue();
////        window.display();
////    }
//
//    public void draw(RenderWindow window){
//        window.draw(rectangle);
//    }
//


