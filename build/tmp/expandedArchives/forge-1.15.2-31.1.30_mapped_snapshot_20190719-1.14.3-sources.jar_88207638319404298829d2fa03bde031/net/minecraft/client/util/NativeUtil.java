package net.minecraft.client.util;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.MemoryUtil;

@OnlyIn(Dist.CLIENT)
public class NativeUtil {
   public static void func_216393_a() {
      MemoryUtil.memSet(0L, 0, 1L);
   }

   public static double func_216394_b() {
      return GLFW.glfwGetTime();
   }
}