package ro.seava.j4e.api.descriptor;

public interface IJobDefinition {

	public String getName();

	public void setName(String name);

	public Class<?> getJavaClass();

	public void setJavaClass(Class<?> javaClass);
}
