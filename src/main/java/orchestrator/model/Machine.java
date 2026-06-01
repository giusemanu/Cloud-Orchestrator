package orchestrator.model;

/**
 * Classe astratta che rappresenta la struttura base di una macchina (virtuale o containerizzata).
 * Implementa {@link ResourceCloud} e funge da superclasse nel pattern polimorfico.
 */

public abstract class Machine implements ResourceCloud{
    private String name;
    private String id;

    public String getName(){
        return name;
    }
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getId(){
		return id;	
	}

	public void setId(String id){
	    this.id = id;
	}
}
