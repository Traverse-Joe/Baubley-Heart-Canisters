package net.minecraft.world.biome;

public enum ColumnFuzzedBiomeMagnifier implements IBiomeMagnifier {
   INSTANCE;

   public Biome func_225532_a_(long p_225532_1_, int p_225532_3_, int p_225532_4_, int p_225532_5_, BiomeManager.IBiomeReader p_225532_6_) {
      return FuzzedBiomeMagnifier.INSTANCE.func_225532_a_(p_225532_1_, p_225532_3_, 0, p_225532_5_, p_225532_6_);
   }
}