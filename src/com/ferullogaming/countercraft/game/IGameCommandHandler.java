package com.ferullogaming.countercraft.game;

import java.util.ArrayList;
import net.minecraft.entity.player.EntityPlayer;

public abstract class IGameCommandHandler {
   private IGame game;

   public IGameCommandHandler(IGame par1) {
      this.game = par1;
   }

   public abstract void onEditCommandCalled(EntityPlayer var1, String[] var2);

   public abstract void getCommandInformation(ArrayList var1);

   public IGame getGame() {
      return this.game;
   }
}
