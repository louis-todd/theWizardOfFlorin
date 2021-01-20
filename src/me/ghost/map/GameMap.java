package me.ghost.map;

import org.jsfml.graphics.Image;
import org.jsfml.graphics.Texture;
import org.jsfml.graphics.TextureCreationException;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class GameMap {
    String fileName;
    private List<List<Integer>> cell;
    public GameMap(String setFileName) throws FileNotFoundException {
        cell = new ArrayList<>();
        this.fileName = setFileName;
        this.newLoadMap(returnBufferedReader());
        System.out.println(cell);
    }

    private void loadMap() {
        File csvFile = new File("resources/map._House.csv");
        if (csvFile.isFile()) {
            String row;
            try {
                BufferedReader csvReader = new BufferedReader(new FileReader("map._House.csv"));
                while ((row = csvReader.readLine()) != null) {
                    String[] data = row.split(",");
                    int imageNo = Integer.parseInt(data[0]);
                    switch (imageNo){
                        case -1:
                            Texture texture2 = new Texture();

                            try {
                                //Load the image.
                                Image image = new Image();
                                image.loadFromFile(Paths.get("resources/tiles/tile6.png"));

                                //Apply the color mask
                                //image.createMaskFromColor(Color.BLUE);

                                //Load the masked image into the texture
                                texture2.loadFromImage(image);
                            } catch(IOException | TextureCreationException ex) {
                                System.err.println("Something went wrong:");
                                ex.printStackTrace();
                            }
                        default:
                            //draw nothing
                    }
                }
                csvReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void newLoadMap(BufferedReader csvReader){
        try {
            String row;
            while((row = csvReader.readLine()) != null){
                String[] data = row.split(",");
                int rowNumber = 0;
                for(String tempStr : data){
                    List<Integer> rowData = new ArrayList<>();

                    rowData.add(extractDigits(tempStr));

                    cell.add(rowNumber, rowData);
                    rowNumber++;
                    cell.add(new ArrayList<Integer>());
                }
            }
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

    public int extractDigits(String dataElement) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < dataElement.length(); i++) {
            char c = dataElement.charAt(i);
            if (Character.isDigit(c) || c == '-') {
                builder.append(c);
            }
        }
        return Integer.parseInt(builder.toString());
    }
}
