package net.minecraft.resources;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Set;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.resources.data.IMetadataSectionSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface IResourcePack extends Closeable, net.minecraftforge.client.extensions.IForgeResourcePack {
   @OnlyIn(Dist.CLIENT)
   InputStream getRootResourceStream(String fileName) throws IOException;

   InputStream getResourceStream(ResourcePackType type, ResourceLocation location) throws IOException;

   Collection<ResourceLocation> func_225637_a_(ResourcePackType p_225637_1_, String p_225637_2_, String p_225637_3_, int p_225637_4_, Predicate<String> p_225637_5_);

   boolean resourceExists(ResourcePackType type, ResourceLocation location);

   Set<String> getResourceNamespaces(ResourcePackType type);

   @Nullable
   <T> T getMetadata(IMetadataSectionSerializer<T> deserializer) throws IOException;

   String getName();
}