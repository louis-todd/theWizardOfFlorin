package me.ghost.map;


import me.ghost.resourceEnum.TileLoader;
import org.jsfml.system.Vector2f;

import java.io.*;

import java.util.ArrayList;
import java.util.List;

public class GameMap {
    String fileName;
    private final List<List<Integer>> cell;
    private final List<List<Tile>> tileMap;
    private final int csvWidth;
    private final TileLoader tileLoader;
    public GameMap(String setFileName, int setCsvWidth, TileLoader setTileLoader) throws FileNotFoundException {
        this.tileLoader = setTileLoader;
        this.csvWidth = setCsvWidth;
        cell = new ArrayList<>();
        cell.add(new ArrayList<>());
        this.fileName = setFileName;
        this.LoadMap(returnBufferedReader());
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

                    if (rowIndex == csvWidth) {
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
        tileMap.add(new ArrayList<>());

        for(int i = 0; i < cell.size() - 1; i++){
            for(int j = 0; j <= csvWidth - 1; j++){
                if(cell.get(i).get(j) == -1){
                    tileMap.get(i).add(new Tile(new Vector2f(j*16, i*16), tileLoader.getTileTexture(10)));
                } else {
                    tileMap.get(i).add(new Tile(new Vector2f(j * 16, i * 16), tileLoader.getTileTexture(cell.get(i).get(j))));
                }
            }
                tileMap.add(new ArrayList<>());
        }
    }

    public Tile getTile(int x, int y){
        return tileMap.get(x).get(y);
    }
}
