package com.epam.izh.rd.online.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public class SimpleBigNumbersService implements BigNumbersService {

    /**
     * Метод делит первое число на второе с заданной точностью
     * Например 1/3 с точностью 2 = 0.33
     * @param range точность
     * @return результат
     */
    @Override
    public BigDecimal getPrecisionNumber(int a, int b, int range) {
        return new BigDecimal(a).divide(new BigDecimal(b), range, RoundingMode.HALF_UP);
    }

    /**
     * Метод находит простое число по номеру
     *
     * @param range номер числа, считая с числа 2
     * @return простое число
     */
    @Override
    public BigInteger getPrimaryNumber(int range) {
        int countNumberPrimary = 0;
        int number = 1;

        while (countNumberPrimary <= range) {
            number++;
            boolean isPrimary = true;

            for (int i = 2; i <= number / i ; i++) {
                if (number % i == 0) {
                    isPrimary = false;
                    break;
                }
            }

            if (isPrimary) {
                countNumberPrimary++;
            }

        }

        return BigInteger.valueOf(number);
    }
}
