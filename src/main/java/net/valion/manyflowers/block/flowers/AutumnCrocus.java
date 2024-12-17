package net.valion.manyflowers.block.flowers;

import eu.pb4.factorytools.api.block.FactoryBlock;
import eu.pb4.factorytools.api.item.ModeledItem;
import eu.pb4.factorytools.api.virtualentity.BlockModel;
import eu.pb4.factorytools.api.virtualentity.ItemDisplayElementUtil;
import eu.pb4.polymer.virtualentity.api.ElementHolder;
import eu.pb4.polymer.virtualentity.api.elements.ItemDisplayElement;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowerBlock;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.valion.manyflowers.ManyFlowers;
import net.valion.manyflowers.block.ManyFlowersBiomeTexturedBlockInterface;
import net.valion.manyflowers.helpers.SoundsHelper;
import org.jetbrains.annotations.Nullable;

public class AutumnCrocus extends FlowerBlock implements ManyFlowersBiomeTexturedBlockInterface, FactoryBlock {
    public static final int delay = 700;
    public static int counter = 0;
    private static Item item;

    public AutumnCrocus() {
        super(StatusEffects.ABSORPTION, 0, FabricBlockSettings.copy(Blocks.DANDELION));

        item = new ModeledItem(Items.STONE, new Item.Settings());
        Registry.register(Registries.ITEM, Identifier.of(ManyFlowers.MOD_ID, "block/autumn_crocus"), item);
    }

    @Override
    public @Nullable ElementHolder createElementHolder(ServerWorld world, BlockPos pos, BlockState initialBlockState) {
        return new FlowerModel();
    }

    public static class FlowerModel extends BlockModel {
        private FlowerModel(){
            ItemDisplayElement itemDisplayElement = ItemDisplayElementUtil.createSimple(item);
            this.addElement(itemDisplayElement);
        }
    }

    @Override
    public boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isIn(BlockTags.BASE_STONE_OVERWORLD);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (counter < 0) counter = 0;
        if (!ManyFlowers.CONFIG.sound_crocus) return;
        if (counter == delay) {
            if (!SoundsHelper.sounds.isEmpty()) {
                world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundsHelper.getRandSound(), SoundCategory.HOSTILE, 2F, 0F, true);
            } else {
                SoundsHelper.putSounds();
            }
            counter = 0;
        } else counter++;
    }
}
