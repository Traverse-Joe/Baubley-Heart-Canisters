package net.minecraft.network.play.server;

import com.google.common.collect.Sets;
import java.io.IOException;
import java.util.Set;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.server.IDynamicRegistries;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.DimensionType;
import net.minecraft.world.GameType;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SJoinGamePacket implements IPacket<IClientPlayNetHandler> {
   private int playerId;
   /** First 8 bytes of the SHA-256 hash of the world's seed */
   private long hashedSeed;
   private boolean hardcoreMode;
   private GameType gameType;
   private GameType field_241785_e_;
   private Set<RegistryKey<World>> field_240811_e_;
   private IDynamicRegistries.Impl field_240812_f_;
   private RegistryKey<DimensionType> field_240813_g_;
   private RegistryKey<World> dimension;
   private int maxPlayers;
   private int viewDistance;
   private boolean reducedDebugInfo;
   /** Set to false when the doImmediateRespawn gamerule is true */
   private boolean enableRespawnScreen;
   private boolean field_240814_m_;
   private boolean field_240815_n_;

   public SJoinGamePacket() {
   }

   public SJoinGamePacket(int p_i241268_1_, GameType p_i241268_2_, GameType p_i241268_3_, long p_i241268_4_, boolean p_i241268_6_, Set<RegistryKey<World>> p_i241268_7_, IDynamicRegistries.Impl p_i241268_8_, RegistryKey<DimensionType> p_i241268_9_, RegistryKey<World> p_i241268_10_, int p_i241268_11_, int p_i241268_12_, boolean p_i241268_13_, boolean p_i241268_14_, boolean p_i241268_15_, boolean p_i241268_16_) {
      this.playerId = p_i241268_1_;
      this.field_240811_e_ = p_i241268_7_;
      this.field_240812_f_ = p_i241268_8_;
      this.field_240813_g_ = p_i241268_9_;
      this.dimension = p_i241268_10_;
      this.hashedSeed = p_i241268_4_;
      this.gameType = p_i241268_2_;
      this.field_241785_e_ = p_i241268_3_;
      this.maxPlayers = p_i241268_11_;
      this.hardcoreMode = p_i241268_6_;
      this.viewDistance = p_i241268_12_;
      this.reducedDebugInfo = p_i241268_13_;
      this.enableRespawnScreen = p_i241268_14_;
      this.field_240814_m_ = p_i241268_15_;
      this.field_240815_n_ = p_i241268_16_;
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
      this.field_241785_e_ = GameType.getByID(buf.readUnsignedByte());
      int j = buf.readVarInt();
      this.field_240811_e_ = Sets.newHashSet();

      for(int k = 0; k < j; ++k) {
         this.field_240811_e_.add(RegistryKey.func_240903_a_(Registry.field_239699_ae_, buf.readResourceLocation()));
      }

      this.field_240812_f_ = buf.func_240628_a_(IDynamicRegistries.Impl.field_239771_a_);
      this.field_240813_g_ = RegistryKey.func_240903_a_(Registry.field_239698_ad_, buf.readResourceLocation());
      this.dimension = RegistryKey.func_240903_a_(Registry.field_239699_ae_, buf.readResourceLocation());
      this.hashedSeed = buf.readLong();
      this.maxPlayers = buf.readUnsignedByte();
      this.viewDistance = buf.readVarInt();
      this.reducedDebugInfo = buf.readBoolean();
      this.enableRespawnScreen = buf.readBoolean();
      this.field_240814_m_ = buf.readBoolean();
      this.field_240815_n_ = buf.readBoolean();
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
      buf.writeByte(this.field_241785_e_.getID());
      buf.writeVarInt(this.field_240811_e_.size());

      for(RegistryKey<World> registrykey : this.field_240811_e_) {
         buf.writeResourceLocation(registrykey.func_240901_a_());
      }

      buf.func_240629_a_(IDynamicRegistries.Impl.field_239771_a_, this.field_240812_f_);
      buf.writeResourceLocation(this.field_240813_g_.func_240901_a_());
      buf.writeResourceLocation(this.dimension.func_240901_a_());
      buf.writeLong(this.hashedSeed);
      buf.writeByte(this.maxPlayers);
      buf.writeVarInt(this.viewDistance);
      buf.writeBoolean(this.reducedDebugInfo);
      buf.writeBoolean(this.enableRespawnScreen);
      buf.writeBoolean(this.field_240814_m_);
      buf.writeBoolean(this.field_240815_n_);
   }

   /**
    * Passes this Packet on to the NetHandler for processing.
    */
   public void processPacket(IClientPlayNetHandler handler) {
      handler.handleJoinGame(this);
   }

   @OnlyIn(Dist.CLIENT)
   public int getPlayerId() {
      return this.playerId;
   }

   /**
    * get value
    */
   @OnlyIn(Dist.CLIENT)
   public long getHashedSeed() {
      return this.hashedSeed;
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
   public GameType func_241786_f_() {
      return this.field_241785_e_;
   }

   @OnlyIn(Dist.CLIENT)
   public Set<RegistryKey<World>> func_240816_f_() {
      return this.field_240811_e_;
   }

   @OnlyIn(Dist.CLIENT)
   public IDynamicRegistries func_240817_g_() {
      return this.field_240812_f_;
   }

   @OnlyIn(Dist.CLIENT)
   public RegistryKey<DimensionType> func_240818_h_() {
      return this.field_240813_g_;
   }

   @OnlyIn(Dist.CLIENT)
   public RegistryKey<World> func_240819_i_() {
      return this.dimension;
   }

   @OnlyIn(Dist.CLIENT)
   public int getViewDistance() {
      return this.viewDistance;
   }

   @OnlyIn(Dist.CLIENT)
   public boolean isReducedDebugInfo() {
      return this.reducedDebugInfo;
   }

   @OnlyIn(Dist.CLIENT)
   public boolean func_229743_k_() {
      return this.enableRespawnScreen;
   }

   @OnlyIn(Dist.CLIENT)
   public boolean func_240820_n_() {
      return this.field_240814_m_;
   }

   @OnlyIn(Dist.CLIENT)
   public boolean func_240821_o_() {
      return this.field_240815_n_;
   }
}