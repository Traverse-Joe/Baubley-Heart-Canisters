package net.minecraft.client.resources.data;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.Set;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AnimationMetadataSection {
   public static final AnimationMetadataSectionSerializer SERIALIZER = new AnimationMetadataSectionSerializer();
   public static final AnimationMetadataSection field_229300_b_ = new AnimationMetadataSection(Lists.newArrayList(), -1, -1, 1, false) {
      public Pair<Integer, Integer> func_225641_a_(int p_225641_1_, int p_225641_2_) {
         return Pair.of(p_225641_1_, p_225641_2_);
      }
   };
   private final List<AnimationFrame> animationFrames;
   private final int frameWidth;
   private final int frameHeight;
   private final int frameTime;
   private final boolean interpolate;

   public AnimationMetadataSection(List<AnimationFrame> animationFramesIn, int frameWidthIn, int frameHeightIn, int frameTimeIn, boolean interpolateIn) {
      this.animationFrames = animationFramesIn;
      this.frameWidth = frameWidthIn;
      this.frameHeight = frameHeightIn;
      this.frameTime = frameTimeIn;
      this.interpolate = interpolateIn;
   }

   private static boolean func_229303_b_(int p_229303_0_, int p_229303_1_) {
      return p_229303_0_ / p_229303_1_ * p_229303_1_ == p_229303_0_;
   }

   public Pair<Integer, Integer> func_225641_a_(int p_225641_1_, int p_225641_2_) {
      Pair<Integer, Integer> pair = this.func_229304_c_(p_225641_1_, p_225641_2_);
      int i = pair.getFirst();
      int j = pair.getSecond();
      if (func_229303_b_(p_225641_1_, i) && func_229303_b_(p_225641_2_, j)) {
         return pair;
      } else {
         throw new IllegalArgumentException(String.format("Image size %s,%s is not multiply of frame size %s,%s", p_225641_1_, p_225641_2_, i, j));
      }
   }

   private Pair<Integer, Integer> func_229304_c_(int p_229304_1_, int p_229304_2_) {
      if (this.frameWidth != -1) {
         return this.frameHeight != -1 ? Pair.of(this.frameWidth, this.frameHeight) : Pair.of(this.frameWidth, p_229304_2_);
      } else if (this.frameHeight != -1) {
         return Pair.of(p_229304_1_, this.frameHeight);
      } else {
         int i = Math.min(p_229304_1_, p_229304_2_);
         return Pair.of(i, i);
      }
   }

   public int func_229301_a_(int p_229301_1_) {
      return this.frameHeight == -1 ? p_229301_1_ : this.frameHeight;
   }

   public int func_229302_b_(int p_229302_1_) {
      return this.frameWidth == -1 ? p_229302_1_ : this.frameWidth;
   }

   public int getFrameCount() {
      return this.animationFrames.size();
   }

   public int getFrameTime() {
      return this.frameTime;
   }

   public boolean isInterpolate() {
      return this.interpolate;
   }

   private AnimationFrame getAnimationFrame(int frame) {
      return this.animationFrames.get(frame);
   }

   public int getFrameTimeSingle(int frame) {
      AnimationFrame animationframe = this.getAnimationFrame(frame);
      return animationframe.hasNoTime() ? this.frameTime : animationframe.getFrameTime();
   }

   public int getFrameIndex(int frame) {
      return this.animationFrames.get(frame).getFrameIndex();
   }

   public Set<Integer> getFrameIndexSet() {
      Set<Integer> set = Sets.newHashSet();

      for(AnimationFrame animationframe : this.animationFrames) {
         set.add(animationframe.getFrameIndex());
      }

      return set;
   }
}