package net.minecraft.client.renderer;

import com.mojang.datafixers.util.Pair;
import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.util.Util;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.tuple.Triple;

@OnlyIn(Dist.CLIENT)
public final class TransformationMatrix implements net.minecraftforge.client.extensions.IForgeTransformationMatrix {
   private final Matrix4f field_227976_a_;
   private boolean field_227977_b_;
   @Nullable
   private Vector3f field_227978_c_;
   @Nullable
   private Quaternion field_227979_d_;
   @Nullable
   private Vector3f field_227980_e_;
   @Nullable
   private Quaternion field_227981_f_;
   private static final TransformationMatrix field_227982_g_ = Util.make(() -> {
      Matrix4f matrix4f = new Matrix4f();
      matrix4f.func_226591_a_();
      TransformationMatrix transformationmatrix = new TransformationMatrix(matrix4f);
      transformationmatrix.func_227989_d_();
      return transformationmatrix;
   });

   public TransformationMatrix(@Nullable Matrix4f p_i225915_1_) {
      if (p_i225915_1_ == null) {
         this.field_227976_a_ = field_227982_g_.field_227976_a_;
      } else {
         this.field_227976_a_ = p_i225915_1_;
      }

   }

   public TransformationMatrix(@Nullable Vector3f p_i225916_1_, @Nullable Quaternion p_i225916_2_, @Nullable Vector3f p_i225916_3_, @Nullable Quaternion p_i225916_4_) {
      this.field_227976_a_ = func_227986_a_(p_i225916_1_, p_i225916_2_, p_i225916_3_, p_i225916_4_);
      this.field_227978_c_ = p_i225916_1_ != null ? p_i225916_1_ : new Vector3f();
      this.field_227979_d_ = p_i225916_2_ != null ? p_i225916_2_ : Quaternion.field_227060_a_.func_227068_g_();
      this.field_227980_e_ = p_i225916_3_ != null ? p_i225916_3_ : new Vector3f(1.0F, 1.0F, 1.0F);
      this.field_227981_f_ = p_i225916_4_ != null ? p_i225916_4_ : Quaternion.field_227060_a_.func_227068_g_();
      this.field_227977_b_ = true;
   }

   public static TransformationMatrix func_227983_a_() {
      return field_227982_g_;
   }

   public TransformationMatrix func_227985_a_(TransformationMatrix p_227985_1_) {
      Matrix4f matrix4f = this.func_227988_c_();
      matrix4f.func_226595_a_(p_227985_1_.func_227988_c_());
      return new TransformationMatrix(matrix4f);
   }

   @Nullable
   public TransformationMatrix func_227987_b_() {
      if (this == field_227982_g_) {
         return this;
      } else {
         Matrix4f matrix4f = this.func_227988_c_();
         return matrix4f.func_226600_c_() ? new TransformationMatrix(matrix4f) : null;
      }
   }

   private void func_227990_e_() {
      if (!this.field_227977_b_) {
         Pair<Matrix3f, Vector3f> pair = func_227984_a_(this.field_227976_a_);
         Triple<Quaternion, Vector3f, Quaternion> triple = pair.getFirst().func_226116_b_();
         this.field_227978_c_ = pair.getSecond();
         this.field_227979_d_ = triple.getLeft();
         this.field_227980_e_ = triple.getMiddle();
         this.field_227981_f_ = triple.getRight();
         this.field_227977_b_ = true;
      }

   }

   private static Matrix4f func_227986_a_(@Nullable Vector3f p_227986_0_, @Nullable Quaternion p_227986_1_, @Nullable Vector3f p_227986_2_, @Nullable Quaternion p_227986_3_) {
      Matrix4f matrix4f = new Matrix4f();
      matrix4f.func_226591_a_();
      if (p_227986_1_ != null) {
         matrix4f.func_226595_a_(new Matrix4f(p_227986_1_));
      }

      if (p_227986_2_ != null) {
         matrix4f.func_226595_a_(Matrix4f.func_226593_a_(p_227986_2_.getX(), p_227986_2_.getY(), p_227986_2_.getZ()));
      }

      if (p_227986_3_ != null) {
         matrix4f.func_226595_a_(new Matrix4f(p_227986_3_));
      }

      if (p_227986_0_ != null) {
         matrix4f.field_226578_d_ = p_227986_0_.getX();
         matrix4f.field_226582_h_ = p_227986_0_.getY();
         matrix4f.field_226586_l_ = p_227986_0_.getZ();
      }

      return matrix4f;
   }

   public static Pair<Matrix3f, Vector3f> func_227984_a_(Matrix4f p_227984_0_) {
      p_227984_0_.func_226592_a_(1.0F / p_227984_0_.field_226590_p_);
      Vector3f vector3f = new Vector3f(p_227984_0_.field_226578_d_, p_227984_0_.field_226582_h_, p_227984_0_.field_226586_l_);
      Matrix3f matrix3f = new Matrix3f(p_227984_0_);
      return Pair.of(matrix3f, vector3f);
   }

   public Matrix4f func_227988_c_() {
      return this.field_227976_a_.func_226601_d_();
   }

   public Quaternion func_227989_d_() {
      this.func_227990_e_();
      return this.field_227979_d_.func_227068_g_();
   }

   public boolean equals(Object p_equals_1_) {
      if (this == p_equals_1_) {
         return true;
      } else if (p_equals_1_ != null && this.getClass() == p_equals_1_.getClass()) {
         TransformationMatrix transformationmatrix = (TransformationMatrix)p_equals_1_;
         return Objects.equals(this.field_227976_a_, transformationmatrix.field_227976_a_);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return Objects.hash(this.field_227976_a_);
   }

   // FORGE START
   public Vector3f getTranslation() {
      func_227990_e_();
      return field_227978_c_.func_229195_e_();
   }
   public Vector3f getScale() {
      func_227990_e_();
      return field_227980_e_.func_229195_e_();
   }

   public Quaternion getRightRot() {
      func_227990_e_();
      return field_227981_f_.func_227068_g_();
   }

   private Matrix3f normalTransform = null;
   public Matrix3f getNormalMatrix() {
      checkNormalTransform();
      return normalTransform;
   }
   private void checkNormalTransform() {
      if (normalTransform == null) {
         normalTransform = new Matrix3f(field_227976_a_);
         normalTransform.func_226123_f_();
         normalTransform.func_226110_a_();
      }
   }
}