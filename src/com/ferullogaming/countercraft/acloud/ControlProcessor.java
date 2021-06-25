package com.ferullogaming.countercraft.acloud;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;

import com.ferullogaming.countercraft.CounterCraft;
import com.ferullogaming.countercraft.client.ClientNotification;
/**
 * CPU Units List
 * <br> 00-09 User
 * <br> 10-29 File system  
 * 
 * <br> 90-99 File type messages
 * 
 * <br> 100-199 Loading/Working systems
 * @author FXG
 * @author P.S. SENDING ONLY RAW DATA
 */
public class ControlProcessor {
	public CounterCraft c = CounterCraft.instance;
	private static HashMap<Integer, String> BUFFER = new HashMap<Integer, String>();
	public static final int invSlots = 16;
	
	/**
	 * PROFILACTICS
	 */
	public static void unit_00() {
		if (!new File("cloud/.dir").exists()) {
			new File("cloud").mkdir();
			try {new File("cloud/.dir").createNewFile();} catch (Exception e) {}
		}
		if (!new File("cloud/users/.dir").exists()) {
			new File("cloud/users/").mkdir();
			try {new File("cloud/users/.dir").createNewFile();} catch (Exception e) {}
		}
		if (!new File("cloud/inv/.dir").exists()) {
			new File("cloud/inv/").mkdir();
			try {new File("cloud/inv/.dir").createNewFile();} catch (Exception e) {}
		}
		//if (!.exists()) .mkdir();
	}
	
	/**
	 * Logging system (onLoad)
	 * @param username
	 */
	public static void unit_01(String username) {
		unit_10(username, Type.ALOGGING);
	}

	/**
	 * Exit system (onQuit)
	 * @param username
	 */
	public static void unit_02(String username) {
		unit_10(username, Type.AEXITING);
	}

	public static void unit_03() {
		
	}
	
	public static void unit_04() {
		
	}
	
	public static void unit_05() {
		
	}
	
	
	/**
	 * Stepping for File I/O
	 * @param username
	 */
	public static void unit_10(String username, int A_TYPE) {
		switch(A_TYPE) {
			case Type.ALOGGING: 
				unit_11(username);
				break;
			case Type.AEXITING:
				unit_12(username);
				break;
			case Type.AREADING:
			case Type.AWRITING:
			default:
				
		}
	}
	
	/**
	 * PlayerProcessing LOGIN
	 * @param username
	 */
	public static void unit_11(String username) {
		File user = new File("cloud/users/" + username + ".data");
		if (!user.exists()) {
			unit_100(username);
			unit_90(username);
			return;
		}
		unit_91(username);
		unit_103(username);
		unit_104(username);
	}
	
	/**
	 * PlayerProcessing QUIT
	 * @param username
	 */
	public static void unit_12(String username) {
		
	}
	
	/**
	 * 	REQUEST ANSWERER, SENDING INFO OBJECT!
	 * 	  DONT CHANGE ANYTHING!
	 * @param username
	 */
	public static Object[] unit_13(String username) {
		Object[] UserData = new Object[16];
		Object[] UserInv = new Object[16];
		UserData = unit_103(username);
		UserInv = unit_104(username);
		return new Object[]{UserData, UserInv};
	}
	
	/**
	 * Read
	 * @param username
	 */
	public static void unit_14(String username) {
		
	}
	
	public static void unit_15(String username) {
		
	}
	
	/**
	 * Write default user file
	 * @param username
	 */
	public static void unit_90(String username) {
		File f = new File("cloud/users/" + username + ".data");
		FileWriter fw = null;
		BufferedWriter bw = null;
		String $l = System.getProperty("line.separator");
		f.mkdir();
		try {
			fw = new FileWriter(f);
			bw = new BufferedWriter(fw);
			bw.write("username:" + username + $l);
			bw.write("created:" + Calendar.getInstance().getTime().getTime() + $l);
			bw.write("sponsor:false" + $l);
			bw.write("sponsince:0" + $l);
			bw.write("ban:false" + $l);
			bw.write("bsince:0" + $l);
			bw.write("coins:0" + $l);
			bw.write("xp:0" + $l);
			bw.write("win:0" + $l);
			bw.write("lose:0" + $l);
			bw.write("rank:0" + $l);
		} catch (Exception e) {
			err();
			log(90, e.getMessage());
		} finally {
			try {
				fw.flush();
				bw.close();
			} catch (Exception e) {
				err();
				log(90, e.getMessage());
			}
		}
	}
	
	/**
	 * Inventory Writer
	 * she have brain ;)
	 * @param username
	 */
	public static void unit_91(String username) {
		File f = new File("cloud/inv/" + username + "/");
		f.mkdir();
		f = new File("cloud/inv/" + username + "/.dir");
		try {f.createNewFile();} catch (Exception e) {f.mkdirs(); try {f.createNewFile();} catch (Exception e1){}}
		Boolean shouldWrite = false;
		Integer from = 0;
		for (int i = 1; i != (invSlots + 1); i++) {
			if (!new File("cloud/inv/" + username + "/slot" + i + ".data").exists()) {
				from = i;
				shouldWrite = true;
				break;
			}
		}
		if (shouldWrite) {
			for (int i = 1; i != (invSlots + 1); i++) {
				if (i < from) continue;
				FileWriter fw = null;
				BufferedWriter bw = null;
				String $l = System.getProperty("line.separator");
				try {
					File fl = new File("cloud/inv/" + username + "/slot" + i + ".data"); //FIXME
					if (!fl.exists()) fl.createNewFile();
					fw = new FileWriter(fl);
					bw = new BufferedWriter(fw);
					bw.write("username:" + username + $l);
					bw.write("created:" + Calendar.getInstance().getTime().getTime() + $l);
					bw.write("slot:" + i + $l);
					bw.write("itemid:0" + $l);
					bw.write("itemmeta:0" + $l);
					bw.write("skindata:none" + $l);
				} catch (Exception e) {
					err();
					log(91, e.getMessage());
				} finally {
					try {
						fw.flush();
						bw.close();
					} catch (Exception e) {
						err();
						log(91, e.getMessage());
					}
				}
			}
		}
	}
	
	/**
	 * Write new user
	 * @param username
	 */
	public static void unit_100(String username) {
		File f = new File("cloud/users/" + username + ".data");
		if (!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				err();
				log(100, e.getMessage());
			}
		}
		
	}
	
	/**
	 * Delete user
	 * @param username
	 */
	public static void unit_101(String username) {
		File f = new File("cloud/users/" + username + ".data");
		if (f.exists()) {
			f.delete();
		}
	}
	
	/**
	 * UserData Reader
	 * @param username
	 * @return users data
	 */
	public static Object[] unit_103(String username) {
		File file = new File("cloud/users/" + username + ".data");
		Object[] data = new Object[11];
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			for (int i = 0; i != data.length; i++) {
				data[i] = reader.readLine();
			}
		} catch (Exception e) {
			err();
			log(103, e.getMessage());
			e.printStackTrace();
		}
		return data;
	}
	
	/**
	 * Inventory reader
	 * @param username
	 * @return users invdata
	 */
	public static Object[][] unit_104(String username) {
		Object[][] data = new Object[invSlots][6];
		BufferedReader reader = null;
		try {
			for (int i = 0; i != invSlots; i++) {
				File f = new File("cloud/inv/" + username + "/slot" + (i + 1) + ".data");
				reader = new BufferedReader(new FileReader(f));
				for (int j = 0; j != data[i].length; j++) {
					data[i][j] = reader.readLine();
				}
			}
		} catch (Exception e) {
			err();
			log(104, e.getMessage());
		}
		return data;
	}
	
	/**
	 * Dont touch.
	 */
	private static void unit_105(File file) {
		BUFFER.clear();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			for (int i = 0; i != 2; i++) {
				BUFFER.put(i, reader.readLine()); 
			}
		} catch (Exception e) {
			err();
			log(105, e.getMessage());
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				err();
				log(105, e.getMessage());
			}
		}
	}
	
	/**
	 * Write special {@link data} data in {@link line} line in file {@link file} file
	 */
	public static void unit_106(String data, int line, File file) {
        FileWriter fr = null;
        BufferedWriter br = null;
        unit_105(file);
        String separator = System.getProperty("line.separator");
        try {
            fr = new FileWriter(file);
            br = new BufferedWriter(fr);
            for(int i = 0; i != 2; i++){
            	if (i == (line - 1)) {
            		br.write(data + separator);
            		continue;
            	}
            	br.write(BUFFER.get(i) + separator);
            }
        } catch (Exception e) {
        	err();
			log(106, e.getMessage());
        } finally {
            try {
                br.close();
                fr.close();
                BUFFER.clear();
            } catch (IOException e) {
            	err();
				log(106, e.getMessage());
            }
        }
    }
	
	static void err() {
		System.out.println("[CPU] Error was happened!");
	}
	
	static void log(Integer id, String mes) {
		System.out.println("[CPU][ID:" + id + "] " + mes);
	}
}
