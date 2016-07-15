package javaControl;
import java.util.*;

import javafx.css.SimpleStyleableObjectProperty;

public class Program {

	static void palindrome(){
		Scanner scanner = new Scanner(System.in);
		System.out.println("Entrez un palindrome :");
		String pal = scanner.nextLine();
		boolean ispal = true;
		for (int i=0; i<pal.length()/2;i++){
			if (pal.charAt(i)!=pal.charAt(pal.length()-1-i)){
				ispal = false;
				break;
			}
		}
		if (ispal){
			System.out.println("C'est un palindrome");
		}else{
			System.out.println("Ce n'est pas un palindrome");
		}
	}
	
	static void testnote(){
		Scanner scanner = new Scanner(System.in);

		int somme = 0, nombre = 0;
		int min = 20, max = 0;
		System.out.println("Entrez les notes jusqu'à STOP");
		while(true){
			String nextline = scanner.nextLine();
			if (nextline.compareTo("STOP")==0){
				break;
			}
			int note = Integer.parseInt(nextline);
			min = Math.min(min, note);
			max = Math.max(max, note);
			somme += note;
			nombre++;
		}
		System.out.println("Note minimale : "+min);
		System.out.println("Note maximale : "+max);
		System.out.println("Moyenne : " +((float)somme)/nombre);
	}
	
	public static void triangle(){
		Scanner scanner = new Scanner(System.in);
		String nextline = scanner.nextLine();
		int taille = Integer.parseInt(nextline);
		for (int j=0;j<taille;j++){
			for (int i=0;i<taille-j;i++){
				if (j==0){
					System.out.print('*');					
				}else{
					if (i==0 || i==taille-j-1){
						System.out.print('*');											
					}else{
						System.out.print('-');					
					}
				}
			}
			System.out.println();
		}
	}

	public static void losange(){
		Scanner scanner = new Scanner(System.in);
		String nextline = scanner.nextLine();
		int taille = Integer.parseInt(nextline);
		if (taille%2==0){
			taille++;
		}
		double k;
		for (int j=0;j<taille;j++){
			k=taille/2-j;
			if (k<0){
				k = -k;
			}
			
			for (int i=0;i<k;i++){
				System.out.print(" ");
			}
			for (int i=0;i<taille-2*k;i++){
				if (i==0 || i == taille-2*k -1){
					System.out.print("*");
				}else{
					System.out.print("-");
				}
			}
			System.out.println();
		}
	}

	public static void main(String[] args) {
		losange();
		
	}

}
