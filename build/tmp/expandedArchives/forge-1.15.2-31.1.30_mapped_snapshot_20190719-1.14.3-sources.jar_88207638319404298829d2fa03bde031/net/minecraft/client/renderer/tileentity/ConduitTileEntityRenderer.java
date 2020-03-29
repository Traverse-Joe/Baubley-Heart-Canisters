package net.minecraft.client.renderer.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Quaternion;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.model.Material;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.tileentity.ConduitTileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ConduitTileEntityRenderer extends TileEntityRenderer<ConduitTileEntity> {
   public static final Material BASE_TEXTURE = new Material(AtlasTexture.LOCATION_BLOCKS_TEXTURE, new ResourceLocation("entity/conduit/base"));
   public static final Material CAGE_TEXTURE = new Material(AtlasTexture.LOCATION_BLOCKS_TEXTURE, new ResourceLocation("entity/conduit/cage"));
   public static final Material WIND_TEXTURE = new Material(AtlasTexture.LOCATION_BLOCKS_TEXTURE, new ResourceLocation("entity/conduit/wind"));
   public static final Material VERTICAL_WIND_TEXTURE = new Material(AtlasTexture.LOCATION_BLOCKS_TEXTURE, new ResourceLocation("entity/conduit/wind_vertical"));
   public static final Material OPEN_EYE_TEXTURE = new Material(AtlasTexture.LOCATION_BLOCKS_TEXTURE, new ResourceLocation("entity/conduit/open_eye"));
   public static final Material CLOSED_EYE_TEXTURE = new Material(AtlasTexture.LOCATION_BLOCKS_TEXTURE, new ResourceLocation("entity/conduit/closed_eye"));
   private final ModelRenderer field_228872_h_ = new ModelRenderer(16, 16, 0, 0);
   private final ModelRenderer field_228873_i_;
   private final ModelRenderer field_228874_j_;
   private final ModelRenderer field_228875_k_;

   public ConduitTileEntityRenderer(TileEntityRendererDispatcher p_i226009_1_) {
      super(p_i226009_1_);
      this.field_228872_h_.func_228301_a_(-4.0F, -4.0F, 0.0F, 8.0F, 8.0F, 0.0F, 0.01F);
      this.field_228873_i_ = new ModelRenderer(64, 32, 0, 0);
      this.field_228873_i_.func_228300_a_(-8.0F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F);
      this.field_228874_j_ = new ModelRenderer(32, 16, 0, 0);
      this.field_228874_j_.func_228300_a_(-3.0F, -3.0F, -3.0F, 6.0F, 6.0F, 6.0F);
      this.field_228875_k_ = new ModelRenderer(32, 16, 0, 0);
      this.field_228875_k_.func_228300_a_(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F);
   }

   public void func_225616_a_(ConduitTileEntity p_225616_1_, float p_225616_2_, MatrixStack p_225616_3_, IRenderTypeBuffer p_225616_4_, int p_225616_5_, int p_225616_6_) {
      float f = (float)p_225616_1_.ticksExisted + p_225616_2_;
      if (!p_225616_1_.isActive()) {
         float f5 = p_225616_1_.getActiveRotation(0.0F);
         IVertexBuilder ivertexbuilder1 = BASE_TEXTURE.func_229311_a_(p_225616_4_, RenderType::func_228634_a_);
         p_225616_3_.func_227860_a_();
         p_225616_3_.func_227861_a_(0.5D, 0.5D, 0.5D);
         p_225616_3_.func_227863_a_(Vector3f.field_229181_d_.func_229187_a_(f5));
         this.field_228874_j_.func_228308_a_(p_225616_3_, ivertexbuilder1, p_225616_5_, p_225616_6_);
         p_225616_3_.func_227865_b_();
      } else {
         float f1 = p_225616_1_.getActiveRotation(p_225616_2_) * (180F / (float)Math.PI);
         float f2 = MathHelper.sin(f * 0.1F) / 2.0F + 0.5F;
         f2 = f2 * f2 + f2;
         p_225616_3_.func_227860_a_();
         p_225616_3_.func_227861_a_(0.5D, (double)(0.3F + f2 * 0.2F), 0.5D);
         Vector3f vector3f = new Vector3f(0.5F, 1.0F, 0.5F);
         vector3f.func_229194_d_();
         p_225616_3_.func_227863_a_(new Quaternion(vector3f, f1, true));
         this.field_228875_k_.func_228308_a_(p_225616_3_, CAGE_TEXTURE.func_229311_a_(p_225616_4_, RenderType::func_228640_c_), p_225616_5_, p_225616_6_);
         p_225616_3_.func_227865_b_();
         int i = p_225616_1_.ticksExisted / 66 % 3;
         p_225616_3_.func_227860_a_();
         p_225616_3_.func_227861_a_(0.5D, 0.5D, 0.5D);
         if (i == 1) {
            p_225616_3_.func_227863_a_(Vector3f.field_229179_b_.func_229187_a_(90.0F));
         } else if (i == 2) {
            p_225616_3_.func_227863_a_(Vector3f.field_229183_f_.func_229187_a_(90.0F));
         }

         IVertexBuilder ivertexbuilder = (i == 1 ? VERTICAL_WIND_TEXTURE : WIND_TEXTURE).func_229311_a_(p_225616_4_, RenderType::func_228640_c_);
         this.field_228873_i_.func_228308_a_(p_225616_3_, ivertexbuilder, p_225616_5_, p_225616_6_);
         p_225616_3_.func_227865_b_();
         p_225616_3_.func_227860_a_();
         p_225616_3_.func_227861_a_(0.5D, 0.5D, 0.5D);
         p_225616_3_.func_227862_a_(0.875F, 0.875F, 0.875F);
         p_225616_3_.func_227863_a_(Vector3f.field_229179_b_.func_229187_a_(180.0F));
         p_225616_3_.func_227863_a_(Vector3f.field_229183_f_.func_229187_a_(180.0F));
         this.field_228873_i_.func_228308_a_(p_225616_3_, ivertexbuilder, p_225616_5_, p_225616_6_);
         p_225616_3_.func_227865_b_();
         ActiveRenderInfo activerenderinfo = this.field_228858_b_.renderInfo;
         p_225616_3_.func_227860_a_();
         p_225616_3_.func_227861_a_(0.5D, (double)(0.3F + f2 * 0.2F), 0.5D);
         p_225616_3_.func_227862_a_(0.5F, 0.5F, 0.5F);
         float f3 = -activerenderinfo.getYaw();
         p_225616_3_.func_227863_a_(Vector3f.field_229181_d_.func_229187_a_(f3));
         p_225616_3_.func_227863_a_(Vector3f.field_229179_b_.func_229187_a_(activerenderinfo.getPitch()));
         p_225616_3_.func_227863_a_(Vector3f.field_229183_f_.func_229187_a_(180.0F));
         float f4 = 1.3333334F;
         p_225616_3_.func_227862_a_(1.3333334F, 1.3333334F, 1.3333334F);
         this.field_228872_h_.func_228308_a_(p_225616_3_, (p_225616_1_.isEyeOpen() ? OPEN_EYE_TEXTURE : CLOSED_EYE_TEXTURE).func_229311_a_(p_225616_4_, RenderType::func_228640_c_), p_225616_5_, p_225616_6_);
         p_225616_3_.func_227865_b_();
      }
   }
}