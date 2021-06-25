package com.ferullogaming.countercraft.client.minimap.map;

import com.ferullogaming.countercraft.client.minimap.MinimapUtils;
import com.ferullogaming.countercraft.client.minimap.forge.MinimapConfig;
import com.ferullogaming.countercraft.client.minimap.map.mapmode.MapMode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MarkerManager {
   public List markerList = new ArrayList();
   public List groupList = new ArrayList();
   public List visibleMarkerList = new ArrayList();
   public Marker selectedMarker = null;
   private String visibleGroupName = "none";

   public void load(MinimapConfig config, String category) {
      this.markerList.clear();
      if (config.hasCategory(category)) {
         int markerCount = config.get(category, "markerCount", 0).getInt();
         this.visibleGroupName = config.get(category, "visibleGroup", "").getString();
         if (markerCount > 0) {
            for(int i = 0; i < markerCount; ++i) {
               String key = "marker" + i;
               String value = config.get(category, key, "").getString();
               Marker marker = this.stringToMarker(value);
               if (marker != null) {
                  this.addMarker(marker);
               } else {
                  MinimapUtils.log("error: could not load " + key + " from config file");
               }
            }
         }
      }

      this.update();
   }

   public void save(MinimapConfig config, String category) {
      config.get(category, "markerCount", 0).set(this.markerList.size());
      config.get(category, "visibleGroup", "").set(this.visibleGroupName);
      int i = 0;

      for(Iterator i$ = this.markerList.iterator(); i$.hasNext(); ++i) {
         Marker marker = (Marker)i$.next();
         String key = "marker" + i;
         String value = this.markerToString(marker);
         config.get(category, key, "").set(value);
      }

   }

   public String getVisibleGroupName() {
      return this.visibleGroupName;
   }

   public void setVisibleGroupName(String groupName) {
      if (groupName != null) {
         this.visibleGroupName = MinimapUtils.mungeString(groupName);
      } else {
         this.visibleGroupName = "none";
      }

   }

   public void clear() {
      this.markerList.clear();
      this.groupList.clear();
      this.visibleMarkerList.clear();
      this.visibleGroupName = "none";
   }

   public String markerToString(Marker marker) {
      return String.format("%s %d %d %d %d %06x %s", marker.name, marker.x, marker.y, marker.z, marker.dimension, marker.colour & 16777215, marker.groupName);
   }

   public Marker stringToMarker(String s) {
      String[] split = s.split(" ");
      Marker marker = null;
      if (split.length != 6 && split.length != 7) {
         MinimapUtils.log("Marker.stringToMarker: incorrect number of parameters %d, need 6 or 7", split.length);
      } else {
         try {
            int x = Integer.parseInt(split[1]);
            int y = Integer.parseInt(split[2]);
            int z = Integer.parseInt(split[3]);
            int i = 4;
            int dimension = 0;
            if (split.length == 7) {
               dimension = Integer.parseInt(split[4]);
               ++i;
            }

            int colour = -16777216 | Integer.parseInt(split[i++], 16);
            marker = new Marker(split[0], split[i++], x, y, z, dimension, colour);
         } catch (NumberFormatException var10) {
            marker = null;
         }
      }

      return marker;
   }

   public void addMarker(Marker marker) {
      this.markerList.add(marker);
   }

   public void addMarker(String name, String groupName, int x, int y, int z, int dimension, int colour, boolean isBombZone, boolean isBombPlanted) {
      name = MinimapUtils.mungeString(name);
      groupName = MinimapUtils.mungeString(groupName);
      this.addMarker(isBombZone ? (new Marker(name, groupName, x, y, z, dimension, colour)).setBombZone(isBombPlanted) : new Marker(name, groupName, x, y, z, dimension, colour));
   }

   public boolean delMarker(Marker markerToDelete) {
      return this.markerList.remove(markerToDelete);
   }

   public boolean delMarker(String name, String group) {
      Marker markerToDelete = null;
      Iterator i$ = this.markerList.iterator();

      while(i$.hasNext()) {
         Marker marker = (Marker)i$.next();
         if ((name == null || marker.name.equals(name)) && (group == null || marker.groupName.equals(group))) {
            markerToDelete = marker;
            break;
         }
      }

      return this.delMarker(markerToDelete);
   }

   public void update() {
      this.visibleMarkerList.clear();
      this.groupList.clear();
      this.groupList.add("none");
      this.groupList.add("all");
      Iterator i$ = this.markerList.iterator();

      while(i$.hasNext()) {
         Marker marker = (Marker)i$.next();
         if (marker.groupName.equals(this.visibleGroupName) || this.visibleGroupName.equals("all")) {
            this.visibleMarkerList.add(marker);
         }

         if (!this.groupList.contains(marker.groupName)) {
            this.groupList.add(marker.groupName);
         }
      }

      if (!this.groupList.contains(this.visibleGroupName)) {
         this.visibleGroupName = "none";
      }

   }

   public void nextGroup(int n) {
      if (this.groupList.size() > 0) {
         int i = this.groupList.indexOf(this.visibleGroupName);
         int size = this.groupList.size();
         if (i != -1) {
            i = (i + size + n) % size;
         } else {
            i = 0;
         }

         this.visibleGroupName = (String)this.groupList.get(i);
      } else {
         this.visibleGroupName = "none";
         this.groupList.add("none");
      }

   }

   public void nextGroup() {
      this.nextGroup(1);
   }

   public int countMarkersInGroup(String group) {
      int count = 0;
      if (group.equals("all")) {
         count = this.markerList.size();
      } else {
         Iterator i$ = this.markerList.iterator();

         while(i$.hasNext()) {
            Marker marker = (Marker)i$.next();
            if (marker.groupName.equals(group)) {
               ++count;
            }
         }
      }

      return count;
   }

   public void selectNextMarker() {
      if (this.visibleMarkerList.size() > 0) {
         int i = 0;
         if (this.selectedMarker != null) {
            i = this.visibleMarkerList.indexOf(this.selectedMarker);
            if (i == -1) {
               i = 0;
            }
         }

         i = (i + 1) % this.visibleMarkerList.size();
         this.selectedMarker = (Marker)this.visibleMarkerList.get(i);
      } else {
         this.selectedMarker = null;
      }

   }

   public Marker getNearestMarker(int x, int z, int maxDistance) {
      int nearestDistance = maxDistance * maxDistance;
      Marker nearestMarker = null;
      Iterator i$ = this.visibleMarkerList.iterator();

      while(i$.hasNext()) {
         Marker marker = (Marker)i$.next();
         int dx = x - marker.x;
         int dz = z - marker.z;
         int d = dx * dx + dz * dz;
         if (d < nearestDistance) {
            nearestMarker = marker;
            nearestDistance = d;
         }
      }

      return nearestMarker;
   }

   public Marker getNearestMarkerInDirection(int x, int z, double desiredAngle) {
      int nearestDistance = 100000000;
      Marker nearestMarker = null;
      Iterator i$ = this.visibleMarkerList.iterator();

      while(i$.hasNext()) {
         Marker marker = (Marker)i$.next();
         int dx = marker.x - x;
         int dz = marker.z - z;
         int d = dx * dx + dz * dz;
         double angle = Math.atan2((double)dz, (double)dx);
         if (Math.cos(desiredAngle - angle) > 0.8D && d < nearestDistance && d > 4) {
            nearestMarker = marker;
            nearestDistance = d;
         }
      }

      return nearestMarker;
   }

   public void drawMarkers(MapMode mapMode, MapView mapView) {
      Iterator i$ = this.visibleMarkerList.iterator();

      while(i$.hasNext()) {
         Marker marker = (Marker)i$.next();
         marker.draw(mapMode, mapView, -16777216, marker.isBombZone);
      }

      if (this.selectedMarker != null) {
         this.selectedMarker.draw(mapMode, mapView, -1, this.selectedMarker.isBombZone);
      }

   }
}
