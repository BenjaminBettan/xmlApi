package com.bbe.xmlapi.util.persist;
import java.io.IOException;
import java.io.InputStream;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.util.ArrayList;

import com.bbe.xmlapi.core.Entity;
import com.bbe.xmlapi.core.VirtualXMLEntity;
import com.bbe.xmlapi.core.XMLEntity;

public class ExtendedObjectInputStream extends ObjectInputStream {
	
	public ExtendedObjectInputStream(InputStream inputStream) throws IOException {
		super(inputStream);
	}

	/**
	 * Only deserialize instances of our expected Entity class
	 */
	@Override
	protected Class<?> resolveClass(ObjectStreamClass desc) throws IOException,
	ClassNotFoundException,InvalidClassException {

		Class<?> instance = super.resolveClass(desc);

		if (instance.isArray()
				|| instance.isPrimitive()
				|| instance.equals(Entity.class)
				|| instance.equals(VirtualXMLEntity.class)
				|| instance.equals(ArrayList.class)
				|| instance.equals(XMLEntity.class)
				|| instance.equals(String.class)
				|| Number.class.isAssignableFrom(instance)) {
			return instance;
		}
		else {
			throw new InvalidClassException("Unauthorized deserialization attempt",	desc.getName());
		}
	}
}