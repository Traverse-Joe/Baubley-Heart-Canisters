package com.traverse.bhc.client.renderer;

import com.geckolib.animation.state.BoneSnapshot;
import com.geckolib.constant.DataTickets;
import com.geckolib.renderer.GeoArmorRenderer;
import com.geckolib.renderer.base.BoneSnapshots;
import com.geckolib.renderer.base.RenderPassInfo;
import com.traverse.bhc.client.model.GoldenPaladinArmorModel;
import com.traverse.bhc.common.items.GoldenPaladinArmorItem;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GoldenPaladinArmorRenderer extends GeoArmorRenderer<GoldenPaladinArmorItem, HumanoidRenderState> {
    private static final double TAU_FAST = 0.06;
    private static final double TAU_SLOW = 0.30;
    private static final double ANGLE_TAU = 0.08;
    private static final double SWING_SCALE = 40.0;
    private static final double LEAN_SCALE = 20.0;
    private static final double VERT_SCALE = 15.0;
    private static final double MAX_PITCH_DEG = 70.0;
    private static final double MAX_FALL_DEG = 40.0;
    private static final double MAX_ROLL_DEG = 25.0;
    private static final double BASE_PITCH_DEG = 2.0;
    private static final double CROUCH_ADD_DEG = 25.0;
    private static final double BOB_FREQ = 0.6662;
    private static final double BOB_AMP_DEG = 4.0;
    private static final double PITCH_SIGN = -1.0;
    private static final double ROLL_SIGN = 1.0;
    private static final int MAX_TRACKED = 64;

    private static final Map<Long, CapeState> CAPE_STATES = new ConcurrentHashMap<>();

    public GoldenPaladinArmorRenderer() {
        super(new GoldenPaladinArmorModel());
    }

    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void adjustModelBonesForRender(RenderPassInfo pass, BoneSnapshots snapshots) {
        super.adjustModelBonesForRender(pass, snapshots);
        HumanoidRenderState state = (HumanoidRenderState) pass.renderState();
        Vec3 pos = (Vec3) pass.getOrDefaultGeckolibData(DataTickets.POSITION, Vec3.ZERO);
        long id = (Long) pass.getOrDefaultGeckolibData(DataTickets.ANIMATABLE_INSTANCE_ID, 0L);
        snapshots.ifPresent("cape", cape -> applyCapePhysics(cape, state, pos, id));
    }

    private void applyCapePhysics(BoneSnapshot cape, HumanoidRenderState state, Vec3 pos, long id) {
        CapeState cs = CAPE_STATES.computeIfAbsent(id, k -> new CapeState(pos));
        if (CAPE_STATES.size() > MAX_TRACKED) CAPE_STATES.clear();

        long now = System.nanoTime();
        double dt = Mth.clamp((now - cs.lastNanos) / 1.0e9, 0.0, 0.1);
        cs.lastNanos = now;

        double aFast = 1.0 - Math.exp(-dt / TAU_FAST);
        double aSlow = 1.0 - Math.exp(-dt / TAU_SLOW);
        cs.fastX += (pos.x - cs.fastX) * aFast;
        cs.fastY += (pos.y - cs.fastY) * aFast;
        cs.fastZ += (pos.z - cs.fastZ) * aFast;
        cs.slowX += (pos.x - cs.slowX) * aSlow;
        cs.slowY += (pos.y - cs.slowY) * aSlow;
        cs.slowZ += (pos.z - cs.slowZ) * aSlow;

        double velX = cs.fastX - cs.slowX;
        double velY = cs.fastY - cs.slowY;
        double velZ = cs.fastZ - cs.slowZ;

        double yawRad = state.bodyRot * Mth.DEG_TO_RAD;
        double sin = Math.sin(yawRad), cos = Math.cos(yawRad);
        double forward = velX * -sin + velZ * cos;
        double side = velX * cos + velZ * sin;

        double swing = Mth.clamp(forward * SWING_SCALE, 0.0, MAX_PITCH_DEG);
        double fall = Mth.clamp(-velY * VERT_SCALE, 0.0, MAX_FALL_DEG);
        double roll = Mth.clamp(side * LEAN_SCALE, -MAX_ROLL_DEG, MAX_ROLL_DEG);

        double bob = Math.sin(state.walkAnimationPos * BOB_FREQ) * BOB_AMP_DEG
                * Mth.clamp(state.walkAnimationSpeed * 4.0F, 0.0, 1.0);

        double targetPitch = BASE_PITCH_DEG + swing + fall + bob + (state.isCrouching ? CROUCH_ADD_DEG : 0.0);
        double targetRoll = roll;

        double aAngle = 1.0 - Math.exp(-dt / ANGLE_TAU);
        cs.pitch += (targetPitch - cs.pitch) * aAngle;
        cs.roll += (targetRoll - cs.roll) * aAngle;

        cape.setRotX(cape.getRotX() + (float) (PITCH_SIGN * cs.pitch * Mth.DEG_TO_RAD));
        cape.setRotZ(cape.getRotZ() + (float) (ROLL_SIGN * cs.roll * Mth.DEG_TO_RAD));
    }

    private static final class CapeState {
        double fastX, fastY, fastZ;
        double slowX, slowY, slowZ;
        double pitch, roll;
        long lastNanos;

        CapeState(Vec3 pos) {
            this.fastX = this.slowX = pos.x;
            this.fastY = this.slowY = pos.y;
            this.fastZ = this.slowZ = pos.z;
            this.lastNanos = System.nanoTime();
        }
    }
}
