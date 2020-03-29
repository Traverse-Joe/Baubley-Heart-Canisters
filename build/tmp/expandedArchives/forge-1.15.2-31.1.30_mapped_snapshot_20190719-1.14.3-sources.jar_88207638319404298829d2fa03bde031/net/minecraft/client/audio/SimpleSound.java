package net.minecraft.client.audio;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SimpleSound extends LocatableSound {
   public SimpleSound(SoundEvent soundIn, SoundCategory categoryIn, float volumeIn, float pitchIn, BlockPos pos) {
      this(soundIn, categoryIn, volumeIn, pitchIn, (float)pos.getX() + 0.5F, (float)pos.getY() + 0.5F, (float)pos.getZ() + 0.5F);
   }

   public static SimpleSound master(SoundEvent soundIn, float pitchIn) {
      return master(soundIn, pitchIn, 0.25F);
   }

   public static SimpleSound master(SoundEvent soundIn, float pitchIn, float volumeIn) {
      return new SimpleSound(soundIn.getName(), SoundCategory.MASTER, volumeIn, pitchIn, false, 0, ISound.AttenuationType.NONE, 0.0F, 0.0F, 0.0F, true);
   }

   public static SimpleSound music(SoundEvent soundIn) {
      return new SimpleSound(soundIn.getName(), SoundCategory.MUSIC, 1.0F, 1.0F, false, 0, ISound.AttenuationType.NONE, 0.0F, 0.0F, 0.0F, true);
   }

   public static SimpleSound record(SoundEvent soundIn, float xIn, float yIn, float zIn) {
      return new SimpleSound(soundIn, SoundCategory.RECORDS, 4.0F, 1.0F, false, 0, ISound.AttenuationType.LINEAR, xIn, yIn, zIn);
   }

   public SimpleSound(SoundEvent soundIn, SoundCategory categoryIn, float volumeIn, float pitchIn, float xIn, float yIn, float zIn) {
      this(soundIn, categoryIn, volumeIn, pitchIn, false, 0, ISound.AttenuationType.LINEAR, xIn, yIn, zIn);
   }

   private SimpleSound(SoundEvent soundIn, SoundCategory categoryIn, float volumeIn, float pitchIn, boolean repeatIn, int repeatDelayIn, ISound.AttenuationType attenuationTypeIn, float xIn, float yIn, float zIn) {
      this(soundIn.getName(), categoryIn, volumeIn, pitchIn, repeatIn, repeatDelayIn, attenuationTypeIn, xIn, yIn, zIn, false);
   }

   public SimpleSound(ResourceLocation p_i50897_1_, SoundCategory p_i50897_2_, float p_i50897_3_, float p_i50897_4_, boolean p_i50897_5_, int p_i50897_6_, ISound.AttenuationType p_i50897_7_, float p_i50897_8_, float p_i50897_9_, float p_i50897_10_, boolean p_i50897_11_) {
      super(p_i50897_1_, p_i50897_2_);
      this.volume = p_i50897_3_;
      this.pitch = p_i50897_4_;
      this.x = p_i50897_8_;
      this.y = p_i50897_9_;
      this.z = p_i50897_10_;
      this.repeat = p_i50897_5_;
      this.repeatDelay = p_i50897_6_;
      this.attenuationType = p_i50897_7_;
      this.global = p_i50897_11_;
   }
}