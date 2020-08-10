package net.minecraft.resources;

import java.io.File;
import java.io.FileFilter;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class FolderPackFinder implements IPackFinder {
   private static final FileFilter FILE_FILTER = (p_195731_0_) -> {
      boolean flag = p_195731_0_.isFile() && p_195731_0_.getName().endsWith(".zip");
      boolean flag1 = p_195731_0_.isDirectory() && (new File(p_195731_0_, "pack.mcmeta")).isFile();
      return flag || flag1;
   };
   private final File folder;
   private final IPackNameDecorator field_232610_c_;

   public FolderPackFinder(File p_i231420_1_, IPackNameDecorator p_i231420_2_) {
      this.folder = p_i231420_1_;
      this.field_232610_c_ = p_i231420_2_;
   }

   public <T extends ResourcePackInfo> void func_230230_a_(Consumer<T> p_230230_1_, ResourcePackInfo.IFactory<T> p_230230_2_) {
      if (!this.folder.isDirectory()) {
         this.folder.mkdirs();
      }

      File[] afile = this.folder.listFiles(FILE_FILTER);
      if (afile != null) {
         for(File file1 : afile) {
            String s = "file/" + file1.getName();
            T t = ResourcePackInfo.createResourcePack(s, false, this.makePackSupplier(file1), p_230230_2_, ResourcePackInfo.Priority.TOP, this.field_232610_c_);
            if (t != null) {
               p_230230_1_.accept(t);
            }
         }

      }
   }

   private Supplier<IResourcePack> makePackSupplier(File fileIn) {
      return fileIn.isDirectory() ? () -> {
         return new FolderPack(fileIn);
      } : () -> {
         return new FilePack(fileIn);
      };
   }
}