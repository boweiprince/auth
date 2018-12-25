package bowei.server;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;

@WebService
public class BoweiBabaServce {

	
	@WebMethod
	public String dis(String display) {
		System.out.println(display);
		return display;
	}
	
	public String queryWeather(String local) {
		System.out.println("Today is sun day!");
		return local;
	}
	
	
	public String say(String name) {
		System.out.println("Hellor Bowei Father , "+ name);
		
		
		return name ;
	}
	
	public static void main(String[] args) {
		Endpoint.publish("http://localhost:8080/weather", new BoweiBabaServce());
		System.out.println("Deploy over!");
	}
}
