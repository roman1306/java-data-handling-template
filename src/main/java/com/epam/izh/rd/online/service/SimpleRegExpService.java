package com.epam.izh.rd.online.service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleRegExpService implements RegExpService {

    /**
     * Метод должен читать файл sensitive_data.txt (из директории resources) и маскировать в нем конфиденциальную информацию.
     * Номер счета должен содержать только первые 4 и последние 4 цифры (1234 **** **** 5678). Метод должен содержать регулярное
     * выражение для поиска счета.
     *
     * @return обработанный текст
     */
    @Override
    public String maskSensitiveData() {
        String result = "";
        try (FileReader reader = new FileReader("src/main/resources/sensitive_data.txt")) {
            StringBuilder text = new StringBuilder();
            int symbol;

            while ((symbol = reader.read()) != -1) {
                text.append((char) symbol);
            }

            result = text.toString().trim();
            Pattern pattern = Pattern.compile("\\d{4}\\s(\\d{4}\\s\\d{4})\\s\\d{4}");
            Matcher matcher = pattern.matcher(result);


            while (matcher.find()) {
                result = result.replaceAll(matcher.group(1), "**** ****");
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * Метод должен считыввать файл sensitive_data.txt (из директории resources) и заменять плейсхолдер ${payment_amount} и ${balance} на заданные числа. Метод должен
     * содержать регулярное выражение для поиска плейсхолдеров
     *
     * @return обработанный текст
     */
    @Override
    public String replacePlaceholders(double paymentAmount, double balance) {
        String result = "";
        try (FileReader reader = new FileReader("src/main/resources/sensitive_data.txt")) {
            int symbol;
            StringBuilder readerText = new StringBuilder();

            while ((symbol = reader.read()) != -1) {
                readerText.append((char) symbol);
            }

            result = readerText.toString().trim();
            result = result.replaceAll("\\$\\{\\w+_\\w+}", String.format("%.0f", paymentAmount));
            result = result.replaceAll("\\$\\{\\w+}", String.format("%.0f", balance));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
