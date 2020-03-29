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

public class SJoinGamePacket implements IPacket<IClientPlayNetHandler> {
   private int playerId;
   private long field_229740_b_;
   private boolean hardcoreMode;
   private GameType gameType;
   private DimensionType dimension;
   private int maxPlayers;
   private WorldType worldType;
   private int field_218729_g;
   private boolean reducedDebugInfo;
   private boolean field_229741_j_;
   private int dimensionInt;

   public SJoinGamePacket() {
   }

   public SJoinGamePacket(int p_i226090_1_, GameType p_i226090_2_, long p_i226090_3_, boolean p_i226090_5_, DimensionType p_i226090_6_, int p_i226090_7_, WorldType p_i226090_8_, int p_i226090_9_, boolean p_i226090_10_, boolean p_i226090_11_) {
      this.playerId = p_i226090_1_;
      this.dimension = p_i226090_6_;
      this.field_229740_b_ = p_i226090_3_;
      this.gameType = p_i226090_2_;
      this.maxPlayers = p_i226090_7_;
      this.hardcoreMode = p_i226090_5_;
      this.worldType = p_i226090_8_;
      this.field_218729_g = p_i226090_9_;
      this.reducedDebugInfo = p_i226090_10_;
      this.field_229741_j_ = p_i226090_11_;
   }

   /**
    * Reads the raw packet data from the data stream.
    */
   public void readPacketData(PacketBuffer buf) throws IOException {
      this.playerId = buf.readInt();
      int i = buf.readUnsignedByte();
      this.hardcoreMode = (i & 8) == 8;
      i = i & -9;
      this.gameType = GameType.getByID(i);
      this.dimensionInt = buf.readInt();
      this.field_229740_b_ = buf.readLong();
      this.maxPlayers = buf.readUnsignedByte();
      this.worldType = WorldType.byName(buf.readString(16));
      if (this.worldType == null) {
         this.worldType = WorldType.DEFAULT;
      }

      this.field_218729_g = buf.readVarInt();
      this.reducedDebugInfo = buf.readBoolean();
      this.field_229741_j_ = buf.readBoolean();
   }

   /**
    * Writes the raw packet data to the data stream.
    */
   public void writePacketData(PacketBuffer buf) throws IOException {
      buf.writeInt(this.playerId);
      int i = this.gameType.getID();
      if (this.hardcoreMode) {
         i |= 8;
      }

      buf.writeByte(i);
      buf.writeInt(this.dimension.getId());
      buf.writeLong(this.field_229740_b_);
      buf.writeByte(this.maxPlayers);
      buf.writeString(this.worldType.getName());
      buf.writeVarInt(this.field_218729_g);
      buf.writeBoolean(this.reducedDebugInfo);
      buf.writeBoolean(this.field_229741_j_);
   }

   public void processPacket(IClientPlayNetHandler handler) {
      handler.handleJoinGame(this);
   }

   @OnlyIn(Dist.CLIENT)
   public int getPlayerId() {
      return this.playerId;
   }

   @OnlyIn(Dist.CLIENT)
   public long func_229742_c_() {
      return this.field_229740_b_;
   }

   @OnlyIn(Dist.CLIENT)
   public boolean isHardcoreMode() {
      return this.hardcoreMode;
   }

   @OnlyIn(Dist.CLIENT)
   public GameType getGameType() {
      return this.gameType;
   }

   @OnlyIn(Dist.CLIENT)
   public DimensionType getDimension() {
      return this.dimension == null ? this.dimension = net.minecraftforge.fml.network.NetworkHooks.getDummyDimType(this.dimensionInt) : this.dimension;
   }

   @OnlyIn(Dist.CLIENT)
   public WorldType getWorldType() {
      return this.worldType;
   }

   @OnlyIn(Dist.CLIENT)
   public int func_218728_h() {
      return this.field_218729_g;
   }

   @OnlyIn(Dist.CLIENT)
   public boolean isReducedDebugInfo() {
      return this.reducedDebugInfo;
   }

   @OnlyIn(Dist.CLIENT)
   public boolean func_229743_k_() {
      return this.field_229741_j_;
   }
}