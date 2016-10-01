package com.marvin.component.kernel.controller;

import com.marvin.component.dialog.Response;
import com.marvin.component.dialog.Request;
import java.lang.reflect.Method;
import java.util.Date;

public class Controller implements Runnable {

    protected long id;

    protected Object controller;
    protected Method action;

    public Controller(Object controller, Method action) {
        super();
        this.id = new Date().getTime() + Math.round(Math.random() * 10);

        this.controller = controller;
        this.action = action;
    }

    @Override
    public void run() {
        synchronized (this) {
            try {
                long start = new Date().getTime();
                System.out.format("Runing Controller at %s", start);

                process();

                long end = new Date().getTime();
                System.out.format("Controller executed in %s ms", end);

            } catch (Exception e) {
                System.err.print(e.getMessage());
//            System.exit(-1);
            }
        }
    }

    public void process() throws Exception {
        synchronized (this) {

            System.out.println("process");

            this.action.setAccessible(true);
            this.action.invoke(this.controller);
            this.action.setAccessible(false);

            System.out.println("end process");
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
