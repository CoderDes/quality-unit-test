package com.eugz;

import java.time.LocalDate;
import java.util.Arrays;

public abstract class LineItem {

    public static LineItem getLineItem(String[] lineData) {
        String lineType = lineData[0];

        String[] restLineData = Arrays.copyOfRange(lineData, 1, lineData.length);

        if (lineType.equals("C")) {
            return new CLineItem(restLineData);
        } else {
            return new DLineItem(restLineData);
        }
    }

    public static boolean serviceIdMatchAll(String[] data) {
        return data[0].equals("*");
    }

    public static int parseServiceId(String[] data) {
        String[] serviceData = data[0].split("\\.");
        return Integer.parseInt(serviceData[0]);
    }

    public static boolean variationExists(String[] data) {
        return data.length >= 2;
    }

    public static int parseVariationId(String[] data) {
        String[] serviceData = data[0].split("\\.");
        return Integer.parseInt(serviceData[0]);
    }

    public static boolean questionTypeDataMatchAll(String[] data) {
        return data[1].equals("*");
    }

    public static int parseQuestionTypeId(String[] data) {
        String[] questionTypeData = data[1].split("\\.");
        return Integer.parseInt(questionTypeData[0]);
    }

    public static boolean categoryIdExists(String[] data) {
        String[] questionTypeData = data[1].split("\\.");
        return questionTypeData.length >= 2;
    }

    public static int parseCategoryId(String[] data) {
        String[] questionTypeData = data[1].split("\\.");
        return Integer.parseInt(questionTypeData[1]);
    }

    public static boolean subCategoryExists(String[] data) {
        String[] questionTypeData = data[1].split("\\.");
        return questionTypeData.length == 3;
    }

    public static int parseSubCategory(String[] data) {
        String[] questionTypeData = data[1].split("\\.");
        return Integer.parseInt(questionTypeData[2]);
    }

    public static boolean isPeriod(String[] data) {
        return data[3].contains("-");
    }

    public static LocalDate parseDate(String[] data) {
        String[] rawDate = data[3].split("\\.");
        return LocalDate.of(Integer.parseInt(rawDate[2]), Integer.parseInt(rawDate[1]), Integer.parseInt(rawDate[0]));
    }

    public static LocalDate parseStartDate(String[] data) {
        String[] datesElems = data[3].split("-");
        String[] startDateElems = datesElems[0].split("\\.");
        return LocalDate.of(Integer.parseInt(startDateElems[2]), Integer.parseInt(startDateElems[1]), Integer.parseInt(startDateElems[0]));
    }

    public static LocalDate parseEndDate(String[] data) {
        String[] datesElems = data[3].split("-");
        String[] endDateElems = datesElems[1].split("\\.");
        return LocalDate.of(Integer.parseInt(endDateElems[2]), Integer.parseInt(endDateElems[1]), Integer.parseInt(endDateElems[0]));
    }


    public static boolean waitingTimeExists(String[] data) {
        return data.length == 5;
    }

    public static double parseWaitingTime(String[] data) {
        return Double.parseDouble(data[4]);
    }

    abstract boolean isServiceMatchAll();

    abstract int getServiceId();

    abstract Integer getVariationId();

    abstract boolean isQuestionTypeIdMatchAll();

    abstract int getQuestionTypeId();

    abstract Integer getCategoryId();

    abstract Integer getSubCategoryId();

    abstract String getResponseAnswer();

    abstract LocalDate getDate();

    abstract LocalDate getStartDate();

    abstract LocalDate getEndDate();
}
