package CMSC125_RoundRobin;

import java.io.*;
import java.util.*;

class Process {
    private int burstTime;
    private int waitTime;
    private int turnAroundTime;
    private int processID;
    private int remainingTime;
    private String gantt;

    Process (int burstTime, int processID){
        setBurstTime(burstTime);
        setProcessID(processID);
        setRemainingTime(burstTime);
        setGantt("");
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


    public int getProcessID() {
        return processID;
    }

    public void setProcessID(int processID) {
        this.processID = processID;
    }

    public String getGantt() {
        return gantt;
    }

    public void setGantt(String gantt) {
        this.gantt = gantt;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }
}

public class cmsc125_roundrobin {

    public static void main(String args[]) throws Exception
    {
        ArrayList<Process> processes = new ArrayList<>();
        ArrayList<Process> doneProcesses = new ArrayList<>();
        int n, burst, quantum;
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

            Process p = new Process(burst, i);
            processes.add(p);

        }

        System.out.println("Enter QUANTUM ");
        quantum = (Integer.parseInt(br.readLine()));

        System.out.println("***********************************************");

        while (!processes.isEmpty()) {
            for (int j = 0; j < quantum; j++) {
                if(processes.isEmpty())
                    break;

                for (Process p : processes) {
                    p.setWaitTime(p.getWaitTime() + 1);
                    p.setGantt(p.getGantt() + " ");
                }

                processes.get(0).setGantt(processes.get(0).getGantt().substring(0, processes.get(0).getGantt().length() - 1));
                processes.get(0).setWaitTime(processes.get(0).getWaitTime() - 1);
                processes.get(0).setRemainingTime(processes.get(0).getRemainingTime() - 1);
                processes.get(0).setGantt(processes.get(0).getGantt() + "#");

                if(processes.get(0).getRemainingTime() == 0) {
                    processes.get(0).setTurnAroundTime(processes.get(0).getWaitTime() + processes.get(0).getBurstTime());
                    doneProcesses.add(processes.remove(0));
                    j = -1;
                }
            }
            if(processes.isEmpty())
                break;
            processes.add(processes.remove(0));
        }
        Collections.sort(doneProcesses, new Comparator<Process>() {
            public int compare(Process left, Process right)
            {
                return left.getProcessID() - right.getProcessID();
            }
        });
        System.out.println("P\tBT\tWT\tTT");

        for (Process process1 : doneProcesses) {
            System.out.print("P" + process1.getProcessID() + "\t");
            System.out.print(process1.getBurstTime() + "\t");
            System.out.print(process1.getWaitTime() + "\t");
            System.out.print(process1.getTurnAroundTime() + "\t");
            System.out.println();

            averageWaitTime += process1.getWaitTime();
            averageTurnAroundTime += process1.getTurnAroundTime();
        }

        System.out.println("Gantt:");

        for (Process process : doneProcesses) {
            System.out.print("P" + process.getProcessID() + ":");
            System.out.println(process.getGantt());
        }

        averageWaitTime /= n;
        averageTurnAroundTime /= n;

        System.out.println("Average wait time: " + averageWaitTime);
        System.out.println("Average turn around time: " + averageTurnAroundTime);
    }
}