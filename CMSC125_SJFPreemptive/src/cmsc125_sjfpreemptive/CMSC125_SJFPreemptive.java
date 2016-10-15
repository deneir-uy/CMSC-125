package cmsc125_sjfpreemptive;

import java.io.*;
import java.util.*;

class Process {
    private int burstTime;
    private int arrivalTime;
    private int waitTime;
    private int turnAroundTime;
    private int remainingTime;
    private int processID;
    private String gantt;

    Process (int burstTime, int arrivalTime, int processID){
        setBurstTime(burstTime);
        setArrivalTime(arrivalTime);
        setRemainingTime(getBurstTime());
        setProcessID(processID);
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

    /**
     * @return the arrivalTime
     */
    public int getArrivalTime() {
        return arrivalTime;
    }

    /**
     * @param arrivalTime the arrivalTime to set
     */
    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    /**
     * @return the remainingTime
     */
    public int getRemainingTime() {
        return remainingTime;
    }

    /**
     * @param remainingTime the remainingTime to set
     */
    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
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
}

class ProcessRemainingTimeComparator implements Comparator<Process>
{
    @Override
    public int compare(Process p, Process p1)
    {
        return p.getRemainingTime() - p1.getRemainingTime();
    }
}

public class CMSC125_SJFPreemptive {
    
    public static void main(String args[]) throws Exception 
    { 
        Comparator<Process> comparator = new ProcessRemainingTimeComparator(); 
        ArrayList<Process> processes = new ArrayList<>();
        PriorityQueue<Process> remainingTimePriority = new PriorityQueue<Process>(comparator);
        Stack<Process> executingProcess = new Stack<>();
        int n, burst, arrival;
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
            
            System.out.println("Enter ARRIVAL TIME for process "+(i));
            arrival = (Integer.parseInt(br.readLine()));


            Process p = new Process(burst, arrival, i);
            processes.add(p);
            
        } 
        
        Collections.sort(processes, new Comparator<Process>() {
            public int compare(Process left, Process right)
            {
                return left.getArrivalTime() - right.getArrivalTime();
            }
        });

        String initialGantt = "";
        int time = 0, i = 0, ganttLength = 0;

        do {
            if(i < processes.size() && processes.get(i).getArrivalTime() == time){
                remainingTimePriority.add(processes.get(i));
                i++;

                if(executingProcess.isEmpty()) {
                    initialGantt = "";
                    for (int j = 0; j < ganttLength; j++) {
                        initialGantt += " ";
                    }

                    executingProcess.add(remainingTimePriority.remove());
                    executingProcess.peek().setGantt(initialGantt);
                }

                else if (executingProcess.peek().getRemainingTime() > remainingTimePriority.peek().getRemainingTime()) {
                    ganttLength = executingProcess.peek().getGantt().length();
                    initialGantt = "";
                    for (int j = 0; j < ganttLength; j++) {
                        initialGantt += " ";
                    }

                    executingProcess.add(remainingTimePriority.remove());
                    executingProcess.peek().setGantt(initialGantt);
                }
            }

            else {
                if(executingProcess.peek().getRemainingTime() == 0) {
                    executingProcess.peek().setTurnAroundTime(executingProcess.peek().getBurstTime() + executingProcess.peek().getWaitTime());
                    ganttLength = executingProcess.pop().getGantt().length();
                    if (!remainingTimePriority.isEmpty()) {
                        if(!executingProcess.isEmpty() && executingProcess.peek().getRemainingTime() > remainingTimePriority.peek().getRemainingTime()) {

                                initialGantt = "";
                                for (int j = 0; j < ganttLength; j++) {
                                    initialGantt += " ";
                                }

                                executingProcess.add(remainingTimePriority.remove());
                                executingProcess.peek().setGantt(initialGantt);
                        }
                    }

                    if(executingProcess.isEmpty() && !remainingTimePriority.isEmpty()) {
                        initialGantt = "";
                        for (int j = 0; j < ganttLength; j++) {
                            initialGantt += " ";
                        }

                        executingProcess.add(remainingTimePriority.remove());
                        executingProcess.peek().setGantt(initialGantt);
                    }
                }
                else {
                    for (Process iterated : remainingTimePriority) {
                        iterated.setWaitTime(iterated.getWaitTime() + 1);
                        iterated.setGantt(iterated.getGantt() + " ");
                    }
                    for (Process iterated : executingProcess) {
                        iterated.setWaitTime(iterated.getWaitTime() + 1);
                        iterated.setGantt(iterated.getGantt() + " ");
                    }
                    executingProcess.peek().setWaitTime(executingProcess.peek().getWaitTime() - 1);
                    executingProcess.peek().setGantt(executingProcess.peek().getGantt().substring(0, executingProcess.peek().getGantt().length() - 1));
                    executingProcess.peek().setRemainingTime(executingProcess.peek().getRemainingTime() - 1);
                    executingProcess.peek().setGantt(executingProcess.peek().getGantt() + "#");
                    time++;
                }
            }
        }while(!executingProcess.isEmpty());
        System.out.println("***********************************************");
        
        System.out.println("P\tBT\tAT\tWT\tTT\t");
        
        for (i = 0; i < processes.size(); i++) 
        {
            System.out.print("P" + processes.get(i).getProcessID() + "\t");
            System.out.print(processes.get(i).getBurstTime() + "\t");
            System.out.print(processes.get(i).getArrivalTime() + "\t");
            System.out.print(processes.get(i).getWaitTime() + "\t");
            System.out.print(processes.get(i).getTurnAroundTime() + "\t");
            System.out.println("");

            averageTurnAroundTime += processes.get(i).getTurnAroundTime();
            averageWaitTime += processes.get(i).getWaitTime();
        }

        System.out.println("Gantt:");

        for (Process process : processes) System.out.println("P" + process.getProcessID() + ":" + process.getGantt());

        averageTurnAroundTime /= n;
        averageWaitTime /= n;

        System.out.println("Average wait time: " + averageWaitTime);
        System.out.println("Average turn around time: " + averageTurnAroundTime);
    }
}