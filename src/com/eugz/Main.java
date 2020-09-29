package com.eugz;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        try (
            BufferedReader reader = new BufferedReader(new FileReader( new File("").getAbsolutePath() + "/src/input.txt"))
        ){
            int linesCount = Integer.parseInt(reader.readLine());
            List<LineItem> lines = new ArrayList<>(linesCount);

            while (reader.ready()) {
                String line = reader.readLine();
                lines.add(LineItem.getLineItem(line.split(" ")));
            }

            Filter filter = new Filter();
            filter.start(lines);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
