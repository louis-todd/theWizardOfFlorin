package me.ghost;

import java.util.ArrayList;
import java.util.Map;

import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;
import org.jsfml.window.VideoMode;

public class BattleWindow implements Drawable{

    private final ArrayList<Drawable> toDraw;

    public BattleWindow(){
        toDraw = new ArrayList<>();
        drawExampleRectangle();
    }

    private void drawExampleRectangle(){
        //Set the rectangle for the text to sit in
        Vector2f dimensions = new Vector2f(600,600);
        RectangleShape textBackground = new RectangleShape(dimensions) {{
            this.setPosition(20, 300);
            this.setSize(dimensions);
            this.setFillColor(new Color(98,52,18));
        }};
        toDraw.add(textBackground);
    }

    @Override
    public void draw(RenderTarget renderTarget, RenderStates renderStates) {
        for(Drawable item : toDraw) {
            renderTarget.draw(item);
        }
    }
}

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