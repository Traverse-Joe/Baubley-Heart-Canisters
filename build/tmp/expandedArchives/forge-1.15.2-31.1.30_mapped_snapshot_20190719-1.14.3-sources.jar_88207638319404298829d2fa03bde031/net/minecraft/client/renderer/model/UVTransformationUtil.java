package net.minecraft.client.renderer.model;

import com.google.common.collect.Maps;
import java.util.EnumMap;
import java.util.function.Supplier;
import net.minecraft.client.renderer.Matrix4f;
import net.minecraft.client.renderer.Quaternion;
import net.minecraft.client.renderer.TransformationMatrix;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.util.Direction;
import net.minecraft.util.Util;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class UVTransformationUtil {
   private static final Logger field_229378_c_ = LogManager.getLogger();
   public static final EnumMap<Direction, TransformationMatrix> field_229376_a_ = Util.make(Maps.newEnumMap(Direction.class), (p_229382_0_) -> {
      p_229382_0_.put(Direction.SOUTH, TransformationMatrix.func_227983_a_());
      p_229382_0_.put(Direction.EAST, new TransformationMatrix((Vector3f)null, new Quaternion(new Vector3f(0.0F, 1.0F, 0.0F), 90.0F, true), (Vector3f)null, (Quaternion)null));
      p_229382_0_.put(Direction.WEST, new TransformationMatrix((Vector3f)null, new Quaternion(new Vector3f(0.0F, 1.0F, 0.0F), -90.0F, true), (Vector3f)null, (Quaternion)null));
      p_229382_0_.put(Direction.NORTH, new TransformationMatrix((Vector3f)null, new Quaternion(new Vector3f(0.0F, 1.0F, 0.0F), 180.0F, true), (Vector3f)null, (Quaternion)null));
      p_229382_0_.put(Direction.UP, new TransformationMatrix((Vector3f)null, new Quaternion(new Vector3f(1.0F, 0.0F, 0.0F), -90.0F, true), (Vector3f)null, (Quaternion)null));
      p_229382_0_.put(Direction.DOWN, new TransformationMatrix((Vector3f)null, new Quaternion(new Vector3f(1.0F, 0.0F, 0.0F), 90.0F, true), (Vector3f)null, (Quaternion)null));
   });
   public static final EnumMap<Direction, TransformationMatrix> field_229377_b_ = Util.make(Maps.newEnumMap(Direction.class), (p_229381_0_) -> {
      for(Direction direction : Direction.values()) {
         p_229381_0_.put(direction, field_229376_a_.get(direction).func_227987_b_());
      }

   });

   public static TransformationMatrix func_229379_a_(TransformationMatrix p_229379_0_) {
      Matrix4f matrix4f = Matrix4f.func_226599_b_(0.5F, 0.5F, 0.5F);
      matrix4f.func_226595_a_(p_229379_0_.func_227988_c_());
      matrix4f.func_226595_a_(Matrix4f.func_226599_b_(-0.5F, -0.5F, -0.5F));
      return new TransformationMatrix(matrix4f);
   }

   public static TransformationMatrix func_229380_a_(TransformationMatrix p_229380_0_, Direction p_229380_1_, Supplier<String> p_229380_2_) {
      Direction direction = Direction.func_229385_a_(p_229380_0_.func_227988_c_(), p_229380_1_);
      TransformationMatrix transformationmatrix = p_229380_0_.func_227987_b_();
      if (transformationmatrix == null) {
         field_229378_c_.warn(p_229380_2_.get());
         return new TransformationMatrix((Vector3f)null, (Quaternion)null, new Vector3f(0.0F, 0.0F, 0.0F), (Quaternion)null);
      } else {
         TransformationMatrix transformationmatrix1 = field_229377_b_.get(p_229380_1_).func_227985_a_(transformationmatrix).func_227985_a_(field_229376_a_.get(direction));
         return func_229379_a_(transformationmatrix1);
      }
   }
}