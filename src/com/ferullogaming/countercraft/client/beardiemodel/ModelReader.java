package com.ferullogaming.countercraft.client.beardiemodel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.Resource;
import net.minecraft.util.ResourceLocation;

public class ModelReader {
   public static ModelBeardieBase readModel(ResourceLocation rl) throws IOException {
      ModelBeardieBase model = new ModelBeardieBase();
      Resource res = null;
      model.path = rl.getResourcePath();
      res = Minecraft.getMinecraft().getResourceManager().getResource(rl);
      InputStream stream = null;

      try {
         stream = res.getInputStream();
      } catch (Exception var29) {
         System.out.println("FATAL ERROR: Model Loading Failed! File Path: " + rl.getResourcePath());
         return null;
      }

      BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
      String line = "";
      BeardieModelRenderer e = null;

      int indOff;
      try {
         while((line = reader.readLine()) != null) {
            String p;
            byte indOff1;
            String texX;
            String texY;
            String rX;
            int indOff11;
            if (line.contains("mOff:")) {
               p = substring(line, line.indexOf(":") + 1, line.indexOf(";"));
               p = p.trim();
               indOff11 = 0;
               texX = substring(p, indOff11, p.indexOf(",", indOff11 + 1));
               texX = texX.trim();
               model.mOffX = (float)Double.parseDouble(texX);
               indOff11 = p.indexOf(",", indOff11 + 1);
               texY = substring(p, indOff11 + 1, p.indexOf(",", indOff11 + 1));
               texY = texY.trim();
               model.mOffY = (float)Double.parseDouble(texY);
               indOff11 = p.indexOf(",", indOff11 + 1);
               rX = substring(p, indOff11 + 1);
               rX = rX.trim();
               model.mOffZ = (float)Double.parseDouble(rX);
            }

            if (line.contains("sPos:")) {
               p = substring(line, line.indexOf(":") + 1, line.indexOf(";"));
               p = p.trim();
               indOff11 = 0;
               texX = substring(p, indOff11, p.indexOf(",", indOff11 + 1));
               texX = texX.trim();
               model.sPosX = (float)Double.parseDouble(texX);
               indOff11 = p.indexOf(",", indOff11 + 1);
               texY = substring(p, indOff11 + 1, p.indexOf(",", indOff11 + 1));
               texY = texY.trim();
               model.sPosY = (float)Double.parseDouble(texY);
               indOff11 = p.indexOf(",", indOff11 + 1);
               rX = substring(p, indOff11 + 1);
               rX = rX.trim();
               model.sPosZ = (float)Double.parseDouble(rX);
            }

            if (line.contains("flamePos:")) {
               p = substring(line, line.indexOf(":") + 1, line.indexOf(";"));
               p = p.trim();
               indOff11 = 0;
               texX = substring(p, indOff11, p.indexOf(",", indOff11 + 1));
               texX = texX.trim();
               model.flamePosX = (float)Double.parseDouble(texX);
               indOff11 = p.indexOf(",", indOff11 + 1);
               texY = substring(p, indOff11 + 1, p.indexOf(",", indOff11 + 1));
               texY = texY.trim();
               model.flamePosY = (float)Double.parseDouble(texY);
               indOff11 = p.indexOf(",", indOff11 + 1);
               rX = substring(p, indOff11 + 1);
               rX = rX.trim();
               model.flamePosZ = (float)Double.parseDouble(rX);
            }

            if (line.contains("lhPos:")) {
               p = substring(line, line.indexOf(":") + 1, line.indexOf(";"));
               p = p.trim();
               indOff11 = 0;
               texX = substring(p, indOff11, p.indexOf(",", indOff11 + 1));
               texX = texX.trim();
               model.lhPosX = (float)Double.parseDouble(texX);
               indOff11 = p.indexOf(",", indOff11 + 1);
               texY = substring(p, indOff11 + 1, p.indexOf(",", indOff11 + 1));
               texY = texY.trim();
               model.lhPosY = (float)Double.parseDouble(texY);
               indOff11 = p.indexOf(",", indOff11 + 1);
               rX = substring(p, indOff11 + 1);
               rX = rX.trim();
               model.lhPosZ = (float)Double.parseDouble(rX);
            }

            if (line.contains("lhRot:")) {
               p = substring(line, line.indexOf(":") + 1, line.indexOf(";"));
               p = p.trim();
               indOff11 = 0;
               texX = substring(p, indOff11, p.indexOf(",", indOff11 + 1));
               texX = texX.trim();
               model.lhRotX = (float)Double.parseDouble(texX);
               indOff11 = p.indexOf(",", indOff11 + 1);
               texY = substring(p, indOff11 + 1, p.indexOf(",", indOff11 + 1));
               texY = texY.trim();
               model.lhRotY = (float)Double.parseDouble(texY);
               indOff11 = p.indexOf(",", indOff11 + 1);
               rX = substring(p, indOff11 + 1);
               rX = rX.trim();
               model.lhRotZ = (float)Double.parseDouble(rX);
            }

            if (line.contains("rhPos:")) {
               p = substring(line, line.indexOf(":") + 1, line.indexOf(";"));
               p = p.trim();
               indOff11 = 0;
               texX = substring(p, indOff11, p.indexOf(",", indOff11 + 1));
               texX = texX.trim();
               model.rhPosX = (float)Double.parseDouble(texX);
               indOff11 = p.indexOf(",", indOff11 + 1);
               texY = substring(p, indOff11 + 1, p.indexOf(",", indOff11 + 1));
               texY = texY.trim();
               model.rhPosY = (float)Double.parseDouble(texY);
               indOff11 = p.indexOf(",", indOff11 + 1);
               rX = substring(p, indOff11 + 1);
               rX = rX.trim();
               model.rhPosZ = (float)Double.parseDouble(rX);
            }

            if (line.contains("rhRot:")) {
               p = substring(line, line.indexOf(":") + 1, line.indexOf(";"));
               p = p.trim();
               indOff11 = 0;
               texX = substring(p, indOff11, p.indexOf(",", indOff11 + 1));
               texX = texX.trim();
               model.rhRotX = (float)Double.parseDouble(texX);
               indOff11 = p.indexOf(",", indOff11 + 1);
               texY = substring(p, indOff11 + 1, p.indexOf(",", indOff11 + 1));
               texY = texY.trim();
               model.rhRotY = (float)Double.parseDouble(texY);
               indOff11 = p.indexOf(",", indOff11 + 1);
               rX = substring(p, indOff11 + 1);
               rX = rX.trim();
               model.rhRotZ = (float)Double.parseDouble(rX);
            }

            if (line.contains("Scale:")) {
               p = substring(line, line.indexOf(":") + 1, line.indexOf(";"));
               p = p.trim();
               model.scale = (float)Double.parseDouble(p);
            }

            if (line.contains("ejectPos:")) {
               p = substring(line, line.indexOf(":") + 1, line.indexOf(";"));
               p = p.trim();
               indOff11 = 0;
               texX = substring(p, indOff11, p.indexOf(",", indOff11 + 1));
               texX = texX.trim();
               model.ePosX = (float)Double.parseDouble(texX);
               indOff11 = p.indexOf(",", indOff11 + 1);
               texY = substring(p, indOff11 + 1, p.indexOf(",", indOff11 + 1));
               texY = texY.trim();
               model.ePosY = (float)Double.parseDouble(texY);
               indOff11 = p.indexOf(",", indOff11 + 1);
               rX = substring(p, indOff11 + 1);
               rX = rX.trim();
               model.ePosZ = (float)Double.parseDouble(rX);
            }

            if (line.contains("textureHeight")) {
               p = substring(line, line.indexOf("=") + 1, line.indexOf(";"));
               p = p.trim();
               model.textureHeight = Integer.parseInt(p);
            }

            if (line.contains("textureWidth")) {
               p = substring(line, line.indexOf("=") + 1, line.indexOf(";"));
               p = p.trim();
               model.textureWidth = Integer.parseInt(p);
            }

            if (line.contains("new WW2ModelRenderer") || line.contains("new ModelRenderer") || line.contains("new BeardieModelRenderer")) {
               if (e != null) {
                  model.pieces.add(e);
               }

               p = substring(line, 0, line.indexOf("="));
               p = p.trim();
               e = new BeardieModelRenderer(model, p);
               indOff11 = 0;
               indOff11 = line.indexOf(",", indOff11);
               texX = substring(line, indOff11 + 1, line.indexOf(",", indOff11 + 1));
               texX.trim();
               e.field_78803_o = (int)Double.parseDouble(texX);
               indOff11 = line.indexOf(",", indOff11 + 1);
               texY = substring(line, indOff11 + 1, line.indexOf(")", indOff11 + 1));
               texY.trim();
               e.field_78813_p = (int)Double.parseDouble(texY);
            }

            double fY;
            String pZ;
            double fZ;
            String vertex;
            String pos;
            double xSize;
            String sY;
            double ySize;
            double fX;
            int indOff111;
            String c;
            if (line.contains("addBox") && !line.contains("\"") && e != null) {
               indOff111 = 0;
               c = substring(line, line.indexOf("addBox(") + 7, line.indexOf(",", indOff111));
               indOff111 = line.indexOf(",", indOff111);
               c = c.replace('F', ' ');
               c = c.trim();
               fX = Double.parseDouble(c);
               rX = substring(line, indOff111 + 1, line.indexOf(",", indOff111 + 1));
               indOff111 = line.indexOf(",", indOff111 + 1);
               rX = rX.replace('F', ' ');
               rX = rX.trim();
               fY = Double.parseDouble(rX);
               pZ = substring(line, indOff111 + 1, line.indexOf(",", indOff111 + 1));
               indOff111 = line.indexOf(",", indOff111 + 1);
               pZ = pZ.replace('F', ' ');
               pZ = pZ.trim();
               fZ = Double.parseDouble(pZ);
               vertex = substring(line, indOff111 + 1, line.indexOf(",", indOff111 + 1));
               indOff111 = line.indexOf(",", indOff111 + 1);
               vertex = vertex.replace('F', ' ');
               vertex = vertex.trim();
               double xSize1 = Double.parseDouble(vertex);
               pos = substring(line, indOff111 + 1, line.indexOf(",", indOff111 + 1));
               indOff111 = line.indexOf(",", indOff111 + 1);
               pos = pos.replace('F', ' ');
               pos = pos.trim();
               xSize1 = Double.parseDouble(pos);
               sY = substring(line, indOff111 + 1, line.indexOf(")", indOff111 + 1));
               sY = sY.replace('F', ' ');
               sY = sY.trim();
               ySize = Double.parseDouble(sY);
               e.addBox((float)fX, (float)fY, (float)fY, (float)xSize1, (float)xSize1, (float)ySize);
            }

            if (line.contains("addShape") && !line.contains("\"") && e != null) {
               indOff111 = 0;
               c = substring(line, line.indexOf("addShape(") + "addShape(".length(), line.indexOf(",", indOff111));
               indOff111 = line.indexOf(",", indOff111);
               c = c.replace('F', ' ');
               c = c.trim();
               fX = Double.parseDouble(c);
               rX = substring(line, indOff111 + 1, line.indexOf(",", indOff111 + 1));
               indOff111 = line.indexOf(",", indOff111 + 1);
               rX = rX.replace('F', ' ');
               rX = rX.trim();
               fY = Double.parseDouble(rX);
               pZ = substring(line, indOff111 + 1, line.indexOf(",", indOff111 + 1));
               line.indexOf(",", indOff111 + 1);
               pZ = pZ.replace('F', ' ');
               pZ = pZ.trim();
               fZ = Double.parseDouble(pZ);
               vertex = substring(line, line.indexOf("{"), line.lastIndexOf("}"));
               indOff111 = line.lastIndexOf("}") + 1;
               vertex = substring(vertex, vertex.indexOf("{") + 1, vertex.lastIndexOf("}") + 1);
               float[][] vertices = new float[8][3];

               for(int vert = 0; vertex.contains("}"); ++vert) {
                  pos = substring(vertex, vertex.indexOf("{") + 1, vertex.indexOf("}"));
                  int index = 0;

                  while(pos.contains(",")) {
                     String p1 = substring(pos, 0, pos.indexOf(",")).trim();
                     p1 = p1.replace('F', ' ');
                     p1 = p1.trim();
                     int neg = index == 1 ? 1 : 1;
                     vertices[vert][index] = Float.parseFloat(p1) * (float)neg;
                     pos = substring(pos, pos.indexOf(",") + 1);
                     ++index;
                     if (index == 2) {
                        p1 = pos.trim();
                        p1 = p1.replace('F', ' ');
                        p1 = p1.trim();
                        vertices[vert][index] = Float.parseFloat(p1);
                     }
                  }

                  int index1 = 0;
                  vertex = substring(vertex, vertex.indexOf("}") + 1);
               }

               pos = substring(line, indOff111 + 1, line.indexOf(",", indOff111 + 1));
               indOff111 = line.indexOf(",", indOff111 + 1);
               pos = pos.replace('F', ' ');
               pos = pos.trim();
               xSize = Double.parseDouble(pos);
               sY = substring(line, indOff111 + 1, line.indexOf(",", indOff111 + 1));
               indOff111 = line.indexOf(",", indOff111 + 1);
               sY = sY.replace('F', ' ');
               sY = sY.trim();
               ySize = Double.parseDouble(sY);
               String sZ = substring(line, indOff111 + 1, line.indexOf(")", indOff111 + 1));
               sZ = sZ.replace('F', ' ');
               sZ = sZ.trim();
               double zSize = Double.parseDouble(sZ);
               e.addShape((float)fX, (float)fY, (float)fZ, vertices, (float)xSize, (float)ySize, (float)zSize);
            }

            if (line.contains("setRotationPoint") && e != null) {
               indOff111 = 0;
               c = substring(line, line.indexOf("Point(") + 6, line.indexOf(",", indOff111));
               indOff111 = line.indexOf(",", indOff111);
               c = c.replace('F', ' ');
               c = c.trim();
               fX = Double.parseDouble(c);
               rX = substring(line, indOff111 + 1, line.indexOf(",", indOff111 + 1));
               indOff111 = line.indexOf(",", indOff111 + 1);
               rX = rX.replace('F', ' ');
               rX = rX.trim();
               fY = Double.parseDouble(rX);
               pZ = substring(line, indOff111 + 1, line.indexOf(")", indOff111 + 1));
               line.indexOf(",", indOff111 + 1);
               pZ = pZ.replace('F', ' ');
               pZ = pZ.trim();
               fZ = Double.parseDouble(pZ);
               e.setRotationPoint((float)fX, (float)fY, (float)fZ);
            }

            if (line.contains("setRotation(") && !line.contains("public") && !line.contains("private") && e != null) {
               float x = 0.0F;
               float y = 0.0F;
               float z = 0.0F;
               String rZ;
               String rY;
               byte indOff1111;
               int indOff11111;
               if (line.contains(".setRot")) {
                  indOff11111 = 0;
                  rX = substring(line, line.indexOf("Rotation(") + 9, line.indexOf(",", indOff11111));
                  rX = rX.replace('F', ' ');
                  rX = rX.trim();
                  if (rX.contains(" / rotFix")) {
                     rX = substring(rX, 0, rX.indexOf(" /"));
                     rX = rX.replace('F', ' ');
                     rX = rX.trim();
                     x = Float.parseFloat(rX);
                  } else {
                     x = Float.parseFloat(rX);
                  }

                  indOff11111 = line.indexOf(",", indOff11111);
                  rY = substring(line, indOff11111 + 1, line.indexOf(",", indOff11111 + 1));
                  rY = rY.replace('F', ' ');
                  rY = rY.trim();
                  if (rY.contains(" / rotFix")) {
                     rY = substring(rY, 0, rY.indexOf(" /"));
                     rY = rY.replace('F', ' ');
                     rY = rY.trim();
                     y = Float.parseFloat(rY);
                  } else {
                     y = Float.parseFloat(rY);
                  }

                  indOff11111 = line.indexOf(",", indOff11111 + 1);
                  rZ = substring(line, indOff11111 + 1, line.indexOf(")", indOff11111 + 1));
                  rZ = rZ.replace('F', ' ');
                  rZ = rZ.trim();
                  if (rZ.contains(" / rotFix")) {
                     rZ = rZ.replace('F', ' ');
                     rZ = substring(rZ, 0, rZ.indexOf(" /"));
                     rZ = rZ.trim();
                     z = Float.parseFloat(rZ);
                  } else {
                     z = Float.parseFloat(rZ);
                  }
               } else {
                  indOff11111 = 0;
                  indOff11111 = line.indexOf(",", indOff11111);
                  rX = substring(line, indOff11111 + 1, line.indexOf(",", indOff11111 + 1));
                  rX = rX.replace('F', ' ');
                  rX = rX.trim();
                  if (rX.contains(" / rotFix")) {
                     rX = substring(rX, 0, rX.indexOf(" /"));
                     rX = rX.replace('F', ' ');
                     rX = rX.trim();
                     x = Float.parseFloat(rX);
                  } else {
                     x = Float.parseFloat(rX);
                  }

                  indOff11111 = line.indexOf(",", indOff11111 + 1);
                  rY = substring(line, indOff11111 + 1, line.indexOf(",", indOff11111 + 1));
                  rY = rY.replace('F', ' ');
                  rY = rY.trim();
                  if (rY.contains(" / rotFix")) {
                     rY = substring(rY, 0, rY.indexOf(" /"));
                     rY = rY.replace('F', ' ');
                     rY = rY.trim();
                     y = Float.parseFloat(rY);
                  } else {
                     y = Float.parseFloat(rY);
                  }

                  indOff11111 = line.indexOf(",", indOff11111 + 1);
                  rZ = substring(line, indOff11111 + 1, line.indexOf(")", indOff11111 + 1));
                  rZ = rZ.replace('F', ' ');
                  rZ = rZ.trim();
                  if (rZ.contains(" / rotFix")) {
                     rZ = substring(rZ, 0, rZ.indexOf(" /"));
                     rZ = rZ.replace('F', ' ');
                     rZ = rZ.trim();
                     z = Float.parseFloat(rZ);
                  } else {
                     z = Float.parseFloat(rZ);
                  }
               }

               e.setRotation(x, y, z);
            }

            if (line.contains("addChild")) {
               if (e != null && !model.pieces.contains(e)) {
                  model.pieces.add(e);
               }

               p = substring(line, 0, line.indexOf("."));
               c = substring(line, line.indexOf("(") + 1, line.indexOf(")"));
               BeardieModelRenderer pEl = null;
               BeardieModelRenderer cEl = null;
               p = p.trim();
               c = c.trim();

               for(int i = 0; i < model.pieces.size(); ++i) {
                  BeardieModelRenderer el = (BeardieModelRenderer)model.pieces.get(i);
                  if (el.field_78802_n.equalsIgnoreCase(p)) {
                     pEl = el;
                  }

                  if (el.field_78802_n.equalsIgnoreCase(c)) {
                     el.isChild = true;
                     cEl = el;
                     model.pieces.set(i, el);
                  }
               }

               if (pEl != null && cEl != null) {
                  pEl.addChild(cEl);
                  cEl.isChild = true;
                  if (cEl.field_78802_n.equalsIgnoreCase(e.field_78802_n)) {
                     e = cEl;
                  }
               }
            }
         }
      } catch (IOException var30) {
         var30.printStackTrace();
      }

      if (e != null && !model.pieces.contains(e) && !e.isChild) {
         model.pieces.add(e);
      }

      for(indOff = 0; indOff < model.pieces.size(); ++indOff) {
         BeardieModelRenderer el = (BeardieModelRenderer)model.pieces.get(indOff);
         if (el.isChild) {
            model.pieces.remove(indOff);
            --indOff;
         }
      }

      return model;
   }

   public static String substring(String s, int startPos, int endPos) {
      return s.substring(startPos, endPos);
   }

   public static String substring(String s, int startPos) {
      return s.substring(startPos);
   }
}
