package com.epam.izh.rd.online.repository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SimpleFileRepository implements FileRepository {

    /**
     * Метод рекурсивно подсчитывает количество файлов в директории
     *
     * @param path путь до директори
     * @return файлов, в том числе скрытых
     */
    @Override
    public long countFilesInDirectory(String path) {
        File file = new File("src/main/resources/" + path);
        long count = 0L;

        if (file.isDirectory()) {
            for (File value : file.listFiles()) {
                count += countFilesInDirectory(path + "/" + value.getName());
            }
        } else {
            count++;
        }

        return count;
    }

    /**
     * Метод рекурсивно подсчитывает количество папок в директории, считая корень
     *
     * @param path путь до директории
     * @return число папок
     */
    @Override
    public long countDirsInDirectory(String path) {
        File file = new File("src/main/resources/" + path);
        long count = 0L;
        if (file.isDirectory()) {
            count++;
            for (File value : file.listFiles()) {
                count += countDirsInDirectory(path + "/" + value.getName());
            }
        }

        return count;
    }

    /**
     * Метод копирует все файлы с расширением .txt
     *
     * @param from путь откуда
     * @param to   путь куда
     */
    @Override
    public void copyTXTFiles(String from, String to) {
        File dir = new File(from);

        if (dir.isFile()) {
            new File(to).mkdir();
            if (dir.getName().endsWith(".txt")) {
                try {
                    Files.copy(Paths.get(from), Paths.get(to));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            for (File file : dir.listFiles()) {
                copyTXTFiles(from + "/" + file.getName(), to);
            }
        }
    }

    /**
     * Метод создает файл на диске с расширением txt
     *
     * @param path путь до нового файла
     * @param name имя файла
     * @return был ли создан файл
     */
    @Override
    public boolean createFile(String path, String name) {
        File dir = new File("src/main/resources/" + path);
        File file = new File("src/main/resources/" + path + "/" + name);

        if (!file.exists()) {
            dir.mkdir();
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Метод считывает тело файла .txt из папки src/main/resources
     *
     * @param fileName имя файла
     * @return контент
     */
    @Override
    public String readFileFromResources(String fileName) {
        StringBuilder text = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/" + fileName))) {
            String string;

            while ((string = reader.readLine()) != null) {
                text.append(string);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return text.toString();
    }
}
