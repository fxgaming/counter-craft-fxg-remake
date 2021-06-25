package com.ferullogaming.countercraft.client.minimap.tasks;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public abstract class Task implements Runnable {
   private Future future = null;

   public abstract void onComplete();

   public abstract void run();

   public final Future getFuture() {
      return this.future;
   }

   public final void setFuture(Future future) {
      this.future = future;
   }

   public final boolean isDone() {
      return this.future != null && this.future.isDone();
   }

   public final void printException() {
      if (this.future != null) {
         try {
            this.future.get();
         } catch (ExecutionException var3) {
            Throwable rootException = var3.getCause();
            rootException.printStackTrace();
         } catch (Exception var4) {
            var4.printStackTrace();
         }
      }

   }
}
