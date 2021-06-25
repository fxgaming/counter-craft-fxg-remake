package com.ferullogaming.countercraft;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;

import com.ferullogaming.countercraft.client.ClientNotification;

public class HDD {
	/**
	 * GameInfoType format - 
	 * <br>
	 * First[INT] - Game ID - 
	 *  ! - null,
	 *  0 - CAS,
	 *  1 - TDM
	 *  2 - CBD,
	 *  3 - DOM,
	 *  4 - INF.
	 *  <br><br>
	 *  First[INT] - GameData = 
	 *  <br>
	 *  0 - BOOLEAN - isEnabled
	 *  1 - STRING - HOST IP
	 *  2 - STRING - HOST PORT
	 *  3 - STRING - MAXPLAYERS IN 1 GAME
	 *  4 - STRING - GAMES IN MAP(SERVER)
	 */
	public static Object[][] GIT = new Object[6][5];
	public static Boolean[] gitActive = new Boolean[5];
	public static File SkinInv = new File("skin.dat");
	public static File StickerInv = new File("sticker.dat");
	public static File KnifeInv = new File("knife.dat");
	public static HashMap<Integer, String> buff = new HashMap<Integer, String>();
	public static HashMap<Integer, String> buff2 = new HashMap<Integer, String>();
	public static HashMap<Integer, String> buff3 = new HashMap<Integer, String>();
	public static String[] SKIN = new String[25];
	public static String[] STICKER = new String[25];
	public static String[] KNIFE = new String[2];
	public void client() {
		CounterCraft.instance.sponsor.put("ceeeeeej", true);
		this.SKIN = new String[25];
		this.STICKER = new String[25];
		this.KNIFE = new String[2];
		this.Read(SkinInv);
		this.Read2(StickerInv);
		this.Read3(KnifeInv);
	}
	
	public void writeAllInformation() {
		BufferedReader reader = null;
		URL now;
		String[] fullinfo = new String[8];
		try {
			now = new URL(References.URI_PW + "/pw/banlist.txt");
			reader = new BufferedReader(new InputStreamReader(now.openStream()));
			String line;
			for (int i = 0; i != 256; i++) {
				line = reader.readLine().toLowerCase();
				CounterCraft.instance.ban.put(line, true);
			}
		} catch(Exception e){}
		
		try {
			now = new URL(References.URI_PW + "/pw/sponsors.txt");
			reader = new BufferedReader(new InputStreamReader(now.openStream()));
			String line;
			for (int i = 0; i != 256; i++) {
				line = reader.readLine().toLowerCase();
				CounterCraft.instance.sponsor.put(line, true);
			}
		} catch(Exception e){}
		
		try {
			now = new URL(References.URI_PW + "/pw/active.txt");
			reader = new BufferedReader(new InputStreamReader(now.openStream()));
			for (int i = 0; i != 6; i++) this.gitActive[i] = reader.readLine().equalsIgnoreCase("true") ? true : false;
		} catch(Exception e){}
		
		try {
			now = new URL(References.URI_PW + "/pw/games.txt");
			reader = new BufferedReader(new InputStreamReader(now.openStream()));
			for (int i = 0; i != 8; i++) fullinfo[i] = reader.readLine();
		} catch(Exception e){ e.printStackTrace(); }
		
		String[] a = fullinfo[0].replaceAll("ENABLE:", "").split(",");
		String b = "";
		String[] c;
		for (int i = 0; i != a.length; i++) {
			this.GIT[Integer.valueOf(a[i])][0] = true;
			c = fullinfo[Integer.valueOf(a[i]) + 2].split("=")[1].split(",");
			for (int j = 0; j != c.length; j++) {
				b = b.concat(c[j].split(":")[0]);
			}
			this.GIT[Integer.valueOf(a[i])][1] = b;
			b = "";
			c = fullinfo[Integer.valueOf(a[i]) + 2].split("=")[1].split(",");
			for (int j = 0; j != c.length; j++) {
				b = b.concat(c[j].split(":")[1]);
			}
			this.GIT[Integer.valueOf(a[i])][2] = b;
			b = "";
			c = fullinfo[Integer.valueOf(a[i]) + 2].split("=")[1].split(",");
			for (int j = 0; j != c.length; j++) {
				b = b.concat(c[j].split(":")[2]);
			}
			this.GIT[Integer.valueOf(a[i])][4] = b;
			b = "";
			c = fullinfo[1].replaceAll("MAXPLA_INT:", "").split(",");
			this.GIT[Integer.valueOf(a[i])][3] = c[Integer.valueOf(a[i])];
		}
	}
	
	public void Read(File a) {
		this.file(a);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(a));
			for (int i = 0; i != this.SKIN.length; i++) {
				this.SKIN[i] = reader.readLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static File file(File a) {
		if (a.exists()) return a;
		try {
			a.createNewFile();
			WriteDefaults(a);
		} catch(Exception e) {}
		return a;
	}
	
	public static File file2(File a) {
		if (a.exists()) return a;
		try {
			a.createNewFile();
			WriteDefaults2(a);
		} catch(Exception e) {}
		return a;
	}
	
	public static void pre_line(File a) {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(a));
			for (int i = 0; i != 25; i++) {
				buff.put(i, reader.readLine()); 
			}
		} catch (Exception e) {
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
			}
		}
	}
	
	public static void line(String data, int line, File files) {
        File file = files;
        FileWriter fr = null;
        BufferedWriter br = null;
        pre_line(files);
        String separator = System.getProperty("line.separator");
        try {
            fr = new FileWriter(file);
            br = new BufferedWriter(fr);
            for(int i = 0; i != 25; i++){
            	if (i == (line - 1)) {
            		br.write(data + separator);
            		continue;
            	}
            	br.write(buff.get(i) + separator);
            }
        } catch (Exception e) {
        	file(SkinInv);
        	buff.clear();
        	ClientNotification.createNotification("Произошла критическая ошибка!");
        } finally {
            try {
                br.close();
                fr.close();
                buff.clear();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
	
	public static void WriteDefaults(File file) {
		FileWriter fr = null;
		BufferedWriter br = null;
		String a = System.getProperty("line.separator");
		try {
			file.createNewFile();
			fr = new FileWriter(file);
			br = new BufferedWriter(fr);
			br.write("1::" + a);
			br.write("2::" + a);
			br.write("3::" + a);
			br.write("4::" + a);
			br.write("5::" + a);
			br.write("6::" + a);
			br.write("7::" + a);
			br.write("8::" + a);
			br.write("9::" + a);
			br.write("10::" + a);
			br.write("11::" + a);
			br.write("12::" + a);
			br.write("13::" + a);
			br.write("14::" + a);
			br.write("15::" + a);
			br.write("16::" + a);
			br.write("17::" + a);
			br.write("18::" + a);
			br.write("19::" + a);
			br.write("20::" + a);
			br.write("21::" + a);
			br.write("22::" + a);
			br.write("23::" + a);
			br.write("24::" + a);
			br.write("25::" + a);
		} catch (Exception e) {
		} finally {
			try {
                br.close();
                fr.close();
            } catch (Exception e) {
            }
        }
    }
	
	public static void WriteDefaults2(File file) {
		FileWriter fr = null;
		BufferedWriter br = null;
		String a = System.getProperty("line.separator");
		try {
			file.createNewFile();
			fr = new FileWriter(file);
			br = new BufferedWriter(fr);
			br.write("item:" + a);
			br.write("skin:" + a);
		} catch (Exception e) {
		} finally {
			try {
                br.close();
                fr.close();
            } catch (Exception e) {
            }
        }
    }
	
	public void Read2(File a) {
		this.file(a);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(a));
			for (int i = 0; i != this.STICKER.length; i++) {
				this.STICKER[i] = reader.readLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void pre_line2(File a) {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(a));
			for (int i = 0; i != 25; i++) {
				buff2.put(i, reader.readLine()); 
			}
		} catch (Exception e) {
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
			}
		}
	}
	
	public static void line2(String data, int line, File files) {
        File file = files;
        FileWriter fr = null;
        BufferedWriter br = null;
        pre_line2(files);
        String separator = System.getProperty("line.separator");
        try {
            fr = new FileWriter(file);
            br = new BufferedWriter(fr);
            for(int i = 0; i != 25; i++){
            	if (i == (line - 1)) {
            		br.write(data + separator);
            		continue;
            	}
            	br.write(buff2.get(i) + separator);
            }
        } catch (Exception e) {
        	file(StickerInv);
        	buff2.clear();
        	ClientNotification.createNotification("Произошла критическая ошибка!");
        } finally {
            try {
                br.close();
                fr.close();
                buff2.clear();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
	
	public void Read3(File a) {
		this.file2(a);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(a));
			for (int i = 0; i != this.KNIFE.length; i++) {
				this.KNIFE[i] = reader.readLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void pre_line3(File a) {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(a));
			for (int i = 0; i != 2; i++) {
				buff3.put(i, reader.readLine()); 
			}
		} catch (Exception e) {
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
			}
		}
	}
	
	public static void line3(String data, int line, File files) {
        File file = files;
        FileWriter fr = null;
        BufferedWriter br = null;
        pre_line3(files);
        String separator = System.getProperty("line.separator");
        try {
            fr = new FileWriter(file);
            br = new BufferedWriter(fr);
            for(int i = 0; i != 2; i++){
            	if (i == (line - 1)) {
            		br.write(data + separator);
            		continue;
            	}
            	br.write(buff3.get(i) + separator);
            }
        } catch (Exception e) {
        	file(KnifeInv);
        	buff3.clear();
        	ClientNotification.createNotification("Произошла критическая ошибка!");
        } finally {
            try {
                br.close();
                fr.close();
                buff3.clear();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}