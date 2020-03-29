package net.minecraft.resources;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.resources.data.IMetadataSectionSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VanillaPack implements IResourcePack {
   public static Path basePath;
   private static final Logger LOGGER = LogManager.getLogger();
   public static Class<?> baseClass;
   private static final Map<ResourcePackType, FileSystem> field_217810_e = Util.make(Maps.newHashMap(), (p_217809_0_) -> {
      synchronized(VanillaPack.class) {
         for(ResourcePackType resourcepacktype : ResourcePackType.values()) {
            URL url = VanillaPack.class.getResource("/" + resourcepacktype.getDirectoryName() + "/.mcassetsroot");

            try {
               URI uri = url.toURI();
               if ("jar".equals(uri.getScheme())) {
                  FileSystem filesystem;
                  try {
                     filesystem = FileSystems.getFileSystem(uri);
                  } catch (FileSystemNotFoundException var11) {
                     filesystem = FileSystems.newFileSystem(uri, Collections.emptyMap());
                  }

                  p_217809_0_.put(resourcepacktype, filesystem);
               }
            } catch (IOException | URISyntaxException urisyntaxexception) {
               LOGGER.error("Couldn't get a list of all vanilla resources", (Throwable)urisyntaxexception);
            }
         }

      }
   });
   public final Set<String> resourceNamespaces;

   public VanillaPack(String... resourceNamespacesIn) {
      this.resourceNamespaces = ImmutableSet.copyOf(resourceNamespacesIn);
   }

   public InputStream getRootResourceStream(String fileName) throws IOException {
      if (!fileName.contains("/") && !fileName.contains("\\")) {
         if (basePath != null) {
            Path path = basePath.resolve(fileName);
            if (Files.exists(path)) {
               return Files.newInputStream(path);
            }
         }

         return this.getInputStreamVanilla(fileName);
      } else {
         throw new IllegalArgumentException("Root resources can only be filenames, not paths (no / allowed!)");
      }
   }

   public InputStream getResourceStream(ResourcePackType type, ResourceLocation location) throws IOException {
      InputStream inputstream = this.getInputStreamVanilla(type, location);
      if (inputstream != null) {
         return inputstream;
      } else {
         throw new FileNotFoundException(location.getPath());
      }
   }

   public Collection<ResourceLocation> func_225637_a_(ResourcePackType p_225637_1_, String p_225637_2_, String p_225637_3_, int p_225637_4_, Predicate<String> p_225637_5_) {
      Set<ResourceLocation> set = Sets.newHashSet();
      if (basePath != null) {
         try {
            func_229867_a_(set, p_225637_4_, p_225637_2_, basePath.resolve(p_225637_1_.getDirectoryName()), p_225637_3_, p_225637_5_);
         } catch (IOException var15) {
            ;
         }

         if (p_225637_1_ == ResourcePackType.CLIENT_RESOURCES) {
            Enumeration<URL> enumeration = null;

            try {
               enumeration = baseClass.getClassLoader().getResources(p_225637_1_.getDirectoryName() + "/");
            } catch (IOException var14) {
               ;
            }

            while(enumeration != null && enumeration.hasMoreElements()) {
               try {
                  URI uri = enumeration.nextElement().toURI();
                  if ("file".equals(uri.getScheme())) {
                     func_229867_a_(set, p_225637_4_, p_225637_2_, Paths.get(uri), p_225637_3_, p_225637_5_);
                  }
               } catch (IOException | URISyntaxException var13) {
                  ;
               }
            }
         }
      }

      try {
         URL url1 = VanillaPack.class.getResource("/" + p_225637_1_.getDirectoryName() + "/.mcassetsroot");
         if (url1 == null) {
            LOGGER.error("Couldn't find .mcassetsroot, cannot load vanilla resources");
            return set;
         }

         URI uri1 = url1.toURI();
         if ("file".equals(uri1.getScheme())) {
            URL url = new URL(url1.toString().substring(0, url1.toString().length() - ".mcassetsroot".length()));
            Path path = Paths.get(url.toURI());
            func_229867_a_(set, p_225637_4_, p_225637_2_, path, p_225637_3_, p_225637_5_);
         } else if ("jar".equals(uri1.getScheme())) {
            Path path1 = field_217810_e.get(p_225637_1_).getPath("/" + p_225637_1_.getDirectoryName());
            func_229867_a_(set, p_225637_4_, "minecraft", path1, p_225637_3_, p_225637_5_);
         } else {
            LOGGER.error("Unsupported scheme {} trying to list vanilla resources (NYI?)", (Object)uri1);
         }
      } catch (NoSuchFileException | FileNotFoundException var11) {
         ;
      } catch (IOException | URISyntaxException urisyntaxexception) {
         LOGGER.error("Couldn't get a list of all vanilla resources", (Throwable)urisyntaxexception);
      }

      return set;
   }

   private static void func_229867_a_(Collection<ResourceLocation> p_229867_0_, int p_229867_1_, String p_229867_2_, Path p_229867_3_, String p_229867_4_, Predicate<String> p_229867_5_) throws IOException {
      Path path = p_229867_3_.resolve(p_229867_2_);

      try (Stream<Path> stream = Files.walk(path.resolve(p_229867_4_), p_229867_1_)) {
         stream.filter((p_229868_1_) -> {
            return !p_229868_1_.endsWith(".mcmeta") && Files.isRegularFile(p_229868_1_) && p_229867_5_.test(p_229868_1_.getFileName().toString());
         }).map((p_229866_2_) -> {
            return new ResourceLocation(p_229867_2_, path.relativize(p_229866_2_).toString().replaceAll("\\\\", "/"));
         }).forEach(p_229867_0_::add);
      }

   }

   @Nullable
   protected InputStream getInputStreamVanilla(ResourcePackType type, ResourceLocation location) {
      String s = func_223458_d(type, location);
      if (basePath != null) {
         Path path = basePath.resolve(type.getDirectoryName() + "/" + location.getNamespace() + "/" + location.getPath());
         if (Files.exists(path)) {
            try {
               return Files.newInputStream(path);
            } catch (IOException var7) {
               ;
            }
         }
      }

      try {
         URL url = VanillaPack.class.getResource(s);
         return func_223459_a(s, url) ? getExtraInputStream(type, s) : null;
      } catch (IOException var6) {
         return VanillaPack.class.getResourceAsStream(s);
      }
   }

   private static String func_223458_d(ResourcePackType p_223458_0_, ResourceLocation p_223458_1_) {
      return "/" + p_223458_0_.getDirectoryName() + "/" + p_223458_1_.getNamespace() + "/" + p_223458_1_.getPath();
   }

   private static boolean func_223459_a(String p_223459_0_, @Nullable URL p_223459_1_) throws IOException {
      return p_223459_1_ != null && (p_223459_1_.getProtocol().equals("jar") || FolderPack.validatePath(new File(p_223459_1_.getFile()), p_223459_0_));
   }

   @Nullable
   protected InputStream getInputStreamVanilla(String pathIn) {
      return getExtraInputStream(ResourcePackType.SERVER_DATA, "/" + pathIn);
   }

   public boolean resourceExists(ResourcePackType type, ResourceLocation location) {
      String s = func_223458_d(type, location);
      if (basePath != null) {
         Path path = basePath.resolve(type.getDirectoryName() + "/" + location.getNamespace() + "/" + location.getPath());
         if (Files.exists(path)) {
            return true;
         }
      }

      try {
         URL url = VanillaPack.class.getResource(s);
         return func_223459_a(s, url);
      } catch (IOException var5) {
         return false;
      }
   }

   public Set<String> getResourceNamespaces(ResourcePackType type) {
      return this.resourceNamespaces;
   }

   @Nullable
   public <T> T getMetadata(IMetadataSectionSerializer<T> deserializer) throws IOException {
      try (InputStream inputstream = this.getRootResourceStream("pack.mcmeta")) {
         Object object = ResourcePack.<T>getResourceMetadata(deserializer, inputstream);
         return (T)object;
      } catch (FileNotFoundException | RuntimeException var16) {
         return (T)null;
      }
   }

   public String getName() {
      return "Default";
   }

   public void close() {
   }

   //Vanilla used to just grab from the classpath, this breaks dev environments, and Forge runtime
   //as forge ships vanilla assets in an 'extra' jar with no classes.
   //So find that extra jar using the .mcassetsroot marker.
   private InputStream getExtraInputStream(ResourcePackType type, String resource) {
      try {
         FileSystem fs = field_217810_e.get(type);
         if (fs != null)
            return Files.newInputStream(fs.getPath(resource));
         return VanillaPack.class.getResourceAsStream(resource);
      } catch (IOException e) {
         return VanillaPack.class.getResourceAsStream(resource);
      }
   }
}