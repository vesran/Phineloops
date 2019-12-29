package Solver;
/**
 *  @author Karim Amrouche
 * @author Bilal Khaldi
 * @author Yves Tran
 * type enum to represent wall to extend or not for the solver for example if i extend north it's not possible to have a north connection
 */
public enum Extend {
	northWest,northEast,north,west,east,southWest,southEast,south,noExtend,allExtend;
}
