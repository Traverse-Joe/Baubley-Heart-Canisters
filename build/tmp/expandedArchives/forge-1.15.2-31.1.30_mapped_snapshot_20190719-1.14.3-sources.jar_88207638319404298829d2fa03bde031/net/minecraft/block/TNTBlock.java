package net.minecraft.block;

import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.TNTEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class TNTBlock extends Block {
   public static final BooleanProperty UNSTABLE = BlockStateProperties.UNSTABLE;

   public TNTBlock(Block.Properties properties) {
      super(properties);
      this.setDefaultState(this.getDefaultState().with(UNSTABLE, Boolean.valueOf(false)));
   }

   public void catchFire(BlockState state, World world, BlockPos pos, @Nullable net.minecraft.util.Direction face, @Nullable LivingEntity igniter) {
      explode(world, pos, igniter);
   }

   public void onBlockAdded(BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
      if (oldState.getBlock() != state.getBlock()) {
         if (worldIn.isBlockPowered(pos)) {
            catchFire(state, worldIn, pos, null, null);
            worldIn.removeBlock(pos, false);
         }

      }
   }

   public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
      if (worldIn.isBlockPowered(pos)) {
         catchFire(state, worldIn, pos, null, null);
         worldIn.removeBlock(pos, false);
      }

   }

   /**
    * Called before the Block is set to air in the world. Called regardless of if the player's tool can actually collect
    * this block
    */
   public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
      if (!worldIn.isRemote() && !player.isCreative() && state.get(UNSTABLE)) {
         catchFire(state, worldIn, pos, null, null);
      }

      super.onBlockHarvested(worldIn, pos, state, player);
   }

   /**
    * Called when this Block is destroyed by an Explosion
    */
   public void onExplosionDestroy(World worldIn, BlockPos pos, Explosion explosionIn) {
      if (!worldIn.isRemote) {
         TNTEntity tntentity = new TNTEntity(worldIn, (double)((float)pos.getX() + 0.5F), (double)pos.getY(), (double)((float)pos.getZ() + 0.5F), explosionIn.getExplosivePlacedBy());
         tntentity.setFuse((short)(worldIn.rand.nextInt(tntentity.getFuse() / 4) + tntentity.getFuse() / 8));
         worldIn.addEntity(tntentity);
      }
   }

   @Deprecated //Forge: Prefer using IForgeBlock#catchFire
   public static void explode(World p_196534_0_, BlockPos worldIn) {
      explode(p_196534_0_, worldIn, (LivingEntity)null);
   }

   @Deprecated //Forge: Prefer using IForgeBlock#catchFire
   private static void explode(World p_196535_0_, BlockPos p_196535_1_, @Nullable LivingEntity p_196535_2_) {
      if (!p_196535_0_.isRemote) {
         TNTEntity tntentity = new TNTEntity(p_196535_0_, (double)p_196535_1_.getX() + 0.5D, (double)p_196535_1_.getY(), (double)p_196535_1_.getZ() + 0.5D, p_196535_2_);
         p_196535_0_.addEntity(tntentity);
         p_196535_0_.playSound((PlayerEntity)null, tntentity.func_226277_ct_(), tntentity.func_226278_cu_(), tntentity.func_226281_cx_(), SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.BLOCKS, 1.0F, 1.0F);
      }
   }

   public ActionResultType func_225533_a_(BlockState p_225533_1_, World p_225533_2_, BlockPos p_225533_3_, PlayerEntity p_225533_4_, Hand p_225533_5_, BlockRayTraceResult p_225533_6_) {
      ItemStack itemstack = p_225533_4_.getHeldItem(p_225533_5_);
      Item item = itemstack.getItem();
      if (item != Items.FLINT_AND_STEEL && item != Items.FIRE_CHARGE) {
         return super.func_225533_a_(p_225533_1_, p_225533_2_, p_225533_3_, p_225533_4_, p_225533_5_, p_225533_6_);
      } else {
         catchFire(p_225533_1_, p_225533_2_, p_225533_3_, p_225533_6_.getFace(), p_225533_4_);
         p_225533_2_.setBlockState(p_225533_3_, Blocks.AIR.getDefaultState(), 11);
         if (!p_225533_4_.isCreative()) {
            if (item == Items.FLINT_AND_STEEL) {
               itemstack.damageItem(1, p_225533_4_, (p_220287_1_) -> {
                  p_220287_1_.sendBreakAnimation(p_225533_5_);
               });
            } else {
               itemstack.shrink(1);
            }
         }

         return ActionResultType.SUCCESS;
      }
   }

   public void onProjectileCollision(World worldIn, BlockState state, BlockRayTraceResult hit, Entity projectile) {
      if (!worldIn.isRemote && projectile instanceof AbstractArrowEntity) {
         AbstractArrowEntity abstractarrowentity = (AbstractArrowEntity)projectile;
         Entity entity = abstractarrowentity.getShooter();
         if (abstractarrowentity.isBurning()) {
            BlockPos blockpos = hit.getPos();
            catchFire(state, worldIn, blockpos, null, entity instanceof LivingEntity ? (LivingEntity)entity : null);
            worldIn.removeBlock(blockpos, false);
         }
      }

   }

   /**
    * Return whether this block can drop from an explosion.
    */
   public boolean canDropFromExplosion(Explosion explosionIn) {
      return false;
   }

   protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
      builder.add(UNSTABLE);
   }
}