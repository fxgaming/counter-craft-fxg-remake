package com.ferullogaming.countercraft.client.minimap;

import com.ferullogaming.countercraft.client.minimap.region.MwChunk;
import com.ferullogaming.countercraft.client.minimap.tasks.SaveChunkTask;
import com.ferullogaming.countercraft.client.minimap.tasks.UpdateSurfaceChunksTask;
import java.util.Iterator;
import java.util.Map.Entry;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;

public class ChunkManager {
   private static final int VISIBLE_FLAG = 1;
   private static final int VIEWED_FLAG = 2;
   public Minimap mw;
   private boolean closed = false;
   private CircularHashMap chunkMap = new CircularHashMap();

   public ChunkManager(Minimap mw) {
      this.mw = mw;
   }

   public static MwChunk copyToMwChunk(Chunk chunk) {
      byte[][] msbArray = new byte[16][];
      byte[][] lsbArray = new byte[16][];
      byte[][] metaArray = new byte[16][];
      byte[][] lightingArray = new byte[16][];
      ExtendedBlockStorage[] storageArrays = chunk.getBlockStorageArray();
      if (storageArrays != null) {
         ExtendedBlockStorage[] arr$ = storageArrays;
         int len$ = storageArrays.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            ExtendedBlockStorage storage = arr$[i$];
            if (storage != null) {
               int y = storage.getYLocation() >> 4 & 15;
               lsbArray[y] = storage.getBlockLSBArray();
               msbArray[y] = storage.getBlockMSBArray() != null ? storage.getBlockMSBArray().data : null;
               metaArray[y] = storage.getMetadataArray() != null ? storage.getMetadataArray().data : null;
               lightingArray[y] = storage.getBlocklightArray() != null ? storage.getBlocklightArray().data : null;
            }
         }
      }

      return new MwChunk(chunk.xPosition, chunk.zPosition, chunk.worldObj.provider.dimensionId, msbArray, lsbArray, metaArray, lightingArray, chunk.getBiomeArray());
   }

   public synchronized void close() {
      this.closed = true;
      this.saveChunks();
      this.chunkMap.clear();
   }

   public synchronized void addChunk(Chunk chunk) {
      if (!this.closed && chunk != null) {
         this.chunkMap.put(chunk, Integer.valueOf(0));
      }

   }

   public synchronized void removeChunk(Chunk chunk) {
      if (!this.closed && chunk != null) {
         int flags = ((Integer)this.chunkMap.get(chunk)).intValue();
         if ((flags & 2) != 0) {
            this.addSaveChunkTask(chunk);
         }

         this.chunkMap.remove(chunk);
      }

   }

   public synchronized void saveChunks() {
      Iterator i$ = this.chunkMap.entrySet().iterator();

      while(i$.hasNext()) {
         Entry entry = (Entry)i$.next();
         int flags = ((Integer)entry.getValue()).intValue();
         if ((flags & 2) != 0) {
            this.addSaveChunkTask((Chunk)entry.getKey());
         }
      }

   }

   public void updateUndergroundChunks() {
      int chunkArrayX = (this.mw.playerXInt >> 4) - 1;
      int chunkArrayZ = (this.mw.playerZInt >> 4) - 1;
      MwChunk[] chunkArray = new MwChunk[9];

      for(int z = 0; z < 3; ++z) {
         for(int x = 0; x < 3; ++x) {
            Chunk chunk = this.mw.mc.theWorld.getChunkFromChunkCoords(chunkArrayX + x, chunkArrayZ + z);
            if (!chunk.isEmpty()) {
               chunkArray[z * 3 + x] = copyToMwChunk(chunk);
            }
         }
      }

   }

   public void updateSurfaceChunks() {
      int chunksToUpdate = Math.min(this.chunkMap.size(), this.mw.chunksPerTick);
      MwChunk[] chunkArray = new MwChunk[chunksToUpdate];

      for(int i = 0; i < chunksToUpdate; ++i) {
         Entry entry = this.chunkMap.getNextEntry();
         if (entry != null) {
            Chunk chunk = (Chunk)entry.getKey();
            int flags = ((Integer)entry.getValue()).intValue();
            if (MinimapUtils.distToChunkSq(this.mw.playerXInt, this.mw.playerZInt, chunk) <= this.mw.maxChunkSaveDistSq) {
               flags |= 3;
            } else {
               flags &= -2;
            }

            entry.setValue(flags);
            if ((flags & 1) != 0) {
               chunkArray[i] = copyToMwChunk(chunk);
            } else {
               chunkArray[i] = null;
            }
         }
      }

      this.mw.executor.addTask(new UpdateSurfaceChunksTask(this.mw, chunkArray));
   }

   public void onTick() {
      if (!this.closed) {
         if ((this.mw.tickCounter & 15) == 0) {
            this.updateUndergroundChunks();
         } else {
            this.updateSurfaceChunks();
         }
      }

   }

   public void forceChunks(MwChunk[] chunkArray) {
      this.mw.executor.addTask(new UpdateSurfaceChunksTask(this.mw, chunkArray));
   }

   private void addSaveChunkTask(Chunk chunk) {
      if ((this.mw.multiplayer && this.mw.regionFileOutputEnabledMP || !this.mw.multiplayer && this.mw.regionFileOutputEnabledSP) && !chunk.isEmpty()) {
         this.mw.executor.addTask(new SaveChunkTask(copyToMwChunk(chunk), this.mw.regionManager));
      }

   }
}
