import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

public class FCFSScheduling {

    public static void runFCFS() {
        List<Process> processes = new ArrayList<>();
        processes.add(new Process(1, 5, 0));
        processes.add(new Process(2, 3, 2));
        processes.add(new Process(3, 8, 4));
        processes.add(new Process(4, 6, 6));

        fcfs(processes);
    }

    private static void fcfs(List<Process> processes) {
        processes.sort(Comparator.comparingInt(p -> p.arrivalTime));

        int currentTime = 0;
        int totalWaitTime = 0;
        int totalTurnaroundTime = 0;

        System.out.println("Process ID\tBurst Time\tArrival Time\tWait Time\tTurnaround Time");

        for (Process process : processes) {
            int waitTime = Math.max(0, currentTime - process.arrivalTime);
            int turnaroundTime = waitTime + process.burstTime;
            totalWaitTime += waitTime;
            totalTurnaroundTime += turnaroundTime;

            System.out.println(process.id + "\t\t\t" + process.burstTime + "\t\t\t" + process.arrivalTime + "\t\t\t" + waitTime + "\t\t\t" + turnaroundTime);

            currentTime += process.burstTime;
        }

        double averageWaitTime = (double) totalWaitTime / processes.size();
        double averageTurnaroundTime = (double) totalTurnaroundTime / processes.size();

        System.out.println("\nAverage Wait Time: " + averageWaitTime);
        System.out.println("Average Turnaround Time: " + averageTurnaroundTime);
    }
}
