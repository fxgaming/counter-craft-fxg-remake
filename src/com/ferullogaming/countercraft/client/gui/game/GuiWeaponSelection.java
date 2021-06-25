package com.ferullogaming.countercraft.client.gui.game;

import com.ferullogaming.countercraft.client.CCRenderHelper;
import com.ferullogaming.countercraft.client.gui.StringListHelperCC;
import com.ferullogaming.countercraft.client.gui.api.GuiFGButton;
import com.ferullogaming.countercraft.game.GameManager;
import com.ferullogaming.countercraft.game.IGame;
import com.ferullogaming.countercraft.game.IGameComponentEconomy;
import com.ferullogaming.countercraft.item.ItemManager;
import com.ferullogaming.countercraft.item.gun.ItemGun;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.opengl.GL11;

public abstract class GuiWeaponSelection extends GuiScreen {
   public int buyTime = -1;
   protected ArrayList slots = new ArrayList();
   protected GuiScreen lastGui;
   protected GuiSlotWSOption lastHover = null;
   protected IGameComponentEconomy theEconomy;

   public GuiWeaponSelection(GuiScreen par1) {
      this.lastGui = par1;
   }

   public GuiWeaponSelection setPriced(IGameComponentEconomy par1) {
      this.theEconomy = par1;
      return this;
   }

   public void addOption(String par1, String[] par2) {
      GuiSlotWSOption slot = new GuiSlotWSOption(0, 0, par1, 0, 0, par2);
      if (ItemManager.getItemFromName(par1) != null) {
         new ItemStack(ItemManager.getItemFromName(par1));
         slot = (new GuiSlotWSOption(0, 0, par1, 0, 0, par2)).setStack(new ItemStack(ItemManager.getItemFromName(par1)));
         this.slots.add(slot);
      } else {
         this.slots.add(slot);
      }
   }

   public void initGui() {
      int mx = super.width / 2;
      int my = super.height / 2;
      GuiFGButton button = new GuiFGButton(20, mx - 150, 18, 40, 10, "< Обратно");
      button.drawBackground = false;
      super.buttonList.add(button);
      this.initSlots();
   }

   public void initSlots() {
      int mx = super.width / 2;
      int my = super.height / 2;
      int x = mx - 140;
      int y = my - 72;
      int w1 = 100;
      int h1 = 20;
      int ym = 22;

      for(int i = 0; i < this.slots.size(); ++i) {
         ((GuiSlotWSOption)this.slots.get(i)).x = x;
         ((GuiSlotWSOption)this.slots.get(i)).y = y + i * ym;
         ((GuiSlotWSOption)this.slots.get(i)).width = w1;
         ((GuiSlotWSOption)this.slots.get(i)).height = h1;
      }

   }

   public void actionPerformed(GuiButton par1GuiButton) {
      if (par1GuiButton.id == 20) {
         super.mc.displayGuiScreen(this.lastGui);
      }

   }

   protected void keyTyped(char par1, int par2) {
      super.keyTyped(par1, par2);
      if (par2 == 18 && GameManager.instance().currentClientGame != null) {
         IGame game = GameManager.instance().currentClientGame;
         game.getClientSide().onKeyPressed(par2);
      }

   }

   public void updateScreen() {
      if (GameManager.instance().currentClientGame != null) {
         IGame game = GameManager.instance().currentClientGame;
         this.buyTime = game.getPlayerGameData(super.mc.thePlayer.username).getInteger("buyTime");
         if (this.buyTime != -1 && this.buyTime == 0) {
            super.mc.displayGuiScreen((GuiScreen)null);
         }
      }

   }

   public void onGuiClosed() {
   }

   public boolean doesGuiPauseGame() {
      return false;
   }

   protected void mouseClicked(int par1, int par2, int par3) {
      super.mouseClicked(par1, par2, par3);

      for(int i = 0; i < this.slots.size(); ++i) {
         if (((GuiSlotWSOption)this.slots.get(i)).isMouseOver(par1, par2) && par3 == 0) {
            this.onOptionClicked(((GuiSlotWSOption)this.slots.get(i)).option, i + 1);
            Minecraft.getMinecraft().sndManager.playSoundFX("random.click", 1.0F, 2.0F);
         }
      }

   }

   public abstract void onOptionClicked(String var1, int var2);

   public void drawScreen(int par1, int par2, float par3) {
      int mx = super.width / 2;
      int my = super.height / 2;
      EntityPlayer player = super.mc.thePlayer;
      CCRenderHelper.drawRectWithShadow(0.0D, 0.0D, (double)super.width, 30.0D, "0x00000", 0.7F);
      CCRenderHelper.drawRectWithShadow(0.0D, (double)(super.height - 30), (double)super.width, 30.0D, "0x00000", 0.7F);
      CCRenderHelper.renderCenteredText("Меню покупок", mx, 10);
      super.drawScreen(par1, par2, par3);
      CCRenderHelper.renderTextRight(EnumChatFormatting.ITALIC + "Время на покупки: " + EnumChatFormatting.RED + "" + EnumChatFormatting.ITALIC + this.buyTime / 20, mx + 150, 18);
      int bh = this.slots.size() * 22;
      CCRenderHelper.drawRectWithShadow((double)(mx - 150), (double)(my - 80), 120.0D, (double)(bh + 15), "0x00000", 0.7F);
      Iterator i$ = this.slots.iterator();

      while(i$.hasNext()) {
         GuiSlotWSOption slot = (GuiSlotWSOption)i$.next();
         slot.doRender(par1, par2, par3, this.theEconomy);
         if (slot.isMouseOver(par1, par2)) {
            this.lastHover = slot;
         }

         if (this.lastHover == slot) {
            slot.doRenderHighlight(1);
         }
      }

      CCRenderHelper.drawRectWithShadow((double)(mx - 25), (double)(my - 80), 100.0D, 160.0D, "0x00000", 0.7F);
      CCRenderHelper.renderText("Выбрано", mx - 20, my - 75);
      ItemStack stack;
      double scale;
      if (this.lastHover != null) {
         CCRenderHelper.renderText(EnumChatFormatting.WHITE + this.lastHover.option, mx - 20, my - 65);
         String opt = this.lastHover.option;
         int var1;
         if (ItemManager.getItemFromName(opt) != null) {
            stack = new ItemStack(ItemManager.getItemFromName(opt));
            if (this.theEconomy != null) {
               var1 = this.theEconomy.getItemPrice(stack, stack.itemID);
               String var2 = "$" + var1;
               if (this.theEconomy.hasPlayerEconomy(player.username, var1)) {
                  var2 = EnumChatFormatting.GREEN + var2;
               } else {
                  var2 = EnumChatFormatting.RED + var2;
               }

               CCRenderHelper.renderCenteredText(var2, mx + 53, my - 75);
            }

            GL11.glPushMatrix();
            GL11.glTranslated((double)(mx + 30), (double)(my - 25), 10.0D);
            scale = 1.8D;
            GL11.glScaled(scale, scale, scale);
            GL11.glRotated(-20.0D, 0.0D, 0.0D, 1.0D);
            CCRenderHelper.renderSpecialItemStackInventory(stack, 0.0D, 0.0D);
            GL11.glPopMatrix();
            if (stack.getItem() instanceof ItemGun) {
               ItemGun gun = (ItemGun)stack.getItem();
               CCRenderHelper.renderText("Урон: " + gun.gunDamage, mx - 20, my - 15);
               CCRenderHelper.renderText("Урон в голову: " + gun.gunDamageHead, mx - 20, my - 5);
               CCRenderHelper.renderText("Разброс: " + gun.gunSpreadIncreaseFired, mx - 20, my + 5);
               CCRenderHelper.renderText("Отдача: " + gun.gunRecoil, mx - 20, my + 15);
               CCRenderHelper.renderText("Патроны: " + gun.clipSize + "/" + gun.gunMaxAmmo, mx - 20, my + 25);
               CCRenderHelper.renderText("Движение: " + gun.gunMovementPenalty, mx - 20, my + 35);
            }
         } else {
            ArrayList info = StringListHelperCC.getListLimitWidth(StringListHelperCC.convertListToString(new ArrayList(Arrays.asList(this.lastHover.info))), 90);

            for(var1 = 0; var1 < info.size(); ++var1) {
               CCRenderHelper.renderText((String)info.get(var1), mx - 20, my - 45 + var1 * 10);
            }
         }
      }

      CCRenderHelper.drawRectWithShadow((double)(mx + 80), (double)(my - 60), 69.0D, 140.0D, "0x00000", 0.7F);
      CCRenderHelper.renderText("Инвентарь", mx + 85, my - 55);
      if (this.theEconomy != null) {
         CCRenderHelper.drawRectWithShadow((double)(mx + 80), (double)(my - 80), 69.0D, 15.0D, "0x00000", 0.7F);
         CCRenderHelper.renderText(EnumChatFormatting.GREEN + "" + EnumChatFormatting.BOLD + "$" + this.theEconomy.getPlayerEconomy(player.username), mx + 85, my - 76);
      }

      if (player != null && player.inventory != null) {
         for(int i = 0; i < 6; ++i) {
            if (player.inventory.mainInventory[i] != null) {
               stack = player.inventory.mainInventory[i];
               GL11.glPushMatrix();
               GL11.glTranslated((double)(mx + 120), (double)(my - 25 + i * 21), 10.0D);
               scale = 1.2D;
               GL11.glScaled(scale, scale, scale);
               GL11.glRotated(-20.0D, 0.0D, 0.0D, 1.0D);
               CCRenderHelper.renderSpecialItemStackInventory(stack, 0.0D, 0.0D);
               GL11.glPopMatrix();
            }
         }
      }

   }
}
