package org.serialization;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {

        writingClass(new Tipclass("Man"));
        readingClass("test_hw.Tipclass_605dc902-cf1c-41d8-be93-505a5d5545f8.txt");



    }

    /**
     * 1. принимает объекты, имплементирующие интерфейс serializable,
     * и сохраняющие их в файл. Название файл - class.getName() + "_" + UUID.randomUUID().toString()
     * @param o
     */
    static void writingClass(Object o){
        try {
            OutputStream outputStream = Files.newOutputStream(Path.of(o.getClass().getName()+"_"+ UUID.randomUUID().toString()+".txt"));
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(o);
            objectOutputStream.close();


        } catch (IOException e) {
            throw new RuntimeException("Ошибка объекта");
        }

    }

    /**
     * 2. принимает строку вида class.getName() + "_" + UUID.randomUUID().toString()
     * и загружает объект из файла и удаляет этот файл.
     *
     * @param nameFile
     */

    static void readingClass(String nameFile){
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(Files.newInputStream(Path.of(nameFile)));
            try {
                Object comeBackObject = objectInputStream.readObject();
                System.out.println(new Tipclass(comeBackObject.getClass().getName()));
                objectInputStream.close();
                File file = new File(nameFile);
                file.delete();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("Класс отсутствует");
            }
        } catch (IOException e) {
            throw new RuntimeException("Ошибка файла");
        }
    }
}
