package net.minecraft.client.gui.screen;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.IPackNameDecorator;
import net.minecraft.resources.PackCompatibility;
import net.minecraft.resources.ResourcePackInfo;
import net.minecraft.resources.ResourcePackList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.ITextProperties;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PackLoadingManager<T extends ResourcePackInfo> {
   private final ResourcePackList<T> field_241617_a_;
   private final List<T> field_238860_a_;
   private final List<T> field_238861_b_;
   private final BiConsumer<T, TextureManager> field_238862_c_;
   private final Runnable field_238863_d_;
   private final Consumer<ResourcePackList<T>> field_238864_e_;

   public PackLoadingManager(Runnable p_i241253_1_, BiConsumer<T, TextureManager> p_i241253_2_, ResourcePackList<T> p_i241253_3_, Consumer<ResourcePackList<T>> p_i241253_4_) {
      this.field_238863_d_ = p_i241253_1_;
      this.field_238862_c_ = p_i241253_2_;
      this.field_241617_a_ = p_i241253_3_;
      this.field_238860_a_ = Lists.newArrayList(p_i241253_3_.getEnabledPacks());
      Collections.reverse(this.field_238860_a_);
      this.field_238861_b_ = Lists.newArrayList(p_i241253_3_.getAllPacks());
      this.field_238861_b_.removeAll(this.field_238860_a_);
      this.field_238864_e_ = p_i241253_4_;
   }

   public Stream<PackLoadingManager.IPack> func_238865_a_() {
      return this.field_238861_b_.stream().map((p_238870_1_) -> {
         return new PackLoadingManager.DisabledPack(p_238870_1_);
      });
   }

   public Stream<PackLoadingManager.IPack> func_238869_b_() {
      return this.field_238860_a_.stream().map((p_238866_1_) -> {
         return new PackLoadingManager.EnabledPack(p_238866_1_);
      });
   }

   public void func_241618_c_() {
      this.field_241617_a_.setEnabledPacks(Lists.reverse(this.field_238860_a_).stream().map(ResourcePackInfo::getName).collect(ImmutableList.toImmutableList()));
      this.field_238864_e_.accept(this.field_241617_a_);
   }

   public void func_241619_d_() {
      this.field_241617_a_.reloadPacksFromFinders();
      this.field_238861_b_.clear();
      this.field_238861_b_.addAll(this.field_241617_a_.getAllPacks());
      this.field_238861_b_.removeAll(this.field_238860_a_);
   }

   @OnlyIn(Dist.CLIENT)
   abstract class AbstractPack implements PackLoadingManager.IPack {
      private final T field_238878_b_;

      public AbstractPack(T p_i232297_2_) {
         this.field_238878_b_ = p_i232297_2_;
      }

      protected abstract List<T> func_230474_q_();

      protected abstract List<T> func_230475_r_();

      public void func_230461_a_(TextureManager p_230461_1_) {
         PackLoadingManager.this.field_238862_c_.accept(this.field_238878_b_, p_230461_1_);
      }

      public PackCompatibility func_230460_a_() {
         return this.field_238878_b_.getCompatibility();
      }

      public ITextComponent func_230462_b_() {
         return this.field_238878_b_.getTitle();
      }

      public ITextComponent func_230463_c_() {
         return this.field_238878_b_.getDescription();
      }

      public IPackNameDecorator func_230464_d_() {
         return this.field_238878_b_.func_232614_i_();
      }

      public boolean func_230465_f_() {
         return this.field_238878_b_.isOrderLocked();
      }

      public boolean func_230466_g_() {
         return this.field_238878_b_.isAlwaysEnabled();
      }

      protected void func_238880_s_() {
         this.func_230474_q_().remove(this.field_238878_b_);
         this.field_238878_b_.getPriority().insert(this.func_230475_r_(), this.field_238878_b_, Function.identity(), true);
         PackLoadingManager.this.field_238863_d_.run();
      }

      protected void func_238879_a_(int p_238879_1_) {
         List<T> list = this.func_230474_q_();
         int i = list.indexOf(this.field_238878_b_);
         list.remove(i);
         list.add(i + p_238879_1_, this.field_238878_b_);
         PackLoadingManager.this.field_238863_d_.run();
      }

      public boolean func_230469_o_() {
         List<T> list = this.func_230474_q_();
         int i = list.indexOf(this.field_238878_b_);
         return i > 0 && !list.get(i - 1).isOrderLocked();
      }

      public void func_230467_j_() {
         this.func_238879_a_(-1);
      }

      public boolean func_230470_p_() {
         List<T> list = this.func_230474_q_();
         int i = list.indexOf(this.field_238878_b_);
         return i >= 0 && i < list.size() - 1 && !list.get(i + 1).isOrderLocked();
      }

      public void func_230468_k_() {
         this.func_238879_a_(1);
      }

      @Override
      public boolean notHidden() {
          return !field_238878_b_.isHidden();
      }
   }

   @OnlyIn(Dist.CLIENT)
   class DisabledPack extends PackLoadingManager<T>.AbstractPack {
      public DisabledPack(T p_i232299_2_) {
         super(p_i232299_2_);
      }

      protected List<T> func_230474_q_() {
         return PackLoadingManager.this.field_238861_b_;
      }

      protected List<T> func_230475_r_() {
         return PackLoadingManager.this.field_238860_a_;
      }

      public boolean func_230473_l_() {
         return false;
      }

      public void func_230471_h_() {
         this.func_238880_s_();
      }

      public void func_230472_i_() {
      }
   }

   @OnlyIn(Dist.CLIENT)
   class EnabledPack extends PackLoadingManager<T>.AbstractPack {
      public EnabledPack(T p_i232298_2_) {
         super(p_i232298_2_);
      }

      protected List<T> func_230474_q_() {
         return PackLoadingManager.this.field_238860_a_;
      }

      protected List<T> func_230475_r_() {
         return PackLoadingManager.this.field_238861_b_;
      }

      public boolean func_230473_l_() {
         return true;
      }

      public void func_230471_h_() {
      }

      public void func_230472_i_() {
         this.func_238880_s_();
      }
   }

   @OnlyIn(Dist.CLIENT)
   public interface IPack {
      void func_230461_a_(TextureManager p_230461_1_);

      PackCompatibility func_230460_a_();

      ITextComponent func_230462_b_();

      ITextComponent func_230463_c_();

      IPackNameDecorator func_230464_d_();

      default ITextProperties func_238874_e_() {
         return this.func_230464_d_().decorate(this.func_230463_c_());
      }

      boolean func_230465_f_();

      boolean func_230466_g_();

      void func_230471_h_();

      void func_230472_i_();

      void func_230467_j_();

      void func_230468_k_();

      boolean func_230473_l_();

      default boolean func_238875_m_() {
         return !this.func_230473_l_();
      }

      default boolean func_238876_n_() {
         return this.func_230473_l_() && !this.func_230466_g_();
      }

      boolean func_230469_o_();

      boolean func_230470_p_();

      default boolean notHidden() { return true; }
   }
}