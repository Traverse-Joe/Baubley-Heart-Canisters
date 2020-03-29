package net.minecraft.world;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.play.server.SChangeGameStatePacket;
import net.minecraft.network.play.server.SEntityStatusPacket;
import net.minecraft.server.MinecraftServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GameRules {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final Map<GameRules.RuleKey<?>, GameRules.RuleType<?>> GAME_RULES = Maps.newTreeMap(Comparator.comparing((p_223597_0_) -> {
      return p_223597_0_.field_223578_a;
   }));
   public static final GameRules.RuleKey<GameRules.BooleanValue> DO_FIRE_TICK = register("doFireTick", GameRules.BooleanValue.create(true));
   public static final GameRules.RuleKey<GameRules.BooleanValue> MOB_GRIEFING = register("mobGriefing", GameRules.BooleanValue.create(true));
   public static final GameRules.RuleKey<GameRules.BooleanValue> KEEP_INVENTORY = register("keepInventory", GameRules.BooleanValue.create(false));
   public static final GameRules.RuleKey<GameRules.BooleanValue> DO_MOB_SPAWNING = register("doMobSpawning", GameRules.BooleanValue.create(true));
   public static final GameRules.RuleKey<GameRules.BooleanValue> DO_MOB_LOOT = register("doMobLoot", GameRules.BooleanValue.create(true));
   public static final GameRules.RuleKey<GameRules.BooleanValue> DO_TILE_DROPS = register("doTileDrops", GameRules.BooleanValue.create(true));
   public static final GameRules.RuleKey<GameRules.BooleanValue> DO_ENTITY_DROPS = register("doEntityDrops", GameRules.BooleanValue.create(true));
   public static final GameRules.RuleKey<GameRules.BooleanValue> COMMAND_BLOCK_OUTPUT = register("commandBlockOutput", GameRules.BooleanValue.create(true));
   public static final GameRules.RuleKey<GameRules.BooleanValue> NATURAL_REGENERATION = register("naturalRegeneration", GameRules.BooleanValue.create(true));
   public static final GameRules.RuleKey<GameRules.BooleanValue> DO_DAYLIGHT_CYCLE = register("doDaylightCycle", GameRules.BooleanValue.create(true));
   public static final GameRules.RuleKey<GameRules.BooleanValue> LOG_ADMIN_COMMANDS = register("logAdminCommands", GameRules.BooleanValue.create(true));
   public static final GameRules.RuleKey<GameRules.BooleanValue> SHOW_DEATH_MESSAGES = register("showDeathMessages", GameRules.BooleanValue.create(true));
   public static final GameRules.RuleKey<GameRules.IntegerValue> RANDOM_TICK_SPEED = register("randomTickSpeed", GameRules.IntegerValue.create(3));
   public static final GameRules.RuleKey<GameRules.BooleanValue> SEND_COMMAND_FEEDBACK = register("sendCommandFeedback", GameRules.BooleanValue.create(true));
   public static final GameRules.RuleKey<GameRules.BooleanValue> REDUCED_DEBUG_INFO = register("reducedDebugInfo", GameRules.BooleanValue.create(false, (p_223589_0_, p_223589_1_) -> {
      byte b0 = (byte)(p_223589_1_.get() ? 22 : 23);

      for(ServerPlayerEntity serverplayerentity : p_223589_0_.getPlayerList().getPlayers()) {
         serverplayerentity.connection.sendPacket(new SEntityStatusPacket(serverplayerentity, b0));
      }

   }));
   public static final GameRules.RuleKey<GameRules.BooleanValue> SPECTATORS_GENERATE_CHUNKS = register("spectatorsGenerateChunks", GameRules.BooleanValue.create(true));
   public static final GameRules.RuleKey<GameRules.IntegerValue> SPAWN_RADIUS = register("spawnRadius", GameRules.IntegerValue.create(10));
   public static final GameRules.RuleKey<GameRules.BooleanValue> DISABLE_ELYTRA_MOVEMENT_CHECK = register("disableElytraMovementCheck", GameRules.BooleanValue.create(false));
   public static final GameRules.RuleKey<GameRules.IntegerValue> MAX_ENTITY_CRAMMING = register("maxEntityCramming", GameRules.IntegerValue.create(24));
   public static final GameRules.RuleKey<GameRules.BooleanValue> DO_WEATHER_CYCLE = register("doWeatherCycle", GameRules.BooleanValue.create(true));
   public static final GameRules.RuleKey<GameRules.BooleanValue> DO_LIMITED_CRAFTING = register("doLimitedCrafting", GameRules.BooleanValue.create(false));
   public static final GameRules.RuleKey<GameRules.IntegerValue> MAX_COMMAND_CHAIN_LENGTH = register("maxCommandChainLength", GameRules.IntegerValue.create(65536));
   public static final GameRules.RuleKey<GameRules.BooleanValue> ANNOUNCE_ADVANCEMENTS = register("announceAdvancements", GameRules.BooleanValue.create(true));
   public static final GameRules.RuleKey<GameRules.BooleanValue> DISABLE_RAIDS = register("disableRaids", GameRules.BooleanValue.create(false));
   public static final GameRules.RuleKey<GameRules.BooleanValue> field_226682_y_ = register("doInsomnia", GameRules.BooleanValue.create(true));
   public static final GameRules.RuleKey<GameRules.BooleanValue> field_226683_z_ = register("doImmediateRespawn", GameRules.BooleanValue.create(false, (p_226686_0_, p_226686_1_) -> {
      for(ServerPlayerEntity serverplayerentity : p_226686_0_.getPlayerList().getPlayers()) {
         serverplayerentity.connection.sendPacket(new SChangeGameStatePacket(11, p_226686_1_.get() ? 1.0F : 0.0F));
      }

   }));
   public static final GameRules.RuleKey<GameRules.BooleanValue> field_226679_A_ = register("drowningDamage", GameRules.BooleanValue.create(true));
   public static final GameRules.RuleKey<GameRules.BooleanValue> field_226680_B_ = register("fallDamage", GameRules.BooleanValue.create(true));
   public static final GameRules.RuleKey<GameRules.BooleanValue> field_226681_C_ = register("fireDamage", GameRules.BooleanValue.create(true));
   public static final GameRules.RuleKey<GameRules.BooleanValue> field_230127_D_ = register("doPatrolSpawning", GameRules.BooleanValue.create(true));
   public static final GameRules.RuleKey<GameRules.BooleanValue> field_230128_E_ = register("doTraderSpawning", GameRules.BooleanValue.create(true));
   private final Map<GameRules.RuleKey<?>, GameRules.RuleValue<?>> rules = GAME_RULES.entrySet().stream().collect(ImmutableMap.toImmutableMap(Entry::getKey, (p_226684_0_) -> {
      return p_226684_0_.getValue().func_223579_a();
   }));

   public static <T extends GameRules.RuleValue<T>> GameRules.RuleKey<T> register(String p_223595_0_, GameRules.RuleType<T> p_223595_1_) {
      GameRules.RuleKey<T> rulekey = new GameRules.RuleKey<>(p_223595_0_);
      GameRules.RuleType<?> ruletype = GAME_RULES.put(rulekey, p_223595_1_);
      if (ruletype != null) {
         throw new IllegalStateException("Duplicate game rule registration for " + p_223595_0_);
      } else {
         return rulekey;
      }
   }

   public <T extends GameRules.RuleValue<T>> T get(GameRules.RuleKey<T> key) {
      return (T)(this.rules.get(key));
   }

   /**
    * Return the defined game rules as NBT.
    */
   public CompoundNBT write() {
      CompoundNBT compoundnbt = new CompoundNBT();
      this.rules.forEach((p_226688_1_, p_226688_2_) -> {
         compoundnbt.putString(p_226688_1_.field_223578_a, p_226688_2_.func_223552_b());
      });
      return compoundnbt;
   }

   /**
    * Set defined game rules from NBT.
    */
   public void read(CompoundNBT nbt) {
      this.rules.forEach((p_226685_1_, p_226685_2_) -> {
         if (nbt.contains(p_226685_1_.field_223578_a)) {
            p_226685_2_.func_223553_a(nbt.getString(p_226685_1_.field_223578_a));
         }

      });
   }

   public static void func_223590_a(GameRules.IRuleEntryVisitor p_223590_0_) {
      GAME_RULES.forEach((p_226687_1_, p_226687_2_) -> {
         func_223596_a(p_223590_0_, p_226687_1_, p_226687_2_);
      });
   }

   private static <T extends GameRules.RuleValue<T>> void func_223596_a(GameRules.IRuleEntryVisitor p_223596_0_, GameRules.RuleKey<?> p_223596_1_, GameRules.RuleType<?> p_223596_2_) {
      p_223596_0_.func_223481_a((GameRules.RuleKey)p_223596_1_, p_223596_2_);
   }

   public boolean getBoolean(GameRules.RuleKey<GameRules.BooleanValue> key) {
      return this.get(key).get();
   }

   public int getInt(GameRules.RuleKey<GameRules.IntegerValue> key) {
      return this.get(key).get();
   }

   public static class BooleanValue extends GameRules.RuleValue<GameRules.BooleanValue> {
      private boolean value;

      private static GameRules.RuleType<GameRules.BooleanValue> create(boolean p_223567_0_, BiConsumer<MinecraftServer, GameRules.BooleanValue> p_223567_1_) {
         return new GameRules.RuleType<>(BoolArgumentType::bool, (p_223574_1_) -> {
            return new GameRules.BooleanValue(p_223574_1_, p_223567_0_);
         }, p_223567_1_);
      }

      private static GameRules.RuleType<GameRules.BooleanValue> create(boolean p_223568_0_) {
         return create(p_223568_0_, (p_223569_0_, p_223569_1_) -> {
         });
      }

      public BooleanValue(GameRules.RuleType<GameRules.BooleanValue> p_i51535_1_, boolean p_i51535_2_) {
         super(p_i51535_1_);
         this.value = p_i51535_2_;
      }

      protected void func_223555_a(CommandContext<CommandSource> p_223555_1_, String p_223555_2_) {
         this.value = BoolArgumentType.getBool(p_223555_1_, p_223555_2_);
      }

      public boolean get() {
         return this.value;
      }

      public void set(boolean p_223570_1_, @Nullable MinecraftServer p_223570_2_) {
         this.value = p_223570_1_;
         this.func_223556_a(p_223570_2_);
      }

      protected String func_223552_b() {
         return Boolean.toString(this.value);
      }

      protected void func_223553_a(String p_223553_1_) {
         this.value = Boolean.parseBoolean(p_223553_1_);
      }

      public int func_223557_c() {
         return this.value ? 1 : 0;
      }

      protected GameRules.BooleanValue func_223213_e_() {
         return this;
      }
   }

   @FunctionalInterface
   public interface IRuleEntryVisitor {
      <T extends GameRules.RuleValue<T>> void func_223481_a(GameRules.RuleKey<T> p_223481_1_, GameRules.RuleType<T> p_223481_2_);
   }

   public static class IntegerValue extends GameRules.RuleValue<GameRules.IntegerValue> {
      private int value;

      private static GameRules.RuleType<GameRules.IntegerValue> func_223564_a(int p_223564_0_, BiConsumer<MinecraftServer, GameRules.IntegerValue> p_223564_1_) {
         return new GameRules.RuleType<>(IntegerArgumentType::integer, (p_223565_1_) -> {
            return new GameRules.IntegerValue(p_223565_1_, p_223564_0_);
         }, p_223564_1_);
      }

      private static GameRules.RuleType<GameRules.IntegerValue> create(int p_223559_0_) {
         return func_223564_a(p_223559_0_, (p_223561_0_, p_223561_1_) -> {
         });
      }

      public IntegerValue(GameRules.RuleType<GameRules.IntegerValue> p_i51534_1_, int p_i51534_2_) {
         super(p_i51534_1_);
         this.value = p_i51534_2_;
      }

      protected void func_223555_a(CommandContext<CommandSource> p_223555_1_, String p_223555_2_) {
         this.value = IntegerArgumentType.getInteger(p_223555_1_, p_223555_2_);
      }

      public int get() {
         return this.value;
      }

      protected String func_223552_b() {
         return Integer.toString(this.value);
      }

      protected void func_223553_a(String p_223553_1_) {
         this.value = func_223563_b(p_223553_1_);
      }

      private static int func_223563_b(String p_223563_0_) {
         if (!p_223563_0_.isEmpty()) {
            try {
               return Integer.parseInt(p_223563_0_);
            } catch (NumberFormatException var2) {
               GameRules.LOGGER.warn("Failed to parse integer {}", (Object)p_223563_0_);
            }
         }

         return 0;
      }

      public int func_223557_c() {
         return this.value;
      }

      protected GameRules.IntegerValue func_223213_e_() {
         return this;
      }
   }

   public static final class RuleKey<T extends GameRules.RuleValue<T>> {
      private final String field_223578_a;

      public RuleKey(String p_i51533_1_) {
         this.field_223578_a = p_i51533_1_;
      }

      public String toString() {
         return this.field_223578_a;
      }

      public boolean equals(Object p_equals_1_) {
         if (this == p_equals_1_) {
            return true;
         } else {
            return p_equals_1_ instanceof GameRules.RuleKey && ((GameRules.RuleKey)p_equals_1_).field_223578_a.equals(this.field_223578_a);
         }
      }

      public int hashCode() {
         return this.field_223578_a.hashCode();
      }

      public String func_223576_a() {
         return this.field_223578_a;
      }
   }

   public static class RuleType<T extends GameRules.RuleValue<T>> {
      private final Supplier<ArgumentType<?>> field_223582_a;
      private final Function<GameRules.RuleType<T>, T> field_223583_b;
      private final BiConsumer<MinecraftServer, T> field_223584_c;

      private RuleType(Supplier<ArgumentType<?>> p_i51531_1_, Function<GameRules.RuleType<T>, T> p_i51531_2_, BiConsumer<MinecraftServer, T> p_i51531_3_) {
         this.field_223582_a = p_i51531_1_;
         this.field_223583_b = p_i51531_2_;
         this.field_223584_c = p_i51531_3_;
      }

      public RequiredArgumentBuilder<CommandSource, ?> func_223581_a(String p_223581_1_) {
         return Commands.argument(p_223581_1_, this.field_223582_a.get());
      }

      public T func_223579_a() {
         return (T)(this.field_223583_b.apply(this));
      }
   }

   public abstract static class RuleValue<T extends GameRules.RuleValue<T>> {
      private final GameRules.RuleType<T> type;

      public RuleValue(GameRules.RuleType<T> type) {
         this.type = type;
      }

      protected abstract void func_223555_a(CommandContext<CommandSource> p_223555_1_, String p_223555_2_);

      public void func_223554_b(CommandContext<CommandSource> p_223554_1_, String p_223554_2_) {
         this.func_223555_a(p_223554_1_, p_223554_2_);
         this.func_223556_a(p_223554_1_.getSource().getServer());
      }

      protected void func_223556_a(@Nullable MinecraftServer p_223556_1_) {
         if (p_223556_1_ != null) {
            this.type.field_223584_c.accept(p_223556_1_, (T)this.func_223213_e_());
         }

      }

      protected abstract void func_223553_a(String p_223553_1_);

      protected abstract String func_223552_b();

      public String toString() {
         return this.func_223552_b();
      }

      public abstract int func_223557_c();

      protected abstract T func_223213_e_();
   }
}