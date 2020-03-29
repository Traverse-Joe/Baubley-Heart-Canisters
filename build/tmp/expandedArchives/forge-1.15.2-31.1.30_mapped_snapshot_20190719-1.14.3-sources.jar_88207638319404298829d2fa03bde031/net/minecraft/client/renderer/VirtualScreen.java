package net.minecraft.client.renderer;

import javax.annotation.Nullable;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Monitor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public final class VirtualScreen implements AutoCloseable {
   private final Minecraft mc;
   private final MonitorHandler field_217627_b;

   public VirtualScreen(Minecraft mcIn) {
      this.mc = mcIn;
      this.field_217627_b = new MonitorHandler(Monitor::new);
   }

   public MainWindow create(ScreenSize p_217626_1_, @Nullable String p_217626_2_, String p_217626_3_) {
      return new MainWindow(this.mc, this.field_217627_b, p_217626_1_, p_217626_2_, p_217626_3_);
   }

   public void close() {
      this.field_217627_b.func_216514_a();
   }
}