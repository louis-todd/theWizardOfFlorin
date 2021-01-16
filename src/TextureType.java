import me.ghost.Game;
import org.jsfml.graphics.Texture;

import java.nio.file.Paths;

public enum TextureType {
    SQUARE16("resources/square-16.png")

    ;

private final Texture texture = new Texture();

    TextureType(String path) {
        try {
            this.texture.loadFromStream(Game.class.getClassLoader().getResourceAsStream(path));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Texture getTexture() {
        return this.texture;
    }
}
