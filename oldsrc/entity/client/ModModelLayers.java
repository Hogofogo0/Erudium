package hogo.wynn.entity.client;

import com.ibm.icu.text.IDNA;
import hogo.wynn.WynnMod;
import net.fabricmc.fabric.mixin.client.rendering.EntityModelLayersAccessor;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

public class ModModelLayers {
    public static final EntityModelLayer VOJTA =
            new EntityModelLayer(new Identifier(WynnMod.MOD_ID, "vojta"), "main");
    public static final EntityModelLayer HONZA =
            new EntityModelLayer(new Identifier(WynnMod.MOD_ID, "honza"), "main");
    public static final EntityModelLayer SIMOCK =
            new EntityModelLayer(new Identifier(WynnMod.MOD_ID, "simock"), "main");

}
