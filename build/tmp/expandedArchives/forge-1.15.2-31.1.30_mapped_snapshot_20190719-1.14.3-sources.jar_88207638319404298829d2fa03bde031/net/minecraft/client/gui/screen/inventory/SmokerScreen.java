package net.minecraft.client.gui.screen.inventory;

import net.minecraft.client.gui.recipebook.SmokerRecipeGui;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.SmokerContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SmokerScreen extends AbstractFurnaceScreen<SmokerContainer> {
   private static final ResourceLocation field_214094_l = new ResourceLocation("textures/gui/container/smoker.png");

   public SmokerScreen(SmokerContainer p_i51077_1_, PlayerInventory p_i51077_2_, ITextComponent p_i51077_3_) {
      super(p_i51077_1_, new SmokerRecipeGui(), p_i51077_2_, p_i51077_3_, field_214094_l);
   }
}