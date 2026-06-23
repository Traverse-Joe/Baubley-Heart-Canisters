package com.traverse.bhc.common.blocks;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.traverse.bhc.common.util.VitalicSource;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Map;

public class VitalicBudBlock extends Block {

    public static final EnumProperty<Direction> FACING = BlockStateProperties.FACING;

    public static final MapCodec<VitalicBudBlock> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            VitalicSource.CODEC.fieldOf("source").forGetter(b -> b.source),
            VitalicBudSize.CODEC.fieldOf("size").forGetter(b -> b.size),
            propertiesCodec()
    ).apply(inst, VitalicBudBlock::new));

    private final VitalicSource source;
    private final VitalicBudSize size;
    private final Map<Direction, VoxelShape> shapes;

    public VitalicBudBlock(VitalicSource source, VitalicBudSize size, BlockBehaviour.Properties properties) {
        super(properties);
        this.source = source;
        this.size = size;
        this.shapes = Shapes.rotateAll(Block.boxZ(size.width, 16.0F - size.height, 16.0));
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.UP));
    }

    @Override
    protected MapCodec<? extends VitalicBudBlock> codec() {
        return CODEC;
    }

    public VitalicSource getSource() {
        return source;
    }

    public VitalicBudSize getSize() {
        return size;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return shapes.get(state.getValue(FACING));
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        Direction facing = state.getValue(FACING);
        BlockPos anchorPos = pos.relative(facing.getOpposite());
        BlockState anchor = level.getBlockState(anchorPos);
        return anchor.getBlock() instanceof BuddingCrystalBlock bcb && bcb.getSource() == source;
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess ticks, BlockPos pos, Direction directionToNeighbour, BlockPos neighbourPos, BlockState neighbourState, RandomSource random) {
        return directionToNeighbour == state.getValue(FACING).getOpposite() && !state.canSurvive(level, pos)
                ? Blocks.AIR.defaultBlockState()
                : super.updateShape(state, level, ticks, pos, directionToNeighbour, neighbourPos, neighbourState, random);
    }

    @Override
    protected BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    protected BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }
}
