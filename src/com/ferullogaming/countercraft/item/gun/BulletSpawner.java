package com.ferullogaming.countercraft.item.gun;

import com.ferullogaming.countercraft.CounterCraft;
import com.ferullogaming.countercraft.block.BlockManager;
import com.ferullogaming.countercraft.entity.EntityPlayerHead;
import com.ferullogaming.countercraft.network.CCPacketBulletCollision;
import com.ferullogaming.countercraft.player.PlayerData;
import com.ferullogaming.countercraft.player.PlayerDataHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class BulletSpawner {
   private static final WallBangable[] wallBangableBlocks;
   public MovingObjectPosition objectMouseOver2;
   public int tempDamageReduction = 0;
   public int damageReduction = 0;
   private Minecraft mc = Minecraft.getMinecraft();
   private Entity pointedEntity;
   private float shootingYaw = -1.0F;
   private float shootingPitch = -1.0F;
   private ArrayList blocksHit = new ArrayList();

   public void spawnBullet(EntityPlayer par1, ItemGun par2, boolean isSuppressed) {
      if (par1 != null && par1.worldObj != null && par1.worldObj.isRemote) {
         PlayerData playerdata = PlayerDataHandler.getPlayerData(par1);
         double var1 = (double)par2.bulletDistance;
         this.tempDamageReduction = 0;
         this.damageReduction = 0;
         this.getMouseOver(0.0F, var1);
         MovingObjectPosition object = this.objectMouseOver2;
         this.damageReduction += isSuppressed ? 1 : 0;
         if (object != null) {
            if (object.entityHit != null) {
               Entity entity = object.entityHit;
               boolean headShot = false;
               if (entity instanceof EntityPlayerHead) {
                  this.mc.sndManager.playSoundFX("random.break", 1.5F, 1.5F);
                  entity = ((EntityPlayerHead)entity).player;
                  headShot = true;
               }

               if (entity instanceof EntityLivingBase && entity != par1) {
                  EntityLivingBase living = (EntityLivingBase)entity;
                  if (!living.isDead && living.getHealth() > 0.0F) {
                     if (this.blocksHit.size() > 0) {
                        Iterator i$ = this.blocksHit.iterator();

                        while(i$.hasNext()) {
                           BlockWallBangableHit block = (BlockWallBangableHit)i$.next();
                           if (par1.getDistance(block.posX, block.posY, block.posZ) < par1.getDistance(living.posX, living.posY, living.posZ)) {
                              this.damageReduction = (int)((double)this.damageReduction + this.getWallbangAttibutes(block.blockID).reduction);
                           }
                        }
                     }

                     new CCPacketBulletCollision();
                     PacketDispatcher.sendPacketToServer(CCPacketBulletCollision.buildPacket(true, living, headShot, this.damageReduction));
                  }
               }
            } else if (object.typeOfHit == EnumMovingObjectType.TILE) {
               World world = par1.worldObj;
               int var12 = world.getBlockId(object.blockX, object.blockY, object.blockZ);
               new CCPacketBulletCollision();
               PacketDispatcher.sendPacketToServer(CCPacketBulletCollision.buildPacket(false, var12, (double)object.blockX, (double)object.blockY, (double)object.blockZ, object.sideHit, object.hitVec.xCoord, object.hitVec.yCoord, object.hitVec.zCoord));
            }
         }
      }

      this.objectMouseOver2 = null;
      this.pointedEntity = null;
   }

   private void getMouseOver(float par1, double par2) {
      this.shootingYaw = -1.0F;
      this.shootingPitch = -1.0F;
      this.objectMouseOver2 = this.rayTrace(this.mc.thePlayer, par2, par1);
      double hitDistance = par2;
      Vec3 vec3 = this.mc.thePlayer.getPosition(par1);
      if (this.objectMouseOver2 != null) {
         hitDistance = this.objectMouseOver2.hitVec.distanceTo(vec3);
      }

      Vec3 vec31 = this.getLook(false, this.mc.thePlayer);
      Vec3 vec32 = vec3.addVector(vec31.xCoord * par2, vec31.yCoord * par2, vec31.zCoord * par2);
      this.pointedEntity = null;
      float f1 = 1.0F;
      List list = this.mc.theWorld.getEntitiesWithinAABBExcludingEntity(this.mc.thePlayer, this.mc.thePlayer.boundingBox.addCoord(vec31.xCoord * par2, vec31.yCoord * par2, vec31.zCoord * par2).expand((double)f1, (double)f1, (double)f1));
      double d2 = hitDistance;
      double d21 = hitDistance;

      int i;
      Entity entity;
      float f2;
      AxisAlignedBB axisalignedbb;
      MovingObjectPosition movingobjectposition;
      for(i = 0; i < list.size(); ++i) {
         entity = (Entity)list.get(i);
         if (entity.canBeCollidedWith() && !(entity instanceof EntityPlayerHead) && (!(entity instanceof EntityLiving) || ((EntityLiving)entity).deathTime == 0)) {
            f2 = entity.getCollisionBorderSize();
            axisalignedbb = entity.boundingBox.expand((double)f2, (double)f2, (double)f2);
            movingobjectposition = axisalignedbb.calculateIntercept(vec3, vec32);
            if (entity instanceof EntityPlayer) {
               PlayerData data = PlayerDataHandler.getPlayerData((EntityPlayer)entity);
               if (data.isGhost) {
                  continue;
               }
            }

            if (axisalignedbb.isVecInside(vec3)) {
               if (0.0D < d2 || d2 == 0.0D) {
                  this.pointedEntity = entity;
                  d2 = 0.0D;
               }
            } else if (movingobjectposition != null) {
               double d3 = vec3.distanceTo(movingobjectposition.hitVec);
               if (d3 < d2 || d2 == 0.0D) {
                  if (entity == this.mc.thePlayer.ridingEntity && !entity.canRiderInteract()) {
                     if (d2 == 0.0D) {
                        this.pointedEntity = entity;
                     }
                  } else {
                     this.pointedEntity = entity;
                     d2 = d3;
                  }
               }
            }
         }
      }

      for(i = 0; i < list.size(); ++i) {
         entity = (Entity)list.get(i);
         if (entity.canBeCollidedWith()) {
            f2 = entity.getCollisionBorderSize();
            axisalignedbb = entity.boundingBox.expand((double)f2, (double)f2, (double)f2);
            movingobjectposition = axisalignedbb.calculateIntercept(vec3, vec32);
            if (entity instanceof EntityPlayerHead) {
               EntityPlayerHead head = (EntityPlayerHead)entity;
               if (head.player != this.mc.thePlayer) {
                  PlayerData data = PlayerDataHandler.getPlayerData(head.player);
                  if (!data.isGhost) {
                     if (axisalignedbb.isVecInside(vec3)) {
                        if (0.0D < d21 || d21 == 0.0D) {
                           this.pointedEntity = entity;
                           d21 = 0.0D;
                           break;
                        }
                     } else if (movingobjectposition != null) {
                        double d3 = vec3.distanceTo(movingobjectposition.hitVec);
                        if (d3 < d21 || d21 == 0.0D) {
                           if (entity != this.mc.thePlayer.ridingEntity || entity.canRiderInteract()) {
                              this.pointedEntity = entity;
                              d21 = d3;
                              break;
                           }

                           if (d21 == 0.0D) {
                              this.pointedEntity = entity;
                              break;
                           }
                        }
                     }
                  }
               }
            }
         }
      }

      if (this.pointedEntity != null && (d2 < par2 || this.objectMouseOver2 == null || d21 < par2)) {
         this.objectMouseOver2 = new MovingObjectPosition(this.pointedEntity);
         if (this.mc.thePlayer.canEntityBeSeen(this.pointedEntity)) {
            this.damageReduction = 0;
         }
      }

   }

   @SideOnly(Side.CLIENT)
   private MovingObjectPosition rayTrace(EntityLivingBase living, double par1, float par3) {
      Vec3 vec3 = living.getPosition(par3);
      Vec3 vec31 = this.getLook(true, living);
      Vec3 vec32 = vec3.addVector(vec31.xCoord * par1, vec31.yCoord * par1, vec31.zCoord * par1);
      return this.clip(living.worldObj, vec3, vec32);
   }

   private Vec3 getLook(boolean isrand, EntityLivingBase living) {
      if (this.shootingYaw == -1.0F && this.shootingPitch == -1.0F) {
         this.shootingYaw = living.rotationYaw;
         this.shootingPitch = living.rotationPitch;
      }

      float spread;
      float randSpread;
      if (isrand) {
         PlayerData data = PlayerDataHandler.getClientPlayerData();
         Random rand = new Random();
         spread = data.spread;
         if (rand.nextInt(5) != 0) {
            randSpread = (float)rand.nextInt((int)(spread * 100.0F)) / 100.0F;
            float preyaw = rand.nextBoolean() ? randSpread : -randSpread;
            this.shootingYaw += preyaw * 1.5F;
         }

         this.shootingPitch -= (float)rand.nextInt((int)(spread * 1.5F * 100.0F)) / 100.0F;
      }

      float f1 = MathHelper.cos(-this.shootingYaw * 0.017453292F - 3.1415927F);
      float f2 = MathHelper.sin(-this.shootingYaw * 0.017453292F - 3.1415927F);
      spread = -MathHelper.cos(-this.shootingPitch * 0.017453292F);
      randSpread = MathHelper.sin(-this.shootingPitch * 0.017453292F);
      return Vec3.createVectorHelper((double)(f2 * spread), (double)randSpread, (double)(f1 * spread));
   }

   private MovingObjectPosition clip(World par1, Vec3 par1Vec3, Vec3 par2Vec3) {
      if (!Double.isNaN(par1Vec3.xCoord) && !Double.isNaN(par1Vec3.yCoord) && !Double.isNaN(par1Vec3.zCoord) && !Double.isNaN(par2Vec3.xCoord) && !Double.isNaN(par2Vec3.yCoord) && !Double.isNaN(par2Vec3.zCoord)) {
         int i = MathHelper.floor_double(par2Vec3.xCoord);
         int j = MathHelper.floor_double(par2Vec3.yCoord);
         int k = MathHelper.floor_double(par2Vec3.zCoord);
         int l = MathHelper.floor_double(par1Vec3.xCoord);
         int i1 = MathHelper.floor_double(par1Vec3.yCoord);
         int j1 = MathHelper.floor_double(par1Vec3.zCoord);
         int k1 = par1.getBlockId(l, i1, j1);
         int l1 = par1.getBlockMetadata(l, i1, j1);
         Block block = Block.blocksList[k1];
         boolean flag2;
         if (block != null && block.getCollisionBoundingBoxFromPool(par1, l, i1, j1) != null && k1 > 0 && block.canCollideCheck(l1, false)) {
            flag2 = true;
            if (this.isWallbangable(k1)) {
               WallBangable wallbang = this.getWallbangAttibutes(k1);
               this.tempDamageReduction = (int)((double)this.tempDamageReduction + wallbang.reduction);
               this.blocksHit.add(new BlockWallBangableHit((double)l, (double)i1, (double)j1, block.blockID));
               if (this.tempDamageReduction <= 100) {
                  if (k1 != 0) {
                     CounterCraft.getClientEvents().onBulletCollisionBlock(par1, par1Vec3.xCoord, par1Vec3.yCoord, par1Vec3.zCoord, k1, 0, par1Vec3, l1);
                  }

                  flag2 = false;
               }
            }

            if (flag2) {
               MovingObjectPosition movingobjectposition = block.collisionRayTrace(par1, l, i1, j1, par1Vec3, par2Vec3);
               if (movingobjectposition != null) {
                  return movingobjectposition;
               }
            }
         }

         k1 = 200;

         while(k1-- >= 0) {
            if (Double.isNaN(par1Vec3.xCoord) || Double.isNaN(par1Vec3.yCoord) || Double.isNaN(par1Vec3.zCoord)) {
               return null;
            }

            if (l == i && i1 == j && j1 == k) {
               return null;
            }

            flag2 = true;
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
               flag2 = false;
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
            double d6 = par2Vec3.xCoord - par1Vec3.xCoord;
            double d7 = par2Vec3.yCoord - par1Vec3.yCoord;
            double d8 = par2Vec3.zCoord - par1Vec3.zCoord;
            if (flag2) {
               d3 = (d0 - par1Vec3.xCoord) / d6;
            }

            if (flag3) {
               d4 = (d1 - par1Vec3.yCoord) / d7;
            }

            if (flag4) {
               d5 = (d2 - par1Vec3.zCoord) / d8;
            }

            boolean flag5 = false;
            byte b0;
            if (d3 < d4 && d3 < d5) {
               if (i > l) {
                  b0 = 4;
               } else {
                  b0 = 5;
               }

               par1Vec3.xCoord = d0;
               par1Vec3.yCoord += d7 * d3;
               par1Vec3.zCoord += d8 * d3;
            } else if (d4 < d5) {
               if (j > i1) {
                  b0 = 0;
               } else {
                  b0 = 1;
               }

               par1Vec3.xCoord += d6 * d4;
               par1Vec3.yCoord = d1;
               par1Vec3.zCoord += d8 * d4;
            } else {
               if (k > j1) {
                  b0 = 2;
               } else {
                  b0 = 3;
               }

               par1Vec3.xCoord += d6 * d5;
               par1Vec3.yCoord += d7 * d5;
               par1Vec3.zCoord = d2;
            }

            Vec3 vec32 = par1.getWorldVec3Pool().getVecFromPool(par1Vec3.xCoord, par1Vec3.yCoord, par1Vec3.zCoord);
            l = (int)(vec32.xCoord = (double)MathHelper.floor_double(par1Vec3.xCoord));
            if (b0 == 5) {
               --l;
               ++vec32.xCoord;
            }

            i1 = (int)(vec32.yCoord = (double)MathHelper.floor_double(par1Vec3.yCoord));
            if (b0 == 1) {
               --i1;
               ++vec32.yCoord;
            }

            j1 = (int)(vec32.zCoord = (double)MathHelper.floor_double(par1Vec3.zCoord));
            if (b0 == 3) {
               --j1;
               ++vec32.zCoord;
            }

            int i2 = par1.getBlockId(l, i1, j1);
            int j2 = par1.getBlockMetadata(l, i1, j1);
            Block block1 = Block.blocksList[i2];
            if (block1 != null && block1.getCollisionBoundingBoxFromPool(par1, l, i1, j1) != null && i2 > 0 && block1.canCollideCheck(j2, false)) {
               if (this.isWallbangable(i2)) {
                  WallBangable wallbang = this.getWallbangAttibutes(i2);
                  this.tempDamageReduction = (int)((double)this.tempDamageReduction + wallbang.reduction);
                  this.blocksHit.add(new BlockWallBangableHit((double)l, (double)i1, (double)j1, block1.blockID));
                  if (this.tempDamageReduction < 100) {
                     if (wallbang.particels && i2 != 0) {
                        CounterCraft.getClientEvents().onBulletCollisionBlock(par1, par1Vec3.xCoord, par1Vec3.yCoord, par1Vec3.zCoord, i2, 0, par1Vec3, j2);
                     }
                     continue;
                  }
               }

               MovingObjectPosition movingobjectposition1 = block1.collisionRayTrace(par1, l, i1, j1, par1Vec3, par2Vec3);
               if (movingobjectposition1 != null) {
                  return movingobjectposition1;
               }
            }
         }
      }

      return null;
   }

   private WallBangable getWallbangAttibutes(int par1) {
      WallBangable[] arr$ = wallBangableBlocks;
      int len$ = arr$.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         WallBangable var1 = arr$[i$];
         if (var1.blockID == par1) {
            return var1;
         }
      }

      return null;
   }

   private boolean isWallbangable(int par1) {
      WallBangable[] arr$ = wallBangableBlocks;
      int len$ = arr$.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         WallBangable var1 = arr$[i$];
         if (var1.blockID == par1) {
            return true;
         }
      }

      return false;
   }

   static {
      wallBangableBlocks = new WallBangable[]{new WallBangable(BlockManager.mapBarrier.blockID, 0.0D, false), new WallBangable(BlockManager.decalSiteA.blockID, 0.0D, false), new WallBangable(BlockManager.decalArrowDown.blockID, 0.0D, false), new WallBangable(BlockManager.decalArrowLeft.blockID, 0.0D, false), new WallBangable(BlockManager.decalArrowRight.blockID, 0.0D, false), new WallBangable(BlockManager.decalArrowUp.blockID, 0.0D, false), new WallBangable(BlockManager.decalSiteB.blockID, 0.0D, false), new WallBangable(Block.glass.blockID, 2.0D), new WallBangable(Block.thinGlass.blockID, 2.0D), new WallBangable(Block.planks.blockID, 25.0D), new WallBangable(Block.cloth.blockID, 50.0D), new WallBangable(Block.bookShelf.blockID, 25.0D), new WallBangable(Block.ice.blockID, 25.0D), new WallBangable(Block.hay.blockID, 2.0D), new WallBangable(Block.web.blockID, 2.0D), new WallBangable(Block.leaves.blockID, 2.0D), new WallBangable(Block.vine.blockID, 2.0D), new WallBangable(Block.tallGrass.blockID, 2.0D), new WallBangable(Block.signPost.blockID, 2.0D), new WallBangable(Block.signWall.blockID, 2.0D), new WallBangable(Block.fence.blockID, 2.0D), new WallBangable(Block.fenceIron.blockID, 2.0D), new WallBangable(Block.woodSingleSlab.blockID, 2.0D), new WallBangable(Block.fenceGate.blockID, 2.0D), new WallBangable(Block.stairsWoodBirch.blockID, 2.0D), new WallBangable(Block.stairsWoodJungle.blockID, 2.0D), new WallBangable(Block.stairsWoodOak.blockID, 2.0D), new WallBangable(Block.stairsWoodSpruce.blockID, 2.0D), new WallBangable(Block.torchWood.blockID, 2.0D), new WallBangable(Block.signPost.blockID, 2.0D), new WallBangable(Block.signWall.blockID, 2.0D), new WallBangable(Block.ladder.blockID, 2.0D), new WallBangable(Block.flowerPot.blockID, 2.0D)};
   }
}
