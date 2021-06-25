package com.ferullogaming.countercraft.core;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.MCVersion;

import java.io.File;
import java.util.Map;

@MCVersion("1.6.4")
public class CCFMLLoadingPlugin implements IFMLLoadingPlugin {
   public static File location;

   public String[] getASMTransformerClass() {
      return new String[]{CCClassTransformer.class.getName()};
   }

   public String getModContainerClass() {
      return CCDummyContainer.class.getName();
   }

   public String getSetupClass() {
      return null;
   }

   public void injectData(Map data) {
      location = (File)data.get("coremodLocation");
   }

@Override
public String[] getLibraryRequestClass() {
	// TODO Auto-generated method stub
	return null;
}
}
