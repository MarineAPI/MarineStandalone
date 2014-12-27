package org.marinemc.io.data;
/**
 * 
 * Diffrent utils for array manipulation of byte arrays
 * 
 * @author Fozie
 *
 */
public class ByteUtils {
	public final static Byte[] wrap(final byte[] array) {
        if (array == null)
            return null;
        else if (array.length == 0)
            return new Byte[]{};

        final Byte[] result = new Byte[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = new Byte(array[i]);
        }
        return result;
    }
	
	public static byte[] merge(byte[]... data) {
		int size = 0;
		for(byte[] a : data) 
			size += a.length;
		
		byte[] r = new byte[size];

		for(byte[] a : data)
			combine(r,a);
	
		return r;
	}
	
    public static byte[] combine(byte[] array1, byte[] array2) {
        if (array1 == null) {
            return copy(array2);
        } else if (array2 == null) {
            return copy(array1);
        }
        byte[] joinedArray = new byte[array1.length + array2.length];
        System.arraycopy(array1, 0, joinedArray, 0, array1.length);
        System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
        return joinedArray;
    }
   
    public static byte[] copy(byte[] array) {
        if (array == null) {
            return null;
        }
        return (byte[]) array.clone();
    }
    
    public static byte[] unwrap(final Byte[] array) {
        if (array == null)
            return null;
        else if (array.length == 0)
            return new byte[]{};

        final byte[] result = new byte[array.length];
        for (int i = 0; i < array.length; i++) {
            Byte b = array[i];
            result[i] = (b == null ? 0 : b.byteValue());
        }
        return result;
    }
    
    public static byte[] insert(final byte[] input, final byte[] data) {
    	byte[] r = extendArray(input, input.length + data.length);
    	System.arraycopy(data, 0, r, input.length, r.length);
    	return r;
    }
    
    public static byte[] extendArray(final byte[] input, final int offset) {
    	return resize(input, input.length + offset);
    }
    
    public static byte[] resize(final byte[] input, final int length) {
    	byte[] r = new byte[length];
    	System.arraycopy(input, 0, r, 0, length);
    	return r;
    }
}