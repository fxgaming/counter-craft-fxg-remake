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

public class GuiSupportStickers extends GuiCCMenu {
   public static ItemGun selectedGun;
   public static int selectedID;
   public static int currentPosition;
   public static int sticker1, sticker2, sticker3;
   public GuiScreen parent;
   private static GuiFGButton sk1, sk2, sk3, sk4, sk5, sk6, sk7, sk8, sk9, sk10, sk11, sk12, sk13, sk14, sk15, sk16, sk17, sk18, sk19, sk20;

   public GuiSupportStickers(GuiScreen s) {
	   if (s instanceof GuiCCMenu) this.parent = s;
   }
   
public void initGui() {
      super.initGui();
      this.initOverheadMenu();
      int x = super.width / 2 - 173;
      PacketClientRequest.sendRequest(RequestType.PLAYER_INVENTORY_DEFAULTS, super.mc.getSession().getUsername());
      this.addContainer(new GuiCCContainerProfile(1, x + 1, 25, 110, 40, this));
      this.addContainer((new GuiFGContainerText(5, x + 1, 68, 110, 14, this)).setText("Выбрано:").setColor(GuiCCMenu.menuTheme3));
      this.addContainer((new GuiFGContainerText(6, x + 114, 25, 238, 14, this)).setText("Выбор оружия").setColor(GuiCCMenu.menuTheme3));
      this.addContainer(new GuiFGContainer(7, x + 1, 83, 110, 150, this));
      this.addContainer(new GuiFGContainer(8, x + 114, 93, 238, 140, this));
      this.addContainer((new GuiFGContainer(9, x + 118, 97, 230, 132, this)).setColor(GuiCCMenu.menuTheme3));
      super.buttonList.add((new GuiFGButton(20, x + 6, 104, 100, 10, "Возврат.")).setToolTip("Вернутся в меню скинов", Color.black));
      super.buttonList.add((new GuiFGButton(21, x + 116, 42, 10, 10, "1")).setToolTip("M4-A4", Color.black));
      super.buttonList.add((new GuiFGButton(22, x + 130, 42, 10, 10, "2")).setToolTip("AK-47", Color.black));
      super.buttonList.add((new GuiFGButton(23, x + 144, 42, 10, 10, "3")).setToolTip("Famas", Color.black));
      super.buttonList.add((new GuiFGButton(24, x + 158, 42, 10, 10, "4")).setToolTip("FN-FAL", Color.black));
      super.buttonList.add((new GuiFGButton(25, x + 172, 42, 10, 10, "5")).setToolTip("Galil-AR", Color.black));
      super.buttonList.add((new GuiFGButton(26, x + 186, 42, 10, 10, "6")).setToolTip("M1911", Color.black));
      super.buttonList.add((new GuiFGButton(27, x + 200, 42, 10, 10, "7")).setToolTip("G18", Color.black));
      super.buttonList.add((new GuiFGButton(28, x + 214, 42, 10, 10, "8")).setToolTip("Deagle", Color.black));
      super.buttonList.add((new GuiFGButton(29, x + 228, 42, 10, 10, "9")).setToolTip("P250", Color.black));
      super.buttonList.add((new GuiFGButton(30, x + 242, 42, 10, 10, "10")).setToolTip("TEC-9", Color.black));
      super.buttonList.add((new GuiFGButton(31, x + 256, 42, 10, 10, "11")).setToolTip("CZ75", Color.black));
      super.buttonList.add((new GuiFGButton(32, x + 270, 42, 10, 10, "12")).setToolTip("Five-Seven", Color.black));
      super.buttonList.add((new GuiFGButton(33, x + 284, 42, 10, 10, "13")).setToolTip("R8 Revolver", Color.black));
      super.buttonList.add((new GuiFGButton(34, x + 298, 42, 10, 10, "14")).setToolTip("P90", Color.black));
      super.buttonList.add((new GuiFGButton(35, x + 312, 42, 10, 10, "15")).setToolTip("MAC-10", Color.black));
      super.buttonList.add((new GuiFGButton(36, x + 326, 42, 10, 10, "16")).setToolTip("Vector", Color.black));
      super.buttonList.add((new GuiFGButton(37, x + 340, 42, 10, 10, "17")).setToolTip("UMP-45", Color.black));
      super.buttonList.add((new GuiFGButton(38, x + 116, 56, 10, 10, "18")).setToolTip("AWP", Color.black));
      super.buttonList.add((new GuiFGButton(39, x + 130, 56, 10, 10, "19")).setToolTip("Dragunov", Color.black));
      super.buttonList.add((new GuiFGButton(40, x + 144, 56, 10, 10, "20")).setToolTip("Scar-20", Color.black));
      super.buttonList.add((new GuiFGButton(41, x + 158, 56, 10, 10, "21")).setToolTip("SSG-08", Color.black));
      super.buttonList.add((new GuiFGButton(42, x + 172, 56, 10, 10, "22")).setToolTip("Nova", Color.black));
      super.buttonList.add((new GuiFGButton(43, x + 186, 56, 10, 10, "23")).setToolTip("Sawed-off", Color.black));
      super.buttonList.add((new GuiFGButton(44, x + 200, 56, 10, 10, "24")).setToolTip("Mag-7", Color.black));
      super.buttonList.add((new GuiFGButton(45, x + 214, 56, 10, 10, "25")).setToolTip("M249", Color.black));
      super.buttonList.add(sk1 = (new GuiFGButton(46, x + 11, 118, 10, 10, "1", true).setToolTip("", Color.black)));
      super.buttonList.add(sk2 = (new GuiFGButton(47, x + 31, 118, 10, 10, "2", true)));
      super.buttonList.add(sk3 = (new GuiFGButton(48, x + 51, 118, 10, 10, "3", true)));
      super.buttonList.add(sk4 = (new GuiFGButton(49, x + 71, 118, 10, 10, "4", true)));
      super.buttonList.add(sk5 = (new GuiFGButton(50, x + 91, 118, 10, 10, "5", true)));
      super.buttonList.add(sk6 = (new GuiFGButton(51, x + 11, 132, 10, 10, "6", true)));
      super.buttonList.add(sk7 = (new GuiFGButton(52, x + 31, 132, 10, 10, "7", true)));
      super.buttonList.add(sk8 = (new GuiFGButton(53, x + 51, 132, 10, 10, "8", true)));
      super.buttonList.add(sk9 = (new GuiFGButton(54, x + 71, 132, 10, 10, "9", true)));
      super.buttonList.add(sk10 = (new GuiFGButton(55, x + 91, 132, 10, 10, "10", true)));
      super.buttonList.add(sk11 = (new GuiFGButton(56, x + 11, 146, 10, 10, "11", true)));
      super.buttonList.add(sk12 = (new GuiFGButton(57, x + 31, 146, 10, 10, "12", true)));
      super.buttonList.add(sk13 = (new GuiFGButton(58, x + 51, 146, 10, 10, "13", true)));
      super.buttonList.add(sk14 = (new GuiFGButton(59, x + 71, 146, 10, 10, "14", true)));
      super.buttonList.add(sk15 = (new GuiFGButton(60, x + 91, 146, 10, 10, "15", true)));
      super.buttonList.add(sk16 = (new GuiFGButton(61, x + 11, 160, 10, 10, "16", true)));
      super.buttonList.add(sk17 = (new GuiFGButton(62, x + 31, 160, 10, 10, "17", true)));
      super.buttonList.add(sk18 = (new GuiFGButton(63, x + 51, 160, 10, 10, "18", true)));
      super.buttonList.add(sk19 = (new GuiFGButton(64, x + 71, 160, 10, 10, "19", true)));
      super.buttonList.add(sk20 = (new GuiFGButton(65, x + 91, 160, 10, 10, "20", true)));
      sk1.setToolTip("Без стикера", Color.black);
	  sk2.setToolTip("Camper", Color.black);
	  sk3.setToolTip("Chicken Strike", Color.black);
	  sk4.setToolTip("Dinked", Color.black);
	  sk5.setToolTip("Howl", Color.black);
	  sk6.setToolTip("IBuyPower Katowice 2014", Color.black);
	  sk7.setToolTip("Kawaii Killer", Color.black);
	  sk8.setToolTip("Lucky 13", Color.black);
	  sk9.setToolTip("Bash", Color.black);
	  sk10.setToolTip("Bosh", Color.black);
	  sk11.setToolTip("Bish", Color.black);
	  sk12.setToolTip("Bomb Code", Color.black);
	  sk13.setToolTip("Crown", Color.black);
	  sk14.setToolTip("Flammable", Color.black);
	  sk15.setToolTip("Silver", Color.black);
	  sk16.setToolTip("Swag", Color.black);
	  sk17.setToolTip("Flick Shot", Color.black);
	  sk18.setToolTip("Drug War Veteran", Color.black);
	  sk19.setToolTip("CS On The Mind", Color.black);
	  sk20.setToolTip("Wanna Fight", Color.black);
	  super.buttonList.add((new GuiFGButton(91, x + 10, 174, 28, 10, "П1", true).setToolTip("Позиция 1", Color.black)));
	  super.buttonList.add((new GuiFGButton(92, x + 42, 174, 28, 10, "П2", true).setToolTip("Позиция 2", Color.black)));
	  super.buttonList.add((new GuiFGButton(93, x + 74, 174, 28, 10, "П3", true).setToolTip("Позиция 3", Color.black)));
      super.buttonList.add((new GuiFGButton(94, x + 116, 78, 72, 10, "Сохранить стикеры")));
      super.buttonList.add((new GuiFGButton(95, x + 192, 78, 72, 10, "Обнулить стикеры")).setToolTip("Используется при багах для восстановления.", Color.black));
      this.clear();
	}

   public void actionPerformed(GuiButton a) {
      super.actionPerformed(a);
      this.addOverheadMenuActionPerformed(a);
      if (a.id == 20) {
    	  this.mc.displayGuiScreen(this.parent);
      }
      if (a.id == 46 || a.id == 47 || a.id == 48 || a.id == 49 || a.id == 50 || a.id == 51 || a.id == 52 || a.id == 53 || a.id == 54 || a.id == 55 || a.id == 56 || a.id == 57 || a.id == 58 || a.id == 59 || a.id == 60 || a.id == 61 || a.id == 62 || a.id == 63 || a.id == 64 || a.id == 65 || a.id == 66) {
    	  if (this.currentPosition == 1) this.sticker1 = a.id - 46;
    	  if (this.currentPosition == 2) this.sticker2 = a.id - 46;
    	  if (this.currentPosition == 3) this.sticker3 = a.id - 46;
    	  return;
      }
      if (a.id == 91 || a.id == 92 || a.id == 93) {
    	  this.currentPosition = a.id - 90;
      }
      if (a.id == 94) {
    	  String rep = this.selectedID + ":" + this.getSticker(sticker1) + "," + this.getSticker(sticker2) + "," + this.getSticker(sticker3) + ":";
    	  CounterCraft.instance.getHDD().line2(rep, this.selectedID, CounterCraft.instance.getHDD().StickerInv);
    	  ClientNotification.createNotification("Сохранено: Стикеры на " + this.selectedGun.getUnlocalizedName().replaceAll("item.", ""));
    	  selectedGun = (ItemGun)ItemManager.m4a1;
          selectedID = 1;
          this.clear();
          CounterCraft.instance.getHDD().Read2(CounterCraft.instance.getHDD().StickerInv);
      }
      if (a.id == 95) {
    	  this.clear();
          CounterCraft.instance.getHDD().StickerInv.delete();
          CounterCraft.instance.getHDD().WriteDefaults(CounterCraft.instance.getHDD().StickerInv);
          CounterCraft.instance.getHDD().Read2(CounterCraft.instance.getHDD().StickerInv);
          ClientNotification.createNotification("Стикеры очищены.");
      }
      
      if (a.id == 21) {
    	  selectedGun = (ItemGun)ItemManager.m4a1;
    	  selectedID = (a.id - 20);
          this.stickerclear();
      }
      if (a.id == 22) {
    	  selectedGun = (ItemGun)ItemManager.ak47;
    	  selectedID = (a.id - 20);
          this.stickerclear();
      }
      if (a.id == 23) {
    	  selectedGun = (ItemGun)ItemManager.famas;
          selectedID = (a.id - 20);
          this.stickerclear();
      }
      if (a.id == 24) {
    	  selectedGun = (ItemGun)ItemManager.fnfal;
          selectedID = (a.id - 20);
          this.stickerclear();
      }
      if (a.id == 25) {
    	  selectedGun = (ItemGun)ItemManager.galil;
          selectedID = (a.id - 20);
          this.stickerclear();
      }
      if (a.id == 26) {
    	  selectedGun = (ItemGun)ItemManager.m1911;
          selectedID = (a.id - 20);
          this.stickerclear();
      }
      if (a.id == 27) {
    	  selectedGun = (ItemGun)ItemManager.g18;
          selectedID = (a.id - 20);
          this.stickerclear();
      }
      if (a.id == 28) {
    	  selectedGun = (ItemGun)ItemManager.deagle;
          selectedID = (a.id - 20);
          this.stickerclear();
      }
      if (a.id == 29) {
    	  selectedGun = (ItemGun)ItemManager.p250;
          selectedID = (a.id - 20);
          this.stickerclear();
      }
      if (a.id == 30) {
    	  selectedGun = (ItemGun)ItemManager.tec9;
          selectedID = (a.id - 20);
          this.stickerclear();
      }
      if (a.id == 31) {
    	  selectedGun = (ItemGun)ItemManager.cz75;
          selectedID = (a.id - 20);
          this.stickerclear();
      }
      if (a.id == 32) {
    	  selectedGun = (ItemGun)ItemManager.fiveSeven;
          selectedID = (a.id - 20);
          this.stickerclear();
      }
      if (a.id == 33) {
    	  selectedGun = (ItemGun)ItemManager.r8;
          selectedID = (a.id - 20);
          this.stickerclear();
      }
      if (a.id == 34) {
    	  selectedGun = (ItemGun)ItemManager.p90;
          selectedID = (a.id - 20);
          this.stickerclear();
      }
      if (a.id == 35) {
    	  selectedGun = (ItemGun)ItemManager.mac10;
          selectedID = (a.id - 20);
          this.stickerclear();
      }
      if (a.id == 36) {
    	  selectedGun = (ItemGun)ItemManager.vector;
          selectedID = (a.id - 20);
          this.stickerclear();
      }
      if (a.id == 37) {
    	  selectedGun = (ItemGun)ItemManager.ump45;
          selectedID = (a.id - 20);
          this.stickerclear();
      }
      if (a.id == 38) {
    	  selectedGun = (ItemGun)ItemManager.awp;
          selectedID = (a.id - 20);
          this.stickerclear();
      }
      if (a.id == 39) {
    	  selectedGun = (ItemGun)ItemManager.dragunov;
          selectedID = (a.id - 20);
          this.stickerclear();
      }
      if (a.id == 40) {
    	  selectedGun = (ItemGun)ItemManager.scar20;
          selectedID = (a.id - 20);
          this.stickerclear();
      }
      if (a.id == 41) {
    	  selectedGun = (ItemGun)ItemManager.ssg08;
          selectedID = (a.id - 20);
          this.stickerclear();
      }
      if (a.id == 42) {
    	  selectedGun = (ItemGun)ItemManager.nova;
          selectedID = (a.id - 20);
          this.stickerclear();
      }
      if (a.id == 43) {
    	  selectedGun = (ItemGun)ItemManager.sawedoff;
          selectedID = (a.id - 20);
          this.stickerclear();
      }
      if (a.id == 44) {
    	  selectedGun = (ItemGun)ItemManager.mag7;
          selectedID = (a.id - 20);
          this.stickerclear();
      }
      if (a.id == 45) {
    	  selectedGun = (ItemGun)ItemManager.m249;
          selectedID = (a.id - 20);
          this.stickerclear();
      }
   }

   public void updateScreen() {
      super.updateScreen();
   }

   public void drawScreen(int par1, int par2, float par3) {
      this.drawBackground(par3);
      super.drawScreen(par1, par2, par3);
      int x = super.width / 2 - 173;
      CCRenderHelper.drawRectWithShadow((double)(x + 107 + 7), (double)(41 - 1), 238.0D, (double)(10 * 5), GuiCCMenu.menuTheme, 1.0F);
      CCRenderHelper.drawImage((double)(x + 118), 97.0D, GuiCCMenuInspect.background, 230.0D, 132.0D);
      if (this.selectedGun != null) {
    	 String textureName = "";
    	 ItemGun gunItem = selectedGun;
         ItemStack gunStack = new ItemStack(gunItem, 1);
         ItemGun.setGunSkin(gunStack, textureName);
         if (sticker1 != 0) ItemGun.setGunStickerInPosition(gunStack, "sticker" + this.getSticker(sticker1), 0);
         if (sticker2 != 0)ItemGun.setGunStickerInPosition(gunStack, "sticker" + this.getSticker(sticker2), 1);
         if (sticker3 != 0)ItemGun.setGunStickerInPosition(gunStack, "sticker" + this.getSticker(sticker3), 2);
         GL11.glPushMatrix();
         GL11.glRotatef(-16F, 0F, 0F, 1F);
         GL11.glTranslatef(-50.0F, 70.0F, 0.0F);
         GL11.glScalef(1.3F, 1.3F, 1.3F);
         CCRenderHelper.renderSpecialItemStackInventoryWithRotationSTIK(gunStack, (double)(super.width / 2 + 60) / 1.3, 195.0D / 1.3, 4.0D, 10.0F, 0.0F);
         GL11.glPopMatrix();
    	 CCRenderHelper.drawTextWithOutline("" + selectedGun.getUnlocalizedName().replaceAll("item.", ""), x + 3, 84, 0xFFFFFF);
    	 CCRenderHelper.drawTextWithOutline("" + (this.getSticker(sticker1).length() > 5 ? this.getSticker(sticker1).substring(0, 5).toUpperCase() : this.getSticker(sticker1).toUpperCase()) + ", " + (this.getSticker(sticker2).length() > 5 ? this.getSticker(sticker2).substring(0, 5).toUpperCase() : this.getSticker(sticker2).toUpperCase()) + ", " + (this.getSticker(sticker3).length() > 5 ? this.getSticker(sticker3).substring(0, 5).toUpperCase() : this.getSticker(sticker3).toUpperCase()), x + 3, 94, 0xFFFFFF);
    	 CCRenderHelper.drawTextWithOutline("Позиция: " + this.currentPosition, x + 10, 188, 0xFFFFFF);
      }
      this.drawButtons(par1, par2, par3);
   }

   public void clear() {
	   currentPosition = 1;
	   selectedID = 1;
	   selectedGun = (ItemGun)ItemManager.m4a1;
	   this.stickerclear();
   }
   
   public void stickerclear() {
	   sticker1 = 0;
	   sticker2 = 0;
	   sticker3 = 0;
   }
   
   public static String getSticker(int i) {
	   switch(i) {
	       case 0:
	    	   return "";
	       case 1:
	    	   return "camper";
	       case 2:
	    	   return "chickenstrike";
	       case 3:
	    	   return "dinked";
	       case 4:
	    	   return "howl";
	       case 5:
	    	   return "ibuypowerkatowice2014";
	       case 6:
	    	   return "kawaiikiller";
	       case 7:
	    	   return "lucky13";
	       case 8:
	    	   return "bash";
	       case 9:
	    	   return "bosh";
	       case 10:
	    	   return "bish";
	       case 11:
	    	   return "bombcode";
	       case 12:
	    	   return "crown";
	       case 13:
	    	   return "flammable";
	       case 14:
	    	   return "silver";
	       case 15:
	    	   return "swag";
	       case 16:
	    	   return "flickshot";
	       case 17:
	    	   return "drugwarveteran";
	       case 18:
	    	   return "csonthemind";
	       case 19:
	    	   return "wannafight";
	       default:
	           return "";
	   }
   }
   
   static {
      selectedGun = (ItemGun)ItemManager.m4a1;
      currentPosition = 1;
      selectedID = 1;
      sticker1 = 0;
      sticker2 = 0;
      sticker3 = 0;
   }
}
