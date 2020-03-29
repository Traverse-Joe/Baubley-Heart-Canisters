package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.item.MerchantOffers;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SMerchantOffersPacket implements IPacket<IClientPlayNetHandler> {
   private int field_218736_a;
   private MerchantOffers field_218737_b;
   private int field_218738_c;
   private int field_218739_d;
   private boolean field_218740_e;
   private boolean field_223478_f;

   public SMerchantOffersPacket() {
   }

   public SMerchantOffersPacket(int p_i51539_1_, MerchantOffers p_i51539_2_, int p_i51539_3_, int p_i51539_4_, boolean p_i51539_5_, boolean p_i51539_6_) {
      this.field_218736_a = p_i51539_1_;
      this.field_218737_b = p_i51539_2_;
      this.field_218738_c = p_i51539_3_;
      this.field_218739_d = p_i51539_4_;
      this.field_218740_e = p_i51539_5_;
      this.field_223478_f = p_i51539_6_;
   }

   /**
    * Reads the raw packet data from the data stream.
    */
   public void readPacketData(PacketBuffer buf) throws IOException {
      this.field_218736_a = buf.readVarInt();
      this.field_218737_b = MerchantOffers.func_222198_b(buf);
      this.field_218738_c = buf.readVarInt();
      this.field_218739_d = buf.readVarInt();
      this.field_218740_e = buf.readBoolean();
      this.field_223478_f = buf.readBoolean();
   }

   /**
    * Writes the raw packet data to the data stream.
    */
   public void writePacketData(PacketBuffer buf) throws IOException {
      buf.writeVarInt(this.field_218736_a);
      this.field_218737_b.func_222196_a(buf);
      buf.writeVarInt(this.field_218738_c);
      buf.writeVarInt(this.field_218739_d);
      buf.writeBoolean(this.field_218740_e);
      buf.writeBoolean(this.field_223478_f);
   }

   public void processPacket(IClientPlayNetHandler handler) {
      handler.func_217273_a(this);
   }

   @OnlyIn(Dist.CLIENT)
   public int func_218732_b() {
      return this.field_218736_a;
   }

   @OnlyIn(Dist.CLIENT)
   public MerchantOffers func_218733_c() {
      return this.field_218737_b;
   }

   @OnlyIn(Dist.CLIENT)
   public int func_218731_d() {
      return this.field_218738_c;
   }

   @OnlyIn(Dist.CLIENT)
   public int func_218734_e() {
      return this.field_218739_d;
   }

   @OnlyIn(Dist.CLIENT)
   public boolean func_218735_f() {
      return this.field_218740_e;
   }

   @OnlyIn(Dist.CLIENT)
   public boolean func_223477_g() {
      return this.field_223478_f;
   }
}