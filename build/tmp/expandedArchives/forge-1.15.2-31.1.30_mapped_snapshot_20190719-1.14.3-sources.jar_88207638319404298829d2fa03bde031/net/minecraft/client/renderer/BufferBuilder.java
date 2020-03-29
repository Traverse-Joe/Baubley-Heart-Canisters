package net.minecraft.client.renderer;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.primitives.Floats;
import com.mojang.blaze3d.vertex.DefaultColorVertexBuilder;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mojang.blaze3d.vertex.IVertexConsumer;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.ints.IntArrays;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.BitSet;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class BufferBuilder extends DefaultColorVertexBuilder implements IVertexConsumer {
   private static final Logger LOGGER = LogManager.getLogger();
   private ByteBuffer byteBuffer;
   private final List<BufferBuilder.DrawState> field_227821_i_ = Lists.newArrayList();
   private int field_227822_j_ = 0;
   private int field_227823_k_ = 0;
   private int field_227824_l_ = 0;
   private int field_227825_m_ = 0;
   private int vertexCount;
   @Nullable
   private VertexFormatElement vertexFormatElement;
   private int vertexFormatIndex;
   private int drawMode;
   private VertexFormat vertexFormat;
   private boolean field_227826_s_;
   private boolean field_227827_t_;
   private boolean isDrawing;

   public BufferBuilder(int bufferSizeIn) {
      this.byteBuffer = GLAllocation.createDirectByteBuffer(bufferSizeIn * 4);
   }

   protected void func_227831_b_() {
      this.growBuffer(this.vertexFormat.getSize());
   }

   private void growBuffer(int increaseAmount) {
      if (this.field_227824_l_ + increaseAmount > this.byteBuffer.capacity()) {
         int i = this.byteBuffer.capacity();
         int j = i + func_216566_c(increaseAmount);
         LOGGER.debug("Needed to grow BufferBuilder buffer: Old size {} bytes, new size {} bytes.", i, j);
         ByteBuffer bytebuffer = GLAllocation.createDirectByteBuffer(j);
         this.byteBuffer.position(0);
         bytebuffer.put(this.byteBuffer);
         bytebuffer.rewind();
         this.byteBuffer = bytebuffer;
      }
   }

   private static int func_216566_c(int p_216566_0_) {
      int i = 2097152;
      if (p_216566_0_ == 0) {
         return i;
      } else {
         if (p_216566_0_ < 0) {
            i *= -1;
         }

         int j = p_216566_0_ % i;
         return j == 0 ? p_216566_0_ : p_216566_0_ + i - j;
      }
   }

   public void sortVertexData(float cameraX, float cameraY, float cameraZ) {
      this.byteBuffer.clear();
      FloatBuffer floatbuffer = this.byteBuffer.asFloatBuffer();
      int i = this.vertexCount / 4;
      float[] afloat = new float[i];

      for(int j = 0; j < i; ++j) {
         afloat[j] = getDistanceSq(floatbuffer, cameraX, cameraY, cameraZ, this.vertexFormat.getIntegerSize(), this.field_227823_k_ / 4 + j * this.vertexFormat.getSize());
      }

      int[] aint = new int[i];

      for(int k = 0; k < aint.length; aint[k] = k++) {
         ;
      }

      IntArrays.mergeSort(aint, (p_227830_1_, p_227830_2_) -> {
         return Floats.compare(afloat[p_227830_2_], afloat[p_227830_1_]);
      });
      BitSet bitset = new BitSet();
      FloatBuffer floatbuffer1 = GLAllocation.createDirectFloatBuffer(this.vertexFormat.getIntegerSize() * 4);

      for(int l = bitset.nextClearBit(0); l < aint.length; l = bitset.nextClearBit(l + 1)) {
         int i1 = aint[l];
         if (i1 != l) {
            this.func_227829_a_(floatbuffer, i1);
            floatbuffer1.clear();
            floatbuffer1.put(floatbuffer);
            int j1 = i1;

            for(int k1 = aint[i1]; j1 != l; k1 = aint[k1]) {
               this.func_227829_a_(floatbuffer, k1);
               FloatBuffer floatbuffer2 = floatbuffer.slice();
               this.func_227829_a_(floatbuffer, j1);
               floatbuffer.put(floatbuffer2);
               bitset.set(j1);
               j1 = k1;
            }

            this.func_227829_a_(floatbuffer, l);
            floatbuffer1.flip();
            floatbuffer.put(floatbuffer1);
         }

         bitset.set(l);
      }
   }

   private void func_227829_a_(FloatBuffer p_227829_1_, int p_227829_2_) {
      int i = this.vertexFormat.getIntegerSize() * 4;
      p_227829_1_.limit(this.field_227823_k_ / 4 + (p_227829_2_ + 1) * i);
      p_227829_1_.position(this.field_227823_k_ / 4 + p_227829_2_ * i);
   }

   public BufferBuilder.State getVertexState() {
      this.byteBuffer.limit(this.field_227824_l_);
      this.byteBuffer.position(this.field_227823_k_);
      ByteBuffer bytebuffer = ByteBuffer.allocate(this.vertexCount * this.vertexFormat.getSize());
      bytebuffer.put(this.byteBuffer);
      this.byteBuffer.clear();
      return new BufferBuilder.State(bytebuffer, this.vertexFormat);
   }

   private static float getDistanceSq(FloatBuffer floatBufferIn, float x, float y, float z, int integerSize, int offset) {
      float f = floatBufferIn.get(offset + integerSize * 0 + 0);
      float f1 = floatBufferIn.get(offset + integerSize * 0 + 1);
      float f2 = floatBufferIn.get(offset + integerSize * 0 + 2);
      float f3 = floatBufferIn.get(offset + integerSize * 1 + 0);
      float f4 = floatBufferIn.get(offset + integerSize * 1 + 1);
      float f5 = floatBufferIn.get(offset + integerSize * 1 + 2);
      float f6 = floatBufferIn.get(offset + integerSize * 2 + 0);
      float f7 = floatBufferIn.get(offset + integerSize * 2 + 1);
      float f8 = floatBufferIn.get(offset + integerSize * 2 + 2);
      float f9 = floatBufferIn.get(offset + integerSize * 3 + 0);
      float f10 = floatBufferIn.get(offset + integerSize * 3 + 1);
      float f11 = floatBufferIn.get(offset + integerSize * 3 + 2);
      float f12 = (f + f3 + f6 + f9) * 0.25F - x;
      float f13 = (f1 + f4 + f7 + f10) * 0.25F - y;
      float f14 = (f2 + f5 + f8 + f11) * 0.25F - z;
      return f12 * f12 + f13 * f13 + f14 * f14;
   }

   public void setVertexState(BufferBuilder.State state) {
      state.field_227841_a_.clear();
      int i = state.field_227841_a_.capacity();
      this.growBuffer(i);
      this.byteBuffer.limit(this.byteBuffer.capacity());
      this.byteBuffer.position(this.field_227823_k_);
      this.byteBuffer.put(state.field_227841_a_);
      this.byteBuffer.clear();
      VertexFormat vertexformat = state.stateVertexFormat;
      this.func_227828_a_(vertexformat);
      this.vertexCount = i / vertexformat.getSize();
      this.field_227824_l_ = this.field_227823_k_ + this.vertexCount * vertexformat.getSize();
   }

   public void begin(int glMode, VertexFormat format) {
      if (this.isDrawing) {
         throw new IllegalStateException("Already building!");
      } else {
         this.isDrawing = true;
         this.drawMode = glMode;
         this.func_227828_a_(format);
         this.vertexFormatElement = format.func_227894_c_().get(0);
         this.vertexFormatIndex = 0;
         this.byteBuffer.clear();
      }
   }

   private void func_227828_a_(VertexFormat p_227828_1_) {
      if (this.vertexFormat != p_227828_1_) {
         this.vertexFormat = p_227828_1_;
         boolean flag = p_227828_1_ == DefaultVertexFormats.field_227849_i_;
         boolean flag1 = p_227828_1_ == DefaultVertexFormats.BLOCK;
         this.field_227826_s_ = flag || flag1;
         this.field_227827_t_ = flag;
      }
   }

   public void finishDrawing() {
      if (!this.isDrawing) {
         throw new IllegalStateException("Not building!");
      } else {
         this.isDrawing = false;
         this.field_227821_i_.add(new BufferBuilder.DrawState(this.vertexFormat, this.vertexCount, this.drawMode));
         this.field_227823_k_ += this.vertexCount * this.vertexFormat.getSize();
         this.vertexCount = 0;
         this.vertexFormatElement = null;
         this.vertexFormatIndex = 0;
      }
   }

   public void func_225589_a_(int p_225589_1_, byte p_225589_2_) {
      this.byteBuffer.put(this.field_227824_l_ + p_225589_1_, p_225589_2_);
   }

   public void func_225591_a_(int p_225591_1_, short p_225591_2_) {
      this.byteBuffer.putShort(this.field_227824_l_ + p_225591_1_, p_225591_2_);
   }

   public void func_225590_a_(int p_225590_1_, float p_225590_2_) {
      this.byteBuffer.putFloat(this.field_227824_l_ + p_225590_1_, p_225590_2_);
   }

   public void endVertex() {
      if (this.vertexFormatIndex != 0) {
         throw new IllegalStateException("Not filled all elements of the vertex");
      } else {
         ++this.vertexCount;
         this.func_227831_b_();
      }
   }

   public void nextVertexFormatIndex() {
      ImmutableList<VertexFormatElement> immutablelist = this.vertexFormat.func_227894_c_();
      this.vertexFormatIndex = (this.vertexFormatIndex + 1) % immutablelist.size();
      this.field_227824_l_ += this.vertexFormatElement.getSize();
      VertexFormatElement vertexformatelement = immutablelist.get(this.vertexFormatIndex);
      this.vertexFormatElement = vertexformatelement;
      if (vertexformatelement.getUsage() == VertexFormatElement.Usage.PADDING) {
         this.nextVertexFormatIndex();
      }

      if (this.field_227854_a_ && this.vertexFormatElement.getUsage() == VertexFormatElement.Usage.COLOR) {
         IVertexConsumer.super.func_225586_a_(this.field_227855_b_, this.field_227856_c_, this.field_227857_d_, this.field_227858_e_);
      }

   }

   public IVertexBuilder func_225586_a_(int p_225586_1_, int p_225586_2_, int p_225586_3_, int p_225586_4_) {
      if (this.field_227854_a_) {
         throw new IllegalStateException();
      } else {
         return IVertexConsumer.super.func_225586_a_(p_225586_1_, p_225586_2_, p_225586_3_, p_225586_4_);
      }
   }

   public void func_225588_a_(float p_225588_1_, float p_225588_2_, float p_225588_3_, float p_225588_4_, float p_225588_5_, float p_225588_6_, float p_225588_7_, float p_225588_8_, float p_225588_9_, int p_225588_10_, int p_225588_11_, float p_225588_12_, float p_225588_13_, float p_225588_14_) {
      if (this.field_227854_a_) {
         throw new IllegalStateException();
      } else if (this.field_227826_s_) {
         this.func_225590_a_(0, p_225588_1_);
         this.func_225590_a_(4, p_225588_2_);
         this.func_225590_a_(8, p_225588_3_);
         this.func_225589_a_(12, (byte)((int)(p_225588_4_ * 255.0F)));
         this.func_225589_a_(13, (byte)((int)(p_225588_5_ * 255.0F)));
         this.func_225589_a_(14, (byte)((int)(p_225588_6_ * 255.0F)));
         this.func_225589_a_(15, (byte)((int)(p_225588_7_ * 255.0F)));
         this.func_225590_a_(16, p_225588_8_);
         this.func_225590_a_(20, p_225588_9_);
         int i;
         if (this.field_227827_t_) {
            this.func_225591_a_(24, (short)(p_225588_10_ & '\uffff'));
            this.func_225591_a_(26, (short)(p_225588_10_ >> 16 & '\uffff'));
            i = 28;
         } else {
            i = 24;
         }

         this.func_225591_a_(i + 0, (short)(p_225588_11_ & '\uffff'));
         this.func_225591_a_(i + 2, (short)(p_225588_11_ >> 16 & '\uffff'));
         this.func_225589_a_(i + 4, IVertexConsumer.func_227846_a_(p_225588_12_));
         this.func_225589_a_(i + 5, IVertexConsumer.func_227846_a_(p_225588_13_));
         this.func_225589_a_(i + 6, IVertexConsumer.func_227846_a_(p_225588_14_));
         this.field_227824_l_ += i + 8;
         this.endVertex();
      } else {
         super.func_225588_a_(p_225588_1_, p_225588_2_, p_225588_3_, p_225588_4_, p_225588_5_, p_225588_6_, p_225588_7_, p_225588_8_, p_225588_9_, p_225588_10_, p_225588_11_, p_225588_12_, p_225588_13_, p_225588_14_);
      }
   }

   public Pair<BufferBuilder.DrawState, ByteBuffer> func_227832_f_() {
      BufferBuilder.DrawState bufferbuilder$drawstate = this.field_227821_i_.get(this.field_227822_j_++);
      this.byteBuffer.position(this.field_227825_m_);
      this.field_227825_m_ += bufferbuilder$drawstate.func_227839_b_() * bufferbuilder$drawstate.func_227838_a_().getSize();
      this.byteBuffer.limit(this.field_227825_m_);
      if (this.field_227822_j_ == this.field_227821_i_.size() && this.vertexCount == 0) {
         this.reset();
      }

      ByteBuffer bytebuffer = this.byteBuffer.slice();
      this.byteBuffer.clear();
      return Pair.of(bufferbuilder$drawstate, bytebuffer);
   }

   public void reset() {
      if (this.field_227823_k_ != this.field_227825_m_) {
         LOGGER.warn("Bytes mismatch " + this.field_227823_k_ + " " + this.field_227825_m_);
      }

      this.func_227833_h_();
   }

   public void func_227833_h_() {
      this.field_227823_k_ = 0;
      this.field_227825_m_ = 0;
      this.field_227824_l_ = 0;
      this.field_227821_i_.clear();
      this.field_227822_j_ = 0;
   }

   public VertexFormatElement func_225592_i_() {
      if (this.vertexFormatElement == null) {
         throw new IllegalStateException("BufferBuilder not started");
      } else {
         return this.vertexFormatElement;
      }
   }

   public boolean func_227834_j_() {
      return this.isDrawing;
   }

   @OnlyIn(Dist.CLIENT)
   public static final class DrawState {
      private final VertexFormat field_227835_a_;
      private final int field_227836_b_;
      private final int field_227837_c_;

      private DrawState(VertexFormat p_i225905_1_, int p_i225905_2_, int p_i225905_3_) {
         this.field_227835_a_ = p_i225905_1_;
         this.field_227836_b_ = p_i225905_2_;
         this.field_227837_c_ = p_i225905_3_;
      }

      public VertexFormat func_227838_a_() {
         return this.field_227835_a_;
      }

      public int func_227839_b_() {
         return this.field_227836_b_;
      }

      public int func_227840_c_() {
         return this.field_227837_c_;
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static class State {
      private final ByteBuffer field_227841_a_;
      private final VertexFormat stateVertexFormat;

      private State(ByteBuffer p_i225907_1_, VertexFormat p_i225907_2_) {
         this.field_227841_a_ = p_i225907_1_;
         this.stateVertexFormat = p_i225907_2_;
      }
   }

   public void putBulkData(ByteBuffer buffer) {
      growBuffer(buffer.limit() + this.vertexFormat.getSize());
      this.byteBuffer.position(this.vertexCount * this.vertexFormat.getSize());
      this.byteBuffer.put(buffer);
      this.vertexCount += buffer.limit() / this.vertexFormat.getSize();
   }

   // Forge start
   public VertexFormat getVertexFormat() { return this.vertexFormat; }
}