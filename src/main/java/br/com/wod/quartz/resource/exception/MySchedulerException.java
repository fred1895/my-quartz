package br.com.wod.quartz.resource.exception;

public class MySchedulerException extends RuntimeException {

    public MySchedulerException(String msg) {
        super(msg);
    }
}
