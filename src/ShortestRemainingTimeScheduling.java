import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

public class ShortestRemainingTimeScheduling {

    public static void runSRT() {
        List<Process> processes = new ArrayList<>();
        processes.add(new Process(1, 8, 0));
        processes.add(new Process(2, 4, 1));
        processes.add(new Process(3, 9, 2));
        processes.add(new Process(4, 5, 3));

        srt(processes);
    }

    private static void srt(List<Process> processes) {
        processes.sort(Comparator.comparingInt(p -> p.arrivalTime));

        int currentTime = 0;
        int totalWaitTime = 0;
        int totalTurnaroundTime = 0;
        List<Process> completedProcesses = new ArrayList<>();

        System.out.println("Process ID\tBurst Time\tArrival Time\tWait Time\tTurnaround Time");

        while (completedProcesses.size() < processes.size()) {
            List<Process> availableProcesses = new ArrayList<>();
            for (Process process : processes) {
                if (process.arrivalTime <= currentTime && !completedProcesses.contains(process)) {
                    availableProcesses.add(process);
                }
            }

            if (availableProcesses.isEmpty()) {
                currentTime = processes.get(0).arrivalTime;
                continue;
            }

            Process shortestRemainingTimeProcess = availableProcesses.stream().min(Comparator.comparingInt(p -> p.burstTime)).get();

            shortestRemainingTimeProcess.burstTime--;
            currentTime++;

            if (shortestRemainingTimeProcess.burstTime == 0) {
                int waitTime = currentTime - shortestRemainingTimeProcess.arrivalTime - shortestRemainingTimeProcess.burstTime;
                int turnaroundTime = currentTime - shortestRemainingTimeProcess.arrivalTime;
                totalWaitTime += waitTime;
                totalTurnaroundTime += turnaroundTime;

                System.out.println(shortestRemainingTimeProcess.id + "\t\t\t" + shortestRemainingTimeProcess.burstTime + "\t\t\t" + shortestRemainingTimeProcess.arrivalTime + "\t\t\t" + waitTime + "\t\t\t" + turnaroundTime);

                completedProcesses.add(shortestRemainingTimeProcess);
            }
        }

        double averageWaitTime = (double) totalWaitTime / processes.size();
        double averageTurnaroundTime = (double) totalTurnaroundTime / processes.size();

        System.out.println("\nAverage Wait Time: " + averageWaitTime);
        System.out.println("Average Turnaround Time: " + averageTurnaroundTime);
    }
}
