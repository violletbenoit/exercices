package application;

import java.util.Random;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Plateau {
	GraphicsContext gc;
	private int[][] hauteurTerrain;
	private int size;
	static final int sizeTile = 8;
	private Image[] imageTerrain;
	public Plateau(int size,GraphicsContext gc){
		this.size = 129;
		this.gc = gc;
		imageTerrain = new Image[10];
		for (int i=0;i<10;i++){
			imageTerrain[i] = new Image("file:Asset/level"+i+".png");
		}
	}
	
	public void draw(){
		
		HeightMap heightMap = new HeightMap();
		double [][] m = new double[size][size];
		heightMap.diamondSquare(m, 52);
		hauteurTerrain = new int[size][size];
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
				hauteurTerrain[j][i] = (int) (c*9.0);
			}
		}

		for (int j=0;j<size;j++){
			for (int i=0; i<size; i++){
				gc.drawImage(imageTerrain[hauteurTerrain[j][i]],
						(i*sizeTile)%256,(j*sizeTile)%256,sizeTile,sizeTile,
						i*sizeTile,j*sizeTile,sizeTile,sizeTile);
			}
		}

		System.out.println(max);
		System.out.println(min);
	}
	
	public void generateRandom(int n){
		Random r = new Random();
		HeightMap heightMap = new HeightMap();
		
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
