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

//            For debug purposes ===
            lines.forEach(lineItem -> {
                if (lineItem instanceof CLineItem) {
                    System.out.println("This is C line!");
                } else if (lineItem instanceof DLineItem) {
                    System.out.println("This is D line!");
                }
            });
//            For debug purposes ===

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
