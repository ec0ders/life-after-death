package fr.ecoders.lad;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.lang.reflect.Constructor;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.RecordComponent;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.List;
import java.util.RandomAccess;

public final class Utils {
  private Utils() {
    throw new AssertionError();
  }

  @SuppressWarnings("unchecked")   // very wrong but works
  private static <T extends Throwable> AssertionError rethrow(Throwable cause) throws T {
    throw (T) cause;
  }

  public static <T> Constructor<T> canonicalConstructor(Class<T> recordClass, RecordComponent[] components) {
    try {
      return recordClass.getConstructor(Arrays.stream(components).map(RecordComponent::getType).toArray(Class[]::new));
    } catch (NoSuchMethodException e) {
      throw (NoSuchMethodError) new NoSuchMethodError("no public canonical constructor " + recordClass.getName()).initCause(e);
    }
  }

  public static <T> T newInstance(Constructor<T> constructor, Object... args) {
    try {
      return constructor.newInstance(args);
    } catch (IllegalArgumentException e) {
      throw new AssertionError(e);
    } catch (InstantiationException e) {
      throw (InstantiationError) new InstantiationError().initCause(e);
    } catch (IllegalAccessException e) {
      throw (IllegalAccessError) new IllegalAccessError().initCause(e);
    } catch (InvocationTargetException e) {
      throw rethrow(e.getCause());
    }
  }
  
}