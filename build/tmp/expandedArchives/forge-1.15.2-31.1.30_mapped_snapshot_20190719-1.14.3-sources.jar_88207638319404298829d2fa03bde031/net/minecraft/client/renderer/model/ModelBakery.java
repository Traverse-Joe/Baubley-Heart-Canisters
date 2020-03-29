package net.minecraft.client.renderer.model;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.TransformationMatrix;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.model.multipart.Multipart;
import net.minecraft.client.renderer.model.multipart.Selector;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.MissingTextureSprite;
import net.minecraft.client.renderer.texture.SpriteMap;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.tileentity.BellTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.ConduitTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.EnchantmentTableTileEntityRenderer;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.tuple.Triple;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class ModelBakery {
   public static final Material LOCATION_FIRE_0 = new Material(AtlasTexture.LOCATION_BLOCKS_TEXTURE, new ResourceLocation("block/fire_0"));
   public static final Material LOCATION_FIRE_1 = new Material(AtlasTexture.LOCATION_BLOCKS_TEXTURE, new ResourceLocation("block/fire_1"));
   public static final Material LOCATION_LAVA_FLOW = new Material(AtlasTexture.LOCATION_BLOCKS_TEXTURE, new ResourceLocation("block/lava_flow"));
   public static final Material LOCATION_WATER_FLOW = new Material(AtlasTexture.LOCATION_BLOCKS_TEXTURE, new ResourceLocation("block/water_flow"));
   public static final Material LOCATION_WATER_OVERLAY = new Material(AtlasTexture.LOCATION_BLOCKS_TEXTURE, new ResourceLocation("block/water_overlay"));
   public static final Material field_229315_f_ = new Material(AtlasTexture.LOCATION_BLOCKS_TEXTURE, new ResourceLocation("entity/banner_base"));
   public static final Material field_229316_g_ = new Material(AtlasTexture.LOCATION_BLOCKS_TEXTURE, new ResourceLocation("entity/shield_base"));
   public static final Material field_229317_h_ = new Material(AtlasTexture.LOCATION_BLOCKS_TEXTURE, new ResourceLocation("entity/shield_base_nopattern"));
   public static final List<ResourceLocation> field_229318_i_ = IntStream.range(0, 10).mapToObj((p_229323_0_) -> {
      return new ResourceLocation("block/destroy_stage_" + p_229323_0_);
   }).collect(Collectors.toList());
   public static final List<ResourceLocation> field_229319_j_ = field_229318_i_.stream().map((p_229351_0_) -> {
      return new ResourceLocation("textures/" + p_229351_0_.getPath() + ".png");
   }).collect(Collectors.toList());
   public static final List<RenderType> field_229320_k_ = field_229319_j_.stream().map(RenderType::func_228656_k_).collect(Collectors.toList());
   protected static final Set<Material> LOCATIONS_BUILTIN_TEXTURES = Util.make(Sets.newHashSet(), (p_229337_0_) -> {
      p_229337_0_.add(LOCATION_WATER_FLOW);
      p_229337_0_.add(LOCATION_LAVA_FLOW);
      p_229337_0_.add(LOCATION_WATER_OVERLAY);
      p_229337_0_.add(LOCATION_FIRE_0);
      p_229337_0_.add(LOCATION_FIRE_1);
      p_229337_0_.add(BellTileEntityRenderer.field_217653_c);
      p_229337_0_.add(ConduitTileEntityRenderer.BASE_TEXTURE);
      p_229337_0_.add(ConduitTileEntityRenderer.CAGE_TEXTURE);
      p_229337_0_.add(ConduitTileEntityRenderer.WIND_TEXTURE);
      p_229337_0_.add(ConduitTileEntityRenderer.VERTICAL_WIND_TEXTURE);
      p_229337_0_.add(ConduitTileEntityRenderer.OPEN_EYE_TEXTURE);
      p_229337_0_.add(ConduitTileEntityRenderer.CLOSED_EYE_TEXTURE);
      p_229337_0_.add(EnchantmentTableTileEntityRenderer.TEXTURE_BOOK);
      p_229337_0_.add(field_229315_f_);
      p_229337_0_.add(field_229316_g_);
      p_229337_0_.add(field_229317_h_);

      for(ResourceLocation resourcelocation : field_229318_i_) {
         p_229337_0_.add(new Material(AtlasTexture.LOCATION_BLOCKS_TEXTURE, resourcelocation));
      }

      p_229337_0_.add(new Material(AtlasTexture.LOCATION_BLOCKS_TEXTURE, PlayerContainer.field_226616_d_));
      p_229337_0_.add(new Material(AtlasTexture.LOCATION_BLOCKS_TEXTURE, PlayerContainer.field_226617_e_));
      p_229337_0_.add(new Material(AtlasTexture.LOCATION_BLOCKS_TEXTURE, PlayerContainer.field_226618_f_));
      p_229337_0_.add(new Material(AtlasTexture.LOCATION_BLOCKS_TEXTURE, PlayerContainer.field_226619_g_));
      p_229337_0_.add(new Material(AtlasTexture.LOCATION_BLOCKS_TEXTURE, PlayerContainer.field_226620_h_));
      Atlases.func_228775_a_(p_229337_0_::add);
   });
   private static final Logger LOGGER = LogManager.getLogger();
   public static final ModelResourceLocation MODEL_MISSING = new ModelResourceLocation("builtin/missing", "missing");
   private static final String field_229321_r_ = MODEL_MISSING.toString();
   @VisibleForTesting
   public static final String MISSING_MODEL_MESH = ("{    'textures': {       'particle': '" + MissingTextureSprite.getLocation().getPath() + "',       'missingno': '" + MissingTextureSprite.getLocation().getPath() + "'    },    'elements': [         {  'from': [ 0, 0, 0 ],            'to': [ 16, 16, 16 ],            'faces': {                'down':  { 'uv': [ 0, 0, 16, 16 ], 'cullface': 'down',  'texture': '#missingno' },                'up':    { 'uv': [ 0, 0, 16, 16 ], 'cullface': 'up',    'texture': '#missingno' },                'north': { 'uv': [ 0, 0, 16, 16 ], 'cullface': 'north', 'texture': '#missingno' },                'south': { 'uv': [ 0, 0, 16, 16 ], 'cullface': 'south', 'texture': '#missingno' },                'west':  { 'uv': [ 0, 0, 16, 16 ], 'cullface': 'west',  'texture': '#missingno' },                'east':  { 'uv': [ 0, 0, 16, 16 ], 'cullface': 'east',  'texture': '#missingno' }            }        }    ]}").replace('\'', '"');
   private static final Map<String, String> BUILT_IN_MODELS = Maps.newHashMap(ImmutableMap.of("missing", MISSING_MODEL_MESH));
   private static final Splitter SPLITTER_COMMA = Splitter.on(',');
   private static final Splitter EQUALS_SPLITTER = Splitter.on('=').limit(2);
   public static final BlockModel MODEL_GENERATED = Util.make(BlockModel.deserialize("{\"gui_light\": \"front\"}"), (p_229347_0_) -> {
      p_229347_0_.name = "generation marker";
   });
   public static final BlockModel MODEL_ENTITY = Util.make(BlockModel.deserialize("{\"gui_light\": \"side\"}"), (p_229332_0_) -> {
      p_229332_0_.name = "block entity marker";
   });
   private static final StateContainer<Block, BlockState> STATE_CONTAINER_ITEM_FRAME = (new StateContainer.Builder<Block, BlockState>(Blocks.AIR)).add(BooleanProperty.create("map")).create(BlockState::new);
   private static final ItemModelGenerator field_217854_z = new ItemModelGenerator();
   private static final Map<ResourceLocation, StateContainer<Block, BlockState>> STATE_CONTAINER_OVERRIDES = ImmutableMap.of(new ResourceLocation("item_frame"), STATE_CONTAINER_ITEM_FRAME);
   protected final IResourceManager resourceManager;
   @Nullable
   private SpriteMap field_229322_z_;
   private final BlockColors field_225365_D;
   private final Set<ResourceLocation> field_217848_D = Sets.newHashSet();
   private final BlockModelDefinition.ContainerHolder containerHolder = new BlockModelDefinition.ContainerHolder();
   private final Map<ResourceLocation, IUnbakedModel> field_217849_F = Maps.newHashMap();
   private final Map<Triple<ResourceLocation, TransformationMatrix, Boolean>, IBakedModel> field_217850_G = Maps.newHashMap();
   private final Map<ResourceLocation, IUnbakedModel> field_217851_H = Maps.newHashMap();
   private final Map<ResourceLocation, IBakedModel> field_217852_I = Maps.newHashMap();
   private Map<ResourceLocation, Pair<AtlasTexture, AtlasTexture.SheetData>> field_217853_J;
   private int field_225366_L = 1;
   private final Object2IntMap<BlockState> field_225367_M = Util.make(new Object2IntOpenHashMap<>(), (p_229336_0_) -> {
      p_229336_0_.defaultReturnValue(-1);
   });

   public ModelBakery(IResourceManager p_i226056_1_, BlockColors p_i226056_2_, IProfiler p_i226056_3_, int p_i226056_4_)
   {
      this(p_i226056_1_, p_i226056_2_, true);
      processLoading(p_i226056_3_, p_i226056_4_);
   }

   protected ModelBakery(IResourceManager p_i226056_1_, BlockColors p_i226056_2_, boolean vanillaBakery)
   {
      this.resourceManager = p_i226056_1_;
      this.field_225365_D = p_i226056_2_;
   }

   protected void processLoading(IProfiler p_i226056_3_, int p_i226056_4_) {
      p_i226056_3_.startSection("missing_model");

      try {
         this.field_217849_F.put(MODEL_MISSING, this.loadModel(MODEL_MISSING));
         this.func_217843_a(MODEL_MISSING);
      } catch (IOException ioexception) {
         LOGGER.error("Error loading missing model, should never happen :(", (Throwable)ioexception);
         throw new RuntimeException(ioexception);
      }

      p_i226056_3_.endStartSection("static_definitions");
      STATE_CONTAINER_OVERRIDES.forEach((p_229344_1_, p_229344_2_) -> {
         p_229344_2_.getValidStates().forEach((p_229343_2_) -> {
            this.func_217843_a(BlockModelShapes.getModelLocation(p_229344_1_, p_229343_2_));
         });
      });
      p_i226056_3_.endStartSection("blocks");

      for(Block block : Registry.BLOCK) {
         block.getStateContainer().getValidStates().forEach((p_229326_1_) -> {
            this.func_217843_a(BlockModelShapes.getModelLocation(p_229326_1_));
         });
      }

      p_i226056_3_.endStartSection("items");

      for(ResourceLocation resourcelocation : Registry.ITEM.keySet()) {
         this.func_217843_a(new ModelResourceLocation(resourcelocation, "inventory"));
      }

      p_i226056_3_.endStartSection("special");
      this.func_217843_a(new ModelResourceLocation("minecraft:trident_in_hand#inventory"));
      for (ResourceLocation rl : getSpecialModels()) {
         addModelToCache(rl);
      }
      p_i226056_3_.endStartSection("textures");
      Set<Pair<String, String>> set = Sets.newLinkedHashSet();
      Set<Material> set1 = this.field_217851_H.values().stream().flatMap((p_229342_2_) -> {
         return p_229342_2_.func_225614_a_(this::getUnbakedModel, set).stream();
      }).collect(Collectors.toSet());
      set1.addAll(LOCATIONS_BUILTIN_TEXTURES);
      net.minecraftforge.client.ForgeHooksClient.gatherFluidTextures(set1);
      set.stream().filter((p_229346_0_) -> {
         return !p_229346_0_.getSecond().equals(field_229321_r_);
      }).forEach((p_229330_0_) -> {
         LOGGER.warn("Unable to resolve texture reference: {} in {}", p_229330_0_.getFirst(), p_229330_0_.getSecond());
      });
      Map<ResourceLocation, List<Material>> map = set1.stream().collect(Collectors.groupingBy(Material::func_229310_a_));
      p_i226056_3_.endStartSection("stitching");
      this.field_217853_J = Maps.newHashMap();

      for(Entry<ResourceLocation, List<Material>> entry : map.entrySet()) {
         AtlasTexture atlastexture = new AtlasTexture(entry.getKey());
         AtlasTexture.SheetData atlastexture$sheetdata = atlastexture.func_229220_a_(this.resourceManager, entry.getValue().stream().map(Material::func_229313_b_), p_i226056_3_, p_i226056_4_);
         this.field_217853_J.put(entry.getKey(), Pair.of(atlastexture, atlastexture$sheetdata));
      }

      p_i226056_3_.endSection();
   }

   public SpriteMap func_229333_a_(TextureManager p_229333_1_, IProfiler p_229333_2_) {
      p_229333_2_.startSection("atlas");

      for(Pair<AtlasTexture, AtlasTexture.SheetData> pair : this.field_217853_J.values()) {
         AtlasTexture atlastexture = pair.getFirst();
         AtlasTexture.SheetData atlastexture$sheetdata = pair.getSecond();
         atlastexture.upload(atlastexture$sheetdata);
         p_229333_1_.func_229263_a_(atlastexture.func_229223_g_(), atlastexture);
         p_229333_1_.bindTexture(atlastexture.func_229223_g_());
         atlastexture.func_229221_b_(atlastexture$sheetdata);
      }

      this.field_229322_z_ = new SpriteMap(this.field_217853_J.values().stream().map(Pair::getFirst).collect(Collectors.toList()));
      p_229333_2_.endStartSection("baking");
      this.field_217851_H.keySet().forEach((p_229350_1_) -> {
         IBakedModel ibakedmodel = null;

         try {
            ibakedmodel = this.func_217845_a(p_229350_1_, ModelRotation.X0_Y0);
         } catch (Exception exception) {
            exception.printStackTrace();
            LOGGER.warn("Unable to bake model: '{}': {}", p_229350_1_, exception);
         }

         if (ibakedmodel != null) {
            this.field_217852_I.put(p_229350_1_, ibakedmodel);
         }

      });
      p_229333_2_.endSection();
      return this.field_229322_z_;
   }

   private static Predicate<BlockState> parseVariantKey(StateContainer<Block, BlockState> containerIn, String variantIn) {
      Map<IProperty<?>, Comparable<?>> map = Maps.newHashMap();

      for(String s : SPLITTER_COMMA.split(variantIn)) {
         Iterator<String> iterator = EQUALS_SPLITTER.split(s).iterator();
         if (iterator.hasNext()) {
            String s1 = iterator.next();
            IProperty<?> iproperty = containerIn.getProperty(s1);
            if (iproperty != null && iterator.hasNext()) {
               String s2 = iterator.next();
               Comparable<?> comparable = parseValue(iproperty, s2);
               if (comparable == null) {
                  throw new RuntimeException("Unknown value: '" + s2 + "' for blockstate property: '" + s1 + "' " + iproperty.getAllowedValues());
               }

               map.put(iproperty, comparable);
            } else if (!s1.isEmpty()) {
               throw new RuntimeException("Unknown blockstate property: '" + s1 + "'");
            }
         }
      }

      Block block = containerIn.getOwner();
      return (p_229325_2_) -> {
         if (p_229325_2_ != null && block == p_229325_2_.getBlock()) {
            for(Entry<IProperty<?>, Comparable<?>> entry : map.entrySet()) {
               if (!Objects.equals(p_229325_2_.get(entry.getKey()), entry.getValue())) {
                  return false;
               }
            }

            return true;
         } else {
            return false;
         }
      };
   }

   @Nullable
   static <T extends Comparable<T>> T parseValue(IProperty<T> property, String value) {
      return (T)(property.parseValue(value).orElse((T)null));
   }

   public IUnbakedModel getUnbakedModel(ResourceLocation modelLocation) {
      if (this.field_217849_F.containsKey(modelLocation)) {
         return this.field_217849_F.get(modelLocation);
      } else if (this.field_217848_D.contains(modelLocation)) {
         throw new IllegalStateException("Circular reference while loading " + modelLocation);
      } else {
         this.field_217848_D.add(modelLocation);
         IUnbakedModel iunbakedmodel = this.field_217849_F.get(MODEL_MISSING);

         while(!this.field_217848_D.isEmpty()) {
            ResourceLocation resourcelocation = this.field_217848_D.iterator().next();

            try {
               if (!this.field_217849_F.containsKey(resourcelocation)) {
                  this.loadBlockstate(resourcelocation);
               }
            } catch (ModelBakery.BlockStateDefinitionException modelbakery$blockstatedefinitionexception) {
               LOGGER.warn(modelbakery$blockstatedefinitionexception.getMessage());
               this.field_217849_F.put(resourcelocation, iunbakedmodel);
            } catch (Exception exception) {
               LOGGER.warn("Unable to load model: '{}' referenced from: {}: {}", resourcelocation, modelLocation, exception);
               this.field_217849_F.put(resourcelocation, iunbakedmodel);
            } finally {
               this.field_217848_D.remove(resourcelocation);
            }
         }

         return this.field_217849_F.getOrDefault(modelLocation, iunbakedmodel);
      }
   }

   private void loadBlockstate(ResourceLocation blockstateLocation) throws Exception {
      if (!(blockstateLocation instanceof ModelResourceLocation)) {
         this.putModel(blockstateLocation, this.loadModel(blockstateLocation));
      } else {
         ModelResourceLocation modelresourcelocation = (ModelResourceLocation)blockstateLocation;
         if (Objects.equals(modelresourcelocation.getVariant(), "inventory")) {
            ResourceLocation resourcelocation2 = new ResourceLocation(blockstateLocation.getNamespace(), "item/" + blockstateLocation.getPath());
            BlockModel blockmodel = this.loadModel(resourcelocation2);
            this.putModel(modelresourcelocation, blockmodel);
            this.field_217849_F.put(resourcelocation2, blockmodel);
         } else {
            ResourceLocation resourcelocation = new ResourceLocation(blockstateLocation.getNamespace(), blockstateLocation.getPath());
            StateContainer<Block, BlockState> statecontainer = Optional.ofNullable(STATE_CONTAINER_OVERRIDES.get(resourcelocation)).orElseGet(() -> {
               return Registry.BLOCK.getOrDefault(resourcelocation).getStateContainer();
            });
            this.containerHolder.setStateContainer(statecontainer);
            List<IProperty<?>> list = ImmutableList.copyOf(this.field_225365_D.func_225310_a(statecontainer.getOwner()));
            ImmutableList<BlockState> immutablelist = statecontainer.getValidStates();
            Map<ModelResourceLocation, BlockState> map = Maps.newHashMap();
            immutablelist.forEach((p_229340_2_) -> {
               BlockState blockstate = map.put(BlockModelShapes.getModelLocation(resourcelocation, p_229340_2_), p_229340_2_);
            });
            Map<BlockState, Pair<IUnbakedModel, Supplier<ModelBakery.ModelListWrapper>>> map1 = Maps.newHashMap();
            ResourceLocation resourcelocation1 = new ResourceLocation(blockstateLocation.getNamespace(), "blockstates/" + blockstateLocation.getPath() + ".json");
            IUnbakedModel iunbakedmodel = this.field_217849_F.get(MODEL_MISSING);
            ModelBakery.ModelListWrapper modelbakery$modellistwrapper = new ModelBakery.ModelListWrapper(ImmutableList.of(iunbakedmodel), ImmutableList.of());
            Pair<IUnbakedModel, Supplier<ModelBakery.ModelListWrapper>> pair = Pair.of(iunbakedmodel, () -> {
               return modelbakery$modellistwrapper;
            });

            try {
               List<Pair<String, BlockModelDefinition>> list1;
               try {
                  list1 = this.resourceManager.getAllResources(resourcelocation1).stream().map((p_229345_1_) -> {
                     try (InputStream inputstream = p_229345_1_.getInputStream()) {
                        Pair<String, BlockModelDefinition> pair2 = Pair.of(p_229345_1_.getPackName(), BlockModelDefinition.fromJson(this.containerHolder, new InputStreamReader(inputstream, StandardCharsets.UTF_8)));
                        return pair2;
                     } catch (Exception exception1) {
                        throw new ModelBakery.BlockStateDefinitionException(String.format("Exception loading blockstate definition: '%s' in resourcepack: '%s': %s", p_229345_1_.getLocation(), p_229345_1_.getPackName(), exception1.getMessage()));
                     }
                  }).collect(Collectors.toList());
               } catch (IOException ioexception) {
                  LOGGER.warn("Exception loading blockstate definition: {}: {}", resourcelocation1, ioexception);
                  return;
               }

               for(Pair<String, BlockModelDefinition> pair1 : list1) {
                  BlockModelDefinition blockmodeldefinition = pair1.getSecond();
                  Map<BlockState, Pair<IUnbakedModel, Supplier<ModelBakery.ModelListWrapper>>> map2 = Maps.newIdentityHashMap();
                  Multipart multipart;
                  if (blockmodeldefinition.hasMultipartData()) {
                     multipart = blockmodeldefinition.getMultipartData();
                     immutablelist.forEach((p_229339_3_) -> {
                        Pair pair2 = map2.put(p_229339_3_, Pair.of(multipart, () -> {
                           return ModelBakery.ModelListWrapper.func_225335_a(p_229339_3_, multipart, list);
                        }));
                     });
                  } else {
                     multipart = null;
                  }

                  blockmodeldefinition.getVariants().forEach((p_229329_9_, p_229329_10_) -> {
                     try {
                        immutablelist.stream().filter(parseVariantKey(statecontainer, p_229329_9_)).forEach((p_229338_6_) -> {
                           Pair<IUnbakedModel, Supplier<ModelBakery.ModelListWrapper>> pair2 = map2.put(p_229338_6_, Pair.of(p_229329_10_, () -> {
                              return ModelBakery.ModelListWrapper.func_225336_a(p_229338_6_, p_229329_10_, list);
                           }));
                           if (pair2 != null && pair2.getFirst() != multipart) {
                              map2.put(p_229338_6_, pair);
                              throw new RuntimeException("Overlapping definition with: " + (String)blockmodeldefinition.getVariants().entrySet().stream().filter((p_229331_1_) -> {
                                 return p_229331_1_.getValue() == pair2.getFirst();
                              }).findFirst().get().getKey());
                           }
                        });
                     } catch (Exception exception1) {
                        LOGGER.warn("Exception loading blockstate definition: '{}' in resourcepack: '{}' for variant: '{}': {}", resourcelocation1, pair1.getFirst(), p_229329_9_, exception1.getMessage());
                     }

                  });
                  map1.putAll(map2);
               }

            } catch (ModelBakery.BlockStateDefinitionException modelbakery$blockstatedefinitionexception) {
               throw modelbakery$blockstatedefinitionexception;
            } catch (Exception exception) {
               throw new ModelBakery.BlockStateDefinitionException(String.format("Exception loading blockstate definition: '%s': %s", resourcelocation1, exception));
            } finally {
               HashMap<ModelBakery.ModelListWrapper, Set<BlockState>> lvt_20_1_ = Maps.newHashMap();
               map.forEach((p_229341_5_, p_229341_6_) -> {
                  Pair<IUnbakedModel, Supplier<ModelBakery.ModelListWrapper>> pair2 = map1.get(p_229341_6_);
                  if (pair2 == null) {
                     LOGGER.warn("Exception loading blockstate definition: '{}' missing model for variant: '{}'", resourcelocation1, p_229341_5_);
                     pair2 = pair;
                  }

                  this.putModel(p_229341_5_, pair2.getFirst());

                  try {
                     ModelBakery.ModelListWrapper modelbakery$modellistwrapper1 = pair2.getSecond().get();
                     lvt_20_1_.computeIfAbsent(modelbakery$modellistwrapper1, (p_229334_0_) -> {
                        return Sets.newIdentityHashSet();
                     }).add(p_229341_6_);
                  } catch (Exception exception1) {
                     LOGGER.warn("Exception evaluating model definition: '{}'", p_229341_5_, exception1);
                  }

               });
               lvt_20_1_.forEach((p_229335_1_, p_229335_2_) -> {
                  Iterator<BlockState> iterator = p_229335_2_.iterator();

                  while(iterator.hasNext()) {
                     BlockState blockstate = iterator.next();
                     if (blockstate.getRenderType() != BlockRenderType.MODEL) {
                        iterator.remove();
                        this.field_225367_M.put(blockstate, 0);
                     }
                  }

                  if (p_229335_2_.size() > 1) {
                     this.func_225352_a(p_229335_2_);
                  }

               });
            }
         }
      }
   }

   private void putModel(ResourceLocation p_209593_1_, IUnbakedModel p_209593_2_) {
      this.field_217849_F.put(p_209593_1_, p_209593_2_);
      this.field_217848_D.addAll(p_209593_2_.getDependencies());
   }

   // Same as loadTopModel but without restricting to MRL's
   private void addModelToCache(ResourceLocation p_217843_1_) {
      IUnbakedModel iunbakedmodel = this.getUnbakedModel(p_217843_1_);
      this.field_217849_F.put(p_217843_1_, iunbakedmodel);
      this.field_217851_H.put(p_217843_1_, iunbakedmodel);
   }

   private void func_217843_a(ModelResourceLocation p_217843_1_) {
      IUnbakedModel iunbakedmodel = this.getUnbakedModel(p_217843_1_);
      this.field_217849_F.put(p_217843_1_, iunbakedmodel);
      this.field_217851_H.put(p_217843_1_, iunbakedmodel);
   }

   private void func_225352_a(Iterable<BlockState> p_225352_1_) {
      int i = this.field_225366_L++;
      p_225352_1_.forEach((p_229324_2_) -> {
         this.field_225367_M.put(p_229324_2_, i);
      });
   }

   @Nullable
   @Deprecated
   public IBakedModel func_217845_a(ResourceLocation p_217845_1_, IModelTransform p_217845_2_) {
      return getBakedModel(p_217845_1_, p_217845_2_, this.field_229322_z_::func_229151_a_);
   }

   @Nullable
   public IBakedModel getBakedModel(ResourceLocation p_217845_1_, IModelTransform p_217845_2_, java.util.function.Function<Material, net.minecraft.client.renderer.texture.TextureAtlasSprite> textureGetter) {
      Triple<ResourceLocation, TransformationMatrix, Boolean> triple = Triple.of(p_217845_1_, p_217845_2_.func_225615_b_(), p_217845_2_.isUvLock());
      if (this.field_217850_G.containsKey(triple)) {
         return this.field_217850_G.get(triple);
      } else if (this.field_229322_z_ == null) {
         throw new IllegalStateException("bake called too early");
      } else {
         IUnbakedModel iunbakedmodel = this.getUnbakedModel(p_217845_1_);
         if (iunbakedmodel instanceof BlockModel) {
            BlockModel blockmodel = (BlockModel)iunbakedmodel;
            if (blockmodel.getRootModel() == MODEL_GENERATED) {
               return field_217854_z.makeItemModel(textureGetter, blockmodel).func_228813_a_(this, blockmodel, this.field_229322_z_::func_229151_a_, p_217845_2_, p_217845_1_, false);
            }
         }

         IBakedModel ibakedmodel = iunbakedmodel.func_225613_a_(this, textureGetter, p_217845_2_, p_217845_1_);
         this.field_217850_G.put(triple, ibakedmodel);
         return ibakedmodel;
      }
   }

   protected BlockModel loadModel(ResourceLocation location) throws IOException {
      Reader reader = null;
      IResource iresource = null;

      BlockModel lvt_5_2_;
      try {
         String s = location.getPath();
         if (!"builtin/generated".equals(s)) {
            if ("builtin/entity".equals(s)) {
               lvt_5_2_ = MODEL_ENTITY;
               return lvt_5_2_;
            }

            if (s.startsWith("builtin/")) {
               String s2 = s.substring("builtin/".length());
               String s1 = BUILT_IN_MODELS.get(s2);
               if (s1 == null) {
                  throw new FileNotFoundException(location.toString());
               }

               reader = new StringReader(s1);
            } else {
               iresource = this.resourceManager.getResource(new ResourceLocation(location.getNamespace(), "models/" + location.getPath() + ".json"));
               reader = new InputStreamReader(iresource.getInputStream(), StandardCharsets.UTF_8);
            }

            lvt_5_2_ = BlockModel.deserialize(reader);
            lvt_5_2_.name = location.toString();
            return lvt_5_2_;
         }

         lvt_5_2_ = MODEL_GENERATED;
      } finally {
         IOUtils.closeQuietly(reader);
         IOUtils.closeQuietly((Closeable)iresource);
      }

      return lvt_5_2_;
   }

   public Map<ResourceLocation, IBakedModel> func_217846_a() {
      return this.field_217852_I;
   }

   public Object2IntMap<BlockState> func_225354_b() {
      return this.field_225367_M;
   }

   public Set<ResourceLocation> getSpecialModels() {
      return java.util.Collections.emptySet();
   }

   @OnlyIn(Dist.CLIENT)
   static class BlockStateDefinitionException extends RuntimeException {
      public BlockStateDefinitionException(String message) {
         super(message);
      }
   }

   public SpriteMap getSpriteMap() {
      return this.field_229322_z_;
   }

   @OnlyIn(Dist.CLIENT)
   static class ModelListWrapper {
      private final List<IUnbakedModel> field_225339_a;
      private final List<Object> field_225340_b;

      public ModelListWrapper(List<IUnbakedModel> p_i51613_1_, List<Object> p_i51613_2_) {
         this.field_225339_a = p_i51613_1_;
         this.field_225340_b = p_i51613_2_;
      }

      public boolean equals(Object p_equals_1_) {
         if (this == p_equals_1_) {
            return true;
         } else if (!(p_equals_1_ instanceof ModelBakery.ModelListWrapper)) {
            return false;
         } else {
            ModelBakery.ModelListWrapper modelbakery$modellistwrapper = (ModelBakery.ModelListWrapper)p_equals_1_;
            return Objects.equals(this.field_225339_a, modelbakery$modellistwrapper.field_225339_a) && Objects.equals(this.field_225340_b, modelbakery$modellistwrapper.field_225340_b);
         }
      }

      public int hashCode() {
         return 31 * this.field_225339_a.hashCode() + this.field_225340_b.hashCode();
      }

      public static ModelBakery.ModelListWrapper func_225335_a(BlockState p_225335_0_, Multipart p_225335_1_, Collection<IProperty<?>> p_225335_2_) {
         StateContainer<Block, BlockState> statecontainer = p_225335_0_.getBlock().getStateContainer();
         List<IUnbakedModel> list = p_225335_1_.getSelectors().stream().filter((p_225338_2_) -> {
            return p_225338_2_.getPredicate(statecontainer).test(p_225335_0_);
         }).map(Selector::getVariantList).collect(ImmutableList.toImmutableList());
         List<Object> list1 = func_225337_a(p_225335_0_, p_225335_2_);
         return new ModelBakery.ModelListWrapper(list, list1);
      }

      public static ModelBakery.ModelListWrapper func_225336_a(BlockState p_225336_0_, IUnbakedModel p_225336_1_, Collection<IProperty<?>> p_225336_2_) {
         List<Object> list = func_225337_a(p_225336_0_, p_225336_2_);
         return new ModelBakery.ModelListWrapper(ImmutableList.of(p_225336_1_), list);
      }

      private static List<Object> func_225337_a(BlockState p_225337_0_, Collection<IProperty<?>> p_225337_1_) {
         return p_225337_1_.stream().<Object>map(p_225337_0_::get).collect(ImmutableList.toImmutableList());
      }
   }
}