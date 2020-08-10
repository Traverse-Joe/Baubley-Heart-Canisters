package net.minecraft.block;

import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public abstract class AbstractFireBlock extends Block {
   private final float field_235325_g_;
   protected static final VoxelShape field_235319_a_ = Block.makeCuboidShape(0.0D, 15.0D, 0.0D, 16.0D, 16.0D, 16.0D);
   protected static final VoxelShape field_235320_b_ = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D);
   protected static final VoxelShape field_235321_c_ = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 1.0D, 16.0D, 16.0D);
   protected static final VoxelShape field_235322_d_ = Block.makeCuboidShape(15.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
   protected static final VoxelShape field_235323_e_ = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 1.0D);
   protected static final VoxelShape field_235324_f_ = Block.makeCuboidShape(0.0D, 0.0D, 15.0D, 16.0D, 16.0D, 16.0D);

   public AbstractFireBlock(AbstractBlock.Properties p_i241173_1_, float p_i241173_2_) {
      super(p_i241173_1_);
      this.field_235325_g_ = p_i241173_2_;
   }

   public BlockState getStateForPlacement(BlockItemUseContext context) {
      return func_235326_a_(context.getWorld(), context.getPos());
   }

   public static BlockState func_235326_a_(IBlockReader p_235326_0_, BlockPos p_235326_1_) {
      BlockPos blockpos = p_235326_1_.down();
      BlockState blockstate = p_235326_0_.getBlockState(blockpos);
      return SoulFireBlock.func_235577_c_(blockstate.getBlock()) ? Blocks.field_235335_bO_.getDefaultState() : ((FireBlock)Blocks.FIRE).getStateForPlacement(p_235326_0_, p_235326_1_);
   }

   public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
      return field_235320_b_;
   }

   /**
    * Called periodically clientside on blocks near the player to show effects (like furnace fire particles). Note that
    * this method is unrelated to {@link randomTick} and {@link #needsRandomTick}, and will always be called regardless
    * of whether the block can receive random update ticks
    */
   @OnlyIn(Dist.CLIENT)
   public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
      if (rand.nextInt(24) == 0) {
         worldIn.playSound((double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, SoundEvents.BLOCK_FIRE_AMBIENT, SoundCategory.BLOCKS, 1.0F + rand.nextFloat(), rand.nextFloat() * 0.7F + 0.3F, false);
      }

      BlockPos blockpos = pos.down();
      BlockState blockstate = worldIn.getBlockState(blockpos);
      if (!this.canBurn(blockstate) && !blockstate.isSolidSide(worldIn, blockpos, Direction.UP)) {
         if (this.canBurn(worldIn.getBlockState(pos.west()))) {
            for(int j = 0; j < 2; ++j) {
               double d3 = (double)pos.getX() + rand.nextDouble() * (double)0.1F;
               double d8 = (double)pos.getY() + rand.nextDouble();
               double d13 = (double)pos.getZ() + rand.nextDouble();
               worldIn.addParticle(ParticleTypes.LARGE_SMOKE, d3, d8, d13, 0.0D, 0.0D, 0.0D);
            }
         }

         if (this.canBurn(worldIn.getBlockState(pos.east()))) {
            for(int k = 0; k < 2; ++k) {
               double d4 = (double)(pos.getX() + 1) - rand.nextDouble() * (double)0.1F;
               double d9 = (double)pos.getY() + rand.nextDouble();
               double d14 = (double)pos.getZ() + rand.nextDouble();
               worldIn.addParticle(ParticleTypes.LARGE_SMOKE, d4, d9, d14, 0.0D, 0.0D, 0.0D);
            }
         }

         if (this.canBurn(worldIn.getBlockState(pos.north()))) {
            for(int l = 0; l < 2; ++l) {
               double d5 = (double)pos.getX() + rand.nextDouble();
               double d10 = (double)pos.getY() + rand.nextDouble();
               double d15 = (double)pos.getZ() + rand.nextDouble() * (double)0.1F;
               worldIn.addParticle(ParticleTypes.LARGE_SMOKE, d5, d10, d15, 0.0D, 0.0D, 0.0D);
            }
         }

         if (this.canBurn(worldIn.getBlockState(pos.south()))) {
            for(int i1 = 0; i1 < 2; ++i1) {
               double d6 = (double)pos.getX() + rand.nextDouble();
               double d11 = (double)pos.getY() + rand.nextDouble();
               double d16 = (double)(pos.getZ() + 1) - rand.nextDouble() * (double)0.1F;
               worldIn.addParticle(ParticleTypes.LARGE_SMOKE, d6, d11, d16, 0.0D, 0.0D, 0.0D);
            }
         }

         if (this.canBurn(worldIn.getBlockState(pos.up()))) {
            for(int j1 = 0; j1 < 2; ++j1) {
               double d7 = (double)pos.getX() + rand.nextDouble();
               double d12 = (double)(pos.getY() + 1) - rand.nextDouble() * (double)0.1F;
               double d17 = (double)pos.getZ() + rand.nextDouble();
               worldIn.addParticle(ParticleTypes.LARGE_SMOKE, d7, d12, d17, 0.0D, 0.0D, 0.0D);
            }
         }
      } else {
         for(int i = 0; i < 3; ++i) {
            double d0 = (double)pos.getX() + rand.nextDouble();
            double d1 = (double)pos.getY() + rand.nextDouble() * 0.5D + 0.5D;
            double d2 = (double)pos.getZ() + rand.nextDouble();
            worldIn.addParticle(ParticleTypes.LARGE_SMOKE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
         }
      }

   }

   protected abstract boolean canBurn(BlockState p_196446_1_);

   public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
      if (!entityIn.func_230279_az_()) {
         entityIn.func_241209_g_(entityIn.getFireTimer() + 1);
         if (entityIn.getFireTimer() == 0) {
            entityIn.setFire(8);
         }

         entityIn.attackEntityFrom(DamageSource.IN_FIRE, this.field_235325_g_);
      }

      super.onEntityCollision(state, worldIn, pos, entityIn);
   }

   public void onBlockAdded(BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
      if (!oldState.isIn(state.getBlock())) {
         if (worldIn.func_234923_W_() != World.field_234918_g_ && worldIn.func_234923_W_() != World.field_234919_h_ || !NetherPortalBlock.trySpawnPortal(worldIn, pos)) {
            if (!state.isValidPosition(worldIn, pos)) {
               worldIn.removeBlock(pos, false);
            }

         }
      }
   }

   /**
    * Called before the Block is set to air in the world. Called regardless of if the player's tool can actually collect
    * this block
    */
   public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
      if (!worldIn.isRemote()) {
         worldIn.playEvent((PlayerEntity)null, 1009, pos, 0);
      }

   }

   public static boolean func_241465_a_(IWorld p_241465_0_, BlockPos p_241465_1_) {
      BlockState blockstate = p_241465_0_.getBlockState(p_241465_1_);
      BlockState blockstate1 = func_235326_a_(p_241465_0_, p_241465_1_);
      return blockstate.isAir() && (blockstate1.isValidPosition(p_241465_0_, p_241465_1_) || func_241466_b_(p_241465_0_, p_241465_1_));
   }

   private static boolean func_241466_b_(IWorld p_241466_0_, BlockPos p_241466_1_) {
      for(Direction direction : Direction.Plane.HORIZONTAL) {
         if (p_241466_0_.getBlockState(p_241466_1_.offset(direction)).isIn(Blocks.OBSIDIAN) && NetherPortalBlock.isPortal(p_241466_0_, p_241466_1_) != null) {
            return true;
         }
      }

      return false;
   }
}