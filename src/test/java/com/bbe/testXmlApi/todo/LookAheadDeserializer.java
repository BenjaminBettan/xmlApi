package com.bbe.testXmlApi.todo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.bbe.xmlapi.core.XMLEntity;

public class LookAheadDeserializer {

    private static byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(obj);
        byte[] buffer = baos.toByteArray();
        oos.close();
        baos.close();
        return buffer;
    }

    private static Object deserialize(byte[] buffer) throws IOException,
            ClassNotFoundException {
        ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
        
        // We use LookAheadObjectInputStream instead of InputStream
        ObjectInputStream ois = new LookAheadObjectInputStream(bais);
        
        Object obj = ois.readObject();
        ois.close();
        bais.close();
        return obj;
    }
 
    public static void main(String[] args) {
        try {
            // Serialize a Bicycle instance
            byte[] serializedBicycle = serialize(new XMLEntity("Unicycle"));

            // Serialize a File instance
            byte[] serializedFile = serialize(new File("Unexpected Object"));

            // Deserialize the Bicycle instance (legitimate use case)
            XMLEntity xMLEntity = (XMLEntity) deserialize(serializedBicycle);
            System.out.println(xMLEntity + " has been deserialized.");

            // Deserialize the File instance (error case)
            xMLEntity = (XMLEntity) deserialize(serializedFile);
            System.out.println(xMLEntity);

        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
    }
}