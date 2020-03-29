package net.minecraft.network;

import java.io.IOException;

public interface IPacket<T extends INetHandler> {
   /**
    * Reads the raw packet data from the data stream.
    */
   void readPacketData(PacketBuffer buf) throws IOException;

   /**
    * Writes the raw packet data to the data stream.
    */
   void writePacketData(PacketBuffer buf) throws IOException;

   void processPacket(T handler);

   default boolean shouldSkipErrors() {
      return false;
   }
}