package mx.ine.sustseycae.helper;

import org.owasp.encoder.Encode;

public class HLPEncoder {

	private HLPEncoder() {
		throw new IllegalStateException("HLPEncoder is an utility class");
	}
	
	public static final String encode(String data) {
		return data == null ? 
				null
				:Encode.forHtml(Encode.forJavaScriptAttribute(data));
	}
	
	public static final Integer encode(Integer data) {
		return data == null ? 
				null
				:Integer.valueOf(Encode.forHtml(Encode.forJavaScript(data.toString())));
	}
	
	public static final Double encode(Double data) {
		return data == null ? 
				null
				:Double.valueOf(Encode.forHtml(Encode.forJavaScript(data.toString())));
	}
	
}
