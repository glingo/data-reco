package com.glingo.marvin.server.response.templating;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.glingo.marvin.server.request.Request;
import com.glingo.marvin.server.response.Response;
import com.glingo.marvin.util.ArrayUtils;
import com.glingo.marvin.util.ObjectUtils;
import com.glingo.marvin.util.StringUtils;

public class Renderer {

	private static final Pattern pattern = Pattern.compile(Pattern.quote("{{ ") + "(.*?)" + Pattern.quote(" }}"));
	
	public Renderer() {}

	private String resolve(Collection<String> map, Object holder) throws Exception {
		
		if(map != null && !map.isEmpty()) {
			Iterator<String> iterator = map.iterator();
			
			String segment = (String) iterator.next();
			
			if(iterator.hasNext() && holder != null) {
				Object resolved = ObjectUtils.get(iterator.next(), holder);
				map.remove(segment);
				return resolve(map, resolved);
			} else {
				return StringUtils.toString(holder);
			}
		}
		
		return "";
	}
	
	private String resolve(String s, HashMap<String, Object> model) throws Exception {
		
		if(model == null || model.isEmpty())
			return "";

		Collection<String> map = ArrayUtils.asCollection(s, "[.]");
		
		if(map != null && !map.isEmpty()) {
			Iterator<String> iterator = map.iterator();
			String segment = (String) iterator.next();
			return resolve(map, model.get(segment));
		}
		
		return "";
	}
	
	public void render(PrintWriter writer, Response response, Request request) throws IOException {
		
		if(response == null || writer == null)
			return;
		
		Template template = new Template();
		
		if(response.getForward() != null) {
			template.setPath(response.getForward());
		} else if(request.getUri() != null) {
			template.setPath(request.getUri().replaceFirst("/", ""));
		}
		
		template.load();
		
		response.setContent(template.getBody());

		Matcher matcher = pattern.matcher(template.getBody());
		
		while (matcher.find()) {
			try {
				String r = resolve(matcher.group(1), response.getRessources());
				String find = template.getBody().substring(matcher.start(), matcher.end());
				
				response.setContent(response.getContent().replace(find, r));
			} catch(Exception e) {
				e.printStackTrace(writer);
			}
		}

		if(response.getContent() != null && !"".equals(response.getContent()))
			writer.println(response.getContent());
		
	}
}
