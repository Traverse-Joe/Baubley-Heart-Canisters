package net.minecraft.world.storage.loot.functions;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Set;
import net.minecraft.item.ItemStack;
import net.minecraft.util.INameable;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootFunction;
import net.minecraft.world.storage.loot.LootParameter;
import net.minecraft.world.storage.loot.LootParameters;
import net.minecraft.world.storage.loot.conditions.ILootCondition;

public class CopyName extends LootFunction {
   private final CopyName.Source field_215894_a;

   private CopyName(ILootCondition[] p_i51242_1_, CopyName.Source p_i51242_2_) {
      super(p_i51242_1_);
      this.field_215894_a = p_i51242_2_;
   }

   public Set<LootParameter<?>> getRequiredParameters() {
      return ImmutableSet.of(this.field_215894_a.field_216237_f);
   }

   public ItemStack doApply(ItemStack stack, LootContext context) {
      Object object = context.get(this.field_215894_a.field_216237_f);
      if (object instanceof INameable) {
         INameable inameable = (INameable)object;
         if (inameable.hasCustomName()) {
            stack.setDisplayName(inameable.getDisplayName());
         }
      }

      return stack;
   }

   public static LootFunction.Builder<?> func_215893_a(CopyName.Source p_215893_0_) {
      return builder((p_215891_1_) -> {
         return new CopyName(p_215891_1_, p_215893_0_);
      });
   }

   public static class Serializer extends LootFunction.Serializer<CopyName> {
      public Serializer() {
         super(new ResourceLocation("copy_name"), CopyName.class);
      }

      public void serialize(JsonObject object, CopyName functionClazz, JsonSerializationContext serializationContext) {
         super.serialize(object, functionClazz, serializationContext);
         object.addProperty("source", functionClazz.field_215894_a.field_216236_e);
      }

      public CopyName deserialize(JsonObject object, JsonDeserializationContext deserializationContext, ILootCondition[] conditionsIn) {
         CopyName.Source copyname$source = CopyName.Source.func_216235_a(JSONUtils.getString(object, "source"));
         return new CopyName(conditionsIn, copyname$source);
      }
   }

   public static enum Source {
      THIS("this", LootParameters.THIS_ENTITY),
      KILLER("killer", LootParameters.KILLER_ENTITY),
      KILLER_PLAYER("killer_player", LootParameters.LAST_DAMAGE_PLAYER),
      BLOCK_ENTITY("block_entity", LootParameters.BLOCK_ENTITY);

      public final String field_216236_e;
      public final LootParameter<?> field_216237_f;

      private Source(String p_i50801_3_, LootParameter<?> p_i50801_4_) {
         this.field_216236_e = p_i50801_3_;
         this.field_216237_f = p_i50801_4_;
      }

      public static CopyName.Source func_216235_a(String p_216235_0_) {
         for(CopyName.Source copyname$source : values()) {
            if (copyname$source.field_216236_e.equals(p_216235_0_)) {
               return copyname$source;
            }
         }

         throw new IllegalArgumentException("Invalid name source " + p_216235_0_);
      }
   }
}