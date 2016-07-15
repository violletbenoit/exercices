package openjavacreeper;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Creeper {
	private int i,j;
	Image imgCreeper;
	int maskSize = 22;

	public Creeper(int i,int j){
		this.i = i;
		this.j = j;
		imgCreeper = new Image("file:assets/emitter.png");			
	}
	
	public int getI(){
		return i;
	}
	public int getJ(){
		return j;
	}
	public void affiche(GraphicsContext gc){
		gc.drawImage(imgCreeper, i*maskSize-((int)imgCreeper.getWidth()+maskSize)/2, j*maskSize-((int)imgCreeper.getHeight()+maskSize)/2);
	}
}
