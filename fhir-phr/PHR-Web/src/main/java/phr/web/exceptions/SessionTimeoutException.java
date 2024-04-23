package phr.web.exceptions;

public class SessionTimeoutException extends Exception {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 詳細メッセージに null を使用して、新規例外を構築します。より詳細な情報は{@link Exception#Exception()}
	 * を参照してください。
	 */
	public SessionTimeoutException()
	{
		super();
	}

	/**
	 * 指定された詳細メッセージを使用して、新規例外を構築します。より詳細な情報は{@link Exception#Exception(String)}
	 * を参照してください。
	 * 
	 * @param message
	 *            詳細メッセージ
	 */
	public SessionTimeoutException(String message)
	{
		super(message);
	}

	/**
	 * (cause==null &#063; null : cause.
	 * toString())の指定された原因および詳細メッセージを使用して新規例外を構築します。より詳細な情報は
	 * {@link Exception#Exception(Throwable)}を参照してください。
	 * 
	 * @param message
	 *            詳細メッセージ
	 * @param cause
	 *            原因
	 */
	public SessionTimeoutException(String message, Throwable cause)
	{
		super(message, cause);
	}

	/**
	 * 指定された詳細メッセージおよび原因を使用して新規例外を構築します。より詳細な情報は
	 * {@link Exception#Exception(String, Throwable)}を参照してください。
	 * 
	 * @param cause
	 *            原因
	 */
	public SessionTimeoutException(Throwable cause)
	{
		super(cause);
	}
}
