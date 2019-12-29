package Solver;

import java.util.Random;

import org.chocosolver.solver.search.strategy.selectors.variables.VariableSelector;
import org.chocosolver.solver.variables.BoolVar;
import org.chocosolver.solver.variables.IntVar;

public class VariableSelectorPersonal implements VariableSelector<IntVar> {
	private int index = 0;

	@Override
	public IntVar getVariable(IntVar[] variables) {
		int max = -1;
		IntVar selected = null;
		for (IntVar a : variables) {
			if (!a.isInstantiated()) {
				if (max < Integer.valueOf(a.getName())) {
					max = Integer.valueOf(a.getName());
					selected = a;
				}
			}
		}
		return selected;
	}
}
