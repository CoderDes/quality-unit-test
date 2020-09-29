package com.eugz;

import java.time.LocalDate;

public class CLineItem extends LineItem {
    private boolean isServiceMatchAll;
    private Integer serviceId;
    private Integer variationId;

    private boolean isQuestionTypeIdMatchAll;
    private Integer questionTypeId;
    private Integer categoryId;
    private Integer subCategoryId;

    private String responseAnswer;

    private boolean isSingleDate;
    private LocalDate date;
    private LocalDate startDate;
    private LocalDate endDate;

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
            isSingleDate = true;
            date = super.parseDate(data);
            startDate = null;
            endDate = null;
        } else {
            date = null;
            startDate = super.parseStartDate(data);
            endDate = super.parseEndDate(data);
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
    Integer getVariationId() {
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
    Integer getCategoryId() {
        return categoryId;
    }

    @Override
    Integer getSubCategoryId() {
        return subCategoryId;
    }

    @Override
    String getResponseAnswer() {
        return responseAnswer;
    }

    public boolean isSingleDate() {
        return isSingleDate;
    }

    @Override
    LocalDate getDate() {
        return date;
    }

    @Override
    LocalDate getStartDate() {
        return startDate;
    }

    @Override
    LocalDate getEndDate() {
        return endDate;
    }

    public double getTimeWaitingInMinutes() {
        return timeWaitingInMinutes;
    }
}
