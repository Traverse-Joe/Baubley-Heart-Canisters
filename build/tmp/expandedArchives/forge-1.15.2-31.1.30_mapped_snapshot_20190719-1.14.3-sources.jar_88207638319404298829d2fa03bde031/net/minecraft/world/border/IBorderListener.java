package net.minecraft.world.border;

public interface IBorderListener {
   void onSizeChanged(WorldBorder border, double newSize);

   void onTransitionStarted(WorldBorder border, double oldSize, double newSize, long time);

   void onCenterChanged(WorldBorder border, double x, double z);

   void onWarningTimeChanged(WorldBorder border, int newTime);

   void onWarningDistanceChanged(WorldBorder border, int newDistance);

   void onDamageAmountChanged(WorldBorder border, double newAmount);

   void onDamageBufferChanged(WorldBorder border, double newSize);

   public static class Impl implements IBorderListener {
      private final WorldBorder field_219590_a;

      public Impl(WorldBorder p_i50549_1_) {
         this.field_219590_a = p_i50549_1_;
      }

      public void onSizeChanged(WorldBorder border, double newSize) {
         this.field_219590_a.setTransition(newSize);
      }

      public void onTransitionStarted(WorldBorder border, double oldSize, double newSize, long time) {
         this.field_219590_a.setTransition(oldSize, newSize, time);
      }

      public void onCenterChanged(WorldBorder border, double x, double z) {
         this.field_219590_a.setCenter(x, z);
      }

      public void onWarningTimeChanged(WorldBorder border, int newTime) {
         this.field_219590_a.setWarningTime(newTime);
      }

      public void onWarningDistanceChanged(WorldBorder border, int newDistance) {
         this.field_219590_a.setWarningDistance(newDistance);
      }

      public void onDamageAmountChanged(WorldBorder border, double newAmount) {
         this.field_219590_a.setDamagePerBlock(newAmount);
      }

      public void onDamageBufferChanged(WorldBorder border, double newSize) {
         this.field_219590_a.setDamageBuffer(newSize);
      }
   }
}