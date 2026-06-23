package com.traverse.bhc.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.traverse.bhc.common.entity.VitalicOrb;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4fc;

public class VitalicOrbRenderer extends EntityRenderer<VitalicOrb, VitalicOrbRenderState> {

    private static final RenderType RENDER_TYPE = RenderTypes.lightning();

    private static final float CORE_RADIUS = 0.09F;
    private static final float MID_RADIUS = 0.16F;
    private static final float TRAIL_HEAD_WIDTH = 0.14F;
    private static final float TRAIL_TAIL_WIDTH = 0.03F;

    public VitalicOrbRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.shadowRadius = 0.0F;
    }

    @Override
    public VitalicOrbRenderState createRenderState() {
        return new VitalicOrbRenderState();
    }

    @Override
    public void extractRenderState(VitalicOrb entity, VitalicOrbRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        state.color = VitalicOrb.colorFor(entity.getSource());
        state.trail.clear();
        state.trail.addAll(entity.getClientTrail());
    }

    @Override
    public void submit(VitalicOrbRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        int r = (state.color >> 16) & 0xFF;
        int g = (state.color >> 8) & 0xFF;
        int b = state.color & 0xFF;

        Vec3 entityPos = new Vec3(state.x, state.y, state.z);
        Vec3 cameraDir = camera.pos.subtract(entityPos);
        if (cameraDir.lengthSqr() < 1.0E-6) cameraDir = new Vec3(0.0, 0.0, 1.0);
        Vec3 cameraDirN = cameraDir.normalize();

        submitNodeCollector.submitCustomGeometry(poseStack, RENDER_TYPE, (pose, buffer) -> {
            Matrix4fc poseMatrix = pose.pose();
            drawTrail(buffer, poseMatrix, state, entityPos, cameraDirN, r, g, b);
        });

        poseStack.pushPose();
        poseStack.mulPose(camera.orientation);
        submitNodeCollector.submitCustomGeometry(poseStack, RENDER_TYPE, (pose, buffer) -> {
            Matrix4fc poseMatrix = pose.pose();
            quadBillboard(buffer, poseMatrix, MID_RADIUS, r, g, b, 180);
            quadBillboard(buffer, poseMatrix, CORE_RADIUS, r, g, b, 255);
        });
        poseStack.popPose();
    }

    private static void drawTrail(VertexConsumer buffer, Matrix4fc pose, VitalicOrbRenderState state, Vec3 entityPos, Vec3 cameraDir, int r, int g, int b) {
        int n = state.trail.size();
        if (n < 2) return;
        for (int i = 0; i < n - 1; i++) {
            Vec3 a = state.trail.get(i).subtract(entityPos);
            Vec3 c = state.trail.get(i + 1).subtract(entityPos);
            Vec3 segDir = c.subtract(a);
            if (segDir.lengthSqr() < 1.0E-6) continue;
            Vec3 right = cameraDir.cross(segDir.normalize());
            if (right.lengthSqr() < 1.0E-6) continue;
            right = right.normalize();

            float tA = i / (float) (n - 1);
            float tC = (i + 1) / (float) (n - 1);
            float widthA = lerp(TRAIL_HEAD_WIDTH, TRAIL_TAIL_WIDTH, tA);
            float widthC = lerp(TRAIL_HEAD_WIDTH, TRAIL_TAIL_WIDTH, tC);
            int alphaA = (int) ((1.0F - tA) * 140);
            int alphaC = (int) ((1.0F - tC) * 140);

            Vec3 aL = a.add(right.scale(widthA));
            Vec3 aR = a.subtract(right.scale(widthA));
            Vec3 cL = c.add(right.scale(widthC));
            Vec3 cR = c.subtract(right.scale(widthC));

            buffer.addVertex(pose, (float) aR.x, (float) aR.y, (float) aR.z).setColor(r, g, b, alphaA);
            buffer.addVertex(pose, (float) cR.x, (float) cR.y, (float) cR.z).setColor(r, g, b, alphaC);
            buffer.addVertex(pose, (float) cL.x, (float) cL.y, (float) cL.z).setColor(r, g, b, alphaC);
            buffer.addVertex(pose, (float) aL.x, (float) aL.y, (float) aL.z).setColor(r, g, b, alphaA);
        }
    }

    private static void quadBillboard(VertexConsumer buffer, Matrix4fc pose, float radius, int r, int g, int b, int a) {
        buffer.addVertex(pose, -radius, -radius, 0.0F).setColor(r, g, b, a);
        buffer.addVertex(pose,  radius, -radius, 0.0F).setColor(r, g, b, a);
        buffer.addVertex(pose,  radius,  radius, 0.0F).setColor(r, g, b, a);
        buffer.addVertex(pose, -radius,  radius, 0.0F).setColor(r, g, b, a);
    }

    private static float lerp(float from, float to, float t) {
        return from + (to - from) * t;
    }

    @Override
    protected int getBlockLightLevel(VitalicOrb entity, BlockPos blockPos) {
        return 15;
    }

    @Override
    protected int getSkyLightLevel(VitalicOrb entity, BlockPos blockPos) {
        return 15;
    }

    @Override
    protected boolean affectedByCulling(VitalicOrb entity) {
        return false;
    }
}
