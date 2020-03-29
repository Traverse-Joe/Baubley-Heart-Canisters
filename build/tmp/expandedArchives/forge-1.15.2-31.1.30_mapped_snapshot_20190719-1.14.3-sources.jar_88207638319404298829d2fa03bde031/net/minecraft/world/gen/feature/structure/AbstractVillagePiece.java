package net.minecraft.world.gen.feature.structure;

import com.google.common.collect.Lists;
import com.mojang.datafixers.Dynamic;
import java.util.List;
import java.util.Random;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.NBTDynamicOps;
import net.minecraft.util.IDynamicDeserializer;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.jigsaw.EmptyJigsawPiece;
import net.minecraft.world.gen.feature.jigsaw.JigsawJunction;
import net.minecraft.world.gen.feature.jigsaw.JigsawPiece;
import net.minecraft.world.gen.feature.template.TemplateManager;

public abstract class AbstractVillagePiece extends StructurePiece {
   protected final JigsawPiece field_214832_a;
   protected BlockPos field_214833_b;
   private final int groundLevelDelta;
   protected final Rotation rotation;
   private final List<JigsawJunction> junctions = Lists.newArrayList();
   private final TemplateManager field_214837_f;

   public AbstractVillagePiece(IStructurePieceType p_i51346_1_, TemplateManager p_i51346_2_, JigsawPiece p_i51346_3_, BlockPos p_i51346_4_, int groundLevelDelta, Rotation rotation, MutableBoundingBox p_i51346_7_) {
      super(p_i51346_1_, 0);
      this.field_214837_f = p_i51346_2_;
      this.field_214832_a = p_i51346_3_;
      this.field_214833_b = p_i51346_4_;
      this.groundLevelDelta = groundLevelDelta;
      this.rotation = rotation;
      this.boundingBox = p_i51346_7_;
   }

   public AbstractVillagePiece(TemplateManager p_i51347_1_, CompoundNBT p_i51347_2_, IStructurePieceType p_i51347_3_) {
      super(p_i51347_3_, p_i51347_2_);
      this.field_214837_f = p_i51347_1_;
      this.field_214833_b = new BlockPos(p_i51347_2_.getInt("PosX"), p_i51347_2_.getInt("PosY"), p_i51347_2_.getInt("PosZ"));
      this.groundLevelDelta = p_i51347_2_.getInt("ground_level_delta");
      this.field_214832_a = IDynamicDeserializer.func_214907_a(new Dynamic<>(NBTDynamicOps.INSTANCE, p_i51347_2_.getCompound("pool_element")), Registry.STRUCTURE_POOL_ELEMENT, "element_type", EmptyJigsawPiece.INSTANCE);
      this.rotation = Rotation.valueOf(p_i51347_2_.getString("rotation"));
      this.boundingBox = this.field_214832_a.func_214852_a(p_i51347_1_, this.field_214833_b, this.rotation);
      ListNBT listnbt = p_i51347_2_.getList("junctions", 10);
      this.junctions.clear();
      listnbt.forEach((p_214827_1_) -> {
         this.junctions.add(JigsawJunction.deserialize(new Dynamic<>(NBTDynamicOps.INSTANCE, p_214827_1_)));
      });
   }

   /**
    * (abstract) Helper method to read subclass data from NBT
    */
   protected void readAdditional(CompoundNBT tagCompound) {
      tagCompound.putInt("PosX", this.field_214833_b.getX());
      tagCompound.putInt("PosY", this.field_214833_b.getY());
      tagCompound.putInt("PosZ", this.field_214833_b.getZ());
      tagCompound.putInt("ground_level_delta", this.groundLevelDelta);
      tagCompound.put("pool_element", this.field_214832_a.serialize(NBTDynamicOps.INSTANCE).getValue());
      tagCompound.putString("rotation", this.rotation.name());
      ListNBT listnbt = new ListNBT();

      for(JigsawJunction jigsawjunction : this.junctions) {
         listnbt.add(jigsawjunction.serialize(NBTDynamicOps.INSTANCE).getValue());
      }

      tagCompound.put("junctions", listnbt);
   }

   public boolean func_225577_a_(IWorld p_225577_1_, ChunkGenerator<?> p_225577_2_, Random p_225577_3_, MutableBoundingBox p_225577_4_, ChunkPos p_225577_5_) {
      return this.field_214832_a.func_225575_a_(this.field_214837_f, p_225577_1_, p_225577_2_, this.field_214833_b, this.rotation, p_225577_4_, p_225577_3_);
   }

   public void offset(int x, int y, int z) {
      super.offset(x, y, z);
      this.field_214833_b = this.field_214833_b.add(x, y, z);
   }

   public Rotation getRotation() {
      return this.rotation;
   }

   public String toString() {
      return String.format("<%s | %s | %s | %s>", this.getClass().getSimpleName(), this.field_214833_b, this.rotation, this.field_214832_a);
   }

   public JigsawPiece func_214826_b() {
      return this.field_214832_a;
   }

   public BlockPos func_214828_c() {
      return this.field_214833_b;
   }

   public int getGroundLevelDelta() {
      return this.groundLevelDelta;
   }

   public void addJunction(JigsawJunction p_214831_1_) {
      this.junctions.add(p_214831_1_);
   }

   public List<JigsawJunction> getJunctions() {
      return this.junctions;
   }
}