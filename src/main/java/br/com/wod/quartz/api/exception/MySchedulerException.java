package br.com.wod.quartz.api.exception;

public class MySchedulerException extends RuntimeException {

    public MySchedulerException(String msg) {
        super(msg);
    }
}
