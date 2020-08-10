package net.minecraft.resources;

import java.util.function.Consumer;

public interface IPackFinder {
   <T extends ResourcePackInfo> void func_230230_a_(Consumer<T> p_230230_1_, ResourcePackInfo.IFactory<T> p_230230_2_);
}