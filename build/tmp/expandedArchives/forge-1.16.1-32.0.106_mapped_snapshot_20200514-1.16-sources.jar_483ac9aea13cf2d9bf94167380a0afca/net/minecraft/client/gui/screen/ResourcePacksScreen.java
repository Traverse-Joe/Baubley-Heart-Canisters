package net.minecraft.client.gui.screen;

import java.io.File;
import java.util.function.Consumer;
import net.minecraft.client.resources.ClientResourcePackInfo;
import net.minecraft.resources.ResourcePackList;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ResourcePacksScreen extends PackScreen {
   public ResourcePacksScreen(Screen p_i241252_1_, ResourcePackList<ClientResourcePackInfo> p_i241252_2_, Consumer<ResourcePackList<ClientResourcePackInfo>> p_i241252_3_, File p_i241252_4_) {
      super(p_i241252_1_, new TranslationTextComponent("resourcePack.title"), (p_241597_2_) -> {
         return new PackLoadingManager<>(p_241597_2_, ClientResourcePackInfo::func_195808_a, p_i241252_2_, p_i241252_3_);
      }, p_i241252_4_);
   }
}