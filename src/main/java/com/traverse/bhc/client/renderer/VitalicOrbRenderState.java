package com.traverse.bhc.client.renderer;

import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class VitalicOrbRenderState extends EntityRenderState {
    public int color = 0xFFFFFF;
    public final List<Vec3> trail = new ArrayList<>();
}
