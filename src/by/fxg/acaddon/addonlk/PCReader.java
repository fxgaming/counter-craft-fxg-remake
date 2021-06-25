package by.fxg.acaddon.addonlk;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Base64;
import java.util.Date;

import net.minecraft.client.Minecraft;

public class PCReader {
	public static Object[] getLKDATA(URL uri) {
		String info[] = new String[16];
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(uri.openStream()));
		} catch(Exception e){}
		for (int i = 0; i < 9; i++) {
			
			try {
				info[i] = reader.readLine();
			} catch(IOException e){}
		}
		try {
		final String[] oldver = info[0].split("version:"); 			final String ver = oldver[1];
		final String[] vers = info[1].split("linesCount:"); 			final String ncount = vers[1];
		final String[] nCount = info[3].split("nextupd:"); 		final String numOfN = nCount[1];
		final String[] log = info[2].split("log:"); 				byte[] var5 = new byte[999]; 
		try {for (int ii = 0; ii < 999; ++ii) {var5 = Base64.getDecoder().decode(log[1]);}} catch(Exception e) {}
		final String[] textInLines = new String(var5).split("/n");
		final String[] bool0 = info[4].split("singleallow:");   final String boolean0 = bool0[1];
		return new Object[]{ver, ncount, textInLines, numOfN, boolean0};
		}
		catch (Exception espan) {return new Object[]{};}
	}
	
	public static void saveImage(String imageUrl, String dest) throws IOException {
		URL url = new URL(imageUrl);
		String fileName = url.getFile();
		String destName = dest;
	 
		InputStream is = url.openStream();
		OutputStream os = new FileOutputStream(destName);
	 
		byte[] b = new byte[2048];
		int length;
	 
		while ((length = is.read(b)) != -1) {
			os.write(b, 0, length);
		}
	 
		is.close();
		os.close();
	}
}
