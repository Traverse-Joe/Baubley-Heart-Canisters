package com.mojang.realmsclient.dto;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.realmsclient.util.JsonUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class UploadInfo extends ValueObject {
   private static final Logger field_230638_a_ = LogManager.getLogger();
   private boolean field_230639_b_;
   private String field_230640_c_ = "";
   private String field_230641_d_ = "";
   private int field_230642_e_;

   public static UploadInfo func_230796_a_(String p_230796_0_) {
      UploadInfo uploadinfo = new UploadInfo();

      try {
         JsonParser jsonparser = new JsonParser();
         JsonObject jsonobject = jsonparser.parse(p_230796_0_).getAsJsonObject();
         uploadinfo.field_230639_b_ = JsonUtils.func_225170_a("worldClosed", jsonobject, false);
         uploadinfo.field_230640_c_ = JsonUtils.func_225171_a("token", jsonobject, (String)null);
         uploadinfo.field_230641_d_ = JsonUtils.func_225171_a("uploadEndpoint", jsonobject, (String)null);
         uploadinfo.field_230642_e_ = JsonUtils.func_225172_a("port", jsonobject, 8080);
      } catch (Exception exception) {
         field_230638_a_.error("Could not parse UploadInfo: " + exception.getMessage());
      }

      return uploadinfo;
   }

   public String func_230795_a_() {
      return this.field_230640_c_;
   }

   public String func_230797_b_() {
      return this.field_230641_d_;
   }

   public boolean func_230799_c_() {
      return this.field_230639_b_;
   }

   public void func_230798_b_(String p_230798_1_) {
      this.field_230640_c_ = p_230798_1_;
   }

   public int func_230800_d_() {
      return this.field_230642_e_;
   }
}