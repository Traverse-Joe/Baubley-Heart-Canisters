package net.minecraft.client.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import javax.annotation.Nullable;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Monitor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWMonitorCallback;
import org.lwjgl.glfw.GLFWMonitorCallbackI;

@OnlyIn(Dist.CLIENT)
public class MonitorHandler {
   private final Long2ObjectMap<Monitor> field_216517_a = new Long2ObjectOpenHashMap<>();
   private final IMonitorFactory field_216520_d;

   public MonitorHandler(IMonitorFactory p_i51171_1_) {
      RenderSystem.assertThread(RenderSystem::isInInitPhase);
      this.field_216520_d = p_i51171_1_;
      GLFW.glfwSetMonitorCallback(this::func_216516_a);
      PointerBuffer pointerbuffer = GLFW.glfwGetMonitors();
      if (pointerbuffer != null) {
         for(int i = 0; i < pointerbuffer.limit(); ++i) {
            long j = pointerbuffer.get(i);
            this.field_216517_a.put(j, p_i51171_1_.createMonitor(j));
         }
      }

   }

   private void func_216516_a(long p_216516_1_, int p_216516_3_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      if (p_216516_3_ == 262145) {
         this.field_216517_a.put(p_216516_1_, this.field_216520_d.createMonitor(p_216516_1_));
      } else if (p_216516_3_ == 262146) {
         this.field_216517_a.remove(p_216516_1_);
      }

   }

   @Nullable
   public Monitor func_216512_a(long p_216512_1_) {
      RenderSystem.assertThread(RenderSystem::isInInitPhase);
      return this.field_216517_a.get(p_216512_1_);
   }

   @Nullable
   public Monitor func_216515_a(MainWindow p_216515_1_) {
      long i = GLFW.glfwGetWindowMonitor(p_216515_1_.getHandle());
      if (i != 0L) {
         return this.func_216512_a(i);
      } else {
         int j = p_216515_1_.getWindowX();
         int k = j + p_216515_1_.getWidth();
         int l = p_216515_1_.getWindowY();
         int i1 = l + p_216515_1_.getHeight();
         int j1 = -1;
         Monitor monitor = null;

         for(Monitor monitor1 : this.field_216517_a.values()) {
            int k1 = monitor1.getVirtualPosX();
            int l1 = k1 + monitor1.getDefaultVideoMode().getWidth();
            int i2 = monitor1.getVirtualPosY();
            int j2 = i2 + monitor1.getDefaultVideoMode().getHeight();
            int k2 = func_216513_a(j, k1, l1);
            int l2 = func_216513_a(k, k1, l1);
            int i3 = func_216513_a(l, i2, j2);
            int j3 = func_216513_a(i1, i2, j2);
            int k3 = Math.max(0, l2 - k2);
            int l3 = Math.max(0, j3 - i3);
            int i4 = k3 * l3;
            if (i4 > j1) {
               monitor = monitor1;
               j1 = i4;
            }
         }

         return monitor;
      }
   }

   public static int func_216513_a(int p_216513_0_, int p_216513_1_, int p_216513_2_) {
      if (p_216513_0_ < p_216513_1_) {
         return p_216513_1_;
      } else {
         return p_216513_0_ > p_216513_2_ ? p_216513_2_ : p_216513_0_;
      }
   }

   public void func_216514_a() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GLFWMonitorCallback glfwmonitorcallback = GLFW.glfwSetMonitorCallback((GLFWMonitorCallbackI)null);
      if (glfwmonitorcallback != null) {
         glfwmonitorcallback.free();
      }

   }
}