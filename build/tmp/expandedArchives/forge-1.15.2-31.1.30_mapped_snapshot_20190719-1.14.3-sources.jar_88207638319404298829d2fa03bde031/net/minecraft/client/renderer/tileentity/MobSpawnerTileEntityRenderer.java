package net.minecraft.client.renderer.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.MobSpawnerTileEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.spawner.AbstractSpawner;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MobSpawnerTileEntityRenderer extends TileEntityRenderer<MobSpawnerTileEntity> {
   public MobSpawnerTileEntityRenderer(TileEntityRendererDispatcher p_i226016_1_) {
      super(p_i226016_1_);
   }

   public void func_225616_a_(MobSpawnerTileEntity p_225616_1_, float p_225616_2_, MatrixStack p_225616_3_, IRenderTypeBuffer p_225616_4_, int p_225616_5_, int p_225616_6_) {
      p_225616_3_.func_227860_a_();
      p_225616_3_.func_227861_a_(0.5D, 0.0D, 0.5D);
      AbstractSpawner abstractspawner = p_225616_1_.getSpawnerBaseLogic();
      Entity entity = abstractspawner.getCachedEntity();
      if (entity != null) {
         float f = 0.53125F;
         float f1 = Math.max(entity.getWidth(), entity.getHeight());
         if ((double)f1 > 1.0D) {
            f /= f1;
         }

         p_225616_3_.func_227861_a_(0.0D, (double)0.4F, 0.0D);
         p_225616_3_.func_227863_a_(Vector3f.field_229181_d_.func_229187_a_((float)MathHelper.lerp((double)p_225616_2_, abstractspawner.getPrevMobRotation(), abstractspawner.getMobRotation()) * 10.0F));
         p_225616_3_.func_227861_a_(0.0D, (double)-0.2F, 0.0D);
         p_225616_3_.func_227863_a_(Vector3f.field_229179_b_.func_229187_a_(-30.0F));
         p_225616_3_.func_227862_a_(f, f, f);
         Minecraft.getInstance().getRenderManager().func_229084_a_(entity, 0.0D, 0.0D, 0.0D, 0.0F, p_225616_2_, p_225616_3_, p_225616_4_, p_225616_5_);
      }

      p_225616_3_.func_227865_b_();
   }
}