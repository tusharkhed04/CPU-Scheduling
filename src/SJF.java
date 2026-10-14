import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class SJF {
    static class Process{
        int pid;
        int arrivalTime;
        int brustTime;
        int completionTime;
        int turnarroundTime;
        int waitingTime;

        Process(int pid, int arrivalTime, int brustTime){
            this.pid = pid;
            this.arrivalTime = arrivalTime;
            this.brustTime = brustTime;
        }

    }

    static void sjf(Process[] processes){
        int n = processes.length;
        int currentTime = 0;
        int complete = 0;

        int totalwaitingTime = 0;
        int totalturnAroundTime = 0;

        boolean[] isCompleted = new boolean[n];

        while(complete < n){

            int shortIndex = -1;
            int shortBrust = Integer.MAX_VALUE;

            // getting shortes available process
            for(int i = 0; i < n; i++){

                if(!isCompleted[i] && processes[i].arrivalTime <= currentTime){

                    if(processes[i].brustTime < shortBrust){
                        shortIndex = i;
                        shortBrust = processes[i].brustTime;
                    }
                }
            }

            // if no process available
            if(shortIndex == -1){

                int nextArrival = Integer.MAX_VALUE;
                for(int i = 0; i < n; i++){
                    if(!isCompleted[i]){
                        nextArrival = Math.min(nextArrival, processes[i].arrivalTime);
                    }
                }

                currentTime = nextArrival;
                continue;
            }

            Process p = processes[shortIndex];
            currentTime += p.brustTime;
            p.completionTime = currentTime;

            p.turnarroundTime = p.completionTime - p.arrivalTime;
            p.waitingTime = p.turnarroundTime - p.brustTime;

            totalwaitingTime += p.waitingTime;
            totalturnAroundTime += p.turnarroundTime;

            isCompleted[shortIndex] = true;
            complete++;
        }

        System.out.println("PID\tAT\tBT\tCT\tTAT\tWT");

        for(Process p : processes){
            System.out.println("P"+p.pid+"\t"+p.arrivalTime+"\t"+
                    p.brustTime+"\t"+p.completionTime+"\t"+
                    p.turnarroundTime+"\t"+p.waitingTime);
        }

        double avgT = (double) totalturnAroundTime / n;
        double avgW = (double) totalwaitingTime / n;

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

            processes[i] = new Process(i + 1, arrivalTime, burstTime);
        }

        System.out.println();

        sjf(processes);

        sc.close();
    }
}
