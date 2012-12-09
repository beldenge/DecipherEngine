package com.ciphertool.zodiacengine;

import static org.junit.Assert.fail;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class GenericTestBase {
	/**
	 * Utility method for setting private properties on real and mock objects
	 * 
	 * @param instance
	 *            the object instance
	 * @param c
	 *            the type of the object
	 * @param field
	 *            the field to set
	 * @param val
	 *            the value to set the field to
	 */
	protected static void setObject(final Object instance, final Class<?> c, final String field,
			final Object val) {
		try {
			Field f = c.getDeclaredField(field);
			f.setAccessible(true); // in case the property is private
			f.set(instance, val);
		} catch (NoSuchFieldException e) {
			fail(e.getMessage());
		} catch (IllegalArgumentException e) {
			fail(e.getMessage());
		} catch (IllegalAccessException e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Utility method for invoking private methods on real and mock objects
	 * 
	 * @param instance
	 *            the object instance
	 * @param method
	 *            the method to invoke
	 * @param params
	 *            the method parameters
	 * @param args
	 *            the method arguments
	 * @throws InvocationTargetException
	 *             if an exception is thrown within the called method
	 */
	protected static Object invokeMethod(final Object instance, final String method,
			final Class<?>[] params, final Object[] args) throws InvocationTargetException {
		try {
			Method m = instance.getClass().getDeclaredMethod(method, params);
			m.setAccessible(true); // in case the method is private
			return m.invoke(instance, args);
		} catch (IllegalArgumentException e) {
			fail(e.getMessage());
		} catch (IllegalAccessException e) {
			fail(e.getMessage());
		} catch (NoSuchMethodException e) {
			fail(e.getMessage());
		}

		return null;
	}
}
