package com.f3rullo14.cloud.forums;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

public class ForumObject {
   public UUID forumID = UUID.randomUUID();
   public ArrayList forumCategories = new ArrayList();
   public String forumTitle = "Official CounterCraft Forums";

   public void addCategory(CategoryObject givenCategoryObject) {
      this.forumCategories.add(givenCategoryObject);
   }

   public ArrayList getForumCategories() {
      return this.forumCategories;
   }

   public CategoryObject getCategoryFromUUID(UUID givenUUID) {
      Iterator i$ = this.forumCategories.iterator();

      CategoryObject categoryObject;
      do {
         if (!i$.hasNext()) {
            return null;
         }

         categoryObject = (CategoryObject)i$.next();
      } while(!categoryObject.categoryID.equals(givenUUID));

      return categoryObject;
   }
}
