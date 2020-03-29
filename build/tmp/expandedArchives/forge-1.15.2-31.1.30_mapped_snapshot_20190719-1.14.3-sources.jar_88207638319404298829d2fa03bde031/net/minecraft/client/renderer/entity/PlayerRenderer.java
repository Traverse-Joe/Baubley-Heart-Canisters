package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.entity.layers.ArrowLayer;
import net.minecraft.client.renderer.entity.layers.BeeStingerLayer;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.client.renderer.entity.layers.CapeLayer;
import net.minecraft.client.renderer.entity.layers.Deadmau5HeadLayer;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.client.renderer.entity.layers.HeadLayer;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import net.minecraft.client.renderer.entity.layers.ParrotVariantLayer;
import net.minecraft.client.renderer.entity.layers.SpinAttackEffectLayer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerModelPart;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.UseAction;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PlayerRenderer extends LivingRenderer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>> {
   public PlayerRenderer(EntityRendererManager renderManager) {
      this(renderManager, false);
   }

   public PlayerRenderer(EntityRendererManager renderManager, boolean useSmallArms) {
      super(renderManager, new PlayerModel<>(0.0F, useSmallArms), 0.5F);
      this.addLayer(new BipedArmorLayer<>(this, new BipedModel(0.5F), new BipedModel(1.0F)));
      this.addLayer(new HeldItemLayer<>(this));
      this.addLayer(new ArrowLayer<>(this));
      this.addLayer(new Deadmau5HeadLayer(this));
      this.addLayer(new CapeLayer(this));
      this.addLayer(new HeadLayer<>(this));
      this.addLayer(new ElytraLayer<>(this));
      this.addLayer(new ParrotVariantLayer<>(this));
      this.addLayer(new SpinAttackEffectLayer<>(this));
      this.addLayer(new BeeStingerLayer<>(this));
   }

   public void func_225623_a_(AbstractClientPlayerEntity p_225623_1_, float p_225623_2_, float p_225623_3_, MatrixStack p_225623_4_, IRenderTypeBuffer p_225623_5_, int p_225623_6_) {
      this.setModelVisibilities(p_225623_1_);
      if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.RenderPlayerEvent.Pre(p_225623_1_, this, p_225623_3_, p_225623_4_, p_225623_5_, p_225623_6_))) return;
      super.func_225623_a_(p_225623_1_, p_225623_2_, p_225623_3_, p_225623_4_, p_225623_5_, p_225623_6_);
      net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.RenderPlayerEvent.Post(p_225623_1_, this, p_225623_3_, p_225623_4_, p_225623_5_, p_225623_6_));
   }

   public Vec3d func_225627_b_(AbstractClientPlayerEntity p_225627_1_, float p_225627_2_) {
      return p_225627_1_.isCrouching() ? new Vec3d(0.0D, -0.125D, 0.0D) : super.func_225627_b_(p_225627_1_, p_225627_2_);
   }

   private void setModelVisibilities(AbstractClientPlayerEntity clientPlayer) {
      PlayerModel<AbstractClientPlayerEntity> playermodel = this.getEntityModel();
      if (clientPlayer.isSpectator()) {
         playermodel.setVisible(false);
         playermodel.bipedHead.showModel = true;
         playermodel.bipedHeadwear.showModel = true;
      } else {
         ItemStack itemstack = clientPlayer.getHeldItemMainhand();
         ItemStack itemstack1 = clientPlayer.getHeldItemOffhand();
         playermodel.setVisible(true);
         playermodel.bipedHeadwear.showModel = clientPlayer.isWearing(PlayerModelPart.HAT);
         playermodel.bipedBodyWear.showModel = clientPlayer.isWearing(PlayerModelPart.JACKET);
         playermodel.bipedLeftLegwear.showModel = clientPlayer.isWearing(PlayerModelPart.LEFT_PANTS_LEG);
         playermodel.bipedRightLegwear.showModel = clientPlayer.isWearing(PlayerModelPart.RIGHT_PANTS_LEG);
         playermodel.bipedLeftArmwear.showModel = clientPlayer.isWearing(PlayerModelPart.LEFT_SLEEVE);
         playermodel.bipedRightArmwear.showModel = clientPlayer.isWearing(PlayerModelPart.RIGHT_SLEEVE);
         playermodel.field_228270_o_ = clientPlayer.isCrouching();
         BipedModel.ArmPose bipedmodel$armpose = this.func_217766_a(clientPlayer, itemstack, itemstack1, Hand.MAIN_HAND);
         BipedModel.ArmPose bipedmodel$armpose1 = this.func_217766_a(clientPlayer, itemstack, itemstack1, Hand.OFF_HAND);
         if (clientPlayer.getPrimaryHand() == HandSide.RIGHT) {
            playermodel.rightArmPose = bipedmodel$armpose;
            playermodel.leftArmPose = bipedmodel$armpose1;
         } else {
            playermodel.rightArmPose = bipedmodel$armpose1;
            playermodel.leftArmPose = bipedmodel$armpose;
         }
      }

   }

   private BipedModel.ArmPose func_217766_a(AbstractClientPlayerEntity p_217766_1_, ItemStack p_217766_2_, ItemStack p_217766_3_, Hand p_217766_4_) {
      BipedModel.ArmPose bipedmodel$armpose = BipedModel.ArmPose.EMPTY;
      ItemStack itemstack = p_217766_4_ == Hand.MAIN_HAND ? p_217766_2_ : p_217766_3_;
      if (!itemstack.isEmpty()) {
         bipedmodel$armpose = BipedModel.ArmPose.ITEM;
         if (p_217766_1_.getItemInUseCount() > 0) {
            UseAction useaction = itemstack.getUseAction();
            if (useaction == UseAction.BLOCK) {
               bipedmodel$armpose = BipedModel.ArmPose.BLOCK;
            } else if (useaction == UseAction.BOW) {
               bipedmodel$armpose = BipedModel.ArmPose.BOW_AND_ARROW;
            } else if (useaction == UseAction.SPEAR) {
               bipedmodel$armpose = BipedModel.ArmPose.THROW_SPEAR;
            } else if (useaction == UseAction.CROSSBOW && p_217766_4_ == p_217766_1_.getActiveHand()) {
               bipedmodel$armpose = BipedModel.ArmPose.CROSSBOW_CHARGE;
            }
         } else {
            boolean flag3 = p_217766_2_.getItem() == Items.CROSSBOW;
            boolean flag = CrossbowItem.isCharged(p_217766_2_);
            boolean flag1 = p_217766_3_.getItem() == Items.CROSSBOW;
            boolean flag2 = CrossbowItem.isCharged(p_217766_3_);
            if (flag3 && flag) {
               bipedmodel$armpose = BipedModel.ArmPose.CROSSBOW_HOLD;
            }

            if (flag1 && flag2 && p_217766_2_.getItem().getUseAction(p_217766_2_) == UseAction.NONE) {
               bipedmodel$armpose = BipedModel.ArmPose.CROSSBOW_HOLD;
            }
         }
      }

      return bipedmodel$armpose;
   }

   public ResourceLocation getEntityTexture(AbstractClientPlayerEntity entity) {
      return entity.getLocationSkin();
   }

   protected void func_225620_a_(AbstractClientPlayerEntity p_225620_1_, MatrixStack p_225620_2_, float p_225620_3_) {
      float f = 0.9375F;
      p_225620_2_.func_227862_a_(0.9375F, 0.9375F, 0.9375F);
   }

   protected void func_225629_a_(AbstractClientPlayerEntity p_225629_1_, String p_225629_2_, MatrixStack p_225629_3_, IRenderTypeBuffer p_225629_4_, int p_225629_5_) {
      double d0 = this.renderManager.func_229099_b_(p_225629_1_);
      p_225629_3_.func_227860_a_();
      if (d0 < 100.0D) {
         Scoreboard scoreboard = p_225629_1_.getWorldScoreboard();
         ScoreObjective scoreobjective = scoreboard.getObjectiveInDisplaySlot(2);
         if (scoreobjective != null) {
            Score score = scoreboard.getOrCreateScore(p_225629_1_.getScoreboardName(), scoreobjective);
            super.func_225629_a_(p_225629_1_, score.getScorePoints() + " " + scoreobjective.getDisplayName().getFormattedText(), p_225629_3_, p_225629_4_, p_225629_5_);
            p_225629_3_.func_227861_a_(0.0D, (double)(9.0F * 1.15F * 0.025F), 0.0D);
         }
      }

      super.func_225629_a_(p_225629_1_, p_225629_2_, p_225629_3_, p_225629_4_, p_225629_5_);
      p_225629_3_.func_227865_b_();
   }

   public void func_229144_a_(MatrixStack p_229144_1_, IRenderTypeBuffer p_229144_2_, int p_229144_3_, AbstractClientPlayerEntity p_229144_4_) {
      this.func_229145_a_(p_229144_1_, p_229144_2_, p_229144_3_, p_229144_4_, (this.entityModel).bipedRightArm, (this.entityModel).bipedRightArmwear);
   }

   public void func_229146_b_(MatrixStack p_229146_1_, IRenderTypeBuffer p_229146_2_, int p_229146_3_, AbstractClientPlayerEntity p_229146_4_) {
      this.func_229145_a_(p_229146_1_, p_229146_2_, p_229146_3_, p_229146_4_, (this.entityModel).bipedLeftArm, (this.entityModel).bipedLeftArmwear);
   }

   private void func_229145_a_(MatrixStack p_229145_1_, IRenderTypeBuffer p_229145_2_, int p_229145_3_, AbstractClientPlayerEntity p_229145_4_, ModelRenderer p_229145_5_, ModelRenderer p_229145_6_) {
      PlayerModel<AbstractClientPlayerEntity> playermodel = this.getEntityModel();
      this.setModelVisibilities(p_229145_4_);
      playermodel.swingProgress = 0.0F;
      playermodel.field_228270_o_ = false;
      playermodel.swimAnimation = 0.0F;
      playermodel.func_225597_a_(p_229145_4_, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
      p_229145_5_.rotateAngleX = 0.0F;
      p_229145_5_.func_228308_a_(p_229145_1_, p_229145_2_.getBuffer(RenderType.func_228634_a_(p_229145_4_.getLocationSkin())), p_229145_3_, OverlayTexture.field_229196_a_);
      p_229145_6_.rotateAngleX = 0.0F;
      p_229145_6_.func_228308_a_(p_229145_1_, p_229145_2_.getBuffer(RenderType.func_228644_e_(p_229145_4_.getLocationSkin())), p_229145_3_, OverlayTexture.field_229196_a_);
   }

   protected void func_225621_a_(AbstractClientPlayerEntity p_225621_1_, MatrixStack p_225621_2_, float p_225621_3_, float p_225621_4_, float p_225621_5_) {
      float f = p_225621_1_.getSwimAnimation(p_225621_5_);
      if (p_225621_1_.isElytraFlying()) {
         super.func_225621_a_(p_225621_1_, p_225621_2_, p_225621_3_, p_225621_4_, p_225621_5_);
         float f1 = (float)p_225621_1_.getTicksElytraFlying() + p_225621_5_;
         float f2 = MathHelper.clamp(f1 * f1 / 100.0F, 0.0F, 1.0F);
         if (!p_225621_1_.isSpinAttacking()) {
            p_225621_2_.func_227863_a_(Vector3f.field_229179_b_.func_229187_a_(f2 * (-90.0F - p_225621_1_.rotationPitch)));
         }

         Vec3d vec3d = p_225621_1_.getLook(p_225621_5_);
         Vec3d vec3d1 = p_225621_1_.getMotion();
         double d0 = Entity.func_213296_b(vec3d1);
         double d1 = Entity.func_213296_b(vec3d);
         if (d0 > 0.0D && d1 > 0.0D) {
            double d2 = (vec3d1.x * vec3d.x + vec3d1.z * vec3d.z) / (Math.sqrt(d0) * Math.sqrt(d1));
            double d3 = vec3d1.x * vec3d.z - vec3d1.z * vec3d.x;
            p_225621_2_.func_227863_a_(Vector3f.field_229181_d_.func_229193_c_((float)(Math.signum(d3) * Math.acos(d2))));
         }
      } else if (f > 0.0F) {
         super.func_225621_a_(p_225621_1_, p_225621_2_, p_225621_3_, p_225621_4_, p_225621_5_);
         float f3 = p_225621_1_.isInWater() ? -90.0F - p_225621_1_.rotationPitch : -90.0F;
         float f4 = MathHelper.lerp(f, 0.0F, f3);
         p_225621_2_.func_227863_a_(Vector3f.field_229179_b_.func_229187_a_(f4));
         if (p_225621_1_.func_213314_bj()) {
            p_225621_2_.func_227861_a_(0.0D, -1.0D, (double)0.3F);
         }
      } else {
         super.func_225621_a_(p_225621_1_, p_225621_2_, p_225621_3_, p_225621_4_, p_225621_5_);
      }

   }
}