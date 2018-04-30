package live.search.fixer.utils;

public class TimeLine {
    private long startTime;
    private long timeLimit;
    private boolean timeoutNow = false;
    private boolean neverTimeout = false;

    private long downloadTime = 0;

    public static TimeLine NEVER_TIMEOUT = new TimeLine(-1);
    public TimeLine(int timeLimit){
        if (timeLimit < 0){
            neverTimeout = true;
        }
        this.timeLimit = timeLimit;
        startTime = System.currentTimeMillis();
    }

    public void addDownloadTime(long time){
        downloadTime +=time;
    }

    public boolean isTimeout(){
        if (timeoutNow){
            return true;
        }
        return (time() > timeLimit) && !neverTimeout;
    }

    public long time(){
        return (System.currentTimeMillis() - startTime - downloadTime)/60000;
    }

    public void timeOutNow(){
        timeoutNow = true;
    }
}
