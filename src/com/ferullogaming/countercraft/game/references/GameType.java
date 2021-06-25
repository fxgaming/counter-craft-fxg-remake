package com.ferullogaming.countercraft.game.references;

import com.ferullogaming.countercraft.client.cloud.PlayerDataCloud;
import com.ferullogaming.countercraft.client.cloud.PlayerRank;
import java.util.ArrayList;
import java.util.Arrays;

public class GameType {
   public static GameType[] gameTypeList = new GameType[16];
   public static boolean ACTIVE_COMPETITIVE = true;
   public static GameType 
   		casual,
   		teamDeathMatch,
   		competitive,
   		dominating,
   		infected;
   public String description = "";
   public String icon;
   public boolean isMapsSelectable = true;
   public int rankRequired = -1;
   private int gameID;
   private String displayName;
   private String nameID;
   private MapType[] maps;
   private boolean inDevelopment;

   public GameType(int par1, String par2, String par3) {
      gameTypeList[par1] = this;
      this.gameID = par1;
      this.displayName = par2;
      this.nameID = par3;
      this.icon = par3.toLowerCase();
      this.inDevelopment = false;
   }

   public static ArrayList getList() {
      ArrayList list = new ArrayList();
      GameType[] arr$ = gameTypeList;
      int len$ = arr$.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         GameType type = arr$[i$];
         if (type != null) {
            list.add(type);
         }
      }

      return list;
   }

   public final GameType setDescription(String par1) {
      this.description = par1;
      return this;
   }

   public final GameType setIcon(String par1) {
      this.icon = par1;
      return this;
   }

   public final GameType setInDevelopment() {
      this.inDevelopment = true;
      return this;
   }

   public final GameType setRankRequired(PlayerRank par1) {
      this.rankRequired = par1.getID();
      return this;
   }

   public final GameType disableMapSelection() {
      this.isMapsSelectable = false;
      return this;
   }

   public int getRequiredRank() {
      return this.rankRequired;
   }

   public String getDisplayName() {
      return this.displayName;
   }

   public int getID() {
      return this.gameID;
   }

   public String getNameID() {
      return this.nameID;
   }

   public MapType[] getMaps() {
      return this.maps;
   }

   public final GameType setMaps(MapType... par1) {
      this.maps = par1;
      return this;
   }

   public boolean isInDevelopment() {
      return this.inDevelopment;
   }

   public boolean isRankAccepted(PlayerDataCloud par1) {
      PlayerRank usersRank = PlayerRank.getRankFromEXP(par1.exp);
      return usersRank == null || usersRank.getID() >= this.rankRequired;
   }

   public ArrayList getMapsList() {
      return new ArrayList(Arrays.asList(this.maps));
   }

   static {
      casual = (new GameType(1, "Обычный", "cas")).setMaps(MapType.AZTEC).setDescription("Команды, 10x10. Время раунда 3 минуты. Выигрывают те, кто выиграют 8 раундов. Устраните противников или выполните цель.").disableMapSelection();
      teamDeathMatch = (new GameType(2, "Командный бой", "tdm")).setMaps(MapType.AZTEC).setDescription("Команды, 8x8. Максимальный счет 100, Время игры 10 минут. Убейте противников вражеской команды быстрее, чем они убьют вас.").disableMapSelection();
      competitive = (new GameType(3, "Соревнования обезвреживания", "cbd")).setMaps(MapType.AZTEC).setDescription("Команды, 5x5. Время раунда 2 минуты. 20 раундов, Устраните противников или выполните цель.").disableMapSelection();
      dominating = (new GameType(4, "Захват точек", "dom")).setMaps(MapType.AZTEC).setDescription("Без команд. Максимальный счет 100. Время игры 10 минут. Наберите больше всего очков и захватите больше всего точек, и победите!").disableMapSelection();
      infected = (new GameType(5, "Заражение", "inf")).setMaps(MapType.AZTEC).setDescription("Команды, Зомби и Выжившие. Появляются предметы на земле. Время игры 10 минут. Сьешьте всех или проживите до конца!").disableMapSelection();
   }
}
