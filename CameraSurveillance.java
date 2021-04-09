package main;
import ilog.concert.*;
import ilog.cplex.*;

public class CameraSurveillance {
	public static void main(String[] args) {
		calcul ();
	}
	public static void calcul (){
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
					IloLinearNumExpr contrainte = simplexe.linearNumExpr();
					contrainte.addTerm(1, x[i]);
					contrainte.addTerm(1, x[j]);
					simplexe.addGe(contrainte, 1);
				}
			}
			
			simplexe.solve(); // lancer resolution
			
			// Afficher des r�sultat
			System.out.println("Voici la valeur de la fonction objectif "+ simplexe.getObjValue());
			System.out.println(" Voici les valeurs des variables de d�cision: ") ;
			for (int i=0;i<n;i++)
				System.out.println( "X"+i+ " = "+ simplexe.getValue(x[i]));
		} catch (IloException e){
			System.out.print("Exception lev�e " + e);
		}
	}
}
