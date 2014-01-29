package ro.seava.j4e.api.email;

public interface IEmailFactory {
	public IEmail createEmail(int type);

	public ITextEmail createTextEmail();

	public IHtmlEmail createHtmlEmail();

	public IMultiPartEmail createMultiPartEmail();
}
