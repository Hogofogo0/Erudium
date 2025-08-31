package hogo.erudium.entity.PlayerProxy;

import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.LivingEntity;

public class StaticPlayerModel<T extends LivingEntity> extends PlayerModel<T> {
    public StaticPlayerModel(ModelPart root, boolean slim) {
        super(root, slim);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount,
                          float ageInTicks, float netHeadYaw, float headPitch) {
        // Do nothing — leaves all parts at their default pose
    }
}
