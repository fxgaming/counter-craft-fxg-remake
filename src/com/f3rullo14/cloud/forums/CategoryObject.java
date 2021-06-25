package com.f3rullo14.cloud.forums;

import java.util.ArrayList;
import java.util.UUID;

public class CategoryObject {
   public UUID categoryID = UUID.randomUUID();
   public String categoryName;
   public ArrayList categoryThreads;
   public ForumObject parentForums;

   public CategoryObject(String givenCategoryName) {
      this.categoryName = givenCategoryName;
      this.categoryThreads = new ArrayList();
   }

   public CategoryObject setUUID(UUID givenUUID) {
      this.categoryID = givenUUID;
      return this;
   }

   public void addThread(ThreadObject givenThreadObject) {
      this.categoryThreads.add(givenThreadObject);
   }

   public ForumObject getParentForums() {
      return this.parentForums;
   }
}
