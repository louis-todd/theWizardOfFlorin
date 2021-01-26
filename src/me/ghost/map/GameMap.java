package me.ghost.map;


import me.ghost.Game;
import me.ghost.resourceEnum.TextureType;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;

import java.io.*;

import java.util.ArrayList;
import java.util.List;

public class GameMap {
    String fileName;
    private final List<List<Integer>> cell;
    private final List<List<Tile>> tileMap;
    public GameMap(String setFileName) throws FileNotFoundException {
        cell = new ArrayList<>();
        cell.add(new ArrayList<>());
        this.fileName = setFileName;
        this.LoadMap(returnBufferedReader());
        System.out.println(cell);
        tileMap = new ArrayList<>();
        createTiles();
    }

    private void LoadMap(BufferedReader csvReader){
        try {
            String row;
            int rowIndex = 0;
            int rowNumber = 0;
            while((row = csvReader.readLine()) != null){
                String[] data = row.split(",");
                for (String dataElement : data) {
                    cell.get(rowNumber).add(Integer.parseInt(dataElement));
                    rowIndex++;

                    if (rowIndex == 50) {
                        rowNumber++;
                        rowIndex = 0;
                        cell.add(new ArrayList<>());
                    }
                }
            }
            csvReader.close();
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }

    private BufferedReader returnBufferedReader() throws FileNotFoundException {
        return new BufferedReader(new InputStreamReader(getFileStream()));
    }
    private InputStream getFileStream() throws FileNotFoundException {
        return new DataInputStream(new FileInputStream(fileName));
    }

    private void createTiles(){
        System.out.println(cell.size());
        tileMap.add(new ArrayList<>());
        for(int i = 0; i < cell.size(); i++){
            for(int j = 0; j < 50; j++){
                tileMap.get(i).add(new Tile(new Vector2f(i*16, j*16), TextureType.TEST.getTexture()));
            }
            tileMap.add(new ArrayList<>());
        }
    }

    private Texture getTileTexture(String textureLocation){
        Texture tileTexture = new Texture();
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
        return tileTexture;
    }

    public Tile getTile(int x, int y){
        return tileMap.get(x).get(y);
    }
}
