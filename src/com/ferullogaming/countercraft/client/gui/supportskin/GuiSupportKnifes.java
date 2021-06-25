package com.ferullogaming.countercraft.client.gui.supportskin;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import org.lwjgl.opengl.GL11;
import com.ferullogaming.countercraft.CounterCraft;
import com.ferullogaming.countercraft.client.CCRenderHelper;
import com.ferullogaming.countercraft.client.ClientNotification;
import com.ferullogaming.countercraft.client.cloud.packet.PacketClientRequest;
import com.ferullogaming.countercraft.client.cloud.packet.RequestType;
import com.ferullogaming.countercraft.client.gui.GuiCCContainerProfile;
import com.ferullogaming.countercraft.client.gui.GuiCCMenu;
import com.ferullogaming.countercraft.client.gui.StringListHelperCC;
import com.ferullogaming.countercraft.client.gui.api.GuiFGButton;
import com.ferullogaming.countercraft.client.gui.api.GuiFGContainer;
import com.ferullogaming.countercraft.client.gui.api.GuiFGContainerText;
import com.ferullogaming.countercraft.client.gui.api.GuiFGScroller;
import com.ferullogaming.countercraft.client.gui.inventory.GuiCCMenuInspect;
import com.ferullogaming.countercraft.item.ItemKnife;
import com.ferullogaming.countercraft.item.ItemManager;
import com.ferullogaming.countercraft.item.gun.ItemGun;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

public class GuiSupportKnifes extends GuiCCMenu {
   public static ItemKnife knife;
   public static String currentSkin;
   public GuiScreen parent;
   public float rotationY = 0.0F;
   private static GuiFGButton sk1, sk2, sk3, sk4, sk5, sk6, sk7, sk8, dr0, dr1;
   private static boolean isRotate;
   
   public GuiSupportKnifes(GuiScreen s) {
	   if (s instanceof GuiCCMenu) this.parent = s;
   }
   
public void initGui() {
      super.initGui();
      this.initOverheadMenu();
      int x = super.width / 2 - 173;
      PacketClientRequest.sendRequest(RequestType.PLAYER_INVENTORY_DEFAULTS, super.mc.getSession().getUsername());
      this.addContainer(new GuiCCContainerProfile(1, x + 1, 25, 110, 40, this));
      this.addContainer((new GuiFGContainerText(5, x + 1, 68, 110, 14, this)).setText("Выбрано:").setColor(GuiCCMenu.menuTheme3));
      this.addContainer((new GuiFGContainerText(6, x + 114, 25, 238, 14, this)).setText("Выбор ножей").setColor(GuiCCMenu.menuTheme3));
      this.addContainer(new GuiFGContainer(7, x + 1, 83, 110, 150, this));
      this.addContainer(new GuiFGContainer(8, x + 114, 93, 238, 140, this));
      this.addContainer((new GuiFGContainer(9, x + 118, 97, 230, 132, this)).setColor(GuiCCMenu.menuTheme3));
      super.buttonList.add((new GuiFGButton(20, x + 6, 104, 100, 10, "Возврат.")).setToolTip("Вернутся в меню скинов", Color.black));
      super.buttonList.add((new GuiFGButton(21, x + 116, 42, 62, 10, "Тактический нож")));
      super.buttonList.add((new GuiFGButton(22, x + 182, 42, 36, 10, "Керамбит")));
      super.buttonList.add((new GuiFGButton(23, x + 222, 42, 42, 10, "Нож бабочка")));
      super.buttonList.add((new GuiFGButton(24, x + 268, 42, 48, 10, "Штык-Нож")));
      super.buttonList.add(sk1 = (new GuiFGButton(30, x + 11, 118, 10, 10, "1", true)));
      super.buttonList.add(sk2 = (new GuiFGButton(31, x + 31, 118, 10, 10, "2", true)));
      super.buttonList.add(sk3 = (new GuiFGButton(32, x + 51, 118, 10, 10, "3", true)));
      super.buttonList.add(sk4 = (new GuiFGButton(33, x + 71, 118, 10, 10, "4", true)));
      super.buttonList.add(sk5 = (new GuiFGButton(34, x + 91, 118, 10, 10, "5", true)));
      super.buttonList.add(sk6 = (new GuiFGButton(35, x + 11, 132, 10, 10, "6", true)));
      super.buttonList.add(sk7 = (new GuiFGButton(36, x + 31, 132, 10, 10, "7", true)));
      super.buttonList.add(sk8 = (new GuiFGButton(37, x + 51, 132, 10, 10, "8", true)));
      super.buttonList.add((new GuiFGButton(92, x + 310, 64, 40, 10, "Вкл. Пов.")).setToolTip("Включить поворот", Color.black));
      super.buttonList.add((new GuiFGButton(93, x + 310, 78, 40, 10, "Выкл. Пов.")).setToolTip("Выключить поворот", Color.black));
      super.buttonList.add((new GuiFGButton(94, x + 116, 78, 72, 10, "Сохранить ножи")));
      super.buttonList.add((new GuiFGButton(95, x + 192, 78, 72, 10, "Обнулить ножи")).setToolTip("Используется при багах для восстановления.", Color.black));
      this.clear();
	}

   public void actionPerformed(GuiButton a) {
      super.actionPerformed(a);
      this.addOverheadMenuActionPerformed(a);
      if (a.id == 20) {
    	  this.mc.displayGuiScreen(this.parent);
      }
      if (a.id == 94) {
    	  CounterCraft.instance.getHDD().line3("knife:" + this.knife.getUnlocalizedName().replaceAll("item.", "").toLowerCase(), 1, CounterCraft.instance.getHDD().KnifeInv);
    	  CounterCraft.instance.getHDD().line3("skin:" + this.currentSkin, 2, CounterCraft.instance.getHDD().KnifeInv);
    	  ClientNotification.createNotification("Сохранено: " + this.knife.getUnlocalizedName().replaceAll("item.", "") + " - " + this.currentSkin);
          CounterCraft.instance.getHDD().Read3(CounterCraft.instance.getHDD().KnifeInv);
          this.clear();
          return;
      }
      if (a.id == 95) {
          CounterCraft.instance.getHDD().KnifeInv.delete();
          CounterCraft.instance.getHDD().Read3(CounterCraft.instance.getHDD().KnifeInv);
          ClientNotification.createNotification("Ножи очищены.");
          this.clear();
          return;
      }
      if (a.id == 30 || a.id == 31 || a.id == 32 || a.id == 33 || a.id == 34 || a.id == 35 || a.id == 36 || a.id == 37) {
    	  this.currentSkin = ((GuiFGButton)a).toolTip.equalsIgnoreCase("обычный") ? "" : ((GuiFGButton)a).toolTip;
    	  return;
      }
      if (a.id == 92 || a.id == 93) {
    	  isRotate = a.id == 92;
    	  return;
      }
      
      sk1.drawButton = true;
      sk2.drawButton = true;
	  sk3.drawButton = true;
	  sk4.drawButton = true;
	  sk5.drawButton = true;
	  sk6.drawButton = true;
	  sk7.drawButton = true;
	  sk8.drawButton = true;
      if (a.id == 21) {
    	  knife = (ItemKnife)ItemManager.knifeTactical;
    	  currentSkin = "";
    	  sk1.setToolTip("", Color.black);
    	  sk2.setToolTip("display", Color.black);
    	  sk3.setToolTip("dopplar", Color.black);
    	  sk4.setToolTip("gamma", Color.black);
    	  sk5.setToolTip("lore", Color.black);
    	  sk6.setToolTip("marble", Color.black);
    	  sk7.setToolTip("steel", Color.black);
    	  sk8.setToolTip("tiger", Color.black);
      }
      if (a.id == 22) {
    	  knife = (ItemKnife)ItemManager.knifeKarambit;
    	  currentSkin = "";
    	  sk1.setToolTip("", Color.black);
    	  sk2.setToolTip("dopplar", Color.black);
    	  sk3.setToolTip("eagle", Color.black);
    	  sk4.setToolTip("gamma", Color.black);
    	  sk5.setToolTip("lore", Color.black);
    	  sk6.setToolTip("marble", Color.black);
    	  sk7.setToolTip("steel", Color.black);
    	  sk8.setToolTip("tiger", Color.black);
      }
      if (a.id == 23) {
    	  knife = (ItemKnife)ItemManager.knifeButterfly;
    	  currentSkin = "";
    	  sk2.drawButton = false;
    	  sk3.drawButton = false;
    	  sk4.drawButton = false;
    	  sk5.drawButton = false;
    	  sk6.drawButton = false;
    	  sk7.drawButton = false;
    	  sk8.drawButton = false;
    	  sk1.setToolTip(" ", Color.black);
      }
      if (a.id == 24) {
    	  knife = (ItemKnife)ItemManager.knifeBayonet;
    	  currentSkin = "";
    	  sk1.setToolTip("", Color.black);
    	  sk2.setToolTip("dopplar", Color.black);
    	  sk3.setToolTip("dragonlore", Color.black);
    	  sk4.setToolTip("gamma", Color.black);
    	  sk5.setToolTip("lore", Color.black);
    	  sk6.setToolTip("marble", Color.black);
    	  sk7.setToolTip("steel", Color.black);
    	  sk8.setToolTip("tiger", Color.black);
      }
   }

   public void updateScreen() {
      super.updateScreen();
      if (isRotate) {
    	  if (this.rotationY == 359F) this.rotationY = 0;
    	  else this.rotationY++;
      }
   }

   public void drawScreen(int par1, int par2, float par3) {
      this.drawBackground(par3);
      super.drawScreen(par1, par2, par3);
      int x = super.width / 2 - 173;
      int x1 = x + 107;
      int y1 = 41;
      int lineCount = 0;
      CCRenderHelper.drawRectWithShadow((double)(x1 + 7), (double)(y1 - 1), 238.0D, (double)(10 * 5), GuiCCMenu.menuTheme, 1.0F);
      CCRenderHelper.drawImage((double)(x + 118), 97.0D, GuiCCMenuInspect.background, 230.0D, 132.0D);
      if (this.knife != null) {
    	 String textureName = this.currentSkin;
    	 ItemKnife knifeItem = knife;
         ItemStack knifeStack = new ItemStack(knifeItem, 1);
         ItemKnife.setKnifeSkin(knifeStack, textureName);
         GL11.glPushMatrix();
         GL11.glRotatef(-16F, 0F, 0F, 1F);
         GL11.glTranslatef(-50.0F, 70.0F, 0.0F);
         GL11.glScalef(1.0F, 1.0F, 1.0F);
         CCRenderHelper.renderSpecialItemStackInventoryWithRotationSTIK(knifeStack, (double)(super.width / 2 + 60), 195.0D, 4.0D, 0.0F, this.rotationY);
         GL11.glPopMatrix();
         GL11.glPushMatrix();
    	 CCRenderHelper.drawTextWithOutline("" + knife.getUnlocalizedName().replaceAll("item.", ""), x + 3, 84, 0xFFFFFF);
         GL11.glPopMatrix();
      }
      this.drawButtons(par1, par2, par3);
   }

   public void clear() {
	   knife = (ItemKnife)ItemManager.knifeTactical;
	   currentSkin = "";
	   sk1.setToolTip("", Color.black);
	   sk2.setToolTip("display", Color.black);
	   sk3.setToolTip("dopplar", Color.black);
	   sk4.setToolTip("gamma", Color.black);
	   sk5.setToolTip("lore", Color.black);
	   sk6.setToolTip("marble", Color.black);
	   sk7.setToolTip("steel", Color.black);
	   sk8.setToolTip("tiger", Color.black);
   }
   
   static {
	   knife = (ItemKnife)ItemManager.knifeTactical;
	   currentSkin = "";
   }
}
