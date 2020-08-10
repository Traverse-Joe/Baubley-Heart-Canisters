package net.minecraft.world;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.io.File;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.function.Supplier;
import net.minecraft.block.Block;
import net.minecraft.server.IDynamicRegistries;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.datafix.codec.RangeCodec;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKeyCodec;
import net.minecraft.util.registry.SimpleRegistry;
import net.minecraft.world.biome.ColumnFuzzedBiomeMagnifier;
import net.minecraft.world.biome.FuzzedBiomeMagnifier;
import net.minecraft.world.biome.IBiomeMagnifier;
import net.minecraft.world.biome.provider.EndBiomeProvider;
import net.minecraft.world.biome.provider.NetherBiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.DimensionSettings;
import net.minecraft.world.gen.NoiseChunkGenerator;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class DimensionType {
   public static final MapCodec<DimensionType> field_235997_a_ = RecordCodecBuilder.mapCodec((p_236026_0_) -> {
      return p_236026_0_.group(Codec.LONG.optionalFieldOf("fixed_time").xmap((p_236028_0_) -> {
         return p_236028_0_.map(OptionalLong::of).orElseGet(OptionalLong::empty);
      }, (p_236029_0_) -> {
         return p_236029_0_.isPresent() ? Optional.of(p_236029_0_.getAsLong()) : Optional.empty();
      }).forGetter((p_236044_0_) -> {
         return p_236044_0_.field_236010_o_;
      }), Codec.BOOL.fieldOf("has_skylight").forGetter(DimensionType::hasSkyLight), Codec.BOOL.fieldOf("has_ceiling").forGetter(DimensionType::func_236037_d_), Codec.BOOL.fieldOf("ultrawarm").forGetter(DimensionType::func_236040_e_), Codec.BOOL.fieldOf("natural").forGetter(DimensionType::func_236043_f_), Codec.BOOL.fieldOf("shrunk").forGetter(DimensionType::func_236045_g_), Codec.BOOL.fieldOf("piglin_safe").forGetter(DimensionType::func_241509_i_), Codec.BOOL.fieldOf("bed_works").forGetter(DimensionType::func_241510_j_), Codec.BOOL.fieldOf("respawn_anchor_works").forGetter(DimensionType::func_241511_k_), Codec.BOOL.fieldOf("has_raids").forGetter(DimensionType::func_241512_l_), RangeCodec.func_232989_a_(0, 256).fieldOf("logical_height").forGetter(DimensionType::func_241513_m_), ResourceLocation.field_240908_a_.fieldOf("infiniburn").forGetter((p_241508_0_) -> {
         return p_241508_0_.field_241504_y_;
      }), Codec.FLOAT.fieldOf("ambient_light").forGetter((p_236042_0_) -> {
         return p_236042_0_.field_236017_x_;
      })).apply(p_236026_0_, DimensionType::new);
   });
   public static final float[] field_235998_b_ = new float[]{1.0F, 0.75F, 0.5F, 0.25F, 0.0F, 0.25F, 0.5F, 0.75F};
   public static final RegistryKey<DimensionType> field_235999_c_ = RegistryKey.func_240903_a_(Registry.field_239698_ad_, new ResourceLocation("overworld"));
   public static final RegistryKey<DimensionType> field_236000_d_ = RegistryKey.func_240903_a_(Registry.field_239698_ad_, new ResourceLocation("the_nether"));
   public static final RegistryKey<DimensionType> field_236001_e_ = RegistryKey.func_240903_a_(Registry.field_239698_ad_, new ResourceLocation("the_end"));
   protected static final DimensionType field_236004_h_ = new DimensionType(OptionalLong.empty(), true, false, false, true, false, false, false, true, false, true, 256, ColumnFuzzedBiomeMagnifier.INSTANCE, BlockTags.field_241277_aC_.func_230234_a_(), 0.0F);
   protected static final DimensionType field_236005_i_ = new DimensionType(OptionalLong.of(18000L), false, true, true, false, true, false, true, false, true, false, 128, FuzzedBiomeMagnifier.INSTANCE, BlockTags.field_241278_aD_.func_230234_a_(), 0.1F);
   protected static final DimensionType field_236006_j_ = new DimensionType(OptionalLong.of(6000L), false, false, false, false, false, true, false, false, false, true, 256, FuzzedBiomeMagnifier.INSTANCE, BlockTags.field_241279_aE_.func_230234_a_(), 0.0F);
   public static final RegistryKey<DimensionType> field_241497_i_ = RegistryKey.func_240903_a_(Registry.field_239698_ad_, new ResourceLocation("overworld_caves"));
   protected static final DimensionType field_241498_j_ = new DimensionType(OptionalLong.empty(), true, true, false, true, false, false, false, true, false, true, 256, ColumnFuzzedBiomeMagnifier.INSTANCE, BlockTags.field_241277_aC_.func_230234_a_(), 0.0F);
   public static final Codec<Supplier<DimensionType>> field_236002_f_ = RegistryKeyCodec.func_241794_a_(Registry.field_239698_ad_, field_235997_a_);
   private final OptionalLong field_236010_o_;
   private final boolean hasSkyLight;
   private final boolean field_236011_q_;
   private final boolean field_236012_r_;
   private final boolean field_236013_s_;
   private final boolean field_236014_t_;
   private final boolean field_236015_u_;
   private final boolean field_241499_s_;
   private final boolean field_241500_t_;
   private final boolean field_241501_u_;
   private final boolean field_241502_v_;
   private final int field_241503_w_;
   private final IBiomeMagnifier magnifier;
   private final ResourceLocation field_241504_y_;
   private final float field_236017_x_;
   private final transient float[] field_236018_y_;

   public static DimensionType func_236019_a_() {
      return field_236004_h_;
   }

   @OnlyIn(Dist.CLIENT)
   public static DimensionType func_241507_b_() {
      return field_241498_j_;
   }

   protected DimensionType(OptionalLong p_i241242_1_, boolean p_i241242_2_, boolean p_i241242_3_, boolean p_i241242_4_, boolean p_i241242_5_, boolean p_i241242_6_, boolean p_i241242_7_, boolean p_i241242_8_, boolean p_i241242_9_, boolean p_i241242_10_, int p_i241242_11_, ResourceLocation p_i241242_12_, float p_i241242_13_) {
      this(p_i241242_1_, p_i241242_2_, p_i241242_3_, p_i241242_4_, p_i241242_5_, p_i241242_6_, false, p_i241242_7_, p_i241242_8_, p_i241242_9_, p_i241242_10_, p_i241242_11_, FuzzedBiomeMagnifier.INSTANCE, p_i241242_12_, p_i241242_13_);
   }

   protected DimensionType(OptionalLong p_i241243_1_, boolean p_i241243_2_, boolean p_i241243_3_, boolean p_i241243_4_, boolean p_i241243_5_, boolean p_i241243_6_, boolean p_i241243_7_, boolean p_i241243_8_, boolean p_i241243_9_, boolean p_i241243_10_, boolean p_i241243_11_, int p_i241243_12_, IBiomeMagnifier p_i241243_13_, ResourceLocation p_i241243_14_, float p_i241243_15_) {
      this.field_236010_o_ = p_i241243_1_;
      this.hasSkyLight = p_i241243_2_;
      this.field_236011_q_ = p_i241243_3_;
      this.field_236012_r_ = p_i241243_4_;
      this.field_236013_s_ = p_i241243_5_;
      this.field_236014_t_ = p_i241243_6_;
      this.field_236015_u_ = p_i241243_7_;
      this.field_241499_s_ = p_i241243_8_;
      this.field_241500_t_ = p_i241243_9_;
      this.field_241501_u_ = p_i241243_10_;
      this.field_241502_v_ = p_i241243_11_;
      this.field_241503_w_ = p_i241243_12_;
      this.magnifier = p_i241243_13_;
      this.field_241504_y_ = p_i241243_14_;
      this.field_236017_x_ = p_i241243_15_;
      this.field_236018_y_ = func_236020_a_(p_i241243_15_);
   }

   private static float[] func_236020_a_(float p_236020_0_) {
      float[] afloat = new float[16];

      for(int i = 0; i <= 15; ++i) {
         float f = (float)i / 15.0F;
         float f1 = f / (4.0F - 3.0F * f);
         afloat[i] = MathHelper.lerp(p_236020_0_, f1, 1.0F);
      }

      return afloat;
   }

   @Deprecated
   public static DataResult<RegistryKey<World>> func_236025_a_(Dynamic<?> p_236025_0_) {
      Optional<Number> optional = p_236025_0_.asNumber().result();
      if (optional.isPresent()) {
         int i = optional.get().intValue();
         if (i == -1) {
            return DataResult.success(World.field_234919_h_);
         }

         if (i == 0) {
            return DataResult.success(World.field_234918_g_);
         }

         if (i == 1) {
            return DataResult.success(World.field_234920_i_);
         }
      }

      return World.field_234917_f_.parse(p_236025_0_);
   }

   public static IDynamicRegistries.Impl func_236027_a_(IDynamicRegistries.Impl p_236027_0_) {
      p_236027_0_.func_239774_a_(field_235999_c_, field_236004_h_);
      p_236027_0_.func_239774_a_(field_241497_i_, field_241498_j_);
      p_236027_0_.func_239774_a_(field_236000_d_, field_236005_i_);
      p_236027_0_.func_239774_a_(field_236001_e_, field_236006_j_);
      return p_236027_0_;
   }

   private static ChunkGenerator func_236038_d_(long p_236038_0_) {
      return new NoiseChunkGenerator(new EndBiomeProvider(p_236038_0_), p_236038_0_, DimensionSettings.Preset.field_236125_e_.func_236137_b_());
   }

   private static ChunkGenerator func_236041_e_(long p_236041_0_) {
      return new NoiseChunkGenerator(NetherBiomeProvider.Preset.field_235288_b_.func_235292_a_(p_236041_0_), p_236041_0_, DimensionSettings.Preset.field_236124_d_.func_236137_b_());
   }

   public static SimpleRegistry<Dimension> func_236022_a_(long p_236022_0_) {
      SimpleRegistry<Dimension> simpleregistry = new SimpleRegistry<>(Registry.field_239700_af_, Lifecycle.experimental());
      simpleregistry.register(Dimension.field_236054_c_, new Dimension(() -> {
         return field_236005_i_;
      }, func_236041_e_(p_236022_0_)));
      simpleregistry.register(Dimension.field_236055_d_, new Dimension(() -> {
         return field_236006_j_;
      }, func_236038_d_(p_236022_0_)));
      simpleregistry.func_239662_d_(Dimension.field_236054_c_);
      simpleregistry.func_239662_d_(Dimension.field_236055_d_);
      return simpleregistry;
   }

   @Deprecated
   public String getSuffix() {
      return this == field_236006_j_ ? "_end" : "";
   }

   public static File func_236031_a_(RegistryKey<World> p_236031_0_, File p_236031_1_) {
      if (p_236031_0_ == World.field_234918_g_) {
         return p_236031_1_;
      } else if (p_236031_0_ == World.field_234920_i_) {
         return new File(p_236031_1_, "DIM1");
      } else {
         return p_236031_0_ == World.field_234919_h_ ? new File(p_236031_1_, "DIM-1") : new File(p_236031_1_, "dimensions/" + p_236031_0_.func_240901_a_().getNamespace() + "/" + p_236031_0_.func_240901_a_().getPath());
      }
   }

   public boolean hasSkyLight() {
      return this.hasSkyLight;
   }

   public boolean func_236037_d_() {
      return this.field_236011_q_;
   }

   public boolean func_236040_e_() {
      return this.field_236012_r_;
   }

   public boolean func_236043_f_() {
      return this.field_236013_s_;
   }

   public boolean func_236045_g_() {
      return this.field_236014_t_;
   }

   public boolean func_241509_i_() {
      return this.field_241499_s_;
   }

   public boolean func_241510_j_() {
      return this.field_241500_t_;
   }

   public boolean func_241511_k_() {
      return this.field_241501_u_;
   }

   public boolean func_241512_l_() {
      return this.field_241502_v_;
   }

   public int func_241513_m_() {
      return this.field_241503_w_;
   }

   public boolean func_236046_h_() {
      return this.field_236015_u_;
   }

   public IBiomeMagnifier getMagnifier() {
      return this.magnifier;
   }

   public boolean func_241514_p_() {
      return this.field_236010_o_.isPresent();
   }

   public float func_236032_b_(long p_236032_1_) {
      double d0 = MathHelper.frac((double)this.field_236010_o_.orElse(p_236032_1_) / 24000.0D - 0.25D);
      double d1 = 0.5D - Math.cos(d0 * Math.PI) / 2.0D;
      return (float)(d0 * 2.0D + d1) / 3.0F;
   }

   public int func_236035_c_(long p_236035_1_) {
      return (int)(p_236035_1_ / 24000L % 8L + 8L) % 8;
   }

   public float func_236021_a_(int p_236021_1_) {
      return this.field_236018_y_[p_236021_1_];
   }

   public ITag<Block> func_241515_q_() {
      ITag<Block> itag = BlockTags.getCollection().get(this.field_241504_y_);
      return (ITag<Block>)(itag != null ? itag : BlockTags.field_241277_aC_);
   }
}