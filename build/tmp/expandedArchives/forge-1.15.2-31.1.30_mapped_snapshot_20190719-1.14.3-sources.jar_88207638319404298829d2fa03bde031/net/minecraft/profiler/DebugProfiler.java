package net.minecraft.profiler;

import java.time.Duration;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import net.minecraft.util.Util;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DebugProfiler implements IProfiler {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final long field_219901_a = Duration.ofMillis(300L).toNanos();
   private final IntSupplier tickCounter;
   private final DebugProfiler.DebugResultEntryImpl field_219903_c = new DebugProfiler.DebugResultEntryImpl();
   private final DebugProfiler.DebugResultEntryImpl field_219904_d = new DebugProfiler.DebugResultEntryImpl();

   public DebugProfiler(IntSupplier p_i50406_1_) {
      this.tickCounter = p_i50406_1_;
   }

   public DebugProfiler.IDebugResultEntry func_219899_d() {
      return this.field_219903_c;
   }

   public void startTick() {
      this.field_219903_c.field_219940_a.startTick();
      this.field_219904_d.field_219940_a.startTick();
   }

   public void endTick() {
      this.field_219903_c.field_219940_a.endTick();
      this.field_219904_d.field_219940_a.endTick();
   }

   /**
    * Start section
    */
   public void startSection(String name) {
      this.field_219903_c.field_219940_a.startSection(name);
      this.field_219904_d.field_219940_a.startSection(name);
   }

   public void startSection(Supplier<String> nameSupplier) {
      this.field_219903_c.field_219940_a.startSection(nameSupplier);
      this.field_219904_d.field_219940_a.startSection(nameSupplier);
   }

   /**
    * End section
    */
   public void endSection() {
      this.field_219903_c.field_219940_a.endSection();
      this.field_219904_d.field_219940_a.endSection();
   }

   public void endStartSection(String name) {
      this.field_219903_c.field_219940_a.endStartSection(name);
      this.field_219904_d.field_219940_a.endStartSection(name);
   }

   @OnlyIn(Dist.CLIENT)
   public void endStartSection(Supplier<String> nameSupplier) {
      this.field_219903_c.field_219940_a.endStartSection(nameSupplier);
      this.field_219904_d.field_219940_a.endStartSection(nameSupplier);
   }

   public void func_230035_c_(String p_230035_1_) {
      this.field_219903_c.field_219940_a.func_230035_c_(p_230035_1_);
      this.field_219904_d.field_219940_a.func_230035_c_(p_230035_1_);
   }

   public void func_230036_c_(Supplier<String> p_230036_1_) {
      this.field_219903_c.field_219940_a.func_230036_c_(p_230036_1_);
      this.field_219904_d.field_219940_a.func_230036_c_(p_230036_1_);
   }

   class DebugResultEntryImpl implements DebugProfiler.IDebugResultEntry {
      protected IResultableProfiler field_219940_a = EmptyProfiler.INSTANCE;

      private DebugResultEntryImpl() {
      }

      public boolean isEnabled() {
         return this.field_219940_a != EmptyProfiler.INSTANCE;
      }

      public IProfileResult func_219938_b() {
         IProfileResult iprofileresult = this.field_219940_a.getResults();
         this.field_219940_a = EmptyProfiler.INSTANCE;
         return iprofileresult;
      }

      @OnlyIn(Dist.CLIENT)
      public IProfileResult func_219937_c() {
         return this.field_219940_a.getResults();
      }

      public void func_219939_d() {
         if (this.field_219940_a == EmptyProfiler.INSTANCE) {
            this.field_219940_a = new Profiler(Util.nanoTime(), DebugProfiler.this.tickCounter, true);
         }

      }
   }

   public interface IDebugResultEntry {
      boolean isEnabled();

      IProfileResult func_219938_b();

      @OnlyIn(Dist.CLIENT)
      IProfileResult func_219937_c();

      void func_219939_d();
   }
}