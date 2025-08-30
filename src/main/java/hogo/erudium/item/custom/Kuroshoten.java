package hogo.erudium.item.custom;

import hogo.erudium.ModDimensions;
import hogo.erudium.block.compressor.screen.ModMenuTypes;
import hogo.erudium.menus.CreativeMenu;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import team.lodestar.lodestone.systems.item.LodestoneItemProperties;

public class Kuroshoten extends SwordItem {
    public Kuroshoten(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(Tiers.NETHERITE, 15, 1.8f, new LodestoneItemProperties(CreativeMenu.TAB));
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {

        if(entity instanceof Player){
            entity.changeDimension(player.getServer().getLevel(ModDimensions.ENDLESS_VOID_KEY));
        }

        return super.onLeftClickEntity(stack, player, entity);
    }


}
