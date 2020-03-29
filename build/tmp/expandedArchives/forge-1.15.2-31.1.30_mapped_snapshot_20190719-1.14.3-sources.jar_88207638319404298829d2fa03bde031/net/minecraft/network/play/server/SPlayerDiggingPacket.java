package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.CPlayerDiggingPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SPlayerDiggingPacket implements IPacket<IClientPlayNetHandler> {
   private static final Logger field_225379_b = LogManager.getLogger();
   private BlockPos field_225380_c;
   private BlockState field_225381_d;
   CPlayerDiggingPacket.Action field_225378_a;
   private boolean field_225382_e;

   public SPlayerDiggingPacket() {
   }

   public SPlayerDiggingPacket(BlockPos p_i226088_1_, BlockState p_i226088_2_, CPlayerDiggingPacket.Action p_i226088_3_, boolean p_i226088_4_, String p_i226088_5_) {
      this.field_225380_c = p_i226088_1_.toImmutable();
      this.field_225381_d = p_i226088_2_;
      this.field_225378_a = p_i226088_3_;
      this.field_225382_e = p_i226088_4_;
   }

   /**
    * Reads the raw packet data from the data stream.
    */
   public void readPacketData(PacketBuffer buf) throws IOException {
      this.field_225380_c = buf.readBlockPos();
      this.field_225381_d = Block.BLOCK_STATE_IDS.getByValue(buf.readVarInt());
      this.field_225378_a = buf.readEnumValue(CPlayerDiggingPacket.Action.class);
      this.field_225382_e = buf.readBoolean();
   }

   /**
    * Writes the raw packet data to the data stream.
    */
   public void writePacketData(PacketBuffer buf) throws IOException {
      buf.writeBlockPos(this.field_225380_c);
      buf.writeVarInt(Block.getStateId(this.field_225381_d));
      buf.writeEnumValue(this.field_225378_a);
      buf.writeBoolean(this.field_225382_e);
   }

   public void processPacket(IClientPlayNetHandler handler) {
      handler.func_225312_a(this);
   }

   @OnlyIn(Dist.CLIENT)
   public BlockState func_225375_b() {
      return this.field_225381_d;
   }

   @OnlyIn(Dist.CLIENT)
   public BlockPos func_225374_c() {
      return this.field_225380_c;
   }

   @OnlyIn(Dist.CLIENT)
   public boolean func_225376_d() {
      return this.field_225382_e;
   }

   @OnlyIn(Dist.CLIENT)
   public CPlayerDiggingPacket.Action func_225377_e() {
      return this.field_225378_a;
   }
}