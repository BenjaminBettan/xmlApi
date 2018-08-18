package com.bbe.testXmlApi.todo;
import java.io.IOException;
import java.io.InputStream;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;

import com.bbe.xmlapi.core.Entity;
import com.bbe.xmlapi.core.VirtualXMLEntity;
import com.bbe.xmlapi.core.XMLEntity;

public class LookAheadObjectInputStream extends ObjectInputStream {

    public LookAheadObjectInputStream(InputStream inputStream)
            throws IOException {
        super(inputStream);
    }

    /**
     * Only deserialize instances of our expected Bicycle class
     */
    @Override
    protected Class<?> resolveClass(ObjectStreamClass desc) throws IOException,
            ClassNotFoundException {
        if (desc.getName().equals(XMLEntity.class.getName()) || desc.getName().equals(VirtualXMLEntity.class.getName()) || desc.getName().equals(Entity.class.getName())) {
        	return super.resolveClass(desc);
        }
        else {
        	throw new InvalidClassException(
                    "Unauthorized deserialization attempt",
                    desc.getName());
		}
        
    }
}