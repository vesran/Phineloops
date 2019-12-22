package Solver;

import java.util.Random;

import org.chocosolver.solver.search.strategy.selectors.variables.VariableSelector;
import org.chocosolver.solver.variables.BoolVar;
import org.chocosolver.solver.variables.IntVar;

public class VariableSelectorPersonal implements VariableSelector<IntVar> {
	private int index = 0;

	@Override
	public IntVar getVariable(IntVar[] variables) {
		for (IntVar a : variables) {
			if (a.getValue() == 0) {
				if (!a.isInstantiated())
					return a;
			}else {
				if(new Random().nextBoolean()) {
					if(!a.isInstantiated())return a ; 
				}
			}
		}
		return null;
	}
}
