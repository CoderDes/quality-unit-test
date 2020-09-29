package com.eugz;

import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;

public class DLineItem extends LineItem {
    private boolean isServiceMatchAll;
    private Integer serviceId;
    private Integer variationId;

    private boolean isQuestionTypeIdMatchAll;
    private Integer questionTypeId;
    private Integer categoryId;
    private Integer subCategoryId;

    private String responseAnswer;

    private boolean isSingleDate;
    private Calendar date;
    private LocalDate startDate;
    private LocalDate endDate;

    public DLineItem(String[] data) {

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

    public boolean isSingleDate() {
        return isSingleDate;
    }

    @Override
    Calendar getDate() {
        return date;
    }

    @Override
    LocalDate getStartDate() {
        return null;
    }

    @Override
    LocalDate getEndDate() {
        return null;
    }
}
