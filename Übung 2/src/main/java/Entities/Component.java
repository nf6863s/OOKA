package Entities;

import Enums.State;
import Interfaces.StoppingClass;
import Runtime.Laufzeitumgebung;

import java.lang.reflect.InvocationTargetException;

public class Component {
    private int id;
    private String name;
    private State state;
    private Thread thread;
    private String path;
    private ClassLoader cl;
    private Class<?> startingClass;

    private static class IdGenerator {

        private static int id = 0;
    }

    public Component() {
        this.id = IdGenerator.id++;
    }

    public void runComponent() {
        if (this.thread != null) {
            this.state = State.STARTING;
            this.thread.start();
            this.state = State.RUNNING;
        } else {
            System.out.println("The thread for Component " + this.name + " has not been properly initialized!");
        }
    }

    public void stopComponent() {
        if (this.thread.isAlive()) {
            System.out.println("test");
            try {
                Laufzeitumgebung.invokeMethod(StoppingClass.class, startingClass);
                this.state = State.STOPPED;
                System.out.println("stopped state");
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
                e.printStackTrace();
                this.state = State.ERROR;
            }
            this.thread.interrupt();
        }
    }

    public String getStatus() {
        return getId() + "\t" + getName() + "\t" + getState().name();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }

    public ClassLoader getCl() {
        return cl;
    }

    public void setCl(ClassLoader cl) {
        this.cl = cl;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Class<?> getStartingClass() {
        return startingClass;
    }

    public void setStartingClass(Class<?> startingClass) {
        this.startingClass = startingClass;
    }
}
