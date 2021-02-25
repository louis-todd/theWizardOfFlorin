package me.ghost.map;

import me.ghost.data.TileLoader;
import org.jsfml.graphics.FloatRect;
import org.jsfml.system.Vector2f;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GameMap {

    private final String fileName;
    private final List<List<Integer>> cell = new ArrayList<>();
    private final List<List<Tile>> tileMap = new ArrayList<>();

    private final int csvWidth;
    private final TileLoader tileLoader;
    private boolean hasLoaded = false;

    /**
     * Sole constuctor.
     * @param fileName sets the filename.
     * @param setCsvWidth sets the width of the csv.
     * @param setTileLoader sets the mechanism to load in the tiles used for map.
     */
    public GameMap(String fileName, int setCsvWidth, TileLoader setTileLoader) {
        this.fileName = fileName;
        this.tileLoader = setTileLoader;
        this.csvWidth = setCsvWidth;

        cell.add(new ArrayList<>());
        tileMap.add(new ArrayList<>());
    }

    
    /** 
     * @return whether the map has loaded.
     */
    public boolean hasLoaded() {
        return hasLoaded;
    }

    /**
     * Attempts to load in tiles (graphical components that make up the map).
     */
    public void loadMap(){
        this.hasLoaded = true;
        BufferedReader csvReader = this.returnBufferedReader();

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

    
    /** 
     * @return BufferedReader
     */
    private BufferedReader returnBufferedReader(){
        return new BufferedReader(new InputStreamReader(getFileStream()));
    }

    /** 
     * @return InputStream
     */
    private InputStream getFileStream()  {
        DataInputStream fileStream = null;
        try {
            fileStream = new DataInputStream(new FileInputStream(fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return fileStream;
    }

    /** 
     * @param rowNumber sets row number.
     * @param rowIndex sets row index.
     */
    private void createTiles(int rowNumber, int rowIndex){
        if(cell.get(rowNumber).get(rowIndex) == -1){
            tileMap.get(rowNumber).add(new Tile(new Vector2f(rowIndex*16, rowNumber*16), tileLoader.getTileTexture(0), cell.get(rowNumber).get(rowIndex), tileLoader));
        } else {
            tileLoader.getTileTexture(cell.get(rowNumber).get(rowIndex));
            tileMap.get(rowNumber).add(new Tile(new Vector2f(rowIndex * 16, rowNumber * 16), tileLoader.getTileTexture(cell.get(rowNumber).get(rowIndex)), cell.get(rowNumber).get(rowIndex), tileLoader));
        }
    }

    /** 
     * @param x defines the x positino of the file.
     * @param y defines the y positino of the file.
     * @return the requested tile by its coordinates.
     */
    public Tile getTile(int x, int y){
        return tileMap.get(x).get(y);
    }

    /** 
     * @return width of the csv.
     */
    public int getDrawWidth() {
        return csvWidth - 1;
    }

    /** 
     * @return height of the csv.
     */
    public int getDrawHeight() {
        return cell.size() - 2;
    }

    /** 
     * @return the boundaries of the map
     */
    public FloatRect getMapBounds(){
        return new FloatRect(0, 0, getDrawWidth() * 16, getDrawHeight() * 16);
    }
}
