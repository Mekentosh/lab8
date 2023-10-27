import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

public class SJNScheduling {

    public static void runSJN() {
        List<Process> processes = new ArrayList<>();
        processes.add(new Process(1, 6, 1));
        processes.add(new Process(2, 8, 1));
        processes.add(new Process(3, 7, 2));
        processes.add(new Process(4, 3, 3));

        sjn(processes);
    }

    private static void sjn(List<Process> processes) {
        processes.sort(Comparator.comparingInt(p -> p.arrivalTime));

        int currentTime = 0;
        int totalWaitTime = 0;
        int totalTurnaroundTime = 0;

        System.out.println("Process ID\tBurst Time\tArrival Time\tWait Time\tTurnaround Time");

        while (!processes.isEmpty()) {

            List<Process> availableProcesses = new ArrayList<>();
            for (Process process : processes) {
                if (process.arrivalTime <= currentTime) {
                    availableProcesses.add(process);
                }
            }


            if (availableProcesses.isEmpty()) {
                currentTime = processes.get(0).arrivalTime;
                continue;
            }


            Process shortestJob = availableProcesses.stream().min(Comparator.comparingInt(p -> p.burstTime)).get();

            int waitTime = currentTime - shortestJob.arrivalTime;
            int turnaroundTime = waitTime + shortestJob.burstTime;
            totalWaitTime += waitTime;
            totalTurnaroundTime += turnaroundTime;

            System.out.println(shortestJob.id + "\t\t\t" + shortestJob.burstTime + "\t\t\t" + shortestJob.arrivalTime + "\t\t\t" + waitTime + "\t\t\t" + turnaroundTime);

            currentTime += shortestJob.burstTime;
            processes.remove(shortestJob);
        }

        double averageWaitTime = (double) totalWaitTime / 4;
        double averageTurnaroundTime = (double) totalTurnaroundTime / 4;

        System.out.println("\nAverage Wait Time: " + averageWaitTime);
        System.out.println("Average Turnaround Time: " + averageTurnaroundTime);
    }
}
