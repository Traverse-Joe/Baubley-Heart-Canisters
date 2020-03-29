package net.minecraft.world.storage.loot.conditions;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Set;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.entity.Entity;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootParameter;
import net.minecraft.world.storage.loot.LootParameters;

public class EntityHasProperty implements ILootCondition {
   private final EntityPredicate field_216001_a;
   private final LootContext.EntityTarget target;

   private EntityHasProperty(EntityPredicate p_i51196_1_, LootContext.EntityTarget p_i51196_2_) {
      this.field_216001_a = p_i51196_1_;
      this.target = p_i51196_2_;
   }

   public Set<LootParameter<?>> getRequiredParameters() {
      return ImmutableSet.of(LootParameters.POSITION, this.target.getParameter());
   }

   public boolean test(LootContext p_test_1_) {
      Entity entity = p_test_1_.get(this.target.getParameter());
      BlockPos blockpos = p_test_1_.get(LootParameters.POSITION);
      return this.field_216001_a.func_217993_a(p_test_1_.getWorld(), blockpos != null ? new Vec3d(blockpos) : null, entity);
   }

   public static ILootCondition.IBuilder func_215998_a(LootContext.EntityTarget p_215998_0_) {
      return func_215999_a(p_215998_0_, EntityPredicate.Builder.create());
   }

   public static ILootCondition.IBuilder func_215999_a(LootContext.EntityTarget p_215999_0_, EntityPredicate.Builder p_215999_1_) {
      return () -> {
         return new EntityHasProperty(p_215999_1_.build(), p_215999_0_);
      };
   }

   public static class Serializer extends ILootCondition.AbstractSerializer<EntityHasProperty> {
      protected Serializer() {
         super(new ResourceLocation("entity_properties"), EntityHasProperty.class);
      }

      public void serialize(JsonObject json, EntityHasProperty value, JsonSerializationContext context) {
         json.add("predicate", value.field_216001_a.serialize());
         json.add("entity", context.serialize(value.target));
      }

      public EntityHasProperty deserialize(JsonObject json, JsonDeserializationContext context) {
         EntityPredicate entitypredicate = EntityPredicate.deserialize(json.get("predicate"));
         return new EntityHasProperty(entitypredicate, JSONUtils.deserializeClass(json, "entity", context, LootContext.EntityTarget.class));
      }
   }
}