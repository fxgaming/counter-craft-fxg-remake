package com.f3rullo14.fds;

import java.io.IOException;
import java.io.OutputStream;

public final class TeeOutputStream extends OutputStream {
   private final OutputStream out;
   private final OutputStream tee;

   public TeeOutputStream(OutputStream out, OutputStream tee) {
      if (out == null) {
         throw new NullPointerException();
      } else if (tee == null) {
         throw new NullPointerException();
      } else {
         this.out = out;
         this.tee = tee;
      }
   }

   public void write(int b) throws IOException {
      this.out.write(b);
      this.tee.write(b);
   }

   public void write(byte[] b) throws IOException {
      this.out.write(b);
      this.tee.write(b);
   }

   public void write(byte[] b, int off, int len) throws IOException {
      this.out.write(b, off, len);
      this.tee.write(b, off, len);
   }

   public void flush() throws IOException {
      this.out.flush();
      this.tee.flush();
   }

   public void close() throws IOException {
      this.out.close();
      this.tee.close();
   }
}
