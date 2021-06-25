package com.f3rullo14.cloud.server.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InformationComponentINNER1 implements ActionListener {
   final InformationComponent field_120030_a;

   InformationComponentINNER1(InformationComponent par1StatsComponent) {
      this.field_120030_a = par1StatsComponent;
   }

   public void actionPerformed(ActionEvent par1ActionEvent) {
      InformationComponent.func_120033_a(this.field_120030_a);
   }
}
