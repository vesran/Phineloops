package model.enumtype;
/**
 * All Piece identified by id and their associated class 
 */
public enum PieceClass {
	circle("model.pieces.Circle", (short) 1), bar("model.pieces.Bar", (short) 2), l("model.pieces.L", (short) 5), t("model.pieces.T", (short) 3),
	x("model.pieces.X", (short) 4), empty("model.pieces.Empty", (short) 0);
	private Class<?> myClass;
	private short idPiece;

	private PieceClass(String classe, short id) {
		this.idPiece = id;
		try {
			this.myClass = Class.forName(classe);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public short getId() {
		return this.idPiece;
	}

	public Class<?> getClasse() {
		return this.myClass;
	}

}
