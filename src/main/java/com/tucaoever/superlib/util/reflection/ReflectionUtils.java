package com.tucaoever.superlib.util.reflection;

import ch.njol.skript.Skript;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Just a simple reflection class
 *
 * @author Tuke_Nuke
 */
public class ReflectionUtils {
	private static final String VERSION = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3] + ".";
	private static final boolean NEW_NMS = Skript.isRunningMinecraft(1, 17);

	/**
	 * @param clazz The class of the method
	 * @param method The method to invoke
	 * @param instance The instance for the method to be invoked from
	 * @param parameters The parameters of the method
	 * @return The result of the method, or null if the method was null or the invocation failed
	 */
	@SuppressWarnings("unchecked")
	public static <T> T invokeMethod(Class<?> clazz, String method, Object instance, Object... parameters) {
		try {
			Class<?>[] parameterTypes = new Class<?>[parameters.length];
			int x = 0;

			for (Object obj : parameters)
				parameterTypes[x++] = obj.getClass();

			Method m = clazz.getDeclaredMethod(method, parameterTypes);
			m.setAccessible(true);

			return (T) m.invoke(instance, parameters);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @param clazz The class to create the instance of.
	 * @return A instance object of the given class.
	 */
	public static <T> T newInstance(Class<T> clazz) {
		try {
			Constructor<T> c = clazz.getDeclaredConstructor();
			c.setAccessible(true);
			return c.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @param from The class of the field
	 * @param obj The instance of the class - you can use null if the field is static
	 * @param field The field name
	 * @return True if the field was successfully set
	 */
	public static <T> boolean setField(Class<T> from, Object obj, String field, Object newValue) {
		try {
			Field f = from.getDeclaredField(field);
			f.setAccessible(true);
			f.set(obj, newValue);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * @param from The class of the field
	 * @param obj The instance of the class - you can use null if the field is static
	 * @param field The field name
	 * @return The field or null if it couldn't be gotten
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getField(Class<?> from, Object obj, String field) {
		try {
			Field f = from.getDeclaredField(field);
			f.setAccessible(true);
			return (T) f.get(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	public static Class<?> getOBCClass(String obcClassString) {
		String name = "org.bukkit.craftbukkit." + VERSION + obcClassString;
		try {
			return Class.forName(name);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Class<?> getNMSClass(String nmsClass, String nmsPackage) {
		try {
			if (NEW_NMS) {
				return Class.forName(nmsPackage + "." + nmsClass);
			} else {
				return Class.forName("net.minecraft.server." + VERSION + nmsClass);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Object getNMSHandle(Entity entity) {
		try {
			Method getHandle = entity.getClass().getMethod("getHandle");
			return getHandle.invoke(entity);
		} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Object getField(String field, Class<?> clazz, Object object) {
		try {
			Field f = clazz.getDeclaredField(field);
			f.setAccessible(true);
			return f.get(object);
		} catch (IllegalAccessException | NoSuchFieldException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public static void setField(String field, Class<?> clazz, Object object, Object toSet) {
		try {
			Field f = clazz.getDeclaredField(field);
			f.setAccessible(true);
			f.set(object, toSet);
		} catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException ex) {
			ex.printStackTrace();
		}
	}

	public static void setField(String field, Object object, Object toSet) {
		try {
			Field f = object.getClass().getDeclaredField(field);
			f.setAccessible(true);
			f.set(object, toSet);
		} catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException ex) {
			ex.printStackTrace();
		}
	}

	public static Object getConnection(Player player)
			throws SecurityException, NoSuchMethodException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		Field conField = getHandle(player).getClass().getField("playerConnection");
		return conField.get(getHandle(player));
	}

	public static Object getHandle(Player player)
			throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		Method getHandle = player.getClass().getMethod("getHandle");
		return getHandle.invoke(player);
	}

	/**
	 * Check if a class and method exist
	 *
	 * @param className  Class to check
	 * @param methodName Method to check
	 * @return True if both class and method exist
	 */
	public static boolean methodExists(String className, String methodName) {
		if (Skript.classExists(className)) {
			try {
				Class<?> clazz = Class.forName(className);
				if (Skript.methodExists(clazz, methodName)) {
					return true;
				}
			} catch (ClassNotFoundException ignore) {
			}
		}
		return false;
	}
}
