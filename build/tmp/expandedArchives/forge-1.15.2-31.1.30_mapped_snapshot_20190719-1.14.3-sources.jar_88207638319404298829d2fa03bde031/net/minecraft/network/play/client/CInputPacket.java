package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.IServerPlayNetHandler;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class CInputPacket implements IPacket<IServerPlayNetHandler> {
   private float strafeSpeed;
   private float forwardSpeed;
   private boolean jumping;
   private boolean field_229754_d_;

   public CInputPacket() {
   }

   @OnlyIn(Dist.CLIENT)
   public CInputPacket(float strafeSpeedIn, float forwardSpeedIn, boolean jumpingIn, boolean sneakingIn) {
      this.strafeSpeed = strafeSpeedIn;
      this.forwardSpeed = forwardSpeedIn;
      this.jumping = jumpingIn;
      this.field_229754_d_ = sneakingIn;
   }

   /**
    * Reads the raw packet data from the data stream.
    */
   public void readPacketData(PacketBuffer buf) throws IOException {
      this.strafeSpeed = buf.readFloat();
      this.forwardSpeed = buf.readFloat();
      byte b0 = buf.readByte();
      this.jumping = (b0 & 1) > 0;
      this.field_229754_d_ = (b0 & 2) > 0;
   }

   /**
    * Writes the raw packet data to the data stream.
    */
   public void writePacketData(PacketBuffer buf) throws IOException {
      buf.writeFloat(this.strafeSpeed);
      buf.writeFloat(this.forwardSpeed);
      byte b0 = 0;
      if (this.jumping) {
         b0 = (byte)(b0 | 1);
      }

      if (this.field_229754_d_) {
         b0 = (byte)(b0 | 2);
      }

      buf.writeByte(b0);
   }

   public void processPacket(IServerPlayNetHandler handler) {
      handler.processInput(this);
   }

   public float getStrafeSpeed() {
      return this.strafeSpeed;
   }

   public float getForwardSpeed() {
      return this.forwardSpeed;
   }

   public boolean isJumping() {
      return this.jumping;
   }

   public boolean func_229755_e_() {
      return this.field_229754_d_;
   }
}