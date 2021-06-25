package com.ferullogaming.countercraft.game.dom;

import com.f3rullo14.fds.tag.FDSTagCompound;
import com.ferullogaming.countercraft.client.CCRenderHelper;
import com.ferullogaming.countercraft.entity.EntityManager;
import com.ferullogaming.countercraft.game.BlockLocation;
import com.ferullogaming.countercraft.game.GameHelper;
import com.ferullogaming.countercraft.game.IGame;
import com.ferullogaming.countercraft.game.Team;
import java.util.ArrayList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class CapturePoint extends BlockLocation {
   public static final int captureTickMax = 240;
   public static final int captureRadius = 5;
   public String title;
   public String capturedBy = "";
   public String capturing = "";
   public String lastCapturing = "";
   public int captureTick = 0;
   public boolean flicker = false;
   public int flickerTick = 0;

   public CapturePoint(double par1, double par2, double par3, double par4, double par5, String par6) {
      super(par1, par2, par3, par4, par5);
      this.title = par6;
   }

   public CapturePoint(EntityPlayer par1, String par2) {
      super(par1);
      this.title = par2;
   }

   public static CapturePoint createBlockLocationFromFDS(String par1, FDSTagCompound par2) {
      if (par2.hasTag("bl" + par1)) {
         FDSTagCompound tag = par2.getTagCompound("bl" + par1);
         double posX = Double.parseDouble(tag.getString("blockLocation-X"));
         double posY = Double.parseDouble(tag.getString("blockLocation-Y"));
         double posZ = Double.parseDouble(tag.getString("blockLocation-Z"));
         double yaw = Double.parseDouble(tag.getString("blockLocation-YAW"));
         double pitch = Double.parseDouble(tag.getString("blockLocation-PITCH"));
         String title = tag.getString("title");
         CapturePoint cp = new CapturePoint(posX, posY, posZ, yaw, pitch, title);
         return cp;
      } else {
         return null;
      }
   }

   public void onClientUpdate() {
      if (this.flicker && this.flickerTick++ > 40) {
         this.flickerTick = 0;
      }

   }

   public void doParticles() {
      if (this.capturedBy != null && this.capturedBy.length() > 0) {
         String block = "42_0";
         if (this.capturedBy.equalsIgnoreCase("red")) {
            block = "35_14";
         }

         if (this.capturedBy.equalsIgnoreCase("blue")) {
            block = "35_3";
         }

         for(int i = 0; i < 4; ++i) {
            EntityManager.spawnParticle("tilecrack_" + block, super.posX, super.posY + 3.5D, super.posZ, 0.0D, 0.0D, 0.0D);
         }
      }

   }

   public void doRender(float par1) {
      String color = "0xffffff";
      if (this.capturedBy.equalsIgnoreCase("red")) {
         color = "0xe34646";
      }

      if (this.capturedBy.equalsIgnoreCase("blue")) {
         color = "0x516ee9";
      }

      if (this.flicker && this.flickerTick > 30) {
         color = "0xffffff";
         if (this.captureTick >= 140 || this.capturedBy.length() <= 0) {
            if (this.capturing.equalsIgnoreCase("red")) {
               color = "0xe34646";
            }

            if (this.capturing.equalsIgnoreCase("blue")) {
               color = "0x516ee9";
            }
         }
      }

      CCRenderHelper.renderStringFacingPlayer("Capture", super.posX, super.posY + 3.6D, super.posZ, par1, 1.0D);
      CCRenderHelper.renderImageFacingPlayer(new ResourceLocation("countercraft:textures/misc/capturepoint.png"), super.posX, super.posY + 3.0D, super.posZ, par1, 60, 60, color);
      CCRenderHelper.renderStringFacingPlayer(this.title.toUpperCase(), super.posX, super.posY + 3.22D, super.posZ, par1, 2.0D);
   }

   public void onCaptured(World par1, IGame par2, String teamName) {
      for(int i = 0; i < 10; ++i) {
         this.doParticles();
      }

      GameHelper.playSoundFireworks(par1, super.posX, super.posY, super.posZ);
      Team team = par2.getPlayerEventHandler().getTeam(teamName);
      GameHelper.sendChatToAll(par2, "Objective", team.teamColor + teamName.toUpperCase() + " Secured " + this.title.toUpperCase() + "!");
      ArrayList list = this.getPlayersAround(par2);

      for(int i = 0; i < list.size(); ++i) {
         GameHelper.increasePlayerGameValue(par2, ((EntityPlayer)list.get(i)).username, "score", 3);
         GameHelper.increasePlayerGameValue(par2, ((EntityPlayer)list.get(i)).username, "cpoints");
      }

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
      par1.setString("cby" + this.title, this.capturedBy);
      par1.setInteger("cper" + this.title, this.captureTick);
      par1.setBoolean("flick" + this.title, this.flicker);
      par1.setString("cappin" + this.title, this.capturing);
   }

   public void readFromFDS(FDSTagCompound par1) {
      this.capturedBy = par1.getString("cby" + this.title);
      this.captureTick = par1.getInteger("cper" + this.title);
      this.flicker = par1.getBoolean("flick" + this.title);
      this.capturing = par1.getString("cappin" + this.title);
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

   public CapturePoint copy() {
      CapturePoint location = new CapturePoint(super.posX, super.posY, super.posZ, super.rotYaw, super.rotPitch, this.title);
      location.capturedBy = this.capturedBy;
      location.captureTick = this.captureTick;
      return location;
   }

   public String getString() {
      return "Coords[title=" + this.title + ", x=" + (int)super.posX + ", y=" + (int)super.posY + ", z=" + (int)super.posZ + "]";
   }

   public String toString() {
      return "CPoint[title=" + this.title + ", x=" + (int)super.posX + ", y=" + (int)super.posY + ", z=" + (int)super.posZ + "]";
   }
}
