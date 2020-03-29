package net.minecraft.client.gui.screen;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.gui.RenderComponentsUtil;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.ChangePageButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.WrittenBookItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ReadBookScreen extends Screen {
   public static final ReadBookScreen.IBookInfo field_214166_a = new ReadBookScreen.IBookInfo() {
      public int func_216918_a() {
         return 0;
      }

      public ITextComponent func_216915_a(int p_216915_1_) {
         return new StringTextComponent("");
      }
   };
   public static final ResourceLocation field_214167_b = new ResourceLocation("textures/gui/book.png");
   private ReadBookScreen.IBookInfo field_214168_c;
   private int field_214169_d;
   private List<ITextComponent> field_214170_e = Collections.emptyList();
   private int field_214171_f = -1;
   private ChangePageButton field_214172_g;
   private ChangePageButton field_214173_h;
   private final boolean field_214174_i;

   public ReadBookScreen(ReadBookScreen.IBookInfo p_i51098_1_) {
      this(p_i51098_1_, true);
   }

   public ReadBookScreen() {
      this(field_214166_a, false);
   }

   private ReadBookScreen(ReadBookScreen.IBookInfo p_i51099_1_, boolean p_i51099_2_) {
      super(NarratorChatListener.field_216868_a);
      this.field_214168_c = p_i51099_1_;
      this.field_214174_i = p_i51099_2_;
   }

   public void func_214155_a(ReadBookScreen.IBookInfo p_214155_1_) {
      this.field_214168_c = p_214155_1_;
      this.field_214169_d = MathHelper.clamp(this.field_214169_d, 0, p_214155_1_.func_216918_a());
      this.func_214151_f();
      this.field_214171_f = -1;
   }

   public boolean func_214160_a(int p_214160_1_) {
      int i = MathHelper.clamp(p_214160_1_, 0, this.field_214168_c.func_216918_a() - 1);
      if (i != this.field_214169_d) {
         this.field_214169_d = i;
         this.func_214151_f();
         this.field_214171_f = -1;
         return true;
      } else {
         return false;
      }
   }

   protected boolean func_214153_b(int p_214153_1_) {
      return this.func_214160_a(p_214153_1_);
   }

   protected void init() {
      this.func_214162_b();
      this.func_214164_c();
   }

   protected void func_214162_b() {
      this.addButton(new Button(this.width / 2 - 100, 196, 200, 20, I18n.format("gui.done"), (p_214161_1_) -> {
         this.minecraft.displayGuiScreen((Screen)null);
      }));
   }

   protected void func_214164_c() {
      int i = (this.width - 192) / 2;
      int j = 2;
      this.field_214172_g = this.addButton(new ChangePageButton(i + 116, 159, true, (p_214159_1_) -> {
         this.func_214163_e();
      }, this.field_214174_i));
      this.field_214173_h = this.addButton(new ChangePageButton(i + 43, 159, false, (p_214158_1_) -> {
         this.func_214165_d();
      }, this.field_214174_i));
      this.func_214151_f();
   }

   private int func_214152_a() {
      return this.field_214168_c.func_216918_a();
   }

   protected void func_214165_d() {
      if (this.field_214169_d > 0) {
         --this.field_214169_d;
      }

      this.func_214151_f();
   }

   protected void func_214163_e() {
      if (this.field_214169_d < this.func_214152_a() - 1) {
         ++this.field_214169_d;
      }

      this.func_214151_f();
   }

   private void func_214151_f() {
      this.field_214172_g.visible = this.field_214169_d < this.func_214152_a() - 1;
      this.field_214173_h.visible = this.field_214169_d > 0;
   }

   public boolean keyPressed(int p_keyPressed_1_, int p_keyPressed_2_, int p_keyPressed_3_) {
      if (super.keyPressed(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_)) {
         return true;
      } else {
         switch(p_keyPressed_1_) {
         case 266:
            this.field_214173_h.onPress();
            return true;
         case 267:
            this.field_214172_g.onPress();
            return true;
         default:
            return false;
         }
      }
   }

   public void render(int p_render_1_, int p_render_2_, float p_render_3_) {
      this.renderBackground();
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.minecraft.getTextureManager().bindTexture(field_214167_b);
      int i = (this.width - 192) / 2;
      int j = 2;
      this.blit(i, 2, 0, 0, 192, 192);
      String s = I18n.format("book.pageIndicator", this.field_214169_d + 1, Math.max(this.func_214152_a(), 1));
      if (this.field_214171_f != this.field_214169_d) {
         ITextComponent itextcomponent = this.field_214168_c.func_216916_b(this.field_214169_d);
         this.field_214170_e = RenderComponentsUtil.splitText(itextcomponent, 114, this.font, true, true);
      }

      this.field_214171_f = this.field_214169_d;
      int i1 = this.func_214156_a(s);
      this.font.drawString(s, (float)(i - i1 + 192 - 44), 18.0F, 0);
      int k = Math.min(128 / 9, this.field_214170_e.size());

      for(int l = 0; l < k; ++l) {
         ITextComponent itextcomponent1 = this.field_214170_e.get(l);
         this.font.drawString(itextcomponent1.getFormattedText(), (float)(i + 36), (float)(32 + l * 9), 0);
      }

      ITextComponent itextcomponent2 = this.func_214154_c((double)p_render_1_, (double)p_render_2_);
      if (itextcomponent2 != null) {
         this.renderComponentHoverEffect(itextcomponent2, p_render_1_, p_render_2_);
      }

      super.render(p_render_1_, p_render_2_, p_render_3_);
   }

   private int func_214156_a(String p_214156_1_) {
      return this.font.getStringWidth(this.font.getBidiFlag() ? this.font.bidiReorder(p_214156_1_) : p_214156_1_);
   }

   public boolean mouseClicked(double p_mouseClicked_1_, double p_mouseClicked_3_, int p_mouseClicked_5_) {
      if (p_mouseClicked_5_ == 0) {
         ITextComponent itextcomponent = this.func_214154_c(p_mouseClicked_1_, p_mouseClicked_3_);
         if (itextcomponent != null && this.handleComponentClicked(itextcomponent)) {
            return true;
         }
      }

      return super.mouseClicked(p_mouseClicked_1_, p_mouseClicked_3_, p_mouseClicked_5_);
   }

   public boolean handleComponentClicked(ITextComponent p_handleComponentClicked_1_) {
      ClickEvent clickevent = p_handleComponentClicked_1_.getStyle().getClickEvent();
      if (clickevent == null) {
         return false;
      } else if (clickevent.getAction() == ClickEvent.Action.CHANGE_PAGE) {
         String s = clickevent.getValue();

         try {
            int i = Integer.parseInt(s) - 1;
            return this.func_214153_b(i);
         } catch (Exception var5) {
            return false;
         }
      } else {
         boolean flag = super.handleComponentClicked(p_handleComponentClicked_1_);
         if (flag && clickevent.getAction() == ClickEvent.Action.RUN_COMMAND) {
            this.minecraft.displayGuiScreen((Screen)null);
         }

         return flag;
      }
   }

   @Nullable
   public ITextComponent func_214154_c(double p_214154_1_, double p_214154_3_) {
      if (this.field_214170_e == null) {
         return null;
      } else {
         int i = MathHelper.floor(p_214154_1_ - (double)((this.width - 192) / 2) - 36.0D);
         int j = MathHelper.floor(p_214154_3_ - 2.0D - 30.0D);
         if (i >= 0 && j >= 0) {
            int k = Math.min(128 / 9, this.field_214170_e.size());
            if (i <= 114 && j < 9 * k + k) {
               int l = j / 9;
               if (l >= 0 && l < this.field_214170_e.size()) {
                  ITextComponent itextcomponent = this.field_214170_e.get(l);
                  int i1 = 0;

                  for(ITextComponent itextcomponent1 : itextcomponent) {
                     if (itextcomponent1 instanceof StringTextComponent) {
                        i1 += this.minecraft.fontRenderer.getStringWidth(itextcomponent1.getFormattedText());
                        if (i1 > i) {
                           return itextcomponent1;
                        }
                     }
                  }
               }

               return null;
            } else {
               return null;
            }
         } else {
            return null;
         }
      }
   }

   public static List<String> func_214157_a(CompoundNBT p_214157_0_) {
      ListNBT listnbt = p_214157_0_.getList("pages", 8).copy();
      Builder<String> builder = ImmutableList.builder();

      for(int i = 0; i < listnbt.size(); ++i) {
         builder.add(listnbt.getString(i));
      }

      return builder.build();
   }

   @OnlyIn(Dist.CLIENT)
   public interface IBookInfo {
      int func_216918_a();

      ITextComponent func_216915_a(int p_216915_1_);

      default ITextComponent func_216916_b(int p_216916_1_) {
         return (ITextComponent)(p_216916_1_ >= 0 && p_216916_1_ < this.func_216918_a() ? this.func_216915_a(p_216916_1_) : new StringTextComponent(""));
      }

      static ReadBookScreen.IBookInfo func_216917_a(ItemStack p_216917_0_) {
         Item item = p_216917_0_.getItem();
         if (item == Items.WRITTEN_BOOK) {
            return new ReadBookScreen.WrittenBookInfo(p_216917_0_);
         } else {
            return (ReadBookScreen.IBookInfo)(item == Items.WRITABLE_BOOK ? new ReadBookScreen.UnwrittenBookInfo(p_216917_0_) : ReadBookScreen.field_214166_a);
         }
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static class UnwrittenBookInfo implements ReadBookScreen.IBookInfo {
      private final List<String> field_216920_a;

      public UnwrittenBookInfo(ItemStack p_i50617_1_) {
         this.field_216920_a = func_216919_b(p_i50617_1_);
      }

      private static List<String> func_216919_b(ItemStack p_216919_0_) {
         CompoundNBT compoundnbt = p_216919_0_.getTag();
         return (List<String>)(compoundnbt != null ? ReadBookScreen.func_214157_a(compoundnbt) : ImmutableList.of());
      }

      public int func_216918_a() {
         return this.field_216920_a.size();
      }

      public ITextComponent func_216915_a(int p_216915_1_) {
         return new StringTextComponent(this.field_216920_a.get(p_216915_1_));
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static class WrittenBookInfo implements ReadBookScreen.IBookInfo {
      private final List<String> field_216922_a;

      public WrittenBookInfo(ItemStack p_i50616_1_) {
         this.field_216922_a = func_216921_b(p_i50616_1_);
      }

      private static List<String> func_216921_b(ItemStack p_216921_0_) {
         CompoundNBT compoundnbt = p_216921_0_.getTag();
         return (List<String>)(compoundnbt != null && WrittenBookItem.validBookTagContents(compoundnbt) ? ReadBookScreen.func_214157_a(compoundnbt) : ImmutableList.of((new TranslationTextComponent("book.invalid.tag")).applyTextStyle(TextFormatting.DARK_RED).getFormattedText()));
      }

      public int func_216918_a() {
         return this.field_216922_a.size();
      }

      public ITextComponent func_216915_a(int p_216915_1_) {
         String s = this.field_216922_a.get(p_216915_1_);

         try {
            ITextComponent itextcomponent = ITextComponent.Serializer.fromJson(s);
            if (itextcomponent != null) {
               return itextcomponent;
            }
         } catch (Exception var4) {
            ;
         }

         return new StringTextComponent(s);
      }
   }
}