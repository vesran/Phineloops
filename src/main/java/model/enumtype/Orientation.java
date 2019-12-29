package model.enumtype;
/**
 * All orientations possible for a piece
 */
public enum Orientation {
	NORTH {public Orientation opposite() { return Orientation.SOUTH; }},
	SOUTH {public Orientation opposite() { return Orientation.NORTH; }},
	WEST {public Orientation opposite() { return Orientation.EAST; }},
	EAST {public Orientation opposite() { return Orientation.WEST; }};

	public abstract Orientation opposite();
} 
