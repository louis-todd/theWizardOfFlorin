package me.ghost.map;


import java.io.*;

import java.util.ArrayList;
import java.util.List;

public class GameMap {
    String fileName;
    private List<List<Integer>> cell;
    public GameMap(String setFileName) throws FileNotFoundException {
        cell = new ArrayList<>();
        cell.add(new ArrayList<>());
        this.fileName = setFileName;
        this.LoadMap(returnBufferedReader());
        System.out.println(cell);
    }

    private void LoadMap(BufferedReader csvReader){
        try {
            String row;
            int rowIndex = 0;
            int rowNumber = 0;
            while((row = csvReader.readLine()) != null){
                String[] data = row.split(",");
                for(int index = 0; index < data.length; index++){

                    cell.get(rowNumber).add(extractDigits(data[index]));
                    rowIndex++;
                    if(rowIndex == 50) {
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
