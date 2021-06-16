package pt.isec.a2018019825.jogo.logica;

import pt.isec.a2018019825.jogo.logica.memento.CareTaker;

import java.io.*;

public class SaveAndLoad {

    public static boolean saveState(Object obj, CareTaker caretaker, File nome) throws IOException {

        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nome));

        Object[] objects = {obj, caretaker};

        oos.writeObject(objects);
        oos.close();
        return true;

    }

    public static Object[] loadGame(File nome) throws Exception {

        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nome));

        Object[] objects;

        objects = (Object[]) ois.readObject();


        ois.close();
        return objects;
    }
}
