package net.minecraft.tags;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class TagRegistry<T> {
   private final TagCollection<T> field_232929_a_ = new TagCollection<>((p_232938_0_) -> {
      return Optional.empty();
   }, "", "");
   private TagCollection<T> field_232930_b_ = this.field_232929_a_;
   private final List<TagRegistry.NamedTag<T>> field_232931_c_ = Lists.newArrayList();

   public ITag.INamedTag<T> func_232937_a_(String p_232937_1_) {
      TagRegistry.NamedTag<T> namedtag = new TagRegistry.NamedTag<>(new ResourceLocation(p_232937_1_));
      namedtag.func_232943_a_(field_232930_b_::get);
      this.field_232931_c_.add(namedtag);
      return namedtag;
   }

   @OnlyIn(Dist.CLIENT)
   public void func_232932_a_() {
      this.field_232930_b_ = this.field_232929_a_;
      ITag<T> itag = Tag.func_241284_a_();
      this.field_232931_c_.forEach((p_232933_1_) -> {
         p_232933_1_.func_232943_a_((p_232934_1_) -> {
            return itag;
         });
      });
   }

   public void func_232935_a_(TagCollection<T> p_232935_1_) {
      this.field_232930_b_ = p_232935_1_;
      this.field_232931_c_.forEach((p_232936_1_) -> {
         p_232936_1_.func_232943_a_(p_232935_1_::get);
      });
   }

   public TagCollection<T> func_232939_b_() {
      return this.field_232930_b_;
   }

   public List<TagRegistry.NamedTag<T>> func_241288_c_() {
      return this.field_232931_c_;
   }

   public Set<ResourceLocation> func_232940_b_(TagCollection<T> p_232940_1_) {
      Set<ResourceLocation> set = this.field_232931_c_.stream().map(TagRegistry.NamedTag::func_230234_a_).collect(Collectors.toSet());
      ImmutableSet<ResourceLocation> immutableset = ImmutableSet.copyOf(p_232940_1_.getRegisteredTags());
      return Sets.difference(set, immutableset);
   }

   public static class NamedTag<T> implements ITag.INamedTag<T> {
      @Nullable
      private ITag<T> field_232942_b_;
      protected final ResourceLocation field_232941_a_;

      private NamedTag(ResourceLocation p_i231430_1_) {
         this.field_232941_a_ = p_i231430_1_;
      }

      public ResourceLocation func_230234_a_() {
         return this.field_232941_a_;
      }

      private ITag<T> func_232944_c_() {
         if (this.field_232942_b_ == null) {
            throw new IllegalStateException("Tag " + this.field_232941_a_ + " used before it was bound");
         } else {
            return this.field_232942_b_;
         }
      }

      void func_232943_a_(Function<ResourceLocation, ITag<T>> p_232943_1_) {
         this.field_232942_b_ = p_232943_1_.apply(this.field_232941_a_);
      }

      public boolean func_230235_a_(T p_230235_1_) {
         return this.func_232944_c_().func_230235_a_(p_230235_1_);
      }

      public List<T> func_230236_b_() {
         return this.func_232944_c_().func_230236_b_();
      }
   }
}