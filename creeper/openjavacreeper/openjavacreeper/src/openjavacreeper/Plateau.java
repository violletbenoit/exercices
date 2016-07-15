package openjavacreeper;

import java.util.Random;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;

public class Plateau {
	Image[] levels;
	Image masks;
	int maskSize = 22;
	int levelSize = 256;
	int sizePlateau = 32;
	int[][] hauteur;
	double [][] hauteurEau;
	double [][] hauteurEauTmp;
	int [][] CountDons;
	Creeper creeper;
	
	public Plateau(){
		
		// levels
		levels = new Image[10];
		for (int i=0; i<10; i++){
			levels[i] = new Image("file:assets/level"+i+".png");			
		}		
		// masks
		masks = new Image("file:assets/mask.png");
		
		generationAleatoireTerrain();
		hauteurEau = new double[sizePlateau][sizePlateau];
		hauteurEauTmp = new double[sizePlateau][sizePlateau];
		CountDons = new int[sizePlateau][sizePlateau];
	}
	
	public void tick(){
		hauteurEau[creeper.getI()][creeper.getJ()]=10.0;
		
		// on copit hauteurEau dans hauteursEauTmp
		for (int i=0;i<sizePlateau;i++){
			for (int j=0;j<sizePlateau;j++){
				hauteurEauTmp[i][j]=hauteurEau[i][j];
			}
		}		
		
		// comptage des dons
		for (int i=0;i<sizePlateau;i++){
			for (int j=0;j<sizePlateau;j++){
				int countDon = 0;
				for (int ii = i-1;ii<=i+1;ii++){
					for (int jj=j-1;jj<=j+1;jj++){
						// hors limite
						if (ii == -1 || ii == sizePlateau || jj==-1 || jj == sizePlateau) continue;
						
						// on donne
						if (hauteur[ii][jj]+hauteurEau[ii][jj]<hauteur[i][j]+hauteurEau[i][j]){
							if (hauteurEau[i][j]<=0){
								continue;
							}
							countDon++;
						}
					}
				}
				CountDons[i][j]=countDon;
			}
		}

		for (int i=0;i<sizePlateau;i++){
			for (int j=0;j<sizePlateau;j++){

				double versement = (double)(hauteurEau[i][j]/50/CountDons[i][j]);

				for (int ii = i-1;ii<=i+1;ii++){
					for (int jj=j-1;jj<=j+1;jj++){
						// hors limite
						if (ii == -1 || ii == sizePlateau || jj==-1 || jj == sizePlateau) continue;
						
						// on donne
						if (hauteur[ii][jj]+hauteurEau[ii][jj]<hauteur[i][j]+hauteurEau[i][j]){
							if (hauteurEau[i][j]<=0){
								continue;
							}
							hauteurEauTmp[i][j]-=versement;
							hauteurEauTmp[ii][jj]+=versement;							
						}
					}
				}
			}
		}

		// on copit hauteurEau dans hauteursEauTmp
		for (int i=0;i<sizePlateau;i++){
			for (int j=0;j<sizePlateau;j++){
					hauteurEau[i][j]=hauteurEauTmp[i][j];					
			}
		}		

	}
	
	private void generationAleatoireTerrain() {
		//hauteur = new int[sizePlateau][sizePlateau];
		creerMapDiamantsCarre();
		Random r = new Random();
		creeper = new Creeper(r.nextInt(sizePlateau), r.nextInt(sizePlateau));
	}

	void draw(GraphicsContext gc){
		
		for (int i=0;i<sizePlateau*maskSize;i++){
			for (int j=0;j<sizePlateau*maskSize;j++){
				gc.getPixelWriter().setColor(i, j, levels[hauteur[i/maskSize][j/maskSize]].getPixelReader().getColor(i%levelSize, j%levelSize));
			}
		}
		
		gc.setFill(new Color(0.0,0.0,1.0,0.5));
		for (int i=0;i<sizePlateau;i++){
			for (int j=0;j<sizePlateau;j++){
				if (hauteurEau[i][j]>=0.001){
					gc.fillRect(i*maskSize, j*maskSize, maskSize, maskSize);
				}
			}
		}
		creeper.affiche(gc);
	}
	
    private void creerMapDiamantsCarre()
    {
    	//Taille de la map
        int precision = sizePlateau+1;//Doit être de la forme 2^n + 1
     
        //Rugosité du terrain
        float rugosite = 0.0005f;
     
        //Les vertex composant la map
        float[][] map = new float[precision][precision];
        
        //Les quelques variables/objets qui serviront à la creation de la map
        int espace = (int) precision;
        int largeur = (int) precision;
        Random r = new Random();
 
        /*
         * On place les coins de notre map à une hauteur précise, puis que les autres
         * points seront crées à partir de ces quatres points pré-existants.
         */
        float hauteurBase = 0f;
        map[0][0] = hauteurBase;
        map[0][precision-1] = hauteurBase;
        map[precision-1][precision-1] = hauteurBase;
        map[precision-1][0] = hauteurBase;
 
        //Tant qu'on a pas modifié tout nos points
        while(espace > 1)
        {
            /*
             * Cette echelle permet de determiner de quelle quantite maximum on va modifier le point testé
             * elle décroit au fur et à mesure que l'on diminue la taille de nos carrés/diamants
             * afin que la map paraissent douce et non totalement aléatoire
             */
            float scale = espace * rugosite;
 
            /*
             * Pour les carrés
             */
            for(int x = espace/2; x < largeur - 1; x += espace)
            {
                for(int y = espace/2; y < largeur - 1; y += espace)
                {
                    //Moyenne des hauteurs des quatres points situés dans les coins autour de ce point
                    float average = (map[x-espace/2][y-espace/2] + map[x+espace/2][y-espace/2] + map[x+espace/2][y+espace/2] + map[x-espace/2][y+espace/2]) / 4f;
 
                    //La hauteur du point depend de la moyenne de ses voisins et d'une valeur aléatoire dont le maximum est 'scale'
                    map[x][y] = average + r.nextFloat() * 2 * scale - scale;
                }
            }
 
            /*
             * Pour les "diamants"
             */
            //Variable qui sert à déterminer si la n-ieme ligne qu'on traite est paire (pas si y est pair)
            boolean estPaire = true;
            for(int y = 0; y < largeur; y += espace/2)
            {
                for(int x = (estPaire) ? espace/2 : 0; x < largeur; x = (espace%2==0) ? x + espace : x + espace - 1)
                {
                    //On fait une moyenne, mais on ne prend que des points existants. D'ou le compteur de points 'ptCount'
                    float ave = 0f;
                    int ptCount = 0;
 
                    if(x > 0)
                    {
                        ave += map[x-espace/2][y];
                        ptCount++;
                    }
                    if(y > 0)
                    {
                        ave += map[x][y-espace/2];
                        ptCount++;
                    }
                    if(x < largeur - 1)
                    {
                        ave += map[x+espace/2][y];
                        ptCount++;
                    }
                    if(y < largeur - 1)
                    {
                        ave += map[x][y+espace/2];
                        ptCount++;
                    }
 
                    ave /= ptCount;
 
                    //On applique cette moyenne sur le point courant, avec le meme systeme d'aléas que pour les carrés
                    map[x][y] = ave + r.nextFloat() * 2 * scale - scale;
                }
 
                estPaire = !estPaire;
            }
 
            espace /= 2;
        }
        
		hauteur = new int[sizePlateau][sizePlateau];

		double min = Double.MAX_VALUE,
				max = Double.MIN_VALUE;

        for (int i=0;i<precision-1;i++){
        	for (int j=0;j<precision-1;j++){
        		min = Math.min(map[i][j], min);
        		max = Math.max(map[i][j], max);
        	}
        }
        for (int i=0;i<precision-1;i++){
        	for (int j=0;j<precision-1;j++){
        		hauteur[i][j]=(int)((map[i][j]-min)/(max-min)*9);
        	}
        }
        
    }

}
