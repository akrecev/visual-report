package com.rokdc.report.utils;

import com.rokdc.report.exception.NoSuchValue;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashSet;
import java.util.function.Function;


public class Tools {

    public static <T, R> boolean areAllElementsUnique(Collection<T> t, Function<T, R> r) {
        return t
                .stream()
                .map(r)
                .allMatch(new HashSet<>()::add);
    }


    public static <T extends Enum<T>> T checkEnumValue(Class<T> enumType, final String type) throws NoSuchValue {

        try {
            return Enum.valueOf(enumType, type.toUpperCase());

        } catch (IllegalArgumentException ex) {
            throw new NoSuchValue("Неверный тип " + enumType.getName() + ": " + type.toLowerCase());
        }

    }


    public static File convertStringToFile(String path) throws FileNotFoundException {
        File tmp = Paths.get(path).toFile();

        if (tmp.exists()
                && Files.isRegularFile(tmp.toPath())) {
            throw new FileNotFoundException(tmp.getAbsolutePath() + " неверный путь к файлу");
        }


        return tmp;
    }


}
