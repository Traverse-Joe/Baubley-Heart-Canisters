package net.minecraft.client;

import com.mojang.bridge.game.GameSession;
import java.util.UUID;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.network.play.ClientPlayNetHandler;
import net.minecraft.client.network.play.NetworkPlayerInfo;
import net.minecraft.client.world.ClientWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ClientGameSession implements GameSession {
   private final int field_216846_a;
   private final boolean field_216847_b;
   private final String field_216848_c;
   private final String field_216849_d;
   private final UUID field_216850_e;

   public ClientGameSession(ClientWorld p_i51152_1_, ClientPlayerEntity p_i51152_2_, ClientPlayNetHandler p_i51152_3_) {
      this.field_216846_a = p_i51152_3_.getPlayerInfoMap().size();
      this.field_216847_b = !p_i51152_3_.getNetworkManager().isLocalChannel();
      this.field_216848_c = p_i51152_1_.getDifficulty().getTranslationKey();
      NetworkPlayerInfo networkplayerinfo = p_i51152_3_.getPlayerInfo(p_i51152_2_.getUniqueID());
      if (networkplayerinfo != null) {
         this.field_216849_d = networkplayerinfo.getGameType().getName();
      } else {
         this.field_216849_d = "unknown";
      }

      this.field_216850_e = p_i51152_3_.func_217277_l();
   }

   public int getPlayerCount() {
      return this.field_216846_a;
   }

   public boolean isRemoteServer() {
      return this.field_216847_b;
   }

   public String getDifficulty() {
      return this.field_216848_c;
   }

   public String getGameMode() {
      return this.field_216849_d;
   }

   public UUID getSessionId() {
      return this.field_216850_e;
   }
}