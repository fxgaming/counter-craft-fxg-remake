package com.ferullogaming.countercraft.client.gui.store;

import com.ferullogaming.countercraft.client.CCRenderHelper;
import com.ferullogaming.countercraft.client.cloud.item.CloudItemStack;
import com.ferullogaming.countercraft.client.gui.api.GuiFGTextPrompt;
import com.ferullogaming.countercraft.client.gui.inventory.GuiCCSlotInventory;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.opengl.GL11;

public abstract class GuiCCStorePrompt extends GuiFGTextPrompt {
   private int id;
   private GuiCCSlotInventory slot;

   public GuiCCStorePrompt(GuiScreen par1, int par2) {
      super(par1);
      this.id = par2;
   }

   public void initGui() {
      super.initGui();
      if (this.id > 0) {
         this.slot = new GuiCCSlotInventory(super.promptX + super.promptWidth / 2 - 22, super.promptY - 50, new CloudItemStack("-fake-", this.id), 45, 45, false);
      }

   }

   public void drawScreen(int i, int j, float f) {
      super.drawScreen(i, j, f);
      if (this.slot != null) {
         int dy = 0;
         if (super.promptInformation[1] != null && super.promptInformation[1].length() > 6) {
            dy = 5;
         }

         GL11.glPushMatrix();
         GL11.glTranslated((double)(super.promptX + super.promptWidth / 2), (double)(super.promptY - 22 + dy), 0.0D);
         double scale = 0.9D;
         GL11.glScaled(scale, scale, scale);
         CCRenderHelper.renderSpecialItemStackInventory(this.slot.stack.getItemStack(), 0.0D, 0.0D);
         GL11.glPopMatrix();
      }

   }
}
