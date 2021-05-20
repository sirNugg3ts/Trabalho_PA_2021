package pt.isec.a2018019825.jogo.logica;

import pt.isec.a2018019825.jogo.logica.dados.Jogo4EmLinha;

import java.io.*;

public class SaveAndLoad {
    public static void saveState(Object obj,String nome){
        try{
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nome));

            oos.writeObject(obj);
            oos.close();
            return;
        } catch (Exception e) {
            System.err.println(e);
            return;
        }
    }

    public static Jogo4EmLinha loadGame(String nome){
        Jogo4EmLinha restoredGame;

        try{
            ObjectInputStream ois  = new ObjectInputStream(new FileInputStream(nome));

            restoredGame = (Jogo4EmLinha) ois.readObject();
            ois.close();
            return restoredGame;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;

    }
}
