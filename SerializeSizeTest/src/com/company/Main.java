package com.company;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class Main {


    public static void main(String[] args) {

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);

            oos.writeObject(new SerializeObject());
            oos.flush();

            byte[] objectBytes = baos.toByteArray();
            System.out.println("Serialized Object size: " + objectBytes.length);

            try {
                oos.close();
                baos.close();
            }catch (IOException e) { e.printStackTrace(); }

        }catch (IOException e){e.printStackTrace();}
    }
}
