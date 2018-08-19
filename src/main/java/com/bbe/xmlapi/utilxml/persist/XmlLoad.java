package com.bbe.xmlapi.utilxml.persist;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.nio.file.Files;
import java.util.List;

import org.apache.log4j.Logger;

import com.bbe.xmlapi.core.Entity;
import com.bbe.xmlapi.core.XMLEntity;

public class XmlLoad
{
	private static final Logger logger = Logger.getLogger(XmlLoad.class);
	private XmlLoad() {}

	public static Entity serializeObjectToEntity(long l) throws ClassNotFoundException, IOException	{
		Entity e = null;
		String filePath = PersistConfigurator.convertToFilePath(l);
		File f = new File((filePath+PersistConfigurator.getPrefix()+l));
		byte[] fileContent = null;
		try {
			fileContent = Files.readAllBytes(f.toPath());
		} catch (IOException e1) {
			logger.warn(e1.getMessage());
		}
		if (fileContent !=null) {
			e = (XMLEntity) deserialize(fileContent);
		}
		return e;
	}

	private static Object deserialize(byte[] buffer){
		Object obj =null;
		try (ByteArrayInputStream bais = new ByteArrayInputStream(buffer)){
			//LookAheadObjectInputStream instead of InputStream
			try(LookAheadObjectInputStream ois = new LookAheadObjectInputStream(bais)) {
				try {
					ois.readObject();
					obj = ois.getInstance();
				} 
				catch (InvalidClassException e1) {
					logger.warn(e1.getMessage());
				}
				catch (ClassNotFoundException | IOException e2) {
					logger.warn(e2.getMessage());
				}
			} catch (IOException e3) {
				logger.warn(e3.getMessage());
			}
		} catch (IOException e5) {
			logger.warn(e5.getMessage());
		}
		return obj;
	}
	
	
	
	
	
	
	
	
	
	
	public static  Object safeReadObject(Class<?> type, List<Class<?>> safeClasses, long maxObjects, long maxBytes, InputStream in ) throws IOException, ClassNotFoundException {
	    // create an input stream limited to a certain number of bytes
	    InputStream lis = new FilterInputStream( in ) {
	        private long len = 0;
	        public int read() throws IOException {
	            int val = super.read();
	            if (val != -1) {
	                len++;
	                checkLength();
	            }
	            return val;
	        }
	        public int read(byte[] b, int off, int len) throws IOException {
	            int val = super.read(b, off, len);
	            if (val > 0) {
	                len += val;
	                checkLength();
	            }
	            return val;
	        }
	        private void checkLength() throws IOException {
	            if (len > maxBytes) {
	                throw new SecurityException("Security violation: attempt to deserialize too many bytes from stream. Limit is " + maxBytes);
	            }
	        }
	    };
	    // create an object input stream that checks classes and limits the number of objects to read
	    ObjectInputStream ois = new ObjectInputStream( lis ) {
	        private int objCount = 0;
	        boolean b = enableResolveObject(true);
	        protected Object resolveObject(Object obj) throws IOException {
	            if ( objCount++ > maxObjects ) throw new SecurityException( "Security violation: attempt to deserialize too many objects from stream. Limit is " + maxObjects );
	            Object object = super.resolveObject(obj);
	            return object;
	        }
	        protected Class<?> resolveClass(ObjectStreamClass osc) throws IOException, ClassNotFoundException {
	            Class<?> clazz = super.resolveClass(osc);
	            if (
	                clazz.isArray() ||
	                clazz.equals(type) ||
	                clazz.equals(String.class) ||
	                Number.class.isAssignableFrom(clazz) ||
	                safeClasses.contains(clazz)
	            ) return clazz;
	            throw new SecurityException("Security violation: attempt to deserialize unauthorized " + clazz);
	        }
	    };
	    // use the protected ObjectInputStream to read object safely and cast to T
	    return ois.readObject();
	}
	
	
	
	
	
	
	
	
	
	
}