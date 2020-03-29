package net.minecraft.client.renderer.model;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import java.util.Map;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.SpriteMap;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.ReloadListener;
import net.minecraft.fluid.IFluidState;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ModelManager extends ReloadListener<ModelBakery> implements AutoCloseable {
   private Map<ResourceLocation, IBakedModel> modelRegistry = new java.util.HashMap<>();
   private SpriteMap field_229352_b_;
   private final BlockModelShapes modelProvider;
   private final TextureManager field_229353_d_;
   private final BlockColors field_224743_d;
   private int field_229354_f_;
   private IBakedModel defaultModel;
   private Object2IntMap<BlockState> field_224744_f;

   public ModelManager(TextureManager p_i226057_1_, BlockColors p_i226057_2_, int p_i226057_3_) {
      this.field_229353_d_ = p_i226057_1_;
      this.field_224743_d = p_i226057_2_;
      this.field_229354_f_ = p_i226057_3_;
      this.modelProvider = new BlockModelShapes(this);
   }

   public IBakedModel getModel(ResourceLocation modelLocation) {
      return this.modelRegistry.getOrDefault(modelLocation, this.defaultModel);
   }

   public IBakedModel getModel(ModelResourceLocation modelLocation) {
      return this.modelRegistry.getOrDefault(modelLocation, this.defaultModel);
   }

   public IBakedModel getMissingModel() {
      return this.defaultModel;
   }

   public BlockModelShapes getBlockModelShapes() {
      return this.modelProvider;
   }

   /**
    * Performs any reloading that can be done off-thread, such as file IO
    */
   protected ModelBakery prepare(IResourceManager resourceManagerIn, IProfiler profilerIn) {
      profilerIn.startTick();
      net.minecraftforge.client.model.ModelLoader modelbakery = new net.minecraftforge.client.model.ModelLoader(resourceManagerIn, this.field_224743_d, profilerIn, this.field_229354_f_);
      profilerIn.endTick();
      return modelbakery;
   }

   protected void apply(ModelBakery splashList, IResourceManager resourceManagerIn, IProfiler profilerIn) {
      profilerIn.startTick();
      profilerIn.startSection("upload");
      if (this.field_229352_b_ != null) {
         this.field_229352_b_.close();
      }

      this.field_229352_b_ = splashList.func_229333_a_(this.field_229353_d_, profilerIn);
      this.modelRegistry = splashList.func_217846_a();
      this.field_224744_f = splashList.func_225354_b();
      this.defaultModel = this.modelRegistry.get(ModelBakery.MODEL_MISSING);
      net.minecraftforge.client.ForgeHooksClient.onModelBake(this, this.modelRegistry, (net.minecraftforge.client.model.ModelLoader) splashList);
      profilerIn.endStartSection("cache");
      this.modelProvider.reloadModels();
      profilerIn.endSection();
      profilerIn.endTick();
   }

   public boolean func_224742_a(BlockState p_224742_1_, BlockState p_224742_2_) {
      if (p_224742_1_ == p_224742_2_) {
         return false;
      } else {
         int i = this.field_224744_f.getInt(p_224742_1_);
         if (i != -1) {
            int j = this.field_224744_f.getInt(p_224742_2_);
            if (i == j) {
               IFluidState ifluidstate = p_224742_1_.getFluidState();
               IFluidState ifluidstate1 = p_224742_2_.getFluidState();
               return ifluidstate != ifluidstate1;
            }
         }

         return true;
      }
   }

   public AtlasTexture func_229356_a_(ResourceLocation p_229356_1_) {
      if (this.field_229352_b_ == null) throw new RuntimeException("func_229356_a_ called too early!");
      return this.field_229352_b_.func_229152_a_(p_229356_1_);
   }

   public void close() {
      this.field_229352_b_.close();
   }

   public void func_229355_a_(int p_229355_1_) {
      this.field_229354_f_ = p_229355_1_;
   }

   // TODO
   //@Override
   public net.minecraftforge.resource.IResourceType getResourceType() {
      return net.minecraftforge.resource.VanillaResourceType.MODELS;
   }
}