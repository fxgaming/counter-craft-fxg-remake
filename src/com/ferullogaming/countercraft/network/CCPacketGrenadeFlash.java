package com.ferullogaming.countercraft.network;

import com.ferullogaming.countercraft.player.PlayerData;
import com.ferullogaming.countercraft.player.PlayerDataHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.util.MathHelper;

public class CCPacketGrenadeFlash extends CCPacket {
   public static Packet buildPacket(double distance, int id) {
      Packet250CustomPayload packet = new Packet250CustomPayload();
      packet.channel = "ccNetworking";
      ByteArrayOutputStream bytes = new ByteArrayOutputStream();
      DataOutputStream data = new DataOutputStream(bytes);

      try {
         data.write(NetworkManager.getIDFromPacket(CCPacketGrenadeFlash.class));
         data.writeDouble(distance);
         data.writeInt(id);
         packet.data = bytes.toByteArray();
         packet.length = packet.data.length;
         data.close();
         bytes.close();
      } catch (Exception var7) {
         var7.printStackTrace();
      }

      return packet;
   }

   @SideOnly(Side.CLIENT)
   public void execute(DataInputStream stream, EntityPlayer player, Object[] extradata, Side side) {
      try {
         double distance = stream.readDouble();
         int id = stream.readInt();
         PlayerData data = PlayerDataHandler.getClientPlayerData();
         if (this.isInFieldOfVision(player.worldObj.getEntityByID(id), player)) {
            data.flashTime += (40.0D - distance) * 0.6D;
         } else if (distance < 10.0D) {
            data.flashTime = 4.0D;
         }

         if (data.flashTime > 11.0D) {
            data.flashTime = 11.0D;
         }
      } catch (IOException var9) {
         var9.printStackTrace();
      }

   }

   protected boolean isInFieldOfVision(Entity e1, EntityLivingBase e2) {
      float rotationYawPrime = e2.rotationYaw;
      float rotationPitchPrime = e2.rotationPitch;
      this.faceEntity(e2, e1, 360.0F, 360.0F);
      float f = e2.rotationYaw;
      float f2 = e2.rotationPitch;
      e2.rotationYaw = rotationYawPrime;
      e2.rotationPitch = rotationPitchPrime;
      float X = 60.0F;
      float Y = 60.0F;
      float yawFOVMin = e2.rotationYaw - X;
      float yawFOVMax = e2.rotationYaw + X;
      float pitchFOVMin = e2.rotationPitch - Y;
      float pitchFOVMax = e2.rotationPitch + Y;
      boolean var10000;
      if ((yawFOVMin >= 0.0F || f < yawFOVMin + 360.0F && f > yawFOVMax) && (yawFOVMax < 360.0F || f > yawFOVMax - 360.0F && f < yawFOVMin) && (yawFOVMax >= 360.0F || yawFOVMin < 0.0F || f > yawFOVMax || f < yawFOVMin)) {
         var10000 = false;
      } else {
         var10000 = true;
      }

      boolean flag1 = f < yawFOVMax && f > yawFOVMin;
      boolean flag2 = pitchFOVMin <= -180.0F && (f2 >= pitchFOVMin + 360.0F || f2 <= pitchFOVMax) || pitchFOVMax > 180.0F && (f2 <= pitchFOVMax - 360.0F || f2 >= pitchFOVMin) || pitchFOVMax < 180.0F && pitchFOVMin >= -180.0F && f2 <= pitchFOVMax && f2 >= pitchFOVMin;
      return flag1 && flag2 && e2.canEntityBeSeen(e1);
   }

   public void faceEntity(EntityLivingBase par1, Entity par1Entity, float par2, float par3) {
      double d0 = par1Entity.posX - par1.posX;
      double d1 = par1Entity.posZ - par1.posZ;
      double d2;
      if (par1Entity instanceof EntityLivingBase) {
         EntityLivingBase entitylivingbase = (EntityLivingBase)par1Entity;
         d2 = entitylivingbase.posY + (double)entitylivingbase.getEyeHeight() - (par1.posY + (double)par1.getEyeHeight());
      } else {
         d2 = (par1Entity.boundingBox.minY + par1Entity.boundingBox.maxY) / 2.0D - (par1.posY + (double)par1.getEyeHeight());
      }

      double d3 = (double)MathHelper.sqrt_double(d0 * d0 + d1 * d1);
      float f2 = (float)(Math.atan2(d1, d0) * 180.0D / 3.141592653589793D) - 90.0F;
      float f3 = (float)(-(Math.atan2(d2, d3) * 180.0D / 3.141592653589793D));
      par1.rotationPitch = this.updateRotation(par1.rotationPitch, f3, par3);
      par1.rotationYaw = this.updateRotation(par1.rotationYaw, f2, par2);
   }

   private float updateRotation(float par1, float par2, float par3) {
      float f3 = MathHelper.wrapAngleTo180_float(par2 - par1);
      if (f3 > par3) {
         f3 = par3;
      }

      if (f3 < -par3) {
         f3 = -par3;
      }

      return par1 + f3;
   }
}
