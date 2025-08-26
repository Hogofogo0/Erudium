package hogo.erudium.entity.honza;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import hogo.erudium.entity.animation.ModAnimations;
import hogo.erudium.entity.vojta.VojtaEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.monster.Monster;

public class HonzaModel<H extends Monster> extends HierarchicalModel<HonzaEntity> {
	private final ModelPart vojta;
	private final ModelPart head;

	public HonzaModel(ModelPart root) {
		this.vojta = root.getChild("Vojta");
		this.head = vojta.getChild("Head");
	}

	public static LayerDefinition createLayer() {
		MeshDefinition mesh = new MeshDefinition();
		PartDefinition root = mesh.getRoot();

		PartDefinition vojta = root.addOrReplaceChild("Vojta", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		// Legs
		vojta.addOrReplaceChild("LeftLeg",
				CubeListBuilder.create()
						.texOffs(16, 48).addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, new CubeDeformation(0.0F))
						.texOffs(0, 48).addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, new CubeDeformation(0.25F)),
				PartPose.offset(1.9F, -12.0F, 0.0F));

		vojta.addOrReplaceChild("RightLeg",
				CubeListBuilder.create()
						.texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, new CubeDeformation(0.0F))
						.texOffs(0, 32).addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, new CubeDeformation(0.25F)),
				PartPose.offset(-1.9F, -12.0F, 0.0F));

		// Arms
		vojta.addOrReplaceChild("LeftArm",
				CubeListBuilder.create()
						.texOffs(32, 48).addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, new CubeDeformation(0.0F))
						.texOffs(48, 48).addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, new CubeDeformation(0.25F)),
				PartPose.offset(5.0F, -22.0F, 0.0F));

		vojta.addOrReplaceChild("RightArm",
				CubeListBuilder.create()
						.texOffs(40, 16).addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, new CubeDeformation(0.0F))
						.texOffs(40, 32).addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, new CubeDeformation(0.25F)),
				PartPose.offset(-5.0F, -22.0F, 0.0F));

		// Body
		vojta.addOrReplaceChild("Body",
				CubeListBuilder.create()
						.texOffs(16, 16).addBox(-4.0F, -12.0F, -2.0F, 8, 12, 4, new CubeDeformation(0.0F))
						.texOffs(16, 32).addBox(-4.0F, -12.0F, -2.0F, 8, 12, 4, new CubeDeformation(0.25F)),
				PartPose.offset(0.0F, -12.0F, 0.0F));

		// Head
		vojta.addOrReplaceChild("Head",
				CubeListBuilder.create()
						.texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, new CubeDeformation(0.0F))
						.texOffs(32, 0).addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, new CubeDeformation(0.5F)),
				PartPose.offset(0.0F, -24.0F, 0.0F));

		return LayerDefinition.create(mesh, 64, 64);
	}

	@Override
	public void setupAnim(HonzaEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		// Reset transforms
		this.root().getAllParts().forEach(ModelPart::resetPose);

		// Head rotation
		head.yRot = Mth.clamp(netHeadYaw, -30f, 30f) * ((float) Math.PI / 180F);
		head.xRot = Mth.clamp(headPitch, -25f, 25f) * ((float) Math.PI / 180F);

		// Example walking animation (replace with your ModAnimations if needed)
		this.animateWalk(ModAnimations.WALK,limbSwing,limbSwingAmount,1f,1f);

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		vojta.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return vojta;
	}

	public ModelPart getVojta() {
		return vojta;
	}
}
