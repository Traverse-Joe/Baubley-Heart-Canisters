package net.minecraft.client.resources;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.ibm.icu.text.ArabicShaping;
import com.ibm.icu.text.ArabicShapingException;
import com.ibm.icu.text.Bidi;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.LanguageMap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class ClientLanguageMap extends LanguageMap {
   private static final Logger field_239493_a_ = LogManager.getLogger();
   private static final Pattern field_239494_b_ = Pattern.compile("%(?:(\\d+)\\$)?([A-Za-z])");
   private final Map<String, String> field_239495_c_;
   private final boolean field_239496_d_;

   private ClientLanguageMap(Map<String, String> p_i232487_1_, boolean p_i232487_2_) {
      this.field_239495_c_ = p_i232487_1_;
      this.field_239496_d_ = p_i232487_2_;
   }

   public static ClientLanguageMap func_239497_a_(IResourceManager p_239497_0_, List<Language> p_239497_1_) {
      Map<String, String> map = Maps.newHashMap();
      boolean flag = false;

      for(Language language : p_239497_1_) {
         flag |= language.isBidirectional();
         String s = String.format("lang/%s.json", language.getCode());

         for(String s1 : p_239497_0_.getResourceNamespaces()) {
            try {
               ResourceLocation resourcelocation = new ResourceLocation(s1, s);
               func_239498_a_(p_239497_0_.getAllResources(resourcelocation), map);
            } catch (FileNotFoundException filenotfoundexception) {
            } catch (Exception exception) {
               field_239493_a_.warn("Skipped language file: {}:{} ({})", s1, s, exception.toString());
            }
         }
      }

      return new ClientLanguageMap(ImmutableMap.copyOf(map), flag);
   }

   private static void func_239498_a_(List<IResource> p_239498_0_, Map<String, String> p_239498_1_) {
      for(IResource iresource : p_239498_0_) {
         try (InputStream inputstream = iresource.getInputStream()) {
            LanguageMap.func_240593_a_(inputstream, p_239498_1_::put);
         } catch (IOException ioexception) {
            field_239493_a_.warn("Failed to load translations from {}", iresource, ioexception);
         }
      }

   }

   public String func_230503_a_(String p_230503_1_) {
      return this.field_239495_c_.getOrDefault(p_230503_1_, p_230503_1_);
   }

   public boolean func_230506_b_(String p_230506_1_) {
      return this.field_239495_c_.containsKey(p_230506_1_);
   }

   public boolean func_230505_b_() {
      return this.field_239496_d_;
   }

   public String func_230504_a_(String p_230504_1_, boolean p_230504_2_) {
      if (!this.field_239496_d_) {
         return p_230504_1_;
      } else {
         if (p_230504_2_ && p_230504_1_.indexOf(37) != -1) {
            p_230504_1_ = func_239499_c_(p_230504_1_);
         }

         return this.func_239500_d_(p_230504_1_);
      }
   }

   public static String func_239499_c_(String p_239499_0_) {
      Matcher matcher = field_239494_b_.matcher(p_239499_0_);
      StringBuffer stringbuffer = new StringBuffer();
      int i = 1;

      while(matcher.find()) {
         String s = matcher.group(1);
         String s1 = s != null ? s : Integer.toString(i++);
         String s2 = matcher.group(2);
         String s3 = Matcher.quoteReplacement("\u2066%" + s1 + "$" + s2 + "\u2069");
         matcher.appendReplacement(stringbuffer, s3);
      }

      matcher.appendTail(stringbuffer);
      return stringbuffer.toString();
   }

   private String func_239500_d_(String p_239500_1_) {
      try {
         Bidi bidi = new Bidi((new ArabicShaping(8)).shape(p_239500_1_), 127);
         bidi.setReorderingMode(0);
         return bidi.writeReordered(10);
      } catch (ArabicShapingException arabicshapingexception) {
         return p_239500_1_;
      }
   }
   
   @Override
   public Map<String, String> getLanguageData() {
      return field_239495_c_;
   }
}