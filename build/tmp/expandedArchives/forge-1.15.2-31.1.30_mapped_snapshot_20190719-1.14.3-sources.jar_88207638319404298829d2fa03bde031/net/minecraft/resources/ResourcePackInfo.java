package net.minecraft.resources;

import com.mojang.brigadier.arguments.StringArgumentType;
import java.io.IOException;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.resources.data.PackMetadataSection;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponentUtils;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ResourcePackInfo implements AutoCloseable {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final PackMetadataSection field_212500_b = new PackMetadataSection((new TranslationTextComponent("resourcePack.broken_assets")).applyTextStyles(new TextFormatting[]{TextFormatting.RED, TextFormatting.ITALIC}), SharedConstants.getVersion().getPackVersion());
   private final String name;
   private final Supplier<IResourcePack> resourcePackSupplier;
   private final ITextComponent field_195802_d;
   private final ITextComponent description;
   private final PackCompatibility compatibility;
   private final ResourcePackInfo.Priority priority;
   private final boolean alwaysEnabled;
   private final boolean orderLocked;
   private final boolean hidden; // Forge: Allow packs to be hidden from the UI entirely

   @Nullable
   public static <T extends ResourcePackInfo> T createResourcePack(String nameIn, boolean p_195793_1_, Supplier<IResourcePack> p_195793_2_, ResourcePackInfo.IFactory<T> factory, ResourcePackInfo.Priority p_195793_4_) {
      try (IResourcePack iresourcepack = p_195793_2_.get()) {
         PackMetadataSection packmetadatasection = iresourcepack.getMetadata(PackMetadataSection.SERIALIZER);
         if (p_195793_1_ && packmetadatasection == null) {
            LOGGER.error("Broken/missing pack.mcmeta detected, fudging it into existance. Please check that your launcher has downloaded all assets for the game correctly!");
            packmetadatasection = field_212500_b;
         }

         if (packmetadatasection != null) {
            ResourcePackInfo resourcepackinfo = factory.create(nameIn, p_195793_1_, p_195793_2_, iresourcepack, packmetadatasection, p_195793_4_);
            return (T)resourcepackinfo;
         }

         LOGGER.warn("Couldn't find pack meta for pack {}", (Object)nameIn);
      } catch (IOException ioexception) {
         LOGGER.warn("Couldn't get pack info for: {}", (Object)ioexception.toString());
      }

      return (T)null;
   }

   @Deprecated
   public ResourcePackInfo(String nameIn, boolean p_i47907_2_, Supplier<IResourcePack> resourcePackSupplierIn, ITextComponent p_i47907_4_, ITextComponent p_i47907_5_, PackCompatibility p_i47907_6_, ResourcePackInfo.Priority p_i47907_7_, boolean p_i47907_8_) {
      this(nameIn, p_i47907_2_, resourcePackSupplierIn, p_i47907_4_, p_i47907_5_, p_i47907_6_, p_i47907_7_, p_i47907_8_, false);
   }

   public ResourcePackInfo(String nameIn, boolean p_i47907_2_, Supplier<IResourcePack> resourcePackSupplierIn, ITextComponent p_i47907_4_, ITextComponent p_i47907_5_, PackCompatibility p_i47907_6_, ResourcePackInfo.Priority p_i47907_7_, boolean p_i47907_8_, boolean hidden) {
      this.name = nameIn;
      this.resourcePackSupplier = resourcePackSupplierIn;
      this.field_195802_d = p_i47907_4_;
      this.description = p_i47907_5_;
      this.compatibility = p_i47907_6_;
      this.alwaysEnabled = p_i47907_2_;
      this.priority = p_i47907_7_;
      this.orderLocked = p_i47907_8_;
      this.hidden = hidden;
   }

   @Deprecated
   public ResourcePackInfo(String p_i47908_1_, boolean p_i47908_2_, Supplier<IResourcePack> p_i47908_3_, IResourcePack p_i47908_4_, PackMetadataSection p_i47908_5_, ResourcePackInfo.Priority p_i47908_6_) {
      this(p_i47908_1_, p_i47908_2_, p_i47908_3_, p_i47908_4_, p_i47908_5_, p_i47908_6_, false);
   }

   public ResourcePackInfo(String p_i47908_1_, boolean p_i47908_2_, Supplier<IResourcePack> p_i47908_3_, IResourcePack p_i47908_4_, PackMetadataSection p_i47908_5_, ResourcePackInfo.Priority p_i47908_6_, boolean hidden) {
      this(p_i47908_1_, p_i47908_2_, p_i47908_3_, new StringTextComponent(p_i47908_4_.getName()), p_i47908_5_.getDescription(), PackCompatibility.func_198969_a(p_i47908_5_.getPackFormat()), p_i47908_6_, hidden, hidden);
   }

   @OnlyIn(Dist.CLIENT)
   public ITextComponent func_195789_b() {
      return this.field_195802_d;
   }

   @OnlyIn(Dist.CLIENT)
   public ITextComponent getDescription() {
      return this.description;
   }

   public ITextComponent func_195794_a(boolean p_195794_1_) {
      return TextComponentUtils.wrapInSquareBrackets(new StringTextComponent(this.name)).applyTextStyle((p_211689_2_) -> {
         p_211689_2_.setColor(p_195794_1_ ? TextFormatting.GREEN : TextFormatting.RED).setInsertion(StringArgumentType.escapeIfRequired(this.name)).setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (new StringTextComponent("")).appendSibling(this.field_195802_d).appendText("\n").appendSibling(this.description)));
      });
   }

   public PackCompatibility getCompatibility() {
      return this.compatibility;
   }

   public IResourcePack getResourcePack() {
      return this.resourcePackSupplier.get();
   }

   public String getName() {
      return this.name;
   }

   public boolean isAlwaysEnabled() {
      return this.alwaysEnabled;
   }

   public boolean isOrderLocked() {
      return this.orderLocked;
   }

   public ResourcePackInfo.Priority getPriority() {
      return this.priority;
   }
   
   public boolean isHidden() { return hidden; }

   public boolean equals(Object p_equals_1_) {
      if (this == p_equals_1_) {
         return true;
      } else if (!(p_equals_1_ instanceof ResourcePackInfo)) {
         return false;
      } else {
         ResourcePackInfo resourcepackinfo = (ResourcePackInfo)p_equals_1_;
         return this.name.equals(resourcepackinfo.name);
      }
   }

   public int hashCode() {
      return this.name.hashCode();
   }

   public void close() {
   }

   @FunctionalInterface
   public interface IFactory<T extends ResourcePackInfo> {
      @Nullable
      T create(String p_create_1_, boolean p_create_2_, Supplier<IResourcePack> p_create_3_, IResourcePack p_create_4_, PackMetadataSection p_create_5_, ResourcePackInfo.Priority p_create_6_);
   }

   public static enum Priority {
      TOP,
      BOTTOM;

      public <T, P extends ResourcePackInfo> int func_198993_a(List<T> p_198993_1_, T p_198993_2_, Function<T, P> p_198993_3_, boolean p_198993_4_) {
         ResourcePackInfo.Priority resourcepackinfo$priority = p_198993_4_ ? this.func_198992_a() : this;
         if (resourcepackinfo$priority == BOTTOM) {
            int j;
            for(j = 0; j < p_198993_1_.size(); ++j) {
               P p1 = p_198993_3_.apply(p_198993_1_.get(j));
               if (!p1.isOrderLocked() || p1.getPriority() != this) {
                  break;
               }
            }

            p_198993_1_.add(j, p_198993_2_);
            return j;
         } else {
            int i;
            for(i = p_198993_1_.size() - 1; i >= 0; --i) {
               P p = p_198993_3_.apply(p_198993_1_.get(i));
               if (!p.isOrderLocked() || p.getPriority() != this) {
                  break;
               }
            }

            p_198993_1_.add(i + 1, p_198993_2_);
            return i + 1;
         }
      }

      public ResourcePackInfo.Priority func_198992_a() {
         return this == TOP ? BOTTOM : TOP;
      }
   }
}