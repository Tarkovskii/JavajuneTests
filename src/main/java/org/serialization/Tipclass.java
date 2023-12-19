package org.serialization;

import java.io.Serializable;

public class Tipclass implements Serializable {

    private String name;
    private int id;

    public Tipclass(String name, int id) {
        this.name = name;
        this.id = id;
    }



    public Tipclass(String name) {
        this(name,20);
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Tipclass{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
