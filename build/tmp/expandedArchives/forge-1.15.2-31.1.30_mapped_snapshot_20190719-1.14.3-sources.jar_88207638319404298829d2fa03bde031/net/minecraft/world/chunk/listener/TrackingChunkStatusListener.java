package net.minecraft.world.chunk.listener;

import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import javax.annotation.Nullable;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TrackingChunkStatusListener implements IChunkStatusListener {
   private final LoggingChunkStatusListener loggingListener;
   private final Long2ObjectOpenHashMap<ChunkStatus> statuses;
   private ChunkPos center = new ChunkPos(0, 0);
   private final int diameter;
   private final int field_219530_e;
   private final int field_219531_f;
   private boolean field_219532_g;

   public TrackingChunkStatusListener(int radius) {
      this.loggingListener = new LoggingChunkStatusListener(radius);
      this.diameter = radius * 2 + 1;
      this.field_219530_e = radius + ChunkStatus.func_222600_b();
      this.field_219531_f = this.field_219530_e * 2 + 1;
      this.statuses = new Long2ObjectOpenHashMap<>();
   }

   public void start(ChunkPos center) {
      if (this.field_219532_g) {
         this.loggingListener.start(center);
         this.center = center;
      }
   }

   public void statusChanged(ChunkPos p_219508_1_, @Nullable ChunkStatus p_219508_2_) {
      if (this.field_219532_g) {
         this.loggingListener.statusChanged(p_219508_1_, p_219508_2_);
         if (p_219508_2_ == null) {
            this.statuses.remove(p_219508_1_.asLong());
         } else {
            this.statuses.put(p_219508_1_.asLong(), p_219508_2_);
         }

      }
   }

   public void func_219521_a() {
      this.field_219532_g = true;
      this.statuses.clear();
   }

   public void stop() {
      this.field_219532_g = false;
      this.loggingListener.stop();
   }

   public int getDiameter() {
      return this.diameter;
   }

   public int func_219523_d() {
      return this.field_219531_f;
   }

   public int getPercentDone() {
      return this.loggingListener.getPercentDone();
   }

   @Nullable
   public ChunkStatus func_219525_a(int p_219525_1_, int p_219525_2_) {
      return this.statuses.get(ChunkPos.asLong(p_219525_1_ + this.center.x - this.field_219530_e, p_219525_2_ + this.center.z - this.field_219530_e));
   }
}