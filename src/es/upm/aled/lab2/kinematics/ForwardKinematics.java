package es.upm.aled.lab2.kinematics;

import es.upm.aled.lab2.gui.Node;

/**
 * This class implements a forward kinematics algorithm using recursion. It
 * expects a tree of Segments (defined by its length and angle with respect to
 * the previous Segment in the tree) and returns a tree of Nodes (defined by
 * their absolute coordinates in a 2-dimensional space).
 * 
 * @author rgarciacarmona
 */
public class ForwardKinematics {

	/**
	 * Returns a tree of Nodes to be used by SkeletonPanel to draw the position of
	 * an exoskeleton. This method is the public facade to a recursive method that
	 * builds the result from a tree of Segments defined by their angle and length,
	 * and the relationship between them (which Segment is children of which).
	 * 
	 * @param root    The root of the tree of Segments.
	 * @param originX The X coordinate for the origin point of the tree.
	 * @param originY The Y coordinate for the origin point of the tree.
	 * @return The tree of Nodes that represent the exoskeleton position in absolute
	 *         coordinates.
	 */
	// Public method: returns the root of the position tree
	public static Node computePositions(Segment root, double originX, double originY) {
		Node inicial = new Node (originX,originY);
		inicial.addChild(computePositions(root, originX, originY,0)) ;
		return inicial;
		
	}

	// Private helper method that implements the recursive algorithm
	private static Node computePositions(Segment link, double baseX, double baseY, double accumulatedAngle) {
		// caso base
		Node nodoActual = new Node (baseX,baseY); // Te creas un nodo en la posición del origen del segmento
		
		for(Segment c: link.getChildren()) {
			double anguloTotal = accumulatedAngle+c.getAngle(); // Suma a los ángulos anteriores el del segmento actual
			double posX = nodoActual.getX() + link.getLength()*Math.cos(anguloTotal); 
			double posY = nodoActual.getY() + link.getLength()*Math.sin(anguloTotal);
			Node nodoSiguiente = new Node (posX, posY);
			nodoActual.addChild(nodoSiguiente);
			// caso final
			if (link.getChildren().isEmpty()) {
				return nodoSiguiente; // Si no tiene más segmetos hijos se suicida y devuelve el nodo actual, que resulta que es el último
			}
			// paso recursivo
			for (Segment s : link.getChildren()) {
				computePositions(s, posX, posY, anguloTotal); // Recorre los segmentos 
			}
			return nodoSiguiente;
		}
	
	}
}









