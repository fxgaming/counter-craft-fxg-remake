package com.f3rullo14.cloud.server.gui;

import com.f3rullo14.cloud.server.G_CloudManager;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class ServerGui extends JComponent {
   private static boolean field_120022_a;
   private G_CloudManager dedicatedServer;

   public static void func_120016_a(G_CloudManager par0DedicatedServer) {
      try {
         UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      } catch (Exception var3) {
         ;
      }

      ServerGui minecraftservergui = new ServerGui(par0DedicatedServer);
      field_120022_a = true;
      JFrame jframe = new JFrame(par0DedicatedServer.cloudName);
      jframe.add(minecraftservergui);
      jframe.pack();
      jframe.setLocationRelativeTo((Component)null);
      jframe.setVisible(true);
      jframe.addWindowListener(new ServerGuiINNER1(par0DedicatedServer));
   }

   public ServerGui(G_CloudManager par1DedicatedServer) {
      this.dedicatedServer = par1DedicatedServer;
      this.setPreferredSize(new Dimension(1000, 550));
      this.setLayout(new BorderLayout());

      try {
         this.add(this.func_120018_d(), "Center");
         this.add(this.func_120019_b(), "West");
      } catch (Exception var3) {
         var3.printStackTrace();
      }

   }

   private JComponent func_120019_b() {
      JPanel jpanel = new JPanel(new BorderLayout());
      jpanel.add(new InformationComponent(this.dedicatedServer), "North");
      jpanel.add(this.func_120020_c(), "Center");
      jpanel.setBorder(new TitledBorder(new EtchedBorder(), "Information"));
      return jpanel;
   }

   private JComponent func_120020_c() {
      ConnectionListComponent playerlistcomponent = new ConnectionListComponent(this.dedicatedServer);
      JScrollPane jscrollpane = new JScrollPane(playerlistcomponent, 22, 30);
      jscrollpane.setBorder(new TitledBorder(new EtchedBorder(), "Players"));
      return jscrollpane;
   }

   private JComponent func_120018_d() {
      JPanel jpanel = new JPanel(new BorderLayout());
      JTextArea jtextarea = new JTextArea();
      //this.dedicatedServer.getLogAgent().getServerLogger().addHandler(new TextAreaLogHandler(jtextarea));
      JScrollPane jscrollpane = new JScrollPane(jtextarea, 22, 30);
      jtextarea.setEditable(false);
      JTextField jtextfield = new JTextField();
      jtextfield.addActionListener(new ServerGuiINNER2(this, jtextfield));
      jtextarea.addFocusListener(new ServerGuiINNER3(this));
      jpanel.add(jscrollpane, "Center");
      jpanel.add(jtextfield, "South");
      jpanel.setBorder(new TitledBorder(new EtchedBorder(), "Console"));
      return jpanel;
   }

   static G_CloudManager func_120017_a(ServerGui par0MinecraftServerGui) {
      return par0MinecraftServerGui.dedicatedServer;
   }
}
