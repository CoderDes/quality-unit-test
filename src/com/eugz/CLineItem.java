package com.eugz;

import java.time.Period;
import java.util.Calendar;

public class CLineItem extends LineItem {
    private boolean isServiceMatchAll;
    private Integer serviceId;
    private Integer variationId;

    private boolean isQuestionTypeIdMatchAll;
    private Integer questionTypeId;
    private Integer categoryId;
    private Integer subCategoryId;

    private String responseAnswer;

    private Calendar date;
    private Period period;

    private Double timeWaitingInMinutes;

    public CLineItem(String[] data) {

        isServiceMatchAll = super.serviceIdMatchAll(data);
        if (!isServiceMatchAll) {
            serviceId = super.parseServiceId(data);
            variationId = super.variationExists(data) ? super.parseVariationId(data) : null;
        }

        isQuestionTypeIdMatchAll = super.questionTypeDataMatchAll(data);
        if (!isQuestionTypeIdMatchAll) {
            questionTypeId = super.parseQuestionTypeId(data);
            categoryId = super.categoryIdExists(data) ? super.parseCategoryId(data) : null;
            subCategoryId = super.subCategoryExists(data) ? super.parseSubCategory(data) : null;
        }

        responseAnswer = data[2];

        if (!super.isPeriod(data)) {
            date = super.parseDate(data);
            period = null;
        } else {
            date = null;
            period = super.parsePeriod(data);
        }

        timeWaitingInMinutes = super.waitingTimeExists(data) ? super.parseWaitingTime(data) : null;
    }

    @Override
    boolean isServiceMatchAll() {
        return isServiceMatchAll;
    }

    @Override
    int getServiceId() {
        return serviceId;
    }

    @Override
    int getVariationId() {
        return variationId;
    }

    @Override
    boolean isQuestionTypeIdMatchAll() {
        return isQuestionTypeIdMatchAll;
    }

    @Override
    int getQuestionTypeId() {
        return questionTypeId;
    }

    @Override
    int getCategoryId() {
        return categoryId;
    }

    @Override
    int getSubCategoryId() {
        return subCategoryId;
    }

    @Override
    String getResponseAnswer() {
        return responseAnswer;
    }

    @Override
    Calendar getDate() {
        return date;
    }

    @Override
    Period getPeriod() {
        return period;
    }

    public double getTimeWaitingInMinutes() {
        return timeWaitingInMinutes;
    }
}
