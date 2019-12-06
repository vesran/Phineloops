package model;

public enum PieceClass {
	circle("model.Circle",(short)1), bar("model.Bar",(short)2),l("model.L",(short)5),t("model.T",(short)3),x("model.X",(short)4),empty("model.Empty",(short)0);
	
	
	private Class<?>myClass;
	private short idPiece ; 
	
	private PieceClass(String classe , short id) {
		this.idPiece = id ; 
		try {
			this.myClass = Class.forName(classe) ;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public short  getId() {
		return this.idPiece ;
	}
	public Class<?> getClasse(){
		return this.myClass;
	}
	

}
