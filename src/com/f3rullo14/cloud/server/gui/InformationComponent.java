package com.f3rullo14.cloud.server.gui;

import com.f3rullo14.cloud.server.G_CloudManager;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Set;
import javax.swing.JComponent;
import javax.swing.Timer;

public class InformationComponent extends JComponent {
   private static final DecimalFormat field_120040_a = new DecimalFormat("########0.000");
   private int[] field_120038_b = new int[256];
   private int field_120039_c;
   private ArrayList displayStrings = new ArrayList();
   private ArrayList displayStrings2 = new ArrayList();
   private final G_CloudManager field_120037_e;

   public InformationComponent(G_CloudManager par1MinecraftServer) {
      this.field_120037_e = par1MinecraftServer;
      this.setPreferredSize(new Dimension(456, 246));
      this.setMinimumSize(new Dimension(456, 246));
      this.setMaximumSize(new Dimension(456, 246));
      (new Timer(100, new InformationComponentINNER1(this))).start();
      this.setBackground(Color.BLACK);
   }

   private void func_120034_a() {
      this.displayStrings.clear();
      this.displayStrings2.clear();
      System.gc();
      Runtime runtime = Runtime.getRuntime();
      NumberFormat format = NumberFormat.getInstance();
      double maxMemory = (double)runtime.maxMemory() / 1024.0D / 1024.0D;
      double allocatedMemory = (double)runtime.totalMemory() / 1024.0D / 1024.0D;
      double freeMemory = (double)runtime.freeMemory() / 1024.0D / 1024.0D;
      double totalMemory = allocatedMemory + maxMemory + 500.0D;
      Set threadSet = Thread.getAllStackTraces().keySet();
      this.displayStrings.addAll(this.field_120037_e.getDisplayInformation(new ArrayList()));
      this.displayStrings2.add("Alloc Mem Usage: " + format.format((long)(allocatedMemory - freeMemory)) + "mb (" + format.format((allocatedMemory - freeMemory) * 100.0D / allocatedMemory) + "%)");
      this.displayStrings2.add("Alloc Mem Free: " + format.format((long)freeMemory) + "mb");
      this.displayStrings2.add("Alloc Mem Total: " + this.roundDouble(allocatedMemory / 1024.0D, 1) + "gb");
      this.displayStrings2.add("Total Mem Max: " + this.roundDouble(totalMemory / 1024.0D, 1) + "gb");
      this.displayStrings2.add("Total Mem Free: " + this.roundDouble((freeMemory + (maxMemory - allocatedMemory) + 500.0D) / 1024.0D, 1) + "gb");
      this.displayStrings2.add("Threads Open: " + threadSet.size());
      this.repaint();
   }

   public void paint(Graphics par1Graphics) {
      par1Graphics.setColor(new Color(16777215));
      par1Graphics.fillRect(0, 0, 456, 246);

      int i;
      for(i = 0; i < 256; ++i) {
         int j = this.field_120038_b[i + this.field_120039_c & 255];
         par1Graphics.setColor(new Color(j + 28 << 16));
         par1Graphics.fillRect(i, 100 - j, 1, j);
      }

      par1Graphics.setColor(Color.BLACK);

      String s;
      for(i = 0; i < this.displayStrings.size(); ++i) {
         s = (String)this.displayStrings.get(i);
         if (s != null) {
            par1Graphics.drawString(s, 10, 14 + i * 16);
         }
      }

      for(i = 0; i < this.displayStrings2.size(); ++i) {
         s = (String)this.displayStrings2.get(i);
         if (s != null) {
            par1Graphics.drawString(s, 228, 14 + i * 16);
         }
      }

   }

   public double roundDouble(double value, int places) {
      if (places < 0) {
         throw new IllegalArgumentException();
      } else {
         long factor = (long)Math.pow(10.0D, (double)places);
         value *= (double)factor;
         long tmp = Math.round(value);
         return (double)tmp / (double)factor;
      }
   }

   static void func_120033_a(InformationComponent par0StatsComponent) {
      par0StatsComponent.func_120034_a();
   }
}
