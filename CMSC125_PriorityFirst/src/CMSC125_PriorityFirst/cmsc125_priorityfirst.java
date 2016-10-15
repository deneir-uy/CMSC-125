package CMSC125_PriorityFirst;

import java.io.*;
import java.util.*;

class Process {
    private int burstTime;
    private int priority;
    private int waitTime;
    private int turnAroundTime;
    private int processID;

    Process (int burstTime, int priority, int processID){
        setBurstTime(burstTime);
        setPriority(priority);
        setProcessID(processID);
    }

    /**
     * @return the burstTime
     */
    public int getBurstTime() {
        return burstTime;
    }

    /**
     * @param burstTime the burstTime to set
     */
    public void setBurstTime(int burstTime) {
        this.burstTime = burstTime;
    }

    /**
     * @return the waitTime
     */
    public int getWaitTime() {
        return waitTime;
    }

    /**
     * @param waitTime the waitTime to set
     */
    public void setWaitTime(int waitTime) {
        this.waitTime = waitTime;
    }

    /**
     * @return the turnAroundTime
     */
    public int getTurnAroundTime() {
        return turnAroundTime;
    }

    /**
     * @param turnAroundTime the turnAroundTime to set
     */
    public void setTurnAroundTime(int turnAroundTime) {
        this.turnAroundTime = turnAroundTime;
    }

    /**
     * @return the priority
     */
    public int getPriority() {
        return priority;
    }

    /**
     * @param priority the priority to set
     */
    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getProcessID() {
        return processID;
    }

    public void setProcessID(int processID) {
        this.processID = processID;
    }

    /**
     * @return the remainingTime
     */

}

class ProcessRemainingTimeComparator implements Comparator<Process>
{
    @Override
    public int compare(Process p, Process p1)
    {
        return p1.getPriority() - p.getPriority();
    }
}

public class cmsc125_priorityfirst {

    public static void main(String args[]) throws Exception
    {
        new ProcessRemainingTimeComparator();
        ArrayList<Process> processes = new ArrayList<>();
        int n, burst, priority;
        float averageWaitTime = 0, averageTurnAroundTime = 0;
        InputStreamReader isr=new InputStreamReader(System.in);
        BufferedReader br=new BufferedReader(isr);
        System.out.println("Enter no of process");
        n=Integer.parseInt(br.readLine());

        System.out.println("Enter Burst time for each processes\n******************************");

        for(int i=0;i<n;i++)
        {

            System.out.println("Enter BURST TIME for process "+(i));
            burst = (Integer.parseInt(br.readLine()));

            System.out.println("Enter PRIORITY for process "+(i));
            priority = (Integer.parseInt(br.readLine()));


            Process p = new Process(burst, priority, i);
            processes.add(p);

        }

        Collections.sort(processes, (left, right) -> left.getPriority() - right.getPriority());
        Collections.reverse(processes);

        System.out.println("***********************************************");

        if (processes.size() == 1) {
            processes.get(0).setWaitTime(0);
            processes.get(0).setTurnAroundTime(processes.get(0).getBurstTime());
        }
        else {
            processes.get(0).setTurnAroundTime(processes.get(0).getBurstTime());
            for (int i = 1; i < processes.size(); i++) {
                processes.get(i).setWaitTime(processes.get(i - 1).getTurnAroundTime());
                processes.get(i).setTurnAroundTime(processes.get(i).getWaitTime() + processes.get(i).getBurstTime());
            }
        }

        System.out.println("P\tBT\tPR\tWT\tTT\tRT");

        for (Process process1 : processes) {
            System.out.print("P" + process1.getProcessID() + "\t");
            System.out.print(process1.getBurstTime() + "\t");
            System.out.print(process1.getPriority() + "\t");
            System.out.print(process1.getWaitTime() + "\t");
            System.out.print(process1.getTurnAroundTime() + "\t");
            System.out.println();

            averageWaitTime += process1.getWaitTime();
            averageTurnAroundTime += process1.getTurnAroundTime();
        }

        System.out.println("Gantt:");

        for (Process process : processes) {
            System.out.print("P" + process.getProcessID() + ": ");

            for (int j = 0; j < process.getWaitTime(); j++) {
                System.out.print(" ");
            }

            for (int j = 0; j < process.getBurstTime(); j++) {
                System.out.print("#");
            }
            System.out.println();
        }

        averageWaitTime /= n;
        averageTurnAroundTime /= n;

        System.out.println("Average wait time: " + averageWaitTime);
        System.out.println("Average turn around time: " + averageTurnAroundTime);
    }
}