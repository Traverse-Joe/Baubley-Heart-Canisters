package net.minecraft.client.renderer;

import com.google.common.collect.ImmutableList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.block.WoodType;
import net.minecraft.client.renderer.model.Material;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.item.DyeColor;
import net.minecraft.state.properties.ChestType;
import net.minecraft.tileentity.BannerPattern;
import net.minecraft.tileentity.EnderChestTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TrappedChestTileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class Atlases {
   public static final ResourceLocation field_228742_a_ = new ResourceLocation("textures/atlas/shulker_boxes.png");
   public static final ResourceLocation field_228743_b_ = new ResourceLocation("textures/atlas/beds.png");
   public static final ResourceLocation field_228744_c_ = new ResourceLocation("textures/atlas/banner_patterns.png");
   public static final ResourceLocation field_228745_d_ = new ResourceLocation("textures/atlas/shield_patterns.png");
   public static final ResourceLocation field_228746_e_ = new ResourceLocation("textures/atlas/signs.png");
   public static final ResourceLocation field_228747_f_ = new ResourceLocation("textures/atlas/chest.png");
   private static final RenderType field_228762_u_ = RenderType.func_228640_c_(field_228742_a_);
   private static final RenderType field_228763_v_ = RenderType.func_228634_a_(field_228743_b_);
   private static final RenderType field_228764_w_ = RenderType.func_228650_h_(field_228744_c_);
   private static final RenderType field_228765_x_ = RenderType.func_228650_h_(field_228745_d_);
   private static final RenderType field_228766_y_ = RenderType.func_228640_c_(field_228746_e_);
   private static final RenderType field_228767_z_ = RenderType.func_228638_b_(field_228747_f_);
   private static final RenderType field_228738_A_ = RenderType.func_228634_a_(AtlasTexture.LOCATION_BLOCKS_TEXTURE);
   private static final RenderType field_228739_B_ = RenderType.func_228638_b_(AtlasTexture.LOCATION_BLOCKS_TEXTURE);
   private static final RenderType field_228740_C_ = RenderType.func_228644_e_(AtlasTexture.LOCATION_BLOCKS_TEXTURE);
   private static final RenderType field_228741_D_ = RenderType.func_228642_d_(AtlasTexture.LOCATION_BLOCKS_TEXTURE);
   public static final Material field_228748_g_ = new Material(field_228742_a_, new ResourceLocation("entity/shulker/shulker"));
   public static final List<Material> field_228749_h_ = Stream.of("white", "orange", "magenta", "light_blue", "yellow", "lime", "pink", "gray", "light_gray", "cyan", "purple", "blue", "brown", "green", "red", "black").map((p_228777_0_) -> {
      return new Material(field_228742_a_, new ResourceLocation("entity/shulker/shulker_" + p_228777_0_));
   }).collect(ImmutableList.toImmutableList());
   public static final Map<WoodType, Material> field_228750_i_ = WoodType.func_227046_a_().collect(Collectors.toMap(Function.identity(), Atlases::func_228773_a_));
   public static final Material[] field_228751_j_ = Arrays.stream(DyeColor.values()).sorted(Comparator.comparingInt(DyeColor::getId)).map((p_228770_0_) -> {
      return new Material(field_228743_b_, new ResourceLocation("entity/bed/" + p_228770_0_.getTranslationKey()));
   }).toArray((p_228769_0_) -> {
      return new Material[p_228769_0_];
   });
   public static final Material field_228752_k_ = func_228774_a_("trapped");
   public static final Material field_228753_l_ = func_228774_a_("trapped_left");
   public static final Material field_228754_m_ = func_228774_a_("trapped_right");
   public static final Material field_228755_n_ = func_228774_a_("christmas");
   public static final Material field_228756_o_ = func_228774_a_("christmas_left");
   public static final Material field_228757_p_ = func_228774_a_("christmas_right");
   public static final Material field_228758_q_ = func_228774_a_("normal");
   public static final Material field_228759_r_ = func_228774_a_("normal_left");
   public static final Material field_228760_s_ = func_228774_a_("normal_right");
   public static final Material field_228761_t_ = func_228774_a_("ender");

   public static RenderType func_228768_a_() {
      return field_228764_w_;
   }

   public static RenderType func_228776_b_() {
      return field_228765_x_;
   }

   public static RenderType func_228778_c_() {
      return field_228763_v_;
   }

   public static RenderType func_228779_d_() {
      return field_228762_u_;
   }

   public static RenderType func_228780_e_() {
      return field_228766_y_;
   }

   public static RenderType func_228781_f_() {
      return field_228767_z_;
   }

   public static RenderType func_228782_g_() {
      return field_228738_A_;
   }

   public static RenderType func_228783_h_() {
      return field_228739_B_;
   }

   public static RenderType func_228784_i_() {
      return field_228740_C_;
   }

   public static RenderType func_228785_j_() {
      return field_228741_D_;
   }

   public static void func_228775_a_(Consumer<Material> p_228775_0_) {
      p_228775_0_.accept(field_228748_g_);
      field_228749_h_.forEach(p_228775_0_);

      for(BannerPattern bannerpattern : BannerPattern.values()) {
         p_228775_0_.accept(new Material(field_228744_c_, bannerpattern.func_226957_a_(true)));
         p_228775_0_.accept(new Material(field_228745_d_, bannerpattern.func_226957_a_(false)));
      }

      field_228750_i_.values().forEach(p_228775_0_);

      for(Material material : field_228751_j_) {
         p_228775_0_.accept(material);
      }

      p_228775_0_.accept(field_228752_k_);
      p_228775_0_.accept(field_228753_l_);
      p_228775_0_.accept(field_228754_m_);
      p_228775_0_.accept(field_228755_n_);
      p_228775_0_.accept(field_228756_o_);
      p_228775_0_.accept(field_228757_p_);
      p_228775_0_.accept(field_228758_q_);
      p_228775_0_.accept(field_228759_r_);
      p_228775_0_.accept(field_228760_s_);
      p_228775_0_.accept(field_228761_t_);
   }

   public static Material func_228773_a_(WoodType p_228773_0_) {
      return new Material(field_228746_e_, new ResourceLocation("entity/signs/" + p_228773_0_.func_227048_b_()));
   }

   private static Material func_228774_a_(String p_228774_0_) {
      return new Material(field_228747_f_, new ResourceLocation("entity/chest/" + p_228774_0_));
   }

   public static Material func_228771_a_(TileEntity p_228771_0_, ChestType p_228771_1_, boolean p_228771_2_) {
      if (p_228771_2_) {
         return func_228772_a_(p_228771_1_, field_228755_n_, field_228756_o_, field_228757_p_);
      } else if (p_228771_0_ instanceof TrappedChestTileEntity) {
         return func_228772_a_(p_228771_1_, field_228752_k_, field_228753_l_, field_228754_m_);
      } else {
         return p_228771_0_ instanceof EnderChestTileEntity ? field_228761_t_ : func_228772_a_(p_228771_1_, field_228758_q_, field_228759_r_, field_228760_s_);
      }
   }

   private static Material func_228772_a_(ChestType p_228772_0_, Material p_228772_1_, Material p_228772_2_, Material p_228772_3_) {
      switch(p_228772_0_) {
      case LEFT:
         return p_228772_2_;
      case RIGHT:
         return p_228772_3_;
      case SINGLE:
      default:
         return p_228772_1_;
      }
   }
}