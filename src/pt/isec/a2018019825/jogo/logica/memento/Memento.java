package pt.isec.a2018019825.jogo.logica.memento;

import java.io.*;

public class Memento {
    private byte[] snapshot = null;

    public Memento(Object obj) throws IOException{
        ByteArrayOutputStream baos;
        ObjectOutputStream oos = null;

        try{
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(obj);
            snapshot = baos.toByteArray();
        }finally {
            if (oos!=null)
                oos.close();
        }
    }

    public Object getSnapShot() throws IOException, ClassNotFoundException{
        ObjectInputStream ois = null;
        if (snapshot==null)
            return null;
        try{
            ois = new ObjectInputStream(new ByteArrayInputStream(snapshot));
            return ois.readObject();
        }finally {
            if (ois!=null)
                ois.close();
        }
    }
}
