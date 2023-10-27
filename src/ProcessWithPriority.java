public class ProcessWithPriority extends Process {
    int priority;

    public ProcessWithPriority(int id, int burstTime, int arrivalTime, int priority) {
        super(id, burstTime, arrivalTime);
        this.priority = priority;
    }
}
