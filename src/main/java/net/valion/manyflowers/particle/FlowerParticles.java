package net.valion.manyflowers.particle;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static net.valion.manyflowers.ManyFlowers.MOD_ID;

public class FlowerParticles {
    public static final SimpleParticleType SWEET_ALYSSUM_PARTICLES = FabricParticleTypes.simple();

    public static void registerParticles() {
        Registry.register(Registries.PARTICLE_TYPE, Identifier.of(MOD_ID, "sweet_alyssum_particles"),
                SWEET_ALYSSUM_PARTICLES);
    }
}
