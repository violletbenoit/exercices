package application;

import java.util.Random;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Plateau {
	GraphicsContext gc;
	private int[][] hauteurTerrain;
	private int size;
	static final int sizeTile = 16;
	private Image[] imageTerrain;
	private Image imageMask;
	private int[][] indextiles;
	public Plateau(int size,GraphicsContext gc){
		this.size = 129;
		this.gc = gc;
		imageTerrain = new Image[10];
		for (int i=0;i<10;i++){
			imageTerrain[i] = new Image("file:Asset/level"+i+".png");
		}
		imageMask = new Image("file:Asset/mask.png");
	}
	
	public void draw(){
		
		HeightMap heightMap = new HeightMap();
		double [][] m = new double[size][size];
		heightMap.diamondSquare(m, 52);
		hauteurTerrain = new int[size][size];
		indextiles = new int[size][size]; 
		double max = 0;
		double min =1000000000;
		for (int j=0;j<size;j++){
			for (int i=0; i<size; i++){
				double c = m[j][i];
				max = Math.max(max, c);
				min = Math.min(min, c);
			}
		}

		for (int j=0;j<size;j++){
			for (int i=0; i<size; i++){
				double c = (m[j][i]-min)/(max-min);
				//hauteurTerrain[j][i] = (int) (c*9.0);
				hauteurTerrain[j][i] = j%10;
			}
		}


	    for (int i = 0; i < size; i++) {
	        for (int j = 0; j < size; j++) {
	          int indexAbove = -1;
	          for (int layer = 9; layer > -1; layer--) { // highest layer to lowest

	            if (layer <= hauteurTerrain[j][i]) { // only draw when layer is <= tile height

	              // calculate autotiling index, neighbouring tiles >= layer
	              int up = 0, down = 0, left = 0, right = 0;
	              if (j - 1 < 0)
	                up = 0;
	              else if (hauteurTerrain[j-1][i] >= layer)
	                up = 1;
	              if (j + 1 > size - 1)
	                down = 0;
	              else if (hauteurTerrain[j+1][i] >= layer)
	                down = 1;
	              if (i - 1 < 0)
	                left = 0;
	              else if (hauteurTerrain[j][i-1] >= layer)
	                left = 1;
	              if (i + 1 > size - 1)
	                right = 0;
	              else if (hauteurTerrain[j][i+1] >= layer)
	                right = 1;

	              int index = (8 * down) + (4 * left) + (2 * up) + right;

	              // save index of highest layer
	              if (layer == hauteurTerrain[j][i])
	            	  indextiles[j][i]=index;

	              if (layer < 9) {
	                // skip tiles that are identical to the one above
	                if (index == indexAbove)
	                  continue;
	                // skip tiles that are not visible
	                if (indexAbove == 5 || indexAbove == 7 || indexAbove == 10 || indexAbove == 11 ||
	                    indexAbove == 13 || indexAbove == 14 || indexAbove == 15)
	                  continue;
	              }

	              indexAbove = index;

	              //Zei.Renderer.get("level$layer").context.drawImageScaledFromSource(Zei.SpriteManager.getSprite("mask")["image"], index * (Tile.size + 6) + 3, (Tile.size + 6) + 3, Tile.size, Tile.size, i * Tile.size, j * Tile.size, Tile.size, Tile.size);
	            }
	          }
	        }
	    }
	    
		for (int j=0;j<size;j++){
			for (int i=0; i<size; i++){
				gc.drawImage(imageTerrain[hauteurTerrain[j][i]],
						(i*sizeTile)%256,(j*sizeTile)%256,sizeTile,sizeTile,
						i*sizeTile,j*sizeTile,sizeTile,sizeTile);
				int index = indextiles[j][i];
				gc.drawImage(imageMask,
						index*16,0,16,16,
						i*sizeTile,j*sizeTile,16,16);
			}
		}


		System.out.println(max);
		System.out.println(min);
	}
	
	public void generateRandom(int n){
		
/*		heightMap.Build(100, size, 0, 90,n);			
		hauteurTerrain = new int[size][size];
		for (int j=0;j<size;j++){
			for (int i=0;i<size;i++){
				int h = (int) (heightMap.getMap()[j][i]/10.0);
				if (h<=0) h=0;
				if (h>=9) h=9;
				hauteurTerrain[j][i] = h;
			}
		}*/
	}
}
