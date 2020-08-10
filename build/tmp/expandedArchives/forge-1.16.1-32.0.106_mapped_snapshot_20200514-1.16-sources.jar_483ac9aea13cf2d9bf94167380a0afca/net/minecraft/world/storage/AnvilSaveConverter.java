package net.minecraft.world.storage;

import com.google.common.collect.Lists;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.NBTDynamicOps;
import net.minecraft.resources.IResourceManager;
import net.minecraft.server.IDynamicRegistries;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.datafix.codec.DatapackCodec;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.WorldSettingsImport;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.biome.provider.OverworldBiomeProvider;
import net.minecraft.world.biome.provider.SingleBiomeProvider;
import net.minecraft.world.chunk.storage.ChunkLoaderUtil;
import net.minecraft.world.chunk.storage.RegionFile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AnvilSaveConverter {
   private static final Logger LOGGER = LogManager.getLogger();

   static boolean func_237330_a_(SaveFormat.LevelSave p_237330_0_, IProgressUpdate p_237330_1_) {
      p_237330_1_.setLoadingProgress(0);
      List<File> list = Lists.newArrayList();
      List<File> list1 = Lists.newArrayList();
      List<File> list2 = Lists.newArrayList();
      File file1 = p_237330_0_.func_237291_a_(World.field_234918_g_);
      File file2 = p_237330_0_.func_237291_a_(World.field_234919_h_);
      File file3 = p_237330_0_.func_237291_a_(World.field_234920_i_);
      LOGGER.info("Scanning folders...");
      func_215789_a(file1, list);
      if (file2.exists()) {
         func_215789_a(file2, list1);
      }

      if (file3.exists()) {
         func_215789_a(file3, list2);
      }

      int i = list.size() + list1.size() + list2.size();
      LOGGER.info("Total conversion count is {}", (int)i);
      IDynamicRegistries.Impl idynamicregistries$impl = IDynamicRegistries.func_239770_b_();
      WorldSettingsImport<INBT> worldsettingsimport = WorldSettingsImport.func_240876_a_(NBTDynamicOps.INSTANCE, IResourceManager.Instance.INSTANCE, idynamicregistries$impl);
      IServerConfiguration iserverconfiguration = p_237330_0_.func_237284_a_(worldsettingsimport, DatapackCodec.field_234880_a_);
      long j = iserverconfiguration != null ? iserverconfiguration.func_230418_z_().func_236221_b_() : 0L;
      BiomeProvider biomeprovider;
      if (iserverconfiguration != null && iserverconfiguration.func_230418_z_().func_236228_i_()) {
         biomeprovider = new SingleBiomeProvider(Biomes.PLAINS);
      } else {
         biomeprovider = new OverworldBiomeProvider(j, false, false);
      }

      func_215794_a(new File(file1, "region"), list, biomeprovider, 0, i, p_237330_1_);
      func_215794_a(new File(file2, "region"), list1, new SingleBiomeProvider(Biomes.field_235254_j_), list.size(), i, p_237330_1_);
      func_215794_a(new File(file3, "region"), list2, new SingleBiomeProvider(Biomes.THE_END), list.size() + list1.size(), i, p_237330_1_);
      func_237329_a_(p_237330_0_);
      p_237330_0_.func_237287_a_(idynamicregistries$impl, iserverconfiguration);
      return true;
   }

   private static void func_237329_a_(SaveFormat.LevelSave p_237329_0_) {
      File file1 = p_237329_0_.func_237285_a_(FolderName.field_237249_e_).toFile();
      if (!file1.exists()) {
         LOGGER.warn("Unable to create level.dat_mcr backup");
      } else {
         File file2 = new File(file1.getParent(), "level.dat_mcr");
         if (!file1.renameTo(file2)) {
            LOGGER.warn("Unable to create level.dat_mcr backup");
         }

      }
   }

   private static void func_215794_a(File p_215794_0_, Iterable<File> p_215794_1_, BiomeProvider p_215794_2_, int p_215794_3_, int p_215794_4_, IProgressUpdate p_215794_5_) {
      for(File file1 : p_215794_1_) {
         func_215793_a(p_215794_0_, file1, p_215794_2_, p_215794_3_, p_215794_4_, p_215794_5_);
         ++p_215794_3_;
         int i = (int)Math.round(100.0D * (double)p_215794_3_ / (double)p_215794_4_);
         p_215794_5_.setLoadingProgress(i);
      }

   }

   private static void func_215793_a(File p_215793_0_, File p_215793_1_, BiomeProvider p_215793_2_, int p_215793_3_, int p_215793_4_, IProgressUpdate p_215793_5_) {
      String s = p_215793_1_.getName();

      try (
         RegionFile regionfile = new RegionFile(p_215793_1_, p_215793_0_, true);
         RegionFile regionfile1 = new RegionFile(new File(p_215793_0_, s.substring(0, s.length() - ".mcr".length()) + ".mca"), p_215793_0_, true);
      ) {
         for(int i = 0; i < 32; ++i) {
            for(int j = 0; j < 32; ++j) {
               ChunkPos chunkpos = new ChunkPos(i, j);
               if (regionfile.contains(chunkpos) && !regionfile1.contains(chunkpos)) {
                  CompoundNBT compoundnbt;
                  try (DataInputStream datainputstream = regionfile.func_222666_a(chunkpos)) {
                     if (datainputstream == null) {
                        LOGGER.warn("Failed to fetch input stream for chunk {}", (Object)chunkpos);
                        continue;
                     }

                     compoundnbt = CompressedStreamTools.read(datainputstream);
                  } catch (IOException ioexception) {
                     LOGGER.warn("Failed to read data for chunk {}", chunkpos, ioexception);
                     continue;
                  }

                  CompoundNBT compoundnbt3 = compoundnbt.getCompound("Level");
                  ChunkLoaderUtil.AnvilConverterData chunkloaderutil$anvilconverterdata = ChunkLoaderUtil.load(compoundnbt3);
                  CompoundNBT compoundnbt1 = new CompoundNBT();
                  CompoundNBT compoundnbt2 = new CompoundNBT();
                  compoundnbt1.put("Level", compoundnbt2);
                  ChunkLoaderUtil.convertToAnvilFormat(chunkloaderutil$anvilconverterdata, compoundnbt2, p_215793_2_);

                  try (DataOutputStream dataoutputstream = regionfile1.func_222661_c(chunkpos)) {
                     CompressedStreamTools.write(compoundnbt1, dataoutputstream);
                  }
               }
            }

            int k = (int)Math.round(100.0D * (double)(p_215793_3_ * 1024) / (double)(p_215793_4_ * 1024));
            int l = (int)Math.round(100.0D * (double)((i + 1) * 32 + p_215793_3_ * 1024) / (double)(p_215793_4_ * 1024));
            if (l > k) {
               p_215793_5_.setLoadingProgress(l);
            }
         }
      } catch (IOException ioexception1) {
         LOGGER.error("Failed to upgrade region file {}", p_215793_1_, ioexception1);
      }

   }

   private static void func_215789_a(File p_215789_0_, Collection<File> p_215789_1_) {
      File file1 = new File(p_215789_0_, "region");
      File[] afile = file1.listFiles((p_215791_0_, p_215791_1_) -> {
         return p_215791_1_.endsWith(".mcr");
      });
      if (afile != null) {
         Collections.addAll(p_215789_1_, afile);
      }

   }
}