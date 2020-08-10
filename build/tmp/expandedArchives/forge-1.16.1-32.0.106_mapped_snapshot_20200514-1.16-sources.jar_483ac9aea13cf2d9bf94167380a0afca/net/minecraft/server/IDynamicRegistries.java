package net.minecraft.server;

import com.mojang.serialization.Codec;
import com.mojang.serialization.Lifecycle;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.MutableRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.SimpleRegistry;
import net.minecraft.world.DimensionType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface IDynamicRegistries {
   <E> Optional<MutableRegistry<E>> func_230521_a_(RegistryKey<Registry<E>> p_230521_1_);

   @OnlyIn(Dist.CLIENT)
   Registry<DimensionType> func_230520_a_();

   static IDynamicRegistries.Impl func_239770_b_() {
      return DimensionType.func_236027_a_(new IDynamicRegistries.Impl());
   }

   public static final class Impl implements IDynamicRegistries {
      public static final Codec<IDynamicRegistries.Impl> field_239771_a_ = SimpleRegistry.func_241743_a_(Registry.field_239698_ad_, Lifecycle.experimental(), DimensionType.field_235997_a_).xmap(IDynamicRegistries.Impl::new, (p_239773_0_) -> {
         return p_239773_0_.field_239772_b_;
      }).fieldOf("dimension").codec();
      private final SimpleRegistry<DimensionType> field_239772_b_;

      public Impl() {
         this(new SimpleRegistry<>(Registry.field_239698_ad_, Lifecycle.experimental()));
      }

      private Impl(SimpleRegistry<DimensionType> p_i232511_1_) {
         this.field_239772_b_ = p_i232511_1_;
      }

      public void func_239774_a_(RegistryKey<DimensionType> p_239774_1_, DimensionType p_239774_2_) {
         this.field_239772_b_.register(p_239774_1_, p_239774_2_);
      }

      public <E> Optional<MutableRegistry<E>> func_230521_a_(RegistryKey<Registry<E>> p_230521_1_) {
         return Objects.equals(p_230521_1_, Registry.field_239698_ad_) ? Optional.of((MutableRegistry<E>) this.field_239772_b_) : Optional.empty();
      }

      public Registry<DimensionType> func_230520_a_() {
         return this.field_239772_b_;
      }
   }
}