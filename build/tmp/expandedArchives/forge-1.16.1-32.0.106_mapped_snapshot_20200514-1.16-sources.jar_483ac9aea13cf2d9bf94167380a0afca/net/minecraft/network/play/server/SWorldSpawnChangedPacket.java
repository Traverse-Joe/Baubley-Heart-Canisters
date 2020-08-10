package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SWorldSpawnChangedPacket implements IPacket<IClientPlayNetHandler> {
   private BlockPos field_240831_a_;

   public SWorldSpawnChangedPacket() {
   }

   public SWorldSpawnChangedPacket(BlockPos p_i232581_1_) {
      this.field_240831_a_ = p_i232581_1_;
   }

   /**
    * Reads the raw packet data from the data stream.
    */
   public void readPacketData(PacketBuffer buf) throws IOException {
      this.field_240831_a_ = buf.readBlockPos();
   }

   /**
    * Writes the raw packet data to the data stream.
    */
   public void writePacketData(PacketBuffer buf) throws IOException {
      buf.writeBlockPos(this.field_240831_a_);
   }

   /**
    * Passes this Packet on to the NetHandler for processing.
    */
   public void processPacket(IClientPlayNetHandler handler) {
      handler.func_230488_a_(this);
   }

   @OnlyIn(Dist.CLIENT)
   public BlockPos func_240832_b_() {
      return this.field_240831_a_;
   }
}