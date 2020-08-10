package net.minecraft.resources;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface IResourceManager {
   Set<String> getResourceNamespaces();

   IResource getResource(ResourceLocation resourceLocationIn) throws IOException;

   boolean hasResource(ResourceLocation p_219533_1_);

   List<IResource> getAllResources(ResourceLocation resourceLocationIn) throws IOException;

   Collection<ResourceLocation> func_230231_a_(ResourceLocation p_230231_1_, Predicate<String> p_230231_2_);

   Collection<ResourceLocation> getAllResourceLocations(String pathIn, Predicate<String> filter);

   @OnlyIn(Dist.CLIENT)
   Stream<IResourcePack> func_230232_b_();

   public static enum Instance implements IResourceManager {
      INSTANCE;

      public Set<String> getResourceNamespaces() {
         return ImmutableSet.of();
      }

      public IResource getResource(ResourceLocation resourceLocationIn) throws IOException {
         throw new FileNotFoundException(resourceLocationIn.toString());
      }

      public boolean hasResource(ResourceLocation p_219533_1_) {
         return false;
      }

      public List<IResource> getAllResources(ResourceLocation resourceLocationIn) {
         return ImmutableList.of();
      }

      public Collection<ResourceLocation> func_230231_a_(ResourceLocation p_230231_1_, Predicate<String> p_230231_2_) {
         return ImmutableSet.of();
      }

      public Collection<ResourceLocation> getAllResourceLocations(String pathIn, Predicate<String> filter) {
         return ImmutableSet.of();
      }

      @OnlyIn(Dist.CLIENT)
      public Stream<IResourcePack> func_230232_b_() {
         return Stream.of();
      }
   }
}