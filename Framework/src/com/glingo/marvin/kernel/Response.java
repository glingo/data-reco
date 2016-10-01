package com.glingo.marvin.kernel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import com.glingo.marvin.commons.util.ClassLoaderUtil;
import com.glingo.marvin.commons.util.StringUtils;

public class Response {
	
	public static final int HTTP_CONTINUE 						= 100;
    public static final int HTTP_SWITCHING_PROTOCOLS 			= 101;
    public static final int HTTP_PROCESSING 					= 102;       // RFC2518
    public static final int HTTP_OK 							= 200;
    public static final int HTTP_CREATED 						= 201;
    public static final int HTTP_ACCEPTED 						= 202;
    public static final int HTTP_NON_AUTHORITATIVE_INFORMATION 	= 203;
    public static final int HTTP_NO_CONTENT 					= 204;
    public static final int HTTP_RESET_CONTENT 					= 205;
    public static final int HTTP_PARTIAL_CONTENT 				= 206;
    public static final int HTTP_MULTI_STATUS					= 207;      // RFC4918
    public static final int HTTP_ALREADY_REPORTED 				= 208;      // RFC5842
    public static final int HTTP_IM_USED 						= 226;      // RFC3229
    public static final int HTTP_MULTIPLE_CHOICES 				= 300;
    public static final int HTTP_MOVED_PERMANENTLY 				= 301;
    public static final int HTTP_FOUND 							= 302;
    public static final int HTTP_SEE_OTHER 						= 303;
    public static final int HTTP_NOT_MODIFIED 					= 304;
    public static final int HTTP_USE_PROXY 						= 305;
    public static final int HTTP_RESERVED 						= 306;
    public static final int HTTP_TEMPORARY_REDIRECT 			= 307;
    public static final int HTTP_PERMANENTLY_REDIRECT 			= 308;  	// RFC7238
    public static final int HTTP_BAD_REQUEST 					= 400;
    public static final int HTTP_UNAUTHORIZED 					= 401;
    public static final int HTTP_PAYMENT_REQUIRED 				= 402;
    public static final int HTTP_FORBIDDEN 						= 403;
    public static final int HTTP_NOT_FOUND 						= 404;
    public static final int HTTP_METHOD_NOT_ALLOWED 			= 405;
    public static final int HTTP_NOT_ACCEPTABLE 				= 406;
    public static final int HTTP_PROXY_AUTHENTICATION_REQUIRED 	= 407;
    public static final int HTTP_REQUEST_TIMEOUT 				= 408;
    public static final int HTTP_CONFLICT 						= 409;
    public static final int HTTP_GONE 							= 410;
    public static final int HTTP_LENGTH_REQUIRED 				= 411;
    public static final int HTTP_PRECONDITION_FAILED 			= 412;
    public static final int HTTP_REQUEST_ENTITY_TOO_LARGE 		= 413;
    public static final int HTTP_REQUEST_URI_TOO_LONG 			= 414;
    public static final int HTTP_UNSUPPORTED_MEDIA_TYPE 		= 415;
    public static final int HTTP_REQUESTED_RANGE_NOT_SATISFIABLE = 416;
    public static final int HTTP_EXPECTATION_FAILED 			= 417;
    public static final int HTTP_I_AM_A_TEAPOT 					= 418;		// RFC2324
    public static final int HTTP_UNPROCESSABLE_ENTITY 			= 422;		// RFC4918
    public static final int HTTP_LOCKED 						= 423;		// RFC4918
    public static final int HTTP_FAILED_DEPENDENCY				= 424;		// RFC4918
    public static final int HTTP_RESERVED_FOR_WEBDAV_ADVANCED_COLLECTIONS_EXPIRED_PROPOSAL = 425;   // RFC2817
    public static final int HTTP_UPGRADE_REQUIRED 				= 426;		// RFC2817
    public static final int HTTP_PRECONDITION_REQUIRED 			= 428;		// RFC6585
    public static final int HTTP_TOO_MANY_REQUESTS 				= 429;		// RFC6585
    public static final int HTTP_REQUEST_HEADER_FIELDS_TOO_LARGE = 431;		// RFC6585
    public static final int HTTP_INTERNAL_SERVER_ERROR 			= 500;
    public static final int HTTP_NOT_IMPLEMENTED				= 501;
    public static final int HTTP_BAD_GATEWAY					= 502;
    public static final int HTTP_SERVICE_UNAVAILABLE 			= 503;
    public static final int HTTP_GATEWAY_TIMEOUT 				= 504;
    public static final int HTTP_VERSION_NOT_SUPPORTED 			= 505;
    public static final int HTTP_VARIANT_ALSO_NEGOTIATES_EXPERIMENTAL = 506;// RFC2295
    public static final int HTTP_INSUFFICIENT_STORAGE 			= 507;		// RFC4918
    public static final int HTTP_LOOP_DETECTED 					= 508;		// RFC5842
    public static final int HTTP_NOT_EXTENDED 					= 510; 		// RFC2774
    public static final int HTTP_NETWORK_AUTHENTICATION_REQUIRED = 511;		// RFC6585
	    
	
	private String protocol;
	private String status;
	private String server;
	private String contentType;
	private String contentDisposition;
	private String content;
	private int code;

	private HashMap<String, Object> ressources = new HashMap<String, Object>();
	
	private HashMap<String, ArrayList<String>> flash = new HashMap<String, ArrayList<String>>();
	
	public Response(String protocol, String status, String contentType, int code) {
		super();
		this.protocol = protocol;
		this.status = status;
		this.contentType = contentType;
		this.code = code;
	}
	
	public Response(String protocol, String status, String server, String contentType, int code) {
		super();
		this.protocol = protocol;
		this.status = status;
		this.server = server;
		this.contentType = contentType;
		this.code = code;
	}
	
	public static Response HTTPResponse() {
		return new Response("HTTP/1.1", "OK", "text/html", 200);
	}
	
	public void flush(OutputStream out) {
		PrintWriter writer = new PrintWriter(out);
		
		writer.println(getProtocol() + " " + getCode() + " " + getStatus());
		writer.println("Content-Type : " + getContentType());

		if(content != null)
			writer.println("Content-Length : " + content.getBytes().length);
		
		if(getContentDisposition() != null && !"".equals(getContentDisposition()))
			writer.println("Content-Disposition : " + getContentDisposition());

		if(getServer() != null && !"".equals(getServer()))
			writer.println("Server : " + getServer());
		
		writer.println();
		writer.println(getContent());

		writer.close();
	}
	
	public void renderFromString(String rendered) {
		if(content == null)
			content = "";
		
		content += rendered + StringUtils.NEWLINE;
	}

	public void render(String path) {
		
		boolean parse = path.endsWith(".marvin");
		
		InputStream in = ClassLoaderUtil.getResourceAsStream(path);

		try {
			if (in != null) {
				BufferedReader buffer = new BufferedReader(new InputStreamReader(in));

				String line = buffer.readLine();

				while (line != null) {
					if(parse) renderFromString(line);
					else content += line + StringUtils.NEWLINE;
					line = buffer.readLine();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void addFlash(String key, String value) {
		if(this.flash == null) {
			this.flash = new HashMap<String, ArrayList<String>>();
		}
		
		ArrayList<String> values = this.flash.get(key);
		
		if(values == null)
			values = new ArrayList<String>();
		
		values.add(value);
		
		this.flash.put(key, values);
	}

	public void addRessource(String key, Object value) {
		if(this.ressources == null) {
			this.ressources = new HashMap<String, Object>();
		}
		this.ressources.put(key, value);
	}

	public HashMap<String, Object> getRessources() {
		return ressources;
	}

	public void setRessources(HashMap<String, Object> ressources) {
		this.ressources = ressources;
	}
	
	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getContentDisposition() {
		return contentDisposition;
	}

	public void setContentDisposition(String contentDisposition) {
		this.contentDisposition = contentDisposition;
	}

	public HashMap<String, ArrayList<String>> getFlash() {
		return flash;
	}

	public void setFlash(HashMap<String, ArrayList<String>> flash) {
		this.flash = flash;
	}
	
	public static void main(String[] args) {
		Response response = new Response("HTTP/1.1", "OK", "text/html", 200);
		
		response.addFlash("message", "ok ok ");
		response.addFlash("message", "lol");
		response.addFlash("erreurs", "erreur");
		
		for (Iterator<Entry<String, ArrayList<String>>> iterator = response.getFlash().entrySet().iterator(); iterator.hasNext();) {
			Entry<String, ArrayList<String>> entry = (Entry<String, ArrayList<String>>) iterator.next();
			System.out.println(entry.getKey());		
			for (Iterator<String> iterator2 = entry.getValue().iterator(); iterator2.hasNext();) {
				String value = (String) iterator2.next();
				System.out.println(value);
			}
		}
	}

}
