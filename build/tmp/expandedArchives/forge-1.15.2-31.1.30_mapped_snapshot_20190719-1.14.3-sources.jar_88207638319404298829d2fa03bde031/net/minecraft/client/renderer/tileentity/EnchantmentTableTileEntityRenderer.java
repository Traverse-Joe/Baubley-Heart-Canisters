package net.minecraft.client.renderer.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.entity.model.BookModel;
import net.minecraft.client.renderer.model.Material;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.tileentity.EnchantingTableTileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class EnchantmentTableTileEntityRenderer extends TileEntityRenderer<EnchantingTableTileEntity> {
   public static final Material TEXTURE_BOOK = new Material(AtlasTexture.LOCATION_BLOCKS_TEXTURE, new ResourceLocation("entity/enchanting_table_book"));
   private final BookModel modelBook = new BookModel();

   public EnchantmentTableTileEntityRenderer(TileEntityRendererDispatcher p_i226010_1_) {
      super(p_i226010_1_);
   }

   public void func_225616_a_(EnchantingTableTileEntity p_225616_1_, float p_225616_2_, MatrixStack p_225616_3_, IRenderTypeBuffer p_225616_4_, int p_225616_5_, int p_225616_6_) {
      p_225616_3_.func_227860_a_();
      p_225616_3_.func_227861_a_(0.5D, 0.75D, 0.5D);
      float f = (float)p_225616_1_.field_195522_a + p_225616_2_;
      p_225616_3_.func_227861_a_(0.0D, (double)(0.1F + MathHelper.sin(f * 0.1F) * 0.01F), 0.0D);

      float f1;
      for(f1 = p_225616_1_.field_195529_l - p_225616_1_.field_195530_m; f1 >= (float)Math.PI; f1 -= ((float)Math.PI * 2F)) {
         ;
      }

      while(f1 < -(float)Math.PI) {
         f1 += ((float)Math.PI * 2F);
      }

      float f2 = p_225616_1_.field_195530_m + f1 * p_225616_2_;
      p_225616_3_.func_227863_a_(Vector3f.field_229181_d_.func_229193_c_(-f2));
      p_225616_3_.func_227863_a_(Vector3f.field_229183_f_.func_229187_a_(80.0F));
      float f3 = MathHelper.lerp(p_225616_2_, p_225616_1_.field_195524_g, p_225616_1_.field_195523_f);
      float f4 = MathHelper.func_226164_h_(f3 + 0.25F) * 1.6F - 0.3F;
      float f5 = MathHelper.func_226164_h_(f3 + 0.75F) * 1.6F - 0.3F;
      float f6 = MathHelper.lerp(p_225616_2_, p_225616_1_.field_195528_k, p_225616_1_.field_195527_j);
      this.modelBook.func_228247_a_(f, MathHelper.clamp(f4, 0.0F, 1.0F), MathHelper.clamp(f5, 0.0F, 1.0F), f6);
      IVertexBuilder ivertexbuilder = TEXTURE_BOOK.func_229311_a_(p_225616_4_, RenderType::func_228634_a_);
      this.modelBook.func_228249_b_(p_225616_3_, ivertexbuilder, p_225616_5_, p_225616_6_, 1.0F, 1.0F, 1.0F, 1.0F);
      p_225616_3_.func_227865_b_();
   }
}