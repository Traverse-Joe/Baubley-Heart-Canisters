package net.minecraft.advancements.criterion;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.scoreboard.Team;
import net.minecraft.tags.Tag;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.server.ServerWorld;

public class EntityPredicate {
   public static final EntityPredicate ANY = new EntityPredicate(EntityTypePredicate.ANY, DistancePredicate.ANY, LocationPredicate.ANY, MobEffectsPredicate.ANY, NBTPredicate.ANY, EntityFlagsPredicate.ALWAYS_TRUE, EntityEquipmentPredicate.ANY, PlayerPredicate.field_226989_a_, (String)null, (ResourceLocation)null);
   public static final EntityPredicate[] ANY_ARRAY = new EntityPredicate[0];
   private final EntityTypePredicate type;
   private final DistancePredicate distance;
   private final LocationPredicate location;
   private final MobEffectsPredicate effects;
   private final NBTPredicate nbt;
   private final EntityFlagsPredicate flags;
   private final EntityEquipmentPredicate field_217995_i;
   private final PlayerPredicate field_226609_j_;
   @Nullable
   private final String field_226610_k_;
   @Nullable
   private final ResourceLocation field_217996_j;

   private EntityPredicate(EntityTypePredicate p_i225735_1_, DistancePredicate p_i225735_2_, LocationPredicate p_i225735_3_, MobEffectsPredicate p_i225735_4_, NBTPredicate p_i225735_5_, EntityFlagsPredicate p_i225735_6_, EntityEquipmentPredicate p_i225735_7_, PlayerPredicate p_i225735_8_, @Nullable String p_i225735_9_, @Nullable ResourceLocation p_i225735_10_) {
      this.type = p_i225735_1_;
      this.distance = p_i225735_2_;
      this.location = p_i225735_3_;
      this.effects = p_i225735_4_;
      this.nbt = p_i225735_5_;
      this.flags = p_i225735_6_;
      this.field_217995_i = p_i225735_7_;
      this.field_226609_j_ = p_i225735_8_;
      this.field_226610_k_ = p_i225735_9_;
      this.field_217996_j = p_i225735_10_;
   }

   public boolean test(ServerPlayerEntity player, @Nullable Entity entity) {
      return this.func_217993_a(player.getServerWorld(), player.getPositionVec(), entity);
   }

   public boolean func_217993_a(ServerWorld p_217993_1_, @Nullable Vec3d p_217993_2_, @Nullable Entity p_217993_3_) {
      if (this == ANY) {
         return true;
      } else if (p_217993_3_ == null) {
         return false;
      } else if (!this.type.test(p_217993_3_.getType())) {
         return false;
      } else {
         if (p_217993_2_ == null) {
            if (this.distance != DistancePredicate.ANY) {
               return false;
            }
         } else if (!this.distance.test(p_217993_2_.x, p_217993_2_.y, p_217993_2_.z, p_217993_3_.func_226277_ct_(), p_217993_3_.func_226278_cu_(), p_217993_3_.func_226281_cx_())) {
            return false;
         }

         if (!this.location.test(p_217993_1_, p_217993_3_.func_226277_ct_(), p_217993_3_.func_226278_cu_(), p_217993_3_.func_226281_cx_())) {
            return false;
         } else if (!this.effects.test(p_217993_3_)) {
            return false;
         } else if (!this.nbt.test(p_217993_3_)) {
            return false;
         } else if (!this.flags.test(p_217993_3_)) {
            return false;
         } else if (!this.field_217995_i.test(p_217993_3_)) {
            return false;
         } else if (!this.field_226609_j_.func_226998_a_(p_217993_3_)) {
            return false;
         } else {
            if (this.field_226610_k_ != null) {
               Team team = p_217993_3_.getTeam();
               if (team == null || !this.field_226610_k_.equals(team.getName())) {
                  return false;
               }
            }

            return this.field_217996_j == null || p_217993_3_ instanceof CatEntity && ((CatEntity)p_217993_3_).getCatTypeName().equals(this.field_217996_j);
         }
      }
   }

   public static EntityPredicate deserialize(@Nullable JsonElement element) {
      if (element != null && !element.isJsonNull()) {
         JsonObject jsonobject = JSONUtils.getJsonObject(element, "entity");
         EntityTypePredicate entitytypepredicate = EntityTypePredicate.deserialize(jsonobject.get("type"));
         DistancePredicate distancepredicate = DistancePredicate.deserialize(jsonobject.get("distance"));
         LocationPredicate locationpredicate = LocationPredicate.deserialize(jsonobject.get("location"));
         MobEffectsPredicate mobeffectspredicate = MobEffectsPredicate.deserialize(jsonobject.get("effects"));
         NBTPredicate nbtpredicate = NBTPredicate.deserialize(jsonobject.get("nbt"));
         EntityFlagsPredicate entityflagspredicate = EntityFlagsPredicate.deserialize(jsonobject.get("flags"));
         EntityEquipmentPredicate entityequipmentpredicate = EntityEquipmentPredicate.deserialize(jsonobject.get("equipment"));
         PlayerPredicate playerpredicate = PlayerPredicate.func_227000_a_(jsonobject.get("player"));
         String s = JSONUtils.getString(jsonobject, "team", (String)null);
         ResourceLocation resourcelocation = jsonobject.has("catType") ? new ResourceLocation(JSONUtils.getString(jsonobject, "catType")) : null;
         return (new EntityPredicate.Builder()).type(entitytypepredicate).distance(distancepredicate).location(locationpredicate).effects(mobeffectspredicate).nbt(nbtpredicate).func_217987_a(entityflagspredicate).func_217985_a(entityequipmentpredicate).func_226613_a_(playerpredicate).func_226614_a_(s).func_217988_b(resourcelocation).build();
      } else {
         return ANY;
      }
   }

   public static EntityPredicate[] deserializeArray(@Nullable JsonElement array) {
      if (array != null && !array.isJsonNull()) {
         JsonArray jsonarray = JSONUtils.getJsonArray(array, "entities");
         EntityPredicate[] aentitypredicate = new EntityPredicate[jsonarray.size()];

         for(int i = 0; i < jsonarray.size(); ++i) {
            aentitypredicate[i] = deserialize(jsonarray.get(i));
         }

         return aentitypredicate;
      } else {
         return ANY_ARRAY;
      }
   }

   public JsonElement serialize() {
      if (this == ANY) {
         return JsonNull.INSTANCE;
      } else {
         JsonObject jsonobject = new JsonObject();
         jsonobject.add("type", this.type.serialize());
         jsonobject.add("distance", this.distance.serialize());
         jsonobject.add("location", this.location.serialize());
         jsonobject.add("effects", this.effects.serialize());
         jsonobject.add("nbt", this.nbt.serialize());
         jsonobject.add("flags", this.flags.serialize());
         jsonobject.add("equipment", this.field_217995_i.serialize());
         jsonobject.add("player", this.field_226609_j_.func_226995_a_());
         jsonobject.addProperty("team", this.field_226610_k_);
         if (this.field_217996_j != null) {
            jsonobject.addProperty("catType", this.field_217996_j.toString());
         }

         return jsonobject;
      }
   }

   public static JsonElement serializeArray(EntityPredicate[] predicates) {
      if (predicates == ANY_ARRAY) {
         return JsonNull.INSTANCE;
      } else {
         JsonArray jsonarray = new JsonArray();

         for(EntityPredicate entitypredicate : predicates) {
            JsonElement jsonelement = entitypredicate.serialize();
            if (!jsonelement.isJsonNull()) {
               jsonarray.add(jsonelement);
            }
         }

         return jsonarray;
      }
   }

   public static class Builder {
      private EntityTypePredicate type = EntityTypePredicate.ANY;
      private DistancePredicate distance = DistancePredicate.ANY;
      private LocationPredicate location = LocationPredicate.ANY;
      private MobEffectsPredicate effects = MobEffectsPredicate.ANY;
      private NBTPredicate nbt = NBTPredicate.ANY;
      private EntityFlagsPredicate field_217990_f = EntityFlagsPredicate.ALWAYS_TRUE;
      private EntityEquipmentPredicate field_217991_g = EntityEquipmentPredicate.ANY;
      private PlayerPredicate field_226611_h_ = PlayerPredicate.field_226989_a_;
      private String field_226612_i_;
      private ResourceLocation field_217992_h;

      public static EntityPredicate.Builder create() {
         return new EntityPredicate.Builder();
      }

      public EntityPredicate.Builder type(EntityType<?> p_203998_1_) {
         this.type = EntityTypePredicate.func_217999_b(p_203998_1_);
         return this;
      }

      public EntityPredicate.Builder func_217989_a(Tag<EntityType<?>> p_217989_1_) {
         this.type = EntityTypePredicate.func_217998_a(p_217989_1_);
         return this;
      }

      public EntityPredicate.Builder func_217986_a(ResourceLocation p_217986_1_) {
         this.field_217992_h = p_217986_1_;
         return this;
      }

      public EntityPredicate.Builder type(EntityTypePredicate p_209366_1_) {
         this.type = p_209366_1_;
         return this;
      }

      public EntityPredicate.Builder distance(DistancePredicate p_203997_1_) {
         this.distance = p_203997_1_;
         return this;
      }

      public EntityPredicate.Builder location(LocationPredicate p_203999_1_) {
         this.location = p_203999_1_;
         return this;
      }

      public EntityPredicate.Builder effects(MobEffectsPredicate p_209367_1_) {
         this.effects = p_209367_1_;
         return this;
      }

      public EntityPredicate.Builder nbt(NBTPredicate p_209365_1_) {
         this.nbt = p_209365_1_;
         return this;
      }

      public EntityPredicate.Builder func_217987_a(EntityFlagsPredicate p_217987_1_) {
         this.field_217990_f = p_217987_1_;
         return this;
      }

      public EntityPredicate.Builder func_217985_a(EntityEquipmentPredicate p_217985_1_) {
         this.field_217991_g = p_217985_1_;
         return this;
      }

      public EntityPredicate.Builder func_226613_a_(PlayerPredicate p_226613_1_) {
         this.field_226611_h_ = p_226613_1_;
         return this;
      }

      public EntityPredicate.Builder func_226614_a_(@Nullable String p_226614_1_) {
         this.field_226612_i_ = p_226614_1_;
         return this;
      }

      public EntityPredicate.Builder func_217988_b(@Nullable ResourceLocation p_217988_1_) {
         this.field_217992_h = p_217988_1_;
         return this;
      }

      public EntityPredicate build() {
         return new EntityPredicate(this.type, this.distance, this.location, this.effects, this.nbt, this.field_217990_f, this.field_217991_g, this.field_226611_h_, this.field_226612_i_, this.field_217992_h);
      }
   }
}