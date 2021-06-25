package com.ferullogaming.countercraft;

import com.ferullogaming.countercraft.client.cloud.PlayerDataCloud;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IImageBuffer;
import net.minecraft.client.renderer.ImageBufferDownload;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureObject;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraft.world.World;

@SideOnly(Side.CLIENT)
public class AbstractClientPlayerCC extends EntityPlayer {
   public static final ResourceLocation locationStevePng = new ResourceLocation("textures/entity/steve.png");
   private ThreadDownloadImageData downloadImageSkin;
   private ThreadDownloadImageData downloadImageCape;
   private ResourceLocation locationSkin;
   private ResourceLocation locationCape;

   public AbstractClientPlayerCC(World par1World, String par2Str) {
      super(par1World, par2Str);
      this.setupCustomSkin();
   }

   public AbstractClientPlayerCC(World par1World, String givenPlayerUsername, PlayerDataCloud givenCloudData) {
      super(par1World, givenPlayerUsername);
      this.setupCustomSkinWithUUID(givenCloudData.playerUUID);
   }

   public static ThreadDownloadImageData getDownloadImageSkin(ResourceLocation par0ResourceLocation, String par1Str) {
      return getDownloadImage(par0ResourceLocation, getSkinUrl(par1Str), locationStevePng, new ImageBufferDownload());
   }

   public static ThreadDownloadImageData getDownloadImageCape(ResourceLocation par0ResourceLocation, String par1Str) {
      return getDownloadImage(par0ResourceLocation, getCapeUrl(par1Str), (ResourceLocation)null, (IImageBuffer)null);
   }

   private static ThreadDownloadImageData getDownloadImage(ResourceLocation par0ResourceLocation, String par1Str, ResourceLocation par2ResourceLocation, IImageBuffer par3IImageBuffer) {
      TextureManager texturemanager = Minecraft.getMinecraft().getTextureManager();
      Object object = texturemanager.getTexture(par0ResourceLocation);
      if (object == null) {
         object = new ThreadDownloadImageData(par1Str, par2ResourceLocation, par3IImageBuffer);
         texturemanager.loadTexture(par0ResourceLocation, (TextureObject)object);
      }

      return (ThreadDownloadImageData)object;
   }

   public static String getSkinUrl(String par0Str) {
      return String.format("https://crafatar.com/skins/%s.png", StringUtils.stripControlCodes(par0Str));
   }

   public static String getCapeUrl(String par0Str) {
      return String.format("https://crafatar.com/cloaks/%s.png", StringUtils.stripControlCodes(par0Str));
   }

   public static ResourceLocation getLocationSkin(String par0Str) {
      return new ResourceLocation("skins/" + StringUtils.stripControlCodes(par0Str));
   }

   public static ResourceLocation getLocationCape(String par0Str) {
      return new ResourceLocation("cloaks/" + StringUtils.stripControlCodes(par0Str));
   }

   public static ResourceLocation getLocationSkull(String par0Str) {
      return new ResourceLocation("skull/" + StringUtils.stripControlCodes(par0Str));
   }

   protected void setupCustomSkin() {
      if (super.username != null && !super.username.isEmpty()) {
         this.locationSkin = getLocationSkin(super.username);
         this.locationCape = getLocationCape(super.username);
         this.downloadImageSkin = getDownloadImageSkin(this.locationSkin, super.username);
         this.downloadImageCape = getDownloadImageCape(this.locationCape, super.username);
      }

   }

   protected void setupCustomSkinWithUUID(String givenUUID) {
      if (givenUUID != null && !givenUUID.isEmpty()) {
         this.locationSkin = getLocationSkin(givenUUID);
         this.locationCape = getLocationCape(givenUUID);
         this.downloadImageSkin = getDownloadImageSkin(this.locationSkin, givenUUID);
         this.downloadImageCape = getDownloadImageCape(this.locationCape, givenUUID);
      }

   }

   public ThreadDownloadImageData getTextureSkin() {
      return this.downloadImageSkin;
   }

   public ThreadDownloadImageData getTextureCape() {
      return this.downloadImageCape;
   }

   public ResourceLocation getLocationSkin() {
      return this.locationSkin == null ? locationStevePng : this.locationSkin;
   }

   public ResourceLocation getLocationCape() {
      return this.locationCape;
   }

   public void sendChatToPlayer(ChatMessageComponent chatmessagecomponent) {
   }

   public boolean canCommandSenderUseCommand(int i, String s) {
      return false;
   }

   public ChunkCoordinates getPlayerCoordinates() {
      return null;
   }
}
