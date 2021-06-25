package com.f3rullo14.cloud.forums;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Iterator;
import java.util.UUID;

public class ForumsManager {
   public static ForumObject mainForums = new ForumObject();
   public static File forumsFile;
   public static Gson gson = (new GsonBuilder()).setPrettyPrinting().create();

   public static void init() throws IOException {
      forumsFile = new File("forumsobject.json");
      if (!forumsFile.exists()) {
         System.out.println("Creating first Forums object!");
         forumsFile.createNewFile();
         createForumsData();
      } else {
         loadForumsData();
      }

      saveForumsData();
   }

   public static ForumObject getMainForums() {
      return mainForums;
   }

   public static void saveForumsData() throws IOException {
      System.out.println("Saving all forums data...");
      Writer writer = new FileWriter(forumsFile);
      writer.write(gson.toJson((Object)mainForums));
      writer.close();
      System.out.println("Data saved!");
   }

   public static void loadForumsData() throws IOException {
      if (forumsFile.exists()) {
         System.out.println("Loading forums data...");
         BufferedReader br = new BufferedReader(new FileReader(forumsFile));
         mainForums = (ForumObject)gson.fromJson((Reader)br, (Class)ForumObject.class);
         br.close();
         System.out.println("Forums data loaded successfully!");
      }

   }

   public static void createForumsData() throws IOException {
      mainForums = new ForumObject();
      createNewCategory(new CategoryObject("General Discussion"));
      createNewCategory(new CategoryObject("Server Discussion"));
      createNewCategory(new CategoryObject("Mod Discussion"));
   }

   public static void createNewCategory(CategoryObject givenCategoryToCreate) {
      getMainForums().forumCategories.add(givenCategoryToCreate);
   }

   public static void createNewThread(ThreadObject givenThreadToCreate, UUID givenCategoryID) {
      if (getCategoryFromID(givenCategoryID) != null) {
         getCategoryFromID(givenCategoryID).addThread(givenThreadToCreate);
      }

   }

   public static CategoryObject getCategoryFromID(UUID givenUUID) {
      Iterator i$ = mainForums.forumCategories.iterator();

      CategoryObject categoryObject;
      do {
         if (!i$.hasNext()) {
            return null;
         }

         categoryObject = (CategoryObject)i$.next();
      } while(categoryObject.categoryID != givenUUID);

      return categoryObject;
   }
}
