package net.Spasmanizer.MCRemake.mixin;


import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ConnectingBlock;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Map;
@Environment(EnvType.CLIENT)
@Mixin(targets = "net.minecraft.block.StainedGlassBlock")
public class StainedGlassBlock extends Block {
    private static final BooleanProperty NORTH;
    private static final BooleanProperty EAST;
    private static final BooleanProperty SOUTH;
    private static final BooleanProperty WEST;
    private static final BooleanProperty UP;
    private static final BooleanProperty DOWN;
    private static final Map<Direction, BooleanProperty> FACING_PROPERTIES;

    public StainedGlassBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(NORTH, true).with(EAST, true).with(SOUTH, true).with(WEST, true).with(UP, true).with(DOWN, true));
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockView blockView = ctx.getWorld();
        BlockPos blockPos = ctx.getBlockPos();
        return this.getDefaultState().with(DOWN, !blockView.getBlockState(blockPos.down()).isOf(this)).with(UP, !blockView.getBlockState(blockPos.up()).isOf(this)).with(NORTH, !blockView.getBlockState(blockPos.north()).isOf(this)).with(EAST, !blockView.getBlockState(blockPos.east()).isOf(this)).with(SOUTH, !blockView.getBlockState(blockPos.south()).isOf(this)).with(WEST, !blockView.getBlockState(blockPos.west()).isOf(this));
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        return neighborState.isOf(this) ? state.with(FACING_PROPERTIES.get(direction), false) : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING_PROPERTIES.get(rotation.rotate(Direction.NORTH)), state.get(NORTH)).with(FACING_PROPERTIES.get(rotation.rotate(Direction.SOUTH)), state.get(SOUTH)).with(FACING_PROPERTIES.get(rotation.rotate(Direction.EAST)), state.get(EAST)).with(FACING_PROPERTIES.get(rotation.rotate(Direction.WEST)), state.get(WEST)).with(FACING_PROPERTIES.get(rotation.rotate(Direction.UP)), state.get(UP)).with(FACING_PROPERTIES.get(rotation.rotate(Direction.DOWN)), state.get(DOWN));
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(UP, DOWN, NORTH, EAST, SOUTH, WEST);
    }

    static {
        NORTH = ConnectingBlock.NORTH;
        EAST = ConnectingBlock.EAST;
        SOUTH = ConnectingBlock.SOUTH;
        WEST = ConnectingBlock.WEST;
        UP = ConnectingBlock.UP;
        DOWN = ConnectingBlock.DOWN;
        FACING_PROPERTIES = ConnectingBlock.FACING_PROPERTIES;
    }
}