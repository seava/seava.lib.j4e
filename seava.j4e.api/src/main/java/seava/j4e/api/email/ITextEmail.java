package seava.j4e.api.email;

import seava.j4e.api.exceptions.EmailException;

public interface ITextEmail extends IEmail {
	public IEmail setMsg(String msg) throws EmailException;
}
