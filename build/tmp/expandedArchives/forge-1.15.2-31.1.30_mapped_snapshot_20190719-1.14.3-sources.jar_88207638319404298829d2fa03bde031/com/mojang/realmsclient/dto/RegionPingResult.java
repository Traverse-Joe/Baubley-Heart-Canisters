package com.mojang.realmsclient.dto;

import java.util.Locale;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RegionPingResult extends ValueObject {
   private final String regionName;
   private final int ping;

   public RegionPingResult(String p_i51641_1_, int p_i51641_2_) {
      this.regionName = p_i51641_1_;
      this.ping = p_i51641_2_;
   }

   public int ping() {
      return this.ping;
   }

   public String toString() {
      return String.format(Locale.ROOT, "%s --> %.2f ms", this.regionName, (float)this.ping);
   }
}