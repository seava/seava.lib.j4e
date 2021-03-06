package seava.j4e.api.email;

import seava.j4e.api.exceptions.EmailException;

public interface IHtmlEmail extends IEmail {

	public IHtmlEmail setHtmlMsg(String aHtml) throws EmailException;

	public IHtmlEmail setTextMsg(String aText) throws EmailException;

}
