package net.minecraft.entity;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum EntityClassification implements net.minecraftforge.common.IExtensibleEnum {
   MONSTER("monster", 70, false, false),
   CREATURE("creature", 10, true, true),
   AMBIENT("ambient", 15, true, false),
   WATER_CREATURE("water_creature", 15, true, false),
   MISC("misc", 15, true, false);

   private static final Map<String, EntityClassification> field_220364_f = Arrays.stream(values()).collect(Collectors.toMap(EntityClassification::func_220363_a, (p_220362_0_) -> {
      return p_220362_0_;
   }));
   private final int maxNumberOfCreature;
   private final boolean isPeacefulCreature;
   private final boolean isAnimal;
   private final String field_220365_j;

   private EntityClassification(String p_i50381_3_, int p_i50381_4_, boolean p_i50381_5_, boolean p_i50381_6_) {
      this.field_220365_j = p_i50381_3_;
      this.maxNumberOfCreature = p_i50381_4_;
      this.isPeacefulCreature = p_i50381_5_;
      this.isAnimal = p_i50381_6_;
   }

   public String func_220363_a() {
      return this.field_220365_j;
   }

   public int getMaxNumberOfCreature() {
      return this.maxNumberOfCreature;
   }

   /**
    * Gets whether or not this creature type is peaceful.
    */
   public boolean getPeacefulCreature() {
      return this.isPeacefulCreature;
   }

   /**
    * Return whether this creature type is an animal.
    */
   public boolean getAnimal() {
      return this.isAnimal;
   }

   public static EntityClassification create(String name, String p_i50381_3_, int p_i50381_4_, boolean p_i50381_5_, boolean p_i50381_6_) {
      throw new IllegalStateException("Enum not extended");
   }
}