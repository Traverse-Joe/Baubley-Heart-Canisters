package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.ModelManager;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.item.ItemFrameEntity;
import net.minecraft.item.FilledMapItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.storage.MapData;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ItemFrameRenderer extends EntityRenderer<ItemFrameEntity> {
   private static final ModelResourceLocation LOCATION_MODEL = new ModelResourceLocation("item_frame", "map=false");
   private static final ModelResourceLocation LOCATION_MODEL_MAP = new ModelResourceLocation("item_frame", "map=true");
   private final Minecraft mc = Minecraft.getInstance();
   private final net.minecraft.client.renderer.ItemRenderer itemRenderer;

   public ItemFrameRenderer(EntityRendererManager renderManagerIn, net.minecraft.client.renderer.ItemRenderer itemRendererIn) {
      super(renderManagerIn);
      this.itemRenderer = itemRendererIn;
   }

   public void func_225623_a_(ItemFrameEntity p_225623_1_, float p_225623_2_, float p_225623_3_, MatrixStack p_225623_4_, IRenderTypeBuffer p_225623_5_, int p_225623_6_) {
      super.func_225623_a_(p_225623_1_, p_225623_2_, p_225623_3_, p_225623_4_, p_225623_5_, p_225623_6_);
      p_225623_4_.func_227860_a_();
      Direction direction = p_225623_1_.getHorizontalFacing();
      Vec3d vec3d = this.func_225627_b_(p_225623_1_, p_225623_3_);
      p_225623_4_.func_227861_a_(-vec3d.getX(), -vec3d.getY(), -vec3d.getZ());
      double d0 = 0.46875D;
      p_225623_4_.func_227861_a_((double)direction.getXOffset() * 0.46875D, (double)direction.getYOffset() * 0.46875D, (double)direction.getZOffset() * 0.46875D);
      p_225623_4_.func_227863_a_(Vector3f.field_229179_b_.func_229187_a_(p_225623_1_.rotationPitch));
      p_225623_4_.func_227863_a_(Vector3f.field_229181_d_.func_229187_a_(180.0F - p_225623_1_.rotationYaw));
      BlockRendererDispatcher blockrendererdispatcher = this.mc.getBlockRendererDispatcher();
      ModelManager modelmanager = blockrendererdispatcher.getBlockModelShapes().getModelManager();
      ModelResourceLocation modelresourcelocation = p_225623_1_.getDisplayedItem().getItem() instanceof FilledMapItem ? LOCATION_MODEL_MAP : LOCATION_MODEL;
      p_225623_4_.func_227860_a_();
      p_225623_4_.func_227861_a_(-0.5D, -0.5D, -0.5D);
      blockrendererdispatcher.getBlockModelRenderer().func_228804_a_(p_225623_4_.func_227866_c_(), p_225623_5_.getBuffer(Atlases.func_228782_g_()), (BlockState)null, modelmanager.getModel(modelresourcelocation), 1.0F, 1.0F, 1.0F, p_225623_6_, OverlayTexture.field_229196_a_);
      p_225623_4_.func_227865_b_();
      ItemStack itemstack = p_225623_1_.getDisplayedItem();
      if (!itemstack.isEmpty()) {
         MapData mapdata = FilledMapItem.getMapData(itemstack, p_225623_1_.world);
         p_225623_4_.func_227861_a_(0.0D, 0.0D, 0.4375D);
         int i = mapdata != null ? p_225623_1_.getRotation() % 4 * 2 : p_225623_1_.getRotation();
         p_225623_4_.func_227863_a_(Vector3f.field_229183_f_.func_229187_a_((float)i * 360.0F / 8.0F));
         if (!net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.RenderItemInFrameEvent(p_225623_1_, this, p_225623_4_, p_225623_5_, p_225623_6_))) {
         if (mapdata != null) {
            p_225623_4_.func_227863_a_(Vector3f.field_229183_f_.func_229187_a_(180.0F));
            float f = 0.0078125F;
            p_225623_4_.func_227862_a_(0.0078125F, 0.0078125F, 0.0078125F);
            p_225623_4_.func_227861_a_(-64.0D, -64.0D, 0.0D);
            p_225623_4_.func_227861_a_(0.0D, 0.0D, -1.0D);
            if (mapdata != null) {
               this.mc.gameRenderer.getMapItemRenderer().func_228086_a_(p_225623_4_, p_225623_5_, mapdata, true, p_225623_6_);
            }
         } else {
            p_225623_4_.func_227862_a_(0.5F, 0.5F, 0.5F);
            this.itemRenderer.func_229110_a_(itemstack, ItemCameraTransforms.TransformType.FIXED, p_225623_6_, OverlayTexture.field_229196_a_, p_225623_4_, p_225623_5_);
         }
         }
      }

      p_225623_4_.func_227865_b_();
   }

   public Vec3d func_225627_b_(ItemFrameEntity p_225627_1_, float p_225627_2_) {
      return new Vec3d((double)((float)p_225627_1_.getHorizontalFacing().getXOffset() * 0.3F), -0.25D, (double)((float)p_225627_1_.getHorizontalFacing().getZOffset() * 0.3F));
   }

   public ResourceLocation getEntityTexture(ItemFrameEntity entity) {
      return AtlasTexture.LOCATION_BLOCKS_TEXTURE;
   }

   protected boolean canRenderName(ItemFrameEntity entity) {
      if (Minecraft.isGuiEnabled() && !entity.getDisplayedItem().isEmpty() && entity.getDisplayedItem().hasDisplayName() && this.renderManager.pointedEntity == entity) {
         double d0 = this.renderManager.func_229099_b_(entity);
         float f = entity.func_226273_bm_() ? 32.0F : 64.0F;
         return d0 < (double)(f * f);
      } else {
         return false;
      }
   }

   protected void func_225629_a_(ItemFrameEntity p_225629_1_, String p_225629_2_, MatrixStack p_225629_3_, IRenderTypeBuffer p_225629_4_, int p_225629_5_) {
      super.func_225629_a_(p_225629_1_, p_225629_1_.getDisplayedItem().getDisplayName().getFormattedText(), p_225629_3_, p_225629_4_, p_225629_5_);
   }
}