package net.minecraft.world.storage.loot.conditions;

import com.google.common.collect.Lists;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.ValidationTracker;

public class Alternative implements ILootCondition {
   private final ILootCondition[] field_215962_a;
   private final Predicate<LootContext> field_215963_b;

   private Alternative(ILootCondition[] p_i51209_1_) {
      this.field_215962_a = p_i51209_1_;
      this.field_215963_b = LootConditionManager.or(p_i51209_1_);
   }

   public final boolean test(LootContext p_test_1_) {
      return this.field_215963_b.test(p_test_1_);
   }

   public void func_225580_a_(ValidationTracker p_225580_1_) {
      ILootCondition.super.func_225580_a_(p_225580_1_);

      for(int i = 0; i < this.field_215962_a.length; ++i) {
         this.field_215962_a[i].func_225580_a_(p_225580_1_.func_227534_b_(".term[" + i + "]"));
      }

   }

   public static Alternative.Builder builder(ILootCondition.IBuilder... p_215960_0_) {
      return new Alternative.Builder(p_215960_0_);
   }

   public static class Builder implements ILootCondition.IBuilder {
      private final List<ILootCondition> field_216303_a = Lists.newArrayList();

      public Builder(ILootCondition.IBuilder... p_i50046_1_) {
         for(ILootCondition.IBuilder ilootcondition$ibuilder : p_i50046_1_) {
            this.field_216303_a.add(ilootcondition$ibuilder.build());
         }

      }

      public Alternative.Builder alternative(ILootCondition.IBuilder p_216297_1_) {
         this.field_216303_a.add(p_216297_1_.build());
         return this;
      }

      public ILootCondition build() {
         return new Alternative(this.field_216303_a.toArray(new ILootCondition[0]));
      }
   }

   public static class Serializer extends ILootCondition.AbstractSerializer<Alternative> {
      public Serializer() {
         super(new ResourceLocation("alternative"), Alternative.class);
      }

      public void serialize(JsonObject json, Alternative value, JsonSerializationContext context) {
         json.add("terms", context.serialize(value.field_215962_a));
      }

      public Alternative deserialize(JsonObject json, JsonDeserializationContext context) {
         ILootCondition[] ailootcondition = JSONUtils.deserializeClass(json, "terms", context, ILootCondition[].class);
         return new Alternative(ailootcondition);
      }
   }
}