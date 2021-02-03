package me.ghost.map;

import me.ghost.resourceEnum.TileLoader;
import org.jsfml.graphics.FloatRect;
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
    public GameMap(String setFileName, int setCsvWidth, TileLoader setTileLoader) {
        this.tileLoader = setTileLoader;
        this.csvWidth = setCsvWidth;
        cell = new ArrayList<>();
        cell.add(new ArrayList<>());
        tileMap = new ArrayList<>();
        tileMap.add(new ArrayList<>());
        this.fileName = setFileName;
        this.LoadMap(returnBufferedReader());

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

                    createTiles(rowNumber, rowIndex);

                    rowIndex++;

                    if (rowIndex == csvWidth) {
                        rowNumber++;
                        rowIndex = 0;
                        cell.add(new ArrayList<>());
                        tileMap.add(new ArrayList<>());
                    }
                }
            }
            csvReader.close();
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }

    private BufferedReader returnBufferedReader(){
        return new BufferedReader(new InputStreamReader(getFileStream()));
    }

    private InputStream getFileStream()  {
        DataInputStream fileStream = null;
        try {
            fileStream = new DataInputStream(new FileInputStream(fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return fileStream;
    }

    private void createTiles(int rowNumber, int rowIndex){
        if(cell.get(rowNumber).get(rowIndex) == -1){
            tileMap.get(rowNumber).add(new Tile(new Vector2f(rowIndex*16, rowNumber*16), tileLoader.getTileTexture(10)));
        } else {
            tileMap.get(rowNumber).add(new Tile(new Vector2f(rowIndex * 16, rowNumber * 16), tileLoader.getTileTexture(cell.get(rowNumber).get(rowIndex))));
        }
    }

    public Tile getTile(int x, int y){
        return tileMap.get(x).get(y);
    }

    public int getDrawWidth() {
        return csvWidth - 1;
    }

    public int getDrawHeight() {
        return cell.size() - 2;
    }

    public FloatRect getMapBounds(){
        return new FloatRect(0, 0, getDrawWidth() * 16, getDrawHeight() * 16);
    }
}
