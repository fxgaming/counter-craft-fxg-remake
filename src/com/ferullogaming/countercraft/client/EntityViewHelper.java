package com.ferullogaming.countercraft.client;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityViewHelper {
   public static boolean canEntityBeSeen(Entity entityViewing, Entity par1Entity) {
      return rayTraceBlocks(entityViewing.worldObj, Vec3.createVectorHelper(entityViewing.posX, entityViewing.posY + (double)entityViewing.getEyeHeight(), entityViewing.posZ), Vec3.createVectorHelper(par1Entity.posX, par1Entity.posY + (double)par1Entity.getEyeHeight(), par1Entity.posZ), false, false, false) == null || rayTraceBlocks(entityViewing.worldObj, Vec3.createVectorHelper(entityViewing.posX, entityViewing.posY + (double)entityViewing.getEyeHeight(), entityViewing.posZ), Vec3.createVectorHelper(par1Entity.posX + 0.5D, par1Entity.posY + (double)par1Entity.getEyeHeight(), par1Entity.posZ + 0.5D), false, false, false) == null || rayTraceBlocks(entityViewing.worldObj, Vec3.createVectorHelper(entityViewing.posX, entityViewing.posY + (double)entityViewing.getEyeHeight(), entityViewing.posZ), Vec3.createVectorHelper(par1Entity.posX - 0.5D, par1Entity.posY + (double)par1Entity.getEyeHeight(), par1Entity.posZ - 0.5D), false, false, false) == null || rayTraceBlocks(entityViewing.worldObj, Vec3.createVectorHelper(entityViewing.posX, entityViewing.posY + (double)entityViewing.getEyeHeight(), entityViewing.posZ), Vec3.createVectorHelper(par1Entity.posX - 0.5D, par1Entity.posY + (double)par1Entity.getEyeHeight(), par1Entity.posZ + 0.5D), false, false, false) == null || rayTraceBlocks(entityViewing.worldObj, Vec3.createVectorHelper(entityViewing.posX, entityViewing.posY + (double)entityViewing.getEyeHeight(), entityViewing.posZ), Vec3.createVectorHelper(par1Entity.posX + 0.5D, par1Entity.posY + (double)par1Entity.getEyeHeight(), par1Entity.posZ - 0.5D), false, false, false) == null || rayTraceBlocks(entityViewing.worldObj, Vec3.createVectorHelper(entityViewing.posX, entityViewing.posY + (double)entityViewing.getEyeHeight(), entityViewing.posZ), Vec3.createVectorHelper(par1Entity.posX, par1Entity.posY, par1Entity.posZ), false, false, false) == null || rayTraceBlocks(entityViewing.worldObj, Vec3.createVectorHelper(entityViewing.posX, entityViewing.posY + (double)entityViewing.getEyeHeight(), entityViewing.posZ), Vec3.createVectorHelper(par1Entity.posX, par1Entity.posY, par1Entity.posZ), false, false, false) == null || rayTraceBlocks(entityViewing.worldObj, Vec3.createVectorHelper(entityViewing.posX, entityViewing.posY + (double)entityViewing.getEyeHeight(), entityViewing.posZ), Vec3.createVectorHelper(par1Entity.posX, par1Entity.posY, par1Entity.posZ), false, false, false) == null || rayTraceBlocks(entityViewing.worldObj, Vec3.createVectorHelper(entityViewing.posX, entityViewing.posY + (double)entityViewing.getEyeHeight(), entityViewing.posZ), Vec3.createVectorHelper(par1Entity.posX, par1Entity.posY, par1Entity.posZ), false, false, false) == null;
   }

   public static MovingObjectPosition rayTraceBlocks(World givenWorld, Vec3 p_147447_1_, Vec3 p_147447_2_, boolean p_147447_3_, boolean p_147447_4_, boolean p_147447_5_) {
      if (!Double.isNaN(p_147447_1_.xCoord) && !Double.isNaN(p_147447_1_.yCoord) && !Double.isNaN(p_147447_1_.zCoord)) {
         if (!Double.isNaN(p_147447_2_.xCoord) && !Double.isNaN(p_147447_2_.yCoord) && !Double.isNaN(p_147447_2_.zCoord)) {
            int i = MathHelper.floor_double(p_147447_2_.xCoord);
            int j = MathHelper.floor_double(p_147447_2_.yCoord);
            int k = MathHelper.floor_double(p_147447_2_.zCoord);
            int l = MathHelper.floor_double(p_147447_1_.xCoord);
            int i1 = MathHelper.floor_double(p_147447_1_.yCoord);
            int j1 = MathHelper.floor_double(p_147447_1_.zCoord);
            int k1 = givenWorld.getBlockId(l, i1, j1);
            int l1 = givenWorld.getBlockMetadata(l, i1, j1);
            Block block = Block.blocksList[k1];
            if (block != null && !block.renderAsNormalBlock()) {
               return null;
            } else {
               MovingObjectPosition movingobjectposition2;
               if (block != null && (!p_147447_4_ || block.getCollisionBoundingBoxFromPool(givenWorld, l, i1, j1) != null) && block.canCollideCheck(k1, p_147447_3_)) {
                  movingobjectposition2 = block.collisionRayTrace(givenWorld, l, i1, j1, p_147447_1_, p_147447_2_);
                  if (movingobjectposition2 != null) {
                     return movingobjectposition2;
                  }
               }

               movingobjectposition2 = null;
               k1 = 200;

               while(k1-- >= 0) {
                  if (Double.isNaN(p_147447_1_.xCoord) || Double.isNaN(p_147447_1_.yCoord) || Double.isNaN(p_147447_1_.zCoord)) {
                     return null;
                  }

                  if (l == i && i1 == j && j1 == k) {
                     return p_147447_5_ ? movingobjectposition2 : null;
                  }

                  boolean flag6 = true;
                  boolean flag3 = true;
                  boolean flag4 = true;
                  double d0 = 999.0D;
                  double d1 = 999.0D;
                  double d2 = 999.0D;
                  if (i > l) {
                     d0 = (double)l + 1.0D;
                  } else if (i < l) {
                     d0 = (double)l + 0.0D;
                  } else {
                     flag6 = false;
                  }

                  if (j > i1) {
                     d1 = (double)i1 + 1.0D;
                  } else if (j < i1) {
                     d1 = (double)i1 + 0.0D;
                  } else {
                     flag3 = false;
                  }

                  if (k > j1) {
                     d2 = (double)j1 + 1.0D;
                  } else if (k < j1) {
                     d2 = (double)j1 + 0.0D;
                  } else {
                     flag4 = false;
                  }

                  double d3 = 999.0D;
                  double d4 = 999.0D;
                  double d5 = 999.0D;
                  double d6 = p_147447_2_.xCoord - p_147447_1_.xCoord;
                  double d7 = p_147447_2_.yCoord - p_147447_1_.yCoord;
                  double d8 = p_147447_2_.zCoord - p_147447_1_.zCoord;
                  if (flag6) {
                     d3 = (d0 - p_147447_1_.xCoord) / d6;
                  }

                  if (flag3) {
                     d4 = (d1 - p_147447_1_.yCoord) / d7;
                  }

                  if (flag4) {
                     d5 = (d2 - p_147447_1_.zCoord) / d8;
                  }

                  boolean flag5 = false;
                  byte b0;
                  if (d3 < d4 && d3 < d5) {
                     if (i > l) {
                        b0 = 4;
                     } else {
                        b0 = 5;
                     }

                     p_147447_1_.xCoord = d0;
                     p_147447_1_.yCoord += d7 * d3;
                     p_147447_1_.zCoord += d8 * d3;
                  } else if (d4 < d5) {
                     if (j > i1) {
                        b0 = 0;
                     } else {
                        b0 = 1;
                     }

                     p_147447_1_.xCoord += d6 * d4;
                     p_147447_1_.yCoord = d1;
                     p_147447_1_.zCoord += d8 * d4;
                  } else {
                     if (k > j1) {
                        b0 = 2;
                     } else {
                        b0 = 3;
                     }

                     p_147447_1_.xCoord += d6 * d5;
                     p_147447_1_.yCoord += d7 * d5;
                     p_147447_1_.zCoord = d2;
                  }

                  Vec3 vec32 = Vec3.createVectorHelper(p_147447_1_.xCoord, p_147447_1_.yCoord, p_147447_1_.zCoord);
                  l = (int)(vec32.xCoord = (double)MathHelper.floor_double(p_147447_1_.xCoord));
                  if (b0 == 5) {
                     --l;
                     ++vec32.xCoord;
                  }

                  i1 = (int)(vec32.yCoord = (double)MathHelper.floor_double(p_147447_1_.yCoord));
                  if (b0 == 1) {
                     --i1;
                     ++vec32.yCoord;
                  }

                  j1 = (int)(vec32.zCoord = (double)MathHelper.floor_double(p_147447_1_.zCoord));
                  if (b0 == 3) {
                     --j1;
                     ++vec32.zCoord;
                  }

                  int i2 = givenWorld.getBlockId(l, i1, j1);
                  givenWorld.getBlockMetadata(l, i1, j1);
                  Block block1 = Block.blocksList[i2];
                  if (block1 != null && !block1.renderAsNormalBlock()) {
                     return null;
                  }

                  if (block1 != null && (!p_147447_4_ || block1.getCollisionBoundingBoxFromPool(givenWorld, l, i1, j1) != null)) {
                     if (block1.canCollideCheck(l1, p_147447_3_)) {
                        MovingObjectPosition movingobjectposition1 = block1.collisionRayTrace(givenWorld, l, i1, j1, p_147447_1_, p_147447_2_);
                        if (movingobjectposition1 != null) {
                           return movingobjectposition1;
                        }
                     } else {
                        movingobjectposition2 = new MovingObjectPosition(l, i1, j1, b0, p_147447_1_);
                     }
                  }
               }

               return p_147447_5_ ? movingobjectposition2 : null;
            }
         } else {
            return null;
         }
      } else {
         return null;
      }
   }
}
