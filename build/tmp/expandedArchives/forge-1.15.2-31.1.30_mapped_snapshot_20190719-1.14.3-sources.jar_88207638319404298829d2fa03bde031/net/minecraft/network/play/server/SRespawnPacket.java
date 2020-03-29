package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.GameType;
import net.minecraft.world.WorldType;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SRespawnPacket implements IPacket<IClientPlayNetHandler> {
   private DimensionType dimensionID;
   private long field_229746_b_;
   private GameType gameType;
   private WorldType worldType;
   private int dimensionInt;

   public SRespawnPacket() {
   }

   public SRespawnPacket(DimensionType p_i226091_1_, long p_i226091_2_, WorldType p_i226091_4_, GameType p_i226091_5_) {
      this.dimensionID = p_i226091_1_;
      this.field_229746_b_ = p_i226091_2_;
      this.gameType = p_i226091_5_;
      this.worldType = p_i226091_4_;
   }

   public void processPacket(IClientPlayNetHandler handler) {
      handler.handleRespawn(this);
   }

   /**
    * Reads the raw packet data from the data stream.
    */
   public void readPacketData(PacketBuffer buf) throws IOException {
      this.dimensionInt = buf.readInt();
      this.field_229746_b_ = buf.readLong();
      this.gameType = GameType.getByID(buf.readUnsignedByte());
      this.worldType = WorldType.byName(buf.readString(16));
      if (this.worldType == null) {
         this.worldType = WorldType.DEFAULT;
      }

   }

   /**
    * Writes the raw packet data to the data stream.
    */
   public void writePacketData(PacketBuffer buf) throws IOException {
      buf.writeInt(this.dimensionID.getId());
      buf.writeLong(this.field_229746_b_);
      buf.writeByte(this.gameType.getID());
      buf.writeString(this.worldType.getName());
   }

   @OnlyIn(Dist.CLIENT)
   public DimensionType getDimension() {
      return this.dimensionID == null ? this.dimensionID = net.minecraftforge.fml.network.NetworkHooks.getDummyDimType(this.dimensionInt) : this.dimensionID;
   }

   @OnlyIn(Dist.CLIENT)
   public long func_229747_c_() {
      return this.field_229746_b_;
   }

   @OnlyIn(Dist.CLIENT)
   public GameType getGameType() {
      return this.gameType;
   }

   @OnlyIn(Dist.CLIENT)
   public WorldType getWorldType() {
      return this.worldType;
   }
}