package com.eugz;

import java.io.*;
import java.time.LocalDate;
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

//            FILTER ENTITY =================================
            List<CLineItem> targetCLines = new ArrayList<>();

            for (int i = 0; i < lines.size(); i++) {
                LineItem current = lines.get(i);

                if (current instanceof DLineItem) {
                    List<CLineItem> cLineItemsBeforeCurrent = getCLineItemBeforeIndex(lines, i);

                    if (current.isServiceMatchAll() && current.isQuestionTypeIdMatchAll()) {
//                    Match all case
                        targetCLines = cLineItemsBeforeCurrent;

                    } else if (current.isServiceMatchAll() && !current.isQuestionTypeIdMatchAll()) {
//                    Certain serviceId case and certain questionTypeId case
                        int questionTypeId = current.getQuestionTypeId();
                        Integer categoryId = current.getCategoryId();
                        Integer subcategoryId = current.getSubCategoryId();
                        targetCLines = filterByQuestionType(cLineItemsBeforeCurrent, questionTypeId, categoryId, subcategoryId);

                    } else if (!current.isServiceMatchAll() && current.isQuestionTypeIdMatchAll()) {
//                    Match all serviceId case and certain questionTypeId case
                        int serviceId = current.getServiceId();
                        Integer variationId = current.getVariationId();
                        targetCLines = filterByService(cLineItemsBeforeCurrent, serviceId, variationId);

                    } else {
//                    Certain serviceId case and match all questionTypeId case
                        int serviceId = current.getServiceId();
                        Integer variationId = current.getVariationId();
                        int questionTypeId = current.getQuestionTypeId();
                        Integer categoryId = current.getCategoryId();
                        Integer subcategoryId = current.getSubCategoryId();

                        targetCLines = getResultCLines(cLineItemsBeforeCurrent, serviceId, variationId, questionTypeId, categoryId, subcategoryId);
                    }

                    targetCLines.forEach(lineItem -> {
                        System.out.println("TARGET C-LINES");
                        if (lineItem instanceof CLineItem) {
                            System.out.println("C-line item!");
                            System.out.println(lineItem.getServiceId());
                        } else {
                            System.out.println("D-line item!");
                        }
                    });

//                    Filtering CLines by date and period

                    List<CLineItem> linesFilteredByAll = new ArrayList<>();
                    if (((DLineItem) current).isSingleDate()) {
                        linesFilteredByAll = getCLinesByDate(targetCLines, current.getDate());
                    } else {
                        linesFilteredByAll = getCLinesByPeriod(targetCLines, current.getStartDate(), current.getEndDate());
                    }

////            For debug purposes ===
//                    linesFilteredByAll.forEach(lineItem -> {
//                        System.out.println("FILTERED BY ALL");
//                        if (lineItem instanceof CLineItem) {
//                            System.out.println("C-line item!");
//                            System.out.println(lineItem.getServiceId());
//                        } else {
//                            System.out.println("D-line item!");
//                        }
//                    });
////            For debug purposes ===
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

//    TODO: refactor to separate entity ===========================================================

    public static List<CLineItem> getCLineItemBeforeIndex(List<LineItem> list, int dItemIndex) {
        List<CLineItem> items = new ArrayList<>();

        for (int i = 0; i < dItemIndex; i++) {
            LineItem item = list.get(i);
            if (item instanceof CLineItem) {
                items.add((CLineItem) item);
            }
        }

        return items;
    }

    public static List<CLineItem> getResultCLines(List<CLineItem> list, int serviceId, Integer variationId, int questionTypeId, Integer categoryId, Integer subcategoryId)  {
        List<CLineItem> filteredByService = filterByService(list, serviceId, variationId);
        List<CLineItem> filteredByAll = filterByQuestionType(filteredByService, questionTypeId, categoryId, subcategoryId);

        return filteredByAll;
    }

    public static List<CLineItem> filterByService(List<CLineItem> list, int serviceId, Integer variationId) {
        List<CLineItem> byServiceOnly = new ArrayList<>();

        for (CLineItem item : list) {
            if (item.getServiceId() == serviceId) {
                byServiceOnly.add(item);
            }
        }

        if (variationId != null) {
            List<CLineItem> byServiceAndVariation = new ArrayList<>();

            for (CLineItem item : byServiceOnly) {
                if (item.getVariationId() == variationId) {
                    byServiceAndVariation.add(item);
                }
            }

            return byServiceAndVariation;
        }

        return byServiceOnly;
    }

    public static List<CLineItem> filterByQuestionType(List<CLineItem> list, int questionTypeId, Integer categoryId, Integer subcategoryId) {
        List<CLineItem> byQuestionTypeIdOnly = new ArrayList<>();

        for (CLineItem item : list) {
            if (item.getQuestionTypeId() == questionTypeId) {
                byQuestionTypeIdOnly.add(item);
            }
        }

        if (categoryId != null) {
            List<CLineItem> byQuestionTypeAndCategory = new ArrayList<>();

            for (CLineItem item : byQuestionTypeIdOnly) {
                if (item.getCategoryId() == categoryId) {
                    byQuestionTypeAndCategory.add(item);
                }
            }

            if (subcategoryId != null) {
                List<CLineItem> byQuestionAndCategoryAndSubcategory = new ArrayList<>();

                for (CLineItem item : byQuestionTypeAndCategory) {
                    if (item.getSubCategoryId() == subcategoryId) {
                        byQuestionAndCategoryAndSubcategory.add(item);
                    }
                }

                return byQuestionAndCategoryAndSubcategory;
            }

            return byQuestionTypeAndCategory;
        }

        return byQuestionTypeIdOnly;
    }

    public static List<CLineItem> getCLinesByDate(List<CLineItem> list, LocalDate date) {
        List<CLineItem> filteredByDate = new ArrayList<>();

        for (CLineItem item : list) {
            if (item.getDate().isEqual(date)) {
                filteredByDate.add(item);
            }
        }

        return filteredByDate;
    }

    public static List<CLineItem> getCLinesByPeriod(List<CLineItem> list, LocalDate startDate, LocalDate endDate) {
        List<CLineItem> filteredByPeriod = new ArrayList<>();

        for (CLineItem item : list) {
            LocalDate itemDate = item.getDate();
            if (itemDate.isAfter(startDate) && itemDate.isBefore(endDate)) {
                filteredByPeriod.add(item);
            }
        }

        return filteredByPeriod;
    }
}
