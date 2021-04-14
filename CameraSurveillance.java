package main;
import ilog.concert.*;
import ilog.cplex.*;

public class CameraSurveillance {
	
	
	
	public static void main(String[] args) {
		
		Graphe ma = new Graphe(49);
		ma.ajoutArete(1, 2);
		ma.ajoutArete(1, 3);
		ma.ajoutArete(2, 41);
		ma.ajoutArete(2, 39);
		ma.ajoutArete(3, 11);
		ma.ajoutArete(4, 5);
		ma.ajoutArete(4, 6);
		ma.ajoutArete(4, 9);
		ma.ajoutArete(6, 7);
		ma.ajoutArete(6, 8);
		ma.ajoutArete(9, 10);
		ma.ajoutArete(11, 21);
		ma.ajoutArete(12, 13);
		ma.ajoutArete(12, 15);
		ma.ajoutArete(13, 14);
		ma.ajoutArete(14, 15);
		ma.ajoutArete(14, 18);
		ma.ajoutArete(15, 19);
		ma.ajoutArete(16, 20);
		ma.ajoutArete(17, 18);
		ma.ajoutArete(18, 19);
		ma.ajoutArete(19, 20);
		ma.ajoutArete(20, 21);
		ma.ajoutArete(21, 22);
		ma.ajoutArete(22, 25);
		ma.ajoutArete(22, 23);
		ma.ajoutArete(23, 32);
		ma.ajoutArete(24, 25);
		ma.ajoutArete(25, 26);
		ma.ajoutArete(25, 30);
		ma.ajoutArete(26, 27);
		ma.ajoutArete(26, 28);
		ma.ajoutArete(28, 29);
		ma.ajoutArete(30, 31);
		ma.ajoutArete(31, 32);
		ma.ajoutArete(31, 33);
		ma.ajoutArete(32, 38);
		ma.ajoutArete(33, 34);
		ma.ajoutArete(33, 37);
		ma.ajoutArete(34, 35);
		ma.ajoutArete(35, 36);
		ma.ajoutArete(37, 43);
		ma.ajoutArete(37, 38);
		ma.ajoutArete(38, 40);
		ma.ajoutArete(39, 40);
		ma.ajoutArete(40, 41);
		ma.ajoutArete(41, 42);
		ma.ajoutArete(43, 44);
		ma.ajoutArete(44, 49);
		ma.ajoutArete(44, 45);
		ma.ajoutArete(45, 47);
		ma.ajoutArete(47, 48);
		
		//System.out.println(ma.toString());
		
		calcul(ma);
	}
	public static void calcul (Graphe adj){
		try {
			int n = 49;
			IloCplex simplexe = new IloCplex ();
			
			// d�claration des Variables de d�cision de type boolean (2�me contrainte)
			IloNumVar[] x = new IloNumVar[n];
			
			for (int i=0;i<n;i++){
				x[i]= simplexe.boolVar();
			}
			
			
			// declaration de la fonction objectif
			IloLinearNumExpr objectif = simplexe.linearNumExpr();
			
			// d�finition des coefficients de la fonction objectif
			for (int i=0;i<n;i++){
				objectif.addTerm(1, x[i]);
			}
			
			
			// D�finir le type d'optimisation de la fonction (min)
			simplexe.addMinimize(objectif);
			
			
			// les contraintes (Xi + Xj >= 1)	
			for(int i = 0; i < n; i++) {
				for(int j = 0; j < n; j++) {
					if(adj.adjacent(i, j)) {
						IloLinearNumExpr contrainte = simplexe.linearNumExpr();
						contrainte.addTerm(1, x[i]);
						contrainte.addTerm(1, x[j]);
						simplexe.addGe(contrainte, 1);						
					}
				}
			}
			
			simplexe.solve(); // lancer resolution
			
			// Afficher des r�sultat
			System.out.println("Voici la valeur de la fonction objectif (Nombre des cam�ras qu'on va plac�): "+ simplexe.getObjValue());
			System.out.println(" Voici les valeurs des variables de d�cision: ") ;
			for (int i=0;i<n;i++) {
				int k = i + 1;
				System.out.println( "X"+k+ " = "+ simplexe.getValue(x[i]));				
			}
		} catch (IloException e){
			System.out.print("Exception lev�e " + e);
		}
	}
}
