package it.valvorobica.thip.base;

/**
 * Un eccezione che estende da NumberFormatException, con l'unica differenza che indica il campo 
 * sul quale avviene l'eccezione, il campo va passato nel costruttore.
 * <h1>Softre Solutions</h1>
 * <br>
 * @author Daniele Signoroni 17/06/2024
 * <br><br>
 * <b>71XXX	DSSOF3	17/06/2024</b>
 * <p>Prima stesura.<br>
 *  
 * </p>
 */

public class ExcelFieldFormatException extends NumberFormatException {
	
	private static final long serialVersionUID = 1L;
	
	public String fieldName;
    public String fieldValue;

    public ExcelFieldFormatException(String fieldName, String fieldValue, String message) {
        super(message);
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    @Override
    public String toString() {
        return "ExcelFieldFormatException{" +
                "fieldName='" + fieldName + '\'' +
                ", fieldValue='" + fieldValue + '\'' +
                ", message='" + getMessage() + '\'' +
                '}';
    }
}
