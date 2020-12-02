package Runtime;

import Entities.Component;
import Enums.State;
import Interfaces.StartingClass;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.rmi.NoSuchObjectException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class Laufzeitumgebung {
    private final HashMap<Integer, Component> components = new HashMap<>();

    public Laufzeitumgebung() {
    }

    // source: https://stackoverflow.com/questions/11016092/how-to-load-classes-at-runtime-from-a-folder-or-jar
    public ArrayList<Class<?>> loadClasses(String path, Component component) throws Exception {
        JarFile jarFile = new JarFile(path);
        Enumeration<JarEntry> e = jarFile.entries();
        component.setPath(path);

        URL[] urls = { new URL("jar:file:" + path+"!/") };
        URLClassLoader cl = URLClassLoader.newInstance(urls);
        component.setCl(cl);
        ArrayList<Class<?>> classList = new ArrayList<>();

        while (e.hasMoreElements()) {
            JarEntry je = e.nextElement();
            if(je.isDirectory() || !je.getName().endsWith(".class")){
                continue;
            }

            // -6 because of .class
            String className = je.getName().substring(0,je.getName().length()-6);
            component.setName(className);
            className = className.replace('/', '.');
            Class<?> c = cl.loadClass(className);
            classList.add(c);
        }
        cl.close();
        component.setState(State.LOADED);
        return classList;
    }

    public Class<?> getStartingClass(ArrayList<Class<?>> classes) throws NoSuchObjectException {
        for(Class<?> c : classes) {
            for(Method m: c.getDeclaredMethods()) {
                if (m.isAnnotationPresent(StartingClass.class)) {
                    return c;
                }
            }
        }

        throw new NoSuchObjectException("No class with a StartingClass annotation found");
    }

    public static void invokeMethod(Class<? extends Annotation> annotation, Class<?> startingClass) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        for (Method m: startingClass.getDeclaredMethods()) {
            if (m.isAnnotationPresent(annotation)) {
                m.setAccessible(true);
                m.invoke(startingClass.getConstructor().newInstance());
            }
        }
    }

    public String addComponent(String path) {
        try {
            final Component c = new Component();
            ArrayList<Class<?>> classes = loadClasses(path, c);
            final Class<?> start = getStartingClass(classes);
            c.setStartingClass(start);
            c.setThread(new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        invokeMethod(StartingClass.class, start);
                        c.setState(State.STOPPED);
                    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
                        e.printStackTrace();
                    }
                }
            }));
            components.put(c.getId(), c);
            return "Component \"" + c.getName() + "\" was successfully added to the Environment!";
        } catch (Exception e) {
            return "The Component could not be added! Please check if the given path is correct or if the .jar-File contains no errors. \n\nStack-Trace:\n" + e.getMessage();
        }
    }

    public String runComponent(int id) {
        if (components.containsKey(id)) {
            Component c = components.get(id);
            c.runComponent();
            return "Component \"" + c.getName() + "\" was started!";
        } else {
            return "Couldn't start Component with id " + id + " because the Component was not found!";
        }
    }

    public String stopComponent(int id) {
        if (components.containsKey(id)) {
            Component c = components.get(id);
            c.stopComponent();
            return "Component \"" + c.getName() + "\" was stopped!";
        } else {
            return "Couldn't stop Component with id " + id + " because the Component was not found!";
        }
    }

    public String runAllComponents() {
        StringBuilder sb = new StringBuilder();
        sb.append("Running all ").append(components.size()).append(" Component(s)!\n");
        for (Component c: components.values()) {
            sb.append(runComponent(c.getId()));
            sb.append("\n");
        }

        return sb.toString();
    }

    public String stopAllComponents() {
        StringBuilder sb = new StringBuilder();
        sb.append("Stopping all ").append(components.size()).append(" Component(s)!\n");
        for (Component c: components.values()) {
            sb.append(stopComponent(c.getId()));
            sb.append("\n");
        }

        return sb.toString();
    }

    public String removeComponent(int id) {
        if (components.containsKey(id)) {
            String name = components.get(id).getName();
            components.get(id).stopComponent();
            components.remove(id);
            return "Component \"" + name + "\" was safely removed from the Environment!";
        } else {
            return "Couldn't remove Component with id " + id + " because the Component was not found!";
        }
    }

    public String removeAllComponents() {
        StringBuilder sb = new StringBuilder();
        sb.append("Removing all ").append(components.size()).append(" Component(s)!\n");
        for (Component c: components.values()) {
            sb.append(removeComponent(c.getId()));
            sb.append("\n");
        }

        return sb.toString();
    }

    public String getStatus() {
        StringBuilder sb = new StringBuilder();

        if (components.size() > 0) {
            sb.append("ID\tName\t\t\tStatus\n");
            for (Component c: components.values()) {
                sb.append(c.getStatus()).append("\n");
            }

            return sb.toString();
        } else {
            return "Currently no Components in Runtime Environment.";
        }
    }

    public static void main(String[] args) {
        try {
            Laufzeitumgebung lzu = new Laufzeitumgebung();
            lzu.addComponent("C:\\Users\\Benutzer1\\Desktop\\OOKA\\OOKA\\Übung 2\\in\\modules\\Artifacts\\TestClass.jar");
            lzu.addComponent("C:\\Users\\Benutzer1\\Desktop\\OOKA\\OOKA\\Übung 2\\in\\modules\\Artifacts\\TestClass.jar");
            lzu.runComponent(0);
            lzu.removeComponent(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
