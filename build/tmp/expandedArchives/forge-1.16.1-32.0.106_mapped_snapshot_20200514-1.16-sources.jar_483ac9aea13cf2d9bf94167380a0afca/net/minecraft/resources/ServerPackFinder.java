package net.minecraft.resources;

import java.util.function.Consumer;

public class ServerPackFinder implements IPackFinder {
   private final VanillaPack field_195738_a = new VanillaPack("minecraft");

   public <T extends ResourcePackInfo> void func_230230_a_(Consumer<T> p_230230_1_, ResourcePackInfo.IFactory<T> p_230230_2_) {
      T t = ResourcePackInfo.createResourcePack("vanilla", false, () -> {
         return this.field_195738_a;
      }, p_230230_2_, ResourcePackInfo.Priority.BOTTOM, IPackNameDecorator.field_232626_b_);
      if (t != null) {
         p_230230_1_.accept(t);
      }

   }
}