import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

public class MultipleLevelQueuesScheduling {

    public static void runMLQ() {
        List<Process> highPriorityQueue = new ArrayList<>();
        List<Process> lowPriorityQueue = new ArrayList<>();

        highPriorityQueue.add(new Process(1, 6, 1));
        highPriorityQueue.add(new Process(2, 8, 1));
        highPriorityQueue.add(new Process(3, 7, 2));

        lowPriorityQueue.add(new Process(4, 3, 3));
        lowPriorityQueue.add(new Process(5, 4, 4));
        lowPriorityQueue.add(new Process(6, 5, 5));

        mlq(highPriorityQueue, lowPriorityQueue);
    }

    private static void mlq(List<Process> highPriorityQueue, List<Process> lowPriorityQueue) {
        highPriorityQueue.sort(Comparator.comparingInt(p -> p.burstTime));

        int currentTime = 0;
        int totalWaitTime = 0;
        int totalTurnaroundTime = 0;
        int processCount = highPriorityQueue.size() + lowPriorityQueue.size();

        System.out.println("Process ID\tBurst Time\tArrival Time\tQueue\tWait Time\tTurnaround Time");

        while (!highPriorityQueue.isEmpty() || !lowPriorityQueue.isEmpty()) {
            Process process = null;

            if (!highPriorityQueue.isEmpty() && highPriorityQueue.get(0).arrivalTime <= currentTime) {
                process = highPriorityQueue.remove(0);
            } else if (!lowPriorityQueue.isEmpty() && lowPriorityQueue.get(0).arrivalTime <= currentTime) {
                process = lowPriorityQueue.remove(0);
            }

            if (process == null) {
                currentTime++;
                continue;
            }

            int waitTime = currentTime - process.arrivalTime;
            int turnaroundTime = waitTime + process.burstTime;
            totalWaitTime += waitTime;
            totalTurnaroundTime += turnaroundTime;

            String queue = highPriorityQueue.contains(process) ? "High" : "Low";
            System.out.println(process.id + "\t\t\t" + process.burstTime + "\t\t\t" + process.arrivalTime + "\t\t\t" + queue + "\t\t\t" + waitTime + "\t\t\t" + turnaroundTime);

            currentTime += process.burstTime;
        }

        double averageWaitTime = (double) totalWaitTime / processCount;
        double averageTurnaroundTime = (double) totalTurnaroundTime / processCount;

        System.out.println("\nAverage Wait Time: " + averageWaitTime);
        System.out.println("Average Turnaround Time: " + averageTurnaroundTime);
    }
}
