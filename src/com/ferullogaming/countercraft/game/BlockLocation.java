package com.ferullogaming.countercraft.game;

import com.f3rullo14.fds.tag.FDSTagCompound;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class BlockLocation {
   public double posX;
   public double posY;
   public double posZ;
   public double rotYaw;
   public double rotPitch;

   public BlockLocation(double par1, double par2, double par3, double par4, double par5) {
      this.posX = par1;
      this.posY = par2;
      this.posZ = par3;
      this.rotYaw = par4;
      this.rotPitch = par5;
   }

   public BlockLocation(EntityPlayer par1) {
      this.posX = par1.posX;
      this.posY = par1.posY;
      this.posZ = par1.posZ;
      this.rotYaw = (double)par1.rotationYaw;
      this.rotPitch = (double)par1.rotationPitch;
   }

   public static BlockLocation createBlockLocationFromFDS(String par1, FDSTagCompound par2) {
      if (par2.hasTag("bl" + par1)) {
         FDSTagCompound tag = par2.getTagCompound("bl" + par1);
         double posX = Double.parseDouble(tag.getString("blockLocation-X"));
         double posY = Double.parseDouble(tag.getString("blockLocation-Y"));
         double posZ = Double.parseDouble(tag.getString("blockLocation-Z"));
         double yaw = Double.parseDouble(tag.getString("blockLocation-YAW"));
         double pitch = Double.parseDouble(tag.getString("blockLocation-PITCH"));
         return new BlockLocation(posX, posY, posZ, yaw, pitch);
      } else {
         return null;
      }
   }

   public void saveToFDS(String par1, FDSTagCompound par2) {
      FDSTagCompound tag = new FDSTagCompound("bl" + par1);
      tag.setString("blockLocation-X", "" + this.posX);
      tag.setString("blockLocation-Y", "" + this.posY);
      tag.setString("blockLocation-Z", "" + this.posZ);
      tag.setString("blockLocation-YAW", "" + this.rotYaw);
      tag.setString("blockLocation-PITCH", "" + this.rotPitch);
      par2.setTagCompound("bl" + par1, tag);
   }

   public boolean isPlayerNear(Entity par1, int par2) {
      double minx = this.posX - (double)par2;
      double miny = this.posY - (double)par2;
      double minz = this.posZ - (double)par2;
      double maxx = this.posX + (double)par2;
      double maxy = this.posY + (double)par2;
      double maxz = this.posZ + (double)par2;
      if (par1.posX >= minx && par1.posX <= maxx && par1.posY >= miny && par1.posY <= maxy) {
         return par1.posZ >= minz && par1.posZ <= maxz;
      } else {
         return false;
      }
   }

   public EntityPlayer getClosestPlayer(World par1, int par2) {
      return par1.getClosestPlayer(this.posX, this.posY, this.posZ, (double)par2);
   }

   public BlockLocation copy() {
      BlockLocation location = new BlockLocation(this.posX, this.posY, this.posZ, this.rotYaw, this.rotPitch);
      return location;
   }

   public String getString() {
      return "Coords[x=" + (int)this.posX + ", y=" + (int)this.posY + ", z=" + (int)this.posZ + "]";
   }

   public String toString() {
      return "BlockLocation[x=" + (int)this.posX + ", y=" + (int)this.posY + ", z=" + (int)this.posZ + "]";
   }
}
