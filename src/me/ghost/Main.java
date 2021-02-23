package me.ghost;

public class Main {
    public static void main(String[] args){

        String OS = System.getProperty("os.name").toLowerCase();
        if(OS.equals("linux")){
            System.loadLibrary("fixXInitThreads");
        }
        
        Game game = new Game();
        game.run();
    }
}