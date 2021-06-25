package com.ferullogaming.countercraft.game.inf;

import com.f3rullo14.fds.tag.FDSTagCompound;
import com.ferullogaming.countercraft.client.CCRenderHelper;
import com.ferullogaming.countercraft.game.BlockLocation;
import com.ferullogaming.countercraft.game.Team;
import com.ferullogaming.countercraft.item.ItemGrenade;
import com.ferullogaming.countercraft.item.ItemManager;
import com.ferullogaming.countercraft.item.ItemPowerup;
import com.ferullogaming.countercraft.item.gun.ItemGun;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ItemSpawn extends BlockLocation {
   public int itemID;
   public boolean respawning = true;
   public int respawnTick = 999999;
   public int respawnTickItemEntity = 999999;
   public int respawnDelay = 100;

   public ItemSpawn(double par1, double par2, double par3, double par4, double par5, int par6) {
      super(par1, par2, par3, par4, par5);
      this.itemID = par6;
   }

   public ItemSpawn(EntityPlayer par1, int par2) {
      super(par1);
      this.itemID = par2;
   }

   public static ItemSpawn createBlockLocationFromFDS(String par1, FDSTagCompound par2) {
      if (par2.hasTag("bl" + par1)) {
         FDSTagCompound tag = par2.getTagCompound("bl" + par1);
         double posX = Double.parseDouble(tag.getString("blockLocation-X"));
         double posY = Double.parseDouble(tag.getString("blockLocation-Y"));
         double posZ = Double.parseDouble(tag.getString("blockLocation-Z"));
         double yaw = Double.parseDouble(tag.getString("blockLocation-YAW"));
         double pitch = Double.parseDouble(tag.getString("blockLocation-PITCH"));
         int item = tag.getInteger("item");
         int delay = tag.getInteger("delay");
         ItemSpawn cp = new ItemSpawn(posX, posY, posZ, yaw, pitch, item);
         cp.respawnDelay = delay;
         return cp;
      } else {
         return null;
      }
   }

   public void onUpdate(World par1, Infected par2) {
      Item item = Item.itemsList[this.itemID];
      if (item != null && item instanceof ItemPowerup) {
         if (this.respawning) {
            if (this.respawnTick++ > this.respawnDelay) {
               this.respawnTick = 0;
               this.respawning = false;
            }
         } else {
            EntityPlayer player = par1.getClosestPlayer(super.posX, super.posY, super.posZ, 1.0D);
            if (player != null) {
               par2.getPlayerEventHandler().allowItemPickup(player, new ItemStack(item), (EntityItem)null);
            }
         }
      }

   }

   public void forceSpawn(World par1) {
      Item item = Item.itemsList[this.itemID];
      if (item != null && item instanceof ItemGun || item instanceof ItemGrenade) {
         par1.spawnEntityInWorld(new EntityItem(par1, (double)((int)super.posX), (double)((int)super.posY), (double)((int)super.posZ), new ItemStack(item)));
      }

   }

   public void notifyItemPickup() {
      this.respawning = true;
      this.respawnTick = 0;
   }

   @SideOnly(Side.CLIENT)
   public void doRender(float par1, Infected par2) {
      EntityPlayer player = Minecraft.getMinecraft().thePlayer;
      Team team = par2.getPlayerEventHandler().getPlayerTeam((EntityPlayer)player);
      Item item = Item.itemsList[this.itemID];
      if (item != null && item instanceof ItemPowerup) {
         ItemPowerup pitem = (ItemPowerup)item;
         if (pitem == ItemManager.gameItemInfectedLivingHealth && team != null && team.teamName.equalsIgnoreCase("dead")) {
            return;
         }

         if ((pitem == ItemManager.gameItemInfectedDeadDamage || pitem == ItemManager.gameItemInfectedDeadHealth || pitem == ItemManager.gameItemInfectedDeadSpeed || pitem == ItemManager.gameItemInfectedDeadVanish) && team != null && team.teamName.equalsIgnoreCase("living")) {
            return;
         }

         ResourceLocation res = new ResourceLocation("countercraft:textures/items/" + pitem.getCCTextureName() + ".png");
         String color = "0xffffff";
         if (this.respawning) {
            color = "0x555555";
         }

         CCRenderHelper.renderImageFacingPlayer(res, super.posX, super.posY + 3.0D, super.posZ, par1, 60, 60, color);
      }

   }

   public void writeToFDS(String par1, FDSTagCompound par2) {
      par2.setBoolean("respawning" + par1, this.respawning);
   }

   public void readFromFDS(String par1, FDSTagCompound par2) {
      this.respawning = par2.getBoolean("respawning" + par1);
   }

   public void saveToFDS(String par1, FDSTagCompound par2) {
      FDSTagCompound tag = new FDSTagCompound("bl" + par1);
      tag.setString("blockLocation-X", "" + super.posX);
      tag.setString("blockLocation-Y", "" + super.posY);
      tag.setString("blockLocation-Z", "" + super.posZ);
      tag.setString("blockLocation-YAW", "" + super.rotYaw);
      tag.setString("blockLocation-PITCH", "" + super.rotPitch);
      tag.setInteger("item", this.itemID);
      tag.setInteger("delay", this.respawnDelay);
      par2.setTagCompound("bl" + par1, tag);
   }

   public ItemSpawn copy() {
      ItemSpawn location = new ItemSpawn(super.posX, super.posY, super.posZ, super.rotYaw, super.rotPitch, this.itemID);
      return location;
   }

   public String getString() {
      return "Coords[item=" + this.itemID + ", x=" + (int)super.posX + ", y=" + (int)super.posY + ", z=" + (int)super.posZ + "]";
   }

   public String toString() {
      return "CPoint[item=" + this.itemID + ", x=" + (int)super.posX + ", y=" + (int)super.posY + ", z=" + (int)super.posZ + "]";
   }
}
