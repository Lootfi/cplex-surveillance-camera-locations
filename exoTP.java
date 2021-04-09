package main;
import ilog.concert.*;
import ilog.cplex.*;
public class exoTP {
	public static void main(String[] args) {
		calcul();
	}
	public static void calcul (){
		try {
			IloCplex simplexe = new IloCplex ();
			// déclaration des Variables de décision de type reel
			IloNumVar var_decis [][] = new IloNumVar [2][1];
			for (int i=0;i<2;i++){
				var_decis[i][0]= simplexe.numVar(0, Double.MAX_VALUE);
			}
			// declaration de la fonction objectif
			IloLinearNumExpr objectif = simplexe.linearNumExpr();
			// définition des coefficients de la fonction objectif
			objectif.addTerm(60, var_decis[0][0]);
			objectif.addTerm(40, var_decis[1][0]);
			// Définir le type d'optimisation de la fonction (max ou min )
			simplexe.addMaximize(objectif);
			// les contraintes 1*X1 + 2*X2 <= 70
			IloLinearNumExpr contrainte_1 = simplexe.linearNumExpr();
			contrainte_1.addTerm(1, var_decis[0][0]);
			contrainte_1.addTerm(2, var_decis[1][0]);
			simplexe.addLe(contrainte_1, 70);
			// la même chose pour les autres contraintes
			//deuxième contrainte
			IloLinearNumExpr contrainte_2 = simplexe.linearNumExpr();
			contrainte_2.addTerm(1, var_decis[0][0]);
			contrainte_2.addTerm(1, var_decis[1][0]);
			simplexe.addLe(contrainte_2, 40);
			//troisième contrainte
			IloLinearNumExpr contrainte_3 = simplexe.linearNumExpr();
			contrainte_3.addTerm(3, var_decis[0][0]);
			contrainte_3.addTerm(1, var_decis[1][0]);
			simplexe.addLe(contrainte_3, 90);
			simplexe.solve(); // lancer resolution
			// Afficher des résultat
			System.out.println("Voici la valeur de la fonction objectif "+ simplexe.getObjValue());
			System.out.println(" Voici les valeurs des variables de décision: ") ;
			for (int i=0;i<2;i++)
				System.out.println( "X"+i+ " = "+ simplexe.getValue(var_decis[i][0]));
		} catch (IloException e){
			System.out.print("Exception levée " + e); }
	} }