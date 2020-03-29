package net.minecraft.client.renderer;

import com.google.common.collect.ImmutableList;
import it.unimi.dsi.fastutil.Hash.Strategy;
import it.unimi.dsi.fastutil.objects.ObjectOpenCustomHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalDouble;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.tileentity.EndPortalTileEntityRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class RenderType extends RenderState {
   private static final RenderType field_228615_R_ = func_228633_a_("solid", DefaultVertexFormats.BLOCK, 7, 2097152, true, false, RenderType.State.func_228694_a_().func_228723_a_(field_228520_l_).func_228719_a_(field_228528_t_).func_228724_a_(field_228521_m_).func_228728_a_(true));
   private static final RenderType field_228616_S_ = func_228633_a_("cutout_mipped", DefaultVertexFormats.BLOCK, 7, 131072, true, false, RenderType.State.func_228694_a_().func_228723_a_(field_228520_l_).func_228719_a_(field_228528_t_).func_228724_a_(field_228521_m_).func_228713_a_(field_228518_j_).func_228728_a_(true));
   private static final RenderType field_228617_T_ = func_228633_a_("cutout", DefaultVertexFormats.BLOCK, 7, 131072, true, false, RenderType.State.func_228694_a_().func_228723_a_(field_228520_l_).func_228719_a_(field_228528_t_).func_228724_a_(field_228522_n_).func_228713_a_(field_228518_j_).func_228728_a_(true));
   private static final RenderType field_228618_U_ = func_228633_a_("translucent", DefaultVertexFormats.BLOCK, 7, 262144, true, true, func_228666_t_());
   private static final RenderType field_228619_V_ = func_228633_a_("translucent_no_crumbling", DefaultVertexFormats.BLOCK, 7, 262144, false, true, func_228666_t_());
   private static final RenderType field_228620_W_ = func_228632_a_("leash", DefaultVertexFormats.field_227850_m_, 7, 256, RenderType.State.func_228694_a_().func_228724_a_(field_228523_o_).func_228714_a_(field_228491_A_).func_228719_a_(field_228528_t_).func_228728_a_(false));
   private static final RenderType field_228621_X_ = func_228632_a_("water_mask", DefaultVertexFormats.POSITION, 7, 256, RenderType.State.func_228694_a_().func_228724_a_(field_228523_o_).func_228727_a_(field_228497_G_).func_228728_a_(false));
   private static final RenderType field_228622_Y_ = func_228632_a_("glint", DefaultVertexFormats.POSITION_TEX, 7, 256, RenderType.State.func_228694_a_().func_228724_a_(new RenderState.TextureState(ItemRenderer.RES_ITEM_GLINT, true, false)).func_228727_a_(field_228496_F_).func_228714_a_(field_228491_A_).func_228715_a_(field_228493_C_).func_228726_a_(field_228513_e_).func_228725_a_(field_228526_r_).func_228728_a_(false));
   private static final RenderType field_228623_Z_ = func_228632_a_("entity_glint", DefaultVertexFormats.POSITION_TEX, 7, 256, RenderType.State.func_228694_a_().func_228724_a_(new RenderState.TextureState(ItemRenderer.RES_ITEM_GLINT, true, false)).func_228727_a_(field_228496_F_).func_228714_a_(field_228491_A_).func_228715_a_(field_228493_C_).func_228726_a_(field_228513_e_).func_228725_a_(field_228527_s_).func_228728_a_(false));
   private static final RenderType field_228624_aa_ = func_228633_a_("lightning", DefaultVertexFormats.POSITION_COLOR, 7, 256, false, true, RenderType.State.func_228694_a_().func_228727_a_(field_228496_F_).func_228726_a_(field_228512_d_).func_228723_a_(field_228520_l_).func_228728_a_(false));
   public static final RenderType.Type field_228614_Q_ = func_228632_a_("lines", DefaultVertexFormats.POSITION_COLOR, 1, 256, RenderType.State.func_228694_a_().func_228720_a_(new RenderState.LineState(OptionalDouble.empty())).func_228718_a_(field_228500_J_).func_228726_a_(field_228515_g_).func_228727_a_(field_228496_F_).func_228728_a_(false));
   private final VertexFormat field_228625_ab_;
   private final int field_228626_ac_;
   private final int field_228627_ad_;
   private final boolean field_228628_ae_;
   private final boolean field_228629_af_;
   private final Optional<RenderType> field_230166_ag_;

   public static RenderType func_228639_c_() {
      return field_228615_R_;
   }

   public static RenderType func_228641_d_() {
      return field_228616_S_;
   }

   public static RenderType func_228643_e_() {
      return field_228617_T_;
   }

   private static RenderType.State func_228666_t_() {
      return RenderType.State.func_228694_a_().func_228723_a_(field_228520_l_).func_228719_a_(field_228528_t_).func_228724_a_(field_228521_m_).func_228726_a_(field_228515_g_).func_228728_a_(true);
   }

   public static RenderType func_228645_f_() {
      return field_228618_U_;
   }

   public static RenderType func_228647_g_() {
      return field_228619_V_;
   }

   public static RenderType func_228634_a_(ResourceLocation p_228634_0_) {
      RenderType.State rendertype$state = RenderType.State.func_228694_a_().func_228724_a_(new RenderState.TextureState(p_228634_0_, false, false)).func_228726_a_(field_228510_b_).func_228716_a_(field_228532_x_).func_228719_a_(field_228528_t_).func_228722_a_(field_228530_v_).func_228728_a_(true);
      return func_228633_a_("entity_solid", DefaultVertexFormats.field_227849_i_, 7, 256, true, false, rendertype$state);
   }

   public static RenderType func_228638_b_(ResourceLocation p_228638_0_) {
      RenderType.State rendertype$state = RenderType.State.func_228694_a_().func_228724_a_(new RenderState.TextureState(p_228638_0_, false, false)).func_228726_a_(field_228510_b_).func_228716_a_(field_228532_x_).func_228713_a_(field_228517_i_).func_228719_a_(field_228528_t_).func_228722_a_(field_228530_v_).func_228728_a_(true);
      return func_228633_a_("entity_cutout", DefaultVertexFormats.field_227849_i_, 7, 256, true, false, rendertype$state);
   }

   public static RenderType func_230167_a_(ResourceLocation p_230167_0_, boolean p_230167_1_) {
      RenderType.State rendertype$state = RenderType.State.func_228694_a_().func_228724_a_(new RenderState.TextureState(p_230167_0_, false, false)).func_228726_a_(field_228510_b_).func_228716_a_(field_228532_x_).func_228713_a_(field_228517_i_).func_228714_a_(field_228491_A_).func_228719_a_(field_228528_t_).func_228722_a_(field_228530_v_).func_228728_a_(p_230167_1_);
      return func_228633_a_("entity_cutout_no_cull", DefaultVertexFormats.field_227849_i_, 7, 256, true, false, rendertype$state);
   }

   public static RenderType func_228640_c_(ResourceLocation p_228640_0_) {
      return func_230167_a_(p_228640_0_, true);
   }

   public static RenderType func_228642_d_(ResourceLocation p_228642_0_) {
      RenderType.State rendertype$state = RenderType.State.func_228694_a_().func_228724_a_(new RenderState.TextureState(p_228642_0_, false, false)).func_228726_a_(field_228515_g_).func_228716_a_(field_228532_x_).func_228713_a_(field_228517_i_).func_228719_a_(field_228528_t_).func_228722_a_(field_228530_v_).func_228728_a_(true);
      return func_228633_a_("entity_translucent_cull", DefaultVertexFormats.field_227849_i_, 7, 256, true, true, rendertype$state);
   }

   public static RenderType func_230168_b_(ResourceLocation p_230168_0_, boolean p_230168_1_) {
      RenderType.State rendertype$state = RenderType.State.func_228694_a_().func_228724_a_(new RenderState.TextureState(p_230168_0_, false, false)).func_228726_a_(field_228515_g_).func_228716_a_(field_228532_x_).func_228713_a_(field_228517_i_).func_228714_a_(field_228491_A_).func_228719_a_(field_228528_t_).func_228722_a_(field_228530_v_).func_228728_a_(p_230168_1_);
      return func_228633_a_("entity_translucent", DefaultVertexFormats.field_227849_i_, 7, 256, true, true, rendertype$state);
   }

   public static RenderType func_228644_e_(ResourceLocation p_228644_0_) {
      return func_230168_b_(p_228644_0_, true);
   }

   public static RenderType func_228646_f_(ResourceLocation p_228646_0_) {
      RenderType.State rendertype$state = RenderType.State.func_228694_a_().func_228724_a_(new RenderState.TextureState(p_228646_0_, false, false)).func_228713_a_(field_228518_j_).func_228716_a_(field_228532_x_).func_228723_a_(field_228520_l_).func_228714_a_(field_228491_A_).func_228719_a_(field_228528_t_).func_228728_a_(true);
      return func_228632_a_("entity_smooth_cutout", DefaultVertexFormats.field_227849_i_, 7, 256, rendertype$state);
   }

   public static RenderType func_228637_a_(ResourceLocation p_228637_0_, boolean p_228637_1_) {
      RenderType.State rendertype$state = RenderType.State.func_228694_a_().func_228724_a_(new RenderState.TextureState(p_228637_0_, false, false)).func_228726_a_(p_228637_1_ ? field_228515_g_ : field_228510_b_).func_228727_a_(p_228637_1_ ? field_228496_F_ : field_228495_E_).func_228717_a_(field_228501_K_).func_228728_a_(false);
      return func_228633_a_("beacon_beam", DefaultVertexFormats.BLOCK, 7, 256, false, true, rendertype$state);
   }

   public static RenderType func_228648_g_(ResourceLocation p_228648_0_) {
      RenderType.State rendertype$state = RenderType.State.func_228694_a_().func_228724_a_(new RenderState.TextureState(p_228648_0_, false, false)).func_228716_a_(field_228532_x_).func_228713_a_(field_228517_i_).func_228715_a_(field_228493_C_).func_228714_a_(field_228491_A_).func_228719_a_(field_228528_t_).func_228722_a_(field_228530_v_).func_228728_a_(false);
      return func_228632_a_("entity_decal", DefaultVertexFormats.field_227849_i_, 7, 256, rendertype$state);
   }

   public static RenderType func_228650_h_(ResourceLocation p_228650_0_) {
      RenderType.State rendertype$state = RenderType.State.func_228694_a_().func_228724_a_(new RenderState.TextureState(p_228650_0_, false, false)).func_228726_a_(field_228515_g_).func_228716_a_(field_228532_x_).func_228713_a_(field_228517_i_).func_228714_a_(field_228491_A_).func_228719_a_(field_228528_t_).func_228722_a_(field_228530_v_).func_228727_a_(field_228496_F_).func_228728_a_(false);
      return func_228633_a_("entity_no_outline", DefaultVertexFormats.field_227849_i_, 7, 256, false, true, rendertype$state);
   }

   public static RenderType func_228635_a_(ResourceLocation p_228635_0_, float p_228635_1_) {
      RenderType.State rendertype$state = RenderType.State.func_228694_a_().func_228724_a_(new RenderState.TextureState(p_228635_0_, false, false)).func_228713_a_(new RenderState.AlphaState(p_228635_1_)).func_228714_a_(field_228491_A_).func_228728_a_(true);
      return func_228632_a_("entity_alpha", DefaultVertexFormats.field_227849_i_, 7, 256, rendertype$state);
   }

   public static RenderType func_228652_i_(ResourceLocation p_228652_0_) {
      RenderState.TextureState renderstate$texturestate = new RenderState.TextureState(p_228652_0_, false, false);
      return func_228633_a_("eyes", DefaultVertexFormats.field_227849_i_, 7, 256, false, true, RenderType.State.func_228694_a_().func_228724_a_(renderstate$texturestate).func_228726_a_(field_228511_c_).func_228727_a_(field_228496_F_).func_228717_a_(field_228503_M_).func_228728_a_(false));
   }

   public static RenderType func_228636_a_(ResourceLocation p_228636_0_, float p_228636_1_, float p_228636_2_) {
      return func_228633_a_("energy_swirl", DefaultVertexFormats.field_227849_i_, 7, 256, false, true, RenderType.State.func_228694_a_().func_228724_a_(new RenderState.TextureState(p_228636_0_, false, false)).func_228725_a_(new RenderState.OffsetTexturingState(p_228636_1_, p_228636_2_)).func_228717_a_(field_228503_M_).func_228726_a_(field_228511_c_).func_228716_a_(field_228532_x_).func_228713_a_(field_228517_i_).func_228714_a_(field_228491_A_).func_228719_a_(field_228528_t_).func_228722_a_(field_228530_v_).func_228728_a_(false));
   }

   public static RenderType func_228649_h_() {
      return field_228620_W_;
   }

   public static RenderType func_228651_i_() {
      return field_228621_X_;
   }

   public static RenderType func_228654_j_(ResourceLocation p_228654_0_) {
      return func_228632_a_("outline", DefaultVertexFormats.field_227851_o_, 7, 256, RenderType.State.func_228694_a_().func_228724_a_(new RenderState.TextureState(p_228654_0_, false, false)).func_228714_a_(field_228491_A_).func_228715_a_(field_228492_B_).func_228713_a_(field_228517_i_).func_228725_a_(field_228525_q_).func_228717_a_(field_228501_K_).func_228721_a_(field_228505_O_).func_230173_a_(RenderType.OutlineState.IS_OUTLINE));
   }

   public static RenderType func_228653_j_() {
      return field_228622_Y_;
   }

   public static RenderType func_228655_k_() {
      return field_228623_Z_;
   }

   public static RenderType func_228656_k_(ResourceLocation p_228656_0_) {
      RenderState.TextureState renderstate$texturestate = new RenderState.TextureState(p_228656_0_, false, false);
      return func_228633_a_("crumbling", DefaultVertexFormats.BLOCK, 7, 256, false, true, RenderType.State.func_228694_a_().func_228724_a_(renderstate$texturestate).func_228713_a_(field_228517_i_).func_228726_a_(field_228514_f_).func_228727_a_(field_228496_F_).func_228718_a_(field_228499_I_).func_228728_a_(false));
   }

   public static RenderType func_228658_l_(ResourceLocation p_228658_0_) {
      return func_228633_a_("text", DefaultVertexFormats.field_227852_q_, 7, 256, false, true, RenderType.State.func_228694_a_().func_228724_a_(new RenderState.TextureState(p_228658_0_, false, false)).func_228713_a_(field_228517_i_).func_228726_a_(field_228515_g_).func_228719_a_(field_228528_t_).func_228728_a_(false));
   }

   public static RenderType func_228660_m_(ResourceLocation p_228660_0_) {
      return func_228633_a_("text_see_through", DefaultVertexFormats.field_227852_q_, 7, 256, false, true, RenderType.State.func_228694_a_().func_228724_a_(new RenderState.TextureState(p_228660_0_, false, false)).func_228713_a_(field_228517_i_).func_228726_a_(field_228515_g_).func_228719_a_(field_228528_t_).func_228715_a_(field_228492_B_).func_228727_a_(field_228496_F_).func_228728_a_(false));
   }

   public static RenderType func_228657_l_() {
      return field_228624_aa_;
   }

   public static RenderType func_228630_a_(int p_228630_0_) {
      RenderState.TransparencyState renderstate$transparencystate;
      RenderState.TextureState renderstate$texturestate;
      if (p_228630_0_ <= 1) {
         renderstate$transparencystate = field_228515_g_;
         renderstate$texturestate = new RenderState.TextureState(EndPortalTileEntityRenderer.END_SKY_TEXTURE, false, false);
      } else {
         renderstate$transparencystate = field_228511_c_;
         renderstate$texturestate = new RenderState.TextureState(EndPortalTileEntityRenderer.END_PORTAL_TEXTURE, false, false);
      }

      return func_228633_a_("end_portal", DefaultVertexFormats.POSITION_COLOR, 7, 256, false, true, RenderType.State.func_228694_a_().func_228726_a_(renderstate$transparencystate).func_228724_a_(renderstate$texturestate).func_228725_a_(new RenderState.PortalTexturingState(p_228630_0_)).func_228717_a_(field_228503_M_).func_228728_a_(false));
   }

   public static RenderType func_228659_m_() {
      return field_228614_Q_;
   }

   public RenderType(String p_i225992_1_, VertexFormat p_i225992_2_, int p_i225992_3_, int p_i225992_4_, boolean p_i225992_5_, boolean p_i225992_6_, Runnable p_i225992_7_, Runnable p_i225992_8_) {
      super(p_i225992_1_, p_i225992_7_, p_i225992_8_);
      this.field_228625_ab_ = p_i225992_2_;
      this.field_228626_ac_ = p_i225992_3_;
      this.field_228627_ad_ = p_i225992_4_;
      this.field_228628_ae_ = p_i225992_5_;
      this.field_228629_af_ = p_i225992_6_;
      this.field_230166_ag_ = Optional.of(this);
   }

   public static RenderType.Type func_228632_a_(String p_228632_0_, VertexFormat p_228632_1_, int p_228632_2_, int p_228632_3_, RenderType.State p_228632_4_) {
      return func_228633_a_(p_228632_0_, p_228632_1_, p_228632_2_, p_228632_3_, false, false, p_228632_4_);
   }

   public static RenderType.Type func_228633_a_(String p_228633_0_, VertexFormat p_228633_1_, int p_228633_2_, int p_228633_3_, boolean p_228633_4_, boolean p_228633_5_, RenderType.State p_228633_6_) {
      return RenderType.Type.func_228676_c_(p_228633_0_, p_228633_1_, p_228633_2_, p_228633_3_, p_228633_4_, p_228633_5_, p_228633_6_);
   }

   public void func_228631_a_(BufferBuilder p_228631_1_, int p_228631_2_, int p_228631_3_, int p_228631_4_) {
      if (p_228631_1_.func_227834_j_()) {
         if (this.field_228629_af_) {
            p_228631_1_.sortVertexData((float)p_228631_2_, (float)p_228631_3_, (float)p_228631_4_);
         }

         p_228631_1_.finishDrawing();
         this.func_228547_a_();
         WorldVertexBufferUploader.draw(p_228631_1_);
         this.func_228549_b_();
      }
   }

   public String toString() {
      return this.field_228509_a_;
   }

   public static List<RenderType> func_228661_n_() {
      return ImmutableList.of(func_228639_c_(), func_228641_d_(), func_228643_e_(), func_228645_f_());
   }

   public int func_228662_o_() {
      return this.field_228627_ad_;
   }

   public VertexFormat func_228663_p_() {
      return this.field_228625_ab_;
   }

   public int func_228664_q_() {
      return this.field_228626_ac_;
   }

   public Optional<RenderType> func_225612_r_() {
      return Optional.empty();
   }

   public boolean func_230041_s_() {
      return false;
   }

   public boolean func_228665_s_() {
      return this.field_228628_ae_;
   }

   public Optional<RenderType> func_230169_u_() {
      return this.field_230166_ag_;
   }

   @OnlyIn(Dist.CLIENT)
   static enum OutlineState {
      NONE,
      IS_OUTLINE,
      AFFECTS_OUTLINE;
   }

   @OnlyIn(Dist.CLIENT)
   public static final class State {
      private final RenderState.TextureState field_228677_a_;
      private final RenderState.TransparencyState field_228678_b_;
      private final RenderState.DiffuseLightingState field_228679_c_;
      private final RenderState.ShadeModelState field_228680_d_;
      private final RenderState.AlphaState field_228681_e_;
      private final RenderState.DepthTestState field_228682_f_;
      private final RenderState.CullState field_228683_g_;
      private final RenderState.LightmapState field_228684_h_;
      private final RenderState.OverlayState field_228685_i_;
      private final RenderState.FogState field_228686_j_;
      private final RenderState.LayerState field_228687_k_;
      private final RenderState.TargetState field_228688_l_;
      private final RenderState.TexturingState field_228689_m_;
      private final RenderState.WriteMaskState field_228690_n_;
      private final RenderState.LineState field_228691_o_;
      private final RenderType.OutlineState field_230171_p_;
      private final ImmutableList<RenderState> field_228693_q_;

      private State(RenderState.TextureState p_i230053_1_, RenderState.TransparencyState p_i230053_2_, RenderState.DiffuseLightingState p_i230053_3_, RenderState.ShadeModelState p_i230053_4_, RenderState.AlphaState p_i230053_5_, RenderState.DepthTestState p_i230053_6_, RenderState.CullState p_i230053_7_, RenderState.LightmapState p_i230053_8_, RenderState.OverlayState p_i230053_9_, RenderState.FogState p_i230053_10_, RenderState.LayerState p_i230053_11_, RenderState.TargetState p_i230053_12_, RenderState.TexturingState p_i230053_13_, RenderState.WriteMaskState p_i230053_14_, RenderState.LineState p_i230053_15_, RenderType.OutlineState p_i230053_16_) {
         this.field_228677_a_ = p_i230053_1_;
         this.field_228678_b_ = p_i230053_2_;
         this.field_228679_c_ = p_i230053_3_;
         this.field_228680_d_ = p_i230053_4_;
         this.field_228681_e_ = p_i230053_5_;
         this.field_228682_f_ = p_i230053_6_;
         this.field_228683_g_ = p_i230053_7_;
         this.field_228684_h_ = p_i230053_8_;
         this.field_228685_i_ = p_i230053_9_;
         this.field_228686_j_ = p_i230053_10_;
         this.field_228687_k_ = p_i230053_11_;
         this.field_228688_l_ = p_i230053_12_;
         this.field_228689_m_ = p_i230053_13_;
         this.field_228690_n_ = p_i230053_14_;
         this.field_228691_o_ = p_i230053_15_;
         this.field_230171_p_ = p_i230053_16_;
         this.field_228693_q_ = ImmutableList.of(this.field_228677_a_, this.field_228678_b_, this.field_228679_c_, this.field_228680_d_, this.field_228681_e_, this.field_228682_f_, this.field_228683_g_, this.field_228684_h_, this.field_228685_i_, this.field_228686_j_, this.field_228687_k_, this.field_228688_l_, this.field_228689_m_, this.field_228690_n_, this.field_228691_o_);
      }

      public boolean equals(Object p_equals_1_) {
         if (this == p_equals_1_) {
            return true;
         } else if (p_equals_1_ != null && this.getClass() == p_equals_1_.getClass()) {
            RenderType.State rendertype$state = (RenderType.State)p_equals_1_;
            return this.field_230171_p_ == rendertype$state.field_230171_p_ && this.field_228693_q_.equals(rendertype$state.field_228693_q_);
         } else {
            return false;
         }
      }

      public int hashCode() {
         return Objects.hash(this.field_228693_q_, this.field_230171_p_);
      }

      public static RenderType.State.Builder func_228694_a_() {
         return new RenderType.State.Builder();
      }

      @OnlyIn(Dist.CLIENT)
      public static class Builder {
         private RenderState.TextureState field_228698_a_ = RenderState.field_228523_o_;
         private RenderState.TransparencyState field_228699_b_ = RenderState.field_228510_b_;
         private RenderState.DiffuseLightingState field_228700_c_ = RenderState.field_228533_y_;
         private RenderState.ShadeModelState field_228701_d_ = RenderState.field_228519_k_;
         private RenderState.AlphaState field_228702_e_ = RenderState.field_228516_h_;
         private RenderState.DepthTestState field_228703_f_ = RenderState.field_228494_D_;
         private RenderState.CullState field_228704_g_ = RenderState.field_228534_z_;
         private RenderState.LightmapState field_228705_h_ = RenderState.field_228529_u_;
         private RenderState.OverlayState field_228706_i_ = RenderState.field_228531_w_;
         private RenderState.FogState field_228707_j_ = RenderState.field_228502_L_;
         private RenderState.LayerState field_228708_k_ = RenderState.field_228498_H_;
         private RenderState.TargetState field_228709_l_ = RenderState.field_228504_N_;
         private RenderState.TexturingState field_228710_m_ = RenderState.field_228524_p_;
         private RenderState.WriteMaskState field_228711_n_ = RenderState.field_228495_E_;
         private RenderState.LineState field_228712_o_ = RenderState.field_228506_P_;

         private Builder() {
         }

         public RenderType.State.Builder func_228724_a_(RenderState.TextureState p_228724_1_) {
            this.field_228698_a_ = p_228724_1_;
            return this;
         }

         public RenderType.State.Builder func_228726_a_(RenderState.TransparencyState p_228726_1_) {
            this.field_228699_b_ = p_228726_1_;
            return this;
         }

         public RenderType.State.Builder func_228716_a_(RenderState.DiffuseLightingState p_228716_1_) {
            this.field_228700_c_ = p_228716_1_;
            return this;
         }

         public RenderType.State.Builder func_228723_a_(RenderState.ShadeModelState p_228723_1_) {
            this.field_228701_d_ = p_228723_1_;
            return this;
         }

         public RenderType.State.Builder func_228713_a_(RenderState.AlphaState p_228713_1_) {
            this.field_228702_e_ = p_228713_1_;
            return this;
         }

         public RenderType.State.Builder func_228715_a_(RenderState.DepthTestState p_228715_1_) {
            this.field_228703_f_ = p_228715_1_;
            return this;
         }

         public RenderType.State.Builder func_228714_a_(RenderState.CullState p_228714_1_) {
            this.field_228704_g_ = p_228714_1_;
            return this;
         }

         public RenderType.State.Builder func_228719_a_(RenderState.LightmapState p_228719_1_) {
            this.field_228705_h_ = p_228719_1_;
            return this;
         }

         public RenderType.State.Builder func_228722_a_(RenderState.OverlayState p_228722_1_) {
            this.field_228706_i_ = p_228722_1_;
            return this;
         }

         public RenderType.State.Builder func_228717_a_(RenderState.FogState p_228717_1_) {
            this.field_228707_j_ = p_228717_1_;
            return this;
         }

         public RenderType.State.Builder func_228718_a_(RenderState.LayerState p_228718_1_) {
            this.field_228708_k_ = p_228718_1_;
            return this;
         }

         public RenderType.State.Builder func_228721_a_(RenderState.TargetState p_228721_1_) {
            this.field_228709_l_ = p_228721_1_;
            return this;
         }

         public RenderType.State.Builder func_228725_a_(RenderState.TexturingState p_228725_1_) {
            this.field_228710_m_ = p_228725_1_;
            return this;
         }

         public RenderType.State.Builder func_228727_a_(RenderState.WriteMaskState p_228727_1_) {
            this.field_228711_n_ = p_228727_1_;
            return this;
         }

         public RenderType.State.Builder func_228720_a_(RenderState.LineState p_228720_1_) {
            this.field_228712_o_ = p_228720_1_;
            return this;
         }

         public RenderType.State func_228728_a_(boolean p_228728_1_) {
            return this.func_230173_a_(p_228728_1_ ? RenderType.OutlineState.AFFECTS_OUTLINE : RenderType.OutlineState.NONE);
         }

         public RenderType.State func_230173_a_(RenderType.OutlineState p_230173_1_) {
            return new RenderType.State(this.field_228698_a_, this.field_228699_b_, this.field_228700_c_, this.field_228701_d_, this.field_228702_e_, this.field_228703_f_, this.field_228704_g_, this.field_228705_h_, this.field_228706_i_, this.field_228707_j_, this.field_228708_k_, this.field_228709_l_, this.field_228710_m_, this.field_228711_n_, this.field_228712_o_, p_230173_1_);
         }
      }
   }

   @OnlyIn(Dist.CLIENT)
   static final class Type extends RenderType {
      private static final ObjectOpenCustomHashSet<RenderType.Type> field_228667_R_ = new ObjectOpenCustomHashSet<>(RenderType.Type.EqualityStrategy.INSTANCE);
      private final RenderType.State field_228668_S_;
      private final int field_228669_T_;
      private final Optional<RenderType> field_228670_U_;
      private final boolean field_230170_V_;

      private Type(String p_i225993_1_, VertexFormat p_i225993_2_, int p_i225993_3_, int p_i225993_4_, boolean p_i225993_5_, boolean p_i225993_6_, RenderType.State p_i225993_7_) {
         super(p_i225993_1_, p_i225993_2_, p_i225993_3_, p_i225993_4_, p_i225993_5_, p_i225993_6_, () -> {
            p_i225993_7_.field_228693_q_.forEach(RenderState::func_228547_a_);
         }, () -> {
            p_i225993_7_.field_228693_q_.forEach(RenderState::func_228549_b_);
         });
         this.field_228668_S_ = p_i225993_7_;
         this.field_228670_U_ = p_i225993_7_.field_230171_p_ == RenderType.OutlineState.AFFECTS_OUTLINE ? p_i225993_7_.field_228677_a_.func_228606_c_().map(RenderType::func_228654_j_) : Optional.empty();
         this.field_230170_V_ = p_i225993_7_.field_230171_p_ == RenderType.OutlineState.IS_OUTLINE;
         this.field_228669_T_ = Objects.hash(super.hashCode(), p_i225993_7_);
      }

      private static RenderType.Type func_228676_c_(String p_228676_0_, VertexFormat p_228676_1_, int p_228676_2_, int p_228676_3_, boolean p_228676_4_, boolean p_228676_5_, RenderType.State p_228676_6_) {
         return field_228667_R_.addOrGet(new RenderType.Type(p_228676_0_, p_228676_1_, p_228676_2_, p_228676_3_, p_228676_4_, p_228676_5_, p_228676_6_));
      }

      public Optional<RenderType> func_225612_r_() {
         return this.field_228670_U_;
      }

      public boolean func_230041_s_() {
         return this.field_230170_V_;
      }

      public boolean equals(@Nullable Object p_equals_1_) {
         return this == p_equals_1_;
      }

      public int hashCode() {
         return this.field_228669_T_;
      }

      @OnlyIn(Dist.CLIENT)
      static enum EqualityStrategy implements Strategy<RenderType.Type> {
         INSTANCE;

         public int hashCode(@Nullable RenderType.Type p_hashCode_1_) {
            return p_hashCode_1_ == null ? 0 : p_hashCode_1_.field_228669_T_;
         }

         public boolean equals(@Nullable RenderType.Type p_equals_1_, @Nullable RenderType.Type p_equals_2_) {
            if (p_equals_1_ == p_equals_2_) {
               return true;
            } else {
               return p_equals_1_ != null && p_equals_2_ != null ? Objects.equals(p_equals_1_.field_228668_S_, p_equals_2_.field_228668_S_) : false;
            }
         }
      }
   }
}