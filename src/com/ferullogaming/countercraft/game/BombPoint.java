package com.ferullogaming.countercraft.game;

import com.f3rullo14.fds.tag.FDSTagCompound;
import com.ferullogaming.countercraft.client.CCRenderHelper;
import com.ferullogaming.countercraft.entity.EntityManager;
import java.util.ArrayList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class BombPoint extends BlockLocation {
   public static final int bombPlantRadius = 5;
   public String title;
   public boolean isBombPlaneted = false;

   public BombPoint(double par1, double par2, double par3, double par4, double par5, String par6) {
      super(par1, par2, par3, par4, par5);
      this.title = par6;
   }

   public BombPoint(EntityPlayer par1, String par2) {
      super(par1);
      this.title = par2;
   }

   public static BombPoint createBlockLocationFromFDS(String par1, FDSTagCompound par2) {
      if (par2.hasTag("bl" + par1)) {
         FDSTagCompound tag = par2.getTagCompound("bl" + par1);
         double posX = Double.parseDouble(tag.getString("blockLocation-X"));
         double posY = Double.parseDouble(tag.getString("blockLocation-Y"));
         double posZ = Double.parseDouble(tag.getString("blockLocation-Z"));
         double yaw = Double.parseDouble(tag.getString("blockLocation-YAW"));
         double pitch = Double.parseDouble(tag.getString("blockLocation-PITCH"));
         String title = tag.getString("title");
         BombPoint cp = new BombPoint(posX, posY, posZ, yaw, pitch, title);
         return cp;
      } else {
         return null;
      }
   }

   public void onClientUpdate() {
   }

   public void doParticles() {
      String block = "42_0";
      if (this.isBombPlaneted) {
         block = "35_14";
      }

      for(int i = 0; i < 4; ++i) {
         EntityManager.spawnParticle("tilecrack_" + block, super.posX, super.posY + 3.5D, super.posZ, 0.0D, 0.0D, 0.0D);
      }

   }

   public void doRender(float par1) {
      String color = "0xffffff";
      if (this.isBombPlaneted) {
         color = "0xe34646";
      }

      CCRenderHelper.renderStringFacingPlayer("Точка закладки бомбы", super.posX, super.posY + 3.6D, super.posZ, par1, 1.0D);
      CCRenderHelper.renderImageFacingPlayer(new ResourceLocation("countercraft:textures/misc/capturepoint.png"), super.posX, super.posY + 3.0D, super.posZ, par1, 60, 60, color);
      CCRenderHelper.renderStringFacingPlayer(this.title.toUpperCase(), super.posX, super.posY + 3.22D, super.posZ, par1, 2.0D);
   }

   public ArrayList getPlayersAround(IGame par1) {
      ArrayList peeps = new ArrayList();

      for(int i = 0; i < par1.getPlayerEventHandler().getPlayers().size(); ++i) {
         EntityPlayer player = GameHelper.getPlayerFromUsername((String)par1.getPlayerEventHandler().getPlayers().get(i));
         if (player != null && !player.isDead && this.isPlayerNear(player, 5)) {
            peeps.add(player);
         }
      }

      return peeps;
   }

   public void writeToFDS(FDSTagCompound par1) {
      par1.setBoolean("planted" + this.title, this.isBombPlaneted);
   }

   public void readFromFDS(FDSTagCompound par1) {
      this.isBombPlaneted = par1.getBoolean("planted" + this.title);
   }

   public void saveToFDS(String par1, FDSTagCompound par2) {
      FDSTagCompound tag = new FDSTagCompound("bl" + par1);
      tag.setString("blockLocation-X", "" + super.posX);
      tag.setString("blockLocation-Y", "" + super.posY);
      tag.setString("blockLocation-Z", "" + super.posZ);
      tag.setString("blockLocation-YAW", "" + super.rotYaw);
      tag.setString("blockLocation-PITCH", "" + super.rotPitch);
      tag.setString("title", this.title);
      par2.setTagCompound("bl" + par1, tag);
   }

   public BombPoint copy() {
      BombPoint location = new BombPoint(super.posX, super.posY, super.posZ, super.rotYaw, super.rotPitch, this.title);
      location.isBombPlaneted = this.isBombPlaneted;
      return location;
   }

   public String getString() {
      return "Coords[title=" + this.title + ", x=" + (int)super.posX + ", y=" + (int)super.posY + ", z=" + (int)super.posZ + "]";
   }

   public String toString() {
      return "BPoint[title=" + this.title + ", x=" + (int)super.posX + ", y=" + (int)super.posY + ", z=" + (int)super.posZ + "]";
   }
}
