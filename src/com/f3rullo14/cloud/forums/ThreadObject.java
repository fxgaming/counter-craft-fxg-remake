package com.f3rullo14.cloud.forums;

import java.util.ArrayList;
import java.util.UUID;

public class ThreadObject {
   public String threadID;
   public String threadName;
   public ArrayList threadContents;
   public CategoryObject parentCategory;

   public ThreadObject(String givenThreadName, ArrayList givenContents, CategoryObject givenParentCategory) {
      this.parentCategory = givenParentCategory;
      this.threadID = UUID.randomUUID().toString();
      this.threadName = givenThreadName;
      this.threadContents = new ArrayList();
      this.populateThreadContents(givenContents);
   }

   public void populateThreadContents(ArrayList givenContents) {
      this.threadContents = givenContents;
   }
}
