package me.ghost;

import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;
import org.jsfml.window.Keyboard;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.KeyEvent;
import java.io.IOException;
import java.nio.file.Paths;

public class Main {
    static boolean RIGHT = false;
    static boolean LEFT = false;
    static boolean UP = false;
    static boolean DOWN = false;

    public static void main(String[] args) {
        Game game = new Game();
        game.run();
    }
}

