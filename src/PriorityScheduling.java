import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

public class PriorityScheduling {

    public static void runPriorityScheduling() {
        List<ProcessWithPriority> processes = new ArrayList<>();
        processes.add(new ProcessWithPriority(1, 10, 3, 2));
        processes.add(new ProcessWithPriority(2, 1, 1, 1));
        processes.add(new ProcessWithPriority(3, 2, 4, 4));
        processes.add(new ProcessWithPriority(4, 1, 5, 5));
        processes.add(new ProcessWithPriority(5, 5, 2, 3));

        priorityScheduling(processes);
    }

    private static void priorityScheduling(List<ProcessWithPriority> processes) {
        processes.sort(Comparator.comparingInt(p -> p.arrivalTime));

        int currentTime = 0;
        int totalWaitTime = 0;
        int totalTurnaroundTime = 0;

        System.out.println("Process ID\tBurst Time\tArrival Time\tPriority\tWait Time\tTurnaround Time");

        while (!processes.isEmpty()) {

            List<ProcessWithPriority> availableProcesses = new ArrayList<>();
            for (ProcessWithPriority process : processes) {
                if (process.arrivalTime <= currentTime) {
                    availableProcesses.add(process);
                }
            }

            if (availableProcesses.isEmpty()) {
                currentTime = processes.get(0).arrivalTime;
                continue;
            }

            ProcessWithPriority highestPriorityProcess = availableProcesses.stream().min(Comparator.comparingInt(p -> p.priority)).get();

            int waitTime = currentTime - highestPriorityProcess.arrivalTime;
            int turnaroundTime = waitTime + highestPriorityProcess.burstTime;
            totalWaitTime += waitTime;
            totalTurnaroundTime += turnaroundTime;

            System.out.println(highestPriorityProcess.id + "\t\t\t" + highestPriorityProcess.burstTime + "\t\t\t" + highestPriorityProcess.arrivalTime + "\t\t\t" + highestPriorityProcess.priority + "\t\t\t" + waitTime + "\t\t\t" + turnaroundTime);

            currentTime += highestPriorityProcess.burstTime;
            processes.remove(highestPriorityProcess);
        }

        double averageWaitTime = (double) totalWaitTime / processes.size();
        double averageTurnaroundTime = (double) totalTurnaroundTime / processes.size();

        System.out.println("\nAverage Wait Time: " + averageWaitTime);
        System.out.println("Average Turnaround Time: " + averageTurnaroundTime);
    }
}
