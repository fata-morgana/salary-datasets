package org.kopkaj.salarydataset.service.model;

public record SortParam(SalaryDatasetField field, SortOrder order) {
    public static SortParam parse(String singleSortParam) {
        String fieldNameParam;
        SortOrder orderParam;
        if (singleSortParam.toLowerCase().endsWith("[asc]")) {
            orderParam = SortOrder.ASC;
            fieldNameParam = singleSortParam.substring(0, singleSortParam.length()-5);
        }
        else if (singleSortParam.toLowerCase().endsWith("[desc]")) {
            orderParam = SortOrder.DESC;
            fieldNameParam = singleSortParam.substring(0, singleSortParam.length()-6);
        }
        else {
            orderParam = null;
            fieldNameParam = null;
        }
        return new SortParam(SalaryDatasetField.parse(fieldNameParam), orderParam);
    }
}
