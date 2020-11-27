package br.com.wod.quartz.resource.exception;

public class QrtzObjectNotFoundException extends RuntimeException{

    private static final String NOT_FOUND_MESSAGE = "Não foram encontrados resultados para esta pesquisa";

    public QrtzObjectNotFoundException() {
        super(NOT_FOUND_MESSAGE);
    }
}
