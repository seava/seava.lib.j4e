package ro.seava.j4e.api.security;

public interface ILoginParams {

	public abstract String getClientCode();

	public abstract void setClientCode(String clientCode);

	public abstract String getLanguage();

	public abstract void setLanguage(String language);

	public abstract String getUserAgent();

	public abstract void setUserAgent(String userAgent);

	public abstract String getRemoteHost();

	public abstract void setRemoteHost(String remoteHost);

	public abstract String getRemoteIp();

	public abstract void setRemoteIp(String remoteIp);

}