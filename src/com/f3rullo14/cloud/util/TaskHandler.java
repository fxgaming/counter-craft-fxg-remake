package com.f3rullo14.cloud.util;

import java.util.ArrayList;

public class TaskHandler {
   private ArrayList taskList = new ArrayList();

   public void runTasks() {
      for(int i = 0; i < this.taskList.size(); ++i) {
         Task task = (Task)this.taskList.get(i);
         if (!task.isComplete()) {
            try {
               task.runTask();
            } catch (Exception var4) {
               System.out.println("Failed to Handle Task: " + task.getTaskName());
               var4.printStackTrace();
               task.taskCycles = 999999999;
            }

            ++task.taskCycles;
         }

         if (task.isComplete() || task.taskCycles > task.getTaskCyclesMax()) {
            this.taskList.remove(i);
         }
      }

   }

   public void addTask(Task par1) {
      if (!this.taskList.contains(par1)) {
         this.taskList.add(par1);
      }

   }
}
