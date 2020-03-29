package net.minecraft.block;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.SwordItem;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BambooLeaves;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class BambooBlock extends Block implements IGrowable {
   protected static final VoxelShape SHAPE_NORMAL = Block.makeCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, 16.0D, 11.0D);
   protected static final VoxelShape SHAPE_LARGE_LEAVES = Block.makeCuboidShape(3.0D, 0.0D, 3.0D, 13.0D, 16.0D, 13.0D);
   protected static final VoxelShape SHAPE_COLLISION = Block.makeCuboidShape(6.5D, 0.0D, 6.5D, 9.5D, 16.0D, 9.5D);
   public static final IntegerProperty PROPERTY_AGE = BlockStateProperties.AGE_0_1;
   public static final EnumProperty<BambooLeaves> PROPERTY_BAMBOO_LEAVES = BlockStateProperties.BAMBOO_LEAVES;
   public static final IntegerProperty PROPERTY_STAGE = BlockStateProperties.STAGE_0_1;

   public BambooBlock(Block.Properties p_i49998_1_) {
      super(p_i49998_1_);
      this.setDefaultState(this.stateContainer.getBaseState().with(PROPERTY_AGE, Integer.valueOf(0)).with(PROPERTY_BAMBOO_LEAVES, BambooLeaves.NONE).with(PROPERTY_STAGE, Integer.valueOf(0)));
   }

   protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
      builder.add(PROPERTY_AGE, PROPERTY_BAMBOO_LEAVES, PROPERTY_STAGE);
   }

   /**
    * Get the OffsetType for this Block. Determines if the model is rendered slightly offset.
    */
   public Block.OffsetType getOffsetType() {
      return Block.OffsetType.XZ;
   }

   public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
      return true;
   }

   public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
      VoxelShape voxelshape = state.get(PROPERTY_BAMBOO_LEAVES) == BambooLeaves.LARGE ? SHAPE_LARGE_LEAVES : SHAPE_NORMAL;
      Vec3d vec3d = state.getOffset(worldIn, pos);
      return voxelshape.withOffset(vec3d.x, vec3d.y, vec3d.z);
   }

   public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
      return false;
   }

   public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
      Vec3d vec3d = state.getOffset(worldIn, pos);
      return SHAPE_COLLISION.withOffset(vec3d.x, vec3d.y, vec3d.z);
   }

   @Nullable
   public BlockState getStateForPlacement(BlockItemUseContext context) {
      IFluidState ifluidstate = context.getWorld().getFluidState(context.getPos());
      if (!ifluidstate.isEmpty()) {
         return null;
      } else {
         BlockState blockstate = context.getWorld().getBlockState(context.getPos().down());
         if (blockstate.isIn(BlockTags.BAMBOO_PLANTABLE_ON)) {
            Block block = blockstate.getBlock();
            if (block == Blocks.BAMBOO_SAPLING) {
               return this.getDefaultState().with(PROPERTY_AGE, Integer.valueOf(0));
            } else if (block == Blocks.BAMBOO) {
               int i = blockstate.get(PROPERTY_AGE) > 0 ? 1 : 0;
               return this.getDefaultState().with(PROPERTY_AGE, Integer.valueOf(i));
            } else {
               return Blocks.BAMBOO_SAPLING.getDefaultState();
            }
         } else {
            return null;
         }
      }
   }

   public void func_225534_a_(BlockState p_225534_1_, ServerWorld p_225534_2_, BlockPos p_225534_3_, Random p_225534_4_) {
      if (!p_225534_1_.isValidPosition(p_225534_2_, p_225534_3_)) {
         p_225534_2_.destroyBlock(p_225534_3_, true);
      } else if (p_225534_1_.get(PROPERTY_STAGE) == 0) {
         if (p_225534_4_.nextInt(3) == 0 && p_225534_2_.isAirBlock(p_225534_3_.up()) && p_225534_2_.func_226659_b_(p_225534_3_.up(), 0) >= 9) {
            int i = this.func_220260_b(p_225534_2_, p_225534_3_) + 1;
            if (i < 16) {
               this.func_220258_a(p_225534_1_, p_225534_2_, p_225534_3_, p_225534_4_, i);
            }
         }

      }
   }

   public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
      return worldIn.getBlockState(pos.down()).isIn(BlockTags.BAMBOO_PLANTABLE_ON);
   }

   /**
    * Update the provided state given the provided neighbor facing and neighbor state, returning a new state.
    * For example, fences make their connections to the passed in state if possible, and wet concrete powder immediately
    * returns its solidified counterpart.
    * Note that this method should ideally consider only the specific face passed in.
    */
   public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
      if (!stateIn.isValidPosition(worldIn, currentPos)) {
         worldIn.getPendingBlockTicks().scheduleTick(currentPos, this, 1);
      }

      if (facing == Direction.UP && facingState.getBlock() == Blocks.BAMBOO && facingState.get(PROPERTY_AGE) > stateIn.get(PROPERTY_AGE)) {
         worldIn.setBlockState(currentPos, stateIn.cycle(PROPERTY_AGE), 2);
      }

      return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
   }

   /**
    * Whether this IGrowable can grow
    */
   public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
      int i = this.func_220259_a(worldIn, pos);
      int j = this.func_220260_b(worldIn, pos);
      return i + j + 1 < 16 && worldIn.getBlockState(pos.up(i)).get(PROPERTY_STAGE) != 1;
   }

   public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
      return true;
   }

   public void func_225535_a_(ServerWorld p_225535_1_, Random p_225535_2_, BlockPos p_225535_3_, BlockState p_225535_4_) {
      int i = this.func_220259_a(p_225535_1_, p_225535_3_);
      int j = this.func_220260_b(p_225535_1_, p_225535_3_);
      int k = i + j + 1;
      int l = 1 + p_225535_2_.nextInt(2);

      for(int i1 = 0; i1 < l; ++i1) {
         BlockPos blockpos = p_225535_3_.up(i);
         BlockState blockstate = p_225535_1_.getBlockState(blockpos);
         if (k >= 16 || blockstate.get(PROPERTY_STAGE) == 1 || !p_225535_1_.isAirBlock(blockpos.up())) {
            return;
         }

         this.func_220258_a(blockstate, p_225535_1_, blockpos, p_225535_2_, k);
         ++i;
         ++k;
      }

   }

   /**
    * Get the hardness of this Block relative to the ability of the given player
    * @deprecated call via {@link IBlockState#getPlayerRelativeBlockHardness(EntityPlayer,World,BlockPos)} whenever
    * possible. Implementing/overriding is fine.
    */
   public float getPlayerRelativeBlockHardness(BlockState state, PlayerEntity player, IBlockReader worldIn, BlockPos pos) {
      return player.getHeldItemMainhand().getItem() instanceof SwordItem ? 1.0F : super.getPlayerRelativeBlockHardness(state, player, worldIn, pos);
   }

   protected void func_220258_a(BlockState p_220258_1_, World p_220258_2_, BlockPos p_220258_3_, Random p_220258_4_, int p_220258_5_) {
      BlockState blockstate = p_220258_2_.getBlockState(p_220258_3_.down());
      BlockPos blockpos = p_220258_3_.down(2);
      BlockState blockstate1 = p_220258_2_.getBlockState(blockpos);
      BambooLeaves bambooleaves = BambooLeaves.NONE;
      if (p_220258_5_ >= 1) {
         if (blockstate.getBlock() == Blocks.BAMBOO && blockstate.get(PROPERTY_BAMBOO_LEAVES) != BambooLeaves.NONE) {
            if (blockstate.getBlock() == Blocks.BAMBOO && blockstate.get(PROPERTY_BAMBOO_LEAVES) != BambooLeaves.NONE) {
               bambooleaves = BambooLeaves.LARGE;
               if (blockstate1.getBlock() == Blocks.BAMBOO) {
                  p_220258_2_.setBlockState(p_220258_3_.down(), blockstate.with(PROPERTY_BAMBOO_LEAVES, BambooLeaves.SMALL), 3);
                  p_220258_2_.setBlockState(blockpos, blockstate1.with(PROPERTY_BAMBOO_LEAVES, BambooLeaves.NONE), 3);
               }
            }
         } else {
            bambooleaves = BambooLeaves.SMALL;
         }
      }

      int i = p_220258_1_.get(PROPERTY_AGE) != 1 && blockstate1.getBlock() != Blocks.BAMBOO ? 0 : 1;
      int j = (p_220258_5_ < 11 || !(p_220258_4_.nextFloat() < 0.25F)) && p_220258_5_ != 15 ? 0 : 1;
      p_220258_2_.setBlockState(p_220258_3_.up(), this.getDefaultState().with(PROPERTY_AGE, Integer.valueOf(i)).with(PROPERTY_BAMBOO_LEAVES, bambooleaves).with(PROPERTY_STAGE, Integer.valueOf(j)), 3);
   }

   protected int func_220259_a(IBlockReader p_220259_1_, BlockPos p_220259_2_) {
      int i;
      for(i = 0; i < 16 && p_220259_1_.getBlockState(p_220259_2_.up(i + 1)).getBlock() == Blocks.BAMBOO; ++i) {
         ;
      }

      return i;
   }

   protected int func_220260_b(IBlockReader p_220260_1_, BlockPos p_220260_2_) {
      int i;
      for(i = 0; i < 16 && p_220260_1_.getBlockState(p_220260_2_.down(i + 1)).getBlock() == Blocks.BAMBOO; ++i) {
         ;
      }

      return i;
   }
}