package engineTester;

import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;
import models.RawModel;
import models.TexturedModel;
import objConverter.ModelData;
import objConverter.OBJFileLoader;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import terrains.Terrain;
import textures.ModelTexture;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainGameLoop {

	public static void main(String[] args) {

		DisplayManager.createDisplay();
		Loader loader = new Loader();
		
		//----------------------------------------------------------------------------

		ModelData dragon = OBJFileLoader.loadOBJ("dragon");
		RawModel dragonModel = loader.loadToVAO(dragon.getVertices(), dragon.getTextureCoords(), dragon.getNormals(), dragon.getIndices());
		TexturedModel dragonT = new TexturedModel(dragonModel,new ModelTexture(loader.loadTexture("BLUE")));
		
//		ModelData flower = OBJFileLoader.loadOBJ("flower");
//		RawModel flowerModel = loader.loadToVAO(flower.getVertices(), flower.getTextureCoords(), flower.getNormals(), flower.getIndices());
		
		ModelData fern = OBJFileLoader.loadOBJ("fern");
		RawModel fernModel = loader.loadToVAO(fern.getVertices(), fern.getTextureCoords(), fern.getNormals(), fern.getIndices());
		
		ModelData tree = OBJFileLoader.loadOBJ("tree");
		RawModel treeModel = loader.loadToVAO(tree.getVertices(), tree.getTextureCoords(), tree.getNormals(), tree.getIndices());
		
		ModelData tree2 = OBJFileLoader.loadOBJ("lowPolyTree");
		RawModel treeModel2 = loader.loadToVAO(tree2.getVertices(), tree2.getTextureCoords(), tree2.getNormals(), tree2.getIndices());
		
		ModelData grass = OBJFileLoader.loadOBJ("grassModel");
		RawModel grassModel = loader.loadToVAO(grass.getVertices(), grass.getTextureCoords(), grass.getNormals(), grass.getIndices());
		
//		TexturedModel flowerT = new TexturedModel(flowerModel,new ModelTexture(loader.loadTexture("fern")));
		TexturedModel fernT = new TexturedModel(fernModel,new ModelTexture(loader.loadTexture("fern")));
		TexturedModel treeT = new TexturedModel(treeModel,new ModelTexture(loader.loadTexture("tree")));
		TexturedModel treeT2 = new TexturedModel(treeModel2,new ModelTexture(loader.loadTexture("tree")));
		TexturedModel grassT = new TexturedModel(grassModel,new ModelTexture(loader.loadTexture("grass")));
		fernT.getTexture().setHasTransparency(true);
		fernT.getTexture().setUseFakeLighting(true);
		grassT.getTexture().setHasTransparency(true);
		grassT.getTexture().setUseFakeLighting(true);
		
		ModelTexture texture = dragonT.getTexture();
		texture.setShineDamper(50);
		texture.setReflectivity(1f);
		
		Light light = new Light(new Vector3f(0, 0, -15), new Vector3f(1,1,1));
		
		Light light2 = new Light(new Vector3f(800, 1000, 800), new Vector3f(1,1,1));
		
		
		//TERRAIN
		Terrain terrain = new Terrain(0,0,loader,new ModelTexture(loader.loadTexture("GRASS2")), "height");
		
		
		List<Entity> entities = new ArrayList<Entity>();
		Random random = new Random();
		
		for(int i = 0 ; i < 100 ; i++) {

		entities.add(new Entity(dragonT, new Vector3f(random.nextFloat() * 800-800, 0, random.nextFloat() * -800), 0, 0, 0f, 1f) );
		entities.add(new Entity(dragonT, new Vector3f(random.nextFloat() * 800, 0, random.nextFloat() * -800), 0, 0, 0f, 1f) );
		entities.add(new Entity(dragonT, new Vector3f(random.nextFloat() * 800-800, 0, random.nextFloat() * -800 + 800), 0, 0, 0f, 1f) );
		entities.add(new Entity(dragonT, new Vector3f(random.nextFloat() * 800, 0, random.nextFloat() * -800 + 800), 0, 0, 0f, 1f) );

//			entities.add(new Entity(flowerT, new Vector3f(random.nextFloat() * 800-800, 0, random.nextFloat() * -800), 0, 0, 0f, 1f) );
//			entities.add(new Entity(flowerT, new Vector3f(random.nextFloat() * 800, 0, random.nextFloat() * -800), 0, 0, 0f, 1f) );
//			entities.add(new Entity(flowerT, new Vector3f(random.nextFloat() * 800-800, 0, random.nextFloat() * -800 + 800), 0, 0, 0f, 1f) );
//			entities.add(new Entity(flowerT, new Vector3f(random.nextFloat() * 800, 0, random.nextFloat() * -800 + 800), 0, 0, 0f, 1f) );
			float x = random.nextFloat()* 800;
			float z = random.nextFloat() * -800+800;
			float y = terrain.getHeightOfTerrain(x,z);
			entities.add(new Entity(treeT2, new Vector3f(x, y-1, z), 0, 0, 0f, 1f) );
		}
		for(int i = 0 ; i < 100 ; i++) {
				float x = random.nextFloat()* 800;
				float z = random.nextFloat() * -800+800;
				float y = terrain.getHeightOfTerrain(x,z);
				entities.add(new Entity(fernT, new Vector3f(x, y-1, z), 0, 0, 0f, 1f) );

				
		}
		for(int i = 0 ; i < 100 ; i++) {
			float x = random.nextFloat()* 800;
			float z = random.nextFloat() * -800+800;
			float y = terrain.getHeightOfTerrain(x,z);
			entities.add(new Entity(treeT, new Vector3f(x, y-1, z), 0, 0, 0f, 10f) );
			

			
		}

		for(int i = 0 ; i < 100 ; i++) {
			float x = random.nextFloat()* 800;
			float z = random.nextFloat() * -800+800;
			float y = terrain.getHeightOfTerrain(x,z);
			entities.add(new Entity(grassT, new Vector3f(x, y, z), 0, 0, 0f, 3f) );
			

			
		}
		
		MasterRenderer renderer = new MasterRenderer();
		
		//PLAYER
		
		RawModel bunnyModel = OBJLoader.loadObjModel("BASEmodel", loader);
		TexturedModel stanfordBunny = new TexturedModel(bunnyModel, new ModelTexture(loader.loadTexture("RED")));
		
		Player player = new Player(stanfordBunny, new Vector3f(0, 0, 740), 0, 0, 0, 1f);
		
		Camera camera = new Camera(player);
		
		while(!Display.isCloseRequested()){

			camera.move();
			player.move(terrain);
			renderer.processEntity(player);
			renderer.processTerrain(terrain);
			
			for(Entity entity : entities) {
				renderer.processEntity(entity);
			}
	
			renderer.render(light2, camera);
			//renderer.render(entity2,shader);

			DisplayManager.updateDisplay();
		}
		
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();

	}

}
