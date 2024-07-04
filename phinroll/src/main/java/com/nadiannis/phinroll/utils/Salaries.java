package com.nadiannis.phinroll.utils;

import java.math.BigDecimal;

public class Salaries {

    public static BigDecimal calculateTotalPayCut(Integer absenceDays, BigDecimal payCut) {
        return payCut.multiply(BigDecimal.valueOf(absenceDays));
    }

    public static BigDecimal calculateTotalAllowance(Integer attendanceDays, BigDecimal allowance) {
        return allowance.multiply(BigDecimal.valueOf(attendanceDays));
    }

    public static BigDecimal calculateTotalSalary(BigDecimal basicSalary, BigDecimal totalAdditionalSalary, BigDecimal totalPayCut) {
        return basicSalary.add(totalAdditionalSalary).subtract(totalPayCut);
    }

}
