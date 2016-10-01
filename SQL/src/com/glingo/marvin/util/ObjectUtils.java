package com.glingo.marvin.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ObjectUtils {
	
	private static final Pattern SETTER_PATTERN = Pattern.compile("set([A-Z][A-Za-z0-9]*)$");
    private static final Pattern GETTER_PATTERN = Pattern.compile("(get|is|has)([A-Z][A-Za-z0-9]*)$");

    /**
     * Adds all fields with the specified Annotation of class clazz and its superclasses to allFields
     *
     * @param annotationClass
     * @param clazz
     * @param allFields
     */
    public static void addAllFields(Class<? extends Annotation> annotationClass, Class<?> clazz, List<Field> allFields) {

        if (clazz == null) {
            return;
        }

        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            Annotation ann = field.getAnnotation(annotationClass);
            if (ann!=null) {
                allFields.add(field);
            }
        }
        
        addAllFields(annotationClass, clazz.getSuperclass(), allFields);
    }

    /**
     * Adds all methods with the specified Annotation of class clazz and its superclasses to allFields
     *
     * @param annotationClass
     * @param clazz
     * @param allMethods
     */
    public static void addAllMethods(Class<? extends Annotation> annotationClass, Class<?> clazz, List<Method> allMethods) {

        if (clazz == null) {
            return;
        }

        Method[] methods = clazz.getDeclaredMethods();

        for (Method method : methods) {
            Annotation ann = method.getAnnotation(annotationClass);
            if (ann!=null) {
                allMethods.add(method);
            }
        }
        addAllMethods(annotationClass, clazz.getSuperclass(), allMethods);
    }

    /**
     *
     * @param clazz
     * @param allInterfaces
     */
    public static void addAllInterfaces(Class<?> clazz, List<Class<?>> allInterfaces) {
        if (clazz == null) {
            return;
        }

        Class<?>[] interfaces = clazz.getInterfaces();
        allInterfaces.addAll(Arrays.asList(interfaces));
        addAllInterfaces(clazz.getSuperclass(), allInterfaces);
    }

	/**
	 * For the given <code>Class</code> get a collection of the the {@link AnnotatedElement}s 
	 * that match the given <code>annotation</code>s or if no <code>annotation</code>s are 
	 * specified then return all of the annotated elements of the given <code>Class</code>. 
	 * Includes only the method level annotations.
	 * 
	 * @param clazz The {@link Class} to inspect
	 * @param annotation the {@link Annotation}s to find
	 * @return A {@link Collection}&lt;{@link AnnotatedElement}&gt; containing all of the
	 *  method {@link AnnotatedElement}s matching the specified {@link Annotation}s
	 */
	@SafeVarargs
	public static Collection<Method> getAnnotatedMethods(Class<?> clazz, Class<? extends Annotation>... annotation){
		Collection<Method> toReturn = new HashSet<Method>();
		
		for(Method m : clazz.getMethods()){
			if( !ArrayUtils.isEmpty(annotation) && isAnnotatedBy(m, annotation) ){
				toReturn.add(m);
			}else if( ArrayUtils.isEmpty(annotation) && !ArrayUtils.isEmpty(m.getAnnotations())){
				toReturn.add(m);
			}
		}
		
		return toReturn;
	}

	/**
	 * Varargs version of <code>AnnotatedElement.isAnnotationPresent()</code>
	 * @see AnnotatedElement
	 */
	@SafeVarargs
	public static boolean isAnnotatedBy(AnnotatedElement annotatedElement, Class<? extends Annotation>... annotation) {
		if(ArrayUtils.isEmpty(annotation)) return false;

		for( Class<? extends Annotation> c : annotation ){
			if( annotatedElement.isAnnotationPresent(c) ) return true;
		}

		return false;
	}

    /**
     * Returns the property name for a method.
     * This method is independent from property fields.
     *
     * @param method The method to get the property name for.
     * @return the property name for given method; null if non could be resolved.
     */
    public static String resolvePropertyName(Method method) {

        Matcher matcher = SETTER_PATTERN.matcher(method.getName());
        if (matcher.matches() && method.getParameterTypes().length == 1) {
            String raw = matcher.group(1);
            return raw.substring(0, 1).toLowerCase() + raw.substring(1);
        }

        matcher = GETTER_PATTERN.matcher(method.getName());
        if (matcher.matches() && method.getParameterTypes().length == 0) {
            String raw = matcher.group(2);
            return raw.substring(0, 1).toLowerCase() + raw.substring(1);
        }

        return null;
    }

    /**
     * Returns the annotation on the given class or the package of the class. This searchs up the
     * class hierarchy and the package hierarchy for the closest match.
     *
     * @param   klass The class to search for the annotation.
     * @param   annotationClass The Class of the annotation.
     * @return  The annotation or null.
     */
    public static <T extends Annotation> T findAnnotation(Class<?> klass, Class<T> annotationClass) {
        T ann = klass.getAnnotation(annotationClass);
        while (ann == null && klass != null) {
            ann = klass.getAnnotation(annotationClass);
            if (ann == null)
                ann = klass.getPackage().getAnnotation(annotationClass);
            if (ann == null) {
                klass = klass.getSuperclass();
                if (klass != null ) {
                    ann = klass.getAnnotation(annotationClass);
                }
            }
        }

        return ann;
    }
    
    public static Annotation getAnnotation(Class<? extends Annotation> anno, Class<?> cl) {
		return cl.getAnnotation(anno);
	}
	
	public static Annotation getAnnotation(Class<? extends Annotation> anno, Object obj) {
		return obj.getClass().getAnnotation(anno);
	}
	
	public static Annotation getAnnotation(Class<? extends Annotation> anno, Method meth) {
		return meth.getAnnotation(anno);
	}
	
	public static Annotation getAnnotation(Class<? extends Annotation> anno, Field field) {
		return field.getAnnotation(anno);
	}
	
	public static Boolean hasAnnotation(Class<? extends Annotation> anno, Field field) {
		return field.isAnnotationPresent(anno);
	}
	
	public static Boolean hasAnnotation(Class<? extends Annotation> anno, Method meth) {
		return meth.isAnnotationPresent(anno);
	}
	
	public static Boolean hasAnnotation(Class<? extends Annotation> anno, Object obj) {
		return (getAnnotation(anno, obj) != null);
	}
	
	public static Boolean hasAnnotation(Class<? extends Annotation> anno, Class<?> cl) {
		return (getAnnotation(anno, cl) != null);
	}
	
	public static Boolean hasAnnotationValue(Class<? extends Annotation> anno, Field field, String param, Object value) throws Exception {
		return field.isAnnotationPresent(anno) && (getAnnotationValue(anno, field, param) == value);
	}
	
	public static Boolean hasAnnotationValue(Class<? extends Annotation> anno, Method meth, String param, Object value) throws Exception {
		return meth.isAnnotationPresent(anno) && (getAnnotationValue(anno, meth, param) == value);
	}
	
	public static Boolean hasAnnotationValue(Class<? extends Annotation> anno, Object obj, String param, Object value) throws Exception {
		return (getAnnotation(anno, obj) != null) && (getAnnotationValue(anno, obj, param) == value);
	}
	
	public static Boolean hasAnnotationValue(Class<? extends Annotation> anno, Class<?> cl, String param, Object value) throws Exception {
		return cl.isAnnotationPresent(anno) && (getAnnotationValue(anno, cl, param) == value);
	}
	
	public static Object getAnnotationValue(Class<? extends Annotation> anno, Field field, String paramName) throws Exception {
		Object value = null;
		if (hasAnnotation(anno, field) && (paramName != null && !paramName.isEmpty())) {
			value = ObjectUtils.callMethodNamed(paramName, field.getAnnotation(anno));
		}
		return value;
	}
	
	public static Object getAnnotationValue(Class<? extends Annotation> anno, Method meth, String paramName) throws Exception {
		Object value = null;
		if (hasAnnotation(anno, meth) && (paramName != null && !paramName.isEmpty())) {
			value = ObjectUtils.callMethodNamed(paramName, meth.getAnnotation(anno));
		}
		return value;
	}
	
	public static Object getAnnotationValue(Class<? extends Annotation> anno, Object obj, String paramName) throws Exception {
		return getAnnotationValue(anno, obj.getClass(), paramName);
	}
	
	public static Object getAnnotationValue(Class<? extends Annotation> anno, Class<?> cl, String paramName) throws Exception {
		Object value = null;
		if (hasAnnotation(anno, cl) && (paramName != null && !paramName.isEmpty())) {
			value = ObjectUtils.callMethodNamed(paramName, cl.getAnnotation(anno));
		}
		return value;
	}
	
	public static List<Object> callMethodsAnnotatedWithValue(Class<? extends Annotation> anno, String param, Object value, Object o, Object... p ) throws Exception {
		return ObjectUtils.call(getMethodsAnnotatedWithValue(anno, o, param, value), o, p);
	}
	
	public static List<Object> callMethodsAnnotated(Class<? extends Annotation> anno, Object o, Object... p ) throws Exception {
		List<Method> toCall = getMethodsAnnotated(anno, o);
		
		if (toCall == null || toCall.isEmpty())
			throw new Exception("Il n'y a aucune methode annoté avec " + anno.getSimpleName());
		
		return ObjectUtils.call(toCall, o, p);
	}

	
	public static List<Method> getMethodsAnnotatedWithValue(Class<? extends Annotation> anno, Object o, String name, Object value) throws Exception {
		ArrayList<Method> methods = new ArrayList<Method>();
		List<Method> all = Arrays.asList(o.getClass().getDeclaredMethods());
		for (Iterator<Method> iterator = all.iterator(); iterator.hasNext();) {
			Method method = (Method) iterator.next();
			if (value != null && value.equals(getAnnotationValue(anno, method, name)))
				methods.add(method);
		}
		return methods;
	}
	
	public static List<Method> getMethodsAnnotated(Class<? extends Annotation> anno, Class<?> holder) {
		ArrayList<Method> methods = new ArrayList<Method>();
		List<Method> all = Arrays.asList(holder.getMethods());
		for (Iterator<Method> iterator = all.iterator(); iterator.hasNext();) {
			Method method = (Method) iterator.next();
			if (hasAnnotation(anno, method))
				methods.add(method);
		}
		return methods;
	}
	
	public static List<Method> getMethodsAnnotated(Class<? extends Annotation> anno, Object holder) {
		ArrayList<Method> methods = new ArrayList<Method>();
		List<Method> all = Arrays.asList(holder.getClass().getMethods());
		for (Iterator<Method> iterator = all.iterator(); iterator.hasNext();) {
			Method method = (Method) iterator.next();
			if (hasAnnotation(anno, method))
				methods.add(method);
		}
		return methods;
	}
	
	public static Object callMethodNamed(String name, Object o, Object... p ) throws Exception {
		Method toCall = getMethodNamed(name, o);
		
		if (toCall == null)
			throw new Exception("Il n'y a aucune methode " + name);
		
		return call(toCall, o, p);
	}
	
	public static Object call(Method m, Object o, Object... p) throws Exception {
		Object value = null;
		try {
			m.setAccessible(true);
			value = m.invoke(o, p);
			m.setAccessible(false);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			throw new Exception("Les arguments transmis sont mauvais.");
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new Exception("Impossible d'acceder a la methode.");
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			throw new Exception("Erreur dans l'execution.");
		}
		return value;
	}
	
	public static List<Object> call(final List<Method> lm, final Object o, final Object... p) throws Exception {
		List<Object> values = new ArrayList<Object>();
		for (Iterator<Method> iterator = lm.iterator(); iterator.hasNext();) {
			Method m = (Method) iterator.next();
			values.add(call(m, o, p));
		}
		return values;
	}
	
	public static Method getMethodNamed(String methodName, Object holder) {
		Method method = null;
		List<Method> all = Arrays.asList(holder.getClass().getDeclaredMethods());
		for (Iterator<Method> iterator = all.iterator(); iterator.hasNext();) {
			Method m = (Method) iterator.next();
			if(methodName.equalsIgnoreCase(m.getName())) {
				method = m;
				break;
			}
		}
		return method;
	}	

	public static Method getMethodNamed(String methodName, Class<?> holder) {
		Method method = null;
		List<Method> all = Arrays.asList(holder.getDeclaredMethods());
		for (Iterator<Method> iterator = all.iterator(); iterator.hasNext();) {
			Method m = (Method) iterator.next();
			if(methodName.equalsIgnoreCase(m.getName())) {
				method = m;
				break;
			}
		}
		return method;
	}
	
	public static List<Method> getMethodsNameStartingWith(String prefix, Object holder) {
		List<Method> all = Arrays.asList(holder.getClass().getDeclaredMethods().clone());
		for (Iterator<Method> iterator = all.iterator(); iterator.hasNext();) {
			Method m = (Method) iterator.next();
			if(m.getName().startsWith(prefix)) {
				all.remove(m);
			}
		}
		return all;
	}
	
	public static List<Method> getMethodsNameEndingWith(String suffix, Object holder) {
		List<Method> all = Arrays.asList(holder.getClass().getDeclaredMethods().clone());
		for (Iterator<Method> iterator = all.iterator(); iterator.hasNext();) {
			Method m = (Method) iterator.next();
			if(!m.getName().endsWith(suffix)) {
				all.remove(m);
			}
		}
		return all;
	}
	
	public static List<Method> getAllSetters(Object holder) {
		return getMethodsNameStartingWith("set", holder);
	}
	
	public static List<Method> getAllGetters(Object holder) {
		return getMethodsNameStartingWith("get", holder);
	}
	
	public static void set(String name, Object value, Object holder) throws Exception {
		String nos = StringUtils.join(" ", "set", name);
		call(getMethodNamed(StringUtils.camelCase(nos), holder), holder, value);
	}	
	
	public static Object get(String name, Object holder) throws Exception {
		String nog = StringUtils.join(" ", "get", name);
		return call(getMethodNamed(StringUtils.camelCase(nog), holder), holder);
	}
}
