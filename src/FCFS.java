import  java.util.Scanner;
import java.util.Arrays;
import java.util.Comparator;

public class FCFS {
    static class Process{
        int pid;
        int arrivalTime;
        int brustTime;
        int completionTime;
        int turnaroundTime;
        int waitingTime;

        Process(int pid, int arrivalTime, int brustTime){
            this.pid = pid;
            this.arrivalTime = arrivalTime;
            this.brustTime = brustTime;
        }
    }

    static void fcfs(Process[] processes){
        Arrays.sort(processes, Comparator.comparingInt(p -> p.arrivalTime));

        int currentTime = 0;
        int totalWaitingTime = 0;
        int totalTurnaroundTime = 0;

        for(Process p : processes){

            if(currentTime < p.arrivalTime){
                currentTime = p.arrivalTime;
            }

            // Completion Time
            currentTime += p.brustTime;
            p.completionTime = currentTime;

            // Turnaround Time
            p.turnaroundTime = p.completionTime - p.arrivalTime;

            // Waiting Time
            p.waitingTime = p.turnaroundTime - p.brustTime;

            totalWaitingTime += p.waitingTime;
            totalTurnaroundTime += p.turnaroundTime;
        }
        System.out.println("PID\tAT\tBT\tCT\tTAT\tWT");

        for(Process p : processes){
            System.out.println("P"+p.pid+"\t"+p.arrivalTime+"\t"+
                    p.brustTime+"\t"+p.completionTime+"\t"+
                    p.turnaroundTime+"\t"+p.waitingTime);
        }

        double avgW = (double) totalWaitingTime / processes.length;
        double avgT = (double) totalTurnaroundTime / processes.length;

        System.out.println();
        System.out.printf("Average Waiting time : %.2f%n", avgW);
        System.out.printf("Average TurnAround time : %.2f%n",avgT);
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Number of processes
        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();

        Process[] processes = new Process[n];

        // User input
        for (int i = 0; i < n; i++) {

            System.out.println("\nProcess P" + (i + 1));

            System.out.print("Enter Arrival Time: ");
            int arrivalTime = sc.nextInt();

            System.out.print("Enter Burst Time: ");
            int burstTime = sc.nextInt();

            processes[i] =
                    new Process(i + 1, arrivalTime, burstTime);
        }
        System.out.println();

        // Call FCFS
        fcfs(processes);

        sc.close();
    }
}
