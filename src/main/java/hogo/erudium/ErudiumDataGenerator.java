package hogo.erudium;

import hogo.erudium.world.ModDimension;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;

public class ErudiumDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {


	}
	@Override
	public void buildRegistry(RegistryBuilder builder){
		builder.addRegistry(RegistryKeys.DIMENSION_TYPE, ModDimension::bootstrapType);
	}
}
