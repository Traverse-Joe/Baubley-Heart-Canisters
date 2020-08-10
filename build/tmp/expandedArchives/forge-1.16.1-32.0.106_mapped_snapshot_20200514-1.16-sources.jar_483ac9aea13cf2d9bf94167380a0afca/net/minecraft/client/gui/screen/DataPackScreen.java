package net.minecraft.client.gui.screen;

import java.io.File;
import java.util.function.Consumer;
import net.minecraft.resources.ResourcePackInfo;
import net.minecraft.resources.ResourcePackList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DataPackScreen extends PackScreen {
   private static final ResourceLocation field_238606_a_ = new ResourceLocation("textures/misc/unknown_pack.png");

   public DataPackScreen(Screen p_i241249_1_, ResourcePackList<ResourcePackInfo> p_i241249_2_, Consumer<ResourcePackList<ResourcePackInfo>> p_i241249_3_, File p_i241249_4_) {
      super(p_i241249_1_, new TranslationTextComponent("dataPack.title"), (p_241583_2_) -> {
         return new PackLoadingManager<>(p_241583_2_, (p_241582_0_, p_241582_1_) -> {
            p_241582_1_.bindTexture(field_238606_a_);
         }, p_i241249_2_, p_i241249_3_);
      }, p_i241249_4_);
   }
}