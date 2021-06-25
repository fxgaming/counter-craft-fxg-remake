package com.ferullogaming.countercraft.client.minimap;

import com.ferullogaming.countercraft.client.minimap.tasks.Task;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class BackgroundExecutor {
   public boolean closed = false;
   private ExecutorService executor = Executors.newSingleThreadExecutor();
   private LinkedList taskQueue = new LinkedList();

   public boolean addTask(Task task) {
      if (!this.closed) {
         Future future = this.executor.submit(task);
         task.setFuture(future);
         this.taskQueue.add(task);
      } else {
         MinimapUtils.log("MwExecutor.addTask: error: cannot add task to closed executor");
      }

      return this.closed;
   }

   public boolean processTaskQueue() {
      boolean processed = false;
      Task task = (Task)this.taskQueue.poll();
      if (task != null) {
         if (task.isDone()) {
            task.printException();
            task.onComplete();
            processed = true;
         } else {
            this.taskQueue.push(task);
         }
      }

      return !processed;
   }

   public boolean processRemainingTasks(int attempts, int delay) {
      while(this.taskQueue.size() > 0 && attempts > 0) {
         if (this.processTaskQueue()) {
            try {
               Thread.sleep((long)delay);
            } catch (Exception var4) {
               ;
            }

            --attempts;
         }
      }

      return attempts <= 0;
   }

   public int tasksRemaining() {
      return this.taskQueue.size();
   }

   public boolean close() {
      boolean error = true;

      try {
         this.executor.shutdown();
         this.processRemainingTasks(50, 5);
         error = !this.executor.awaitTermination(10L, TimeUnit.SECONDS);
         error = false;
      } catch (InterruptedException var3) {
         MinimapUtils.log("error: IO task was interrupted during shutdown");
         var3.printStackTrace();
      }

      this.closed = true;
      return error;
   }
}
