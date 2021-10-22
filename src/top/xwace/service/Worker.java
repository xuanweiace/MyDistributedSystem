package top.xwace.service;

public interface Worker {
    public boolean registerToPark();
    public void waitWorking(String host, int port, String workerType);
    public void waitWorkingByService(String host, int port);

}
