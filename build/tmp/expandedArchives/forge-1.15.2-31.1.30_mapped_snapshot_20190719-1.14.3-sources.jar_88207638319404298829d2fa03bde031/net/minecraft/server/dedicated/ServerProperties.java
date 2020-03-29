package net.minecraft.server.dedicated;

import java.nio.file.Path;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameType;
import net.minecraft.world.WorldType;

public class ServerProperties extends PropertyManager<ServerProperties> {
   public final boolean onlineMode = this.func_218982_a("online-mode", true);
   public final boolean preventProxyConnections = this.func_218982_a("prevent-proxy-connections", false);
   public final String serverIp = this.func_218973_a("server-ip", "");
   public final boolean spawnAnimals = this.func_218982_a("spawn-animals", true);
   public final boolean spawnNPCs = this.func_218982_a("spawn-npcs", true);
   public final boolean allowPvp = this.func_218982_a("pvp", true);
   public final boolean allowFlight = this.func_218982_a("allow-flight", false);
   public final String resourcePack = this.func_218973_a("resource-pack", "");
   public final String motd = this.func_218973_a("motd", "A Minecraft Server");
   public final boolean forceGamemode = this.func_218982_a("force-gamemode", false);
   public final boolean enforceWhitelist = this.func_218982_a("enforce-whitelist", false);
   public final boolean generateStructures = this.func_218982_a("generate-structures", true);
   public final Difficulty difficulty = this.func_218983_a("difficulty", func_218964_a(Difficulty::byId, Difficulty::byName), Difficulty::getTranslationKey, Difficulty.EASY);
   public final GameType gamemode = this.func_218983_a("gamemode", func_218964_a(GameType::getByID, GameType::getByName), GameType::getName, GameType.SURVIVAL);
   public final String worldName = this.func_218973_a("level-name", "world");
   public final String worldSeed = this.func_218973_a("level-seed", "");
   public final WorldType worldType = this.func_218983_a("level-type", WorldType::byName, WorldType::getName, WorldType.DEFAULT);
   public final String generatorSettings = this.func_218973_a("generator-settings", "");
   public final int serverPort = this.func_218968_a("server-port", 25565);
   public final int maxBuildHeight = this.func_218962_a("max-build-height", (p_218987_0_) -> {
      return MathHelper.clamp((p_218987_0_ + 8) / 16 * 16, 64, 256);
   }, 256);
   public final Boolean announceAdvancements = this.func_218978_b("announce-player-achievements");
   public final boolean enableQuery = this.func_218982_a("enable-query", false);
   public final int queryPort = this.func_218968_a("query.port", 25565);
   public final boolean enableRcon = this.func_218982_a("enable-rcon", false);
   public final int rconPort = this.func_218968_a("rcon.port", 25575);
   public final String rconPassword = this.func_218973_a("rcon.password", "");
   /** Deprecated. Use resourcePackSha1 instead. */
   public final String resourcePackHash = this.func_218980_a("resource-pack-hash");
   public final String resourcePackSha1 = this.func_218973_a("resource-pack-sha1", "");
   public final boolean hardcore = this.func_218982_a("hardcore", false);
   public final boolean allowNether = this.func_218982_a("allow-nether", true);
   public final boolean spawnMonsters = this.func_218982_a("spawn-monsters", true);
   public final boolean field_218993_F;
   public final boolean useNativeTransport;
   public final boolean enableCommandBlock;
   public final int spawnProtection;
   public final int opPermissionLevel;
   public final int field_225395_K;
   public final long maxTickTime;
   public final int viewDistance;
   public final int maxPlayers;
   public final int networkCompressionThreshold;
   public final boolean broadcastRconToOps;
   public final boolean broadcastConsoleToOps;
   public final int maxWorldSize;
   public final PropertyManager<ServerProperties>.Property<Integer> playerIdleTimeout;
   public final PropertyManager<ServerProperties>.Property<Boolean> whitelistEnabled;

   public ServerProperties(Properties properties) {
      super(properties);
      if (this.func_218982_a("snooper-enabled", true)) {
         ;
      }

      this.field_218993_F = false;
      this.useNativeTransport = this.func_218982_a("use-native-transport", true);
      this.enableCommandBlock = this.func_218982_a("enable-command-block", false);
      this.spawnProtection = this.func_218968_a("spawn-protection", 16);
      this.opPermissionLevel = this.func_218968_a("op-permission-level", 4);
      this.field_225395_K = this.func_218968_a("function-permission-level", 2);
      this.maxTickTime = this.func_218967_a("max-tick-time", TimeUnit.MINUTES.toMillis(1L));
      this.viewDistance = this.func_218968_a("view-distance", 10);
      this.maxPlayers = this.func_218968_a("max-players", 20);
      this.networkCompressionThreshold = this.func_218968_a("network-compression-threshold", 256);
      this.broadcastRconToOps = this.func_218982_a("broadcast-rcon-to-ops", true);
      this.broadcastConsoleToOps = this.func_218982_a("broadcast-console-to-ops", true);
      this.maxWorldSize = this.func_218962_a("max-world-size", (p_218986_0_) -> {
         return MathHelper.clamp(p_218986_0_, 1, 29999984);
      }, 29999984);
      this.playerIdleTimeout = this.func_218974_b("player-idle-timeout", 0);
      this.whitelistEnabled = this.func_218961_b("white-list", false);
   }

   public static ServerProperties create(Path pathIn) {
      return new ServerProperties(load(pathIn));
   }

   protected ServerProperties func_212857_b_(Properties properties) {
      return new ServerProperties(properties);
   }
}