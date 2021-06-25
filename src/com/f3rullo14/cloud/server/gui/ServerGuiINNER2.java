package com.f3rullo14.cloud.server.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextField;

class ServerGuiINNER2 implements ActionListener {
   final JTextField field_120025_a;
   final ServerGui field_120024_b;

   ServerGuiINNER2(ServerGui par1MinecraftServerGui, JTextField par2JTextField) {
      this.field_120024_b = par1MinecraftServerGui;
      this.field_120025_a = par2JTextField;
   }

   public void actionPerformed(ActionEvent par1ActionEvent) {
      String s = this.field_120025_a.getText().trim();
      if (s.length() > 0) {
         ServerGui.func_120017_a(this.field_120024_b).addPendingCommand(s);
      }

      this.field_120025_a.setText("");
   }
}
