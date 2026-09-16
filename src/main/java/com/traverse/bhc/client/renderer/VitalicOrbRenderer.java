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

    private static final float HOT_CENTER_RADIUS = 0.045F;
    private static final float CORE_RADIUS = 0.10F;
    private static final float INNER_HALO_RADIUS = 0.18F;
    private static final float OUTER_HALO_RADIUS = 0.32F;
    private static final float STAR_SPIKE_RADIUS = 0.50F;

    private static final int HOT_CENTER_ALPHA = 255;
    private static final int CORE_ALPHA = 220;
    private static final int INNER_HALO_ALPHA = 130;
    private static final int OUTER_HALO_ALPHA = 60;
    private static final int STAR_SPIKE_ALPHA = 30;

    private static final int DISC_SEGMENTS = 20;

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

        int hotR = lerpToWhite(r, 0.75F);
        int hotG = lerpToWhite(g, 0.75F);
        int hotB = lerpToWhite(b, 0.75F);

        int coreR = lerpToWhite(r, 0.35F);
        int coreG = lerpToWhite(g, 0.35F);
        int coreB = lerpToWhite(b, 0.35F);

        float t = state.ageInTicks;
        float pulse = 1.0F + 0.12F * (float) Math.sin(t * 0.20F);
        float halopulse = 1.0F + 0.18F * (float) Math.sin(t * 0.13F + 1.7F);
        float spinA = t * 0.05F;
        float spinB = -t * 0.035F + 0.7854F;

        Vec3 entityPos = new Vec3(state.x, state.y, state.z);
        Vec3 cameraDir = camera.pos.subtract(entityPos);
        if (cameraDir.lengthSqr() < 1.0E-6) cameraDir = new Vec3(0.0, 0.0, 1.0);
        Vec3 cameraDirN = cameraDir.normalize();

        submitNodeCollector.submitCustomGeometry(poseStack, RENDER_TYPE, (pose, buffer) -> {
            Matrix4fc poseMatrix = pose.pose();
            drawTrail(buffer, poseMatrix, state, entityPos, cameraDirN, r, g, b);

            float spinY = t * 0.04F;
            float cosY = (float) Math.cos(spinY);
            float sinY = (float) Math.sin(spinY);
            float[] axA1 = { cosY, 0F, sinY };
            float[] axA2 = { 0F, 1F, 0F };
            float[] axB1 = { -sinY, 0F, cosY };
            float[] axB2 = { 0F, 1F, 0F };
            float[] axC1 = { cosY, 0F, sinY };
            float[] axC2 = { -sinY, 0F, cosY };

            float coreR3D = CORE_RADIUS * pulse;
            disc(buffer, poseMatrix, axA1, axA2, coreR3D, coreR, coreG, coreB, CORE_ALPHA, 0);
            disc(buffer, poseMatrix, axB1, axB2, coreR3D, coreR, coreG, coreB, CORE_ALPHA, 0);
            disc(buffer, poseMatrix, axC1, axC2, coreR3D * 0.95F, coreR, coreG, coreB, CORE_ALPHA, 0);

            float hotR3D = HOT_CENTER_RADIUS;
            disc(buffer, poseMatrix, axA1, axA2, hotR3D, hotR, hotG, hotB, HOT_CENTER_ALPHA, 0);
            disc(buffer, poseMatrix, axB1, axB2, hotR3D, hotR, hotG, hotB, HOT_CENTER_ALPHA, 0);
            disc(buffer, poseMatrix, axC1, axC2, hotR3D, hotR, hotG, hotB, HOT_CENTER_ALPHA, 0);
        });

        poseStack.pushPose();
        poseStack.mulPose(camera.orientation);
        float[] xAxis = { 1F, 0F, 0F };
        float[] yAxis = { 0F, 1F, 0F };
        submitNodeCollector.submitCustomGeometry(poseStack, RENDER_TYPE, (pose, buffer) -> {
            Matrix4fc poseMatrix = pose.pose();
            spike(buffer, poseMatrix, STAR_SPIKE_RADIUS * halopulse, spinA, r, g, b, STAR_SPIKE_ALPHA);
            spike(buffer, poseMatrix, STAR_SPIKE_RADIUS * halopulse * 0.85F, spinB, r, g, b, STAR_SPIKE_ALPHA);
            disc(buffer, poseMatrix, xAxis, yAxis, OUTER_HALO_RADIUS * halopulse, r, g, b, OUTER_HALO_ALPHA, 0);
            disc(buffer, poseMatrix, xAxis, yAxis, INNER_HALO_RADIUS * pulse, r, g, b, INNER_HALO_ALPHA, 0);
        });
        poseStack.popPose();
    }

    private static int lerpToWhite(int channel, float t) {
        return Math.max(0, Math.min(255, (int) (channel + (255 - channel) * t)));
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

    private static void disc(VertexConsumer buffer, Matrix4fc pose, float[] u, float[] v, float radius, int r, int g, int b, int centerAlpha, int rimAlpha) {
        for (int i = 0; i < DISC_SEGMENTS; i++) {
            float a0 = (i / (float) DISC_SEGMENTS) * 6.2831853F;
            float a1 = ((i + 1) / (float) DISC_SEGMENTS) * 6.2831853F;
            float c0 = (float) Math.cos(a0) * radius;
            float s0 = (float) Math.sin(a0) * radius;
            float c1 = (float) Math.cos(a1) * radius;
            float s1 = (float) Math.sin(a1) * radius;
            float x0 = u[0] * c0 + v[0] * s0;
            float y0 = u[1] * c0 + v[1] * s0;
            float z0 = u[2] * c0 + v[2] * s0;
            float x1 = u[0] * c1 + v[0] * s1;
            float y1 = u[1] * c1 + v[1] * s1;
            float z1 = u[2] * c1 + v[2] * s1;
            buffer.addVertex(pose, 0F, 0F, 0F).setColor(r, g, b, centerAlpha);
            buffer.addVertex(pose, x0, y0, z0).setColor(r, g, b, rimAlpha);
            buffer.addVertex(pose, x1, y1, z1).setColor(r, g, b, rimAlpha);
            buffer.addVertex(pose, x1, y1, z1).setColor(r, g, b, rimAlpha);
        }
    }

    private static void spike(VertexConsumer buffer, Matrix4fc pose, float radius, float angle, int r, int g, int b, int rimAlpha) {
        float spikeLen = radius;
        float spikeHalfWidth = radius * 0.04F;
        for (int arm = 0; arm < 4; arm++) {
            float armAngle = angle + arm * 1.5707963F;
            float cos = (float) Math.cos(armAngle);
            float sin = (float) Math.sin(armAngle);
            float tipX = cos * spikeLen;
            float tipY = sin * spikeLen;
            float perpX = -sin * spikeHalfWidth;
            float perpY =  cos * spikeHalfWidth;
            buffer.addVertex(pose, 0F, 0F, 0F).setColor(r, g, b, rimAlpha * 4);
            buffer.addVertex(pose,  perpX,  perpY, 0F).setColor(r, g, b, 0);
            buffer.addVertex(pose, tipX, tipY, 0F).setColor(r, g, b, 0);
            buffer.addVertex(pose, -perpX, -perpY, 0F).setColor(r, g, b, 0);
        }
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
