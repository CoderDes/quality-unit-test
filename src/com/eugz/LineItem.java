package com.eugz;

import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;

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

    public static Calendar parseDate(String[] data) {
        String[] rawDate = data[3].split("\\.");
        return new GregorianCalendar(Integer.parseInt(rawDate[2]), Integer.parseInt(rawDate[1]), Integer.parseInt(rawDate[0]));
    }

    public static Period parsePeriod(String[] data) {
        String[] datesElems = data[3].split("-");
        String[] startDateElems = datesElems[0].split("\\.");
        String[] endDateElems = datesElems[1].split("\\.");

        LocalDate startDate = LocalDate.of(Integer.parseInt(startDateElems[2]), Integer.parseInt(startDateElems[1]), Integer.parseInt(startDateElems[0]));
        LocalDate endDate = LocalDate.of(Integer.parseInt(endDateElems[2]), Integer.parseInt(endDateElems[1]), Integer.parseInt(endDateElems[0]));

        return Period.between(startDate, endDate);
    }

    public static boolean waitingTimeExists(String[] data) {
        return data.length == 5;
    }

    public static double parseWaitingTime(String[] data) {
        return Double.parseDouble(data[4]);
    }

    abstract boolean isServiceMatchAll();

    abstract int getServiceId();

    abstract int getVariationId();

    abstract boolean isQuestionTypeIdMatchAll();

    abstract int getQuestionTypeId();

    abstract int getCategoryId();

    abstract int getSubCategoryId();

    abstract String getResponseAnswer();

    abstract Calendar getDate();

    abstract Period getPeriod();
}
