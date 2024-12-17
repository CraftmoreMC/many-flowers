package net.valion.manyflowers.block;

import eu.pb4.polymer.blocks.api.BlockModelType;
import eu.pb4.polymer.blocks.api.PolymerBlockResourceUtils;
import eu.pb4.polymer.blocks.api.PolymerTexturedBlock;
import net.minecraft.block.BlockState;

public interface ManyFlowersTexturedBlockInterface extends PolymerTexturedBlock {
    @Override
    public default BlockState getPolymerBlockState(BlockState state) {
        return PolymerBlockResourceUtils.requestEmpty(BlockModelType.PLANT_BLOCK);
    }
}
