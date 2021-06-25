package com.ferullogaming.countercraft.client.events;

import com.ferullogaming.countercraft.block.BlockManager;
import com.ferullogaming.countercraft.client.CCRenderHelper;
import com.ferullogaming.countercraft.client.render.IRenderParticleTick;
import com.ferullogaming.countercraft.client.render.RenderBombItem;
import com.ferullogaming.countercraft.game.GameManager;
import com.ferullogaming.countercraft.game.IGame;
import com.ferullogaming.countercraft.item.ItemCC;
import com.ferullogaming.countercraft.player.PlayerData;
import com.ferullogaming.countercraft.player.PlayerDataHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.client.event.RenderPlayerEvent.Pre;
import net.minecraftforge.event.ForgeSubscribe;

public class RenderEvents {
   @SideOnly(Side.CLIENT)
   private Minecraft mc = Minecraft.getMinecraft();

   private static float interpolateRotation(float a, float b, float p_77034_3_) {
      float f3;
      for(f3 = b - a; f3 < -180.0F; f3 += 360.0F) {
         ;
      }

      while(f3 >= 180.0F) {
         f3 -= 360.0F;
      }

      return a + p_77034_3_ * f3;
   }

   @ForgeSubscribe
   @SideOnly(Side.CLIENT)
   public void onRenderWorldPost(RenderWorldLastEvent evt) {
      EntityPlayer player = this.mc.thePlayer;
      ItemStack itemstack = player.getCurrentEquippedItem();
      if (itemstack != null) {
         IItemRenderer customRenderer = MinecraftForgeClient.getItemRenderer(itemstack, ItemRenderType.EQUIPPED_FIRST_PERSON);
         if (customRenderer != null && customRenderer instanceof IRenderParticleTick) {
            ((IRenderParticleTick)customRenderer).setParticleTick(evt.partialTicks);
         }
      }

      RenderBombItem.particleTick = evt.partialTicks;
      if (GameManager.instance() != null && GameManager.instance().getPlayerGame((EntityPlayer)player) != null) {
         GameManager.instance().getPlayerGame((EntityPlayer)player).getClientSide().onRenderWorld(evt.context, evt.partialTicks);
         PlayerData data = PlayerDataHandler.getPlayerData((EntityPlayer)player);
         if (data.isGhost && data.ghostViewing != null) {
            ;
         }
      }

      if (this.mc.theWorld != null) {
         int radius = 12;

         for(int x = -radius; x < radius; ++x) {
            for(int y = -radius; y < radius; ++y) {
               for(int z = -radius; z < radius; ++z) {
                  int px = (int)(player.posX + (double)x);
                  int py = (int)(player.posY + (double)y);
                  int pz = (int)(player.posZ + (double)z);
                  int blockID = this.mc.theWorld.getBlockId(px, py, pz);
                  if (blockID == BlockManager.benchMenu.blockID) {
                     int size = 40;
                     CCRenderHelper.renderImageFacingPlayer(new ResourceLocation("countercraft:textures/gui/menu.png"), (double)px + 0.5D, (double)py + 1.5D, (double)pz + 0.5D, evt.partialTicks, size, size, "0xffffff");
                  }
               }
            }
         }
      }

   }

   @ForgeSubscribe
   public void onRenderPlayerPre(Pre event) {
      if (Minecraft.getMinecraft() != null && Minecraft.getMinecraft().thePlayer != null) {
         EntityPlayer mcPlayer = Minecraft.getMinecraft().thePlayer;
         RendererLivingEntity.NAME_TAG_RANGE = 0.0F;//64
         RendererLivingEntity.NAME_TAG_RANGE_SNEAK = 0.0F;//32
         if (event.entity != null && event.entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer)event.entity;
            IGame clientGame = GameManager.instance().getPlayerGame((EntityPlayer)mcPlayer);
            PlayerData data = PlayerDataHandler.getPlayerData(player);
            if (clientGame != null && clientGame.getPlayerEventHandler().getPlayers().contains(player.username) && !clientGame.getClientSide().allowNameTagRendering(player.username)) {
               RendererLivingEntity.NAME_TAG_RANGE = 0.0F;
               RendererLivingEntity.NAME_TAG_RANGE_SNEAK = 0.0F;
            }

            if (RendererLivingEntity.NAME_TAG_RANGE > 0.0F && player.getDistanceToEntity(mcPlayer) <= 24.0F && data != null && data.featuredCoin != -1 && !data.isGhost) {
               ItemCC item = (ItemCC)Item.itemsList[data.featuredCoin];
               String coinTexture = "countercraft:textures/items/" + item.getCCTextureName() + ".png";
               double d0 = player.lastTickPosX + (player.posX - player.lastTickPosX) * (double)event.partialRenderTick;
               double d1 = player.lastTickPosY + (player.posY - player.lastTickPosY) * (double)event.partialRenderTick;
               double d2 = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double)event.partialRenderTick;
               double d3 = 2.48D;
               if (player.isSneaking()) {
                  d3 = 2.15D;
               }

               CCRenderHelper.renderImageFacingPlayer(new ResourceLocation(coinTexture), d0, d1 + d3, d2, event.partialRenderTick, 12, 12, "0xffffff");
            }

            if (RendererLivingEntity.NAME_TAG_RANGE > 0.0F && player.getDistanceToEntity(mcPlayer) <= RendererLivingEntity.NAME_TAG_RANGE && data.hasBomb && !data.isGhost) {
               String bombRes = "countercraft:textures/items/bomb.png";
               double d0 = player.lastTickPosX + (player.posX - player.lastTickPosX) * (double)event.partialRenderTick;
               double d1 = player.lastTickPosY + (player.posY - player.lastTickPosY) * (double)event.partialRenderTick;
               double d2 = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double)event.partialRenderTick;
               double d3 = 2.8D;
               if (player.isSneaking()) {
                  d3 = 2.7D;
               }

               CCRenderHelper.renderImageFacingPlayer(new ResourceLocation(bombRes), d0, d1 + d3, d2, event.partialRenderTick, 16, 16, "0xffffff");
            }
         }
      }

   }
}
