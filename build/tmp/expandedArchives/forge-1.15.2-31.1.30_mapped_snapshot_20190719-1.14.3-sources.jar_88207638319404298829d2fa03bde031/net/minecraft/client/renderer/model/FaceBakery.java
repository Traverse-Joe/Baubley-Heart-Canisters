package net.minecraft.client.renderer.model;

import javax.annotation.Nullable;
import net.minecraft.client.renderer.FaceDirection;
import net.minecraft.client.renderer.Matrix3f;
import net.minecraft.client.renderer.Matrix4f;
import net.minecraft.client.renderer.Quaternion;
import net.minecraft.client.renderer.TransformationMatrix;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.Vector4f;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3i;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FaceBakery {
   private static final float SCALE_ROTATION_22_5 = 1.0F / (float)Math.cos((double)((float)Math.PI / 8F)) - 1.0F;
   private static final float SCALE_ROTATION_GENERAL = 1.0F / (float)Math.cos((double)((float)Math.PI / 4F)) - 1.0F;

   public BakedQuad func_228824_a_(Vector3f p_228824_1_, Vector3f p_228824_2_, BlockPartFace p_228824_3_, TextureAtlasSprite p_228824_4_, Direction p_228824_5_, IModelTransform p_228824_6_, @Nullable BlockPartRotation p_228824_7_, boolean p_228824_8_, ResourceLocation p_228824_9_) {
      BlockFaceUV blockfaceuv = p_228824_3_.blockFaceUV;
      if (p_228824_6_.isUvLock()) {
         blockfaceuv = func_228821_a_(p_228824_3_.blockFaceUV, p_228824_5_, p_228824_6_.func_225615_b_(), p_228824_9_);
      }

      float[] afloat = new float[blockfaceuv.uvs.length];
      System.arraycopy(blockfaceuv.uvs, 0, afloat, 0, afloat.length);
      float f = p_228824_4_.func_229242_p_();
      float f1 = (blockfaceuv.uvs[0] + blockfaceuv.uvs[0] + blockfaceuv.uvs[2] + blockfaceuv.uvs[2]) / 4.0F;
      float f2 = (blockfaceuv.uvs[1] + blockfaceuv.uvs[1] + blockfaceuv.uvs[3] + blockfaceuv.uvs[3]) / 4.0F;
      blockfaceuv.uvs[0] = MathHelper.lerp(f, blockfaceuv.uvs[0], f1);
      blockfaceuv.uvs[2] = MathHelper.lerp(f, blockfaceuv.uvs[2], f1);
      blockfaceuv.uvs[1] = MathHelper.lerp(f, blockfaceuv.uvs[1], f2);
      blockfaceuv.uvs[3] = MathHelper.lerp(f, blockfaceuv.uvs[3], f2);
      // FORGE: Apply diffuse lighting at render-time instead of baking it in
      int[] aint = this.func_228820_a_(blockfaceuv, p_228824_4_, p_228824_5_, this.getPositionsDiv16(p_228824_1_, p_228824_2_), p_228824_6_.func_225615_b_(), p_228824_7_, false);
      Direction direction = getFacingFromVertexData(aint);
      System.arraycopy(afloat, 0, blockfaceuv.uvs, 0, afloat.length);
      if (p_228824_7_ == null) {
         this.applyFacing(aint, direction);
      }

      net.minecraftforge.client.ForgeHooksClient.fillNormal(aint, direction);
      return new BakedQuad(aint, p_228824_3_.tintIndex, direction, p_228824_4_, p_228824_8_);
   }

   public static BlockFaceUV func_228821_a_(BlockFaceUV p_228821_0_, Direction p_228821_1_, TransformationMatrix p_228821_2_, ResourceLocation p_228821_3_) {
      Matrix4f matrix4f = UVTransformationUtil.func_229380_a_(p_228821_2_, p_228821_1_, () -> {
         return "Unable to resolve UVLock for model: " + p_228821_3_;
      }).func_227988_c_();
      float f = p_228821_0_.getVertexU(p_228821_0_.getVertexRotatedRev(0));
      float f1 = p_228821_0_.getVertexV(p_228821_0_.getVertexRotatedRev(0));
      Vector4f vector4f = new Vector4f(f / 16.0F, f1 / 16.0F, 0.0F, 1.0F);
      vector4f.func_229372_a_(matrix4f);
      float f2 = 16.0F * vector4f.getX();
      float f3 = 16.0F * vector4f.getY();
      float f4 = p_228821_0_.getVertexU(p_228821_0_.getVertexRotatedRev(2));
      float f5 = p_228821_0_.getVertexV(p_228821_0_.getVertexRotatedRev(2));
      Vector4f vector4f1 = new Vector4f(f4 / 16.0F, f5 / 16.0F, 0.0F, 1.0F);
      vector4f1.func_229372_a_(matrix4f);
      float f6 = 16.0F * vector4f1.getX();
      float f7 = 16.0F * vector4f1.getY();
      float f8;
      float f9;
      if (Math.signum(f4 - f) == Math.signum(f6 - f2)) {
         f8 = f2;
         f9 = f6;
      } else {
         f8 = f6;
         f9 = f2;
      }

      float f10;
      float f11;
      if (Math.signum(f5 - f1) == Math.signum(f7 - f3)) {
         f10 = f3;
         f11 = f7;
      } else {
         f10 = f7;
         f11 = f3;
      }

      float f12 = (float)Math.toRadians((double)p_228821_0_.rotation);
      Vector3f vector3f = new Vector3f(MathHelper.cos(f12), MathHelper.sin(f12), 0.0F);
      Matrix3f matrix3f = new Matrix3f(matrix4f);
      vector3f.func_229188_a_(matrix3f);
      int i = Math.floorMod(-((int)Math.round(Math.toDegrees(Math.atan2((double)vector3f.getY(), (double)vector3f.getX())) / 90.0D)) * 90, 360);
      return new BlockFaceUV(new float[]{f8, f10, f9, f11}, i);
   }

   private int[] func_228820_a_(BlockFaceUV p_228820_1_, TextureAtlasSprite p_228820_2_, Direction p_228820_3_, float[] p_228820_4_, TransformationMatrix p_228820_5_, @Nullable BlockPartRotation p_228820_6_, boolean p_228820_7_) {
      int[] aint = new int[32];

      for(int i = 0; i < 4; ++i) {
         this.func_228827_a_(aint, i, p_228820_3_, p_228820_1_, p_228820_4_, p_228820_2_, p_228820_5_, p_228820_6_, p_228820_7_);
      }

      return aint;
   }

   private int getFaceShadeColor(Direction facing) {
      float f = this.getFaceBrightness(facing);
      int i = MathHelper.clamp((int)(f * 255.0F), 0, 255);
      return -16777216 | i << 16 | i << 8 | i;
   }

   private float getFaceBrightness(Direction facing) {
      switch(facing) {
      case DOWN:
         return 0.5F;
      case UP:
         return 1.0F;
      case NORTH:
      case SOUTH:
         return 0.8F;
      case WEST:
      case EAST:
         return 0.6F;
      default:
         return 1.0F;
      }
   }

   private float[] getPositionsDiv16(Vector3f pos1, Vector3f pos2) {
      float[] afloat = new float[Direction.values().length];
      afloat[FaceDirection.Constants.WEST_INDEX] = pos1.getX() / 16.0F;
      afloat[FaceDirection.Constants.DOWN_INDEX] = pos1.getY() / 16.0F;
      afloat[FaceDirection.Constants.NORTH_INDEX] = pos1.getZ() / 16.0F;
      afloat[FaceDirection.Constants.EAST_INDEX] = pos2.getX() / 16.0F;
      afloat[FaceDirection.Constants.UP_INDEX] = pos2.getY() / 16.0F;
      afloat[FaceDirection.Constants.SOUTH_INDEX] = pos2.getZ() / 16.0F;
      return afloat;
   }

   private void func_228827_a_(int[] p_228827_1_, int p_228827_2_, Direction p_228827_3_, BlockFaceUV p_228827_4_, float[] p_228827_5_, TextureAtlasSprite p_228827_6_, TransformationMatrix p_228827_7_, @Nullable BlockPartRotation p_228827_8_, boolean p_228827_9_) {
      Direction direction = Direction.func_229385_a_(p_228827_7_.func_227988_c_(), p_228827_3_);
      int i = p_228827_9_ ? this.getFaceShadeColor(direction) : -1;
      FaceDirection.VertexInformation facedirection$vertexinformation = FaceDirection.getFacing(p_228827_3_).getVertexInformation(p_228827_2_);
      Vector3f vector3f = new Vector3f(p_228827_5_[facedirection$vertexinformation.xIndex], p_228827_5_[facedirection$vertexinformation.yIndex], p_228827_5_[facedirection$vertexinformation.zIndex]);
      this.rotatePart(vector3f, p_228827_8_);
      this.func_228822_a_(vector3f, p_228827_7_);
      this.func_228826_a_(p_228827_1_, p_228827_2_, vector3f, i, p_228827_6_, p_228827_4_);
   }

   private void func_228826_a_(int[] p_228826_1_, int p_228826_2_, Vector3f p_228826_3_, int p_228826_4_, TextureAtlasSprite p_228826_5_, BlockFaceUV p_228826_6_) {
      int i = p_228826_2_ * 8;
      p_228826_1_[i] = Float.floatToRawIntBits(p_228826_3_.getX());
      p_228826_1_[i + 1] = Float.floatToRawIntBits(p_228826_3_.getY());
      p_228826_1_[i + 2] = Float.floatToRawIntBits(p_228826_3_.getZ());
      p_228826_1_[i + 3] = p_228826_4_;
      p_228826_1_[i + 4] = Float.floatToRawIntBits(p_228826_5_.getInterpolatedU((double)p_228826_6_.getVertexU(p_228826_2_)  * .999 + p_228826_6_.getVertexU((p_228826_2_ + 2) % 4) * .001));
      p_228826_1_[i + 4 + 1] = Float.floatToRawIntBits(p_228826_5_.getInterpolatedV((double)p_228826_6_.getVertexV(p_228826_2_) * .999 + p_228826_6_.getVertexV((p_228826_2_ + 2) % 4) * .001));
   }

   private void rotatePart(Vector3f vec, @Nullable BlockPartRotation partRotation) {
      if (partRotation != null) {
         Vector3f vector3f;
         Vector3f vector3f1;
         switch(partRotation.axis) {
         case X:
            vector3f = new Vector3f(1.0F, 0.0F, 0.0F);
            vector3f1 = new Vector3f(0.0F, 1.0F, 1.0F);
            break;
         case Y:
            vector3f = new Vector3f(0.0F, 1.0F, 0.0F);
            vector3f1 = new Vector3f(1.0F, 0.0F, 1.0F);
            break;
         case Z:
            vector3f = new Vector3f(0.0F, 0.0F, 1.0F);
            vector3f1 = new Vector3f(1.0F, 1.0F, 0.0F);
            break;
         default:
            throw new IllegalArgumentException("There are only 3 axes");
         }

         Quaternion quaternion = new Quaternion(vector3f, partRotation.angle, true);
         if (partRotation.rescale) {
            if (Math.abs(partRotation.angle) == 22.5F) {
               vector3f1.mul(SCALE_ROTATION_22_5);
            } else {
               vector3f1.mul(SCALE_ROTATION_GENERAL);
            }

            vector3f1.add(1.0F, 1.0F, 1.0F);
         } else {
            vector3f1.set(1.0F, 1.0F, 1.0F);
         }

         this.func_228823_a_(vec, partRotation.origin.func_229195_e_(), new Matrix4f(quaternion), vector3f1);
      }
   }

   public void func_228822_a_(Vector3f p_228822_1_, TransformationMatrix p_228822_2_) {
      if (p_228822_2_ != TransformationMatrix.func_227983_a_()) {
         this.func_228823_a_(p_228822_1_, new Vector3f(0.5F, 0.5F, 0.5F), p_228822_2_.func_227988_c_(), new Vector3f(1.0F, 1.0F, 1.0F));
      }
   }

   private void func_228823_a_(Vector3f p_228823_1_, Vector3f p_228823_2_, Matrix4f p_228823_3_, Vector3f p_228823_4_) {
      Vector4f vector4f = new Vector4f(p_228823_1_.getX() - p_228823_2_.getX(), p_228823_1_.getY() - p_228823_2_.getY(), p_228823_1_.getZ() - p_228823_2_.getZ(), 1.0F);
      vector4f.func_229372_a_(p_228823_3_);
      vector4f.scale(p_228823_4_);
      p_228823_1_.set(vector4f.getX() + p_228823_2_.getX(), vector4f.getY() + p_228823_2_.getY(), vector4f.getZ() + p_228823_2_.getZ());
   }

   public static Direction getFacingFromVertexData(int[] faceData) {
      Vector3f vector3f = new Vector3f(Float.intBitsToFloat(faceData[0]), Float.intBitsToFloat(faceData[1]), Float.intBitsToFloat(faceData[2]));
      Vector3f vector3f1 = new Vector3f(Float.intBitsToFloat(faceData[8]), Float.intBitsToFloat(faceData[9]), Float.intBitsToFloat(faceData[10]));
      Vector3f vector3f2 = new Vector3f(Float.intBitsToFloat(faceData[16]), Float.intBitsToFloat(faceData[17]), Float.intBitsToFloat(faceData[18]));
      Vector3f vector3f3 = vector3f.func_229195_e_();
      vector3f3.sub(vector3f1);
      Vector3f vector3f4 = vector3f2.func_229195_e_();
      vector3f4.sub(vector3f1);
      Vector3f vector3f5 = vector3f4.func_229195_e_();
      vector3f5.cross(vector3f3);
      vector3f5.func_229194_d_();
      Direction direction = null;
      float f = 0.0F;

      for(Direction direction1 : Direction.values()) {
         Vec3i vec3i = direction1.getDirectionVec();
         Vector3f vector3f6 = new Vector3f((float)vec3i.getX(), (float)vec3i.getY(), (float)vec3i.getZ());
         float f1 = vector3f5.dot(vector3f6);
         if (f1 >= 0.0F && f1 > f) {
            f = f1;
            direction = direction1;
         }
      }

      return direction == null ? Direction.UP : direction;
   }

   private void applyFacing(int[] p_178408_1_, Direction p_178408_2_) {
      int[] aint = new int[p_178408_1_.length];
      System.arraycopy(p_178408_1_, 0, aint, 0, p_178408_1_.length);
      float[] afloat = new float[Direction.values().length];
      afloat[FaceDirection.Constants.WEST_INDEX] = 999.0F;
      afloat[FaceDirection.Constants.DOWN_INDEX] = 999.0F;
      afloat[FaceDirection.Constants.NORTH_INDEX] = 999.0F;
      afloat[FaceDirection.Constants.EAST_INDEX] = -999.0F;
      afloat[FaceDirection.Constants.UP_INDEX] = -999.0F;
      afloat[FaceDirection.Constants.SOUTH_INDEX] = -999.0F;

      for(int i = 0; i < 4; ++i) {
         int j = 8 * i;
         float f = Float.intBitsToFloat(aint[j]);
         float f1 = Float.intBitsToFloat(aint[j + 1]);
         float f2 = Float.intBitsToFloat(aint[j + 2]);
         if (f < afloat[FaceDirection.Constants.WEST_INDEX]) {
            afloat[FaceDirection.Constants.WEST_INDEX] = f;
         }

         if (f1 < afloat[FaceDirection.Constants.DOWN_INDEX]) {
            afloat[FaceDirection.Constants.DOWN_INDEX] = f1;
         }

         if (f2 < afloat[FaceDirection.Constants.NORTH_INDEX]) {
            afloat[FaceDirection.Constants.NORTH_INDEX] = f2;
         }

         if (f > afloat[FaceDirection.Constants.EAST_INDEX]) {
            afloat[FaceDirection.Constants.EAST_INDEX] = f;
         }

         if (f1 > afloat[FaceDirection.Constants.UP_INDEX]) {
            afloat[FaceDirection.Constants.UP_INDEX] = f1;
         }

         if (f2 > afloat[FaceDirection.Constants.SOUTH_INDEX]) {
            afloat[FaceDirection.Constants.SOUTH_INDEX] = f2;
         }
      }

      FaceDirection facedirection = FaceDirection.getFacing(p_178408_2_);

      for(int i1 = 0; i1 < 4; ++i1) {
         int j1 = 8 * i1;
         FaceDirection.VertexInformation facedirection$vertexinformation = facedirection.getVertexInformation(i1);
         float f8 = afloat[facedirection$vertexinformation.xIndex];
         float f3 = afloat[facedirection$vertexinformation.yIndex];
         float f4 = afloat[facedirection$vertexinformation.zIndex];
         p_178408_1_[j1] = Float.floatToRawIntBits(f8);
         p_178408_1_[j1 + 1] = Float.floatToRawIntBits(f3);
         p_178408_1_[j1 + 2] = Float.floatToRawIntBits(f4);

         for(int k = 0; k < 4; ++k) {
            int l = 8 * k;
            float f5 = Float.intBitsToFloat(aint[l]);
            float f6 = Float.intBitsToFloat(aint[l + 1]);
            float f7 = Float.intBitsToFloat(aint[l + 2]);
            if (MathHelper.epsilonEquals(f8, f5) && MathHelper.epsilonEquals(f3, f6) && MathHelper.epsilonEquals(f4, f7)) {
               p_178408_1_[j1 + 4] = aint[l + 4];
               p_178408_1_[j1 + 4 + 1] = aint[l + 4 + 1];
            }
         }
      }

   }
}