package com.mazprojects.aeshae;



import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

//import android.util.Log;


public class CallSoap {

	
	public  final String WSDL_TARGET_NAMESPACE = "http://mazinaltokhais.com";
	public  final String SOAP_ADDRESS = "http://mazinaltokhais.com/test1/Service1.asmx";
	
	public CallSoap() 
	{ 
	}
	public String Call_GetBeds(String _name, String _lat, String _lng, String _city)
	{
		final String SOAP_ACTION ="http://mazinaltokhais.com/UpdateListWS";
		final String OPERATION_NAME ="UpdateListWS";
		
		SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,OPERATION_NAME);
		
		PropertyInfo p = new PropertyInfo();
		p.setName("name");
		p.setNamespace(WSDL_TARGET_NAMESPACE);
		p.setValue(_name);
		request.addProperty(p);

		PropertyInfo p1 = new PropertyInfo();
		p1.setName("lat");
		p1.setNamespace(WSDL_TARGET_NAMESPACE);
		p1.setValue(_lat);
		request.addProperty(p1);
		
		PropertyInfo p2 = new PropertyInfo();
		p2.setName("lng");
		p2.setNamespace(WSDL_TARGET_NAMESPACE);
		p2.setValue(_lng);
		request.addProperty(p2);
		
		PropertyInfo p3 = new PropertyInfo();
		p3.setName("city");
		p3.setNamespace(WSDL_TARGET_NAMESPACE);
		p3.setValue(_city);
		request.addProperty(p3);
		
		//----------------header,------------------------
/*		Element[] header = new Element[1];
		header[0] = new Element().createElement(WSDL_TARGET_NAMESPACE,"AuthHeader");
		//Add Username tag with username,

		Element username = new Element().createElement(WSDL_TARGET_NAMESPACE, "username_tag");
		username.addChild(Node.TEXT, "mazin15");
		header[0].addChild(Node.ELEMENT, username);
		//Add Password tag with password,

		Element password = new Element().createElement(WSDL_TARGET_NAMESPACE, "password_tag");
		password.addChild(Node.TEXT, "Mazin1515");
		header[0].addChild(Node.ELEMENT, password);
		//Add the header to the SoapSerializationEnvelope
				//----------------,------------------------
*/
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		//envelope.headerOut = header;
				
		envelope.implicitTypes = true;
		envelope.setOutputSoapObject(request);
		envelope.dotNet = true;
		HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);
		SoapPrimitive response=null;
		
		try
			{
		
				httpTransport.debug = true;
				httpTransport.call(SOAP_ACTION, envelope);
				//Log.d("dump Request: " ,httpTransport.requestDump);
				response =  (SoapPrimitive) envelope.getResponse();
			}
		catch (Exception exception)
			{
			exception.printStackTrace();
			
				return exception.toString();
			}
			
		return response.toString();
	}
		

}
