package net.minecraft.client.renderer;

import com.google.common.base.MoreObjects;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import java.util.Objects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.FilledMapItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.storage.MapData;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FirstPersonRenderer {
   private static final RenderType field_230010_a_ = RenderType.func_228658_l_(new ResourceLocation("textures/map/map_background.png"));
   private static final RenderType field_230011_b_ = RenderType.func_228658_l_(new ResourceLocation("textures/map/map_background_checkerboard.png"));
   private final Minecraft mc;
   private ItemStack itemStackMainHand = ItemStack.EMPTY;
   private ItemStack itemStackOffHand = ItemStack.EMPTY;
   private float equippedProgressMainHand;
   private float prevEquippedProgressMainHand;
   private float equippedProgressOffHand;
   private float prevEquippedProgressOffHand;
   private final EntityRendererManager renderManager;
   private final ItemRenderer itemRenderer;

   public FirstPersonRenderer(Minecraft mcIn) {
      this.mc = mcIn;
      this.renderManager = mcIn.getRenderManager();
      this.itemRenderer = mcIn.getItemRenderer();
   }

   public void func_228397_a_(LivingEntity p_228397_1_, ItemStack p_228397_2_, ItemCameraTransforms.TransformType p_228397_3_, boolean p_228397_4_, MatrixStack p_228397_5_, IRenderTypeBuffer p_228397_6_, int p_228397_7_) {
      if (!p_228397_2_.isEmpty()) {
         this.itemRenderer.func_229109_a_(p_228397_1_, p_228397_2_, p_228397_3_, p_228397_4_, p_228397_5_, p_228397_6_, p_228397_1_.world, p_228397_7_, OverlayTexture.field_229196_a_);
      }
   }

   /**
    * Return the angle to render the Map
    */
   private float getMapAngleFromPitch(float pitch) {
      float f = 1.0F - pitch / 45.0F + 0.1F;
      f = MathHelper.clamp(f, 0.0F, 1.0F);
      f = -MathHelper.cos(f * (float)Math.PI) * 0.5F + 0.5F;
      return f;
   }

   private void func_228403_a_(MatrixStack p_228403_1_, IRenderTypeBuffer p_228403_2_, int p_228403_3_, HandSide p_228403_4_) {
      this.mc.getTextureManager().bindTexture(this.mc.player.getLocationSkin());
      PlayerRenderer playerrenderer = (PlayerRenderer)this.renderManager.<AbstractClientPlayerEntity>getRenderer(this.mc.player);
      p_228403_1_.func_227860_a_();
      float f = p_228403_4_ == HandSide.RIGHT ? 1.0F : -1.0F;
      p_228403_1_.func_227863_a_(Vector3f.field_229181_d_.func_229187_a_(92.0F));
      p_228403_1_.func_227863_a_(Vector3f.field_229179_b_.func_229187_a_(45.0F));
      p_228403_1_.func_227863_a_(Vector3f.field_229183_f_.func_229187_a_(f * -41.0F));
      p_228403_1_.func_227861_a_((double)(f * 0.3F), (double)-1.1F, (double)0.45F);
      if (p_228403_4_ == HandSide.RIGHT) {
         playerrenderer.func_229144_a_(p_228403_1_, p_228403_2_, p_228403_3_, this.mc.player);
      } else {
         playerrenderer.func_229146_b_(p_228403_1_, p_228403_2_, p_228403_3_, this.mc.player);
      }

      p_228403_1_.func_227865_b_();
   }

   private void func_228402_a_(MatrixStack p_228402_1_, IRenderTypeBuffer p_228402_2_, int p_228402_3_, float p_228402_4_, HandSide p_228402_5_, float p_228402_6_, ItemStack p_228402_7_) {
      float f = p_228402_5_ == HandSide.RIGHT ? 1.0F : -1.0F;
      p_228402_1_.func_227861_a_((double)(f * 0.125F), -0.125D, 0.0D);
      if (!this.mc.player.isInvisible()) {
         p_228402_1_.func_227860_a_();
         p_228402_1_.func_227863_a_(Vector3f.field_229183_f_.func_229187_a_(f * 10.0F));
         this.func_228401_a_(p_228402_1_, p_228402_2_, p_228402_3_, p_228402_4_, p_228402_6_, p_228402_5_);
         p_228402_1_.func_227865_b_();
      }

      p_228402_1_.func_227860_a_();
      p_228402_1_.func_227861_a_((double)(f * 0.51F), (double)(-0.08F + p_228402_4_ * -1.2F), -0.75D);
      float f1 = MathHelper.sqrt(p_228402_6_);
      float f2 = MathHelper.sin(f1 * (float)Math.PI);
      float f3 = -0.5F * f2;
      float f4 = 0.4F * MathHelper.sin(f1 * ((float)Math.PI * 2F));
      float f5 = -0.3F * MathHelper.sin(p_228402_6_ * (float)Math.PI);
      p_228402_1_.func_227861_a_((double)(f * f3), (double)(f4 - 0.3F * f2), (double)f5);
      p_228402_1_.func_227863_a_(Vector3f.field_229179_b_.func_229187_a_(f2 * -45.0F));
      p_228402_1_.func_227863_a_(Vector3f.field_229181_d_.func_229187_a_(f * f2 * -30.0F));
      this.func_228404_a_(p_228402_1_, p_228402_2_, p_228402_3_, p_228402_7_);
      p_228402_1_.func_227865_b_();
   }

   private void func_228400_a_(MatrixStack p_228400_1_, IRenderTypeBuffer p_228400_2_, int p_228400_3_, float p_228400_4_, float p_228400_5_, float p_228400_6_) {
      float f = MathHelper.sqrt(p_228400_6_);
      float f1 = -0.2F * MathHelper.sin(p_228400_6_ * (float)Math.PI);
      float f2 = -0.4F * MathHelper.sin(f * (float)Math.PI);
      p_228400_1_.func_227861_a_(0.0D, (double)(-f1 / 2.0F), (double)f2);
      float f3 = this.getMapAngleFromPitch(p_228400_4_);
      p_228400_1_.func_227861_a_(0.0D, (double)(0.04F + p_228400_5_ * -1.2F + f3 * -0.5F), (double)-0.72F);
      p_228400_1_.func_227863_a_(Vector3f.field_229179_b_.func_229187_a_(f3 * -85.0F));
      if (!this.mc.player.isInvisible()) {
         p_228400_1_.func_227860_a_();
         p_228400_1_.func_227863_a_(Vector3f.field_229181_d_.func_229187_a_(90.0F));
         this.func_228403_a_(p_228400_1_, p_228400_2_, p_228400_3_, HandSide.RIGHT);
         this.func_228403_a_(p_228400_1_, p_228400_2_, p_228400_3_, HandSide.LEFT);
         p_228400_1_.func_227865_b_();
      }

      float f4 = MathHelper.sin(f * (float)Math.PI);
      p_228400_1_.func_227863_a_(Vector3f.field_229179_b_.func_229187_a_(f4 * 20.0F));
      p_228400_1_.func_227862_a_(2.0F, 2.0F, 2.0F);
      this.func_228404_a_(p_228400_1_, p_228400_2_, p_228400_3_, this.itemStackMainHand);
   }

   private void func_228404_a_(MatrixStack p_228404_1_, IRenderTypeBuffer p_228404_2_, int p_228404_3_, ItemStack p_228404_4_) {
      p_228404_1_.func_227863_a_(Vector3f.field_229181_d_.func_229187_a_(180.0F));
      p_228404_1_.func_227863_a_(Vector3f.field_229183_f_.func_229187_a_(180.0F));
      p_228404_1_.func_227862_a_(0.38F, 0.38F, 0.38F);
      p_228404_1_.func_227861_a_(-0.5D, -0.5D, 0.0D);
      p_228404_1_.func_227862_a_(0.0078125F, 0.0078125F, 0.0078125F);
      MapData mapdata = FilledMapItem.getMapData(p_228404_4_, this.mc.world);
      IVertexBuilder ivertexbuilder = p_228404_2_.getBuffer(mapdata == null ? field_230010_a_ : field_230011_b_);
      Matrix4f matrix4f = p_228404_1_.func_227866_c_().func_227870_a_();
      ivertexbuilder.func_227888_a_(matrix4f, -7.0F, 135.0F, 0.0F).func_225586_a_(255, 255, 255, 255).func_225583_a_(0.0F, 1.0F).func_227886_a_(p_228404_3_).endVertex();
      ivertexbuilder.func_227888_a_(matrix4f, 135.0F, 135.0F, 0.0F).func_225586_a_(255, 255, 255, 255).func_225583_a_(1.0F, 1.0F).func_227886_a_(p_228404_3_).endVertex();
      ivertexbuilder.func_227888_a_(matrix4f, 135.0F, -7.0F, 0.0F).func_225586_a_(255, 255, 255, 255).func_225583_a_(1.0F, 0.0F).func_227886_a_(p_228404_3_).endVertex();
      ivertexbuilder.func_227888_a_(matrix4f, -7.0F, -7.0F, 0.0F).func_225586_a_(255, 255, 255, 255).func_225583_a_(0.0F, 0.0F).func_227886_a_(p_228404_3_).endVertex();
      if (mapdata != null) {
         this.mc.gameRenderer.getMapItemRenderer().func_228086_a_(p_228404_1_, p_228404_2_, mapdata, false, p_228404_3_);
      }

   }

   private void func_228401_a_(MatrixStack p_228401_1_, IRenderTypeBuffer p_228401_2_, int p_228401_3_, float p_228401_4_, float p_228401_5_, HandSide p_228401_6_) {
      boolean flag = p_228401_6_ != HandSide.LEFT;
      float f = flag ? 1.0F : -1.0F;
      float f1 = MathHelper.sqrt(p_228401_5_);
      float f2 = -0.3F * MathHelper.sin(f1 * (float)Math.PI);
      float f3 = 0.4F * MathHelper.sin(f1 * ((float)Math.PI * 2F));
      float f4 = -0.4F * MathHelper.sin(p_228401_5_ * (float)Math.PI);
      p_228401_1_.func_227861_a_((double)(f * (f2 + 0.64000005F)), (double)(f3 + -0.6F + p_228401_4_ * -0.6F), (double)(f4 + -0.71999997F));
      p_228401_1_.func_227863_a_(Vector3f.field_229181_d_.func_229187_a_(f * 45.0F));
      float f5 = MathHelper.sin(p_228401_5_ * p_228401_5_ * (float)Math.PI);
      float f6 = MathHelper.sin(f1 * (float)Math.PI);
      p_228401_1_.func_227863_a_(Vector3f.field_229181_d_.func_229187_a_(f * f6 * 70.0F));
      p_228401_1_.func_227863_a_(Vector3f.field_229183_f_.func_229187_a_(f * f5 * -20.0F));
      AbstractClientPlayerEntity abstractclientplayerentity = this.mc.player;
      this.mc.getTextureManager().bindTexture(abstractclientplayerentity.getLocationSkin());
      p_228401_1_.func_227861_a_((double)(f * -1.0F), (double)3.6F, 3.5D);
      p_228401_1_.func_227863_a_(Vector3f.field_229183_f_.func_229187_a_(f * 120.0F));
      p_228401_1_.func_227863_a_(Vector3f.field_229179_b_.func_229187_a_(200.0F));
      p_228401_1_.func_227863_a_(Vector3f.field_229181_d_.func_229187_a_(f * -135.0F));
      p_228401_1_.func_227861_a_((double)(f * 5.6F), 0.0D, 0.0D);
      PlayerRenderer playerrenderer = (PlayerRenderer)this.renderManager.<AbstractClientPlayerEntity>getRenderer(abstractclientplayerentity);
      if (flag) {
         playerrenderer.func_229144_a_(p_228401_1_, p_228401_2_, p_228401_3_, abstractclientplayerentity);
      } else {
         playerrenderer.func_229146_b_(p_228401_1_, p_228401_2_, p_228401_3_, abstractclientplayerentity);
      }

   }

   private void func_228398_a_(MatrixStack p_228398_1_, float p_228398_2_, HandSide p_228398_3_, ItemStack p_228398_4_) {
      float f = (float)this.mc.player.getItemInUseCount() - p_228398_2_ + 1.0F;
      float f1 = f / (float)p_228398_4_.getUseDuration();
      if (f1 < 0.8F) {
         float f2 = MathHelper.abs(MathHelper.cos(f / 4.0F * (float)Math.PI) * 0.1F);
         p_228398_1_.func_227861_a_(0.0D, (double)f2, 0.0D);
      }

      float f3 = 1.0F - (float)Math.pow((double)f1, 27.0D);
      int i = p_228398_3_ == HandSide.RIGHT ? 1 : -1;
      p_228398_1_.func_227861_a_((double)(f3 * 0.6F * (float)i), (double)(f3 * -0.5F), (double)(f3 * 0.0F));
      p_228398_1_.func_227863_a_(Vector3f.field_229181_d_.func_229187_a_((float)i * f3 * 90.0F));
      p_228398_1_.func_227863_a_(Vector3f.field_229179_b_.func_229187_a_(f3 * 10.0F));
      p_228398_1_.func_227863_a_(Vector3f.field_229183_f_.func_229187_a_((float)i * f3 * 30.0F));
   }

   private void func_228399_a_(MatrixStack p_228399_1_, HandSide p_228399_2_, float p_228399_3_) {
      int i = p_228399_2_ == HandSide.RIGHT ? 1 : -1;
      float f = MathHelper.sin(p_228399_3_ * p_228399_3_ * (float)Math.PI);
      p_228399_1_.func_227863_a_(Vector3f.field_229181_d_.func_229187_a_((float)i * (45.0F + f * -20.0F)));
      float f1 = MathHelper.sin(MathHelper.sqrt(p_228399_3_) * (float)Math.PI);
      p_228399_1_.func_227863_a_(Vector3f.field_229183_f_.func_229187_a_((float)i * f1 * -20.0F));
      p_228399_1_.func_227863_a_(Vector3f.field_229179_b_.func_229187_a_(f1 * -80.0F));
      p_228399_1_.func_227863_a_(Vector3f.field_229181_d_.func_229187_a_((float)i * -45.0F));
   }

   private void func_228406_b_(MatrixStack p_228406_1_, HandSide p_228406_2_, float p_228406_3_) {
      int i = p_228406_2_ == HandSide.RIGHT ? 1 : -1;
      p_228406_1_.func_227861_a_((double)((float)i * 0.56F), (double)(-0.52F + p_228406_3_ * -0.6F), (double)-0.72F);
   }

   public void func_228396_a_(float p_228396_1_, MatrixStack p_228396_2_, IRenderTypeBuffer.Impl p_228396_3_, ClientPlayerEntity p_228396_4_, int p_228396_5_) {
      float f = p_228396_4_.getSwingProgress(p_228396_1_);
      Hand hand = MoreObjects.firstNonNull(p_228396_4_.swingingHand, Hand.MAIN_HAND);
      float f1 = MathHelper.lerp(p_228396_1_, p_228396_4_.prevRotationPitch, p_228396_4_.rotationPitch);
      boolean flag = true;
      boolean flag1 = true;
      if (p_228396_4_.isHandActive()) {
         ItemStack itemstack = p_228396_4_.getActiveItemStack();
         if (itemstack.getItem() instanceof net.minecraft.item.ShootableItem) {
            flag = p_228396_4_.getActiveHand() == Hand.MAIN_HAND;
            flag1 = !flag;
         }

         Hand hand1 = p_228396_4_.getActiveHand();
         if (hand1 == Hand.MAIN_HAND) {
            ItemStack itemstack1 = p_228396_4_.getHeldItemOffhand();
            if (itemstack1.getItem() instanceof CrossbowItem && CrossbowItem.isCharged(itemstack1)) {
               flag1 = false;
            }
         }
      } else {
         ItemStack itemstack2 = p_228396_4_.getHeldItemMainhand();
         ItemStack itemstack3 = p_228396_4_.getHeldItemOffhand();
         if (itemstack2.getItem() instanceof CrossbowItem && CrossbowItem.isCharged(itemstack2)) {
            flag1 = !flag;
         }

         if (itemstack3.getItem() instanceof CrossbowItem && CrossbowItem.isCharged(itemstack3)) {
            flag = !itemstack2.isEmpty();
            flag1 = !flag;
         }
      }

      float f3 = MathHelper.lerp(p_228396_1_, p_228396_4_.prevRenderArmPitch, p_228396_4_.renderArmPitch);
      float f4 = MathHelper.lerp(p_228396_1_, p_228396_4_.prevRenderArmYaw, p_228396_4_.renderArmYaw);
      p_228396_2_.func_227863_a_(Vector3f.field_229179_b_.func_229187_a_((p_228396_4_.getPitch(p_228396_1_) - f3) * 0.1F));
      p_228396_2_.func_227863_a_(Vector3f.field_229181_d_.func_229187_a_((p_228396_4_.getYaw(p_228396_1_) - f4) * 0.1F));
      if (flag) {
         float f5 = hand == Hand.MAIN_HAND ? f : 0.0F;
         float f2 = 1.0F - MathHelper.lerp(p_228396_1_, this.prevEquippedProgressMainHand, this.equippedProgressMainHand);
         if(!net.minecraftforge.client.ForgeHooksClient.renderSpecificFirstPersonHand(Hand.MAIN_HAND, p_228396_2_, p_228396_3_, p_228396_5_, p_228396_1_, f1, f5, f2, this.itemStackMainHand))
         this.func_228405_a_(p_228396_4_, p_228396_1_, f1, Hand.MAIN_HAND, f5, this.itemStackMainHand, f2, p_228396_2_, p_228396_3_, p_228396_5_);
      }

      if (flag1) {
         float f6 = hand == Hand.OFF_HAND ? f : 0.0F;
         float f7 = 1.0F - MathHelper.lerp(p_228396_1_, this.prevEquippedProgressOffHand, this.equippedProgressOffHand);
         if(!net.minecraftforge.client.ForgeHooksClient.renderSpecificFirstPersonHand(Hand.OFF_HAND, p_228396_2_, p_228396_3_, p_228396_5_, p_228396_1_, f1, f6, f7, this.itemStackOffHand))
         this.func_228405_a_(p_228396_4_, p_228396_1_, f1, Hand.OFF_HAND, f6, this.itemStackOffHand, f7, p_228396_2_, p_228396_3_, p_228396_5_);
      }

      p_228396_3_.func_228461_a_();
   }

   private void func_228405_a_(AbstractClientPlayerEntity p_228405_1_, float p_228405_2_, float p_228405_3_, Hand p_228405_4_, float p_228405_5_, ItemStack p_228405_6_, float p_228405_7_, MatrixStack p_228405_8_, IRenderTypeBuffer p_228405_9_, int p_228405_10_) {
      boolean flag = p_228405_4_ == Hand.MAIN_HAND;
      HandSide handside = flag ? p_228405_1_.getPrimaryHand() : p_228405_1_.getPrimaryHand().opposite();
      p_228405_8_.func_227860_a_();
      if (p_228405_6_.isEmpty()) {
         if (flag && !p_228405_1_.isInvisible()) {
            this.func_228401_a_(p_228405_8_, p_228405_9_, p_228405_10_, p_228405_7_, p_228405_5_, handside);
         }
      } else if (p_228405_6_.getItem() instanceof FilledMapItem) {
         if (flag && this.itemStackOffHand.isEmpty()) {
            this.func_228400_a_(p_228405_8_, p_228405_9_, p_228405_10_, p_228405_3_, p_228405_7_, p_228405_5_);
         } else {
            this.func_228402_a_(p_228405_8_, p_228405_9_, p_228405_10_, p_228405_7_, handside, p_228405_5_, p_228405_6_);
         }
      } else if (p_228405_6_.getItem() instanceof CrossbowItem) {
         boolean flag1 = CrossbowItem.isCharged(p_228405_6_);
         boolean flag2 = handside == HandSide.RIGHT;
         int i = flag2 ? 1 : -1;
         if (p_228405_1_.isHandActive() && p_228405_1_.getItemInUseCount() > 0 && p_228405_1_.getActiveHand() == p_228405_4_) {
            this.func_228406_b_(p_228405_8_, handside, p_228405_7_);
            p_228405_8_.func_227861_a_((double)((float)i * -0.4785682F), (double)-0.094387F, (double)0.05731531F);
            p_228405_8_.func_227863_a_(Vector3f.field_229179_b_.func_229187_a_(-11.935F));
            p_228405_8_.func_227863_a_(Vector3f.field_229181_d_.func_229187_a_((float)i * 65.3F));
            p_228405_8_.func_227863_a_(Vector3f.field_229183_f_.func_229187_a_((float)i * -9.785F));
            float f9 = (float)p_228405_6_.getUseDuration() - ((float)this.mc.player.getItemInUseCount() - p_228405_2_ + 1.0F);
            float f13 = f9 / (float)CrossbowItem.getChargeTime(p_228405_6_);
            if (f13 > 1.0F) {
               f13 = 1.0F;
            }

            if (f13 > 0.1F) {
               float f16 = MathHelper.sin((f9 - 0.1F) * 1.3F);
               float f3 = f13 - 0.1F;
               float f4 = f16 * f3;
               p_228405_8_.func_227861_a_((double)(f4 * 0.0F), (double)(f4 * 0.004F), (double)(f4 * 0.0F));
            }

            p_228405_8_.func_227861_a_((double)(f13 * 0.0F), (double)(f13 * 0.0F), (double)(f13 * 0.04F));
            p_228405_8_.func_227862_a_(1.0F, 1.0F, 1.0F + f13 * 0.2F);
            p_228405_8_.func_227863_a_(Vector3f.field_229180_c_.func_229187_a_((float)i * 45.0F));
         } else {
            float f = -0.4F * MathHelper.sin(MathHelper.sqrt(p_228405_5_) * (float)Math.PI);
            float f1 = 0.2F * MathHelper.sin(MathHelper.sqrt(p_228405_5_) * ((float)Math.PI * 2F));
            float f2 = -0.2F * MathHelper.sin(p_228405_5_ * (float)Math.PI);
            p_228405_8_.func_227861_a_((double)((float)i * f), (double)f1, (double)f2);
            this.func_228406_b_(p_228405_8_, handside, p_228405_7_);
            this.func_228399_a_(p_228405_8_, handside, p_228405_5_);
            if (flag1 && p_228405_5_ < 0.001F) {
               p_228405_8_.func_227861_a_((double)((float)i * -0.641864F), 0.0D, 0.0D);
               p_228405_8_.func_227863_a_(Vector3f.field_229181_d_.func_229187_a_((float)i * 10.0F));
            }
         }

         this.func_228397_a_(p_228405_1_, p_228405_6_, flag2 ? ItemCameraTransforms.TransformType.FIRST_PERSON_RIGHT_HAND : ItemCameraTransforms.TransformType.FIRST_PERSON_LEFT_HAND, !flag2, p_228405_8_, p_228405_9_, p_228405_10_);
      } else {
         boolean flag3 = handside == HandSide.RIGHT;
         if (p_228405_1_.isHandActive() && p_228405_1_.getItemInUseCount() > 0 && p_228405_1_.getActiveHand() == p_228405_4_) {
            int k = flag3 ? 1 : -1;
            switch(p_228405_6_.getUseAction()) {
            case NONE:
               this.func_228406_b_(p_228405_8_, handside, p_228405_7_);
               break;
            case EAT:
            case DRINK:
               this.func_228398_a_(p_228405_8_, p_228405_2_, handside, p_228405_6_);
               this.func_228406_b_(p_228405_8_, handside, p_228405_7_);
               break;
            case BLOCK:
               this.func_228406_b_(p_228405_8_, handside, p_228405_7_);
               break;
            case BOW:
               this.func_228406_b_(p_228405_8_, handside, p_228405_7_);
               p_228405_8_.func_227861_a_((double)((float)k * -0.2785682F), (double)0.18344387F, (double)0.15731531F);
               p_228405_8_.func_227863_a_(Vector3f.field_229179_b_.func_229187_a_(-13.935F));
               p_228405_8_.func_227863_a_(Vector3f.field_229181_d_.func_229187_a_((float)k * 35.3F));
               p_228405_8_.func_227863_a_(Vector3f.field_229183_f_.func_229187_a_((float)k * -9.785F));
               float f8 = (float)p_228405_6_.getUseDuration() - ((float)this.mc.player.getItemInUseCount() - p_228405_2_ + 1.0F);
               float f12 = f8 / 20.0F;
               f12 = (f12 * f12 + f12 * 2.0F) / 3.0F;
               if (f12 > 1.0F) {
                  f12 = 1.0F;
               }

               if (f12 > 0.1F) {
                  float f15 = MathHelper.sin((f8 - 0.1F) * 1.3F);
                  float f18 = f12 - 0.1F;
                  float f20 = f15 * f18;
                  p_228405_8_.func_227861_a_((double)(f20 * 0.0F), (double)(f20 * 0.004F), (double)(f20 * 0.0F));
               }

               p_228405_8_.func_227861_a_((double)(f12 * 0.0F), (double)(f12 * 0.0F), (double)(f12 * 0.04F));
               p_228405_8_.func_227862_a_(1.0F, 1.0F, 1.0F + f12 * 0.2F);
               p_228405_8_.func_227863_a_(Vector3f.field_229180_c_.func_229187_a_((float)k * 45.0F));
               break;
            case SPEAR:
               this.func_228406_b_(p_228405_8_, handside, p_228405_7_);
               p_228405_8_.func_227861_a_((double)((float)k * -0.5F), (double)0.7F, (double)0.1F);
               p_228405_8_.func_227863_a_(Vector3f.field_229179_b_.func_229187_a_(-55.0F));
               p_228405_8_.func_227863_a_(Vector3f.field_229181_d_.func_229187_a_((float)k * 35.3F));
               p_228405_8_.func_227863_a_(Vector3f.field_229183_f_.func_229187_a_((float)k * -9.785F));
               float f7 = (float)p_228405_6_.getUseDuration() - ((float)this.mc.player.getItemInUseCount() - p_228405_2_ + 1.0F);
               float f11 = f7 / 10.0F;
               if (f11 > 1.0F) {
                  f11 = 1.0F;
               }

               if (f11 > 0.1F) {
                  float f14 = MathHelper.sin((f7 - 0.1F) * 1.3F);
                  float f17 = f11 - 0.1F;
                  float f19 = f14 * f17;
                  p_228405_8_.func_227861_a_((double)(f19 * 0.0F), (double)(f19 * 0.004F), (double)(f19 * 0.0F));
               }

               p_228405_8_.func_227861_a_(0.0D, 0.0D, (double)(f11 * 0.2F));
               p_228405_8_.func_227862_a_(1.0F, 1.0F, 1.0F + f11 * 0.2F);
               p_228405_8_.func_227863_a_(Vector3f.field_229180_c_.func_229187_a_((float)k * 45.0F));
            }
         } else if (p_228405_1_.isSpinAttacking()) {
            this.func_228406_b_(p_228405_8_, handside, p_228405_7_);
            int j = flag3 ? 1 : -1;
            p_228405_8_.func_227861_a_((double)((float)j * -0.4F), (double)0.8F, (double)0.3F);
            p_228405_8_.func_227863_a_(Vector3f.field_229181_d_.func_229187_a_((float)j * 65.0F));
            p_228405_8_.func_227863_a_(Vector3f.field_229183_f_.func_229187_a_((float)j * -85.0F));
         } else {
            float f5 = -0.4F * MathHelper.sin(MathHelper.sqrt(p_228405_5_) * (float)Math.PI);
            float f6 = 0.2F * MathHelper.sin(MathHelper.sqrt(p_228405_5_) * ((float)Math.PI * 2F));
            float f10 = -0.2F * MathHelper.sin(p_228405_5_ * (float)Math.PI);
            int l = flag3 ? 1 : -1;
            p_228405_8_.func_227861_a_((double)((float)l * f5), (double)f6, (double)f10);
            this.func_228406_b_(p_228405_8_, handside, p_228405_7_);
            this.func_228399_a_(p_228405_8_, handside, p_228405_5_);
         }

         this.func_228397_a_(p_228405_1_, p_228405_6_, flag3 ? ItemCameraTransforms.TransformType.FIRST_PERSON_RIGHT_HAND : ItemCameraTransforms.TransformType.FIRST_PERSON_LEFT_HAND, !flag3, p_228405_8_, p_228405_9_, p_228405_10_);
      }

      p_228405_8_.func_227865_b_();
   }

   public void tick() {
      this.prevEquippedProgressMainHand = this.equippedProgressMainHand;
      this.prevEquippedProgressOffHand = this.equippedProgressOffHand;
      ClientPlayerEntity clientplayerentity = this.mc.player;
      ItemStack itemstack = clientplayerentity.getHeldItemMainhand();
      ItemStack itemstack1 = clientplayerentity.getHeldItemOffhand();
      if (clientplayerentity.isRowingBoat()) {
         this.equippedProgressMainHand = MathHelper.clamp(this.equippedProgressMainHand - 0.4F, 0.0F, 1.0F);
         this.equippedProgressOffHand = MathHelper.clamp(this.equippedProgressOffHand - 0.4F, 0.0F, 1.0F);
      } else {
         float f = clientplayerentity.getCooledAttackStrength(1.0F);
         boolean requipM = net.minecraftforge.client.ForgeHooksClient.shouldCauseReequipAnimation(this.itemStackMainHand, itemstack, clientplayerentity.inventory.currentItem);
         boolean requipO = net.minecraftforge.client.ForgeHooksClient.shouldCauseReequipAnimation(this.itemStackOffHand, itemstack1, -1);

         if (!requipM && !Objects.equals(this.itemStackMainHand, itemstack))
        	this.itemStackMainHand = itemstack;
         if (!requipO && !Objects.equals(this.itemStackOffHand, itemstack1))
            this.itemStackOffHand = itemstack1;

         this.equippedProgressMainHand += MathHelper.clamp((!requipM ? f * f * f : 0.0F) - this.equippedProgressMainHand, -0.4F, 0.4F);
         this.equippedProgressOffHand += MathHelper.clamp((float)(!requipO ? 1 : 0) - this.equippedProgressOffHand, -0.4F, 0.4F);
      }

      if (this.equippedProgressMainHand < 0.1F) {
         this.itemStackMainHand = itemstack;
      }

      if (this.equippedProgressOffHand < 0.1F) {
         this.itemStackOffHand = itemstack1;
      }

   }

   public void resetEquippedProgress(Hand hand) {
      if (hand == Hand.MAIN_HAND) {
         this.equippedProgressMainHand = 0.0F;
      } else {
         this.equippedProgressOffHand = 0.0F;
      }
   }
}