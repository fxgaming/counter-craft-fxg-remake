package com.ferullogaming.countercraft.client;

import com.f3rullo14.fds.FileHelper;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureObject;
import net.minecraft.util.ResourceLocation;

public class Crosshair {
   public String name;
   public String author;
   public boolean isStatic = false;
   public ResourceLocation top;
   public ResourceLocation bottom;
   public ResourceLocation left;
   public ResourceLocation right;
   public ResourceLocation middle;
   private File theFile;

   public Crosshair(File par1) {
      this.theFile = par1;
   }

   public Crosshair(String par1) {
      this.theFile = new File(par1);
   }

   public boolean loadFromFile() {
      try {
         File prop = new File(this.theFile.getAbsolutePath() + "/properties.txt");
         if (!prop.exists()) {
            return false;
         } else {
            ArrayList list = FileHelper.readFileToArray(prop);
            Iterator i$ = list.iterator();

            while(i$.hasNext()) {
               String var1 = (String)i$.next();
               String[] var2 = var1.split(": ");
               if (var2[0].equals("name")) {
                  this.name = var2[1];
               }

               if (var2[0].equals("author")) {
                  this.author = var2[1];
               }

               if (var2[0].equals("static")) {
                  this.isStatic = var2[1].equalsIgnoreCase("true");
               }
            }

            Random rand = new Random();
            this.top = new ResourceLocation(this.name + rand.nextInt(2000) + "/top.png");
            this.bottom = new ResourceLocation(this.name + rand.nextInt(2000) + "/bottom.png");
            this.left = new ResourceLocation(this.name + rand.nextInt(2000) + "/left.png");
            this.right = new ResourceLocation(this.name + rand.nextInt(2000) + "/right.png");
            this.middle = new ResourceLocation(this.name + rand.nextInt(2000) + "/middle.png");
            this.loadResource(this.theFile.getAbsolutePath() + "/top.png", this.top);
            this.loadResource(this.theFile.getAbsolutePath() + "/bottom.png", this.bottom);
            this.loadResource(this.theFile.getAbsolutePath() + "/left.png", this.left);
            this.loadResource(this.theFile.getAbsolutePath() + "/right.png", this.right);
            this.loadResource(this.theFile.getAbsolutePath() + "/middle.png", this.middle);
            return true;
         }
      } catch (Exception var6) {
         var6.printStackTrace();
         return false;
      }
   }

   private void loadResource(String par1, ResourceLocation par2) {
      try {
         TextureManager texturemanager = Minecraft.getMinecraft().getTextureManager();
         Object object = texturemanager.getTexture(par2);
         BufferedImage image = ImageIO.read(new File(par1));
         if (object == null) {
            Object object1 = new DynamicTexture(image);
            texturemanager.loadTexture(par2, (TextureObject)object1);
         }
      } catch (Exception var6) {
         var6.printStackTrace();
      }

   }
}
