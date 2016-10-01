package com.glingo.marvin.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * This class is extremely useful for loading resources and classes in a fault tolerant manner
 * that works across different applications servers.
 */
public class ClassLoaderUtil {
	
	/**
     * Load all resources with a given name, potentially aggregating all results 
     * from the searched classloaders.  If no results are found, the resource name
     * is prepended by '/' and tried again.
     *
     * This method will try to load the resources using the following methods (in order):
     * <ul>
     *  <li>From Thread.currentThread().getContextClassLoader()</li>
     *  <li>From ClassLoaderUtil.class.getClassLoader()</li>
     *  <li>callingClass.getClassLoader()</li>
     * </ul>
     *
     * @param resourceName The name of the resources to load
     * @param callingClass The Class object of the calling object
     */
     public static Iterator<URL> getResources(String resourceName, Class<?> callingClass, boolean aggregate) throws IOException {

         AggregateIterator<URL> iterator = new AggregateIterator<URL>();

         iterator.addEnumeration(Thread.currentThread().getContextClassLoader().getResources(resourceName));

         if (!iterator.hasNext() || aggregate) {
             iterator.addEnumeration(ClassLoaderUtil.class.getClassLoader().getResources(resourceName));
         }

         if (!iterator.hasNext() || aggregate) {
             ClassLoader cl = callingClass.getClassLoader();

             if (cl != null) {
                 iterator.addEnumeration(cl.getResources(resourceName));
             }
         }

         if (!iterator.hasNext() && (resourceName != null) && ((resourceName.length() == 0) || (resourceName.charAt(0) != '/'))) { 
             return getResources('/' + resourceName, callingClass, aggregate);
         }

         return iterator;
     }
     
     public static Iterator<URL> getResources(String resourceName, boolean aggregate) throws IOException {
    	 return getResources(resourceName, null, aggregate);
     }

    /**
     * Load a given resource.
     * <p/>
     * This method will try to load the resource using the following methods (in order):
     * <ul>
     * <li>From {@link Thread#getContextClassLoader() Thread.currentThread().getContextClassLoader()}
     * <li>From {@link Class#getClassLoader() ClassLoaderUtil.class.getClassLoader()}
     * <li>From the {@link Class#getClassLoader() callingClass.getClassLoader() }
     * </ul>
     *
     * @param resourceName The name of the resource to load
     * @param callingClass The Class object of the calling object
     */
    public static URL getResource(String resourceName, Class<?> callingClass) {
        URL url = Thread.currentThread().getContextClassLoader().getResource(resourceName);

        if (url == null) {
            url = ClassLoaderUtil.class.getClassLoader().getResource(resourceName);
        }

        if (url == null && callingClass != null) {
            ClassLoader cl = callingClass.getClassLoader();

            if (cl != null) {
                url = cl.getResource(resourceName);
            }
        }
        
        if (url == null && callingClass != null) {
            url = callingClass.getResource(resourceName);
        }
        
        if ((url == null) && (resourceName != null) && ((resourceName.length() == 0) || (resourceName.charAt(0) != '/'))) { 
            return getResource('/' + resourceName, callingClass);
        }

        return url;
    }

    public static URL getResource(String resourceName) {
    	return getResource(resourceName, null);
    }
    
    public static File getResourceAsFile(String resourceName, Class<?> callingClass) {
    	URL url = getResource(resourceName, callingClass);
		return new File(url.getPath());
    }

    /**
    * This is a convenience method to load a resource as a stream.
    *
    * The algorithm used to find the resource is given in getResource()
    *
    * @param resourceName The name of the resource to load
    * @param callingClass The Class<?> object of the calling object
    */
    public static InputStream getResourceAsStream(String resourceName, Class<?> callingClass) {
        URL url = getResource(resourceName, callingClass);

        try {
            return (url != null) ? url.openStream() : null;
        } catch (IOException e) {
            return null;
        }
    }
    
    public static InputStream getResourceAsStream(String resourceName) {
    	return getResourceAsStream(resourceName, null);
    }

    /**
     * Load a class with a given name.
     * <p/>
     * It will try to load the class in the following order:
     * <ul>
     * <li>From {@link Thread#getContextClassLoader() Thread.currentThread().getContextClassLoader()}
     * <li>Using the basic {@link Class#forName(java.lang.String) }
     * <li>From {@link Class#getClassLoader() ClassLoaderUtil.class.getClassLoader()}
     * <li>From the {@link Class#getClassLoader() callingClass.getClassLoader() }
     * </ul>
     *
     * @param className    The name of the class to load
     * @param callingClass The Class object of the calling object
     * @throws ClassNotFoundException If the class cannot be found anywhere.
     */
    public static Class<?> loadClass(String className, Class<?> callingClass) {
        try {
            return Thread.currentThread().getContextClassLoader().loadClass(className);
        } catch (ClassNotFoundException e) {
            try {
                return Class.forName(className);
            } catch (ClassNotFoundException ex) {
                try {
                    return ClassLoaderUtil.class.getClassLoader().loadClass(className);
                } catch (ClassNotFoundException exc) {
                	try {
                        return callingClass.getClassLoader().loadClass(className);
                    } catch (Exception other) {
                		return null;
                    }
                }
            }
        }
    }
    
    public static Class<?> loadClass(String className) {
    	return loadClass(className, null);
    }
    
    public static Object getInstanceOf(String className) {
    	Object objet = null;
    	try {
    		Class<?> clazz = loadClass(className, null);
    		objet = clazz.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return objet;
    }

    /**
     * Prints the current classloader hierarchy - useful for debugging.
     */
    public static void printClassLoader() {
        System.out.println("ClassLoaderUtils.printClassLoader");
        printClassLoader(Thread.currentThread().getContextClassLoader());
    }

    /**
     * Prints the classloader hierarchy from a given classloader - useful for debugging.
     */
    public static void printClassLoader(ClassLoader cl) {
        System.out.println("ClassLoaderUtils.printClassLoader(cl = " + cl + ")");

        if (cl != null) {
            printClassLoader(cl.getParent());
        }
    }

	/**
	 * Aggregates Enumeration instances into one iterator and filters out duplicates.  Always keeps one
	 * ahead of the enumerator to protect against returning duplicates.
	 */
	static class AggregateIterator<E> implements Iterator<E> {

	    private LinkedList<Enumeration<E>> enums = new LinkedList<Enumeration<E>>();
	    private Enumeration<E> cur = null;
	    private E next = null;
	    private Set<E> loaded = new HashSet<E>();

	    public AggregateIterator<E> addEnumeration(Enumeration<E> e) {
	        if (e.hasMoreElements()) {
	            if (cur == null) {
	                cur = e;
	                next = e.nextElement();
	                loaded.add(next);
	            } else {
	                enums.add(e);
	            }
	        }
	        return this;
	    }

	    public boolean hasNext() {
	        return (next != null);
	    }

	    public E next() {
	        if (next != null) {
	            E prev = next;
	            next = loadNext();
	            return prev;
	        } else {
	            throw new NoSuchElementException();
	        }
	    }

	    private Enumeration<E> determineCurrentEnumeration() {
	        if (cur != null && !cur.hasMoreElements()) {
	            if (enums.size() > 0) {
	                cur = enums.removeLast();
	            } else {
	                cur = null;
	            }
	        }
	        return cur;
	    }

	    private E loadNext() {
	        if (determineCurrentEnumeration() != null) {
	            E tmp = cur.nextElement();
	            int loadedSize = loaded.size();
	            while (loaded.contains(tmp)) {
	                tmp = loadNext();
	                if (tmp == null || loaded.size() > loadedSize) {
	                    break;
	                }
	            }
	            if (tmp != null) {
	                loaded.add(tmp);
	            }
	            return tmp;
	        }
	        return null;

	    }

	    public void remove() {
	        throw new UnsupportedOperationException();
	    }
	}
}
