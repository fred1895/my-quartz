package br.com.wod.quartz.core.adapters;

public interface IJobsPlayerService {
    void start();

    void resume();

    void pause();

    void delete();
}
