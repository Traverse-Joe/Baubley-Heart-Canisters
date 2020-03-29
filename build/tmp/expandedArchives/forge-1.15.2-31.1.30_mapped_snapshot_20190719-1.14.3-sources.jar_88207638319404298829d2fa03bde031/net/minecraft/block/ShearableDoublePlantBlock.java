package net.minecraft.block;

import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.properties.DoubleBlockHalf;

public class ShearableDoublePlantBlock extends DoublePlantBlock implements net.minecraftforge.common.IShearable {
   public static final EnumProperty<DoubleBlockHalf> field_208063_b = DoublePlantBlock.HALF;

   public ShearableDoublePlantBlock(Block.Properties p_i49975_1_) {
      super(p_i49975_1_);
   }

   public boolean isReplaceable(BlockState state, BlockItemUseContext useContext) {
      boolean flag = super.isReplaceable(state, useContext);
      return flag && useContext.getItem().getItem() == this.asItem() ? false : flag;
   }
}