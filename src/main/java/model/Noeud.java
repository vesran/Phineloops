package model;

public class Noeud {
	int x, y;
	Noeud pere;

	public Noeud(int x, int y) {
		this.x = x;
		this.y = y;
		this.pere = this;
	}

	public void union(Noeud n) {
		n.pere = this;
	}

	public Noeud find() {
		if (this.pere == this)
			return this;
		else
			return this.pere.find();
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return " x = " + this.x + " y = " + this.y;
	}
}
