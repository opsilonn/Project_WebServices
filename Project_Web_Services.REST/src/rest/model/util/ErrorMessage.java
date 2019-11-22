package rest.model.util;


import javax.xml.bind.annotation.XmlRootElement;




@XmlRootElement
public class ErrorMessage {

	private String errorMessage;
	private int errorCode;
	private String documentation;
	
	
	public ErrorMessage() { }
	
	
	public ErrorMessage(String errorMessage, int errorCode, String documentation) {
		super();
		
		this.errorMessage = errorMessage;
		this.errorCode = errorCode;
		this.documentation = documentation;
	}


	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}


	/**
	 * @param errorMessage the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}


	/**
	 * @return the errorCode
	 */
	public int getErrorCode() {
		return errorCode;
	}


	/**
	 * @param errorCode the errorCode to set
	 */
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}


	/**
	 * @return the documentation
	 */
	public String getDocumentation() {
		return documentation;
	}


	/**
	 * @param documentation the documentation to set
	 */
	public void setDocumentation(String documentation) {
		this.documentation = documentation;
	}

	
	
}
