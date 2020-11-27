package br.com.wod.quartz.resource.exception;

public class QrtzObjectNotFoundException extends RuntimeException{

    public QrtzObjectNotFoundException(String msg) {
        super(msg);
    }
}
