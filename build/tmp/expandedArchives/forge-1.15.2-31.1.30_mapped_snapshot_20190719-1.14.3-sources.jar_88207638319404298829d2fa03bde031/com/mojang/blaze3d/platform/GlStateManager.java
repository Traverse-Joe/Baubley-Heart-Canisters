package com.mojang.blaze3d.platform;

import com.mojang.blaze3d.systems.RenderSystem;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.stream.IntStream;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.Matrix4f;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.Vector4f;
import net.minecraft.client.shader.FramebufferConstants;
import net.minecraft.client.util.LWJGLMemoryUntracker;
import net.minecraft.util.Util;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.opengl.ARBFramebufferObject;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.MemoryUtil;

@OnlyIn(Dist.CLIENT)
public class GlStateManager {
   private static final FloatBuffer field_225656_a_ = GLX.make(MemoryUtil.memAllocFloat(16), (p_209238_0_) -> {
      LWJGLMemoryUntracker.untrack(MemoryUtil.memAddress(p_209238_0_));
   });
   private static final GlStateManager.AlphaState field_225657_b_ = new GlStateManager.AlphaState();
   private static final GlStateManager.BooleanState field_225658_c_ = new GlStateManager.BooleanState(2896);
   private static final GlStateManager.BooleanState[] field_225659_d_ = IntStream.range(0, 8).mapToObj((p_227620_0_) -> {
      return new GlStateManager.BooleanState(16384 + p_227620_0_);
   }).toArray((p_227618_0_) -> {
      return new GlStateManager.BooleanState[p_227618_0_];
   });
   private static final GlStateManager.ColorMaterialState field_225660_e_ = new GlStateManager.ColorMaterialState();
   private static final GlStateManager.BlendState field_225661_f_ = new GlStateManager.BlendState();
   private static final GlStateManager.DepthState field_225662_g_ = new GlStateManager.DepthState();
   private static final GlStateManager.FogState field_225663_h_ = new GlStateManager.FogState();
   private static final GlStateManager.CullState field_225664_i_ = new GlStateManager.CullState();
   private static final GlStateManager.PolygonOffsetState field_225665_j_ = new GlStateManager.PolygonOffsetState();
   private static final GlStateManager.ColorLogicState field_225666_k_ = new GlStateManager.ColorLogicState();
   private static final GlStateManager.TexGenState field_225667_l_ = new GlStateManager.TexGenState();
   private static final GlStateManager.ClearState field_225668_m_ = new GlStateManager.ClearState();
   private static final GlStateManager.StencilState field_225669_n_ = new GlStateManager.StencilState();
   private static final FloatBuffer field_227601_o_ = GLAllocation.createDirectFloatBuffer(4);
   private static final Vector3f field_227602_p_ = Util.make(new Vector3f(0.2F, 1.0F, -0.7F), Vector3f::func_229194_d_);
   private static final Vector3f field_227603_q_ = Util.make(new Vector3f(-0.2F, 1.0F, 0.7F), Vector3f::func_229194_d_);
   private static int field_225670_r_;
   private static final GlStateManager.TextureState[] field_225671_s_ = IntStream.range(0, 8).mapToObj((p_227616_0_) -> {
      return new GlStateManager.TextureState();
   }).toArray((p_227614_0_) -> {
      return new GlStateManager.TextureState[p_227614_0_];
   });
   private static int field_225672_t_ = 7425;
   private static final GlStateManager.BooleanState field_225673_u_ = new GlStateManager.BooleanState(32826);
   private static final GlStateManager.ColorMask field_225674_v_ = new GlStateManager.ColorMask();
   private static final GlStateManager.Color field_225675_w_ = new GlStateManager.Color();
   private static GlStateManager.FramebufferExtension field_227604_x_;

   @Deprecated
   public static void func_227630_a_() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL11.glPushAttrib(8256);
   }

   @Deprecated
   public static void func_227669_b_() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL11.glPushAttrib(270336);
   }

   @Deprecated
   public static void func_227686_c_() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL11.glPopAttrib();
   }

   @Deprecated
   public static void func_227700_d_() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      field_225657_b_.field_179208_a.func_179198_a();
   }

   @Deprecated
   public static void func_227709_e_() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      field_225657_b_.field_179208_a.func_179200_b();
   }

   @Deprecated
   public static void func_227639_a_(int p_227639_0_, float p_227639_1_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      if (p_227639_0_ != field_225657_b_.field_179206_b || p_227639_1_ != field_225657_b_.field_179207_c) {
         field_225657_b_.field_179206_b = p_227639_0_;
         field_225657_b_.field_179207_c = p_227639_1_;
         GL11.glAlphaFunc(p_227639_0_, p_227639_1_);
      }

   }

   @Deprecated
   public static void func_227716_f_() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      field_225658_c_.func_179200_b();
   }

   @Deprecated
   public static void func_227722_g_() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      field_225658_c_.func_179198_a();
   }

   @Deprecated
   public static void func_227638_a_(int p_227638_0_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      field_225659_d_[p_227638_0_].func_179200_b();
   }

   @Deprecated
   public static void func_227725_h_() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      field_225660_e_.field_179191_a.func_179200_b();
   }

   @Deprecated
   public static void func_227728_i_() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      field_225660_e_.field_179191_a.func_179198_a();
   }

   @Deprecated
   public static void func_227641_a_(int p_227641_0_, int p_227641_1_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      if (p_227641_0_ != field_225660_e_.field_179189_b || p_227641_1_ != field_225660_e_.field_179190_c) {
         field_225660_e_.field_179189_b = p_227641_0_;
         field_225660_e_.field_179190_c = p_227641_1_;
         GL11.glColorMaterial(p_227641_0_, p_227641_1_);
      }

   }

   @Deprecated
   public static void func_227653_a_(int p_227653_0_, int p_227653_1_, FloatBuffer p_227653_2_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL11.glLightfv(p_227653_0_, p_227653_1_, p_227653_2_);
   }

   @Deprecated
   public static void func_227656_a_(int p_227656_0_, FloatBuffer p_227656_1_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL11.glLightModelfv(p_227656_0_, p_227656_1_);
   }

   @Deprecated
   public static void func_227636_a_(float p_227636_0_, float p_227636_1_, float p_227636_2_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL11.glNormal3f(p_227636_0_, p_227636_1_, p_227636_2_);
   }

   public static void func_227731_j_() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      field_225662_g_.field_179052_a.func_179198_a();
   }

   public static void func_227734_k_() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      field_225662_g_.field_179052_a.func_179200_b();
   }

   public static void func_227674_b_(int p_227674_0_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      if (p_227674_0_ != field_225662_g_.field_179051_c) {
         field_225662_g_.field_179051_c = p_227674_0_;
         GL11.glDepthFunc(p_227674_0_);
      }

   }

   public static void func_227667_a_(boolean p_227667_0_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      if (p_227667_0_ != field_225662_g_.field_179050_b) {
         field_225662_g_.field_179050_b = p_227667_0_;
         GL11.glDepthMask(p_227667_0_);
      }

   }

   public static void func_227737_l_() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      field_225661_f_.field_179213_a.func_179198_a();
   }

   public static void func_227740_m_() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      field_225661_f_.field_179213_a.func_179200_b();
   }

   public static void func_227676_b_(int p_227676_0_, int p_227676_1_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      if (p_227676_0_ != field_225661_f_.field_179211_b || p_227676_1_ != field_225661_f_.field_179212_c) {
         field_225661_f_.field_179211_b = p_227676_0_;
         field_225661_f_.field_179212_c = p_227676_1_;
         GL11.glBlendFunc(p_227676_0_, p_227676_1_);
      }

   }

   public static void func_227644_a_(int p_227644_0_, int p_227644_1_, int p_227644_2_, int p_227644_3_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      if (p_227644_0_ != field_225661_f_.field_179211_b || p_227644_1_ != field_225661_f_.field_179212_c || p_227644_2_ != field_225661_f_.field_179209_d || p_227644_3_ != field_225661_f_.field_179210_e) {
         field_225661_f_.field_179211_b = p_227644_0_;
         field_225661_f_.field_179212_c = p_227644_1_;
         field_225661_f_.field_179209_d = p_227644_2_;
         field_225661_f_.field_179210_e = p_227644_3_;
         func_227706_d_(p_227644_0_, p_227644_1_, p_227644_2_, p_227644_3_);
      }

   }

   public static void func_227637_a_(float p_227637_0_, float p_227637_1_, float p_227637_2_, float p_227637_3_) {
      GL14.glBlendColor(p_227637_0_, p_227637_1_, p_227637_2_, p_227637_3_);
   }

   public static void func_227690_c_(int p_227690_0_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL14.glBlendEquation(p_227690_0_);
   }

   public static String func_227666_a_(GLCapabilities p_227666_0_) {
      RenderSystem.assertThread(RenderSystem::isInInitPhase);
      if (p_227666_0_.OpenGL30) {
         field_227604_x_ = GlStateManager.FramebufferExtension.BASE;
         FramebufferConstants.field_227592_a_ = 36160;
         FramebufferConstants.field_227593_b_ = 36161;
         FramebufferConstants.field_227594_c_ = 36064;
         FramebufferConstants.field_227595_d_ = 36096;
         FramebufferConstants.field_227596_e_ = 36053;
         FramebufferConstants.field_227597_f_ = 36054;
         FramebufferConstants.field_227598_g_ = 36055;
         FramebufferConstants.field_227599_h_ = 36059;
         FramebufferConstants.field_227600_i_ = 36060;
         return "OpenGL 3.0";
      } else if (p_227666_0_.GL_ARB_framebuffer_object) {
         field_227604_x_ = GlStateManager.FramebufferExtension.ARB;
         FramebufferConstants.field_227592_a_ = 36160;
         FramebufferConstants.field_227593_b_ = 36161;
         FramebufferConstants.field_227594_c_ = 36064;
         FramebufferConstants.field_227595_d_ = 36096;
         FramebufferConstants.field_227596_e_ = 36053;
         FramebufferConstants.field_227598_g_ = 36055;
         FramebufferConstants.field_227597_f_ = 36054;
         FramebufferConstants.field_227599_h_ = 36059;
         FramebufferConstants.field_227600_i_ = 36060;
         return "ARB_framebuffer_object extension";
      } else if (p_227666_0_.GL_EXT_framebuffer_object) {
         field_227604_x_ = GlStateManager.FramebufferExtension.EXT;
         FramebufferConstants.field_227592_a_ = 36160;
         FramebufferConstants.field_227593_b_ = 36161;
         FramebufferConstants.field_227594_c_ = 36064;
         FramebufferConstants.field_227595_d_ = 36096;
         FramebufferConstants.field_227596_e_ = 36053;
         FramebufferConstants.field_227598_g_ = 36055;
         FramebufferConstants.field_227597_f_ = 36054;
         FramebufferConstants.field_227599_h_ = 36059;
         FramebufferConstants.field_227600_i_ = 36060;
         return "EXT_framebuffer_object extension";
      } else {
         throw new IllegalStateException("Could not initialize framebuffer support.");
      }
   }

   public static int func_227691_c_(int p_227691_0_, int p_227691_1_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      return GL20.glGetProgrami(p_227691_0_, p_227691_1_);
   }

   public static void func_227704_d_(int p_227704_0_, int p_227704_1_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL20.glAttachShader(p_227704_0_, p_227704_1_);
   }

   public static void func_227703_d_(int p_227703_0_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL20.glDeleteShader(p_227703_0_);
   }

   public static int func_227711_e_(int p_227711_0_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      return GL20.glCreateShader(p_227711_0_);
   }

   public static void func_227654_a_(int p_227654_0_, CharSequence p_227654_1_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL20.glShaderSource(p_227654_0_, p_227654_1_);
   }

   public static void func_227717_f_(int p_227717_0_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL20.glCompileShader(p_227717_0_);
   }

   public static int func_227712_e_(int p_227712_0_, int p_227712_1_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      return GL20.glGetShaderi(p_227712_0_, p_227712_1_);
   }

   public static void func_227723_g_(int p_227723_0_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL20.glUseProgram(p_227723_0_);
   }

   public static int func_227743_n_() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      return GL20.glCreateProgram();
   }

   public static void func_227726_h_(int p_227726_0_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL20.glDeleteProgram(p_227726_0_);
   }

   public static void func_227729_i_(int p_227729_0_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL20.glLinkProgram(p_227729_0_);
   }

   public static int func_227680_b_(int p_227680_0_, CharSequence p_227680_1_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      return GL20.glGetUniformLocation(p_227680_0_, p_227680_1_);
   }

   public static void func_227657_a_(int p_227657_0_, IntBuffer p_227657_1_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL20.glUniform1iv(p_227657_0_, p_227657_1_);
   }

   public static void func_227718_f_(int p_227718_0_, int p_227718_1_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL20.glUniform1i(p_227718_0_, p_227718_1_);
   }

   public static void func_227681_b_(int p_227681_0_, FloatBuffer p_227681_1_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL20.glUniform1fv(p_227681_0_, p_227681_1_);
   }

   public static void func_227682_b_(int p_227682_0_, IntBuffer p_227682_1_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL20.glUniform2iv(p_227682_0_, p_227682_1_);
   }

   public static void func_227696_c_(int p_227696_0_, FloatBuffer p_227696_1_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL20.glUniform2fv(p_227696_0_, p_227696_1_);
   }

   public static void func_227697_c_(int p_227697_0_, IntBuffer p_227697_1_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL20.glUniform3iv(p_227697_0_, p_227697_1_);
   }

   public static void func_227707_d_(int p_227707_0_, FloatBuffer p_227707_1_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL20.glUniform3fv(p_227707_0_, p_227707_1_);
   }

   public static void func_227708_d_(int p_227708_0_, IntBuffer p_227708_1_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL20.glUniform4iv(p_227708_0_, p_227708_1_);
   }

   public static void func_227715_e_(int p_227715_0_, FloatBuffer p_227715_1_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL20.glUniform4fv(p_227715_0_, p_227715_1_);
   }

   public static void func_227659_a_(int p_227659_0_, boolean p_227659_1_, FloatBuffer p_227659_2_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL20.glUniformMatrix2fv(p_227659_0_, p_227659_1_, p_227659_2_);
   }

   public static void func_227683_b_(int p_227683_0_, boolean p_227683_1_, FloatBuffer p_227683_2_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL20.glUniformMatrix3fv(p_227683_0_, p_227683_1_, p_227683_2_);
   }

   public static void func_227698_c_(int p_227698_0_, boolean p_227698_1_, FloatBuffer p_227698_2_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL20.glUniformMatrix4fv(p_227698_0_, p_227698_1_, p_227698_2_);
   }

   public static int func_227695_c_(int p_227695_0_, CharSequence p_227695_1_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      return GL20.glGetAttribLocation(p_227695_0_, p_227695_1_);
   }

   public static int func_227746_o_() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      return GL15.glGenBuffers();
   }

   public static void func_227724_g_(int p_227724_0_, int p_227724_1_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      GL15.glBindBuffer(p_227724_0_, p_227724_1_);
   }

   public static void func_227655_a_(int p_227655_0_, ByteBuffer p_227655_1_, int p_227655_2_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      GL15.glBufferData(p_227655_0_, p_227655_1_, p_227655_2_);
   }

   public static void func_227732_j_(int p_227732_0_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL15.glDeleteBuffers(p_227732_0_);
   }

   public static void func_227727_h_(int p_227727_0_, int p_227727_1_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      switch(field_227604_x_) {
      case BASE:
         GL30.glBindFramebuffer(p_227727_0_, p_227727_1_);
         break;
      case ARB:
         ARBFramebufferObject.glBindFramebuffer(p_227727_0_, p_227727_1_);
         break;
      case EXT:
         EXTFramebufferObject.glBindFramebufferEXT(p_227727_0_, p_227727_1_);
      }

   }

   public static void func_227730_i_(int p_227730_0_, int p_227730_1_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      switch(field_227604_x_) {
      case BASE:
         GL30.glBindRenderbuffer(p_227730_0_, p_227730_1_);
         break;
      case ARB:
         ARBFramebufferObject.glBindRenderbuffer(p_227730_0_, p_227730_1_);
         break;
      case EXT:
         EXTFramebufferObject.glBindRenderbufferEXT(p_227730_0_, p_227730_1_);
      }

   }

   public static void func_227735_k_(int p_227735_0_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      switch(field_227604_x_) {
      case BASE:
         GL30.glDeleteRenderbuffers(p_227735_0_);
         break;
      case ARB:
         ARBFramebufferObject.glDeleteRenderbuffers(p_227735_0_);
         break;
      case EXT:
         EXTFramebufferObject.glDeleteRenderbuffersEXT(p_227735_0_);
      }

   }

   public static void func_227738_l_(int p_227738_0_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      switch(field_227604_x_) {
      case BASE:
         GL30.glDeleteFramebuffers(p_227738_0_);
         break;
      case ARB:
         ARBFramebufferObject.glDeleteFramebuffers(p_227738_0_);
         break;
      case EXT:
         EXTFramebufferObject.glDeleteFramebuffersEXT(p_227738_0_);
      }

   }

   public static int func_227749_p_() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      switch(field_227604_x_) {
      case BASE:
         return GL30.glGenFramebuffers();
      case ARB:
         return ARBFramebufferObject.glGenFramebuffers();
      case EXT:
         return EXTFramebufferObject.glGenFramebuffersEXT();
      default:
         return -1;
      }
   }

   public static int func_227752_q_() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      switch(field_227604_x_) {
      case BASE:
         return GL30.glGenRenderbuffers();
      case ARB:
         return ARBFramebufferObject.glGenRenderbuffers();
      case EXT:
         return EXTFramebufferObject.glGenRenderbuffersEXT();
      default:
         return -1;
      }
   }

   public static void func_227678_b_(int p_227678_0_, int p_227678_1_, int p_227678_2_, int p_227678_3_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      switch(field_227604_x_) {
      case BASE:
         GL30.glRenderbufferStorage(p_227678_0_, p_227678_1_, p_227678_2_, p_227678_3_);
         break;
      case ARB:
         ARBFramebufferObject.glRenderbufferStorage(p_227678_0_, p_227678_1_, p_227678_2_, p_227678_3_);
         break;
      case EXT:
         EXTFramebufferObject.glRenderbufferStorageEXT(p_227678_0_, p_227678_1_, p_227678_2_, p_227678_3_);
      }

   }

   public static void func_227693_c_(int p_227693_0_, int p_227693_1_, int p_227693_2_, int p_227693_3_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      switch(field_227604_x_) {
      case BASE:
         GL30.glFramebufferRenderbuffer(p_227693_0_, p_227693_1_, p_227693_2_, p_227693_3_);
         break;
      case ARB:
         ARBFramebufferObject.glFramebufferRenderbuffer(p_227693_0_, p_227693_1_, p_227693_2_, p_227693_3_);
         break;
      case EXT:
         EXTFramebufferObject.glFramebufferRenderbufferEXT(p_227693_0_, p_227693_1_, p_227693_2_, p_227693_3_);
      }

   }

   public static int func_227741_m_(int p_227741_0_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      switch(field_227604_x_) {
      case BASE:
         return GL30.glCheckFramebufferStatus(p_227741_0_);
      case ARB:
         return ARBFramebufferObject.glCheckFramebufferStatus(p_227741_0_);
      case EXT:
         return EXTFramebufferObject.glCheckFramebufferStatusEXT(p_227741_0_);
      default:
         return -1;
      }
   }

   public static void func_227645_a_(int p_227645_0_, int p_227645_1_, int p_227645_2_, int p_227645_3_, int p_227645_4_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      switch(field_227604_x_) {
      case BASE:
         GL30.glFramebufferTexture2D(p_227645_0_, p_227645_1_, p_227645_2_, p_227645_3_, p_227645_4_);
         break;
      case ARB:
         ARBFramebufferObject.glFramebufferTexture2D(p_227645_0_, p_227645_1_, p_227645_2_, p_227645_3_, p_227645_4_);
         break;
      case EXT:
         EXTFramebufferObject.glFramebufferTexture2DEXT(p_227645_0_, p_227645_1_, p_227645_2_, p_227645_3_, p_227645_4_);
      }

   }

   public static void func_227744_n_(int p_227744_0_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL13.glActiveTexture(p_227744_0_);
   }

   @Deprecated
   public static void func_227747_o_(int p_227747_0_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL13.glClientActiveTexture(p_227747_0_);
   }

   /* Stores the last values sent into glMultiTexCoord2f */
   public static float lastBrightnessX = 0.0f;
   public static float lastBrightnessY = 0.0f;
   @Deprecated
   public static void func_227640_a_(int p_227640_0_, float p_227640_1_, float p_227640_2_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL13.glMultiTexCoord2f(p_227640_0_, p_227640_1_, p_227640_2_);
      if (p_227640_0_ == GL13.GL_TEXTURE1) {
          lastBrightnessX = p_227640_1_;
          lastBrightnessY = p_227640_2_;
       }
   }

   public static void func_227706_d_(int p_227706_0_, int p_227706_1_, int p_227706_2_, int p_227706_3_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL14.glBlendFuncSeparate(p_227706_0_, p_227706_1_, p_227706_2_, p_227706_3_);
   }

   public static String func_227733_j_(int p_227733_0_, int p_227733_1_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      return GL20.glGetShaderInfoLog(p_227733_0_, p_227733_1_);
   }

   public static String func_227736_k_(int p_227736_0_, int p_227736_1_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      return GL20.glGetProgramInfoLog(p_227736_0_, p_227736_1_);
   }

   public static void func_227755_r_() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      func_227643_a_(8960, 8704, 34160);
      func_227751_p_(7681, 34168);
   }

   public static void func_227757_s_() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      func_227643_a_(8960, 8704, 8448);
      func_227720_f_(8448, 5890, 34168, 34166);
   }

   public static void func_227739_l_(int p_227739_0_, int p_227739_1_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      func_227756_r_(33985);
      func_227619_H_();
      func_227768_x_(5890);
      func_227625_M_();
      float f = 1.0F / (float)(p_227739_1_ - 1);
      func_227672_b_(f, f, f);
      func_227768_x_(5888);
      func_227760_t_(p_227739_0_);
      func_227677_b_(3553, 10241, 9728);
      func_227677_b_(3553, 10240, 9728);
      func_227677_b_(3553, 10242, 10496);
      func_227677_b_(3553, 10243, 10496);
      func_227643_a_(8960, 8704, 34160);
      func_227720_f_(34165, 34168, 5890, 5890);
      func_227754_q_(7681, 34168);
      func_227756_r_(33984);
   }

   public static void func_227759_t_() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      func_227756_r_(33985);
      func_227621_I_();
      func_227756_r_(33984);
   }

   private static void func_227751_p_(int p_227751_0_, int p_227751_1_) {
      func_227643_a_(8960, 34161, p_227751_0_);
      func_227643_a_(8960, 34176, p_227751_1_);
      func_227643_a_(8960, 34192, 768);
   }

   private static void func_227720_f_(int p_227720_0_, int p_227720_1_, int p_227720_2_, int p_227720_3_) {
      func_227643_a_(8960, 34161, p_227720_0_);
      func_227643_a_(8960, 34176, p_227720_1_);
      func_227643_a_(8960, 34192, 768);
      func_227643_a_(8960, 34177, p_227720_2_);
      func_227643_a_(8960, 34193, 768);
      func_227643_a_(8960, 34178, p_227720_3_);
      func_227643_a_(8960, 34194, 770);
   }

   private static void func_227754_q_(int p_227754_0_, int p_227754_1_) {
      func_227643_a_(8960, 34162, p_227754_0_);
      func_227643_a_(8960, 34184, p_227754_1_);
      func_227643_a_(8960, 34200, 770);
   }

   public static void func_227661_a_(Matrix4f p_227661_0_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      func_227626_N_();
      func_227625_M_();
      func_227638_a_(0);
      func_227638_a_(1);
      Vector4f vector4f = new Vector4f(field_227602_p_);
      vector4f.func_229372_a_(p_227661_0_);
      func_227653_a_(16384, 4611, func_227710_e_(vector4f.getX(), vector4f.getY(), vector4f.getZ(), 0.0F));
      float f = 0.6F;
      func_227653_a_(16384, 4609, func_227710_e_(0.6F, 0.6F, 0.6F, 1.0F));
      func_227653_a_(16384, 4608, func_227710_e_(0.0F, 0.0F, 0.0F, 1.0F));
      func_227653_a_(16384, 4610, func_227710_e_(0.0F, 0.0F, 0.0F, 1.0F));
      Vector4f vector4f1 = new Vector4f(field_227603_q_);
      vector4f1.func_229372_a_(p_227661_0_);
      func_227653_a_(16385, 4611, func_227710_e_(vector4f1.getX(), vector4f1.getY(), vector4f1.getZ(), 0.0F));
      func_227653_a_(16385, 4609, func_227710_e_(0.6F, 0.6F, 0.6F, 1.0F));
      func_227653_a_(16385, 4608, func_227710_e_(0.0F, 0.0F, 0.0F, 1.0F));
      func_227653_a_(16385, 4610, func_227710_e_(0.0F, 0.0F, 0.0F, 1.0F));
      func_227762_u_(7424);
      float f1 = 0.4F;
      func_227656_a_(2899, func_227710_e_(0.4F, 0.4F, 0.4F, 1.0F));
      func_227627_O_();
   }

   public static void func_229983_u_() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      Matrix4f matrix4f = new Matrix4f();
      matrix4f.func_226591_a_();
      matrix4f.func_226595_a_(Matrix4f.func_226593_a_(1.0F, -1.0F, 1.0F));
      matrix4f.func_226596_a_(Vector3f.field_229181_d_.func_229187_a_(-22.5F));
      matrix4f.func_226596_a_(Vector3f.field_229179_b_.func_229187_a_(135.0F));
      func_227661_a_(matrix4f);
   }

   public static void func_229984_v_() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      Matrix4f matrix4f = new Matrix4f();
      matrix4f.func_226591_a_();
      matrix4f.func_226596_a_(Vector3f.field_229181_d_.func_229187_a_(62.0F));
      matrix4f.func_226596_a_(Vector3f.field_229179_b_.func_229187_a_(185.5F));
      matrix4f.func_226595_a_(Matrix4f.func_226593_a_(1.0F, -1.0F, 1.0F));
      matrix4f.func_226596_a_(Vector3f.field_229181_d_.func_229187_a_(-22.5F));
      matrix4f.func_226596_a_(Vector3f.field_229179_b_.func_229187_a_(135.0F));
      func_227661_a_(matrix4f);
   }

   private static FloatBuffer func_227710_e_(float p_227710_0_, float p_227710_1_, float p_227710_2_, float p_227710_3_) {
      field_227601_o_.clear();
      field_227601_o_.put(p_227710_0_).put(p_227710_1_).put(p_227710_2_).put(p_227710_3_);
      field_227601_o_.flip();
      return field_227601_o_;
   }

   public static void func_227761_u_() {
      func_227663_a_(GlStateManager.TexGen.S, 9216);
      func_227663_a_(GlStateManager.TexGen.T, 9216);
      func_227663_a_(GlStateManager.TexGen.R, 9216);
      func_227664_a_(GlStateManager.TexGen.S, 9474, func_227710_e_(1.0F, 0.0F, 0.0F, 0.0F));
      func_227664_a_(GlStateManager.TexGen.T, 9474, func_227710_e_(0.0F, 1.0F, 0.0F, 0.0F));
      func_227664_a_(GlStateManager.TexGen.R, 9474, func_227710_e_(0.0F, 0.0F, 1.0F, 0.0F));
      func_227662_a_(GlStateManager.TexGen.S);
      func_227662_a_(GlStateManager.TexGen.T);
      func_227662_a_(GlStateManager.TexGen.R);
   }

   public static void func_227763_v_() {
      func_227685_b_(GlStateManager.TexGen.S);
      func_227685_b_(GlStateManager.TexGen.T);
      func_227685_b_(GlStateManager.TexGen.R);
   }

   public static void func_227765_w_() {
      func_227721_f_(2983, field_225656_a_);
      func_227665_a_(field_225656_a_);
      func_227721_f_(2982, field_225656_a_);
      func_227665_a_(field_225656_a_);
   }

   @Deprecated
   public static void func_227767_x_() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      field_225663_h_.field_179049_a.func_179200_b();
   }

   @Deprecated
   public static void func_227769_y_() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      field_225663_h_.field_179049_a.func_179198_a();
   }

   @Deprecated
   public static void func_227750_p_(int p_227750_0_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      if (p_227750_0_ != field_225663_h_.field_179047_b) {
         field_225663_h_.field_179047_b = p_227750_0_;
         func_227742_m_(2917, p_227750_0_);
      }

   }

   @Deprecated
   public static void func_227634_a_(float p_227634_0_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      if (p_227634_0_ != field_225663_h_.field_179048_c) {
         field_225663_h_.field_179048_c = p_227634_0_;
         GL11.glFogf(2914, p_227634_0_);
      }

   }

   @Deprecated
   public static void func_227671_b_(float p_227671_0_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      if (p_227671_0_ != field_225663_h_.field_179045_d) {
         field_225663_h_.field_179045_d = p_227671_0_;
         GL11.glFogf(2915, p_227671_0_);
      }

   }

   @Deprecated
   public static void func_227687_c_(float p_227687_0_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      if (p_227687_0_ != field_225663_h_.field_179046_e) {
         field_225663_h_.field_179046_e = p_227687_0_;
         GL11.glFogf(2916, p_227687_0_);
      }

   }

   @Deprecated
   public static void func_227660_a_(int p_227660_0_, float[] p_227660_1_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL11.glFogfv(p_227660_0_, p_227660_1_);
   }

   @Deprecated
   public static void func_227742_m_(int p_227742_0_, int p_227742_1_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL11.glFogi(p_227742_0_, p_227742_1_);
   }

   public static void func_227771_z_() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      field_225664_i_.field_179054_a.func_179200_b();
   }

   public static void func_227605_A_() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      field_225664_i_.field_179054_a.func_179198_a();
   }

   public static void func_227745_n_(int p_227745_0_, int p_227745_1_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL11.glPolygonMode(p_227745_0_, p_227745_1_);
   }

   public static void func_227607_B_() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      field_225665_j_.field_179044_a.func_179200_b();
   }

   public static void func_227609_C_() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      field_225665_j_.field_179044_a.func_179198_a();
   }

   public static void func_227611_D_() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      field_225665_j_.field_179042_b.func_179200_b();
   }

   public static void func_227613_E_() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      field_225665_j_.field_179042_b.func_179198_a();
   }

   public static void func_227635_a_(float p_227635_0_, float p_227635_1_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      if (p_227635_0_ != field_225665_j_.field_179043_c || p_227635_1_ != field_225665_j_.field_179041_d) {
         field_225665_j_.field_179043_c = p_227635_0_;
         field_225665_j_.field_179041_d = p_227635_1_;
         GL11.glPolygonOffset(p_227635_0_, p_227635_1_);
      }

   }

   public static void func_227615_F_() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      field_225666_k_.field_179197_a.func_179200_b();
   }

   public static void func_227617_G_() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      field_225666_k_.field_179197_a.func_179198_a();
   }

   public static void func_227753_q_(int p_227753_0_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      if (p_227753_0_ != field_225666_k_.field_179196_b) {
         field_225666_k_.field_179196_b = p_227753_0_;
         GL11.glLogicOp(p_227753_0_);
      }

   }

   @Deprecated
   public static void func_227662_a_(GlStateManager.TexGen p_227662_0_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      func_225677_c_(p_227662_0_).field_179067_a.func_179200_b();
   }

   @Deprecated
   public static void func_227685_b_(GlStateManager.TexGen p_227685_0_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      func_225677_c_(p_227685_0_).field_179067_a.func_179198_a();
   }

   @Deprecated
   public static void func_227663_a_(GlStateManager.TexGen p_227663_0_, int p_227663_1_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GlStateManager.TexGenCoord glstatemanager$texgencoord = func_225677_c_(p_227663_0_);
      if (p_227663_1_ != glstatemanager$texgencoord.field_179066_c) {
         glstatemanager$texgencoord.field_179066_c = p_227663_1_;
         GL11.glTexGeni(glstatemanager$texgencoord.field_179065_b, 9472, p_227663_1_);
      }

   }

   @Deprecated
   public static void func_227664_a_(GlStateManager.TexGen p_227664_0_, int p_227664_1_, FloatBuffer p_227664_2_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL11.glTexGenfv(func_225677_c_(p_227664_0_).field_179065_b, p_227664_1_, p_227664_2_);
   }

   @Deprecated
   private static GlStateManager.TexGenCoord func_225677_c_(GlStateManager.TexGen p_225677_0_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      switch(p_225677_0_) {
      case S:
         return field_225667_l_.field_179064_a;
      case T:
         return field_225667_l_.field_179062_b;
      case R:
         return field_225667_l_.field_179063_c;
      case Q:
         return field_225667_l_.field_179061_d;
      default:
         return field_225667_l_.field_179064_a;
      }
   }

   public static void func_227756_r_(int p_227756_0_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      if (field_225670_r_ != p_227756_0_ - '\u84c0') {
         field_225670_r_ = p_227756_0_ - '\u84c0';
         func_227744_n_(p_227756_0_);
      }

   }

   public static void func_227619_H_() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      field_225671_s_[field_225670_r_].field_179060_a.func_179200_b();
   }

   public static void func_227621_I_() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      field_225671_s_[field_225670_r_].field_179060_a.func_179198_a();
   }

   @Deprecated
   public static void func_227643_a_(int p_227643_0_, int p_227643_1_, int p_227643_2_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL11.glTexEnvi(p_227643_0_, p_227643_1_, p_227643_2_);
   }

   public static void func_227642_a_(int p_227642_0_, int p_227642_1_, float p_227642_2_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      GL11.glTexParameterf(p_227642_0_, p_227642_1_, p_227642_2_);
   }

   public static void func_227677_b_(int p_227677_0_, int p_227677_1_, int p_227677_2_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      GL11.glTexParameteri(p_227677_0_, p_227677_1_, p_227677_2_);
   }

   public static int func_227692_c_(int p_227692_0_, int p_227692_1_, int p_227692_2_) {
      RenderSystem.assertThread(RenderSystem::isInInitPhase);
      return GL11.glGetTexLevelParameteri(p_227692_0_, p_227692_1_, p_227692_2_);
   }

   public static int func_227622_J_() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      return GL11.glGenTextures();
   }

   public static void func_227758_s_(int p_227758_0_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      GL11.glDeleteTextures(p_227758_0_);

      for(GlStateManager.TextureState glstatemanager$texturestate : field_225671_s_) {
         if (glstatemanager$texturestate.field_179059_b == p_227758_0_) {
            glstatemanager$texturestate.field_179059_b = -1;
         }
      }

   }

   public static void func_227760_t_(int p_227760_0_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      if (p_227760_0_ != field_225671_s_[field_225670_r_].field_179059_b) {
         field_225671_s_[field_225670_r_].field_179059_b = p_227760_0_;
         GL11.glBindTexture(3553, p_227760_0_);
      }

   }

   public static void func_227647_a_(int p_227647_0_, int p_227647_1_, int p_227647_2_, int p_227647_3_, int p_227647_4_, int p_227647_5_, int p_227647_6_, int p_227647_7_, @Nullable IntBuffer p_227647_8_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      GL11.glTexImage2D(p_227647_0_, p_227647_1_, p_227647_2_, p_227647_3_, p_227647_4_, p_227647_5_, p_227647_6_, p_227647_7_, p_227647_8_);
   }

   public static void func_227646_a_(int p_227646_0_, int p_227646_1_, int p_227646_2_, int p_227646_3_, int p_227646_4_, int p_227646_5_, int p_227646_6_, int p_227646_7_, long p_227646_8_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      GL11.glTexSubImage2D(p_227646_0_, p_227646_1_, p_227646_2_, p_227646_3_, p_227646_4_, p_227646_5_, p_227646_6_, p_227646_7_, p_227646_8_);
   }

   public static void func_227649_a_(int p_227649_0_, int p_227649_1_, int p_227649_2_, int p_227649_3_, long p_227649_4_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL11.glGetTexImage(p_227649_0_, p_227649_1_, p_227649_2_, p_227649_3_, p_227649_4_);
   }

   @Deprecated
   public static void func_227762_u_(int p_227762_0_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      if (p_227762_0_ != field_225672_t_) {
         field_225672_t_ = p_227762_0_;
         GL11.glShadeModel(p_227762_0_);
      }

   }

   @Deprecated
   public static void func_227623_K_() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      field_225673_u_.func_179200_b();
   }

   @Deprecated
   public static void func_227624_L_() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      field_225673_u_.func_179198_a();
   }

   public static void func_227714_e_(int p_227714_0_, int p_227714_1_, int p_227714_2_, int p_227714_3_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      GlStateManager.Viewport.INSTANCE.field_199289_b = p_227714_0_;
      GlStateManager.Viewport.INSTANCE.field_199290_c = p_227714_1_;
      GlStateManager.Viewport.INSTANCE.field_199291_d = p_227714_2_;
      GlStateManager.Viewport.INSTANCE.field_199292_e = p_227714_3_;
      GL11.glViewport(p_227714_0_, p_227714_1_, p_227714_2_, p_227714_3_);
   }

   public static void func_227668_a_(boolean p_227668_0_, boolean p_227668_1_, boolean p_227668_2_, boolean p_227668_3_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      if (p_227668_0_ != field_225674_v_.field_179188_a || p_227668_1_ != field_225674_v_.field_179186_b || p_227668_2_ != field_225674_v_.field_179187_c || p_227668_3_ != field_225674_v_.field_179185_d) {
         field_225674_v_.field_179188_a = p_227668_0_;
         field_225674_v_.field_179186_b = p_227668_1_;
         field_225674_v_.field_179187_c = p_227668_2_;
         field_225674_v_.field_179185_d = p_227668_3_;
         GL11.glColorMask(p_227668_0_, p_227668_1_, p_227668_2_, p_227668_3_);
      }

   }

   public static void func_227705_d_(int p_227705_0_, int p_227705_1_, int p_227705_2_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      if (p_227705_0_ != field_225669_n_.field_179078_a.field_179081_a || p_227705_0_ != field_225669_n_.field_179078_a.field_212902_b || p_227705_0_ != field_225669_n_.field_179078_a.field_179080_c) {
         field_225669_n_.field_179078_a.field_179081_a = p_227705_0_;
         field_225669_n_.field_179078_a.field_212902_b = p_227705_1_;
         field_225669_n_.field_179078_a.field_179080_c = p_227705_2_;
         GL11.glStencilFunc(p_227705_0_, p_227705_1_, p_227705_2_);
      }

   }

   public static void func_227764_v_(int p_227764_0_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      if (p_227764_0_ != field_225669_n_.field_179076_b) {
         field_225669_n_.field_179076_b = p_227764_0_;
         GL11.glStencilMask(p_227764_0_);
      }

   }

   public static void func_227713_e_(int p_227713_0_, int p_227713_1_, int p_227713_2_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      if (p_227713_0_ != field_225669_n_.field_179077_c || p_227713_1_ != field_225669_n_.field_179074_d || p_227713_2_ != field_225669_n_.field_179075_e) {
         field_225669_n_.field_179077_c = p_227713_0_;
         field_225669_n_.field_179074_d = p_227713_1_;
         field_225669_n_.field_179075_e = p_227713_2_;
         GL11.glStencilOp(p_227713_0_, p_227713_1_, p_227713_2_);
      }

   }

   public static void func_227631_a_(double p_227631_0_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      if (p_227631_0_ != field_225668_m_.field_179205_a) {
         field_225668_m_.field_179205_a = p_227631_0_;
         GL11.glClearDepth(p_227631_0_);
      }

   }

   public static void func_227673_b_(float p_227673_0_, float p_227673_1_, float p_227673_2_, float p_227673_3_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      if (p_227673_0_ != field_225668_m_.field_179203_b.field_179195_a || p_227673_1_ != field_225668_m_.field_179203_b.field_179193_b || p_227673_2_ != field_225668_m_.field_179203_b.field_179194_c || p_227673_3_ != field_225668_m_.field_179203_b.field_179192_d) {
         field_225668_m_.field_179203_b.field_179195_a = p_227673_0_;
         field_225668_m_.field_179203_b.field_179193_b = p_227673_1_;
         field_225668_m_.field_179203_b.field_179194_c = p_227673_2_;
         field_225668_m_.field_179203_b.field_179192_d = p_227673_3_;
         GL11.glClearColor(p_227673_0_, p_227673_1_, p_227673_2_, p_227673_3_);
      }

   }

   public static void func_227766_w_(int p_227766_0_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      if (p_227766_0_ != field_225668_m_.field_212901_c) {
         field_225668_m_.field_212901_c = p_227766_0_;
         GL11.glClearStencil(p_227766_0_);
      }

   }

   public static void func_227658_a_(int p_227658_0_, boolean p_227658_1_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      GL11.glClear(p_227658_0_);
      if (p_227658_1_) {
         func_227629_Q_();
      }

   }

   @Deprecated
   public static void func_227768_x_(int p_227768_0_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      GL11.glMatrixMode(p_227768_0_);
   }

   @Deprecated
   public static void func_227625_M_() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      GL11.glLoadIdentity();
   }

   @Deprecated
   public static void func_227626_N_() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL11.glPushMatrix();
   }

   @Deprecated
   public static void func_227627_O_() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL11.glPopMatrix();
   }

   @Deprecated
   public static void func_227721_f_(int p_227721_0_, FloatBuffer p_227721_1_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL11.glGetFloatv(p_227721_0_, p_227721_1_);
   }

   @Deprecated
   public static void func_227633_a_(double p_227633_0_, double p_227633_2_, double p_227633_4_, double p_227633_6_, double p_227633_8_, double p_227633_10_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL11.glOrtho(p_227633_0_, p_227633_2_, p_227633_4_, p_227633_6_, p_227633_8_, p_227633_10_);
   }

   @Deprecated
   public static void func_227689_c_(float p_227689_0_, float p_227689_1_, float p_227689_2_, float p_227689_3_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL11.glRotatef(p_227689_0_, p_227689_1_, p_227689_2_, p_227689_3_);
   }

   @Deprecated
   public static void func_227672_b_(float p_227672_0_, float p_227672_1_, float p_227672_2_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL11.glScalef(p_227672_0_, p_227672_1_, p_227672_2_);
   }

   @Deprecated
   public static void func_227632_a_(double p_227632_0_, double p_227632_2_, double p_227632_4_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL11.glScaled(p_227632_0_, p_227632_2_, p_227632_4_);
   }

   @Deprecated
   public static void func_227688_c_(float p_227688_0_, float p_227688_1_, float p_227688_2_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL11.glTranslatef(p_227688_0_, p_227688_1_, p_227688_2_);
   }

   @Deprecated
   public static void func_227670_b_(double p_227670_0_, double p_227670_2_, double p_227670_4_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL11.glTranslated(p_227670_0_, p_227670_2_, p_227670_4_);
   }

   @Deprecated
   public static void func_227665_a_(FloatBuffer p_227665_0_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL11.glMultMatrixf(p_227665_0_);
   }

   @Deprecated
   public static void func_227699_c_(Matrix4f p_227699_0_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      p_227699_0_.write(field_225656_a_);
      field_225656_a_.rewind();
      func_227665_a_(field_225656_a_);
   }

   @Deprecated
   public static void func_227702_d_(float p_227702_0_, float p_227702_1_, float p_227702_2_, float p_227702_3_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      if (p_227702_0_ != field_225675_w_.field_179195_a || p_227702_1_ != field_225675_w_.field_179193_b || p_227702_2_ != field_225675_w_.field_179194_c || p_227702_3_ != field_225675_w_.field_179192_d) {
         field_225675_w_.field_179195_a = p_227702_0_;
         field_225675_w_.field_179193_b = p_227702_1_;
         field_225675_w_.field_179194_c = p_227702_2_;
         field_225675_w_.field_179192_d = p_227702_3_;
         GL11.glColor4f(p_227702_0_, p_227702_1_, p_227702_2_, p_227702_3_);
      }

   }

   @Deprecated
   public static void func_227628_P_() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      field_225675_w_.field_179195_a = -1.0F;
      field_225675_w_.field_179193_b = -1.0F;
      field_225675_w_.field_179194_c = -1.0F;
      field_225675_w_.field_179192_d = -1.0F;
   }

   @Deprecated
   public static void func_227652_a_(int p_227652_0_, int p_227652_1_, long p_227652_2_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL11.glNormalPointer(p_227652_0_, p_227652_1_, p_227652_2_);
   }

   @Deprecated
   public static void func_227650_a_(int p_227650_0_, int p_227650_1_, int p_227650_2_, long p_227650_3_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL11.glTexCoordPointer(p_227650_0_, p_227650_1_, p_227650_2_, p_227650_3_);
   }

   @Deprecated
   public static void func_227679_b_(int p_227679_0_, int p_227679_1_, int p_227679_2_, long p_227679_3_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL11.glVertexPointer(p_227679_0_, p_227679_1_, p_227679_2_, p_227679_3_);
   }

   @Deprecated
   public static void func_227694_c_(int p_227694_0_, int p_227694_1_, int p_227694_2_, long p_227694_3_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL11.glColorPointer(p_227694_0_, p_227694_1_, p_227694_2_, p_227694_3_);
   }

   public static void func_227651_a_(int p_227651_0_, int p_227651_1_, int p_227651_2_, boolean p_227651_3_, int p_227651_4_, long p_227651_5_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL20.glVertexAttribPointer(p_227651_0_, p_227651_1_, p_227651_2_, p_227651_3_, p_227651_4_, p_227651_5_);
   }

   @Deprecated
   public static void func_227770_y_(int p_227770_0_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL11.glEnableClientState(p_227770_0_);
   }

   @Deprecated
   public static void func_227772_z_(int p_227772_0_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL11.glDisableClientState(p_227772_0_);
   }

   public static void func_227606_A_(int p_227606_0_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL20.glEnableVertexAttribArray(p_227606_0_);
   }

   public static void func_227608_B_(int p_227608_0_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL20.glEnableVertexAttribArray(p_227608_0_);
   }

   public static void func_227719_f_(int p_227719_0_, int p_227719_1_, int p_227719_2_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL11.glDrawArrays(p_227719_0_, p_227719_1_, p_227719_2_);
   }

   public static void func_227701_d_(float p_227701_0_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL11.glLineWidth(p_227701_0_);
   }

   public static void func_227748_o_(int p_227748_0_, int p_227748_1_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      GL11.glPixelStorei(p_227748_0_, p_227748_1_);
   }

   public static void func_227675_b_(int p_227675_0_, float p_227675_1_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL11.glPixelTransferf(p_227675_0_, p_227675_1_);
   }

   public static void func_227648_a_(int p_227648_0_, int p_227648_1_, int p_227648_2_, int p_227648_3_, int p_227648_4_, int p_227648_5_, ByteBuffer p_227648_6_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL11.glReadPixels(p_227648_0_, p_227648_1_, p_227648_2_, p_227648_3_, p_227648_4_, p_227648_5_, p_227648_6_);
   }

   public static int func_227629_Q_() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      return GL11.glGetError();
   }

   public static String func_227610_C_(int p_227610_0_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      return GL11.glGetString(p_227610_0_);
   }

   public static int func_227612_D_(int p_227612_0_) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      return GL11.glGetInteger(p_227612_0_);
   }

   @Deprecated
   @OnlyIn(Dist.CLIENT)
   static class AlphaState {
      public final GlStateManager.BooleanState field_179208_a = new GlStateManager.BooleanState(3008);
      public int field_179206_b = 519;
      public float field_179207_c = -1.0F;

      private AlphaState() {
      }
   }

   @OnlyIn(Dist.CLIENT)
   static class BlendState {
      public final GlStateManager.BooleanState field_179213_a = new GlStateManager.BooleanState(3042);
      public int field_179211_b = 1;
      public int field_179212_c = 0;
      public int field_179209_d = 1;
      public int field_179210_e = 0;

      private BlendState() {
      }
   }

   @OnlyIn(Dist.CLIENT)
   static class BooleanState {
      private final int field_179202_a;
      private boolean field_179201_b;

      public BooleanState(int p_i50871_1_) {
         this.field_179202_a = p_i50871_1_;
      }

      public void func_179198_a() {
         this.func_179199_a(false);
      }

      public void func_179200_b() {
         this.func_179199_a(true);
      }

      public void func_179199_a(boolean p_179199_1_) {
         RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
         if (p_179199_1_ != this.field_179201_b) {
            this.field_179201_b = p_179199_1_;
            if (p_179199_1_) {
               GL11.glEnable(this.field_179202_a);
            } else {
               GL11.glDisable(this.field_179202_a);
            }
         }

      }
   }

   @OnlyIn(Dist.CLIENT)
   static class ClearState {
      public double field_179205_a = 1.0D;
      public final GlStateManager.Color field_179203_b = new GlStateManager.Color(0.0F, 0.0F, 0.0F, 0.0F);
      public int field_212901_c;

      private ClearState() {
      }
   }

   @Deprecated
   @OnlyIn(Dist.CLIENT)
   static class Color {
      public float field_179195_a = 1.0F;
      public float field_179193_b = 1.0F;
      public float field_179194_c = 1.0F;
      public float field_179192_d = 1.0F;

      public Color() {
         this(1.0F, 1.0F, 1.0F, 1.0F);
      }

      public Color(float p_i50869_1_, float p_i50869_2_, float p_i50869_3_, float p_i50869_4_) {
         this.field_179195_a = p_i50869_1_;
         this.field_179193_b = p_i50869_2_;
         this.field_179194_c = p_i50869_3_;
         this.field_179192_d = p_i50869_4_;
      }
   }

   @OnlyIn(Dist.CLIENT)
   static class ColorLogicState {
      public final GlStateManager.BooleanState field_179197_a = new GlStateManager.BooleanState(3058);
      public int field_179196_b = 5379;

      private ColorLogicState() {
      }
   }

   @OnlyIn(Dist.CLIENT)
   static class ColorMask {
      public boolean field_179188_a = true;
      public boolean field_179186_b = true;
      public boolean field_179187_c = true;
      public boolean field_179185_d = true;

      private ColorMask() {
      }
   }

   @Deprecated
   @OnlyIn(Dist.CLIENT)
   static class ColorMaterialState {
      public final GlStateManager.BooleanState field_179191_a = new GlStateManager.BooleanState(2903);
      public int field_179189_b = 1032;
      public int field_179190_c = 5634;

      private ColorMaterialState() {
      }
   }

   @OnlyIn(Dist.CLIENT)
   static class CullState {
      public final GlStateManager.BooleanState field_179054_a = new GlStateManager.BooleanState(2884);
      public int field_179053_b = 1029;

      private CullState() {
      }
   }

   @OnlyIn(Dist.CLIENT)
   static class DepthState {
      public final GlStateManager.BooleanState field_179052_a = new GlStateManager.BooleanState(2929);
      public boolean field_179050_b = true;
      public int field_179051_c = 513;

      private DepthState() {
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static enum DestFactor {
      CONSTANT_ALPHA(32771),
      CONSTANT_COLOR(32769),
      DST_ALPHA(772),
      DST_COLOR(774),
      ONE(1),
      ONE_MINUS_CONSTANT_ALPHA(32772),
      ONE_MINUS_CONSTANT_COLOR(32770),
      ONE_MINUS_DST_ALPHA(773),
      ONE_MINUS_DST_COLOR(775),
      ONE_MINUS_SRC_ALPHA(771),
      ONE_MINUS_SRC_COLOR(769),
      SRC_ALPHA(770),
      SRC_COLOR(768),
      ZERO(0);

      public final int field_225654_o_;

      private DestFactor(int p_i51106_3_) {
         this.field_225654_o_ = p_i51106_3_;
      }
   }

   @Deprecated
   @OnlyIn(Dist.CLIENT)
   public static enum FogMode {
      LINEAR(9729),
      EXP(2048),
      EXP2(2049);

      public final int field_187351_d;

      private FogMode(int p_i50862_3_) {
         this.field_187351_d = p_i50862_3_;
      }
   }

   @Deprecated
   @OnlyIn(Dist.CLIENT)
   static class FogState {
      public final GlStateManager.BooleanState field_179049_a = new GlStateManager.BooleanState(2912);
      public int field_179047_b = 2048;
      public float field_179048_c = 1.0F;
      public float field_179045_d;
      public float field_179046_e = 1.0F;

      private FogState() {
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static enum FramebufferExtension {
      BASE,
      ARB,
      EXT;
   }

   @OnlyIn(Dist.CLIENT)
   public static enum LogicOp {
      AND(5377),
      AND_INVERTED(5380),
      AND_REVERSE(5378),
      CLEAR(5376),
      COPY(5379),
      COPY_INVERTED(5388),
      EQUIV(5385),
      INVERT(5386),
      NAND(5390),
      NOOP(5381),
      NOR(5384),
      OR(5383),
      OR_INVERTED(5389),
      OR_REVERSE(5387),
      SET(5391),
      XOR(5382);

      public final int field_187370_q;

      private LogicOp(int p_i50860_3_) {
         this.field_187370_q = p_i50860_3_;
      }
   }

   @OnlyIn(Dist.CLIENT)
   static class PolygonOffsetState {
      public final GlStateManager.BooleanState field_179044_a = new GlStateManager.BooleanState(32823);
      public final GlStateManager.BooleanState field_179042_b = new GlStateManager.BooleanState(10754);
      public float field_179043_c;
      public float field_179041_d;

      private PolygonOffsetState() {
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static enum SourceFactor {
      CONSTANT_ALPHA(32771),
      CONSTANT_COLOR(32769),
      DST_ALPHA(772),
      DST_COLOR(774),
      ONE(1),
      ONE_MINUS_CONSTANT_ALPHA(32772),
      ONE_MINUS_CONSTANT_COLOR(32770),
      ONE_MINUS_DST_ALPHA(773),
      ONE_MINUS_DST_COLOR(775),
      ONE_MINUS_SRC_ALPHA(771),
      ONE_MINUS_SRC_COLOR(769),
      SRC_ALPHA(770),
      SRC_ALPHA_SATURATE(776),
      SRC_COLOR(768),
      ZERO(0);

      public final int field_225655_p_;

      private SourceFactor(int p_i50328_3_) {
         this.field_225655_p_ = p_i50328_3_;
      }
   }

   @OnlyIn(Dist.CLIENT)
   static class StencilFunc {
      public int field_179081_a = 519;
      public int field_212902_b;
      public int field_179080_c = -1;

      private StencilFunc() {
      }
   }

   @OnlyIn(Dist.CLIENT)
   static class StencilState {
      public final GlStateManager.StencilFunc field_179078_a = new GlStateManager.StencilFunc();
      public int field_179076_b = -1;
      public int field_179077_c = 7680;
      public int field_179074_d = 7680;
      public int field_179075_e = 7680;

      private StencilState() {
      }
   }

   @Deprecated
   @OnlyIn(Dist.CLIENT)
   public static enum TexGen {
      S,
      T,
      R,
      Q;
   }

   @Deprecated
   @OnlyIn(Dist.CLIENT)
   static class TexGenCoord {
      public final GlStateManager.BooleanState field_179067_a;
      public final int field_179065_b;
      public int field_179066_c = -1;

      public TexGenCoord(int p_i50853_1_, int p_i50853_2_) {
         this.field_179065_b = p_i50853_1_;
         this.field_179067_a = new GlStateManager.BooleanState(p_i50853_2_);
      }
   }

   @Deprecated
   @OnlyIn(Dist.CLIENT)
   static class TexGenState {
      public final GlStateManager.TexGenCoord field_179064_a = new GlStateManager.TexGenCoord(8192, 3168);
      public final GlStateManager.TexGenCoord field_179062_b = new GlStateManager.TexGenCoord(8193, 3169);
      public final GlStateManager.TexGenCoord field_179063_c = new GlStateManager.TexGenCoord(8194, 3170);
      public final GlStateManager.TexGenCoord field_179061_d = new GlStateManager.TexGenCoord(8195, 3171);

      private TexGenState() {
      }
   }

   @OnlyIn(Dist.CLIENT)
   static class TextureState {
      public final GlStateManager.BooleanState field_179060_a = new GlStateManager.BooleanState(3553);
      public int field_179059_b;

      private TextureState() {
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static enum Viewport {
      INSTANCE;

      protected int field_199289_b;
      protected int field_199290_c;
      protected int field_199291_d;
      protected int field_199292_e;
   }
}