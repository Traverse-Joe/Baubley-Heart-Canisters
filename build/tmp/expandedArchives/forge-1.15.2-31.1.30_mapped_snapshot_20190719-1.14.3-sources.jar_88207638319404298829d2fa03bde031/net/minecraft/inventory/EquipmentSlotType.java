package net.minecraft.inventory;

public enum EquipmentSlotType {
   MAINHAND(EquipmentSlotType.Group.HAND, 0, 0, "mainhand"),
   OFFHAND(EquipmentSlotType.Group.HAND, 1, 5, "offhand"),
   FEET(EquipmentSlotType.Group.ARMOR, 0, 1, "feet"),
   LEGS(EquipmentSlotType.Group.ARMOR, 1, 2, "legs"),
   CHEST(EquipmentSlotType.Group.ARMOR, 2, 3, "chest"),
   HEAD(EquipmentSlotType.Group.ARMOR, 3, 4, "head");

   private final EquipmentSlotType.Group slotType;
   private final int index;
   private final int slotIndex;
   private final String name;

   private EquipmentSlotType(EquipmentSlotType.Group slotTypeIn, int indexIn, int slotIndexIn, String nameIn) {
      this.slotType = slotTypeIn;
      this.index = indexIn;
      this.slotIndex = slotIndexIn;
      this.name = nameIn;
   }

   public EquipmentSlotType.Group getSlotType() {
      return this.slotType;
   }

   public int getIndex() {
      return this.index;
   }

   /**
    * Gets the actual slot index.
    */
   public int getSlotIndex() {
      return this.slotIndex;
   }

   public String getName() {
      return this.name;
   }

   public static EquipmentSlotType fromString(String targetName) {
      for(EquipmentSlotType equipmentslottype : values()) {
         if (equipmentslottype.getName().equals(targetName)) {
            return equipmentslottype;
         }
      }

      throw new IllegalArgumentException("Invalid slot '" + targetName + "'");
   }

   public static EquipmentSlotType func_220318_a(EquipmentSlotType.Group p_220318_0_, int p_220318_1_) {
      for(EquipmentSlotType equipmentslottype : values()) {
         if (equipmentslottype.getSlotType() == p_220318_0_ && equipmentslottype.getIndex() == p_220318_1_) {
            return equipmentslottype;
         }
      }

      throw new IllegalArgumentException("Invalid slot '" + p_220318_0_ + "': " + p_220318_1_);
   }

   public static enum Group {
      HAND,
      ARMOR;
   }
}