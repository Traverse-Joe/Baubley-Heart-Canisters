package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.Blocks;
import net.minecraft.tileentity.EndGatewayTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.StructureManager;

public class EndGatewayFeature extends Feature<EndGatewayConfig> {
   public EndGatewayFeature(Codec<EndGatewayConfig> p_i231951_1_) {
      super(p_i231951_1_);
   }

   public boolean func_230362_a_(ISeedReader p_230362_1_, StructureManager p_230362_2_, ChunkGenerator p_230362_3_, Random p_230362_4_, BlockPos p_230362_5_, EndGatewayConfig p_230362_6_) {
      for(BlockPos blockpos : BlockPos.getAllInBoxMutable(p_230362_5_.add(-1, -2, -1), p_230362_5_.add(1, 2, 1))) {
         boolean flag = blockpos.getX() == p_230362_5_.getX();
         boolean flag1 = blockpos.getY() == p_230362_5_.getY();
         boolean flag2 = blockpos.getZ() == p_230362_5_.getZ();
         boolean flag3 = Math.abs(blockpos.getY() - p_230362_5_.getY()) == 2;
         if (flag && flag1 && flag2) {
            BlockPos blockpos1 = blockpos.toImmutable();
            this.func_230367_a_(p_230362_1_, blockpos1, Blocks.END_GATEWAY.getDefaultState());
            p_230362_6_.func_214700_b().ifPresent((p_236280_3_) -> {
               TileEntity tileentity = p_230362_1_.getTileEntity(blockpos1);
               if (tileentity instanceof EndGatewayTileEntity) {
                  EndGatewayTileEntity endgatewaytileentity = (EndGatewayTileEntity)tileentity;
                  endgatewaytileentity.setExitPortal(p_236280_3_, p_230362_6_.func_214701_c());
                  tileentity.markDirty();
               }

            });
         } else if (flag1) {
            this.func_230367_a_(p_230362_1_, blockpos, Blocks.AIR.getDefaultState());
         } else if (flag3 && flag && flag2) {
            this.func_230367_a_(p_230362_1_, blockpos, Blocks.BEDROCK.getDefaultState());
         } else if ((flag || flag2) && !flag3) {
            this.func_230367_a_(p_230362_1_, blockpos, Blocks.BEDROCK.getDefaultState());
         } else {
            this.func_230367_a_(p_230362_1_, blockpos, Blocks.AIR.getDefaultState());
         }
      }

      return true;
   }
}