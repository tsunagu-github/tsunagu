package kis.inc.ssmix2storagemaker.search.dto;

public class ErrorDefinitionDto implements Cloneable {

	@Override
	public ErrorDefinitionDto clone() throws CloneNotSupportedException {
		// TODO 自動生成されたメソッド・スタブ
		ErrorDefinitionDto obj = (ErrorDefinitionDto)super.clone();
		obj.errorId = this.errorId.toString();
		obj.errorMessage = this.errorMessage.toString();
		obj.status = this.status;
		obj.name = this.name.toString();
		
		return obj;
	}

	private String name;
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private String errorId;
	private int status;
	private String errorMessage;
	
	public String getErrorId() {
		return errorId;
	}

	public void setErrorId(String errorId) {
		this.errorId = errorId;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public ErrorDefinitionDto() {
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
