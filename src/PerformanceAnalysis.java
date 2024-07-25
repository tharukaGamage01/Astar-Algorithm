// Name : Tharuka Gamage
// Student No: 20212177
public class PerformanceAnalysis {
    private long startTime;
    private long endTime;

    public void startTime() {
        startTime = System.currentTimeMillis();
    }

    public void endTime() {
        endTime = System.currentTimeMillis();
    }

    public String executedTime(){

        long timeMili = (endTime - startTime);
        double timeSec = timeMili /1000.0;
        return "Time taken for execution: " +timeSec+"s";

    }



}
