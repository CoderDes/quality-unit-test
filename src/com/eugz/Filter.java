package com.eugz;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Filter {

    public void start(List<LineItem> lines) {
        for (int i = 0; i < lines.size(); i++) {
            LineItem current = lines.get(i);

            if (current instanceof DLineItem) {
                DLineItem currentDItem = (DLineItem) current;

                List<CLineItem> cLineItemsByServQuest = this.getCLinesByServiceAndQuestion(lines, currentDItem, i);
                List<CLineItem> cLineItemsByServQuestResp = this.getCLinesByResponseType(cLineItemsByServQuest, currentDItem.getResponseAnswer());
                List<CLineItem> cLineItemsByServQuestRespDate = this.getCLinesByTime(cLineItemsByServQuestResp, currentDItem);

                String result = this.calcResultFromFiltered(cLineItemsByServQuestRespDate);
                System.out.println(result);
            }
        }
    }

    public List<CLineItem> getCLineItemBeforeIndex(List<LineItem> list, int dItemIndex) {
        List<CLineItem> items = new ArrayList<>();

        for (int i = 0; i < dItemIndex; i++) {
            LineItem item = list.get(i);
            if (item instanceof CLineItem) {
                items.add((CLineItem) item);
            }
        }

        return items;
    }

    public List<CLineItem> getCLinesByServiceAndQuestion(List<LineItem> allLines, DLineItem current, int currIndex) {
        List<CLineItem> cLineItemsBeforeCurrent = getCLineItemBeforeIndex(allLines, currIndex);

        if (current.isServiceMatchAll() && current.isQuestionTypeIdMatchAll()) {
            return cLineItemsBeforeCurrent;
        } else if (current.isServiceMatchAll() && !current.isQuestionTypeIdMatchAll()) {
            int questionTypeId = current.getQuestionTypeId();
            Integer categoryId = current.getCategoryId();
            Integer subcategoryId = current.getSubCategoryId();

            return filterByQuestionType(cLineItemsBeforeCurrent, questionTypeId, categoryId, subcategoryId);
        } else if (!current.isServiceMatchAll() && current.isQuestionTypeIdMatchAll()) {
            int serviceId = current.getServiceId();
            Integer variationId = current.getVariationId();

            return filterByService(cLineItemsBeforeCurrent, serviceId, variationId);
        } else {
            int serviceId = current.getServiceId();
            Integer variationId = current.getVariationId();
            int questionTypeId = current.getQuestionTypeId();
            Integer categoryId = current.getCategoryId();
            Integer subcategoryId = current.getSubCategoryId();

            return getResultCLines(cLineItemsBeforeCurrent, serviceId, variationId, questionTypeId, categoryId, subcategoryId);
        }
    }

    public List<CLineItem> getResultCLines(List<CLineItem> list, int serviceId, Integer variationId, int questionTypeId, Integer categoryId, Integer subcategoryId)  {
        List<CLineItem> filteredByService = filterByService(list, serviceId, variationId);
        List<CLineItem> filteredByAll = filterByQuestionType(filteredByService, questionTypeId, categoryId, subcategoryId);

        return filteredByAll;
    }

    public List<CLineItem> filterByService(List<CLineItem> list, int serviceId, Integer variationId) {
        List<CLineItem> byServiceOnly = new ArrayList<>();

        for (CLineItem item : list) {
            if (item.getServiceId() == serviceId) {
                byServiceOnly.add(item);
            }
        }

        if (variationId != null) {
            List<CLineItem> byServiceAndVariation = new ArrayList<>();

            for (CLineItem item : byServiceOnly) {
                if (item.getVariationId().equals(variationId)) {
                    byServiceAndVariation.add(item);
                }
            }

            return byServiceAndVariation;
        }

        return byServiceOnly;
    }

    public List<CLineItem> filterByQuestionType(List<CLineItem> list, int questionTypeId, Integer categoryId, Integer subcategoryId) {
        List<CLineItem> byQuestionTypeIdOnly = new ArrayList<>();

        for (CLineItem item : list) {
            if (item.getQuestionTypeId() == questionTypeId) {
                byQuestionTypeIdOnly.add(item);
            }
        }

        if (categoryId != null) {
            List<CLineItem> byQuestionTypeAndCategory = new ArrayList<>();

            for (CLineItem item : byQuestionTypeIdOnly) {
                if (item.getCategoryId().equals(categoryId)) {
                    byQuestionTypeAndCategory.add(item);
                }
            }

            if (subcategoryId != null) {
                List<CLineItem> byQuestionAndCategoryAndSubcategory = new ArrayList<>();

                for (CLineItem item : byQuestionTypeAndCategory) {
                    if (item.getSubCategoryId().equals(subcategoryId)) {
                        byQuestionAndCategoryAndSubcategory.add(item);
                    }
                }

                return byQuestionAndCategoryAndSubcategory;
            }

            return byQuestionTypeAndCategory;
        }

        return byQuestionTypeIdOnly;
    }

    public List<CLineItem> getCLinesByResponseType(List<CLineItem> list, String responseType) {
        List<CLineItem> filteredByResponseType = new ArrayList<>();

        for (CLineItem item : list) {
            if (item.getResponseAnswer().equals(responseType)) {
                filteredByResponseType.add(item);
            }
        }

        return filteredByResponseType;
    }

    public List<CLineItem> getCLinesByTime(List<CLineItem> list, DLineItem current) {

        if (current.isSingleDate()) {
            return getCLinesByDate(list, current.getDate());
        } else {
            return getCLinesByPeriod(list, current.getStartDate(), current.getEndDate());
        }

    }

    public List<CLineItem> getCLinesByDate(List<CLineItem> list, LocalDate date) {
        List<CLineItem> filteredByDate = new ArrayList<>();

        for (CLineItem item : list) {
            if (item.getDate().isEqual(date)) {
                filteredByDate.add(item);
            }
        }

        return filteredByDate;
    }

    public List<CLineItem> getCLinesByPeriod(List<CLineItem> list, LocalDate startDate, LocalDate endDate) {
        List<CLineItem> filteredByPeriod = new ArrayList<>();

        for (CLineItem item : list) {
            LocalDate itemDate = item.getDate();
            if (itemDate.isAfter(startDate) && itemDate.isBefore(endDate)) {
                filteredByPeriod.add(item);
            }
        }

        return filteredByPeriod;
    }

    public String calcResultFromFiltered(List<CLineItem> list) {

        if (list.size() > 1) {
            Double sumWatingTimeInMinutes = 0d;

            for (CLineItem item : list) {
                sumWatingTimeInMinutes = Double.sum(sumWatingTimeInMinutes, item.getTimeWaitingInMinutes());
            }

            Double average = sumWatingTimeInMinutes / list.size();

            return average.toString();
        } else if (list.size() > 0){
            return list.get(0).getTimeWaitingInMinutes().toString();
        }

        return "-";
    }
}
